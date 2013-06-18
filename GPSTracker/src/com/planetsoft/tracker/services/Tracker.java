package com.planetsoft.tracker.services;

import java.io.FileOutputStream;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;

public class Tracker extends Service implements LocationListener{
	
	public static final int RQ_START_TRACKING=1;
	public static final int RQ_STOP_TRACKING=2; 
	public static final int RQ_TRACKING_STATUS=3; 
	public static final int RQ_RETRIEVE_TRACKs_LIST=4; 
	public static final int MSG_TRACKING_STARTED=1; 
	public static final int MSG_TRACKING_STOPPED=2; 
	public static final int MSG_TRACKING_STATUS=3; 
	public static final int MSG_TRACKs_LIST=4;
	
	public static final String  TRACES_DIRECTORY= "tracks";
	public static final String  TRACKS_List= "tracks_list";
	private static class TrackingInfos{
		
		FileOutputStream mOut;
		String mFilePath ;
		ActivityType mActivityType ;
		Date mDAte;
		float mDistance ;
		Location mFirstLocation ;
		Location mLastLocation ;
		String mName ;
		public TrackingInfos(FileOutputStream mOut, String mFilePath,
				ActivityType mActivityType, Date mDAte) {
			super();
			this.mOut = mOut;
			this.mFilePath = mFilePath;
			this.mActivityType = mActivityType;
			this.mDAte = mDAte;
		}
		
	}


	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
