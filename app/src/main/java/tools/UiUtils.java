package tools;

import android.content.Context;
import android.content.res.Resources;

import com.example.dell_pc.googleplay.BaseApplication;

public class UiUtils {

	public static Resources getResource() {
		return BaseApplication.getApplication().getResources();
	}

	/** dip转换px */
	public static int dip2px(int dip) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}

	/** pxz转换dip */

	public static int px2dip(int px) {
		final float scale = getResource().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	public static Context getContext(){
		return BaseApplication.getApplication();
	}

	/**
	 * 把Runnable 方法提交到主线程运行
	 * @param runnable
	 */
	public static void runOnUiThread(Runnable runnable) {
		// 在主线程运行
		if(android.os.Process.myTid()==BaseApplication.getMainTid()){
			runnable.run();
		}else{
			//获取handler
			BaseApplication.getHandler().post(runnable);
		}
	}
}
