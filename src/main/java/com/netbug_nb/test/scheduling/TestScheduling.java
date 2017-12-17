package com.netbug_nb.test.scheduling;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.netbug_nb.application.SpringBeanUtil;
import com.netbug_nb.test.TestInterface;

@EnableScheduling
@Component
@ConditionalOnProperty(prefix = "netbug_nb.test.scheduling", name = "enable", havingValue = "true")
public class TestScheduling {
	private final static Logger logger = LoggerFactory.getLogger(TestScheduling.class);

	@Scheduled(initialDelayString = "${netbug_nb.test.scheduling.initialDelay:5000}", fixedDelayString = "${netbug_nb.test.scheduling.fixedDelay:5000}")
	public void schedule() {
		logger.debug("计划正在执行……");
		Map<String, TestInterface> map = SpringBeanUtil.getBeansOfType(TestInterface.class);
		for (TestInterface ti : map.values()) {
			ti.sayHello();
		}
	}
}
