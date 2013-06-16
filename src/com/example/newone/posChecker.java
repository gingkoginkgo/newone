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
	private final double EARTH_RADIUS = 6378137.0;
	ArrayList<ToDo> _todoList = ToDoManager.getInstance().getUserToDo();
	
	private final LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
		    _lat = location.getLatitude();
		    _lon = location.getLongitude();
		    
		    
		    //compare CurrentTime and Deadline.
		    //if Deadline is closed enough(maybe 30 mins) ,also jump Notification	    
		   
		    //get CurrentTime
		    Calendar _calendar=Calendar.getInstance();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm");
            
            //because in Java , month start from 0 , so +1
            int CurrentTime = _calendar.get(Calendar.YEAR)
            		        + (_calendar.get(Calendar.MONTH)+1)
            				+ _calendar.get(Calendar.DAY_OF_MONTH)
            				+ _calendar.get(Calendar.HOUR_OF_DAY)
            				+ _calendar.get(Calendar.MINUTE);
            
		    
		    //get All Deadline of ToDoList
		    ArrayList<String> deadLines = new ArrayList<String>();
            for(int i =0; i<_todoList.size();i++)
		    {
		    //	deadLines.add(_todoList.get(i).getDeadlineTime());
		    }           
            
            //if (Deadline - CurrentTime) <30 mins , jump CurrentTime
		    
            
            
            
		    
		    //maybe can change to run function is better
		    POIService _ps = POIService.getInstance();
	//	    ArrayList<ToDo> _todoList = ToDoManager.getInstance().getUserToDo();
		    ArrayList<POI> _result = new ArrayList<POI>();
		    for(ToDo t : _todoList){
		    	ArrayList<POI> _resulttmp = _ps.getNearByPOIs(_lat, _lon, t.getTarget_Place());
		    	_result.addAll(_resulttmp);
		    }
		    if(_result.size() == 0)				//check array size
		    	return;
		    POIService.getInstance().setResultPOI(_result.get(0));
		    /////////////////////////////////////////////

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
    	handler.postDelayed(showTime, 1000);
    	
    	//Get LocationManager
    	LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
        super.onStart(intent, startId);
    }
 
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
    public double checkDis(double lat_a, double lng_a, double lat_b, double lng_b){
    	double radLat1 = (lat_a * Math.PI / 180.0);
    	double radLat2 = (lat_b * Math.PI / 180.0);
    	double a = radLat1 - radLat2;
    	double b = (lng_a - lng_b) * Math.PI / 180.0;
    	double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
    	+ Math.cos(radLat1) * Math.cos(radLat2)
    	* Math.pow(Math.sin(b / 2), 2)));
    	s = s * EARTH_RADIUS;
    	s = Math.round(s * 10000) / 10000;
    	return s;
    }
     
    private Runnable showTime = new Runnable() {
       public void run() {
    	   //check distance
           //Log.i("Distance:", ""+checkDis(30.0, -121.0, _lat, _lon)); 
           handler.postDelayed(this, 1000);
        }
    };
        
}
