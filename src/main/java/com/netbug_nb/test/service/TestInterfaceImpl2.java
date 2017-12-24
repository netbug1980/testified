package com.netbug_nb.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.netbug_nb.test.TestInterface;

@Component
public class TestInterfaceImpl2 implements TestInterface {
	private final static Logger logger = LoggerFactory.getLogger(TestInterfaceImpl2.class);

	@Override
	@Async
	public void sayHello() {
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("I`m {}", TestInterfaceImpl2.class.getSimpleName());
	}

}
