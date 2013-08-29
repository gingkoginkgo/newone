package com.example.newone;

import java.util.ArrayList;
import org.json.*;

import android.util.Log;

public class POIService {
	private static POIService _instance = null;
	private String _apikey;
	private POI _resultPOI;

	private POIService() {
		_apikey = "AIzaSyAj1FoqemBG2Lj6Wu1mfGPu4ohhLbnESqI";
		_resultPOI = null;
	}

	public static POIService getInstance() {
		if (_instance == null) {
			_instance = new POIService();
		}
		return _instance;
	}

	public ArrayList<POI> getNearByPOIs(double lat, double lan, String target) {
		ArrayList<POI> _ret = new ArrayList<POI>();
		// search poi by location. Use Httpservices & google Place API.
		httpService _hs = new httpService();
		String _url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
		String _vaule = "location=" + lat + "," + lan + "&" + "radius=500"
				+ "&types=" + target + "&sensor=false" + "&key=" + _apikey;
		String _result = _hs.httpServiceGet(_url, _vaule);

		try {
			JSONObject j = new JSONObject(_result);

			for (int i = 0;; i++) {
				Object POIt = j.getJSONArray("results").get(i);
				if (POIt == null)
					break;

				Object _name = j.getJSONArray("results").getJSONObject(i)
						.get("name");
				String _lat = j.getJSONArray("results").getJSONObject(i)
						.getJSONObject("geometry").getJSONObject("location")
						.get("lat").toString();
				String _lan = j.getJSONArray("results").getJSONObject(i)
						.getJSONObject("geometry").getJSONObject("location")
						.get("lng").toString();
				POI tmpPOI = new POI((String) _name, Double.parseDouble(_lat),
						Double.parseDouble(_lan));
				_ret.add(tmpPOI);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		return _ret;
	}

	public void setResultPOI(POI p) {
		_resultPOI = p;
	}

	public POI getResultPOI() {
		return _resultPOI;
	}

}
