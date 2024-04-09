package com.example.addictlock;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.ListFragment;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Calendar;

/**
 * Created by USER on 1/29/2017.
 */
public class Appservice extends Service {

    Context context = this;
    public Appservice()
    {}



    private boolean isRunning  = false;
    final String TAG = Appservice.class.getSimpleName();
    long milliseconds = 0;

    @Override
    public void onCreate() {

        isRunning = true;
        Log.d(TAG, "Created service");
    }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId){
            Log.d(TAG, "Started service");

            new AddictLockApp().execute();

            return Service.START_STICKY;
        }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        Log.d(TAG, "Destroy service");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        Log.d(TAG, "TASK REMOVED");
        final Intent intent = new Intent(getApplicationContext(), Appservice.class);
        startService(intent);
        Log.d(TAG, "Restarted service");

        PendingIntent service = PendingIntent.getService(
                getApplicationContext(),
                1001,
                new Intent(getApplicationContext(), Appservice.class),
                PendingIntent.FLAG_ONE_SHOT);

        Log.d(TAG, "Pending Intent");

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, service);

        Log.d(TAG, "Alarm fired");
    }

    class AddictLockApp extends AsyncTask<String, Void, String>
    {

        final String TAG = AddictLockApp.class.getSimpleName();



        protected String doInBackground(String...  urls) {
            Log.d(TAG, "Async Task");
            String currentApp,previousApp = "";
            while (true)
            {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
                    long endTime = System.currentTimeMillis();
                    long beginTime = endTime - 1000 * 60;
                    Log.v(TAG, "Inside Usage stats");
                    // result
                    String topActivity = null;

                    // We get usage stats for the last minute
                    List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, beginTime, endTime);

                    // Sort the stats by the last time used
                    if (stats != null) {
                        Log.v(TAG, "Inside stats");
                        SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                        for (UsageStats usageStats : stats) {
                            mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                        }
                        if (mySortedMap != null && !mySortedMap.isEmpty()) {
                            topActivity = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                            Log.v(TAG, "foreground activity is " + topActivity);
                            currentApp = topActivity;
                            if (!currentApp.equals(previousApp)) {
                                if (!(currentApp.equalsIgnoreCase("com.example.addictlock"))){
                                    checklock(currentApp);
                                }
                                else
                                {
                                    Log.v(TAG, "foreground activity is " + topActivity);
                                }
                            }

                            Log.v(TAG, "current app " + currentApp + " previous app " + previousApp);
                            previousApp = topActivity;
                        }
                    }

                } else {
                    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                    List<ActivityManager.RunningAppProcessInfo> tasks = am
                            .getRunningAppProcesses();
                    currentApp = tasks.get(0).processName;

                    if (currentApp.length() != 0) {
                        Log.d(TAG, "App is running : " + currentApp);
                    }

                }
            }
        }
    }

    void checklock(String process) {
        Log.d(TAG, "Inside check lock");
        SQliteDB db = new SQliteDB(this);
        List<Apprecord> Apps = db.getAllApps();
        db.close();

        for (Apprecord app : Apps) {
            Log.d(TAG, "package name : " + app.getpackagename());
            if (app.getpackagename().equals(process)) {
                if (app.getIsAddictlocked().equals("true")){
                    boolean isLaunchAddictLock = checkaddictlock(app);
                    if (isLaunchAddictLock)
                    {
                        String MS = "";
                        launchaddictlock(MS.valueOf(milliseconds));
                    }
                }
                else if (app.getisApplocked().equals("true")) {
                    launchapplock();
                }
            }
        }
    }

    void launchapplock()
    {
        Log.d(TAG, "Innside Launch App Lock");
        try {
            Intent applockintent = new Intent(getApplicationContext(), AppLock.class);
            applockintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(applockintent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void launchaddictlock(String milliseconds)
    {
        Log.d(TAG, "Inside Launch Addict Lock");
        try {
            Intent addictlockintent = new Intent(getApplicationContext(), AddictLock.class);
            addictlockintent.putExtra("MilliSeconds", milliseconds);
            addictlockintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(addictlockintent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    boolean checkaddictlock(Apprecord app) {
        Log.d(TAG, "Inside check addict lock");
        boolean addictLock = false;
        DateFormat df;
        Calendar cal;
        String scurrtime;
        int hour,min;
        Date dstarttime, dendtime, dcurrtime;
        try {
            cal = Calendar.getInstance();
            df = new java.text.SimpleDateFormat("hh:mm:ss");
            String starttime = app.getStartTime();
            String endtime = app.getEndtime();
            starttime = starttime + ":00";
            endtime = endtime + ":00";
            dstarttime =df.parse(starttime);
            dendtime = df.parse(endtime);
            long diff = dendtime.getTime() - dstarttime.getTime();// diff in milliseconds to seed the counter
            hour= cal.get(Calendar.HOUR);
            min = cal.get(Calendar.MINUTE);
            scurrtime = String.valueOf(hour) + ":" + String.valueOf(min) + ":00";
            dcurrtime = df.parse(scurrtime);
            String days = app.getDaysRepeat();
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if (days.charAt(day-1) == '1')
            {
                if ((dstarttime.compareTo(dcurrtime)<=0)&&(dcurrtime.compareTo(dendtime)<=0))
                {
                    milliseconds = dendtime.getTime() - dcurrtime.getTime();
                    addictLock = true;
                }
            }
    }

    catch (Exception e)
    {
        e.printStackTrace();
    }
        return addictLock;
    }
}
