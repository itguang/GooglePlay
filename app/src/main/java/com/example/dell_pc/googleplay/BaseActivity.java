package com.example.dell_pc.googleplay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

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

/**
 * 抽取BaseActivity ,管理所有的Activity 方便退出
 */
public class BaseActivity extends AppCompatActivity {

    // 管理运行的所有的activity
    public final static List<BaseActivity> mActivities = new LinkedList<BaseActivity>();
    private KillAllReceiver killAllReceiver;
    private IntentFilter filter;

    /**
     * 广播接受者,退出整个应用程序
     */
    class KillAllReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        killAllReceiver = new KillAllReceiver();
        filter = new IntentFilter("com.example.dell_pc.googleplay.killall");
        registerReceiver(killAllReceiver, filter);//注册广播接受者

        synchronized (mActivities) {
            mActivities.add(this);
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        //退出时,接触广播注册
        if (killAllReceiver != null) {
            unregisterReceiver(killAllReceiver);
        }
    }

    /**
     * 退出整个应用程序,释放所有资源
     */
    public void killAll() {
        LinkedList<BaseActivity> copy;
        // 复制了一份mActivities 集合
        synchronized (mActivities) {
            copy = new LinkedList<>(mActivities);
        }
        for (BaseActivity activity : copy) {
            activity.finish();

        }
        // 杀死当前的进程
        android.os.Process.killProcess(android.os.Process.myPid());

    }


}
