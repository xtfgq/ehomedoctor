package com.zzu.ehome.ehomefordoctor.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.KeyEvent;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zzu.ehome.ehomefordoctor.R;
import com.zzu.ehome.ehomefordoctor.app.Constans;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zzu.ehome.ehomefordoctor.activity.MedicalRecordActivity.TARGETID;


/**
 * Created by Administrator on 2016/5/16.
 */
public class SmartWebView extends BaseSimpleActivity {


    @BindView(R.id.webview)
    WebView mWebView;

    private String mUserNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_webview);
        ButterKnife.bind(this);
        mUserNo = getIntent().getStringExtra(MedicalRecordActivity.UserNo);
        WebSettings webSettings = mWebView.getSettings();
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setDownloadListener(new MyWebViewDownLoadListener());
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        String url=Constans.WEBSERVICE_URL+"LaiKang/HealthDataList.aspx?CardNo="+mUserNo;
        mWebView.loadUrl(url);

    }

    @Override
    public void init() {

    }



    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            http:
//ehome.staging.topmd.cn:81/LaiKang/BackToAppHomePage
            if (url.contains("BackToAppHomePage")) {
                finish();
            } else {
                view.loadUrl(url);
            }
            return true;

        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            // 返回键退回
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }

    }
}
