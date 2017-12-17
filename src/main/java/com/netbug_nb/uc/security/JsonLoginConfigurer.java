package com.netbug_nb.uc.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

public class JsonLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
		AbstractAuthenticationFilterConfigurer<H, JsonLoginConfigurer<H>, JsonUsernamePasswordAuthenticationFilter> {

	protected JsonLoginConfigurer() {
		super(new JsonUsernamePasswordAuthenticationFilter(), null);
	}

	@Override
	protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
		ContentNegotiationStrategy contentNegotiationStrategy = new HeaderContentNegotiationStrategy();
		MediaTypeRequestMatcher requestMatcherForJSON = new MediaTypeRequestMatcher(contentNegotiationStrategy,
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8);
		requestMatcherForJSON.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
		return new AndRequestMatcher(
				Arrays.asList(requestMatcherForJSON, new AntPathRequestMatcher(loginProcessingUrl, "POST")));
	}

	protected JsonUsernamePasswordAuthenticationFilter getFilter() {
		return super.getAuthenticationFilter();
	}
}
