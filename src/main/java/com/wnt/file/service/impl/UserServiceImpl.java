package com.wnt.file.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wnt.file.repository.feign.UserProfileFeign;
import com.wnt.file.service.UserService;
import com.wnt.file.table.system.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserProfileFeign userProfileFeign;

    @Override

    public Optional<Profile> findUserByToken(String token) {
        ObjectMapper objectMapper = new ObjectMapper();
        Profile profile = objectMapper.convertValue(userProfileFeign.getProfile().getData(), Profile.class);
        return Optional.of(profile);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
