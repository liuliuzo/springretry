package com.liuliu.demo.springretry;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

import com.liuliu.demo.springretry.service.RetryService;

@Configuration
//@EnableRetry能否重试。
@EnableRetry
//当proxyTargetClass属性为true时，使用CGLIB代理。默认使用标准JAVA注解。
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RetryServiceMain {

	@Bean
	public RetryService retryService() {
		return new RetryService();
	}

	public static void main(String[] args) throws Exception {
		final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				RetryServiceMain.class);
		final RetryService retryService = applicationContext.getBean(RetryService.class);
		//retryService.retryTest01();
		retryService.retryTest02("testtesttesttesttest");
	}

}