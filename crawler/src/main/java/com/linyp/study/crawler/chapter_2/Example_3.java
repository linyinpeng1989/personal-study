package com.linyp.study.crawler.chapter_2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by linyp on 2017/7/30.
 */
public class Example_3 {
    public static void main(String[] args) throws IOException {
        String url = "http://china.cnr.cn/yaowen/";
        // 解析的结果就是一个文档对象
        Document doc = Jsoup.connect(url).timeout(1000).get();

        Element content = doc.getElementsByClass("articleList").first();
        Elements es = content.getElementsByTag("strong");
        for (Element link : es) {
            Element aLink = link.getElementsByTag("a").first();
            if (aLink != null) {
                System.out.println(aLink.attr("href"));
                System.out.println(aLink.text());
            }
        }
    }
}
