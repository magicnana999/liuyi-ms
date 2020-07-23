package com.creolophus.liuyi.common.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author magicnana
 * @date 2019/7/3 上午10:36
 */
public class ApiContext implements Serializable {

    private long userId;
    private String ip;
    private String uri;
    @JSONField(serialize = false)
    private String userAgent;
    private String token;
    private Map<String,Object> ext = new HashMap(8);


    @JSONField(serialize = false)
    private HttpServletRequest request;
    private Object apiResult;

    public static ApiContext getContext() {
        ApiContext apiContext = ApiContextLocal.getInstance().get();
        return apiContext;
    }

    public static void releaseContext(){
        ApiContextLocal.getInstance().remove();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Map<String, Object> getExt() {
        return ext;
    }

    public void setExt(Map<String, Object> ext) {
        this.ext = ext;
    }

    public void setExt(String key,Object value){
        this.getExt().put(key, value);
    }

    public void delExt(String key){
        this.getExt().remove(key);
    }

    public <T> T  getExt(String key){
        return (T)this.getExt().get(key);
    }

    public Object getApiResult() {
        return apiResult;
    }

    public void setApiResult(Object apiResult) {
        this.apiResult = apiResult;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}