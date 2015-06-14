package com.szc.fast_express_system.ui.fragment;


import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.szc.fast_express_system.R;
import com.szc.fast_express_system.common.util.ProgressDialogUtil;
import com.szc.fast_express_system.entities.LoginData;
import com.szc.fast_express_system.entities.OrderData;
import com.szc.fast_express_system.service.AppController;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OrderFragment extends Fragment implements OnClickListener{
	private static int CurrentTab;
	private AppController controller;
	private View view;
	private FragmentActivity mContext;
	private LoginData user;
	private Button submit;
	private EditText order_usertext;
	private EditText order_phonetext;
	private EditText order_adresstext;
	private EditText order_thingstext;
	private EditText order_weighttext;
	private TextView adress_text;
	private Button adress_button;
	private LocationClient mLocationClient;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor="gcj02";
	private TextView lon_text;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		controller = AppController.getController(getActivity());
		mLocationClient = ((LocationApplication)getActivity().getApplication()).mLocationClient;
		view = inflater.inflate(R.layout.myorder_fragment, container, false);
		
		mContext = getActivity();
		user = (LoginData) controller.getContext().getBusinessData("loginData");
	//	double lon=mLocationClient.
		findView();
		initView();
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
		submit = (Button)view.findViewById(R.id.order_submit);
		order_usertext = (EditText)view.findViewById(R.id.order_usertext);
		order_phonetext = (EditText)view.findViewById(R.id.order_phonetext);
		order_adresstext = (EditText)view.findViewById(R.id.order_adresstext);
		order_thingstext = (EditText)view.findViewById(R.id.order_thingstext);
		order_weighttext = (EditText)view.findViewById(R.id.order_weighttext);
		adress_text = (TextView)view.findViewById(R.id.textView3);
		lon_text = (TextView)view.findViewById(R.id.textView8);
		((LocationApplication)getActivity().getApplication()).mLocationResult = adress_text;
		((LocationApplication)getActivity().getApplication()).mLocationResult1 = lon_text;
		InitLocation();
		mLocationClient.start();
		mLocationClient.stop();
	//	adress_button = (Button)view.findViewById(R.id.adress_button);
		//adress_button.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.order_submit:
			submit();
			break;	
		/*case R.id.adress_button:
			
			if(adress_button.getText().equals(getString(R.string.startlocation))){
				
				adress_button.setText(getString(R.string.stoplocation));
			}else{
				
				adress_button.setText(getString(R.string.startlocation));
			}
			break;	*/
		}
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
	
	private void submit() {
		// TODO Auto-generated method stub
		String username=order_usertext.getText().toString();
		String phonetext=order_phonetext.getText().toString();
		String adresstext=order_adresstext.getText().toString();
		String thingstext=order_thingstext.getText().toString();
		String weighttext=order_weighttext.getText().toString();
		String la=adress_text.getText().toString();
		String lon=lon_text.getText().toString();
		/*double la=Double.parseDouble(adress_text.getText().toString());
		double lon=Double.parseDouble(lon_text.getText().toString());*/
		
		OrderData u=new OrderData();
		u.setUsername(username);
		u.setPhone(phonetext);
		u.setAdress(adresstext);
		u.setArticle(thingstext);
		u.setWeight(weighttext);
		u.setLatitude(la);
		u.setLontitude(lon);
		controller.getContext().addBusinessData("Order.Data", u);
		new Thread(new Runnable(){
			public void run() {
				controller.submit();
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}
	
}
