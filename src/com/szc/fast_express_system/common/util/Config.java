package com.szc.fast_express_system.common.util;

import java.io.File;

import android.os.Environment;

/**
 * **************************************** 
 * 类描述： 配置信息�? 类名称：Config
 * @version: 1.0
 * @author: why
 * @time: 2014-2-14 下午3:32:29
 ***************************************** 
 */
public class Config {
	public static String DOWNLOADPATH = "/sdcard/timetalent/";
	public static String IMAGEPATH = "/sdcard/timetalent/image/";

	/** 是否调试. */
	public final static boolean DEBUG = true;
	
	// 后台 测试
public final static String MY_SERVICE = "http://192.168.191.1:8080/LastServer";
//	public final static String MY_SERVICE = "http://10.0.2.2:8080/LastServer";
	/** 临时文件保存路径. */
	// 项目路径
	public static final String PATH_SDCARD = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + File.separator + "TimeTalent";
	
////缩略�?

	//图片保存路径
	public static final String PIC_PATH = "/mnt/sdcard/TimeTalent/picture/";
	
	//登录
	public final static String HTTP_USER_LOGIN = MY_SERVICE+ "/userServlet/userLogin"; // 登录接口
	public final static String HTTP_USER_REGISTER = MY_SERVICE+ "/userServlet/userRegister"; // 注册接口
	public final static String HTTP_USER_SUBMIT = MY_SERVICE+ "/userServlet/userOrder"; // 订单
	public final static String HTTP_USER_getorder = MY_SERVICE+ "/userServlet/orderlist"; // 获得订单
	public final static String HTTP_USER_getallorder = MY_SERVICE+ "/userServlet/allorderlist"; // 
	public final static String HTTP_USER_OrderState = MY_SERVICE+ "/userServlet/orderstate"; // 修改
	
	//获取验证�?
	public final static String HTTP_USER_OrderChange= MY_SERVICE+ "/userServlet/orderchange"; 
	public final static String HTTP_USER_deleteOrder = MY_SERVICE+ "/userServlet/orderdelete"; 
	public final static String HTTP_USER_acceptlist = MY_SERVICE+ "/userServlet/acceptlist"; 

	
	
	
	
}
