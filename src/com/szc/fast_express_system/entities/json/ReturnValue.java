package com.szc.fast_express_system.entities.json;

public class ReturnValue {
	private String LoginMsg;
	private String RegistMsg;

	public String getRegistMsg() {
		return RegistMsg;
	}

	public void setRegistMsg(String registMsg) {
		RegistMsg = registMsg;
	}

	public String getLoginMsg() {
		return LoginMsg;
	}

	public void setLoginMsg(String loginMsg) {
		LoginMsg = loginMsg;
	}

}
