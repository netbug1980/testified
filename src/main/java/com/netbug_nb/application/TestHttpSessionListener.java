package com.netbug_nb.application;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class TestHttpSessionListener implements HttpSessionListener {
	// private final static Logger logger =
	// LoggerFactory.getLogger(TestHttpSessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// logger.debug("sessionCreated:{}", se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// logger.debug("sessionDestroyed:{}", se.getSession().getId());

	}

}
