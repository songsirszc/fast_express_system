package com.szc.fast_express_system.ui.login;



import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.szc.fast_express_system.R;
import com.szc.fast_express_system.common.util.IntentUtil;
import com.szc.fast_express_system.common.util.ProgressDialogUtil;
import com.szc.fast_express_system.common.util.ToastUtil;
import com.szc.fast_express_system.entities.Register;
import com.szc.fast_express_system.service.AppController;
import com.szc.fast_express_system.ui.BaseActivity;;

public   class RegistActivity extends BaseActivity implements OnClickListener {
	
	
	private Button bt_regist;
	private EditText regist_username;
	private EditText regist_password;
	private TextView main_top_right;
	private EditText reregist_password;
	private Spinner spinner;
	 String  selected="0";
	 String[] users = {"Users","Courier","Admin"};
	
	protected AppController controller;
	 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_layout);
		controller = AppController.getController(this);
		
		findview();	
		initview();
	
		
	}
	private void findview() {
		// TODO Auto-generated method stub
		//得到所有的视图插件
		bt_regist = (Button)findViewById(R.id.regist_button);	
		main_top_right = (TextView)findViewById(R.id.main_top_right);
	    regist_username = (EditText)findViewById(R.id.regist_username);
		regist_password = (EditText)findViewById(R.id.regist_password1);
		reregist_password = (EditText)findViewById(R.id.regist_password);
		spinner =(Spinner)findViewById(R.id.spingarr);
		
	}


	private void initview() {
		// TODO Auto-generated method stub
		((TextView)findViewById(R.id.main_top_title)).setText("用户注册");
		main_top_right.setVisibility(View.VISIBLE);
		main_top_right.setText("");
		bt_regist.setOnClickListener(this);
	
		 initMySpinner();
		
	}

	//下拉菜单
	private void initMySpinner() {
		// TODO Auto-generated method stub
		
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,  android.R.layout. simple_spinner_item,
                users);
        adapter.setDropDownViewResource(android.R.layout.test_list_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt("身份选择");
        spinner.setSelection(0, true);
        spinner.setOnItemSelectedListener(new SpinnerOnSelectedListener());
    }
    
    class SpinnerOnSelectedListener implements OnItemSelectedListener{

    	public void onItemSelected(AdapterView<?> adapterView, View view, int position,
                long id) {
            // TODO Auto-generated method stub 
    		 String user = spinner.getItemAtPosition(position).toString(); 
        }

        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
            System.out.println("selected===========>" + "Nothing");
        }
    }

    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.regist_button){
			if (invaild()) {
				regist();
		//		IntentUtil.intent(RegistActivity.this, LoginActivity.class);
		}
		
	}
	}	
    





	private boolean invaild() {
		// TODO Auto-generated method stub
		// 页面判定
		
		String account = regist_username.getText().toString().trim();
		String password = regist_password.getText().toString().trim();
		String pwd = reregist_password.getText().toString().trim();
		
		if(account.equals("")||account.length()>20||account.length()<4){
			ToastUtil.showToast(this, "用户名不符", ToastUtil.LENGTH_LONG);
			return false;
		} 
		
		if (password.equals("")||password.length()>20||password.length()<4) {
			ToastUtil.showToast(this, "密码不符", ToastUtil.LENGTH_LONG);
			return false;
		}
		if(!password.equals(pwd)){
			Toast.makeText(RegistActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
		
		

	private void regist() {
		// TODO Auto-generated method stub
		String account = regist_username.getText().toString().trim();
		String password = regist_password.getText().toString().trim();
		String type= spinner.getSelectedItem().toString().trim();
		Register u=new Register();
		u.setUsername(account);
		u.setPassword(password);
		u.setType(type);
		controller.getContext().addBusinessData("user.register", u);
		ProgressDialogUtil.showProgressDialog(this, "通信中", false);
		new Thread(new Runnable(){
			public void run() {
				controller.register();
				ProgressDialogUtil.closeProgressDialog();
			}
		}).start();
     
	}
	
      
    


	


	
   
}
