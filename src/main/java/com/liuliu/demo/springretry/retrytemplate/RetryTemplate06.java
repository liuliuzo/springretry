package com.liuliu.demo.springretry.retrytemplate;

import java.util.concurrent.TimeoutException;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.ExponentialRandomBackOffPolicy;
import org.springframework.retry.policy.AlwaysRetryPolicy;
import org.springframework.retry.policy.CompositeRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * 	通过监听器，可以在重试操作的某些位置嵌入调用者定义的一些操作，以便在某些场景触发。
 * 	代码注册了两个Listener，Listener中的三个实现方法，onError,open,close会在执行重试操作时被调用，
 * 	在RetryTemplate中doOpenInterceptors,doCloseInterceptors,doOnErrorInterceptors会调用监听器对应的open,close,onError方法。
 *  doOpenInterceptors方法在第一次重试之前会被调用，如果该方法返回true，则会继续向下直接，如果返回false，则抛出异常，停止重试。
 *  doCloseInterceptors 会在重试操作执行完毕后调用。
 *  doOnErrorInterceptors 在抛出异常后执行，
 *  当注册多个Listener时，open方法按会按Listener的注册顺序调用，而onError和close则按Listener注册的顺序逆序调用。
 */
public class RetryTemplate06 {

	public static void main(String[] args) throws Exception {
		
		RetryTemplate template = new RetryTemplate();

		ExponentialRandomBackOffPolicy exponentialBackOffPolicy = new ExponentialRandomBackOffPolicy();
		exponentialBackOffPolicy.setInitialInterval(1500);
		exponentialBackOffPolicy.setMultiplier(2);
		exponentialBackOffPolicy.setMaxInterval(6000);

		CompositeRetryPolicy policy = new CompositeRetryPolicy();
		RetryPolicy[] polices = { new SimpleRetryPolicy(), new AlwaysRetryPolicy() };

		policy.setPolicies(polices);
		policy.setOptimistic(true);

		template.setRetryPolicy(policy);
		template.setBackOffPolicy(exponentialBackOffPolicy);

		template.registerListener(new RetryListener() {
			public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
				System.out.println("open");
				return true;
			}
			public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
					Throwable throwable) {
				System.out.println("onError");
			}
			public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
					Throwable throwable) {
				System.out.println("close");
			}
		});

		template.registerListener(new RetryListener() {
			public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
				System.out.println("open2");
				return true;
			}
			public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback,
					Throwable throwable) {

				System.out.println("onError2");
			}
			public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback,
					Throwable throwable) {
				System.out.println("close2");
			}
		});
		String result = template.execute(new RetryCallback<String, Exception>() {
			public String doWithRetry(RetryContext arg0) throws Exception {
				arg0.getAttribute("");
				if (arg0.getRetryCount() >= 2) {
					throw new NullPointerException();
				}
				throw new TimeoutException("TimeoutException");
			}
		});
		System.out.println(result);
	}

}
