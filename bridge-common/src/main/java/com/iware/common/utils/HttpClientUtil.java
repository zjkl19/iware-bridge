package com.iware.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class  HttpClientUtil <T> {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private String url;
    private String method;
    private Map<String, T> requestData;
    private CloseableHttpResponse response;
    private CloseableHttpClient client;
    private Map<String,T> header;
    public HttpClientUtil(){
        this.client = HttpClients.createDefault();
    }
    public HttpClientUtil(String url,String method,Map<String,T> requestData){
        this.url=url;
        this.method=method;
        this.requestData=requestData;
        this.client = HttpClients.createDefault();
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setRequestData(Map<String, T> requestData) {
        this.requestData = requestData;
    }

    public void setHeader(String key,T value){
        this.header.put(key,value);
    }
    public void setHeader(Map<String,T> header){
        this.header=header;
    }
    public CloseableHttpResponse request(){
        if ("post".equals(this.method)){
                this.response = requestPost();
        }
        return this.response;
    }
    public CloseableHttpResponse requestPost(){
        HttpPost post = new HttpPost(url);
        List<NameValuePair> nameValuePairList=new ArrayList<>();
        if(!this.requestData.isEmpty()){
            for (Map.Entry<String, T> entry : this.requestData.entrySet()) {
                Object value = entry.getValue();
                nameValuePairList.add(new BasicNameValuePair(entry.getKey(), (String) value));
            }

        }
        StringEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            post.setEntity(entity);
            if(this.header!=null && !this.header.isEmpty()){
                for (Map.Entry<String, T> entry : this.header.entrySet()) {
                    post.setHeader(new BasicHeader(entry.getKey(), (String) entry.getValue()));
                }

            }else{
                post.setHeader(new BasicHeader("Content-Type"," application/x-www-form-urlencoded;charset=utf-8"));
                post.setHeader(new BasicHeader("Accept","*/*"));
            }
            this.response = client.execute(post);
            return this.response;
        } catch (IOException e) {
            logger.error(ExceptionUtils.getErrorStack(e));
        }
        return null;
    }
    public int getStatus(){
        return  this.response.getStatusLine().getStatusCode();
    }
    public JSONObject getResponse() throws IOException {
        if(this.response!=null){
            String result = EntityUtils.toString(this.response.getEntity(),"UTF-8");
            return JSONObject.parseObject(result);
        }
        return null;
    }
}
