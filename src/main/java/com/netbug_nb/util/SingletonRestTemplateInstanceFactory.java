package com.netbug_nb.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class SingletonRestTemplateInstanceFactory {
	private static volatile RestTemplate instance;

	private SingletonRestTemplateInstanceFactory() {

	}

	public final static RestTemplate getInstance() {
		if (instance == null) {
			synchronized (SingletonRestTemplateInstanceFactory.class) {
				if (instance == null) {
					// 初始化对象开始
					CloseableHttpClient httpClient = SingletonCloseableHttpClientInstanceFactory.getInstance();
					HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
							httpClient);
					instance = new RestTemplate(requestFactory);// 利用httpclient提供的连接池
					// 初始化对象结束
				}
			}
		}
		return instance;
	}
}
