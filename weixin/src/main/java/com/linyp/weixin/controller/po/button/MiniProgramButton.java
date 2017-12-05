package com.linyp.weixin.controller.po.button;

/**
 * @author Yinpeng Lin
 * @date 2017/12/5
 * @desc
 */
public class MiniProgramButton extends Button {
    /**
     * 旧版微信客户端无法支持小程序，用户点击菜单时将会打开备用网页
     */
    private String url;
    /**
     * 小程序appId
     */
    private String appid;
    /**
     * 小程序页面路径
     */
    private String pagepath;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }
}
