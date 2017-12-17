package com.netbug_nb.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netbug_nb.test.TestInterface;

@Component
public class TestInterfaceImpl implements TestInterface {
	private final static Logger logger = LoggerFactory.getLogger(TestInterfaceImpl.class);

	@Override
	public void sayHello() {
		logger.debug("I`m {}", TestInterfaceImpl.class.getSimpleName());
	}

}
