package com.szc.fast_express_system.entities.json;

import com.szc.fast_express_system.entities.LoginData;



/******************************************
 * 类描述： 登录返回值
 * 类名称：LoginResp  
 * @version: 1.0
 * @time: 2014-2-20 下午3:29:26 
 ******************************************/
public class LoginResp extends BaseResp {
	private LoginData data;

	/**
	 * @return data : return the property data.
	 */
	public LoginData getData() {
		return data;
	}

	/**
	 * @param data : set the property data.
	 */
	public void setData(LoginData data) {
		this.data = data;
	}
	
}
