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
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT, properties = {
		"server.port:8080" })
public class ApplicationTest {
	private final static Logger logger = LoggerFactory.getLogger(ApplicationTest.class);
	public final static String USER_ID = "800f60dc-996c-4fe9-bc2c-68507397cac7";
	public final static String USER_NAME = "netbug_nb";
	public final static String PASSWORD = "123456";

	@Autowired
	protected TestRestTemplate template;

	@LocalServerPort
	protected int port;

	@Test
	public void test() {
		logger.debug("空测试");
	}
}
