package com.wnt.file.table.system;

import lombok.Data;

import java.util.Date;

@Data
public class WrapData {
    private String code;
    private Date sendDate;
    private Object data;
}
