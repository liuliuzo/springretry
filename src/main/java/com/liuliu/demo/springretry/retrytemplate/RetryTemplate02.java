package com.liuliu.demo.springretry.retrytemplate;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;


/**
 * 	代码重试两次后，仍然失败，RecoveryCallback被调用，返回”recovery callback”。
 * 	如果没有定义RecoveryCallback，那么重试2次后，将会抛出异常。
 */
public class RetryTemplate02 {

	public static void main(String[] args) throws Exception {

		RetryTemplate template = new RetryTemplate();
		SimpleRetryPolicy policy = new SimpleRetryPolicy();
		policy.setMaxAttempts(2);
		template.setRetryPolicy(policy);

		String result = template.execute(new RetryCallback<String, Exception>() {
			public String doWithRetry(RetryContext arg0) throws Exception {
				throw new NullPointerException("nullPointerException");
			}
		}, new RecoveryCallback<String>() {
			public String recover(RetryContext context) throws Exception {
				return "recovery callback";
			}
		});
		System.out.println(result);
	}
}
