package com.liuliu.demo.springretry.statistics;

import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryStatistics;
import org.springframework.retry.stats.DefaultStatisticsRepository;
import org.springframework.retry.stats.StatisticsListener;
import org.springframework.retry.support.RetryTemplate;

/**
 * spring-retry通过RetryListener实现拦截器模式，默认提供了StatisticsListener实现重试操作统计分析数据
 * 此处要给操作定义一个name如“method.key”，从而查询该操作的统计分析数据
 */
public class RetryTemplate09 {

	public static void main(String[] args) throws Exception {

		RetryTemplate template = new RetryTemplate();
		DefaultStatisticsRepository repository = new DefaultStatisticsRepository();
		StatisticsListener listener = new StatisticsListener(repository);
		template.setListeners(new RetryListener[] { listener });
		for (int i = 0; i < 10; i++) {
			String result = template.execute(new RetryCallback<String, RuntimeException>() {
				@Override
				public String doWithRetry(RetryContext context) throws RuntimeException {
					context.setAttribute(RetryContext.NAME, "method.key");
					return "ok";
				}
			});
		}

		RetryStatistics statistics = repository.findOne("method.key");
		System.out.println(statistics);
	}

}
