package com.liuliu.demo.springretry.retrytemplate;

import java.util.concurrent.TimeoutException;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.policy.CompositeRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 *	用户指定一组策略，随后根据optimistic选项来确认如何重试。
 * 	下面的代码中创建CompositeRetryPolicy策略，并创建了RetryPolicy数组，数组有两个具体策略SimpleRetryPolicy与AlwaysRetryPolicy。
 * 	当CompositeRetryPolicy设置optimistic为true时，Spring-retry会顺序遍历RetryPolicy[]数组，如果有一个重试策略可重试，例如SimpleRetryPolicy没有达到重试次数，那么就会进行重试。 
 * 	如果optimistic选项设置为false。那么有一个重试策略无法重试，那么就不进行重试。
 * 	例如SimpleRetryPolicy达到重试次数不能再重试，而AlwaysRetryPolicy可以重试，那么最终是无法重试的。
 *  下面代码设置setOptimistic(true)，而AlwaysRetryPolicy一直可重试，那么最终可以不断进行重试。
 */
public class RetryTemplate05 {

	public static void main(String[] args) throws Exception {
		RetryTemplate template = new RetryTemplate();

		CompositeRetryPolicy policy = new CompositeRetryPolicy();
		RetryPolicy[] polices = { new SimpleRetryPolicy(), new AlwaysRetryPolicy() };

		policy.setPolicies(polices);
		policy.setOptimistic(true);
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
