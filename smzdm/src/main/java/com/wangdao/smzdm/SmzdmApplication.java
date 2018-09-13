package com.wangdao.smzdm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SmzdmApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmzdmApplication.class, args);
	}
}
