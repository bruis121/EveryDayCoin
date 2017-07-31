package com.fangfa.everydaycoin.vov;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatActivity;


import com.mob.MobSDK;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.sdk.QbSdk;

import java.util.LinkedList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class MyApplication extends Application {
    //便与后面退出程序
    public List<AppCompatActivity> mList = new LinkedList<AppCompatActivity >();
    public static MyApplication  instans;


    public MyApplication(){

    }
    public   synchronized  static   MyApplication  getInstans(){
        if(null==instans){
            instans=new MyApplication();
        }
        return   instans;
    }


    public  void  addActivity(AppCompatActivity  activty){
        mList.add(activty);
    }


    /**
     *  activty退出
     */

    public void exit(){
        try {
            for (AppCompatActivity  activity : mList) {
                if (activity != null) {
                    activity.finish();
                    activity = null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }




    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }


    protected String a() {
        return null;
    }

    protected String b() {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instans=this;
        MobSDK.init(this, this.a(), this.b());








        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。

            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);


        /**
         * jpush
         */
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

    }
}
