package com.fangfa.everydaycoin.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fangfa.everydaycoin.Bean.ShareBean;
import com.fangfa.everydaycoin.R;
import com.fangfa.everydaycoin.utils.Share;
import com.fangfa.everydaycoin.vov.BaseActivty;

/**
 * Created by Administrator on 2017/7/25 0025.
 *
 */

public class custmDialog  extends Activity implements View.OnClickListener{
    private ImageView  delete;
    private LinearLayout  wechat;
    private  LinearLayout  wemoments;
    private   LinearLayout  qq;
    private   LinearLayout  qzone;
    private   LinearLayout sina;
    private   ShareBean  bean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertdialog);
        delete=findViewById(R.id.delete);
        wechat=findViewById(R.id.wechat);
        wemoments=findViewById(R.id.wemoments);
        qq=findViewById(R.id.qq);
        qzone=findViewById(R.id.qzone);
        sina=findViewById(R.id.sina);


        Intent intent = this.getIntent();
        bean=(ShareBean)intent.getSerializableExtra("bean");


        delete.setOnClickListener(this);
        wechat.setOnClickListener(this);
        wemoments.setOnClickListener(this);
        qq.setOnClickListener(this);
        qzone.setOnClickListener(this);
        sina.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete:
                finish();
                break;
            case  R.id.wechat:
                new Share().weichat(custmDialog.this,bean);
                break;
            case  R.id.wemoments:
                new Share().weiMoments(custmDialog.this,bean);
                break;
            case  R.id.qq:
                new Share().qq(custmDialog.this,bean);
                break;
            case R.id.qzone:
                new Share().qzome(custmDialog.this,bean);
                break;
            case  R.id.sina:
                new Share().weibo(custmDialog.this,bean);
                break;
        }
    }
}
