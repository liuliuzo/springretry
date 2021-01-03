package com.liuliu.demo.springretry.service;

import java.util.concurrent.TimeoutException;

import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class RetryService {

	@Retryable(value = { RemoteAccessException.class,
			TimeoutException.class }, maxAttempts = 5, backoff = @Backoff(delay = 500l, multiplier = 1))
	public void retryTest01(String arg01) throws Exception {
		System.out.println("do something...");
		throw new RemoteAccessException("RemoteAccessException....");
	}

	@Retryable(value = { RemoteAccessException.class,
			TimeoutException.class }, maxAttempts = 5, backoff = @Backoff(delay = 500l, multiplier = 1))
	public void retryTest02(String arg01) throws Exception {
		System.out.println("do something...");
		throw new TimeoutException("TimeoutException....");
	}

	@Recover
	public void recover(RemoteAccessException e,String arg01) {
		System.out.println(e.getMessage());
		System.out.println("RemoteAccessException recover...."+arg01);
	}

	@Recover
	public void recover(TimeoutException e,String arg01) {
		System.out.println(e.getMessage());
		System.out.println("TimeoutException recover...."+arg01);
	}
}