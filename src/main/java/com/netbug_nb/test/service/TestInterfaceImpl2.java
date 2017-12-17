package com.netbug_nb.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netbug_nb.test.TestInterface;

@Component
public class TestInterfaceImpl2 implements TestInterface {
	private final static Logger logger = LoggerFactory.getLogger(TestInterfaceImpl2.class);

	@Override
	public void sayHello() {
		logger.debug("I`m {}", TestInterfaceImpl2.class.getSimpleName());
	}

}
