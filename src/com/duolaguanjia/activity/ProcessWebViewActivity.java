package com.duolaguanjia.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.duolaguanjia.R;
import com.duolaguanjia.base.BaseActivity;
import com.duolaguanjia.manager.TitleManager;

/**
 * Created by Administrator on 2016/1/26.
 */
public class ProcessWebViewActivity extends BaseActivity
{
    TitleManager titleManager;
    private WebView mWebView;
    private WebSettings settings;//webview 设置器
String url;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_webview);
        titleManager=new TitleManager(findViewById(R.id.title),this);
        titleManager.setTitleName(getIntent().getStringExtra("title"));
        titleManager.setLeftOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        mWebView= (WebView) findViewById(R.id.wv_web);
        settings = mWebView.getSettings();
        setting();
        setListener();
        url=getIntent().getStringExtra("url");
        //打开
         mWebView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
       System.exit(0);
        super.onDestroy();
    }

    private void setListener()
    {
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                System.out.println("获取到的title"+title);
            }

        };

        // 设置setWebChromeClient对象
        mWebView.setWebChromeClient(wvcc);
        mWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
             showDiaog();
                return super.shouldOverrideUrlLoading(view, url);
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideDialog();
                super.onPageFinished(view, url);
            }
        });

    }
    /**
     * 设置属性
     */
    private void setting() {
        /**
         * 设置属性
         */
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        mWebView.getSettings().setUseWideViewPort(true);
      mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置缓存模式:根据cache-control决定是否从网络上取数据

    }

    @Override
    public void httpError(String code, String response) {

    }
}
