package com.fragments.hsr;

import java.io.IOException;
import java.util.List;

import com.example.hsr.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MapViewFragment extends Fragment implements 
OnClickListener,GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener{
	
	MapView mapView;
	GoogleMap map;
	View v;
	 private static final int GPS_UNDERDIALOG_REQUEST = 20140727;
	 private static final float DEFAULTZOOM=15;
	 
	 LocationClient locationClient;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		    v=inflater.inflate(R.layout.fragment_mapview,container,false);
		    
		    if(servicesOK()){
		    	
		    	mapView=(MapView)v.findViewById(R.id.mapview);
		    	mapView.onCreate(savedInstanceState);
		    	
		    	if(initMap()){
		    		
		    		map.getUiSettings().setMyLocationButtonEnabled(true);
			    	map.setMyLocationEnabled(false);
			    	
			    	locationClient =new LocationClient(this.getActivity(),this,this);
			    	locationClient.connect();
			    	
		    		
		    	}
		    }
		
		    Button go_btn=(Button)v.findViewById(R.id.button_go);
		    go_btn.setOnClickListener(this);
		    
		    
		        
		   return v;
	
		
	}
	public boolean servicesOK(){
    	
    	//check whether google play services are available
    	int isAvailable=GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
    	
    	if(isAvailable==ConnectionResult.SUCCESS){
    		//google play service apk is installed, return true
    		return true;
    		
    		//evaluate the error whether it can be corrected
    	}else if(GooglePlayServicesUtil.isUserRecoverableError(isAvailable)){
    		
    		//determine what error message is displayed to user
    		Dialog dialog=GooglePlayServicesUtil.getErrorDialog(isAvailable, getActivity(),
    				GPS_UNDERDIALOG_REQUEST);
    		dialog.show();
    		
    	}else{//if error is not recoverable
    		
    		Toast.makeText(getActivity(),"Can not connect to Google Play services"
    				,Toast.LENGTH_SHORT).show();
    		
    	}
    	
    	return false;
    	
    }
	
	 private void gotoLocation(double lat,double lng){
		 
	    	LatLng ll=new LatLng(lat,lng);
	    	CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
	    	map.moveCamera(update);
	 }
	    
	 
	 private void gotoLocation(double lat,
				double lng, float zoom) {
			// TODO Auto-generated method stub
	    	
	    	LatLng ll=new LatLng(lat,lng);
	    	CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll,zoom);
	    	map.moveCamera(update);
			
	}
	 public void geoLocate() throws IOException {
			
			EditText et = (EditText) v.findViewById(R.id.editText_location);
			String location = et.getText().toString();
			
			if(location.length()==0){
				Toast.makeText(this.getActivity(), "Please enter a location", Toast.LENGTH_SHORT);
				return ;
			}
			Geocoder gc=new Geocoder(this.getActivity());
			
			List<Address> list =gc.getFromLocationName(location, 1);
			
			
			Address add=list.get(0);
			
			String locality =add.getLocality();
			
			Toast.makeText(this.getActivity(), locality, Toast.LENGTH_LONG).show();
			
			double lat=add.getLatitude();
			double lng=add.getLongitude();
			
			gotoLocation(lat,lng,DEFAULTZOOM);
			
			MarkerOptions option=new MarkerOptions().
					title(locality).
					position(new LatLng(lat,lng)).rotation(0.0f).flat(true);
			map.addMarker(option);
			
		
		}
	
	 private void hideSoftKeyboard() {
			InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
		}
	 
	 protected void goToCurrentLocation(){
		 
		 Location currentLocation=locationClient.getLastLocation();
		 
		 if(currentLocation==null){
			 
			 Toast.makeText(this.getActivity(),"current location is not available",Toast.LENGTH_SHORT).show();
			
		 }else{
			 
			 LatLng ll =new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
			 CameraUpdate update=CameraUpdateFactory.newLatLngZoom(ll, DEFAULTZOOM);
		 }
	 }
	
	 private boolean initMap(){
		 
		 	if(map==null){
		 		map=mapView.getMap();
		 	    MapsInitializer.initialize(this.getActivity());
		 	}
		 	
		 	return (map!=null);
	 }
	
	
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		MapStateManager mgr= new MapStateManager(this.getActivity());
		mgr.saveMapState(map);
		
	}
	@Override
	public void onDestroy() {
		
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		mapView.onLowMemory();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapView.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		mapView.onResume();
		
		MapStateManager mgr =new MapStateManager(getActivity());
		CameraPosition position=mgr.getSavedCameraPosition();
		
		if(position !=null){
			
			CameraUpdate update =CameraUpdateFactory.newCameraPosition(position);
			map.moveCamera(update);
			
		}
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.button_go:
			try {
				hideSoftKeyboard();
				geoLocate();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
		
		
		}
		
	}
	
	//fail in connection
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}
	
	//success in connection
	@Override
	public void onConnected(Bundle arg0) {
		
		Toast.makeText(this.getActivity(), "connected to location server", Toast.LENGTH_SHORT).show();
		
		LocationRequest request=LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
		request.setInterval(10000);
		request.setFastestInterval(1000);
		locationClient.requestLocationUpdates(request,this);
	}
	
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		String msg="Location :"+location.getLatitude()+"," +location.getLongitude();
		
		if(this.getActivity()!=null)
			Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
		
	}
	
	
	

}
