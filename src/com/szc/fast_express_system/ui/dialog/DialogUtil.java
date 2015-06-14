package com.szc.fast_express_system.ui.dialog;

import com.szc.fast_express_system.ExpressSystemApplication;
import com.szc.fast_express_system.common.util.IntentUtil;
import com.szc.fast_express_system.common.util.ProgressDialogUtil;
import com.szc.fast_express_system.common.util.ToastUtil;
import com.szc.fast_express_system.service.AppController;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;



/**
 * **************************************** 
 * 类描述： 对话框统�?管理 类名称：DialogUtil
 * @version: 1.0
 * @author: GHZ
 * @time: 2014-5-8 下午5:43:09
 ***************************************** 
 */
public class DialogUtil {
	
	/**
	  * 方法描述：�??出账�?
	  * @param activity
	  * @author: why
	  * @time: 2014-8-13 下午7:43:12
	  */
	public static void showExit(Activity activity,final Button submit) {
		final IOSStyleDialog dialog = new IOSStyleDialog(activity, IOSStyleDialog.DIALOG_TWO);
		dialog.setMessage("确定�?出账号？");
		dialog.setLeft("确定", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.closeDialog();
				// 对话�?
				ExpressSystemApplication.getInstance().setLogin(false);
				submit.setText("登录");
			}
		});
		dialog.setRight("取消", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.closeDialog();
			}
		});
	}

	
	/**
	  * 方法描述：单个按�?
	  * @author: wanghy
	  * @time: 2014-10-12 下午5:22:19
	  */
	public static void showMessage(Context context,
			String message) {
		final IOSStyleDialog dialog = new IOSStyleDialog(context, IOSStyleDialog.DIALOG_ONE);
		dialog.setMessage(message);
		dialog.setOne("确认",new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.closeDialog();
			}
		});
	}
	
	/**
	  * 方法描述：左右两个按�?
	  * @param activity
	  * @author: why
	  * @time: 2014-8-13 下午7:43:12
	  */
	public static void showMessageTwo(final Context context,String message,final String toast) {
		final IOSStyleDialog dialog = new IOSStyleDialog(context, IOSStyleDialog.DIALOG_TWO);
		dialog.setMessage(message);
		dialog.setLeft("取消", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.closeDialog();
			}
		});
		dialog.setRight("确定", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.closeDialog();
				// 对话�?
				ToastUtil.showToast(context, toast, ToastUtil.LENGTH_LONG);
			}
		});
	}


	
	/**
	  * 方法描述：TODO
	  * @param currentActivity
	  * @author: why
	  * @time: 2014-12-5 下午4:49:49
	  */
	public static void messageAccess(final Activity context) {

		final IOSStyleDialog dialog = new IOSStyleDialog(context, IOSStyleDialog.DIALOG_TWO);
		dialog.setMessage("您不在对方的好友列表,对话�?要支付星�?!");
		dialog.setLeft("取消", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.closeDialog();
			}
		});
		dialog.setRight("确定", new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.closeDialog();
				ProgressDialogUtil.showProgressDialog(context, "支付中�??", false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						ProgressDialogUtil.closeProgressDialog();
					}
				}).start();
			}
		});
	}
	
	/**
	  * 方法描述：TODO
	  * @param context
	  * @author: why
	  * @time: 2014-12-5 下午4:57:46
	  */
	public  static void messagePayN(final Activity context) {
		final IOSStyleDialog dialog = new IOSStyleDialog(context, IOSStyleDialog.DIALOG_TWO);
		dialog.setMessage("您的星币余额不足是否充�??!");
		dialog.setLeft("取消", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.closeDialog();
			}
		});
		
		dialog.setRight("确定", new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.closeDialog();
				// 充�??
			}
		});
	}
}
