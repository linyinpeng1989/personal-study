package com.netty.rpc.protocol;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by linyp on 2017/5/22.
 * 序列化工具类(ProtoStuff实现)
 */
public class ProtoStuffSerializableUtil {
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    private static Objenesis objenesis = new ObjenesisStd(true);

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
    public static <T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();
        Schema<T> schema = getSchema(cls);
        return ProtostuffIOUtil.toByteArray(obj, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    /**
     * 反序列化
     * @param data  字节数组
     * @param cls   对象类型
     * @return
     */
    public static <T> T deserialize(byte[] data, Class<T> cls) {
        T message = objenesis.newInstance(cls);
        Schema<T> schema = getSchema(cls);
        ProtostuffIOUtil.mergeFrom(data, message, schema);
        return message;
    }

    public static void main(String[] args) {
        Student std = new Student("小明", 18, "15900000000");
        // 使用Protostuff进行序列化
        long begin1 = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        byte[] bytes = serialize(std);
        long end1 = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        System.out.println("Protostuff序列化后字节数组长度：" + bytes.length);
        System.out.println("Protostuff序列化耗时：" + (end1 - begin1));
        Student newStd = deserialize(bytes, Student.class);
        System.out.println(newStd.toString());

        // 使用Object Serializable进行序列化
        long begin2 = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        byte[] bytes1 = ObjectSerializableUtil.serialize(std);
        long end2 = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        System.out.println("Object Serializable序列化后字节数组长度：" + bytes1.length);
        System.out.println("Object Serializable序列化耗时：" + (end2 - begin2));
        Student newStd1 = ObjectSerializableUtil.deserialize(bytes1, Student.class);
        System.out.println(newStd1.toString());
    }

}

class Student implements Serializable {
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
}