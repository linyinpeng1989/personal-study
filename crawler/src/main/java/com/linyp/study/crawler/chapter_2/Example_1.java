package com.linyp.study.crawler.chapter_2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by linyp on 2017/7/30.
 */
public class Example_1 {
    public static void main(String[] args) throws IOException {
        String url = "http://www.lietuw.com/";
        // 解析的结果就是一个文档对象
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        for (Element link : links) {
            String linkHref = link.attr("href");
            System.out.println(linkHref);
        }
    }
}
