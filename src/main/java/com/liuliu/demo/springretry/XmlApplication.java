package com.liuliu.demo.springretry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.retry.annotation.EnableRetry;


@SpringBootApplication
@EnableRetry
@EnableAspectJAutoProxy
@ImportResource("classpath:/retryadvice.xml")
public class XmlApplication {
	public static void main(String[] args) {
		SpringApplication.run(XmlApplication.class, args);
	}
}