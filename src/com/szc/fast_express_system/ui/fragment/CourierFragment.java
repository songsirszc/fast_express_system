package com.szc.fast_express_system.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szc.fast_express_system.R;
import com.szc.fast_express_system.common.util.ProgressDialogUtil;
import com.szc.fast_express_system.entities.LoginData;
import com.szc.fast_express_system.entities.OrderData;
import com.szc.fast_express_system.service.AppContext;
import com.szc.fast_express_system.service.AppController;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CourierFragment extends Fragment  implements OnClickListener{
	private View view;
	private Context mContext;
	private AppController controller;
	public String search = "";
	public String lat = "116.287128";
	public String lng = "39.830486";
	public String sex = "";
	public String age_min = "0";
	public String age_max = "80";
	public String type = "star";
	public String major = "";
	public String state = "";
	public String ListStr[]={};
	LoginData user;
	private AppContext context;
	int r = 0;
	boolean showupdate = false;
	private String username;
	boolean run = true;
	
	private String[] from;
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor="gcj02";
	private LocationClient mLocationClient;
	private TextView adress_text;
	private TextView lon_text;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		controller = AppController.getController(getActivity());
		
		view = inflater.inflate(R.layout.main_layout, container, false);
		mContext = getActivity();
    	initView();
    	adress_text = (TextView)view.findViewById(R.id.textViewla);
    	lon_text = (TextView)view.findViewById(R.id.textViewlon);
    	adress_text.setVisibility(view.INVISIBLE);
    	lon_text.setVisibility(view.INVISIBLE);
		user = (LoginData) controller.getContext().getBusinessData("loginData");
		username =  (String) controller.getContext().getBusinessData("user.account");
		from = new String[] { "username", "phone", "adress" ,"article","weight","state","ordernum"}; 
		final Handler findViewHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.what == 2234){
					findView();
				}
			}
			
		};
		
		new Thread(){
			public void run() {
				while(run){
					try {
						mLocationClient = ((LocationApplication)getActivity().getApplication()).mLocationClient;
						
				    	((LocationApplication)getActivity().getApplication()).mLocationResult = adress_text;
						((LocationApplication)getActivity().getApplication()).mLocationResult1 = lon_text;
						InitLocation();
						mLocationClient.start();
						mLocationClient.stop();
						String la=adress_text.getText().toString();
						String lon=lon_text.getText().toString();
					
						OrderData u1=new OrderData();
						u1.setLatitude(la);
						u1.setLontitude(lon);
						controller.getContext().addBusinessData("jingwei.Data", u1);
						getallorder();
						findViewHandler.sendEmptyMessage(2234);
						sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			};
		}.start();
		
//		
		
		
		return view;
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


	private void getallorder() {
		// TODO Auto-generated method stub	
			controller.getallorder();
	
	}

	/**
	 * 方法描述：TODO
	 * 
	 * @author: why
	 * @time: 2014-10-10 下午6:36:02
	 */
	private void initView() {
	//	String type=(String)context.getBusinessData("user.type");
		((TextView)view.findViewById(R.id.main_top_title)).setText(username);
		
	}



	/**
	 * 方法描述：TODO
	 * 
	 * @author: why
	 * @time: 2014-10-10 下午6:36:00
	 */
	private void findView() {
		String	orderlist =  (String) controller.getContext().getBusinessData("order.list");

		Gson gson = new Gson();
		 List<OrderData> retList = gson.fromJson(orderlist,  
	                new TypeToken<List<OrderData>>() {  
	                }.getType()); 
		 ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();  
		  for (int i = 0; i < retList.size(); i++) {  
	            HashMap<String, Object> map = new HashMap<String, Object>();  
	      
	            map.put(from[0], retList.get(i).getUsername());  
	            map.put(from[1], retList.get(i).getPhone());  
	            map.put(from[2], retList.get(i).getAdress());  
	            map.put(from[3], retList.get(i).getArticle()); 
	            map.put(from[4], retList.get(i).getWeight()); 
	            map.put(from[5], retList.get(i).getState());
	            map.put(from[6], retList.get(i).getOrdernum());
	            data.add(map);  
	        }  
	//	List<HashMap<String, Object>> data=new ArrayList<HashMap<String,Object>>(); 
		
		final ListView lv = (ListView)view.findViewById(R.id.listView1);
		SimpleAdapter adapter = new SimpleAdapter(mContext,data,R.layout.item_listview,
				from,
				new int[]{R.id.textView02,R.id.textView03,R.id.textView04,R.id.textView05,R.id.textView01,R.id.textViewstate,R.id.ordernum});
		adapter.notifyDataSetChanged();
		lv.setAdapter(adapter);
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				 @SuppressWarnings("unchecked")
				HashMap<String,String> map=(HashMap<String,String>)lv.getItemAtPosition(arg2); 
	                String username=map.get("username"); 
	                String phone=map.get("phone"); 
	                String adress=map.get("adress"); 
	                String article=map.get("article"); 
	                String weight=map.get("weight"); 
	                
	                OrderData user=new OrderData();
	                 user.setUsername(username);
	                 user.setPhone(phone);
	                 user.setAdress(adress);
	                 user.setArticle(article);
	                 user.setWeight(weight);
	                 controller.getContext().addBusinessData("UserOrder.Data", user);
	                 registerForContextMenu(lv);
				return false;
			} 
		});
	}
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
			super.onCreateContextMenu(menu, v, menuInfo);
			menu.add(0,0,0,"接受");
			menu.add(0,1,0,"拒绝");
			}
	public boolean onContextItemSelected(MenuItem aItem) {
		ContextMenuInfo menuInfo = (ContextMenuInfo) aItem.getMenuInfo();
		/* Switch on the ID of the item, to get what the user selected. */
		switch (aItem.getItemId()) {
		case 0:
			state="accept";
			OrderData user=new OrderData();
            user.setState(state);
            controller.getContext().addBusinessData("Userstate.Data", user);
			sendstate();
		}
		return run;
	}
	private void sendstate() {
		// TODO Auto-generated method stub
		new Thread(new Runnable(){
			public void run() {
				controller.sendstate();
			}
		}).start();
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.main_top_right:
			break;
	//	case R.id.main_top_right2:
		//	AlertDialog.Builder builder = new AlertDialog.Builder(this); 
	    //    builder.setTitle("Prompt"); 
		default:
			break;
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				break;
			}
		}
	};
}
