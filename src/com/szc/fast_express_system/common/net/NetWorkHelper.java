package com.szc.fast_express_system.common.net;

import com.szc.fast_express_system.common.util.LogUtil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;


/**
 * ****************************************
	 * 类描述： 网络帮助�?
	 * 类名称：NetWorkHelper  
 	 * @version: 1.0
	 * @author: ly
	 * @time: 2013-12-3 上午11:54:52 
*****************************************
 */
public class NetWorkHelper {
	private static String TAG = "NetWorkHelper";

	/**
	 * 判断是否有网络连�?
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity == null) {
			LogUtil.Log(TAG, "couldn't get connectivity manager");
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						LogUtil.Log(TAG, "network is available");
						return true;
					}
				}
			}
		}
		LogUtil.Log(TAG, "network is not available");
		return false;
	}
	
	/**
	 * 判断wifi 是否可用
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean isWifiDataEnable(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isWifiDataEnable = false;
		isWifiDataEnable = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		return isWifiDataEnable;
	}

	/**
	 * 判断MOBILE网络是否可用
	 * 
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static boolean isMobileDataEnable(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean isMobileDataEnable = false;

		isMobileDataEnable = connectivityManager.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

		return isMobileDataEnable;
	}


	/**
	 * 判断网络是否为漫�?
	 */
	public static boolean isNetworkRoaming(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			LogUtil.Log(TAG, "couldn't get connectivity manager");
		} else {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null
					&& info.getType() == ConnectivityManager.TYPE_MOBILE) {
				TelephonyManager tm = (TelephonyManager) context
						.getSystemService(Context.TELEPHONY_SERVICE);
				if (tm != null && tm.isNetworkRoaming()) {
					LogUtil.Log(TAG, "network is roaming");
					return true;
				} else {
					LogUtil.Log(TAG,  "network is not roaming");
				}
			} else {
				LogUtil.Log(TAG, "not using mobile network");
			}
		}
		return false;
	}

}
