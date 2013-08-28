//well, notification of deadline is also put here:P

package com.example.newone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.util.Log;

public class posChecker extends Service{
	private Handler handler = new Handler();
	private double _lon;
	private double _lat;
	private final int delaySec = 60;
	private final double EARTH_RADIUS = 6378137.0;
	ArrayList<ToDo> _todoList = ToDoManager.getInstance().getUserToDo();
	
	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
		    _lat = location.getLatitude();
		    _lon = location.getLongitude(); 
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub	
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub	
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub	
		}
	};

	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
    @Override
    public void onStart(Intent intent, int startId) {
    	handler.postDelayed(checker, 1000);
    	
    	//Get LocationManager
    	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
        super.onStart(intent, startId);
    }
 
    @Override
    public void onDestroy() {
    	handler.removeCallbacks(checker);
    	super.onDestroy();
    }
    
    private void checkByPOI(){
	    ArrayList<ToDo> tmpl = ToDoManager.getInstance().getUserToDo();
	    ArrayList<String> targetlist = new ArrayList<String>();
	    for(ToDo d : tmpl){
	    	if(targetlist.indexOf(d.getTarget_Place())<0){
	    		//didnt add before
	    		targetlist.add(d.getTarget_Place());
	    	}
	    }
	    //get all target
	    for(String s : targetlist){
	    	checkPOIbyTarget(s);
	    }
    }
    
    private int checkPOIbyTarget(String s){
	    POIService _ps = POIService.getInstance();
	    ArrayList<POI> _result = new ArrayList<POI>();
	    ArrayList<POI> _resulttmp = _ps.getNearByPOIs(_lat, _lon, s);
	    _result.addAll(_resulttmp);
	    if(_result.size() == 0)
	    	return 0 ;
	    ArrayList<ToDo> tmpl = ToDoManager.getInstance().getUserToDo();
		for(ToDo d : tmpl){
			if(d.getTarget_Place().equals(s)){
				//add score
				d.setScore(d.getScore()+10f);
				d.setIsByLoc(_result.get(0));   //塞入最近的
			}
		}
	    return _result.size();
    }
    
    private void gNotification(String Title,String subTitle,String Scr,int NotiTag){
	    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_launcher, Title, System.currentTimeMillis());
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(getApplicationContext(), Reminder.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        notification.setLatestEventInfo(getApplicationContext(), subTitle,Scr, contentIntent);
        notificationManager.notify(NotiTag, notification);
    }
    
    private void tNotification(String Title,String subTitle,String Scr,int NotiTag){
	    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_launcher, Title, System.currentTimeMillis());
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        notification.setLatestEventInfo(getApplicationContext(), subTitle,Scr, contentIntent);
        notificationManager.notify(NotiTag, notification);
    }
    
    private void checkByTime(){
    	ArrayList<ToDo> tmpl = ToDoManager.getInstance().getUserToDo();
    	long CurrentTime = System.currentTimeMillis();
    	
    	for(ToDo d : tmpl){
    		if((d.getDeadlineTime()-CurrentTime)<1800000){
    			d.setScore(d.getScore()+10.0f);
    		}
    		else if((d.getDeadlineTime()-CurrentTime)<3600000){
    			d.setScore(d.getScore()+5.0f);
    		}
    		else{
    			d.setScore(d.getScore()+1.0f);
    		}
    	}
    }
    
    private Runnable checker = new Runnable() {
       public void run() {
           Log.i("checkPOI:", ""); 
           ToDoManager.getInstance().resetAllScore();
           /*Score = priority + deadline(半小時以內10 一小時以內5 一小時以上1)
        		   +500m裡有地點 10 沒有0*/
           checkByTime();
           checkByPOI();
           ArrayList<ToDo> tmpl = ToDoManager.getInstance().getUserToDo();
           if(tmpl.size() == 0)
        	   return;
           for(int i =0;i<tmpl.size();i++){
        	   if(tmpl.get(i).getPOI()!=null){
        		   POIService.getInstance().setResultPOI(tmpl.get(i).getPOI());
        		   gNotification(tmpl.get(i).getPOI().getName(),"Near :",tmpl.get(i).getPOI().getName(),i);
        	   }
        	   else{
        		   tNotification(tmpl.get(i).getDescription(), "時間要到了", String.valueOf(tmpl.get(i).getDeadlineTime()), i);
        	   }
           }
           handler.postDelayed(checker, delaySec*1000);
        }
    };
        
}
