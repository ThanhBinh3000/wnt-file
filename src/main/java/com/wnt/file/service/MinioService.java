package com.wnt.file.service;

import com.wnt.file.table.FileDinhKem;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wnt.file.util.FileDto;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.buckek.name}")
    private String bucketName;

    public List<FileDto> getListObjects() {
        List<FileDto> objects = new ArrayList<>();
        try {
            Iterable<Result<Item>> result = minioClient.listObjects(ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .recursive(true)
                    .build());
            for (Result<Item> item : result) {
                objects.add(FileDto.builder()
                        .filename(item.get().objectName())
                        .size(item.get().size())
                        .url(getPreSignedUrl(item.get().objectName()))
                        .build());
            }
            return objects;
        } catch (Exception e) {
            log.error("Happened error when get list objects from minio: ", e);
        }

        return objects;
    }

    public FileDto uploadFile(FileDto request) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(request.getFolder()+"/"+request.getFile().getOriginalFilename())
                    .stream(request.getFile().getInputStream(), request.getFile().getSize(), -1)
                    .build());
        } catch (Exception e) {
            log.error("Happened error when upload file: ", e);
        }
        return FileDto.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .size(request.getFile().getSize())
                //.url(getPreSignedUrl(request.getFile().getOriginalFilename()))
                .url(request.getFolder() +"/"+ request.getFile().getOriginalFilename())
                .filename(request.getFile().getOriginalFilename())
                .dataType(request.getDataType())
                .dataId(request.getDataId())
                .build();
    }
    private String getPreSignedUrl(String filename) {
        return filename.substring(0, filename.length() - 1);
        //return "http://192.168.1.69:8080/file/".concat(filename);
    }
    public InputStream getObject(String filename) {
        InputStream stream;
        try {
            stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(filename)
                    .build());
        } catch (Exception e) {
            log.error("Happened error when get list objects from minio: ", e);
            return null;
        }

        return stream;
    }

    public byte[] downloadZipFile(List<FileDinhKem> dataPage, HttpServletResponse response) throws IOException {
        int BUFFER_SIZE = 1024;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
        ZipEntry zipEntry = null;
        InputStream inputStream = null;

        try {
            for (FileDinhKem fileDinhKem : dataPage) {
                zipEntry = new ZipEntry(fileDinhKem.getFileName());
                inputStream = this.getObject(fileDinhKem.getFileUrl());
                zipOutputStream.putNextEntry(zipEntry);
                byte[] bytes = new byte[BUFFER_SIZE];
                int length;
                while ((length = inputStream.read(bytes)) >= 0) {
                    zipOutputStream.write(bytes, 0, length);
                }
            }
            // set zip size in response
            response.setContentLength((int) (zipEntry != null ? zipEntry.getSize() : 0));
        } catch (IOException e) {
            log.error("Exception while reading and streaming data {} ", e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            zipOutputStream.close();
        }
        return baos.toByteArray();
    }

    public Boolean deleteFile(List<DeleteObject> objects)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            Iterable<Result<DeleteError>> results =
                    minioClient.removeObjects(
                            RemoveObjectsArgs.builder().bucket(bucketName).objects(objects).build());
            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                System.out.println(
                        "Error in deleting object " + error.objectName() + "; " + error.message());
                return false;
            }
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            return false;
        }
        return true;
    }

    @PostConstruct
    public void init() {
        System.out.println("================== " + bucketName + "================== ");
    }
}