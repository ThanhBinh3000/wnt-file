package com.wnt.file.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class PaggingReq {
	@NotNull(message = "Không được để trống")
	@ApiModelProperty(example = "20")
	Integer limit;
	@NotNull(message = "Không được để trống")
	@PositiveOrZero(message = "Trang tìm kiếm phải >= 1")
	@ApiModelProperty(example = "1")
	Integer page;
}