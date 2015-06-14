package com.szc.fast_express_system.ui.fragment;

import java.util.Arrays;
import java.util.List;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.szc.fast_express_system.R;
import com.szc.fast_express_system.common.util.IntentUtil;
import com.szc.fast_express_system.entities.LoginData;
import com.szc.fast_express_system.entities.OrderData;
import com.szc.fast_express_system.service.AppController;



import com.szc.fast_express_system.ui.GuideActivity;
import com.szc.fast_express_system.ui.MainFragmentActivity;
import com.szc.fast_express_system.ui.login.LoginActivity;
import com.szc.fast_express_system.ui.login.RegistActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class UserModleFragment extends Fragment implements OnClickListener, OnRefreshListener{
		private static int CurrentTab;
	private AppController controller;
	private View view;
	private static final int REFRESH_COMPLETE = 0X110;
	private LoginData user;
	private String[] items;
	private TextView adress_text;
	private TextView lon_text;
	private Button submit;
	private Button adress_button;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor="gcj02";
	private LocationClient mLocationClient;
	private TextView username;
	private String username2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		controller = AppController.getController(getActivity());
		view = inflater.inflate(R.layout.usermodle_fragment, container, false);
		getActivity();
		mLocationClient = ((LocationApplication)getActivity().getApplication()).mLocationClient;
		user = (LoginData) controller.getContext().getBusinessData("loginData");
		username2 =  (String) controller.getContext().getBusinessData("user.account");
		username = (TextView)view.findViewById(R.id.textView3);
		username.setText(username2);
		findView();
		initView();
		mLocationClient.stop();
		return view;
	}
	private void initView() {
		
	}



	/**
	 * 方法描述：TODO
	 * 
	 * @author: why
	 * @time: 2014-10-10 下午6:36:00
	 */
	private void findView() {
			
			adress_text = (TextView)view.findViewById(R.id.textView11);
	    	lon_text = (TextView)view.findViewById(R.id.textView12);
	    	((LocationApplication)getActivity().getApplication()).mLocationResult = adress_text;
			((LocationApplication)getActivity().getApplication()).mLocationResult1 = lon_text;
			//adress_button.setOnClickListener(this);
			InitLocation();
			mLocationClient.start();
			
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		
	}
	private void InitLocation() {
		// TODO Auto-generated method stub
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);//设置定位模式
		option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
		int span=1000;
		option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}
	public static void setCurrentTab(int currentTab) {
		CurrentTab = currentTab;
	}

	/**
	 * @return currentTab : return the property currentTab.
	 */
	public static int getCurrentTab() {
		return CurrentTab;
	}
	
	public void onRefresh()
	{
		// Log.e("xxx", Thread.currentThread().getName());
		// UI Thread


	}
}
