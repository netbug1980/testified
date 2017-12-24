package com.netbug_nb.application;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSessionListener;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.netbug_nb.test.TestAutoConfiguration;
import com.netbug_nb.uc.UCAutoConfiguration;
import com.netbug_nb.util.UtilAutoConfiguration;

//@EnableAutoConfiguration(exclude = {
// DataSourceAutoConfiguration.class //排除暂时不需要的configuration
//})
@SpringBootApplication()
@EnableCaching
@ServletComponentScan // 启动注解式@WebFilter、@WebListener开关
@ImportAutoConfiguration({ //
		UCAutoConfiguration.class, //
		TestAutoConfiguration.class, //
		UtilAutoConfiguration.class //
})
public class Application extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);
		application.run(args);
	}

	@Autowired
	private SpringHttpSessionConfiguration springHttpSessionConfiguration;

	@Autowired
	private TestHandlerInterceptor interceptor;

	/* session监听 */
	@PostConstruct
	public void init() {
		List<HttpSessionListener> listeners = new ArrayList<>();
		listeners.add(new TestHttpSessionListener());
		springHttpSessionConfiguration.setHttpSessionListeners(listeners);
	}

	/* 静态资源使用默认的servlet而不是dispatcherServlet */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/* 配置内容协商默认的内容类型 */
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.defaultContentType(MediaType.APPLICATION_JSON);
	}

	/* 拦截器 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(interceptor);
	}

	/* 过滤器 */
	@Bean
	public FilterRegistrationBean remoteIpFilterRegistrationBean() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.addUrlPatterns("/*");
		bean.setName("remoteIpFilter");
		bean.setFilter(new RemoteIpFilter());
		bean.setOrder(-100);
		return bean;
	}

	@Bean
	public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
		cacheManager.setDefaultExpiration(600);
		return cacheManager;
	}

}
