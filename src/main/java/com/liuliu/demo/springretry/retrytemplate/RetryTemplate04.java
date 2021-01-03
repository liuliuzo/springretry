package com.liuliu.demo.springretry.retrytemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * 通过PolicyMap定义异常及其重试策略。
 * 下面的代码在抛出NullPointerException采用NeverRetryPolicy策略，而TimeoutException采用AlwaysRetryPolicy。
 */
public class RetryTemplate04 {

	public static void main(String[] args) throws Exception {
		RetryTemplate template = new RetryTemplate();
		ExceptionClassifierRetryPolicy policy = new ExceptionClassifierRetryPolicy();
		Map<Class<? extends Throwable>, RetryPolicy> policyMap = new HashMap<Class<? extends Throwable>, RetryPolicy>();
		policyMap.put(TimeoutException.class, new AlwaysRetryPolicy());
		policyMap.put(NullPointerException.class, new NeverRetryPolicy());
		policy.setPolicyMap(policyMap);
		template.setRetryPolicy(policy);
		String result = template.execute(new RetryCallback<String, Exception>() {
			public String doWithRetry(RetryContext arg0) throws Exception {
				if (arg0.getRetryCount() >= 2) {
					Thread.sleep(1000);
					throw new NullPointerException();
				}
				throw new TimeoutException("TimeoutException");
			}
		}, new RecoveryCallback<String>() {
			public String recover(RetryContext context) throws Exception {
				return "recovery callback";
			}
		});
		System.out.println(result);
	}

}
