package com.example.newone;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

//如果確定提醒，交給他處理

public class Reminder extends Activity {

	private WebView ShowTargeMapForUser;
	public Reminder(){}
		
	@Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.showtargetmap);
	    ShowTargeMapForUser = (WebView)findViewById(R.id.webview);
	    WebSettings webSettings = ShowTargeMapForUser.getSettings();
	    webSettings.setJavaScriptEnabled(true);
		}
	
	  @Override
	  public void onStart()
	  {
	    super.onStart();
	    String ShowResUrl = "http://ascii.net/ok.jsp";
	    ShowTargeMapForUser.loadUrl(ShowResUrl);
	    ShowTargeMapForUser.setWebViewClient(new WebViewClientImpl());
	  }
		
	}
	

