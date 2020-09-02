package com.creolophus.liuyi.common.json;

/**
 * @author magicnana
 * @date 2020/8/17 1:51 PM
 */
public class JSON {

    public static <T> T parseObject(String json, Class<T> clazz) {
        return JacksonUtil.toJava(json, clazz);
    }

    public static <T> T parseObject(Object object,Class<T> clazz){

        if(object == null || clazz == null){
            return null;
        }
        return JacksonUtil.toJava(object, clazz);

    }

    public static <T> T parseObject(byte[] bytes,Class<T> clazz){
        if(bytes == null || bytes.length==0 || clazz == null){
            return null;
        }
        return JacksonUtil.toJava(bytes, clazz);

    }

    public static String toJSONString(Object object) {
        if(object == null){
            return null;
        }
        return JacksonUtil.toJson(object);
    }

    public static byte[] toJSONBytes(Object object){
        if(object == null){
            return null;
        }
        return JacksonUtil.toByteArray(object);
    }
}
