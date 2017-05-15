package com.linyp.weixin.constant;

/**
 * Created by linyp on 2017/4/28.
 */
public interface WeixinConstant {
    /** 微信开发模式服务器配置中填写的Token */
    String TOKEN = "zxc123asd456qwe789";
    /** 微信公众号APPID */
    String APPID = "wx33c96e8327e5907f";
    /** 微信公众号APPSECRET */
    String APPSECRET = "5b66ff36c3963a7c6033893c6bb93736";

    /** 获取微信ACCESS_TOKEN接口地址 */
    String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}";
    /** 上传临时素材接口地址 */
    String MEDIA_UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token={ACCESS_TOKEN}&type={TYPE}";
    /** 获取临时素材接口地址 */
    String MEDIA_GET_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token={ACCESS_TOKEN}&media_id={MEDIA_ID}";
    /** 创建自定义菜单接口地址 */
    String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token={ACCESS_TOKEN}";
    /** 获取自定义菜单接口地址 */
    String GET_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token={ACCESS_TOKEN}";
    /** 删除自定义菜单接口地址 */
    String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token={ACCESS_TOKEN}";
    /** 设置所属行业接口地址 */
    String SET_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token={ACCESS_TOKEN}";
    /** 获取所属行业接口地址 */
    String GET_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token={ACCESS_TOKEN}";
    /** 获取模板列表接口地址 */
    String GET_ALL_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token={ACCESS_TOKEN}";
    /** 发送模板消息接口地址 */
    String SEND_TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={ACCESS_TOKEN}";

    /** 消息类型 */
    String MESSAGE_TEXT = "text";
    String MESSAGE_IMAGE = "image";
    String MESSAGE_VOICE = "voice";
    String MESSAGE_VIDEO = "video";
    String MESSAGE_NEWS = "news";
    String MESSAGE_MUSIC = "music";
    String MESSAGE_SHORTVIDEO = "shortvideo";
    String MESSAGE_LOCATION = "location";
    String MESSAGE_EVENT = "event";

    /** 事件类型 */
    String EVENT_SUBSCRIBE = "subscribe";
    String EVENT_UNSUBSCRIBE = "unsubscribe";
    String EVENT_CLICK = "CLICK";
    String EVENT_VIEW = "VIEW";
    String EVENT_SCANCODE_WAITMSG = "scancode_waitmsg";
    String EVENT_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
    String EVENT_LOCATION_SELECT = "location_select";

    /** 按钮类型 */
    String MENUTYPE_CLICK = "click";
    String MENUTYPE_VIEW = "view";
    String MENUTYPE_SCANCODE_PUSH = "scancode_push";
    String MENUTYPE_SCANCODE_WAITMSG = "scancode_waitmsg";
    String MENUTYPE_PIC_SYSPHOTO = "pic_sysphoto";
    String MENUTYPE_PIC_PHOTO_OR_ALBUM = "pic_photo_or_album";
    String MENUTYPE_PIC_WEIXIN = "pic_weixin";
    String MENUTYPE_LOCATION_SELECT = "location_select";
    String MENUTYPE_MEDIA_ID = "media_id";
    String MENUTYPE_VIEW_LIMITED = "view_limited";

    String ACCESS_TOKEN = "E4AiKSjUnyb-jSTZ-4Ha45x5ABgsab9Nl7LkF42N1kDt0bFxJvzbDy3skExcs7O_VEq7c2PnZ520yB0aA8K79UsAA0fEnwfw0s5Oe-7NMOQyA7Rs1ycFPN3T2OWmWrHzUSOiAFADBX";
    String MEDIA_ID = "C4KMB-5X5YwudJ7p1WfqCJdwFTW7BWyJJltM-UodLXvDzhkNqBlL1_iVvZwM7IiC";
    String TEMPLATE_ID = "EHv9m4ZOoM4GS645ZResbs3gUBhwrIvmS4uWoFigjgs";

    /** 回调域名 */
    String CALLBACK_SERVER = "http://mywx.tunnel.2bdata.com";
    String SNSAPI_BASE = "snsapi_base";
    String SNSAPI_USERINFO = "snsapi_userinfo";
    String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={APPID}&redirect_uri={REDIRECT_URI}&response_type=code&scope={SCOPE}&state={STATE}#wechat_redirect";
    String WEB_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={APPID}&secret={SECRET}&code={CODE}&grant_type=authorization_code";
    String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token={ACCESS_TOKEN}&openid={OPENID}&lang=zh_CN";
}
