package com.szc.fast_express_system;

import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.szc.fast_express_system.entities.LoginData;
import com.szc.fast_express_system.ui.fragment.LocationApplication;
import com.szc.fast_express_system.common.net.AppSocketInterface;
import com.szc.fast_express_system.common.net.XUtilsSocketImpl;

/******************************************
 * 类描述： 程序入口�? 类名称：ExpressSystemApplication
 * 
 * @version: 1.0
 * @author: why
 * @time: 2014-10-13 下午2:09:22
 ******************************************/
public class ExpressSystemApplication extends LocationApplication {
	private String TAG = "ExpressSystemApplication";
	public static Context applicationContext;

	/** 实例�? **/
	private static ExpressSystemApplication instance;
	/** 网络链接 **/
	private static AppSocketInterface appSocket;

	public int curVersionCode; // 版本�?
	public String curVersionName; // 版本名字
	public String systemVersion; 
	public String deviceID; // 设备ID
	

	private boolean login;// 登录情况

	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}

	/**
	 * 方法描述：初始化
	 * 
	 * @author: why
	 * @time: 2014-2-14 下午3:46:04
	 */
	private void init() {
		applicationContext = this;
		instance = this;
		appSocket = new XUtilsSocketImpl();
		getCurrentVersion();
		initImageLoad();
		//getDeviceID();
	}

	
	
	/**
	  * 方法描述：TODO
	  * @author: wanghy
	  * @time: 2014-11-16 下午11:27:48
	  */
	private void initImageLoad() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT) 
		.bitmapConfig(Bitmap.Config.RGB_565)// 防止内存溢出的，图片太多就这这个。还有其他设�?
		.displayer(new RoundedBitmapDisplayer(5))  //圆角，不�?要请删除
		                           .build();  
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				.memoryCacheExtraOptions(480, 800)// 缓存在内存的图片的宽和高�?
				.memoryCache(new WeakMemoryCache()) 
				.memoryCacheSize(2 * 1024 * 1024) //缓存到内存的�?大数�?
				.defaultDisplayImageOptions(options).  //上面的options对象，一些属性配�?
				build();
		ImageLoader.getInstance().init(config); //初始�?
	}

	/**
	 * 方法描述：获取设备唯�?标示
	 * 
	 * @author: why
	 * @time: 2014-2-21 下午8:53:03
	 */
	private void getDeviceID() {
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String DEVICE_ID = tm.getDeviceId();
		if (!TextUtils.isEmpty(DEVICE_ID)) {
			deviceID = DEVICE_ID;
		} else {
			//deviceID = TCAgent.getDeviceId(this);
		}
	}

	/**
	 * @return login : return the property login.
	 */
	public boolean isLogin() {
		return login;
	}

	/**
	 * @param login
	 *            : set the property login.
	 */
	public void setLogin(boolean login) {
		this.login = login;
		if (!login) {
			//AppController.getController().getContext().clearBusinessData();
		}
	}

	/**
	 * 方法描述: 获取网络通信实例
	 * 
	 * @return
	 * @author: why
	 * @time: 2013-10-21 下午3:32:02
	 */
	public static AppSocketInterface getAppSocket() {
		return appSocket;
	}

	/**
	 * 方法描述：获取实�?
	 * 
	 * @return
	 * @author: why
	 * @time: 2013-10-21 下午2:52:44
	 */
	public static ExpressSystemApplication getInstance() {
		return instance;
	}

	/**
	 * 获取当前客户端版本信�?
	 */
	private void getCurrentVersion() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			curVersionName = info.versionName;
			curVersionCode = info.versionCode;
			systemVersion = android.os.Build.VERSION.SDK;
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
	}
	
	/**
	 * 方法描述：存储token信息
	 * @author: why
	 * @time: 2014-7-1 下午3:16:35
	 */
	public void saveToken(String token) {
		AppSharedPref.getInstance(this).saveToken(token);
	}
	
	/**
	 * 方法描述：TODO
	 * @author: why
	 * @time: 2014-7-1 下午3:17:47
	 */
	public String getToken() {
		return AppSharedPref.getInstance(this).getToken();
	}
	
	
	/**
	 * 方法描述：存储token信息
	 * @author: why
	 * @time: 2014-7-1 下午3:16:35
	 */
	public void saveTokenFlag(boolean flag) {
		AppSharedPref.getInstance(this).setTokenFlag(flag);
	}
	
	
	/**
	 * 方法描述：TODO
	 * @author: why
	 * @time: 2014-7-1 下午3:17:47
	 */
	public boolean isTokenFlag() {
		return AppSharedPref.getInstance(this).getTokenFlag();
	}
	
	
	
	/**
	 * 获取登录信息
	 * 
	 * @return
	 */
	public LoginData getLoginInfo() {
		return AppSharedPref.getInstance(this).getLoginInfo();
	}
	/**
	 * 保存登录信息
	 * 
	 * @param username
	 * @param pwd
	 */
	public void saveLoginInfo(LoginData data) {
		AppSharedPref.getInstance(this).setLoginInfo(data);
	}
}
