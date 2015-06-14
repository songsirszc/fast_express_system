package com.szc.fast_express_system.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.RemoteViews;

public class Downloadapk {
	// 应用程序Context
	private Context mContext;
	// 下载安装包的网络路径

	String url = null;
	String title = null;
	public boolean ifinstall = false;
	public String apkname = null;
	public String label = null;
	public Drawable icon = null;
	public int startat = 0;
	private String apkUrl = url;
	private String savePath = "/sdcard/timetalent/apps/";// 保存apk的文件夹
	String saveFileName = savePath + apkname;
	public int fileSize = 0;
	boolean redownload = false;
	// 进度条与通知UI刷新的handler和msg常量
	int progress = 0;// 当前进度
	private Thread downLoadThread; // 下载线程
	private boolean interceptFlag = false;// 用户取消下载
	public String packagename = "";
	int notifyid = 0;
	Notification notify1 = null;
	PendingIntent pendingIntent = null;
	NotificationManager mNotificationManager = null;
	public Downloadapk(Context context) {
		this.mContext = context;
	}

	public void downloadApk(String url,
			String title, String apkname) {
		if (!android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			return;
		}
		apkUrl = url;
		saveFileName = savePath + apkname;
		this.url = url;
		this.title = title;
		this.apkname = apkname;
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
		notification(false);
	}
	protected void installApk() {
		notification(true);
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		icon = loadUninstallApkIcon(mContext, saveFileName);
		label = loadUninstallApkLabel(mContext, saveFileName);
		packagename = getpackagename(mContext, saveFileName);
		Intent install = new Intent();
		install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		install.setAction(android.content.Intent.ACTION_VIEW);
		install.setDataAndType(Uri.fromFile(apkfile),
				"application/vnd.android.package-archive");
		if (mContext != null) {
			mContext.startActivity(install);
		}
	}

	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			while(true){
				if(doDownloadTheFile() == 1){
					installApk();
					break;
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				}
			}
		}
		/**
		 * 
		 * @param size 已下载数
		 * @return 1下载�?  -1下载失败
		 */
		public int doDownloadTheFile() {
			//file.size()即可得到原来下载文件的大�?
			RandomAccessFile fos = null;
			InputStream is = null;
			HttpResponse response = null;
			// 用来获取下载文件的大�?
			HttpResponse response_test = null;
			try {
				HttpClient client = new DefaultHttpClient();
				HttpClient client_test = new DefaultHttpClient();
				HttpGet request = new HttpGet(url);
				HttpGet request_test = new HttpGet(url);
//				Header header_gziptest = new BasicHeader("Accept-Encoding", "identity");
//				request_test.addHeader(header_gziptest);
				response_test = client_test.execute(request_test);
				//获取�?要下载文件的大小
				fileSize = (int) response_test.getEntity().getContentLength();
				// 验证下载文件的完整�??
				if (fileSize > 0 && fileSize == startat) {
					//已下载完
					progress = 100;
					return 1;
				}
				response = client.execute(request);
				is = response.getEntity().getContent();
				if (is == null) {
					return -1;
				}
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				//获取文件对象，开始往文件里面写内�?
				File myTempFile = new File(saveFileName);
				if(myTempFile.length() !=0 && myTempFile.length() == fileSize){
					progress = 100;
					return 1;
				}else{
					myTempFile.delete();
					myTempFile= new File(saveFileName);
				}
				fos = new RandomAccessFile(myTempFile, "rwd");
				//从文件的size以后的位置开始写入，其实也不用，直接�?后写就可以�?�有时�?�多线程下载�?要用
				fos.seek(startat);
				byte buf[] = new byte[1024];
				int mark = 0;
				do {
					int numread = is.read(buf);
					if (numread <= 0) {
						progress = 100;
						break;
					}
					fos.write(buf, 0, numread);
					startat += numread;
					mark++;
					if(mark%1024 == 0){
						fos.close();
						fos = null;
						fos = new RandomAccessFile(myTempFile, "rwd");
						//从文件的size以后的位置开始写入，其实也不用，直接�?后写就可以�?�有时�?�多线程下载�?要用
						fos.seek(startat);
					}
						progress = (int) (((float) startat / fileSize) * 100);
						if(progress > 100){
							progress = 100;
							break;
						}
				} while (true);
				if(fos != null || is != null){
					fos.close();
					is.close();	
				}
			} catch (Exception ex) {
				if(fos != null || is != null){
					try {
						fos.close();
						is.close();
					} catch (IOException e) {
						return -1;
					}
				}
				return -1;
			}
			return 1;
		}
		

	};
	public Drawable loadUninstallApkIcon(Context context, String archiveFilePath) {
        PackageManager pm = context.getPackageManager();  
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);  
  
        if(info == null){return null;}
        ApplicationInfo appInfo = info.applicationInfo;  
        if(appInfo == null){return null;}
        appInfo.sourceDir = archiveFilePath;  
        appInfo.publicSourceDir = archiveFilePath;  
        Drawable icon =  appInfo.loadIcon(pm);
        appInfo.loadLabel(pm);
        return icon;
    }
	public String loadUninstallApkLabel(Context context, String archiveFilePath) {
        PackageManager pm = context.getPackageManager();  
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);  
  
        if(info == null){return null;}
        ApplicationInfo appInfo = info.applicationInfo;  
        if(appInfo == null){return null;}
        appInfo.sourceDir = archiveFilePath;  
        appInfo.publicSourceDir = archiveFilePath;  
        String label =  appInfo.loadLabel(pm).toString();
        return label;
    }
	public String getpackagename(Context context, String archiveFilePath) {
        PackageManager pm = context.getPackageManager();  
        PackageInfo info = pm.getPackageArchiveInfo(archiveFilePath, PackageManager.GET_ACTIVITIES);  
  
        if(info == null){return null;}
        ApplicationInfo appInfo = info.applicationInfo;  
        if(appInfo == null){return null;}
        appInfo.sourceDir = archiveFilePath;  
        appInfo.publicSourceDir = archiveFilePath;  
        String packagename =  appInfo.packageName;
        return packagename;
    }
    public void notification(boolean downloaded){
    	Intent install = new Intent();
    	if(downloaded){
        	File apkfile = new File(saveFileName);
    		if (!apkfile.exists()) {
    			return;
    		}
        	install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		install.setAction(android.content.Intent.ACTION_VIEW);
    		install.setDataAndType(Uri.fromFile(apkfile),
    				"application/vnd.android.package-archive");
    	}

    	pendingIntent = PendingIntent.getActivity(mContext, 0,
    			install, PendingIntent.FLAG_UPDATE_CURRENT);
        // 下面�?兼容Android 2.x版本是的处理方式  
        // Notification notify1 = new Notification(R.drawable.message,  
        // "TickerText:" + "您有新短消息，请注意查收�?", System.currentTimeMillis());  
        notify1 = new Notification();  
        notify1.icon = R.drawable.stat_sys_download;  
        notify1.tickerText = title;
        notify1.when = System.currentTimeMillis();
        notify1.number = 1;
//        notify1.defaults = Notification.DEFAULT_SOUND;
        notify1.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当�?�知被用户点击时，�?�知将被清除�?  
        // 通过通知管理器来发起通知。如果id不同，则每click，在statu那里增加�?个提�?  
        mNotificationManager=(NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        new Thread(){
        	public void run() {
        		try {
        			if(notify1 == null || mNotificationManager == null){
        				return;
        			}
            		while(progress!= 100){
            			try {
        					sleep(3000);
        				} catch (InterruptedException e) {
        				}
            			if(notify1 == null || mNotificationManager == null){
            				return;
            			}
                        notify1.setLatestEventInfo(mContext, title,
                        		"已下�?"+progress+"%", pendingIntent);
                        mNotificationManager.notify(notifyid, notify1);	
            		}
            		if(progress == 100){
            			if(notify1 == null || mNotificationManager == null){
            				return;
            			}
            			notify1.setLatestEventInfo(mContext, title,
                        		"完成下载", pendingIntent);
                        mNotificationManager.notify(notifyid, notify1);
            		}
				} catch (Exception e) {
				}

        	};
        }.start();
    }
    public static Bitmap drawableToBitmap(Drawable drawable)
	{

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}
}
