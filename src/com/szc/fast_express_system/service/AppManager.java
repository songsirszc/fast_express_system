package com.szc.fast_express_system.service;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

/**
 * 应用程序Activity管理类：用于Activity管理和应用程序�??�?
 * @author why (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class AppManager {

	private static Stack<Activity> activityStack;
	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	/**
	 * 添加Activity到堆�?
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获取当前Activity（堆栈中�?后一个压入的�?
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 结束当前Activity（堆栈中�?后一个压入的�?
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}
	
	/**
	 * 结束�?有Activity，留下指定类名的Activity
	 */
	@SuppressWarnings("unused")
	private  void finishActivityBut(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (!activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束�?有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * �?出应用程�?
	 */
	@SuppressWarnings("deprecation")
	public void AppExit(Context context) {
		try {
			//关闭�?有界�?
			finishAllActivity();
			if (VERSION.SDK_INT < VERSION_CODES.FROYO) {
				ActivityManager activityMgr = (ActivityManager) context
						.getSystemService(Context.ACTIVITY_SERVICE);
				activityMgr.restartPackage(context.getPackageName());
			} else {
				try {
					ActivityManager activityMgr = (ActivityManager) context
							.getSystemService(Context.ACTIVITY_SERVICE);
					activityMgr.killBackgroundProcesses(context
							.getPackageCodePath());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			System.exit(0);
		} catch (Exception e) {
		}
	}
}