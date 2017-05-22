package com.linyp.study.utils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by linyp on 2017/5/21.
 * 序列化工具
 */
public class ProtoStuffUtils {
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    /**
     * 获取Schema对象
     * @param cls
     * @param <T>
     * @return
     */
    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            if (schema != null) {
                cachedSchema.put(cls, schema);
            }
        }
        return schema;
    }

    /**
     * 序列化
     * @param obj   序列化目标对象
     * @return
     */
    public static <T> byte[] toBytes(T obj) {
        Class<T> clz = (Class<T>) obj.getClass();
        Schema<T> schema = getSchema(clz);
        return ProtobufIOUtil.toByteArray(obj, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    /**
     * 反序列化
     * @param bytes 字节数组
     * @param clz   对象类型
     * @return
     */
    public static <T> T toObject(byte[] bytes, Class<T> clz) {
        Schema<T> schema = getSchema(clz);
        T obj = schema.newMessage();
        ProtobufIOUtil.mergeFrom(bytes, obj, schema);
        return obj;
    }

    /*public static void main(String[] args) {
        Student std = new Student("小明", 18, "15900000000");
        byte[] bytes = toBytes(std);
        System.out.println(bytes.length);
        Student newStd = toObject(bytes, Student.class);
        System.out.println(newStd.toString());
    }*/

}

/*class Student {
    private String name;
    private int age;
    private String phone;

    public Student(String name, int age, String phone) {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }
}*/
