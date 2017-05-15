package com.linyp.weixin.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import com.linyp.weixin.constant.WeixinConstant;
import com.linyp.weixin.controller.param.VerifyParam;
import com.linyp.weixin.util.MessageUtil;
import com.linyp.weixin.util.QRCodeUtil;
import com.linyp.weixin.util.WeixinUtil;
import com.linyp.weixin.util.XmlConvertUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by linyp on 2017/4/26.
 */
@Controller
@RequestMapping("/wx")
public class WXController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private WeixinUtil weixinUtil;

    @RequestMapping(method = RequestMethod.GET)
    public void verify(VerifyParam param, HttpServletResponse response) throws IOException {
        PrintWriter printWriter = response.getWriter();
        if (weixinUtil.verifyToken(param.getTimestamp(), param.getNonce(), param.getSignature())) {
            printWriter.write(param.getEchostr());
        }
        IOUtils.closeQuietly(printWriter);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void textMessage(HttpServletResponse response) throws IOException, DocumentException {
        PrintWriter printWriter = response.getWriter();
        Map<String, String> map = XmlConvertUtil.xmlToMap(request);
        System.out.println(map);

        String content = map.get("Content");
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String msgType = map.get("MsgType");

        String message = StringUtils.EMPTY;
        if (WeixinConstant.MESSAGE_TEXT.equals(msgType)) {
            // 关键词“1”的回复
            if ("1".equals(content)) {
                message = MessageUtil.textMessage(fromUserName, toUserName, MessageUtil.keyWorkOneMessage());
            }
            // 关键词“2”的回复
            else if ("2".equals(content)) {
                message = MessageUtil.textMessage(fromUserName, toUserName, MessageUtil.keyWorkTwoMessage());
            }
            // 关键词“?”或“？”的回复
            else if (StringUtils.equalsAny(content, "?", "？")) {
                message = MessageUtil.textMessage(fromUserName, toUserName, MessageUtil.keyWorkMessage());
            }
            // 关键词“图文”的回复
            else if ("图文".equals(content)) {
                message = MessageUtil.newsMessage(fromUserName, toUserName);
            }
            // 关键词“图片”的回复
            else if ("图片".equals(content)) {
                message = MessageUtil.imageMessage(fromUserName, toUserName);
            }
            // 关键词“音乐”的回复
            else if ("音乐".equals(content)) {
                message = MessageUtil.musicMessage(fromUserName, toUserName);
            }
            // 默认回复
            else {
                message = MessageUtil.textMessage(fromUserName, toUserName, "您发送的消息是：" + content);
            }
        } else if (WeixinConstant.MESSAGE_EVENT.equals(msgType)) {
            String event = map.get("Event");
            String eventKey = map.get("EventKey");
            // 关注事件
            if (WeixinConstant.EVENT_SUBSCRIBE.equals(event)) {
                message = MessageUtil.textMessage(fromUserName, toUserName, MessageUtil.keyWorkMessage());
            }
            // CLICK事件
            else if (WeixinConstant.EVENT_CLICK.equals(event)) {
                if ("click_11".equals(eventKey) || "click_31".equals(eventKey)) {
                    message = MessageUtil.textMessage(fromUserName, toUserName, "CLICK按钮事件，EventKey=" + eventKey);
                }
            }
            // VIEW事件
            else if (WeixinConstant.EVENT_VIEW.equals(event)) {
                message = MessageUtil.textMessage(fromUserName, toUserName, "VIEW按钮事件，EventKey=" + eventKey);
            }
            // 扫码等待事件
            else if (WeixinConstant.EVENT_SCANCODE_WAITMSG.equals(event)) {
                message = MessageUtil.textMessage(fromUserName, toUserName, "扫码等待事件，EventKey=" + eventKey);
            }
            // 弹出相机或相册发图事件
            else if (WeixinConstant.EVENT_PIC_PHOTO_OR_ALBUM.equals(event)) {
                message = MessageUtil.textMessage(fromUserName, toUserName, "弹出相机或相册发图事件，EventKey=" + eventKey);
            }
            // 弹出地理位置选择器事件
            else if (WeixinConstant.EVENT_LOCATION_SELECT.equals(event)) {
                message = MessageUtil.textMessage(fromUserName, toUserName, "弹出地理位置选择器事件，EventKey=" + eventKey);
            }
            // 其他事件
            else {
                message = MessageUtil.textMessage(fromUserName, toUserName, "该事件类型暂时不支持");
            }
        } else if (WeixinConstant.MESSAGE_VOICE.equals(msgType)) {
            message = MessageUtil.textMessage(fromUserName, toUserName, "暂不支持语音消息");
        } else if (WeixinConstant.MESSAGE_IMAGE.equals(msgType)) {
            message = MessageUtil.textMessage(fromUserName, toUserName, "暂不支持图片消息");
        }
        System.out.println(message);
        printWriter.write(message);
        IOUtils.closeQuietly(printWriter);
    }

    @RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
    @ResponseBody
    public Object getAccessToken() {
        return weixinUtil.getAccessToken();
    }

    @RequestMapping(value = "/getMenu", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object getMenu() {
        return weixinUtil.getMenu();
    }

    @RequestMapping(value = "/deleteMenu", method = RequestMethod.GET)
    @ResponseBody
    public Object deleteMenu() {
        return weixinUtil.deleteMenu();
    }

    @RequestMapping(value = "/initMenu", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Object initMenu() {
        return weixinUtil.createMenu();
    }

    @RequestMapping(value = "/setIndustry", method = RequestMethod.GET)
    @ResponseBody
    public Object setIndustry() {
        return weixinUtil.setIndustry();
    }

    @RequestMapping(value = "/getIndustry", method = RequestMethod.GET)
    @ResponseBody
    public Object getIndustry() {
        return weixinUtil.getIndustry();
    }

    @RequestMapping(value = "/getAllTemplate", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllTemplate() {
        return weixinUtil.getAllTemplate();
    }

    @RequestMapping(value = "/sendTemplateMsg", method = RequestMethod.GET)
    @ResponseBody
    public Object sendTemplateMsg() {
        return weixinUtil.sendTemplateMsg();
    }

    @RequestMapping(value = "/avoidLogin", method = RequestMethod.GET)
    public String avoidLogin() {
        String code = request.getParameter("code");
        String state = request.getParameter("state");

        // 通过code获取网页授权的ACCESS_TOKEN
        JSONObject jsonObject = weixinUtil.getWebAccessToken(code);
        String accessToken = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");
        String scope = jsonObject.getString("scope");
        // 拉取微信用户信息
        if (WeixinConstant.SNSAPI_USERINFO.equals(scope)) {
            JSONObject userInfoJson = weixinUtil.getWeixinUserInfo(accessToken, openid);
            System.out.println(userInfoJson);
        }
        return "redirect:" + state;
        // return "forward:" + state;
        // response.sendRedirect(state);    // 转发另一种方式
        // request.getRequestDispatcher(targetUrl).forward(request, response);      // 重定向另一种方式
    }

    @RequestMapping(value = "/business", method = RequestMethod.GET)
    public String business() {
        return "list";
    }

    @RequestMapping(value = "/qrCode", method = RequestMethod.GET)
    public void qrCode(HttpServletResponse response) throws IOException, WriterException {
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        // 通过code获取网页授权的ACCESS_TOKEN
        JSONObject jsonObject = weixinUtil.getWebAccessToken(code);
        String accessToken = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");
        String scope = jsonObject.getString("scope");
        // 拉取微信用户信息
        if (WeixinConstant.SNSAPI_USERINFO.equals(scope)) {
            JSONObject userInfoJson = weixinUtil.getWeixinUserInfo(accessToken, openid);
            System.out.println(userInfoJson);
        }

        String url = WeixinConstant.CALLBACK_SERVER + "/wx/showQrCodeContent?" + state;
        // 生成二维码图片
        BufferedImage bufferedImage = QRCodeUtil.generateQrcode(url);
        OutputStream outputStream = response.getOutputStream();
        if (ImageIO.write(bufferedImage, "jpg", outputStream)) {
            IOUtils.closeQuietly(outputStream);
        }
    }

    @RequestMapping(value = "/webQrCode", method = RequestMethod.GET)
    public void webQrCode(HttpServletResponse response) throws IOException, WriterException {
        String url = WeixinConstant.CALLBACK_SERVER + "/wx/showQrCodeContent?departmentId=12345";
        // 生成二维码图片
        BufferedImage bufferedImage = QRCodeUtil.generateQrcode(url);
        OutputStream outputStream = response.getOutputStream();
        if (ImageIO.write(bufferedImage, "jpg", outputStream)) {
            IOUtils.closeQuietly(outputStream);
        }
    }

    @RequestMapping(value = "/showQrCodeContent", method = RequestMethod.GET)
    public void showQrCodeContent(HttpServletResponse response) throws IOException {
        String departmentId = request.getParameter("departmentId");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(departmentId);
        IOUtils.closeQuietly(printWriter);
    }

}
