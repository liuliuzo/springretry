package com.liuliu.demo.springretry.state;

import java.util.Collections;

import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryState;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;

/**
 * 当把状态放入缓存时，通过该key查询获取，全局模式 DataAccessException进行回滚
 */
public class RetryTemplate07 {

	public static void main(String[] args) throws Exception {
		RetryTemplate template = new RetryTemplate();
		Object key = "mykey";
		boolean isForceRefresh = true;
		BinaryExceptionClassifier rollbackClassifier = new BinaryExceptionClassifier(
				Collections.<Class<? extends Throwable>>singleton(DataAccessException.class));
		RetryState state = new DefaultRetryState(key, isForceRefresh, rollbackClassifier);
		String result = template.execute(new RetryCallback<String, RuntimeException>() {
			@Override
			public String doWithRetry(RetryContext context) throws RuntimeException {
				System.out.println("retry count:" + context.getRetryCount());
				throw new TypeMismatchDataAccessException();
			}
		}, new RecoveryCallback<String>() {
			@Override
			public String recover(RetryContext context) throws Exception {
				return "default";
			}
		}, state);
		System.out.println(result);
	}
}
