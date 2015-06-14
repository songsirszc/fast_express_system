package com.szc.fast_express_system.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szc.fast_express_system.R;
import com.szc.fast_express_system.entities.LoginData;
import com.szc.fast_express_system.entities.OrderData;
import com.szc.fast_express_system.service.AppContext;
import com.szc.fast_express_system.service.AppController;

public class AcceptorderFragment extends Fragment implements OnClickListener{
	private View view;
	private Context mContext;
	private AppController controller;
	private ListView list;
	private TextView btshaixuan;
	private TextView tvtitle;
	private TextView btsearch;
	private ImageButton btback;
	
	
	private int pageNum = 1;
	private int  tolalPage = -1;
	private boolean refresh = false;

	public String search = "";
	public String lat = "116.287128";
	public String lng = "39.830486";
	public String sex = "";
	public String age_min = "0";
	public String age_max = "80";
	public String type = "star";
	public String major = "";
	public String ListStr[]={};
	LoginData user;
	private AppContext context;
	int r = 0;
	boolean showupdate = false;
	private String username;
	boolean run = true;
	private String[] from;
	private OrderData u;
	private ListView lv;
	protected String state;
	private android.app.AlertDialog.Builder dialog;
	private LayoutInflater inflater;
	private EditText art;
	private EditText weig;
	private Button bt1;
	private Button bt2;

	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		controller = AppController.getController(getActivity());
		view = inflater.inflate(R.layout.acceptlist, container, false);
		mContext = getActivity();
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
						acceptorder();
						findViewHandler.sendEmptyMessage(2234);
						sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			};
		}.start();
		
//		findView();
		initView();
		return view;
	}


	private void acceptorder() {
		// TODO Auto-generated method stub	
			controller.acceptorder();
	
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
		
		lv = (ListView)view.findViewById(R.id.acceptlist);
		SimpleAdapter adapter = new SimpleAdapter(mContext,data,R.layout.item_listview,
				from,
				new int[]{R.id.textView02,R.id.textView03,R.id.textView04,R.id.textView05,R.id.textView01,R.id.textViewstate,R.id.ordernum});
		adapter.notifyDataSetChanged();
		lv.setAdapter(adapter);
		
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

	
}
