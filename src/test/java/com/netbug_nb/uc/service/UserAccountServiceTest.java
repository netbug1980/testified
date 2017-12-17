package com.netbug_nb.uc.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.netbug_nb.application.ApplicationTest;
import com.netbug_nb.uc.service.UserAccountService;

public class UserAccountServiceTest extends ApplicationTest {
	private final static Logger logger = LoggerFactory.getLogger(UserAccountServiceTest.class);

	@Autowired
	private UserAccountService userService;

	@Test
	public void reporTodayTest() {
		userService.findUserAccountFromCache(USER_NAME);
		logger.debug("success");
	}
}
