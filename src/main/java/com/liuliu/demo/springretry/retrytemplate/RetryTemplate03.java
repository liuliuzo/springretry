package com.liuliu.demo.springretry.retrytemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * 该策略定义了对指定的异常进行若干次重试。默认情况下，对Exception异常及其子类重试3次。
 * 如果创建SimpleRetryPolicy并指定重试异常map，可以选择性重试或不进行重试。
 * 下面的代码定义了对TimeOutException进行重试。
 */
public class RetryTemplate03 {

	public static void main(String[] args) throws Exception {
		
		RetryTemplate template = new RetryTemplate();
		Map<Class<? extends Throwable>, Boolean> maps = new HashMap<Class<? extends Throwable>, Boolean>();
		maps.put(TimeoutException.class, true);
		SimpleRetryPolicy policy2 = new SimpleRetryPolicy(2, maps);
		template.setRetryPolicy(policy2);

		String result = template.execute(new RetryCallback<String, Exception>() {
			public String doWithRetry(RetryContext arg0) throws Exception {
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
