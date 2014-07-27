package com.example.hsr;




import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.protobuf.*;

import com.google.transit.realtime.*;



public class MainActivity extends Activity {
	


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
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
}
