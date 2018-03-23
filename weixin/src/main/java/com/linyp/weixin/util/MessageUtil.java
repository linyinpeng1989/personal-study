package com.linyp.weixin.util;

import com.linyp.weixin.constant.WeixinConstant;
import com.linyp.weixin.controller.po.button.*;
import com.linyp.weixin.controller.po.message.*;
import com.linyp.weixin.controller.po.template.Content;
import com.linyp.weixin.controller.po.template.Data;
import com.linyp.weixin.controller.po.template.Template;

import java.util.*;

/**
 * Created by linyp on 2017/4/27.
 */
public class MessageUtil {
    /**
     * 文本消息转XML
     * @param fromUserName
     * @param toUserName
     * @param content
     * @return
     */
    public static String textMessage(String fromUserName, String toUserName, String content) {
        TextMessage textMessage = new TextMessage();
        textMessage.setContent(content);
        textMessage.setCreateTime(System.currentTimeMillis());
        textMessage.setFromUserName(toUserName);
        textMessage.setToUserName(fromUserName);
        textMessage.setMsgType(WeixinConstant.MESSAGE_TEXT);

        Map<String, Class> map = new HashMap<>();
        map.put("xml", TextMessage.class);
        return XmlConvertUtil.toXml(textMessage, map);
    }

    /**
     * 图文消息转XML
     * @param fromUserName  发送方
     * @param toUserName    接收方
     * @return
     */
    public static String newsMessage(String fromUserName, String toUserName) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setCreateTime(System.currentTimeMillis());
        newsMessage.setFromUserName(toUserName);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setMsgType(WeixinConstant.MESSAGE_NEWS);

        List<News> newsList = new ArrayList<>();
        News news = new News();
        news.setTitle("慕课网介绍");
        news.setDescription("慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。");

        String mediaUrl = WeixinConstant.MEDIA_GET_URL.replace("{ACCESS_TOKEN}", WeixinConstant.ACCESS_TOKEN)
                .replace("{MEDIA_ID}", WeixinConstant.MEDIA_ID);
        news.setPicUrl(mediaUrl);
        // 微信会屏蔽非腾讯系域名的图片
        //news.setPicUrl("http://mywx.tunnel.2bdata.com/images/test.jpg");
        news.setUrl("www.imooc.com");
        newsList.add(news);

        newsMessage.setArticleCount(1);
        newsMessage.setArticles(newsList);

        Map<String, Class> map = new HashMap<>();
        map.put("xml", NewsMessage.class);
        map.put("item", News.class);
        return XmlConvertUtil.toXml(newsMessage, map);
    }

    /**
     * 图片消息转XML
     * @param fromUserName  发送方
     * @param toUserName    接收方
     * @return
     */
    public static String imageMessage(String fromUserName, String toUserName) {
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setCreateTime(System.currentTimeMillis());
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(WeixinConstant.MESSAGE_IMAGE);

        Image image = new Image();
        image.setMediaId(WeixinConstant.MEDIA_ID);
        imageMessage.setImage(image);

        Map<String, Class> map = new HashMap<>();
        map.put("xml", ImageMessage.class);
        map.put("Image", Image.class);
        return XmlConvertUtil.toXml(imageMessage, map);
    }

    /**
     * 音乐消息转XML
     * @param fromUserName  发送方
     * @param toUserName    接收方
     * @return
     */
    public static String musicMessage(String fromUserName, String toUserName) {
        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setCreateTime(System.currentTimeMillis());
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setMsgType(WeixinConstant.MESSAGE_MUSIC);

        Music music = new Music();
        music.setTitle("执迷不悔");
        music.setDescription("《执迷不悔》是王菲（曾用艺名：王靖雯）演唱的歌曲，歌曲收录在第五张粤语专辑（其中包含一首国语）《执迷不悔》中，为该专辑第二首歌曲，粤语版为该专辑第十首歌曲，1993年发行。");
        music.setMusicUrl("http://mywx.tunnel.2bdata.com/resources/test.mp3");
        music.setHQMusicUrl("http://mywx.tunnel.2bdata.com/resources/test.mp3");
        music.setThumbMediaId("IJEHd6KP-Pc04-5WtDDRLnKEukpl5jr43zqFiyGnZIBBksN7fjNmpjMfQAAP3pKo");
        musicMessage.setMusic(music);

        Map<String, Class> map = new HashMap<>();
        map.put("xml", MusicMessage.class);
        map.put("Music", Music.class);
        return XmlConvertUtil.toXml(musicMessage, map);
    }

    /**
     * 创建自定义菜单
     * @return
     */
    public static Menu initMenu() {
        Menu menu = new Menu();

        ClickButton button1 = new ClickButton();
        button1.setName("click菜单");
        button1.setType(WeixinConstant.MENUTYPE_CLICK);
        button1.setKey("click_11");

        Button button2 = new ViewButton();
        button2.setName("组合菜单1");

        ViewButton button21 = new ViewButton();
        button21.setName("view菜单");
        button21.setType(WeixinConstant.MENUTYPE_VIEW);
        button21.setUrl("http://www.imooc.com");

        String url = WeixinConstant.AUTHORIZE_URL.replace("{APPID}", WeixinConstant.APPID)
                .replace("{REDIRECT_URI}", WeixinConstant.CALLBACK_SERVER + "/wx/avoidLogin")
                .replace("{SCOPE}", WeixinConstant.SNSAPI_USERINFO)
                .replace("{STATE}", "https://service.guahao.com/json/white/search/mediafocus.jsonp?pageNo=1&pageSize=5&label=1&category=5&type=130");
        ViewButton button22 = new ViewButton();
        button22.setName("测试view");
        button22.setType(WeixinConstant.MENUTYPE_VIEW);
        button22.setUrl(url);

        button2.setSub_button(Arrays.asList(button21, button22));

        Button button3 = new Button();
        button3.setName("组合菜单2");

        /*
            模拟微信免登陆并跳转页面，REDIRECT_URI为免登陆接口，STATE为后续跳转页面
            获取openId
         */
        String url1 = WeixinConstant.AUTHORIZE_URL.replace("{APPID}", WeixinConstant.APPID)
                .replace("{REDIRECT_URI}", WeixinConstant.CALLBACK_SERVER + "/wx/avoidLogin")
                .replace("{SCOPE}", WeixinConstant.SNSAPI_USERINFO)
                .replace("{STATE}", "/wx/business");
        ViewButton button31 = new ViewButton();
        button31.setName("view_去绑定");
        button31.setType(WeixinConstant.MENUTYPE_VIEW);
        button31.setUrl(url1);

        /*
            点击菜单，生成业务相关的二维码图片并输出，REDIRECT_URI为生成二维码接口，STATE为相关参数
            获取openId并获取unionId
         */
        String url2 = WeixinConstant.AUTHORIZE_URL.replace("{APPID}", WeixinConstant.APPID)
                .replace("{REDIRECT_URI}", WeixinConstant.CALLBACK_SERVER + "/wx/qrCode")
                .replace("{SCOPE}", WeixinConstant.SNSAPI_USERINFO)
                .replace("{STATE}", "departmentId=12345");
        ViewButton button32 = new ViewButton();
        button32.setName("view_二维码");
        button32.setType(WeixinConstant.MENUTYPE_VIEW);
        button32.setUrl(url2);

        ClickButton button33 = new ClickButton();
        button33.setName("扫码子菜单");
        button33.setType(WeixinConstant.MENUTYPE_SCANCODE_WAITMSG);
        button33.setKey("scancode_waitmsg_33");

        ClickButton button34 = new ClickButton();
        button34.setName("照片子菜单");
        button34.setType(WeixinConstant.MENUTYPE_PIC_PHOTO_OR_ALBUM);
        button34.setKey("click_34");

        //ClickButton button35 = new ClickButton();
        //button35.setName("地理位置");
        //button35.setType(WeixinConstant.MENUTYPE_LOCATION_SELECT);
        //button35.setKey("click_35");

        MiniProgramButton button35 = new MiniProgramButton();
        button35.setName("mini");
        button35.setType(WeixinConstant.MENUTYPE_MINI_PROGRAM);
        button35.setAppid(WeixinConstant.WXXCX_APPID);
        button35.setPagepath("pages/index/index");
        button35.setUrl("https://www.baidu.com");

        button3.setSub_button(Arrays.asList(button31, button32, button33, button34, button35));
        menu.setButton(Arrays.asList(button1, button2, button3));
        return menu;
    }

    public static Template initTemplateMsg() {
        Template template = new Template();
        template.setTouser("ororVvvCx6_sZMWRdHRkv4V7lVfo");
        template.setTemplate_id(WeixinConstant.TEMPLATE_ID);
        template.setUrl("http://www.imooc.com");

        Content first = new Content();
        first.setValue("您好,您的诉求编号为：12345");
        first.setColor("#173177");

        Content keyWord1 = new Content();
        keyWord1.setValue("市财政局");
        keyWord1.setColor("#173177");

        Content keyWord2 = new Content();
        keyWord2.setValue("2015-01-15 10:47:35");
        keyWord2.setColor("#173177");

        Content keyWord3 = new Content();
        keyWord3.setValue("经我局核实，关于南郊公园领购票据事宜回复如下");
        keyWord3.setColor("#173177");

        Content remark = new Content();
        remark.setValue("感谢您对热线的支持");
        remark.setColor("#173177");

        Data data = new Data();
        data.setFirst(first);
        data.setKeyword1(keyWord1);
        data.setKeyword2(keyWord2);
        data.setKeyword3(keyWord3);
        data.setRemark(remark);
        template.setData(data);

        return template;
    }

    public static String keyWorkMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("欢迎您的关注，请按照以下说明进行操作\n\n")
                .append("1、课程介绍\n")
                .append("2、慕课网介绍\n\n")
                .append("回复“？”显示此帮助菜单");
        return sb.toString();
    }

    public static String keyWorkOneMessage() {
        return "本套课程主要是介绍微信公众号开发相关的知识，主要内容涉及微信公众号介绍、编辑模式、开发模式以及自定义菜单的创建等。";
    }

    public static String keyWorkTwoMessage() {
        return "慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。慕课网课程涵盖前端开发、PHP、Html5、Android、iOS、Swift等IT前沿技术语言，包括基础课程、实用案例、高级分享三大类型，适合不同阶段的学习人群。以纯干货、短视频的形式为平台特点，为在校学生、职场白领提供了一个迅速提升技能、共同分享进步的学习平台。";
    }
}
