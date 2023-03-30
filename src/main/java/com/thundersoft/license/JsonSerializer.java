package com.thundersoft.license;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class JsonSerializer {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    // 将Java对象转换为JSON字符串
    public static <T> String serialize(T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("json序列化异常:" + e.getMessage());
        }
    }

    // 将JSON字符串转换为Java对象
    public static <T> T deserialize(String json, Class<T> cls) {
        try {
            return mapper.readValue(json, cls);
        } catch (Exception e) {
            throw new RuntimeException("json反序列化异常:" + e.getMessage());
        }
    }
}
