package com.example.newone;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

//如果確定提醒，交給他處理

public class Reminder extends Activity {

	private WebView ShowTargeMapForUser;

	private double recive_resultLat;	
	private double recive_resultLng;
	private String MAP_URL;
	public void recive_Location(double lat,double lan) 
	{
		
		recive_resultLat = lat;
		recive_resultLng = lan; 
		
	}

	
	public double get_resultLat(){
		return recive_resultLat;
	}
	public double get_resultLng(){
		return recive_resultLng;
	}
	
	
	
	
	@Override
		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.showtargetmap);
	    ShowTargeMapForUser = (WebView)findViewById(R.id.webview);
	    WebSettings webSettings = ShowTargeMapForUser.getSettings();
	    ShowTargeMapForUser.getSettings().setJavaScriptEnabled(true); // 開啟JAVASCRIPT功能
		MAP_URL="https://maps.google.com.tw";
	    
	    ShowTargeMapForUser.loadUrl(MAP_URL);
	    
	    
	    
	  }  
	}
	

