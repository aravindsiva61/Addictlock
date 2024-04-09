package com.example.addictlock;

import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String TAG = MainActivity.class.getSimpleName();
    final String sFirstPassword = "Firstpassword";
    final String sSecondPassword = "Secondpassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "Inside Activity");

        final Intent Serviceintent = new Intent(getApplicationContext(), Appservice.class);
        startService(Serviceintent);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(!hasPermission()){
                Log.d(TAG, "No permission");
                Intent settingsintent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivity(settingsintent);
               try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
            else
            {
                Log.d(TAG, "Has permission");
            }
        }

        PackageManager pm = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.PERMISSION_GRANTED);
        ArrayList<String> results = new ArrayList<String>();
        ArrayList<Drawable> appicons = new ArrayList<Drawable>();
        SQliteDB db = new SQliteDB(this);

        for (ResolveInfo rInfo : list)
        {
            String spackage = rInfo.activityInfo.applicationInfo.packageName;
            Apprecord app = db.getAppDetails(spackage);
            if(app == null)
            {
                Apprecord newapp = new Apprecord(spackage,
                        rInfo.activityInfo.applicationInfo.loadLabel(pm).toString(), "false", "false", "starttime","endtime" , "days");
                db.addApp(newapp);
                Log.d(TAG, spackage + "is added in DB");
            }
            else {
                Log.d(TAG, spackage + "is in DB");
                Log.d(TAG, "Appname : " + app.getappname() + "packagename : " + app.getpackagename() +
                        "Applockstate : " + app.getisApplocked() + "Addictlockstate : " + app.getIsAddictlocked());       }
        }

        ToggleButtonListAdapter adapter = new ToggleButtonListAdapter(this, list);
        ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);
        lv.setDivider(null);
        Log.d(TAG, "Inside Activity");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

            }
        });
    }

    public class ToggleButtonListAdapter extends ArrayAdapter<ResolveInfo> {
        private final Context context;

        private final List<ResolveInfo> appinfo;
        PackageManager pmgr ;
        SQliteDB db;


        public ToggleButtonListAdapter(Context context, List<ResolveInfo> list) {
            super(context, R.layout.activity_main, list);
            this.context = context;
            this.appinfo = list;
            this.pmgr = context.getPackageManager();
            this.db = new SQliteDB(context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d("Getview", "called");
            //PackageManager pm = context.getPackageManager();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.row_layout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.txt);
            final Switch appswitch = (Switch) rowView.findViewById(R.id.applock);
            final Switch addictswitch = (Switch) rowView.findViewById(R.id.addictlock);
            final ResolveInfo data = appinfo.get(position);
            final String spackage = data.activityInfo.applicationInfo.packageName;
            final Apprecord app = db.getAppDetails(spackage);
            Log.d(TAG, spackage + "is in DB");
            if(app == null)
            {
                Apprecord newapp = new Apprecord(spackage,
                        data.activityInfo.applicationInfo.loadLabel(pmgr).toString(), "false", "false", "starttime","endtime" , "days");
                db.addApp(newapp);
                Log.d(TAG, spackage + "is added in DB");
            }
            else
            {
                String applockstate = app.getisApplocked();
                String addictlockstate = app.getIsAddictlocked();
                Log.d(TAG, spackage + "already in DB - " + applockstate);
                if (applockstate.equals("true"))
                {
                    appswitch.setChecked(true);
                }
                else
                {
                    appswitch.setChecked(false);
                }

                if (addictlockstate.equals("true"))
                {
                    addictswitch.setChecked(true);
                }
                else
                {
                    addictswitch.setChecked(false);
                }
            }

            ImageView imageView = (ImageView)  rowView.findViewById(R.id.img);


            appswitch.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (appswitch.isChecked())
                    {
                        Toast.makeText(getContext(), data.activityInfo.applicationInfo.loadLabel(pmgr) + " app lock" + " ON", Toast.LENGTH_LONG).show();
                        app.setisApplocked("true");
                        db.updateApp(app);
                        Apprecord temp = db.getAppDetails(spackage);
                        Log.d(TAG, spackage + " - state - " + temp.getisApplocked());
                    }
                    else {

                        Toast.makeText(getContext(), data.activityInfo.applicationInfo.loadLabel(pmgr) + " app lock" + " OFF", Toast.LENGTH_LONG).show();
                        app.setisApplocked("false");
                        db.updateApp(app);
                    }
                }
            });

            addictswitch.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (addictswitch.isChecked()) {
                        app.setIsAddictlocked("true");
                        db.updateApp(app);
                        try {
                            Intent applockintent = new Intent(MainActivity.this, LockPreferences.class);
                            applockintent.putExtra("PackageName", spackage);
                            startActivity(applockintent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getContext(), data.activityInfo.applicationInfo.loadLabel(pmgr) + "addict lock" + "ON", Toast.LENGTH_LONG).show();
                    }
                    else{
                        app.setIsAddictlocked("false");
                        db.updateApp(app);
                        Toast.makeText(getContext(), data.activityInfo.applicationInfo.loadLabel(pmgr) + "addict lock" + "OFF", Toast.LENGTH_LONG).show();
                    }
                }
            });

            textView.setText(data.activityInfo.applicationInfo.loadLabel(pmgr));
            imageView.setImageDrawable(data.activityInfo.applicationInfo.loadIcon(pmgr));
            Log.d("Getview", "returned");
            return rowView;
        }
    }

    private boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager)
                getSystemService(Context.APP_OPS_SERVICE);
        int mode = 0;
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), getPackageName());
        }
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    private boolean isPasswordSet()
    {
        return false;
    }

    private void setpassword()
    {
        Intent FisrtPasswordintent = new Intent(MainActivity.this, AppLock.class);
        FisrtPasswordintent.putExtra("PassConfig", sFirstPassword);
        startActivity(FisrtPasswordintent);

        Intent SecondPasswordIntent = new Intent(MainActivity.this, AppLock.class);
        SecondPasswordIntent.putExtra("PassConfig", SecondPasswordIntent);
        startActivity(SecondPasswordIntent);
    }
}
