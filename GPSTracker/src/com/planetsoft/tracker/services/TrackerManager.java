package com.planetsoft.tracker.services;

import java.util.ArrayList;
import java.util.List;



import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class TrackerManager {

	public interface TrackerListener {

		public void onTrackerConnected();

		public void onTrackingStarted();

		public void onTrackingStopped();

		public void onTrackingStatus(boolean tracking);

		public void onTracksListRetrieved(List<Track> trackList);

		public void onTrackingError();
	}

	private Context mContext;
	private TrackerListener mTrackerListener;
	private Messenger mService;
	private boolean mIsBound = false;
	private Handler mClientHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case Tracker.MSG_TRACKING_STARTED:
				if (mTrackerListener != null)
					mTrackerListener.onTrackingStarted();
				break;

			case Tracker.MSG_TRACKING_STOPPED:
				if (mTrackerListener != null)
					mTrackerListener.onTrackingStopped();
				break;

			case Tracker.MSG_TRACKING_STATUS:
				if (mTrackerListener != null)
					mTrackerListener.onTrackingStatus(msg.arg1 != 0);
				break;

			case Tracker.MSG_TRACKS_LIST_RETRIEVED :
				ArrayList<Track> trackslist = msg.getData()
						.getParcelableArrayList("trackslist");
				if (mTrackerListener != null)
					mTrackerListener.onTracksListRetrieved(trackList);
				break;
			}
			super.handleMessage(msg);
		}

	};
	private Messenger mMessenger = new Messenger(mClientHandler);
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = new Messenger(service);
			if (mTrackerListener != null)
				mTrackerListener.onTrackerConnected();

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

			mService = null;

		}
	};

	public TrackerManager(Context context, TrackerListener listener) {
		super();
		this.mContext = context;
		this.mTrackerListener = listener;
		Intent serviceIntent = new Intent(mContext, Tracker.class);
		mContext.startService(serviceIntent);
		mContext.bindService(serviceIntent, mConnection,
				Context.BIND_AUTO_CREATE);
		mIsBound = true;

	}

	public void discount() {

		if (mIsBound) {
			mContext.unbindService(mConnection);
			mIsBound = false;

		}
	}

	private boolean sendMessage(Message msg) {
		msg.replyTo = mMessenger;
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			return false ;
		}
	}
	
	public boolean requestTrackingStatus(){
		Message msg= Message.obtain(null, Tracker.RQ_TRACKING_STATUS);
		return sendMessage(msg);
		
	}

	public boolean requestStartTracking(){
		Message msg= Message.obtain(null, Tracker.RQ_START_TRACKING,activityType.ordinal(),0);
		return sendMessage(msg);
		
	}
	
	public boolean requestStopTracking(String tracName){
		
		Message msg =Message.obtain(null,Tracker.RQ_STOP_TRACKING);
		msg.getData().putString("name",tracName);
		return sendMessage(msg);
	}
	
	public boolean requestTrackList(){
		Message msg =Message.obtain(null,Tracker.RQ_RETRIEVE_TRACKs_LIST);
		return sendMessage(msg);
		
		
	}
}
