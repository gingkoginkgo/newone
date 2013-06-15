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
		
		
		//取得Notification服務
		NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);	
		//設定當按下這個通知之後要執行的activity 囧!
		 Intent notify_Intent = new Intent(Reminder.this,Reminder.class);
         notify_Intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
         PendingIntent appIntent = PendingIntent.getActivity(Reminder.this,0,notify_Intent,0);
         Notification notification = new Notification();  
        //設定出現在狀態列的圖示  
        notification.icon=R.drawable.ic_launcher;
        //顯示在狀態列的文字
        notification.tickerText="這附近有可處理的待辦事項";     
        //設定通知的標題、內容
        notification.setLatestEventInfo(Reminder.this,"ToDoList","在哪裡呢?",appIntent);
        //送出Notification
        notificationManager.notify(1,notification);
		
        
      
		//顯示地圖
		//////////////////////////////////////////////////////////////////////
	    setContentView(R.layout.showtargetmap);
	    POI r = POIService.getInstance().getResultPOI();
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
	

