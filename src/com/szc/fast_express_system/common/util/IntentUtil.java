package com.szc.fast_express_system.common.util;

import com.szc.fast_express_system.R;
import com.szc.fast_express_system.service.AppManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


/******************************************
 * 类描述： 跳转处理
 *  类名称：IntentUtil
 * 
 * @version: 1.0
 * @time: 2014-2-19 下午5:35:38
 ******************************************/
public class IntentUtil {
	
	/**
	  * 方法描述�? 不finish当前�? 跳转到指定页�?  
	  * @param loginActivity
	  * @param class1
	  * @author: why
	  * @time: 2014-4-3 上午11:49:44
	  */
	@SuppressWarnings("rawtypes")
	public static void intent(Context context,
			Class class1) {
		intent(context,class1,false);
	}
	
	/**
	 * 
	 * 方法描述�? finish当前�? 跳转到指定页�?  
	 * 
	 * @author: Administrator
	 * @time: 2014-2-19 下午5:37:37
	 */
	@SuppressWarnings("rawtypes")
	public static void intent(Context context,Class class1,boolean flag) {
		intent(context,null,class1,flag);
	}
	
	/**
	 * 
	 * 方法描述：跳转到指定页面
	 * 
	 * @author: Administrator
	 * @param isFinish是否finish掉activity
	 * @time: 2014-2-19 下午5:37:37
	 */
	public static void intent(Context context, Bundle bundle,
			@SuppressWarnings("rawtypes") Class class1, boolean isFinish) {
		Intent intent = new Intent(context, class1);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
		if (isFinish) {
			AppManager.getAppManager().finishActivity((Activity) context);
		}
		pushFromRight((Activity) context);
	}
	 

	/**
	 * 
	 * 界面前进动画效果
	 * 
	 * @param activity
	 */
	public static void pushFromRight(Activity activity) {
		activity.overridePendingTransition(R.anim.push_left_in,
				R.anim.push_left_out);
	}

	/**
	 * 
	 * 界面返回动画效果
	 * 
	 * @param activity
	 */
	public static void popFromLeft(Activity activity) {
		activity.overridePendingTransition(R.anim.push_right_out,
				R.anim.push_right_in);
	}
}
