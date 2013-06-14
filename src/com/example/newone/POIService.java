package com.example.newone;

import java.util.ArrayList;

public class POIService {
	
	private String _apikey;
	
	public POIService(){
		_apikey = "AIzaSyDsVduKQ1Yv8f8Zmfe7Dw7e_cTsUYYO6EU";
	}
	public ArrayList<POI> getNearByPOIs(double lat, double lan, String target){
		ArrayList<POI> _ret = new ArrayList<POI>();
		//search poi by location. Use Httpservices & google Place API.
		
		 httpService _hs = new httpService();
		 String _url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
		 String _vaule = "location="+lat+","+lan+"&"
				         +"radius=500"+"&types="+target+"&sensor=false"+"&key="+_apikey;
		 String _result = _hs.httpServiceGet(_url, _vaule);
		
		
		
		return _ret;
	}

}
