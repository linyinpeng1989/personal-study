/**
 * 
 */
package com.linyp.weixin.util.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * @author LongTao
 *
 */
public class CommonHttpClientBuilder extends HttpClientBuilder {
	
	private CommonHttpClientBuilder() {
		
	}
	
	public static CommonHttpClientBuilder custom() {
		return new CommonHttpClientBuilder();
	}

	public CommonHttpClientBuilder timeout(int timeout) {
		RequestConfig config = RequestConfig.custom()
				.setConnectionRequestTimeout(timeout)
				.setConnectTimeout(timeout)
				.setSocketTimeout(timeout)
				.build();
		return (CommonHttpClientBuilder) this.setDefaultRequestConfig(config);
	}
	
	public CommonHttpClientBuilder pool(int maxTotal, int defaultMaxPerRoute) {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// Increase max total connection to 200
		cm.setMaxTotal(maxTotal);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
		return (CommonHttpClientBuilder) this.setConnectionManager(cm);
	}
	
	
}
