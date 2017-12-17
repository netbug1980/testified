package com.netbug_nb.uc.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.web.accept.ContentNegotiationStrategy;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private RedisOperationsSessionRepository redisOperationsSessionRepository;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setHideUserNotFoundExceptions(false);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		authenticationProvider.setUserDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		JsonAuthenticationHandler jsonAuthenticationHandler = new JsonAuthenticationHandler();
		MediaTypeRequestMatcher requestMatcherForJSON = new MediaTypeRequestMatcher(
				http.getSharedObject(ContentNegotiationStrategy.class), MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_JSON_UTF8);
		requestMatcherForJSON.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));

		http.csrf().disable();
		http.servletApi().disable();
		// http.anonymous().disable();
		http.formLogin();

		/**
		 * json请求：无登陆或者无权限时
		 */
		http.exceptionHandling().defaultAuthenticationEntryPointFor(jsonAuthenticationHandler, requestMatcherForJSON);
		/**
		 * 退出时
		 */
		SimpleUrlLogoutSuccessHandler urlLogoutHandler = new SimpleUrlLogoutSuccessHandler();
		urlLogoutHandler.setDefaultTargetUrl("/login?logout");
		http.logout().defaultLogoutSuccessHandlerFor(urlLogoutHandler, new NegatedRequestMatcher(requestMatcherForJSON))
				.defaultLogoutSuccessHandlerFor(jsonAuthenticationHandler, requestMatcherForJSON);
		/**
		 * json请求：登陆时
		 */
		JsonLoginConfigurer<HttpSecurity> jsonLoginConfigurer = new JsonLoginConfigurer<HttpSecurity>();
		jsonLoginConfigurer.failureHandler(jsonAuthenticationHandler).successHandler(jsonAuthenticationHandler);
		http.addFilterBefore(jsonLoginConfigurer.getFilter(), UsernamePasswordAuthenticationFilter.class);
		http.apply(jsonLoginConfigurer);

		/**
		 * remember-me token基于redis缓存 TODO 自动set Cookie的过期时间
		 */
		SpringSessionRememberMeServices springSessionRemembermeServices = new SpringSessionRememberMeServices();
		springSessionRemembermeServices.setValiditySeconds(10 * 60);// 默认30天
		http.rememberMe().rememberMeServices(springSessionRemembermeServices);

		/**
		 * 相同用户只能登陆一次
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		SessionRegistry sessionRegistry = new SpringSessionBackedSessionRegistry(
				(FindByIndexNameSessionRepository) redisOperationsSessionRepository);
		http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false).sessionRegistry(sessionRegistry)
				.expiredSessionStrategy(jsonAuthenticationHandler);

		http.authorizeRequests().antMatchers("/api/uc/useraccount/register").anonymous().antMatchers("/api/**")
				.authenticated().anyRequest().permitAll();
	}
}
