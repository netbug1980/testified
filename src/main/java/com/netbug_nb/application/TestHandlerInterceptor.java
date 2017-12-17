package com.netbug_nb.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TestHandlerInterceptor implements HandlerInterceptor {
	// private final static Logger logger =
	// LoggerFactory.getLogger(DemoHandlerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// logger.debug("preHandle {}:{}{} at {}", request.getRemoteAddr(),
		// request.getRemotePort(),
		// request.getRequestURI(), new Date());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// logger.debug("postHandle {}:{}{} at {}", request.getRemoteAddr(),
		// request.getRemotePort(),
		// request.getRequestURI(), new Date());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// logger.debug("afterCompletion {}:{}{} at {}", request.getRemoteAddr(),
		// request.getRemotePort(),
		// request.getRequestURI(), new Date());
	}

}
