package com.wnt.file.request.search;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wnt.file.request.BaseRequest;
import com.wnt.file.util.Contains;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDinhKemSearchReq extends BaseRequest {

	@ApiModelProperty(example = "HANG_HOA")
	@NotNull
	String dataType;
	
	@ApiModelProperty(example = "12345")
	@NotNull
	Long dataId;
}
