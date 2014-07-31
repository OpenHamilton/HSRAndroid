package com.fragments.hsr;

import android.app.Activity;
import android.app.ActionBar;
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
import com.example.hsr.*;

public class TabsPagerAdapter extends FragmentPagerAdapter  {

	
	
	private String []tabs={
			"Route",
			"Map",
			"Account"
	};
	
	RouteListFragment routelistfragment;
	MapViewFragment mapviewfragment;
	AccountManageFragment accountmanagefragment;
	
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		
		routelistfragment=new RouteListFragment();
		mapviewfragment=new MapViewFragment();
		accountmanagefragment=new AccountManageFragment();
		
	}

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		
		switch(index){
		
		case 0:
			
			return routelistfragment;
			
			
		case 1:
			
			return mapviewfragment;
			
		case 2:
			
			return accountmanagefragment;
			
		
		}
		
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return tabs[position];
	}
	
	
	public void destoryFragments(){
		
//		if(routelistfragment!=null)
//			routelistfragment.getActivity().
//			getFragmentManager().beginTransaction().remove(routelistfragment).commit();
//		
//		if(mapviewfragment!=null)
//			mapviewfragment.getActivity().
//			getFragmentManager().beginTransaction().remove(mapviewfragment).commit();
//		
//		if(accountmanagefragment!=null)
//			accountmanagefragment.getActivity().
//			getFragmentManager().beginTransaction().remove(accountmanagefragment).commit();
		
		
		
	}


}
