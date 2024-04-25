package com.wnt.file.config;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException e)
			throws IOException {

		log.error("Unauthorized error. Message - {}", e.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Thông tin đăng nhập của người dùng không hợp lệ hoặc đã hết hạn");
	}
}