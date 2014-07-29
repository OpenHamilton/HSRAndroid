package com.fragments.hsr;

import com.example.hsr.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MapViewFragment extends Fragment{
	
	MapView mapView;
	GoogleMap map;
	View v;
	 private static final int GPS_UNDERDIALOG_REQUEST = 20140727;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		    v=inflater.inflate(R.layout.fragment_mapview,container,false);
		    
		    if(servicesOK()){
		    	mapView=(MapView)v.findViewById(R.id.mapview);
		    	mapView.onCreate(savedInstanceState);
		    	map=mapView.getMap();
		    	map.getUiSettings().setMyLocationButtonEnabled(true);
		    	map.setMyLocationEnabled(true);
		    	
		    	MapsInitializer.initialize(this.getActivity());
			
		    }
		
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
	}
	
	
	

}
