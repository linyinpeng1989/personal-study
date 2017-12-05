package com.linyp.weixin.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;

/**
 * @author Yinpeng Lin
 * @date 2017/10/30
 *
 * AES128 算法
 * CBC 模式
 * PKCS7Padding 填充模式
 *
 * CBC模式需要添加一个参数iv
 *
 * 介于java 不支持PKCS7Padding，只支持PKCS5Padding 但是PKCS7Padding 和 PKCS5Padding 没有什么区别
 * 要实现在java端用PKCS7Padding填充，需要用到bouncycastle组件来实现
 */
public class AESUtil {
    private static final String KEY_ALGORITHM = "AES";
    /**
     * 加解密算法/模式/填充方式
     */
    private static final String ALGORITHMSTR = "AES/CBC/PKCS7Padding";

    /**
     * 加密方法
     * @param content   要加密的字符串
     * @param keyBytes  加密密钥
     * @return
     */
    public static byte[] encrypt(byte[] ivBytes, String content, byte[] keyBytes) {
        byte[] encryptedText = null;
        // 补齐秘钥
        keyBytes = fillUpKeyBytes(keyBytes);
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        Key key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR, "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivBytes));
            encryptedText = cipher.doFinal(content.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    /**
     * 解密方法
     * @param encryptedData     需要解密的字节数组
     * @param keyBytes          解密密钥
     * @return
     */
    public static String decrypt(byte[] ivBytes, byte[] encryptedData, byte[] keyBytes) {
        String resultStr = null;
        // 补齐秘钥
        keyBytes = fillUpKeyBytes(keyBytes);
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        Key key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            Cipher cipher = Cipher.getInstance(ALGORITHMSTR, "BC");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
            byte[] encryBytes = cipher.doFinal(encryptedData);
            resultStr = new String(encryBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    /**
     * 若秘钥长度不足16位，则补齐
     * @param keyBytes
     * @return
     */
    private static byte[] fillUpKeyBytes(byte[] keyBytes) {
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        return keyBytes;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String enStr = "pqmmtsyNXgUbVqxDuuEgbj7pS+xXHULEsU/lZMd2YADmyd418Ftb1gRcaA704AoKnkceFweeBsj3sIMpm8nZr50jyo7ElamodKQWOJYlXg+s13f0et3TbmiJS4uvLJbwhGt6QVtayHIB7wPyADr/XxEP5WJCvZl81d0T23Yw+SAhqVzEeoJt+wpWnus6Zdpz76pYFcB7Xw/VdX9HyNaMdOJzP4eNB42JtB+b9OaA3xf/AW/fxk1/4+yLS+khZyDoOvaeMHxp8m1hpD9Bem3Kh0Y4jf5+nRxd1AEdIJBDfhw/r8qTirbU7Me69Y3tpc96bIIHqM7jKOIGV6YFvFGm//a9jGKGFm0ueEJlCZPat5DNNkvXvCj4VpPsy//DhEiIqj7yGIAHc4fBmspvoKhtOT6mK8bOc95oUm28Wao96OBEUi0pCSBgC+7DHD1BJ/6I/EB/1Fzaxz60zUti9PCe2OTQmEisMdsgT1G8t34FZPg1DdOxxdD3pUDZGmUxkL/TPYts0HPPSeUuwzaJsUfniuroouVDzFg7cD43nE/3r/3Yx0wgl12pVRXxT55bapvkNohmKal/Qrgr7rS7NUcoLW9T3mocTYtCo+LywXUaphwVESR1kSs8PGD8IvJufy3+75YAQ11MIApx1XYSO4DM1kTROE6Zyj6wiRloLrevf4NtnFtWqD+AYyPvfQztjkFkNAUPYk5YVFz7Q3SZ3c0esMjs1EyB7xuYznWgRh+OEblIBlFZSbYQy2VP/WxbopC+vr38Em/RNVl/Pt9ObBTvO8gyIVqhY6V35YzCS0MTIP8Eb5tXgRuVR0aPT1empq+vSHzpGKnremec7+yEfO0k7M63k0htQis/jnEYYnoUHvUlGh05IL/Q0JO6vscqCCLyiejW3LMPlxUB41Mzlm2ieOgDiZ8j2DhwJDglOz5uw8n2SIIWFuBD7wOBe6Nv9vWh1cgI1MFJ6TPHg0PPaFkfWnB4cH6/bBbfbm1A6N0hHZn9ppqIt+nxq8XuSEPegkABjWV74wTKG/dQ/APvNDSVrwYdJ3Y3fGHsKgY7LhYP1CwDcG6FPdMs/EQA/GtS8U/aHxtkGhX8JxEKOrIonr0R5kZ4qwi5zIbahyHA2hEJRLAlN+pkLPHxJcbRpeoYE6qBYQu6R+7Kays+qiW9hurZ7AWP88yVZrIojedpkSidUr/qxbAGI842RMoUIBCmFge8KJzOmxZSLvC5LI/SGCcKpX0jttuX6xW1i/PJ2nU7LJto9MmgRrtZLqsmMbqMUf1HJ61RGSpYS8zmVXiCnfXHtJUNQn/kpFs046T7aqb0EVALJGP0qZzCW+HwB6DstnhZQEqTYP1ZClPKnewGmPkOUrcFfK8BptB7LTl9kC/nDS1E+BYkNrFm/Do0YYPF+GEpZKBuy04ZbGjGN1tvabJijwL7ZgzvVo6NtsguL2BglpC8GjrxVZClCm/h6tSn6z3Egy6H1oeLfji4t9waoLscnI1uBNyp3ch8MPxzEaZe5TMWw6s/6/zIE8zmaSbMlr0xAHt1cJ/cH+azb57CfeD2+P1GY9f4fy4sukKF0Ct2AugC15vCrWjz3qkqHwSDsh3PGQuE3cGzypDnf6rb8+N6bRsTObhqwZ1ExEU8Tx7jmpQ=";
        String ivStr = "ZBjoNJG+mXI3ZHZJeSztKw==";
        String sessionKey = "DxZNFOaPmftShIpYrfHwTg==";

        byte[] keyBytes = Base64.decode(sessionKey);
        byte[] ivBytes = Base64.decode(ivStr);
        byte[] enBytes = Base64.decode(enStr);

        String result = AESUtil.decrypt(ivBytes, enBytes, keyBytes);
        System.out.println(result);
    }
}
