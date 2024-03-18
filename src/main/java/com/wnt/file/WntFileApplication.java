package com.wnt.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = { "com.wnt.file.table" })
public class WntFileApplication {
	public static void main(String[] args) {
		SpringApplication.run(WntFileApplication.class, args);
	}

}
