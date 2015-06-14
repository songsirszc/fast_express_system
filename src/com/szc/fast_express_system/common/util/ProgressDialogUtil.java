package com.szc.fast_express_system.common.util;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtil {
	
	private static ProgressDialog progressDialog = null;

	public static ProgressDialog showProgressDialog(Context context, int msgId, boolean flagCancelable){
		return showProgressDialog(context, context.getString(msgId), flagCancelable);
	}
	
	public static ProgressDialog showProgressDialog(Context context, String msg, boolean flagCancelable){
		closeProgressDialog();
		// 联网进度�?
		progressDialog = new ProgressDialog(context);
		// 设置风格为圆形进度条
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 设置进度条是否为不明�?
		progressDialog.setIndeterminate(false);
		// 设置进度条是否可以按�?回键取消
		progressDialog.setCancelable(flagCancelable);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setMessage(msg);
		progressDialog.show();
		return progressDialog;
	}
	
	public static boolean closeProgressDialog(){
		if(progressDialog != null && progressDialog.isShowing()){
			progressDialog.cancel();
			progressDialog = null;
			return true;
		}
		return false;
	}
	
	
	public static boolean isShowing(){
		if(progressDialog != null && progressDialog.isShowing()){
			return true;
		}
		return false;
	}
}
