package com.liuliu.demo.springretry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
//proxyTargetClass属性为true时，使用CGLIB代理。默认使用标准JAVA注解。
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringretryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringretryApplication.class, args);
	}
	
}
