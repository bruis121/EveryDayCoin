package com.fangfa.everydaycoin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.fangfa.everydaycoin.R;
import com.fangfa.everydaycoin.vov.BaseActivty;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class Splash extends BaseActivty {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        StatusBarUtil.setTranslucent(Splash.this);
        new Thread(new MyThread()).start();
    }


    public class MyThread implements Runnable{

        @Override
        public void run() {

            try {
                Thread.sleep(3000);
                Message  msg=new Message();
                msg.what=1;
                handler.sendMessage(msg);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                Intent intent=new Intent(Splash.this,MainActivity.class);
                startActivity(intent);
            }
        }
        };
}
