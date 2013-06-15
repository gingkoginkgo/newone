package com.example.newone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

//先去買便當，要改成Notification
//如果確定提醒，交給他處理

public class Reminder extends Activity {

	private WebView ShowTargeMapForUser;

	private String recive_resultLat;	
	private String recive_resultLng;
	private String MAP_URL;


	@Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.showtargetmap);
	    POI r = POIService.getInstance().getResultPOI();
	    recive_resultLat = ""+r.getLat();
	    recive_resultLng = ""+r.getLng();
	    ShowTargeMapForUser = (WebView)findViewById(R.id.webview);
	    WebSettings webSettings = ShowTargeMapForUser.getSettings();
	    ShowTargeMapForUser.getSettings().setJavaScriptEnabled(true); // 開啟JAVASCRIPT功能
		MAP_URL="https://maps.google.com.tw/maps?q="+recive_resultLat+","+recive_resultLng;
	    ShowTargeMapForUser.loadUrl(MAP_URL);
	  } 

}
	

