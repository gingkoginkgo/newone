package com.example.newone;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

//如果確定提醒，交給他處理

public class Reminder extends Activity {

	private WebView ShowTargeMapForUser;
	private String MAP_URL="http://www.google.com";
		
	@Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.showtargetmap);
	    ShowTargeMapForUser = (WebView)findViewById(R.id.webview);
	    WebSettings webSettings = ShowTargeMapForUser.getSettings();
	    ShowTargeMapForUser.getSettings().setJavaScriptEnabled(true); // 開啟JAVASCRIPT功能
	    ShowTargeMapForUser.loadUrl(MAP_URL);
	  }  
	}
	

