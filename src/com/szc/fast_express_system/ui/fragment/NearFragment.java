package com.szc.fast_express_system.ui.fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import m.framework.ui.widget.pulltorefresh.PullToRefreshView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szc.fast_express_system.R;
import com.szc.fast_express_system.common.util.IntentUtil;
import com.szc.fast_express_system.common.util.ProgressDialogUtil;
import com.szc.fast_express_system.entities.LoginData;
import com.szc.fast_express_system.entities.OrderData;
import com.szc.fast_express_system.service.AppContext;
import com.szc.fast_express_system.service.AppController;
import com.szc.fast_express_system.ui.adapter.listViewAdapter;
import com.szc.fast_express_system.ui.dialog.EditDialogFragment;

import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


/**
 * **************************************** 类描述： 附近 类名称：NearFragment
 * 
 * @version: 1.0
 * @author: why
 * @time: 2014-10-10 下午6:30:07
 ***************************************** 
 */
public class NearFragment extends Fragment  implements OnClickListener {
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

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		controller = AppController.getController(getActivity());
		view = inflater.inflate(R.layout.main_layout, container, false);
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
						getorder();
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


	private void getorder() {
		// TODO Auto-generated method stub	
			controller.getorder();
	
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
		
		lv = (ListView)view.findViewById(R.id.listView1);
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
	                String ordernum=map.get("ordernum");
	                 state=map.get("state");
	                
	                OrderData user=new OrderData();
	                 user.setUsername(username);
	                 user.setOrdernum(ordernum);
	                 user.setState(state);
	                 controller.getContext().addBusinessData("UserOrder.Data", user);
	          
	        		registerForContextMenu(lv);   
				return false;
			} 
		});
	}
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
			super.onCreateContextMenu(menu, v, menuInfo);
			menu.add(0,0,0,"修改订单");
			menu.add(0,1,0,"取消订单");
			}
	public boolean onContextItemSelected(MenuItem aItem) {
		ContextMenuInfo menuInfo = (ContextMenuInfo) aItem.getMenuInfo();
		
		/* Switch on the ID of the item, to get what the user selected. */
		switch (aItem.getItemId()) {
		case 0:	
			if(state.equals("accept")){
				Toast.makeText(mContext, "you can not modify", Toast.LENGTH_SHORT).show();
			}else if(state.equals("NoAccept")){
			//	Toast.makeText(mContext, "you can not modify", Toast.LENGTH_SHORT).show();
				modify(view);
			}
			break;
		case 1:
			deleteOrder();
		}
		
		return run;
	}
	private void deleteOrder() {
		// TODO Auto-generated method stub
		new Thread(new Runnable(){
			public void run() {
				controller.deleteOrder();
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
	}


	private void modify(View view) {
		// TODO Auto-generated method stub
	
		 EditDialogFragment editNameDialog = new EditDialogFragment();
		
	        editNameDialog.show(getFragmentManager(), "wupin"); 
	       
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
