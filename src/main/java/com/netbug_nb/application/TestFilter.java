package com.netbug_nb.application;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * 继承 GenericFilterBean
 * 
 * @author leisen
 *
 */
@WebFilter(filterName = "testFilter", urlPatterns = "/*")
public class TestFilter implements Filter {
	// private final static Logger logger =
	// LoggerFactory.getLogger(TestFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// logger.debug("init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// logger.debug("doFilter");
		chain.doFilter(request, response);
		// logger.debug("doFilter");
	}

	@Override
	public void destroy() {
		// logger.debug("destroy");
	}

}
