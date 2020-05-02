package com.example.xuchichi.mytest.view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.view.widget.FlowLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatusbarActivity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv)
    TextView tv;

    @BindView(R.id.webview)
    WebView webview;


    StringBuilder builder = new StringBuilder();
    @BindView(R.id.flowlayout)
    FlowLayout flowlayout;


    private String mNames[] = {
            "welcome", "android", "TextView",
            "apple", "jamy", "kobe bryant",
            "jordan", "layout", "viewgroup",
            "margin", "padding", "text",
            "name", "type", "search", "logcat", "name", "type", "search", "logcat"
    };
//
//    private void initChildViews() {
//        // TODO Auto-generated method stub
//        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        lp.leftMargin = 5;
//        lp.rightMargin = 5;
//        lp.topMargin = 5;
//        lp.bottomMargin = 5;
//        for (int i = 0; i < mNames.length; i++) {
//            TextView view = new TextView(this);
//            view.setText(mNames[i]);
//            view.setTextColor(Color.WHITE);
//            view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//            flowlayout.addView(view, lp);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_statusbar);

//        fullScreen(this);


        ButterKnife.bind(this);
//        initChildViews();
//        webSet();
//
//        Glide.with(this).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534079651286&di=bb9b72d5698e0f95734243ce7ddabfe5&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F03%2F10%2Fd6f051cded1dfe9c064ce33e6de0757f.jpg")
//                .into(iv);
//
//
//        builder.append("type=").append("passport").append("&")
//                .append("ic=").append("1234");
//
//        String postDate = "";
//
//        String url = "http://202.76.236.10/gkash/api/qrtest.asp?ic=123";
//        webview.loadUrl(url);
//
//        try {
//
////            webview.postUrl(url,null);// URLEncoder.encode(postDate, "utf-8").getBytes()
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }


    }


    public void webSet() {
        WebSettings webSettings = webview.getSettings();
        // 让WebView能够执行javaScript
        webSettings.setJavaScriptEnabled(true);
        // 让JavaScript可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式,一共有四种模式
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // 设置缓存路径
//        webSettings.setAppCachePath("");
        // 支持缩放(适配到当前屏幕)
        webSettings.setSupportZoom(true);
        // 将图片调整到合适的大小
        webSettings.setUseWideViewPort(true);
        // 支持内容重新布局,一共有四种方式
        // 默认的是NARROW_COLUMNS
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 设置可以被显示的屏幕控制
        webSettings.setDisplayZoomControls(true);
        // 设置默认字体大小
        webSettings.setDefaultFontSize(12);
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private void fullScreen(Activity activity) {
        getWindow().setFlags(

                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,

                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }


}
