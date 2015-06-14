package com.szc.fast_express_system.entities.json;

import com.szc.fast_express_system.entities.RegisterData;



/******************************************
 * 类描述： TODO
 * 类名称：RegisterResp  
 * @version: 1.0
 * @author: wanghy
 * @time: 2014-11-4 下午10:58:57 
 ******************************************/
public class RegisterResp extends BaseResp {
	private RegisterData data;
	
	/**
	 * @param data : set the property data.
	 */
	public void setData(RegisterData data) {
		this.data = data;
	}
	
	/**
	 * @return data : return the property data.
	 */
	public RegisterData getData() {
		return data;
	}
	

}
