package com.linyp.weixin.controller.po.message;

/**
 * Created by linyp on 2017/4/28.
 */
public class ImageMessage extends BaseMessage {
    private Image Image;

    public com.linyp.weixin.controller.po.message.Image getImage() {
        return Image;
    }

    public void setImage(com.linyp.weixin.controller.po.message.Image image) {
        Image = image;
    }
}
