package com.netbug_nb.test.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.netbug_nb.test.scheduling.TestScheduling;

@Component
@Aspect
@EnableAspectJAutoProxy
@ConditionalOnProperty(prefix = "netbug_nb.test.aspect", name = "enable", havingValue = "true")
public class TestAspect {
	private final static Logger logger = LoggerFactory.getLogger(TestAspect.class);

	@Around("execution(** com.netbug_nb.test.scheduling.TestScheduling.schedule(..))")
	public void run(ProceedingJoinPoint pjp) {
		try {
			logger.debug("计划任务执行前");
			TestScheduling workItemProcessor = (TestScheduling) pjp.getTarget();
			workItemProcessor.schedule();
			logger.debug("计划任务执行后");
		} catch (Throwable t) {// 捕获全局异常
			logger.error("扫描处理服务工作任务出错：" + t.getMessage(), t);
		}
	}
}
