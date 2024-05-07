package com.example.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.example")
@MapperScan("com.example.shiro.dao")
@SpringBootApplication
public class shiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(shiroApplication.class, args);
	}

}
