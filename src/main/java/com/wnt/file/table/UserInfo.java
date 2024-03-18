package com.wnt.file.table;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "UserProfile")
@Data
public class UserInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "UserId")
	Long userId;
	@Column(name = "UserName")
	String userName;
	@Column(name = "TenDayDu")
	String tenDayDu;
	@Column(name = "HoatDong")
	String hoatDong;//bit
	@Column(name = "SoCMT")
	String soCMT;
	@Column(name = "Enable_NT")
	Integer enable_NT;
	@Column(name = "ArchivedId")
	Long archivedId;
	@Column(name = "StoreId")
	Long storeId;
	@Column(name = "Email")
	String email;
	@Column(name = "MaNhaThuoc")
	String maNhaThuoc;
	@Column(name = "CityId")
	Long cityId;
	@Column(name = "SoDienThoai")
	String soDienThoai;
	@Column(name = "WardId")
	Long wardId;
	@Column(name = "Addresses")
	String addresses;
	@Column(name = "RegionId")
	Long regionId;
	@Column(name = "TypeAccount")
	Integer typeAccount;
	@Column(name = "TokenDevice")
	String tokenDevice;
	@Column(name = "TokenBrowser")
	String tokenBrowser;
	@Column(name = "IsVerificationAccount")
	Integer isVerificationAccount;//bit
	@Column(name = "TokenDevice2")
	String tokenDevice2;
}
