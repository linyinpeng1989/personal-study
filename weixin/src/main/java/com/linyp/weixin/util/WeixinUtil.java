package com.linyp.weixin.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.linyp.weixin.constant.WeixinConstant;
import com.linyp.weixin.controller.po.button.Menu;
import com.linyp.weixin.controller.po.template.Template;
import com.linyp.weixin.util.httpclient.CommonHttpClient;
import com.linyp.weixin.util.httpclient.CommonHttpClientPoolManager;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by linyp on 2017/4/26.
 * 微信相关工具类
 */
public class WeixinUtil {
    @Resource
    private CommonHttpClientPoolManager commonHttpClientPoolManager;

    /**
     * 校验微信服务器配置的Token
     * @param timestamp     时间戳
     * @param nonce         随机数
     * @return
     */
    public boolean verifyToken(String timestamp, String nonce, String signature) {
        String[] arr = new String[]{WeixinConstant.TOKEN, timestamp, nonce};
        Arrays.sort(arr);

        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < arr.length; index++) {
            sb.append(arr[index]);
        }

        String temp = SecureUtil.getSha1(sb.toString());
        return temp.equals(signature);
    }

    /**
     * 获取AccessToken
     * @return
     */
    public String getAccessToken() {
        String url = WeixinConstant.ACCESS_TOKEN_URL.replace("{APPID}", WeixinConstant.APPID)
                .replace("{APPSECRET}", WeixinConstant.APPSECRET);
        CommonHttpClient commonHttpClient = commonHttpClientPoolManager.getCommonHttpClient();
        String result = commonHttpClient.doGet(url, null);
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject.getString("access_token");
    }

    /**
     * 获取网页授权AccessToken
     * @return
     */
    public JSONObject getWebAccessToken(String code) {
        String url = WeixinConstant.WEB_ACCESS_TOKEN_URL.replace("{APPID}", WeixinConstant.APPID)
                .replace("{SECRET}", WeixinConstant.APPSECRET)
                .replace("{CODE}", code);
        CommonHttpClient commonHttpClient = commonHttpClientPoolManager.getCommonHttpClient();
        String result = commonHttpClient.doGet(url, null);
        return JSON.parseObject(result);
    }

    /**
     * 获取网页授权AccessToken
     * @return
     */
    public JSONObject getWeixinUserInfo(String accessToken, String openId) {
        String url = WeixinConstant.USERINFO_URL.replace("{ACCESS_TOKEN}", accessToken)
                .replace("{OPENID}", openId);
        CommonHttpClient commonHttpClient = commonHttpClientPoolManager.getCommonHttpClient();
        String result = commonHttpClient.doGet(url, null);
        return JSON.parseObject(result);
    }

    /**
     * 创建自定义菜单
     * @return
     */
    public String createMenu() {
        String url = WeixinConstant.CREATE_MENU_URL.replace("{ACCESS_TOKEN}", WeixinConstant.ACCESS_TOKEN);
        Menu menu = MessageUtil.initMenu();
        String menuStr = JSON.toJSONString(menu);
        CommonHttpClient commonHttpClient = commonHttpClientPoolManager.getCommonHttpClient();
        return commonHttpClient.doPostStr(url, menuStr, "UTF-8");
    }

    /**
     * 获取自定义菜单
     * @return
     */
    public String getMenu() {
        String url = WeixinConstant.GET_MENU_URL.replace("{ACCESS_TOKEN}", WeixinConstant.ACCESS_TOKEN);
        CommonHttpClient commonHttpClient = commonHttpClientPoolManager.getCommonHttpClient();
        return commonHttpClient.doGet(url, null);
    }

    /**
     * 删除自定义菜单
     * @return
     */
    public String deleteMenu() {
        String url = WeixinConstant.DELETE_MENU_URL.replace("{ACCESS_TOKEN}", WeixinConstant.ACCESS_TOKEN);
        CommonHttpClient commonHttpClient = commonHttpClientPoolManager.getCommonHttpClient();
        return commonHttpClient.doGet(url, null);
    }

    /**
     * 设置所属行业
     * @return
     */
    public String setIndustry() {
        String url = WeixinConstant.SET_INDUSTRY_URL.replace("{ACCESS_TOKEN}", WeixinConstant.ACCESS_TOKEN);
        Map<String, Object> map = new HashMap<>();
        map.put("industry_id1", "10");
        map.put("industry_id2", "11");

        CommonHttpClient commonHttpClient = commonHttpClientPoolManager.getCommonHttpClient();
        return commonHttpClient.doPost(url, map);
    }

    /**
     * 获取所属行业
     * @return
     */
    public String getIndustry() {
        String url = WeixinConstant.GET_INDUSTRY_URL.replace("{ACCESS_TOKEN}", WeixinConstant.ACCESS_TOKEN);
        CommonHttpClient commonHttpClient = commonHttpClientPoolManager.getCommonHttpClient();
        return commonHttpClient.doGet(url, null);
    }

    /**
     * 获取所属行业
     * @return
     */
    public String getAllTemplate() {
        String url = WeixinConstant.GET_ALL_TEMPLATE_URL.replace("{ACCESS_TOKEN}", WeixinConstant.ACCESS_TOKEN);
        CommonHttpClient commonHttpClient = commonHttpClientPoolManager.getCommonHttpClient();
        return commonHttpClient.doGet(url, null);
    }

    /**
     * 获取所属行业
     * @return
     */
    public String sendTemplateMsg() {
        String url = WeixinConstant.SEND_TEMPLATE_MSG_URL.replace("{ACCESS_TOKEN}", WeixinConstant.ACCESS_TOKEN);
        Template template = MessageUtil.initTemplateMsg();
        CommonHttpClient commonHttpClient = commonHttpClientPoolManager.getCommonHttpClient();
        String tempStr = JSON.toJSONString(template);
        return commonHttpClient.doPostStr(url, tempStr, "UTF-8");
    }

    /**
     * 根据JSCODE获取微信小程序的openId和SessionKey
     * @param code
     * @return
     */
    public JSONObject getSessionKeyByCode(String code) {
        String url = WeixinConstant.WXXCX_SESSIONKEY_URL.replace("{APPID}", WeixinConstant.WXXCX_APPID)
                .replace("{SECRET}", WeixinConstant.WXXCX_APPSECRET)
                .replace("{JSCODE}", code);
        CommonHttpClient commonHttpClient = commonHttpClientPoolManager.getCommonHttpClient();
        String result = commonHttpClient.doGet(url, null);
        return JSON.parseObject(result);
    }

}
