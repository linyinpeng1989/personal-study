package com.linyp.weixin.controller.po.message;

import java.util.List;

/**
 * Created by linyp on 2017/4/27.
 */
public class NewsMessage extends BaseMessage {
    private Integer ArticleCount;
    private List<News> Articles;

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public List<News> getArticles() {
        return Articles;
    }

    public void setArticles(List<News> articles) {
        Articles = articles;
    }
}
