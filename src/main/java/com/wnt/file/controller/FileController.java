package com.wnt.file.controller;

import com.wnt.file.repository.FileDinhKemRepository;
import com.wnt.file.request.search.FileDinhKemSearchReq;
import com.wnt.file.service.MinioService;
import com.wnt.file.table.FileDinhKem;
import com.wnt.file.util.FileDto;
import com.wnt.file.enums.EnumResponse;
import com.wnt.file.response.BaseResponse;
import com.wnt.file.secification.FileDinhKemSpecification;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(value = "/file")
@Api(tags = "Upload/Download")
public class FileController {
	@Autowired
    private MinioService minioService;

	@Autowired
	private FileDinhKemRepository fileDinhKemRepository;

    @GetMapping
    public ResponseEntity<Object> getFiles() {
        return ResponseEntity.ok(minioService.getListObjects());
    }
    @ApiOperation(value = "Upload file lên server", response = List.class)
    @PostMapping(value = "/upload")
    public ResponseEntity<Object> upload(@ModelAttribute FileDto request) {
        return ResponseEntity.ok().body(minioService.uploadFile(request));
    }

    @ApiOperation(value = "Tải file từ server")
    @GetMapping(value = "/**")
    public ResponseEntity<Object> getFile(HttpServletRequest request) throws IOException {
        String pattern = (String) request.getAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE);
        String filename = new AntPathMatcher().extractPathWithinPattern(pattern, request.getServletPath());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header("Content-disposition", "attachment; filename=\"" + filename + "\"")
                .body(IOUtils.toByteArray(minioService.getObject(filename)));
    }

    @ApiOperation(value = "Upload file lên server và lưu db", response = List.class)
    @PostMapping(value = "/upload-attachment")
    public ResponseEntity<Object> uploadAttach(@ModelAttribute FileDto request) {
    	FileDto result = minioService.uploadFile(request);
    	if (StringUtils.isNotBlank(request.getDataType())) {
    		FileDinhKem file = new FileDinhKem();
    		file.setFileName(result.getFilename());
    		file.setFileSize(result.getSize()+"");
    		file.setFileUrl(result.getUrl());
    		file.setDataType(request.getDataType());
    		file.setDataId(request.getDataId());
    		fileDinhKemRepository.save(file);
    	}
    	return ResponseEntity.ok().body(result);
    }

    @ApiOperation(value = "Lấy danh sách file", response = List.class)
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<BaseResponse> colection(HttpServletRequest request,
			@Valid @RequestBody FileDinhKemSearchReq objReq) {
		BaseResponse resp = new BaseResponse();
		try {

			List<FileDinhKem> dataPage = fileDinhKemRepository
					.findAll(FileDinhKemSpecification.buildSearchQuery(objReq));

			resp.setData(dataPage);
			resp.setStatusCode(EnumResponse.RESP_SUCC.getValue());
			resp.setMsg(EnumResponse.RESP_SUCC.getDescription());
		} catch (

		Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}

	@ApiOperation(value = "Tải file từ server", response = List.class)
	@PostMapping(value = "/download", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> download(@Valid @RequestBody FileDinhKemSearchReq objReq, HttpServletResponse response) {
		BaseResponse resp = new BaseResponse();
		try {

			List<FileDinhKem> dataPage = fileDinhKemRepository
					.findAll(FileDinhKemSpecification.buildSearchQuery(objReq));

			if (CollectionUtils.isEmpty(dataPage))
				return ResponseEntity.ok(resp);

			if (dataPage.size() == 1) {
				FileDinhKem fileDinhKem = dataPage.get(0);
				response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileDinhKem.getFileName() + "\"");
				return ResponseEntity.ok(IOUtils.toByteArray(minioService.getObject(fileDinhKem.getFileUrl())));
			}
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());

			response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + objReq.getDataType() + "_" + currentDateTime + ".zip" + "\"");

			return ResponseEntity.ok(minioService.downloadZipFile(dataPage, response));

		} catch (Exception e) {
			resp.setStatusCode(EnumResponse.RESP_FAIL.getValue());
			resp.setMsg(e.getMessage());
			log.error(e.getMessage());
		}

		return ResponseEntity.ok(resp);
	}
}