package com.szc.fast_express_system.ui;

import com.szc.fast_express_system.common.util.IntentUtil;
import com.szc.fast_express_system.service.AppController;
import com.szc.fast_express_system.service.AppManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


/******************************************
 * 类描述： �?有Activity的父�? 
 * 类名称：BaseActivity
 * @version: 1.0
 * @author: why
 * @time: 2014-3-19 下午5:22:32
 ******************************************/
public class BaseActivity extends Activity {
	//InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//添加Activity到堆�?
		AppManager.getAppManager().addActivity(this);
	}
	
	public void goBack(View view) {
		this.finish();  
		IntentUtil.popFromLeft(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		AppController.getController(this);
	}

	@Override
	public void onBackPressed() {
		this.finish();
		IntentUtil.popFromLeft(this);
	}

	
/*	 (non-Javadoc)
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			System.out.println("down");
			if (BaseActivity.this.getCurrentFocus() != null) {
				if (BaseActivity.this.getCurrentFocus().getWindowToken() != null) {
					imm.hideSoftInputFromWindow(BaseActivity.this.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		return super.onTouchEvent(event);
	}*/

	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}
}
