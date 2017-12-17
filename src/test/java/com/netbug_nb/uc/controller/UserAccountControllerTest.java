package com.netbug_nb.uc.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netbug_nb.application.ApplicationTest;
import com.netbug_nb.uc.domain.UserAccount;
import com.netbug_nb.uc.security.JsonAuthenticationHandler.HandlerResponseBody;
import com.netbug_nb.uc.security.JsonUsernamePasswordAuthenticationFilter.BasicRequest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAccountControllerTest extends ApplicationTest {
	private static String TEST_ID = null;
	private static String TEST_SESSION = null;
	private static final String TEST_USERNAME = "test";
	private static final String TEST_PASSWORD = "123456";
	private final static Logger logger = LoggerFactory.getLogger(UserAccountControllerTest.class);

	@Test
	public void t01_register() {
		UserAccount request = new UserAccount();
		request.setUsername(TEST_USERNAME);
		request.setPassword(TEST_PASSWORD);
		ResponseEntity<UserAccount> response = template.postForEntity("/api/uc/useraccount/register", request,
				UserAccount.class);
		TEST_ID = response.getBody().getId();
		assertNotNull(TEST_ID);
		logger.info("注册成功:{}-{}", TEST_USERNAME, TEST_ID);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void t02_login() throws JsonProcessingException {
		BasicRequest request = new BasicRequest();
		request.setUsername(TEST_USERNAME);
		request.setPassword(TEST_PASSWORD);
		ResponseEntity<HandlerResponseBody> response = template.postForEntity("/login", request,
				HandlerResponseBody.class);
		logger.info(objectMapper.writeValueAsString(response.getBody()));
		LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>) response.getBody().getData();
		LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) data.get("details");
		TEST_SESSION = (String) details.get("sessionId");
		assertNotNull(TEST_SESSION);
		logger.info("登陆成功:{}-{}", TEST_USERNAME, TEST_SESSION);
	}

	@Test
	public void t03_delete() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.COOKIE, "SESSION=" + TEST_SESSION);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
		ResponseEntity<Object> response = template.exchange("/api/uc/useraccount/" + TEST_ID, HttpMethod.DELETE,
				requestEntity, Object.class);
		assertTrue(response.getStatusCode().is2xxSuccessful());
		logger.info("删除成功:{}", TEST_USERNAME);

	}

	@Test
	public void t04_logout() throws JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.COOKIE, "SESSION=" + TEST_SESSION);
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
		ResponseEntity<Object> response = template.exchange("/logout", HttpMethod.GET, requestEntity, Object.class);
		logger.info("退出成功:{}", objectMapper.writeValueAsString(response.getBody()));
	}
}
