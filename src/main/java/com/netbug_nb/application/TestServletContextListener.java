package com.netbug_nb.application;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TestServletContextListener implements ServletContextListener {
	// private final static Logger logger =
	// LoggerFactory.getLogger(DemoServletContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// logger.debug("contextInitialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// logger.debug("contextDestroyed");
	}

}
