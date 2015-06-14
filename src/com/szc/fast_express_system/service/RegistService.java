package com.szc.fast_express_system.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.szc.fast_express_system.common.util.Config;

public class RegistService {
	 private static final int REQUEST_TIMEOUT = 5*1000;//设置请求超时10秒钟    
	 private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟    
	 private static final int LOGIN_OK = 1;  

	public static boolean registerServer(String username, String password)  
     {  
         boolean loginValidate = false;  
         
         //使用apache HTTP客户端实现  
         String urlStr = Config.HTTP_USER_REGISTER;  
         HttpPost request = new HttpPost(urlStr);  
         //如果传递参数多的话，可以丢传递的参数进行封装  
         List<NameValuePair> params = new ArrayList<NameValuePair>();  
         //添加用户名和密码  
         params.add(new BasicNameValuePair("username",username));  
         params.add(new BasicNameValuePair("password",password));  
         try  
         {  
             //设置请求参数项  
             request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));  
             HttpClient client = getHttpClient();  
             //执行请求返回相应  
             HttpResponse response = client.execute(request);  
               
             //判断是否请求成功  
             if(response.getStatusLine().getStatusCode()==200)  
             {  
                 loginValidate = true;  
                 EntityUtils.toString(response.getEntity());  
             }  
         }catch(Exception e)  
         {  
             e.printStackTrace();  
         }  
         return loginValidate;  
     } 
	//初始化HttpClient，并设置超时  
    public static HttpClient getHttpClient()  
    {  
        BasicHttpParams httpParams = new BasicHttpParams();  
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);  
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);  
        HttpClient client = new DefaultHttpClient(httpParams);  
        return client;  
    }  

    
    
}
