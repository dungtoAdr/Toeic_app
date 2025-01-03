package com.example.toeicapp.activty;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.toeicapp.R;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView=findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        webView.getSettings().setJavaScriptEnabled(true);

        String url=getIntent().getStringExtra("url");
        if(url!=null){
            webView.loadUrl(url);
        }else{
            Toast.makeText(getApplicationContext(),"Fail to load url",Toast.LENGTH_SHORT).show();
        }
    }
    // Đảm bảo khi bấm nút back sẽ thoát khỏi WebView nếu trang web đang được hiển thị
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}