package com.szc.fast_express_system.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.szc.fast_express_system.ExpressSystemApplication;
import com.szc.fast_express_system.common.exception.BusinessException;
import com.szc.fast_express_system.common.exception.ErrorMessage;
import com.szc.fast_express_system.common.net.Request;
import com.szc.fast_express_system.common.util.Config;
import com.szc.fast_express_system.common.util.ToastUtil;
import com.szc.fast_express_system.entities.OrderData;
import com.szc.fast_express_system.entities.Register;
import com.szc.fast_express_system.entities.json.LoginResp;
import com.szc.fast_express_system.entities.json.RegisterResp;
import com.szc.fast_express_system.entities.json.ReturnValue;
import com.szc.fast_express_system.service.AppContext;
import com.szc.fast_express_system.service.AppController;
import com.szc.fast_express_system.service.AppService;

import android.os.Handler;
import android.util.Base64;
import android.util.Log;


/******************************************
 * 类描述： 业务实现�? 类名称：ServiceImpl
 * 
 * @version: 1.0
 * @author: why
 * @time: 2014-2-13 下午2:09:22
 ******************************************/
public class AppServiceImpl implements AppService {
	@SuppressWarnings("unused")
	private String TAG = "AppServiceImpl";
	private AppContext context;
	private Boolean loginmsg=false;
	private String responseMsg="";
	private static final int REQUEST_TIMEOUT = 5 * 1000;// 设置请求超时10秒钟
	private static final int SO_TIMEOUT = 10 * 1000; // 设置等待数据超时时间10秒钟
	/**
	 * 类的构�?�方�? 创建�?个新的实�? AppServiceImpl.
	 * 
	 * @param
	 * @param context
	 */
	public AppServiceImpl(AppContext context) {
		this.context = context;
	}
	// 初始化HttpClient，并设置超时
	public HttpClient getHttpClient() {
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		return client;
	}

	
	@Override
	public void login(final Handler handler) throws BusinessException {
		String account = (String)context.getBusinessData("user.account");
		String password = (String)context.getBusinessData("user.password");
		String type = (String)context.getBusinessData("user.type");
		//Request<LoginResp> request = new Request<LoginResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		
		nameValuePairs.add(new BasicNameValuePair("username", account));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		nameValuePairs.add(new BasicNameValuePair("type", type));
	
		/*nameValuePairs.add(new BasicNameValuePair("isdata", "true"));
		request.addParameter(Request.AJAXPARAMS, nameValuePairs);
		request.setUrl(Config.HTTP_USER_LOGIN);
		request.setR_calzz(LoginResp.class);
		final LoginResp resp = ExpressSystemApplication.getAppSocket().shortConnect(request);
		if ("1".equals(resp.getStatus())) {
			handler.sendEmptyMessage(1);
		} else{
			handler.sendEmptyMessage(1);
			throw new BusinessException(new ErrorMessage(resp.getText()));
		}*/
		String urlStr=Config.HTTP_USER_LOGIN;
		HttpPost request=new HttpPost(urlStr);
		try {
			// 设置请求参数项
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			HttpClient client = getHttpClient();
			// 执行请求返回相应
			HttpResponse response = client.execute(request);
			
			Log.d("state", response.getStatusLine().getStatusCode()+"");
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				
				// 获得响应信息
				final HttpEntity entity = response.getEntity();
				if (entity == null) {
				    Log.w(TAG, "The response has no entity.");

				    //  NOTE: this method will return "" in this case, so we must check for that in onPostExecute().

				    // Do whatever is necessary here...
				} else {
					responseMsg = EntityUtils.toString(response.getEntity(), "utf-8");
					Log.d("neirong", responseMsg);
//					responseMsg = EntityUtils.toString((HttpEntity) entity.getContent());
					context.addBusinessData("return.login", responseMsg);
				}
			//	responseMsg = EntityUtils.toString(response.getEntity());
				Log.d("huoqu fanhui zhuangtai ", responseMsg);
				
				/*ReturnValue lv=new ReturnValue();
				lv.setLoginMsg(responseMsg);*/
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	
	
	
	
	
	public void register(final  Handler handler) throws BusinessException {
	//	Register register = (Register)context.getBusinessData("Register.register");
		Register u=(Register) context.getBusinessData("user.register");
	//	Request<RegisterResp> request = new Request<RegisterResp>();
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair("username", u.getUsername()));
		//nameValuePairs.add(new BasicNameValuePair("phone", register.getPhone()));
		nameValuePairs.add(new BasicNameValuePair("passwd", u.getPassword()));
		nameValuePairs.add(new BasicNameValuePair("type", u.getType()));

	
		String urlStr=Config.HTTP_USER_REGISTER;
		HttpPost request=new HttpPost(urlStr);
		try {
			// 设置请求参数项
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			HttpClient client = getHttpClient();
			// 执行请求返回相应
			HttpResponse response = client.execute(request);

			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				
				// 获得响应信息			
			//	responseMsg = EntityUtils.toString(response.getEntity());
				responseMsg = EntityUtils.toString(response.getEntity(), "utf-8");
                 context.addBusinessData("return.regist", responseMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void submit(final  Handler handler) throws BusinessException {
		//	Register register = (Register)context.getBusinessData("Register.register");
			OrderData u=(OrderData) context.getBusinessData("Order.Data");
		//	Request<RegisterResp> request = new Request<RegisterResp>();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			
			nameValuePairs.add(new BasicNameValuePair("username",u.getUsername()));
			//nameValuePairs.add(new BasicNameValuePair("phone", register.getPhone()));
			nameValuePairs.add(new BasicNameValuePair("phone", u.getPhone()));
			nameValuePairs.add(new BasicNameValuePair("adress", u.getAdress()));
			nameValuePairs.add(new BasicNameValuePair("article", u.getArticle()));
			nameValuePairs.add(new BasicNameValuePair("weight", u.getWeight()));
			nameValuePairs.add(new BasicNameValuePair("latitude", u.getLatitude()));
			nameValuePairs.add(new BasicNameValuePair("lontitude", u.getLontitude()));
		
			String urlStr=Config.HTTP_USER_SUBMIT;
			HttpPost request=new HttpPost(urlStr);
			try {
				// 设置请求参数项
				request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
				HttpClient client = getHttpClient();
				// 执行请求返回相应
				HttpResponse response = client.execute(request);

				// 判断是否请求成功
				if (response.getStatusLine().getStatusCode() == 200) {
					
					// 获得响应信息			
				//	responseMsg = EntityUtils.toString(response.getEntity());
					responseMsg = EntityUtils.toString(response.getEntity(), "utf-8");
	                 context.addBusinessData("order.rsp", responseMsg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	
	public void getorder(final  Handler handler) throws BusinessException {
		
		String username=(String)context.getBusinessData("user.account");
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			
		nameValuePairs.add(new BasicNameValuePair("username",username));
	
		
		String urlStr=Config.HTTP_USER_getorder;
		HttpPost request=new HttpPost(urlStr);
		
			try {
				// 设置请求参数项
				//URLEncoder.encode(((NameValuePair) request).getValue(), "UTF-8");
				request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
				HttpClient client = new DefaultHttpClient();
				// 请求超时
				client.getParams().setParameter(
						CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
				// 读取超时
				client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
						6000);
				
				// 执行请求返回相应
				HttpResponse response = client.execute(request);

				// 判断是否请求成功
				if (response.getStatusLine().getStatusCode() == 200) {
					
					// 获得响应信息	
				//	TNBLogUtil.debug(EntityUtils.toString(response.getEntity(), "utf-8"));

					responseMsg = EntityUtils.toString(response.getEntity(), "utf-8");
					
					Log.d("neirong", responseMsg);
	                context.addBusinessData("order.list", responseMsg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*try {
				JSONArray jsona = new JSONArray(responseMsg);
				JSONObject json = jsona.getJSONObject(1);
				byte adds[] = json.getString("adress").getBytes();
				StringBuilder sb = new StringBuilder();
				for(byte b : adds){
					sb.append(byte2bits(b));
					sb.append("~~");
				}
				Log.e("response", sb.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			
		}	
	
	


	
	public void getallorder(final  Handler handler) throws BusinessException {
		
		OrderData u=(OrderData) context.getBusinessData("jingwei.Data");
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			
		nameValuePairs.add(new BasicNameValuePair("Latitude",u.getLatitude()));
		nameValuePairs.add(new BasicNameValuePair("Lontitude",u.getLontitude()));
	
		String urlStr=Config.HTTP_USER_getallorder;
		HttpPost request=new HttpPost(urlStr);
			try {
				// 设置请求参数项
				request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
				HttpClient client = getHttpClient();
				// 执行请求返回相应
				HttpResponse response = client.execute(request);

				// 判断是否请求成功
				if (response.getStatusLine().getStatusCode() == 200) {
					
					// 获得响应信息			
					responseMsg = EntityUtils.toString(response.getEntity(), "utf-8");
					URLDecoder.decode(responseMsg,"utf-8"); 
					Log.d("neirong", responseMsg);
	                 context.addBusinessData("order.list", responseMsg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
public void sendstate(final  Handler handler) throws BusinessException {
		
		OrderData u=(OrderData) context.getBusinessData("UserOrder.Data");
		OrderData state=(OrderData) context.getBusinessData("Userstate.Data");
		String acceptnm=(String)context.getBusinessData("user.account");
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			
		nameValuePairs.add(new BasicNameValuePair("username",u.getUsername()));
		nameValuePairs.add(new BasicNameValuePair("phone",u.getPhone()));
		nameValuePairs.add(new BasicNameValuePair("adress",u.getAdress()));
		nameValuePairs.add(new BasicNameValuePair("article",u.getArticle()));
		nameValuePairs.add(new BasicNameValuePair("weight",u.getWeight()));
		nameValuePairs.add(new BasicNameValuePair("state",state.getState()));
		nameValuePairs.add(new BasicNameValuePair("acceptnm",acceptnm));
	
		String urlStr=Config.HTTP_USER_OrderState;
		HttpPost request=new HttpPost(urlStr);
			try {
				// 设置请求参数项
				request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
				HttpClient client = getHttpClient();
				// 执行请求返回相应
				HttpResponse response = client.execute(request);

				// 判断是否请求成功
				if (response.getStatusLine().getStatusCode() == 200) {
					
					// 获得响应信息			
					responseMsg = EntityUtils.toString(response.getEntity(), "utf-8");
					Log.d("neirong", responseMsg);
	                 context.addBusinessData("order.list", responseMsg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
public void orderchange(final  Handler handler) throws BusinessException {
	
	OrderData u=(OrderData) context.getBusinessData("UserChange.Data");
	OrderData s=(OrderData) context.getBusinessData("UserOrder.Data");
	
	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
	nameValuePairs.add(new BasicNameValuePair("weight",u.getWeight()));
	nameValuePairs.add(new BasicNameValuePair("article",u.getArticle()));
	nameValuePairs.add(new BasicNameValuePair("ordernum",s.getOrdernum()));

	String urlStr=Config.HTTP_USER_OrderChange;
	HttpPost request=new HttpPost(urlStr);
		try {
			// 设置请求参数项
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			HttpClient client = getHttpClient();
			// 执行请求返回相应
			HttpResponse response = client.execute(request);

			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				
				// 获得响应信息			
				responseMsg = EntityUtils.toString(response.getEntity(), "utf-8");
				Log.d("neirong", responseMsg);
                 context.addBusinessData("order.list", responseMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
public void deleteOrder(final  Handler handler) throws BusinessException {
	
	OrderData s=(OrderData) context.getBusinessData("UserOrder.Data");
	
	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
	nameValuePairs.add(new BasicNameValuePair("ordernum",s.getOrdernum()));

	String urlStr=Config.HTTP_USER_deleteOrder;
	HttpPost request=new HttpPost(urlStr);
		try {
			// 设置请求参数项
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			HttpClient client = getHttpClient();
			// 执行请求返回相应
			HttpResponse response = client.execute(request);

			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				
				// 获得响应信息			
				responseMsg = EntityUtils.toString(response.getEntity(), "utf-8");
				Log.d("neirong", responseMsg);
                 context.addBusinessData("order.list", responseMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
public void acceptorder(final  Handler handler) throws BusinessException {
	
	String username=(String)context.getBusinessData("user.account");
	
	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
	nameValuePairs.add(new BasicNameValuePair("username",username));

	String urlStr=Config.HTTP_USER_acceptlist;
	HttpPost request=new HttpPost(urlStr);
		try {
			// 设置请求参数项
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
			HttpClient client = getHttpClient();
			// 执行请求返回相应
			HttpResponse response = client.execute(request);

			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				
				// 获得响应信息			
				responseMsg = EntityUtils.toString(response.getEntity(), "UTF-8");
				//URLDecoder.decode(responseMsg,"UTF-8"); 
				Log.d("neirong", responseMsg);
                 context.addBusinessData("order.list", responseMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
