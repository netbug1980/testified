package com.netbug_nb.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netbug_nb.application.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT, properties = {
		"server.port:8080" })
public class ApplicationTest {
	private final static Logger logger = LoggerFactory.getLogger(ApplicationTest.class);
	public final static String USER_NAME = "netbug_nb";
	public final static String PASSWORD = "123456";

	@Autowired
	protected TestRestTemplate template;

	protected final static ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();

	@LocalServerPort
	protected int port;

	@Test
	public void test() {
		logger.debug("空测试");
	}
}
