package com.example.dell_pc.googleplay;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 作者: 小光哥 on 2016/7/30.
 * 邮箱: 1445037803@qq.com
 * 修订历史:
 * 描述:
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓━━━━┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　史　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　诗　┃
 * 　　　　　　　　　┃　之　┃
 * 　　　　　　　　　┃　宠　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃BUG天敌　　　┣┓┣┓┣┓┣┓┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 */
public class BaseApplication extends Application {
    private static BaseApplication application;

    private static int mainTid;
    private static Handler handler;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        mainTid = android.os.Process.myTid();
        handler=new Handler();
    }

    /**
     * 得到当前应用程序的上下文
     * @return
     */
    public static Context getApplication(){
        return application;
    }
    public static int getMainTid() {
        return mainTid;
    }
    public static Handler getHandler() {
        return handler;
    }


}
