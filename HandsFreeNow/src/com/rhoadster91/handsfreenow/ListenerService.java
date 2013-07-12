package com.rhoadster91.handsfreenow;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

public class ListenerService extends Service {

	@Override
	public IBinder onBind(Intent arg0) 
	{
		return null;
	}

	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		AudioManager mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        ComponentName rec = new ComponentName(getPackageName(), NowIntentReceiver.class.getName());
        mAudioManager.registerMediaButtonEventReceiver(rec);
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        mediaButtonIntent.setComponent(rec);        
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		Log.d("TEST", "TEST");
		return START_STICKY;
	}
	

}
