package com.linyp.weixin.util.httpclient;

import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Created by linyp on 2017/1/2.
 * http连接池
 */
public class CommonHttpClientPoolManager {
    private final CommonHttpClientBuilder builder = CommonHttpClientBuilder.custom()
            .timeout(30000).pool(200, 20);

    // 创建一个新的连接
    public CommonHttpClient getCommonHttpClient() {
        CloseableHttpClient httpClient = builder.build();
        return new CommonHttpClient(httpClient);
    }
}
