package com.wnt.file.table;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "FILE_DINH_KEM")
@Data
public class FileDinhKem {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_DINH_KEM_SEQ")
	@SequenceGenerator(sequenceName = "FILE_DINH_KEM_SEQ", allocationSize = 1, name = "FILE_DINH_KEM_SEQ")
	Long id;
	String fileName;
	String fileSize;
	String fileUrl;
	String fileType;
	String dataType;
	Long dataId;
	Date createDate;
}
