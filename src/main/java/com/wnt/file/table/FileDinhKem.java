package com.wnt.file.table;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FileDinhKem")
@Data
public class FileDinhKem {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="Id")
	Long id;
	@Column(name="FileName")
	String fileName;
	@Column(name="FileSize")
	String fileSize;
	@Column(name="FileUrl")
	String fileUrl;
	@Column(name="FileType")
	String fileType;
	@Column(name="DataType")
	String dataType;
	@Column(name="DataId")
	Long dataId;
	@Column(name="CreateDate")
	Date createDate;
}
