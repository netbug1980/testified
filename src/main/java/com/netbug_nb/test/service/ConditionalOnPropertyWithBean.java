package com.netbug_nb.test.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@ConditionalOnProperty(prefix = "netbug_nb.test", name = "conditionalOnPropertyWithBean", havingValue = "true", matchIfMissing = false)
@Component
public class ConditionalOnPropertyWithBean {
	private final static Logger logger = LoggerFactory.getLogger(ConditionalOnPropertyWithBean.class);

	public ConditionalOnPropertyWithBean() {
		logger.debug("---------------------------------------------------------");
		logger.debug("---------------------------------------------------------");
		logger.debug("---------------------------------------------------------");
		logger.debug("---------------------------------------------------------");
		logger.debug("---------------------------------------------------------");
	}
}
