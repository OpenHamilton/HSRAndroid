package com.example.hsr;




import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.protobuf.*;

import com.google.transit.realtime.*;



public class MapActivity extends FragmentActivity {
	
    private static final int GPS_UNDERDIALOG_REQUEST = 20140727;
    
    
	GoogleMap mMap;//Map initiaily is set to (0,0) which is 
	//the intersection of the Equator and the Prime Meridian
	//A location consists of latitude and longitude

	MapView mMapView;// it has lifecycle just like activity,override is needed
	//mapView object does not work with the fragment's API and it can be nested
	//within any other view.
	
	private static final double MACNAB_TERMINAL_LAT=43.257347,
	MACNAB_TERMINAL_LNG=-79.870709;
	
	private static final float DEFAULTZOOM=15;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        
        //if google play service is on , display map,otherwise, display default settings
        if(servicesOK()){
        	 setContentView(R.layout.activity_map);
        	 
        	// mMapView=(MapView) findViewById(R.id.map);
        	 
        	 //mMapView.onCreate(savedInstanceState);
        	    if(initMap()){
        	    	
        	    	Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();
        	    	gotoLocation(MACNAB_TERMINAL_LAT,MACNAB_TERMINAL_LNG,DEFAULTZOOM);
        	    	
        	    }else{
        	    	Toast.makeText(this, "Map not available!", Toast.LENGTH_SHORT).show();
        	    	
        	    }
        	 
        	 
        }else{
        	//setContentView(R.layout.activity_main);
        }
        
        
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        
//        InputStream is = null;
//		try {
//			is = new URL("http://opendata.hamilton.ca/GTFS-RT/GTFS_TripUpdates.pb").openStream();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        try {
//        	
//			Log.v("MainActivity",GtfsRealtime.FeedMessage.parseFrom(is).getEntityCount()+"");
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//         
    }
    
    
    
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
    	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}




	private void gotoLocation(double lat,
			double lng, float zoom) {
		// TODO Auto-generated method stub
    	
    	LatLng ll=new LatLng(lat,lng);
    	CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
    	mMap.moveCamera(update);
		
	}

	public boolean servicesOK(){
    	
    	//check whether google play services are available
    	int isAvailable=GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    	
    	if(isAvailable==ConnectionResult.SUCCESS){
    		//google play service apk is installed, return true
    		return true;
    		
    		//evaluate the error whether it can be corrected
    	}else if(GooglePlayServicesUtil.isUserRecoverableError(isAvailable)){
    		
    		//determine what error message is displayed to user
    		Dialog dialog=GooglePlayServicesUtil.getErrorDialog(isAvailable, this,
    				GPS_UNDERDIALOG_REQUEST);
    		dialog.show();
    		
    	}else{//if error is not recoverable
    		
    		Toast.makeText(this,"Can not connect to Google Play services"
    				,Toast.LENGTH_SHORT).show();
    		
    	}
    	
    	return false;
    	
    }
    
    private boolean initMap(){
    	if(mMap==null){
    		
    		//get the mapFrag --reference to the map fragment
    		SupportMapFragment mapFrag=
    				(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    		
    		mMap=mapFrag.getMap();//get reference to the map
    		
    	}
    	
    	return (mMap!=null);
    	
    }
    
    private void gotoLocation(double lat,double lng){
    	LatLng ll=new LatLng(lat,lng);
    	CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
    	mMap.moveCamera(update);
    }
    
    public void geoLocate(View v) throws IOException {
		hideSoftKeyboard(v);
		
		EditText et = (EditText) findViewById(R.id.editText1);
		String location = et.getText().toString();
		Geocoder gc=new Geocoder(this);
		List<Address> list =gc.getFromLocationName(location, 1);
		Address add=list.get(0);
		String locality =add.getLocality();
		
		Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
		
		double lat=add.getLatitude();
		double lng=add.getLongitude();
		
		gotoLocation(lat,lng,DEFAULTZOOM);
	
	}
	
	private void hideSoftKeyboard(View v) {
		InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
    
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		//mMapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		//mMapView.onLowMemory();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//mMapView.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//mMapView.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		//mMapView.onSaveInstanceState(outState);
	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		
//		
//		switch(item.getItemId()){
//		
//		case R.id.mapTypeNone:
//			mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
//			break;
//		case R.id.mapTypeNormal:
//			mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//			break;
//		case R.id.mapTypeSatellite:
//			mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//			break;
//		case R.id.mapTypeTerrain:
//			mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//			break;
//		case R.id.mapTypeHybrid:
//			mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
//			break;
//		
//		
//		}
//		return super.onOptionsItemSelected(item);
//	}
	
	
    
    
}
