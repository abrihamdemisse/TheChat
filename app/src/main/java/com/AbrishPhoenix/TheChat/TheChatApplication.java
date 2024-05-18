package com.AbrishPhoenix.TheChat;

import android.app.*;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.os.Build;
import android.util.Log;
import android.content.ComponentCallbacks2;
import android.app.ActivityManager;
import java.util.List;
import java.util.Locale;
import android.os.LocaleList;
import com.google.firebase.auth.FirebaseAuth;
import android.content.res.Configuration;
import java.util.HashMap;
import java.lang.CharSequence;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import androidx.appcompat.app.AppCompatDelegate;
// import com.google.android.gms.ads.MobileAds;
// import com.google.android.gms.ads.initialization.InitializationStatus;
// import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class TheChatApplication extends Application {
	
    private static Context mApplicationContext;
    public static String CHANNEL_ID = "Downloads";
	private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
	
    public static Context getContext() {
        return mApplicationContext;
    }
    
	@Override
	public void onCreate() {
        mApplicationContext = getApplicationContext();
		// PreferenceHelper.setDefaultSettings(getBaseContext());
        // Picasso.setSingletonInstance(new Picasso.Builder(getBaseContext()).build());
		
		try{
		    createNotificationChannel();
		}catch(Exception e){}
		
        try{
            // com.google.firebase.database.FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            // com.google.firebase.database.FirebaseDatabase.getInstance().getReference().keepSynced(true);
        }catch(Exception e){}
		
		this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable throwable) {
				Intent intent = new Intent(getBaseContext(), DebugActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.putExtra("error", Log.getStackTraceString(throwable));
				PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 11111, intent, PendingIntent.FLAG_ONE_SHOT);
				
				AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, pendingIntent);
				
				Process.killProcess(Process.myPid());
				System.exit(1);
				
				uncaughtExceptionHandler.uncaughtException(thread, throwable);
			}
		});
        SketchLogger.startLogging();
		super.onCreate();
	}
	
	@Override
	public void attachBaseContext(Context cxt) {
		super.attachBaseContext(cxt);
	}
    
	/*
	public void createNotificationChannels() {
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = "Quick access";
			String description = "Notification to quickly access Portal AI.";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel((String)name, name, importance);
			channel.setDescription(description);
			channel.enableLights(false);
			channel.setShowBadge(false);
			channel.setSound(null,null);
			// Register the channel with the system; you can't change the importance
			// or other notification behaviors after this
			NotificationManager notificationManager = getBaseContext().getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = "Speech service";
			String description = "Service for text to speech functionality.";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel((String)name, name, importance);
			channel.setDescription(description);
			channel.enableLights(false);
			channel.setShowBadge(false);
			channel.setSound(null,null);
			// Register the channel with the system; you can't change the importance
			// or other notification behaviors after this
			NotificationManager notificationManager = getBaseContext().getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}
	*/
    
	@Override
	public void onTrimMemory (int level) {
		
		/*
		if(level >= ComponentCallbacks2.TRIM_MEMORY_COMPLETE) {
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE); 
		if(am != null) {
		List<ActivityManager.AppTask> tasks = am.getAppTasks();
		if (tasks != null && tasks.size() > 0) {
		SketchwareUtil.showMessage(getBaseContext(), "Excute in Application");
		tasks.get(0).setExcludeFromRecents(true);
		}
		}
		} */
		super.onTrimMemory(level);
	}
    
    private void createNotificationChannel() {
		// Create the NotificationChannel, but only on API 26+ because
		// the NotificationChannel class is new and not in the support library
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			CharSequence name = CHANNEL_ID;
			String description = "Downloader channel";
			int importance = NotificationManager.IMPORTANCE_DEFAULT;
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
			channel.setDescription(description);
			//channel.setSound(null,null);
			// Register the channel with the system; you can't change the importance
			// or other notification behaviors after this
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}
	
}
