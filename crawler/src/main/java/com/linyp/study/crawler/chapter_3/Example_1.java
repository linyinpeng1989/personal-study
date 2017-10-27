package com.linyp.study.crawler.chapter_3;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by linyp on 2017/8/5.
 * 下载网页（使用URL建模并下载，效果与Jsoup类似）
 */
public class Example_1 {

    /**
     * 下载网页内容(无格式)
     * @param path
     * @return
     * @throws IOException
     */
    public static String downloadPage(String path) throws IOException {
        URL pageUrl = new URL(path);
        // 构建网络流
        InputStream inputStream = pageUrl.openStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        // 读取网页内容
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        String path = "http://www.lietuw.com";
        System.out.println(downloadPage(path));

        Document document = Jsoup.connect(path).get();
        System.out.println(document.html());
    }
}
