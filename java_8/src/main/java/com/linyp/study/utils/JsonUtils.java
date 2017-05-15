package com.linyp.study.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;


public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 对象转换成json格式
     *
     * @param obj
     * @return
     */
    public static String toJSON(Object obj) {
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    /**
     * json格式转换成对象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJSON(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从json map表达式中获取一个map，简单对象可直接使用此方法，object如是List等对象则注意类型转换问题，需作进一步转化:
     * 再调用getList4Json转换为list
     *
     * @param jsonString
     * @return Map
     */
    @SuppressWarnings("unchecked")
    public static Map<Object, Object> getMap4Json(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        Map<Object, Object> map = null;
        try {
            map = mapper.readValue(jsonString, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}