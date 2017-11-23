package com.chenqihong.genjiwangcrawer;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String URL = ".ganji.com/";
    public WebView mWebView;
    public Button mConfirmButton;
    public Iterator<CityBean> mIterator;
    public CityBean mCurrentCity;
    public Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while(null != mCurrentCity || mIterator.hasNext()){
                CityBean city = null;
                if(null != mCurrentCity) {
                    city = mCurrentCity;
                    mCurrentCity = null;
                }else{
                    city = mIterator.next();
                }

                for(int index = 1; index <= 100; index++){
                    try {
                        Thread.sleep(2000);
                        String raw = Crawer.syncGet("http://" + city.getPinyin().trim().toLowerCase() + URL + "danbaobaoxian/o" + index + "/");
                        String result = Parser.parse(raw, city.getPinyin().trim().toLowerCase() + URL);
                        if (null != result) {
                            Message message = mHandler.obtainMessage();
                            Bundle bundle = new Bundle();
                            bundle.putString("url", result);
                            message.setData(bundle);
                            message.sendToTarget();
                            mCurrentCity = city;
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    public Handler mHandler = new Handler(){
        public void handleMessage(Message message){
            Bundle bundle = message.getData();
            String url = bundle.getString("url");
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mWebView.getSettings().setSupportMultipleWindows(true);
            mWebView.setWebViewClient(new WebViewClient());
            mWebView.setWebChromeClient(new WebChromeClient());
            mWebView.loadUrl(url);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        new Thread(mRunnable).start();
    }

    private void initView(){
        mWebView = (WebView)findViewById(R.id.webview);
        mConfirmButton = (Button)findViewById(R.id.confirmButton);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(mRunnable).start();
            }
        });

        List<CityBean> cityBeanList = City.getCityList();
        mIterator = cityBeanList.iterator();
    }
}
