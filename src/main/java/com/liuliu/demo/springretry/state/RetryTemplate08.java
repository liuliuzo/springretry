package com.liuliu.demo.springretry.state;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryState;
import org.springframework.retry.policy.CircuitBreakerRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;

/**
 * 	熔断器场景。在有状态重试时，且是全局模式，不在当前循环中处理重试，而是全局重试模式（不是线程上下文），
 * 	如熔断器策略时测试代码如下所示
 */
public class RetryTemplate08 {

	public static void main(String[] args) throws Exception {
		RetryTemplate template = new RetryTemplate();
		CircuitBreakerRetryPolicy retryPolicy = new CircuitBreakerRetryPolicy(new SimpleRetryPolicy(3));
		retryPolicy.setOpenTimeout(5000);
		retryPolicy.setResetTimeout(20000);
		template.setRetryPolicy(retryPolicy);
		for (int i = 0; i < 10; i++) {
			try {
				Object key = "circuit";
				boolean isForceRefresh = false;
				RetryState state = new DefaultRetryState(key, isForceRefresh);
				String result = template.execute(new RetryCallback<String, RuntimeException>() {
					@Override
					public String doWithRetry(RetryContext context) throws RuntimeException {
						System.out.println("retry count:" + context.getRetryCount());
						throw new RuntimeException("timeout");
					}
				}, new RecoveryCallback<String>() {
					@Override
					public String recover(RetryContext context) throws Exception {
						return "default";
					}
				}, state);
				System.out.println(result);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
