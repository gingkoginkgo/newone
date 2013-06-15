package com.example.newone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;



//如果確定提醒，就跳出提醒+超連結地圖

public class Reminder extends Activity {

	private WebView ShowTargeMapForUser;
	private String recive_resultLat;	
	private String recive_resultLng;
	private String MAP_URL;


	@Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);       
 		//顯示地圖
		//////////////////////////////////////////////////////////////////////
	    setContentView(R.layout.showtargetmap);
	    POI r = POIService.getInstance().getResultPOI();
	    POIService.getInstance().setResultPOI(null);	//reset result
	    recive_resultLat = ""+r.getLat();
	    recive_resultLng = ""+r.getLng();
	    ShowTargeMapForUser = (WebView)findViewById(R.id.webview);
	    WebSettings webSettings = ShowTargeMapForUser.getSettings();
	    ShowTargeMapForUser.getSettings().setJavaScriptEnabled(true); // 開啟JAVASCRIPT功能
		MAP_URL="https://maps.google.com.tw/maps?q="+recive_resultLat+","+recive_resultLng;
	    ShowTargeMapForUser.loadUrl(MAP_URL);
	    //////////////////////////////////////////////////////////////////////
        	
        } 

}
	

