package com.linyp.study.crawler.chapter_2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by linyp on 2017/7/30.
 */
public class Example_2 {
    public static void main(String[] args) throws IOException {
        String url = "http://politics.people.com.cn/GB/1024/";
        // 解析的结果就是一个文档对象
        Document doc = Jsoup.connect(url).timeout(1000).get();
        System.out.println(doc.html());

        Elements es = doc.getElementsByClass("list_16");
        Elements links = es.select("a[href]");
        for (Element link : links) {
            String title = link.text();
            System.out.println(title);
            String linkHref = link.attr("href");
            System.out.println(linkHref);
        }
    }
}
