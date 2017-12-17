package com.netbug_nb.util;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingletonCloseableHttpClientInstanceFactory {
	private static final Logger logger = LoggerFactory.getLogger(SingletonCloseableHttpClientInstanceFactory.class);
	private static volatile CloseableHttpClient instance;

	private SingletonCloseableHttpClientInstanceFactory() {

	}

	private static PoolingHttpClientConnectionManager connManager = null;
	private static RequestConfig defaultRequestConfig = null;

	public final static CloseableHttpClient getInstance() {
		if (instance == null) {
			synchronized (SingletonCloseableHttpClientInstanceFactory.class) {
				if (instance == null) {
					connManager = new PoolingHttpClientConnectionManager();

					defaultRequestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000)
							.setConnectionRequestTimeout(10000).build();
					HttpClientBuilder builder = HttpClients.custom();

					if (logger.isDebugEnabled()) {
						HttpRequestInterceptor itcp = new HttpRequestInterceptor() {

							@Override
							public void process(HttpRequest request, HttpContext context)
									throws HttpException, IOException {
								PoolStats poolStats = connManager.getTotalStats();
								logger.debug("ConnectionPool Total Status:" + poolStats.toString());
							}
						};
						builder.addInterceptorLast(itcp);
					}

					instance = builder.setConnectionManager(connManager).setDefaultRequestConfig(defaultRequestConfig)
							.build();
				}
			}
		}
		return instance;
	}

	public static PoolingHttpClientConnectionManager getConnectionManager() {
		return connManager;
	}
}
