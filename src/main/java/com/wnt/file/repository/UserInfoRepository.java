package com.wnt.file.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.wnt.file.table.UserInfo;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

	UserInfo findByUserName(String username);

	//UserInfo findByToken(String token);
}
