package com.fangfa.everydaycoin.utils;

import android.app.Activity;
import android.widget.Toast;

import com.fangfa.everydaycoin.Bean.ShareBean;
import com.fangfa.everydaycoin.ui.MainActivity;
import com.fangfa.everydaycoin.vov.Param;
import com.mob.MobSDK;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class Share {

   private Activity  activity;



    //微博
    public void weibo(Activity activity, ShareBean bean) {
        this.activity=activity;
        // 初始化ShareSDK
        MobSDK.init(activity.getApplicationContext());
        cn.sharesdk.sina.weibo.SinaWeibo.ShareParams sp = new cn.sharesdk.sina.weibo.SinaWeibo.ShareParams(); ;
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setText(bean.getTitle() + bean.getUrl());
        sp.setImageUrl(Param.Share);
        Platform weibo1 = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo1.setPlatformActionListener(new mPlatformActionListener() );
        weibo1.share(sp);
    }

    //qq空间
    public   void  qzome(Activity activity, ShareBean bean){
        this.activity=activity;
        // 初始化ShareSDK
        MobSDK.init(activity.getApplicationContext());
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle( bean.getTitle());
        sp.setTitleUrl(bean.getUrl()); // 标题的超链接
        sp.setText(bean.getDesc());
        sp.setImageUrl(Param.Share);
        sp.setSite("币读");
        sp.setSiteUrl("发布分享网站的地址");

        Platform qzone = ShareSDK.getPlatform (QZone.NAME);
        qzone.setPlatformActionListener(new mPlatformActionListener() );
        qzone.share(sp);
    }

    //qq
    public  void   qq(Activity activity, ShareBean bean){
        this.activity=activity;
        // 初始化ShareSDK
        MobSDK.init(activity.getApplicationContext());
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(bean.getTitle());
        sp.setTitleUrl(bean.getUrl()); // 标题的超链接
        sp.setText(bean.getDesc());
        sp.setImageUrl(Param.Share);
        sp.setSite("币读");
        sp.setSiteUrl(bean.getUrl());

        Platform qq = ShareSDK.getPlatform (QQ.NAME);
        qq.setPlatformActionListener(new mPlatformActionListener() );
        qq.share(sp);
    }

    //微信
    public   void   weichat(Activity activity,ShareBean bean){
        this.activity=activity;
        // 初始化ShareSDK
        MobSDK.init(activity.getApplicationContext());
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(bean.getTitle());
        sp.setUrl(bean.getUrl());
        sp.setImageUrl(Param.Share);
        sp.setText(bean.getDesc());

        Platform wechat = ShareSDK.getPlatform (Wechat.NAME);
        wechat.setPlatformActionListener(new mPlatformActionListener() );
        wechat.share(sp);

    }

    //微信朋友圈
    public   void   weiMoments(Activity activity, ShareBean bean){
        this.activity=activity;
        // 初始化ShareSDK
        MobSDK.init(activity.getApplicationContext());
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setTitle(bean.getDesc());
        sp.setUrl(bean.getUrl());
        sp.setImageUrl(Param.Share);
//        sp.setText(bean.getDesc());

        Platform wechatMoments = ShareSDK.getPlatform (WechatMoments.NAME);
        wechatMoments.setPlatformActionListener(new mPlatformActionListener() );
        wechatMoments.share(sp);

    }




    class   mPlatformActionListener implements PlatformActionListener {

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            //这里需要说明的一个参数就是HashMap<String, Object> arg2
            //这 个参数在你进行登录操作的时候里面会保存有用户的数据，例如用户名之类的。
            MiuiToast.MakeText(activity,"分享成功", MiuiToast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            MiuiToast.MakeText(activity,"分享失败", MiuiToast.LENGTH_SHORT).show();


        }

        @Override
        public void onCancel(Platform platform, int i) {
            MiuiToast.MakeText(activity,"取消分享", MiuiToast.LENGTH_SHORT).show();
        }
    }

}
