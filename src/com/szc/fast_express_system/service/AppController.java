package com.szc.fast_express_system.service;

import java.util.List;

import org.apache.http.protocol.ResponseDate;

import com.szc.fast_express_system.common.exception.BusinessException;
import com.szc.fast_express_system.common.util.IntentUtil;
import com.szc.fast_express_system.common.util.ToastUtil;
import com.szc.fast_express_system.entities.json.ReturnValue;
import com.szc.fast_express_system.service.impl.AppServiceImpl;
import com.szc.fast_express_system.ui.CourierActivity;
import com.szc.fast_express_system.ui.MainFragmentActivity;
import com.szc.fast_express_system.ui.dialog.DialogUtil;
import com.szc.fast_express_system.ui.login.LoginActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;


/******************************************
 * 类描述： 控制�? 该类保存客户端控制器�? 类名称：AppController
 * 
 * @version: 1.0
 * @author: why
 * @time: 2014-2-13 下午2:09:22
 ******************************************/

public class AppController {
	/**
	 * 客户端上下文，该对象用来缓存客户端业务数据和参数配置
	 */
	private AppContext context;
	/** 服务对象 **/
	private AppService service;

	public AppContext getContext() {
		return context;
	}

	public void setContext(AppContext context) {
		this.context = context;
	}

	/***
	 * 控制器单例对�?
	 */
	private static AppController controller = null;

	/***
	 * 当前android的活动对�?
	 */
	private Activity currentActivity;

	private AppController(Activity act) {
		this.currentActivity = act;
		createContext();
		service = new AppServiceImpl(context);
	}

	/**
	 * 初始化客户端配置信息
	 */
	private void createContext() {
		context = new AppContext();
		// context.setConfigProerties(getProperties());
	}

	/**
	 * 得到单例的controller对象
	 * 
	 * @return
	 */
	public synchronized static AppController getController() {
		if (controller == null) {
			controller = new AppController(null);
		}
		return controller;
	}

	/**
	 * 得到单例controller对象，并设置当前controller当前关联的Activity活动对象
	 */
	public synchronized static AppController getController(Activity act) {
		if (controller == null) {
			controller = new AppController(act);
		} else {
			controller.setCurrentActivity(act);
		}
		return controller;
	}

	public Activity getCurrentActivity() {
		return currentActivity;
	}

	public void setCurrentActivity(Activity currentActivity) {
		this.currentActivity = currentActivity;
	}
	private static final int HANDLER_DIALOG = 0; //弹对话框
	public static final int HANDLER_TOAST = 1; // 吐司 专用
	private static final int HANDLER_UPDATE = 2; // 更新
	private static final int HANDLER_UPDATE_ABOUT = 3; // 更新 错误信息由提�? about
	
	private static final int MESSAGE_ACCESS = 4;// 不能聊天对话�?
	private static final int MESSAGE_PAY = 5;// 提示支付聊天费用
	private Handler accHandler; // 账户页面专用 Handler

	public void setAccHandler(Handler accHandler) {
		this.accHandler = accHandler;
	}

	public Handler getAccHandler() {
		return accHandler;
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			
			case HANDLER_DIALOG:
				if(!TextUtils.isEmpty(msg.obj.toString())){
					DialogUtil.showMessage(currentActivity,msg.obj.toString());
				}
				break;
			case HANDLER_TOAST:
				if(!TextUtils.isEmpty(msg.obj.toString())){
					ToastUtil.showToast(currentActivity, msg.obj.toString(), ToastUtil.LENGTH_LONG);
				}
				break;
			case MESSAGE_ACCESS:
				DialogUtil.messageAccess(currentActivity);
				break;
			case MESSAGE_PAY:
				DialogUtil.messagePayN(currentActivity);
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 方法描述：登录业务
	 * 
	 * @author: why
	 * @time: 2014-2-17 下午5:51:11
	 */
	public void login() {
		try {
			service.login(handler);
			String loginMsg=(String)context.getBusinessData("return.login");
			String loginType=(String)context.getBusinessData("user.type");
			if(loginMsg.equals("success")){
				
				if(loginType.equals("Users")){
					IntentUtil.intent(currentActivity, MainFragmentActivity.class);
					handler.obtainMessage(HANDLER_TOAST, "登录成功").sendToTarget();
					
				}else if(loginType.equals("Courier")){
					IntentUtil.intent(currentActivity, CourierActivity.class);
					handler.obtainMessage(HANDLER_TOAST, "登录成功").sendToTarget();
					
				}
				
			}else if(loginMsg.equals("failed")){
				//false
				handler.obtainMessage(HANDLER_TOAST, "用户名或密码不正确").sendToTarget();
			}else{
				handler.obtainMessage(HANDLER_TOAST, "没有连接到网络").sendToTarget();
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			handler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	  * 方法描述：TODO
	  * @param alterHandler
	  * @author: why
	  * @time: 2014-10-11 下午3:39:55
	  */
	public void setAlterHandler(Handler alterHandler) {
		
	}

	
	/**
	  * 方法描述：TODO
	  * @author: wanghy
	  * @time: 2014-10-23 下午11:24:31
	  */
	public void register() {
		try {
			service.register(handler);
			String registMsg=(String)context.getBusinessData("return.regist");
			if(registMsg.equals("success")){
				handler.obtainMessage(HANDLER_TOAST, "注册成功").sendToTarget();
				IntentUtil.intent(currentActivity, LoginActivity.class);
			}else if(registMsg.equals("failed")){
				//false
				handler.obtainMessage(HANDLER_TOAST, "注册失败");
			}
			
			
		} catch (BusinessException e) {
			e.printStackTrace();
			handler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void submit() {
		// TODO Auto-generated method stub
		try {
			service.submit(handler);
			String registMsg=(String)context.getBusinessData("order.rsp");
			if(registMsg.equals("success")){
				handler.obtainMessage(HANDLER_TOAST, "提交成功").sendToTarget();
				
			}else if(registMsg.equals("failed")){
				//false
				handler.obtainMessage(HANDLER_TOAST, "提交失败").sendToTarget();
			}
			
			
		} catch (BusinessException e) {
			e.printStackTrace();
			handler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getorder() {
		// TODO Auto-generated method stub
		try {
			service.getorder(handler);
			
		} catch (BusinessException e) {
			e.printStackTrace();
			handler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getallorder() {
		// TODO Auto-generated method stub
		try {
			service.getallorder(handler);
			
		} catch (BusinessException e) {
			e.printStackTrace();
			handler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendstate() {
		// TODO Auto-generated method stub
		try {
			service.sendstate(handler);
			
		} catch (BusinessException e) {
			e.printStackTrace();
			handler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void orderchange() {
		// TODO Auto-generated method stub
		try {
			service.orderchange(handler);
			
		} catch (BusinessException e) {
			e.printStackTrace();
			handler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void deleteOrder() {
		// TODO Auto-generated method stub
		try {
			service.deleteOrder(handler);
			
		} catch (BusinessException e) {
			e.printStackTrace();
			handler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void acceptorder() {
		// TODO Auto-generated method stub
		try {
			service.acceptorder(handler);
			
		} catch (BusinessException e) {
			e.printStackTrace();
			handler.obtainMessage(HANDLER_TOAST, e.getErrorMessage().getMessage()).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
