package com.szc.fast_express_system.ui;

import com.szc.fast_express_system.service.AppManager;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


/**
 * 应用程序Activity的基�?
 * @author
 * @version 
 * @created 
 */
public class BaseFragmentActivity extends FragmentActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//添加Activity到堆�?
		AppManager.getAppManager().addActivity(this);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}
}
