package com.netbug_nb.application;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TestApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {
	// private final static Logger logger =
	// LoggerFactory.getLogger(TestApplicationReadyListener.class);

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		// logger.debug("Application Ready");
	}

}
