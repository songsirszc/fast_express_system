package com.szc.fast_express_system.ui.fragment;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.szc.fast_express_system.service.AppContext;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

/**
 * ��Application
 */
public class LocationApplication extends Application {
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	
	public TextView mLocationResult,logMsg,mLocationResult1;
	public TextView trigger,exit;
	public Vibrator mVibrator;
	public AppContext context;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		
		
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
	}

	
	/**
	 * ʵ��ʵλ�ص�����
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			StringBuffer la = new StringBuffer(256);
			StringBuffer lon = new StringBuffer(256);
			/*double []du=new double[1];
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());*/
		//	la.append("\nlatitude : ");
			la.append(location.getLatitude());
		//	lon.append("\nlontitude : ");
			lon.append(location.getLongitude());
		/*	double la=location.getLatitude();
			double lon=location.getLongitude();
			context.addBusinessData("Latitude", la);
			context.addBusinessData("Longitude", lon);
			
			du[0]=la;
			du[1]=lon;*/
			/*sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation){
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				//��Ӫ����Ϣ
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
				
			}*/
			logMsg(la.toString(),lon.toString());
			Log.i("BaiduLocationApiDem", la.toString());
		}

	}
	

	/**
	 * ��ʾ�����ַ�
	 * @param str
	 */
	public void logMsg(String str,String str1) {
		try {
			
				mLocationResult.setText(str);
				mLocationResult1.setText(str1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �߾��ȵ���Χ���ص�
	 * @author jpren
	 *
	 */
	
}
