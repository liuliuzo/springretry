package com.liuliu.demo.springretry.retrytemplate;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * 	TimeoutRetryPolicy策略，TimeoutRetryPolicy超时时间默认是1秒。
 * 	TimeoutRetryPolicy超时是指在execute方法内部，从open操作开始到调用TimeoutRetryPolicy的canRetry方法这之间所经过的时间。
 * 	这段时间未超过TimeoutRetryPolicy定义的超时时间，那么执行操作，否则抛出异常。
 * 	当重试执行完闭，操作还未成为，那么可以通过RecoveryCallback完成一些失败事后处理。
 */
public class RetryTemplate01 {

	public static void main(String[] args) throws Exception {
		RetryTemplate template = new RetryTemplate();
		TimeoutRetryPolicy policy = new TimeoutRetryPolicy();
		template.setRetryPolicy(policy);

		String result = template.execute(new RetryCallback<String, Exception>() {
			public String doWithRetry(RetryContext arg0) throws Exception {
				return "Retry";
			}
		});
		System.out.println(result);
	}
}




