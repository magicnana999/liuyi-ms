package com.creolophus.liuyi.common.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author magicnana
 * @date 2020/8/17 1:51 PM
 */
public class JSON {

    public static <T> T parseObject(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T parseObject(Object object,Class<T> clazz){
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(object, clazz);
    }

    public static <T> T parseObject(byte[] bytes,Class<T> clazz){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(bytes,clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJSONString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toJSONBytes(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
