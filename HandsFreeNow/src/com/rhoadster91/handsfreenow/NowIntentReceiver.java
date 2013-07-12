package com.rhoadster91.handsfreenow;

import wei.mark.standout.StandOutWindow;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

public class NowIntentReceiver extends BroadcastReceiver {
	static long firstElapsed = -1;
	long currentElapsed;
	static int count = 0;
	final static int MAX_DELAY = 1000;
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		String intentAction = intent.getAction();
		if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) 
		{
            KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            
            if (event == null) 
            {
                return;
            }

            int keycode = event.getKeyCode();
            if(firstElapsed == -1)
            {
            	firstElapsed = System.currentTimeMillis();
            	count = 0;
            }
            currentElapsed = System.currentTimeMillis();
            long duration = currentElapsed - firstElapsed;
            if(duration>0 && duration < MAX_DELAY && keycode==KeyEvent.KEYCODE_HEADSETHOOK)
            	count++;
            if(duration>MAX_DELAY)
            {
            	firstElapsed = -1;
            	count = 0;
            }               
            Log.d("EVENT", ""+keycode + " " + count);            
    		if(count==3)
    		{
    			StandOutWindow.show(context, LockScreenNowService.class, StandOutWindow.DEFAULT_ID);
    			count = 0;
    			firstElapsed = -1;
       		}           
		}
	}
}
