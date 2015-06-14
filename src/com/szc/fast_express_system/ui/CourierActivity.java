package com.szc.fast_express_system.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.TabSpec;

import com.szc.fast_express_system.R;
import com.szc.fast_express_system.service.AppController;
import com.szc.fast_express_system.service.AppManager;
import com.szc.fast_express_system.ui.fragment.AcceptorderFragment;
import com.szc.fast_express_system.ui.fragment.CourierFragment;
import com.szc.fast_express_system.ui.fragment.UserModleFragment;

public class CourierActivity extends BaseFragmentActivity{
	public static int CurrentTab = 0; // 当前活动�?

	private AppController controller;
	private FragmentTabHost fTabHost;
	private RadioGroup tabRg;
	// fragments
	private Class<?>[] fragments = { CourierFragment.class,AcceptorderFragment.class,UserModleFragment.class};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.courierlayout);
		controller = AppController.getController(this);
		findView();
		Log.i("时间戳", System.currentTimeMillis()+"");
		initView();
	}

	/**
	 * 方法描述：findVIew
	 * 
	 * @author: why
	 * @time: 2014-4-3 上午11:45:16
	 */
	private void findView() {
		fTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabRg = (RadioGroup) findViewById(R.id.cu_tab_menu);
	
	}
		
	/**
	 * 方法描述：TODO
	 * 
	 * @author: why
	 * @time: 2014-9-20 下午2:27:09
	 */
	private void initView() {
		
		fTabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);
		int count = fragments.length;
		for (int i = 0; i < count; i++) {
			TabSpec tabSpec = fTabHost.newTabSpec(i + "").setIndicator(i + "");
			fTabHost.addTab(tabSpec, fragments[i], null);
		}
		fTabHost.setCurrentTab(CurrentTab);
		tabRg.setOnCheckedChangeListener(tabListener);
	}

	private OnCheckedChangeListener tabListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {

			switch (checkedId) {
			case R.id.tab_1:
				MainFragmentActivity.setCurrentTab(0);
				fTabHost.setCurrentTab(0);
				break;
			case R.id.tab_2:
				MainFragmentActivity.setCurrentTab(1);
				fTabHost.setCurrentTab(1);
				break;
			case R.id.tab_5:
				MainFragmentActivity.setCurrentTab(2);
				fTabHost.setCurrentTab(2);
				break;
			
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		setTabValue();
	}

	/**
	 * @param currentTab
	 *            : set the property currentTab.
	 */
	public static void setCurrentTab(int currentTab) {
		CurrentTab = currentTab;
	}

	/**
	 * @return currentTab : return the property currentTab.
	 */
	public static int getCurrentTab() {
		return CurrentTab;
	}

	/**
	 * 方法描述：设置的�?
	 * 
	 * @author: why
	 * @time: 2014-4-30 下午6:03:36
	 */
	private void setTabValue() {
		fTabHost.setCurrentTab(CurrentTab);

		switch (CurrentTab) {
		case 0:
			tabRg.check(R.id.tab_rb_1);
			break;
		case 1:
			tabRg.check(R.id.tab_rb_2);
			break;
		
		default:
			break;
		}
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下键盘上返回按�?
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击�?出函�?
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	/**
	 * 双击�?出函�?
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备�?�?
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消�?�?
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任�?

		} else {
		//	this.finish();
			// �?出应用程�?
		AppManager.getAppManager().AppExit(this);
		}
	}
}