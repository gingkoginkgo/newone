package com.example.newone;


//position of  interest

public class POI {
	private String _name;
	private double _lat;
	private double _lng;
	
	public POI(String name, double lat, double lng){
		_name = name;
		_lat = lat;
		_lng = lng;
	}
	
	public String getName(){
		return _name;
	}
	public double getLat(){
		return _lat;
	}
	public double getLng(){
		return _lng;
	}
}
