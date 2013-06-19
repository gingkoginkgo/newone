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
		    

		    //get CurrentTime
		    Calendar _calendar=Calendar.getInstance();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm");
            long CurrentTime = System.currentTimeMillis();
            
		    
		    //get All Deadline of ToDoList and compare CurrentTime and Deadline, pop Notification if Deadline<30 mins

            for(int i =0; i<_todoList.size();i++){
		    	if(_todoList.get(i).getDeadlineTime()-CurrentTime<1800000){
		    		 // notify!!
		    		//Notification
				    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		            Notification notification = new Notification(R.drawable.ic_launcher, _todoList.get(i).getDescription(), System.currentTimeMillis());
		            Intent intent = new Intent(Intent.ACTION_MAIN);
		            intent.setClass(getApplicationContext(), Reminder.class);
		            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
		            notification.setLatestEventInfo(getApplicationContext(), "快到deadline了!", _todoList.get(i).getDescription(), contentIntent);
		            notificationManager.notify(R.drawable.ic_launcher, notification);
		    	}
		    	
		    }           
            
   	    
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
    	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
        super.onStart(intent, startId);
    }
 
    @Override
    public void onDestroy() {
    	handler.removeCallbacks(checker);
    	super.onDestroy();
    }
    
    public void notificationByPOI(){
	    POIService _ps = POIService.getInstance();
	    ArrayList<POI> _result = new ArrayList<POI>();
	    for(ToDo t : _todoList){
	    	ArrayList<POI> _resulttmp = _ps.getNearByPOIs(_lat, _lon, t.getTarget_Place());
	    	_result.addAll(_resulttmp);
	    }
	    if(_result.size() == 0)				//check array size
	    	return;
	    POIService.getInstance().setResultPOI(_result.get(0));
	    //Notification
	    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_launcher, _result.get(0).getName(), System.currentTimeMillis());
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(getApplicationContext(), Reminder.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        notification.setLatestEventInfo(getApplicationContext(), "附近有", _result.get(0).getName(), contentIntent);
        notificationManager.notify(R.drawable.ic_launcher, notification);
    }
    private Runnable checker = new Runnable() {
       public void run() {
           Log.i("checkPOI:", ""); 
           notificationByPOI();
           handler.postDelayed(checker, delaySec*1000);
        }
    };
        
}
