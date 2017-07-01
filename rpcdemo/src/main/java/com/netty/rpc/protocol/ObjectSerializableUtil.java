package com.netty.rpc.protocol;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Created by linyp on 2017/6/26.
 * 序列化工具类(Object Serializable)
 */
public class ObjectSerializableUtil {
    /**
     * 序列化
     * @param obj   序列化目标对象
     * @return
     */
    public static <T> byte[] serialize(T obj) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream outputStream = null;
        byte[] result = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(obj);
            result = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(byteArrayOutputStream);
            IOUtils.closeQuietly(outputStream);
        }
        return result;
    }

    /**
     * 反序列化
     * @param data  字节数组
     * @param cls   对象类型
     * @return
     */
    public static <T> T deserialize(byte[] data, Class<T> cls) {
        T obj = null;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            obj = (T) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(objectInputStream);
        }
        return obj;
    }
}
