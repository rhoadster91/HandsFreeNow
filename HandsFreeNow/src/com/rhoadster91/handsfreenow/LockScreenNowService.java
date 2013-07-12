package com.rhoadster91.handsfreenow;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.speech.RecognizerIntent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import wei.mark.standout.StandOutWindow;
import wei.mark.standout.constants.StandOutFlags;
import wei.mark.standout.ui.Window;

public class LockScreenNowService extends StandOutWindow 
{

	@Override
	public String getAppName() 
	{
		return getString(R.string.app_name);		
	}

	@Override
	public int getAppIcon() 
	{
		return R.drawable.ic_launcher;
	}
	@SuppressWarnings("deprecation")	
	@Override
	public void createAndAttachView(int id, FrameLayout frame) 
	{
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.lock_guard, frame, true);
		
		final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "bbbb");
		KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		km.newKeyguardLock("").disableKeyguard();
		wl.acquire();
		wl.release();
		km.newKeyguardLock("").reenableKeyguard();
		Intent intent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		new AsyncTask<Void, Void, Void>()
		{

			@Override
			protected Void doInBackground(Void... arg0) 
			{
				try 
				{
					Thread.sleep(10000);
				}
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) 
			{
				android.os.Process.killProcess(android.os.Process.myPid());
				super.onPostExecute(result);
			}
			
			
		}.execute();
		
	}

	@Override
	public StandOutLayoutParams getParams(int id, Window window) 
	{
		DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics(); 
		return new StandOutLayoutParams(id, displayMetrics.widthPixels, displayMetrics.heightPixels, StandOutLayoutParams.CENTER, StandOutLayoutParams.CENTER);
	}
	
	@Override
	public int getFlags(int id) 
	{
		return super.getFlags(id) | StandOutFlags.FLAG_WINDOW_FOCUSABLE_DISABLE;
	}
	
	@Override
	public String getPersistentNotificationMessage(int id) 
	{
		return "Close";
	}

	@Override
	public Intent getPersistentNotificationIntent(int id) 
	{
		return StandOutWindow.getCloseIntent(this, LockScreenNowService.class, id);
	}	

}
