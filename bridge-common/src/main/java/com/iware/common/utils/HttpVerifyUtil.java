package com.iware.common.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.common.base.Charsets;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpVerifyUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public static final CloseableHttpClient httpClient = HttpClients.createDefault();

	public static int sendVerifyPost(String url,String product,String proModel,int count){

		CloseableHttpResponse response = null;
		int code = 0;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url+"/license/verifyLicenseList");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("product",product);
			jsonObject.put("proModel",proModel);
			jsonObject.put("count",count);
			StringEntity entity = new StringEntity(jsonObject.toString(), Charsets.UTF_8);
			entity.setContentType("application/json;charset=UTF-8");
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
			JSONObject content = JSON.parseObject(resultString);
			code = content.getIntValue("code");

		} catch (Exception e) {
			logger.error(ExceptionUtils.getErrorStack(e));
		} finally {
			try {
				if(response!=null) {
					response.close();
				}
			} catch (IOException e) {
				logger.error(ExceptionUtils.getErrorStack(e));
			}
		}
		return code;
	}
	public static String getUrl() {
		Properties prop = new Properties();
		InputStream in =  HttpVerifyUtil.class.getResourceAsStream("/verify.properties");
		try
		{
			prop.load(in);
		} catch (IOException e) {
			logger.error(ExceptionUtils.getErrorStack(e));
		}

		String url = prop.getProperty("verify.url");
		return url;
	}
}
