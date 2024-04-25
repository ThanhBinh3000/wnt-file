package com.wnt.file.service;

import com.wnt.file.table.system.Profile;

import java.util.Optional;

public interface UserService {
    Optional<Profile> findUserByToken(String token);

}
