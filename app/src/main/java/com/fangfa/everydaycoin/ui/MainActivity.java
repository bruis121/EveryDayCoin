package com.fangfa.everydaycoin.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fangfa.everydaycoin.Bean.ShareBean;
import com.fangfa.everydaycoin.R;
import com.fangfa.everydaycoin.utils.MiuiToast;
import com.fangfa.everydaycoin.utils.Share;
import com.fangfa.everydaycoin.vov.BaseActivty;
import com.fangfa.everydaycoin.vov.MyApplication;
import com.fangfa.everydaycoin.vov.Param;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.jaeger.library.StatusBarUtil;
import com.tencent.bugly.Bugly;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.List;

//查看的
//md5:668bcc72a4ce7dcfad8f2f4066cba8ee
@SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
public class MainActivity extends BaseActivty {
    //定义退出程序boolen类型
    private boolean Exit= false;


    private  com.tencent.smtt.sdk.WebView  webview;
    private AVLoadingIndicatorView AVLoading;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    //文件
    private final static int FILECHOOSER_RESULTCODE = 1;



    private BounceTopEnter mBasIn;
    private SlideBottomExit mBasOut;

    private ImageView  maobo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview= (WebView) findViewById(R.id.webview);
        AVLoading= (AVLoadingIndicatorView) findViewById(R.id.progressav);
        maobo= (ImageView) findViewById(R.id.maobo);

        Bugly.init(getApplicationContext(), "2cd58192fb", false);



       int black = ContextCompat.getColor(MainActivity.this, R.color.black);
        StatusBarUtil.setColorNoTranslucent(MainActivity.this,black);

        maobo.postDelayed(() -> {
             maobo.setVisibility(View.GONE);
             webview.setVisibility(View.VISIBLE);
        },2000);


        mBasIn = new BounceTopEnter();
        mBasOut = new SlideBottomExit();

        init();
        //识别js
        viewjs();

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(com.tencent.smtt.sdk.WebView webView, com.tencent.smtt.export.external.interfaces.SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
                sslErrorHandler.proceed(); // 接受所有证书
            }


            @Override
            public void onPageStarted(com.tencent.smtt.sdk.WebView webView, String url, Bitmap bitmap){
                if (url.equalsIgnoreCase(Param.Home)||url.equalsIgnoreCase(Param.HomeTwo)
                        ||url.equalsIgnoreCase(Param.HomeFour)){
                    Exit = true;
                }else{
                    Exit = false;
                }


            }

            @Override
            public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView view, final String url) {
                view.loadUrl(url); // 在当前的webview中跳转到新的url
                return   true;
            }


            //加速处理
            @Override
            public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String s) {
                super.onPageFinished(webView, s);
                webView.getSettings().setBlockNetworkImage(false);


                if (!webView.getSettings().getLoadsImagesAutomatically()) {
                   webView.getSettings().setLoadsImagesAutomatically(true);
                }
            }



            @Override
            public void onReceivedError(com.tencent.smtt.sdk.WebView view, int errorCode, String description, final String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (errorCode == WebViewClient.ERROR_CONNECT || errorCode == WebViewClient.ERROR_TIMEOUT || errorCode == WebViewClient.ERROR_HOST_LOOKUP) {
                }


            }




        });



       webview.setWebChromeClient(new com.tencent.smtt.sdk.WebChromeClient(){

           @Override
           public void onReceivedTitle(com.tencent.smtt.sdk.WebView view, String s) {
               super.onReceivedTitle(view, s);

           }


           @Override
           public void onProgressChanged(com.tencent.smtt.sdk.WebView view, int newProgress) {
               if (newProgress >= 80) {
                   AVLoading.setVisibility(View.GONE);
               } else {

                   AVLoading.setVisibility(View.VISIBLE);
               }
               super.onProgressChanged(view, newProgress);

           }


           @Override
           public boolean onJsAlert(com.tencent.smtt.sdk.WebView webView, String s, String s1, JsResult jsResult) {
               final NormalDialog dialogs = new NormalDialog(MainActivity.this);
               dialogs.content(s1)
                       .btnNum(1)
                       .style(NormalDialog.STYLE_TWO)
                       .titleTextColor(Color.parseColor("#323232"))
                       .contentTextColor(Color.parseColor("#666666"))
                       .btnTextColor(Color.parseColor("#323232"))
                       .title(getResources().getString(R.string.title))
                       .titleTextSize(20)
                       .btnTextSize(20)
                       .showAnim(mBasIn)
                       .btnText(getResources().getString(R.string.sure))
                       .dismissAnim(mBasOut)
                       .show();
               dialogs.setOnBtnClickL(

                       (OnBtnClickL) () -> dialogs.dismiss());
               jsResult.cancel();
               return true;
           }

           // For Android 3.0-
           public void openFileChooser(ValueCallback<Uri> uploadMsg) {

               mUploadMessage = uploadMsg;
               Intent i = new Intent(Intent.ACTION_GET_CONTENT);
               i.addCategory(Intent.CATEGORY_OPENABLE);
               i.setType("*/*");
               MainActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);

           }

           // For Android 3.0+
           public void openFileChooser(ValueCallback uploadMsg, String acceptType) {

               mUploadMessage = uploadMsg;

               Intent i = new Intent(Intent.ACTION_GET_CONTENT);
               i.addCategory(Intent.CATEGORY_OPENABLE);
               i.setType("*/*");
               MainActivity.this.startActivityForResult(
                       Intent.createChooser(i, "File Browser"),
                       FILECHOOSER_RESULTCODE);

           }

           //For Android 4.1
           public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
               mUploadMessage = uploadMsg;
               Intent i = new Intent(Intent.ACTION_GET_CONTENT);
               i.addCategory(Intent.CATEGORY_OPENABLE);
               i.setType("*/*");
               MainActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), MainActivity.FILECHOOSER_RESULTCODE);

           }

           // For Android 5.0+
           public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
               mUploadCallbackAboveL = filePathCallback;
               Intent i = new Intent(Intent.ACTION_GET_CONTENT);
               i.addCategory(Intent.CATEGORY_OPENABLE);
               i.setType("*/*");
               MainActivity.this.startActivityForResult(
                       Intent.createChooser(i, "File Browser"),
                       FILECHOOSER_RESULTCODE);

               return true;
           }

       });



        webview.loadUrl(Param.Home);
    }

    @SuppressLint("JavascriptInterface")
    private void viewjs() {

        webview.addJavascriptInterface(getHtmlObject(), "jsWechat");
        webview.addJavascriptInterface(getHtmlObject1(), "jsWechatmoments");
        webview.addJavascriptInterface(getHtmlObject2(), "jsQq");
        webview.addJavascriptInterface(getHtmlObject3(), "jsQzone");
        webview.addJavascriptInterface(getHtmlObject4(), "jsSinaweibo");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAllowFileAccessFromFileURLs(true);
        webview.getSettings().setSupportZoom(true);
       webview.getSettings().setUseWideViewPort(true);//关键点//扩大比例的缩放
//        //自适应屏幕
        webview.getSettings().setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setDisplayZoomControls(false);

        webview.getSettings().setAllowFileAccess(true); // 允许访问文件
        webview.getSettings().setBuiltInZoomControls(true); // 设置显示缩放按钮
        webview.getSettings().setLoadWithOverviewMode(true); //缩放至屏幕的大小

        webview.setHorizontalScrollBarEnabled(false);//水平不显示
        webview.setVerticalScrollBarEnabled(false); //垂直不显示
        webview.getSettings().setBlockNetworkImage(true);
    }


    //    加速
        private void init() {

            if(Build.VERSION.SDK_INT >=19) {
                webview.getSettings().setLoadsImagesAutomatically(true);
            } else {
                webview.getSettings().setLoadsImagesAutomatically(false);
            }
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==FILECHOOSER_RESULTCODE)
        {
            if (null == mUploadMessage && null == mUploadCallbackAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (mUploadCallbackAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            }
            else  if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        }


    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (data == null) {

            } else {
                String dataString = data.getDataString();


                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }

                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
        return;
    }



    /**
     * 按两次返回键退出程序
     */
    private boolean isExit = false;
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK&&!Exit)// 当keyCode等于退出事件值时
        {

            webview.setVisibility(View.VISIBLE);
            webview.goBack();
            return true;

        }

        else if(keyCode == KeyEvent.KEYCODE_BACK&&Exit) {
            exit();
            return  false;
        }
        return super.onKeyDown(keyCode, event);

    }

    private void exit() {
        if (isExit) {

            MyApplication.getInstans().exit();}
        else{
            isExit = true;


            MiuiToast.MakeText(this, getResources().getString(R.string.exit), MiuiToast.LENGTH_SHORT)
                    .show();
            mHandler.sendEmptyMessageDelayed(0, 3000);// 3秒后发送消息
        }


    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg)// 处理消息
        {
            super.handleMessage(msg);
            isExit = false;
        }
    };






    //微信分享
    public Object getHtmlObject() {
        Object insertObj = new Object() {
            @JavascriptInterface
            public String HtmlcallJava2(final String param) {


                ShareBean  bean=JSON.parseObject(param,ShareBean.class);



               new  Share().weichat(MainActivity.this,bean);

                return "app" ;
            }
        };
        return insertObj;
    }

    //微信朋友圈分享
    public Object getHtmlObject1() {
        Object insertObj = new Object() {
            @JavascriptInterface
            public String HtmlcallJava2(final String param) {


                ShareBean  bean=JSON.parseObject(param,ShareBean.class);



                new  Share().weiMoments(MainActivity.this,bean);

                return "app" ;
            }
        };
        return insertObj;
    }


    //qq分享
    public Object getHtmlObject2() {
        Object insertObj = new Object() {
            @JavascriptInterface
            public String HtmlcallJava2(final String param) {


                ShareBean  bean=JSON.parseObject(param,ShareBean.class);



                new  Share().qq(MainActivity.this,bean);

                return "app" ;
            }
        };
        return insertObj;
    }
    //qq空间分享
    public Object getHtmlObject3() {
        Object insertObj = new Object() {
            @JavascriptInterface
            public String HtmlcallJava2(final String param) {



                ShareBean  bean=JSON.parseObject(param,ShareBean.class);



                new  Share().qzome(MainActivity.this,bean);

                return "app" ;
            }
        };
        return insertObj;
    }

    //其他分享
    public Object getHtmlObject4() {
        Object insertObj = new Object() {
            @JavascriptInterface
            public String HtmlcallJava2(final String param) {
                ShareBean  bean=JSON.parseObject(param,ShareBean.class);

                Intent  intent = new Intent(MainActivity.this,custmDialog.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", bean);
                intent.putExtras(bundle);
                startActivity(intent);

                return "app" ;
            }
        };
        return insertObj;
    }


}
