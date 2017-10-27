package com.linyp.study.crawler.chapter_3;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * Created by linyp on 2017/8/5.
 * 抓取压缩的网页
 */
public class ClientGzipContentCompression {

    public static void main(String[] args) throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            // 设置请求拦截器，添加头信息
            httpClient.addRequestInterceptor((request, context) -> {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding","gzip");
                }
            });

            // 设置响应拦截器
            httpClient.addResponseInterceptor((response, context) -> {
                HttpEntity entity = response.getEntity();
                Header ceheader = entity.getContentEncoding();
                if (ceheader != null) {
                    HeaderElement[] codecs = ceheader.getElements();
                    for (HeaderElement headerElement : codecs) {
                        if (headerElement.getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                        }
                    }
                }
            });

            HttpGet httpGet = new HttpGet("http://www.sohu.com");
            System.out.println("executing request" + httpGet.getURI());
            HttpResponse response = httpClient.execute(httpGet);

            System.out.println("=====================================");
            System.out.println(response.getStatusLine());
            System.out.println(response.getLastHeader("Content-Encoding"));
            System.out.println(response.getLastHeader("Content-Length"));
            System.out.println("=====================================");

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String content = EntityUtils.toString(entity, "gbk");
                System.out.println(content);
                System.out.println("=====================================");
                System.out.println("Uncompressed size:" + content.length());
            }
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

}

class GzipDecompressingEntity extends HttpEntityWrapper {

    /**
     * Creates a new entity wrapper.
     *
     * @param wrappedEntity
     */
    public GzipDecompressingEntity(HttpEntity wrappedEntity) {
        super(wrappedEntity);
    }

    @Override
    public InputStream getContent() throws IOException {
        // 包装实体的getContent()决定了重复性
        InputStream wrappedin = wrappedEntity.getContent();
        return new GZIPInputStream(wrappedin);
    }

    @Override
    public long getContentLength() {
        // 解压缩内容的长度未知
        return -1;
    }
}


