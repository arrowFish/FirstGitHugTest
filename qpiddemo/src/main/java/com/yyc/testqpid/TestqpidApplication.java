package com.yyc.testqpid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ServletComponentScan
@ImportResource("classpath:application-qpid.xml")
@ComponentScan(basePackages = "com.yyc")
public class TestqpidApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestqpidApplication.class, args);
	}

}

