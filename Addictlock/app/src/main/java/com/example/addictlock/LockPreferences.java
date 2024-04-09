package com.example.addictlock;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 11/12/2017.
 */
public class LockPreferences extends Activity {

    int starttimehour, starttimemin, endtimehour, endtimemin;
    String starttime = "starttime";
    String endtime = "endtime";
    String defaultcolorcode = "#000000";
    String  setbtncolorcode = 	"#8b814c";
    String TAG = MainActivity.class.getSimpleName();
    Map<String,Integer> state = new HashMap<String,Integer>();
    SQliteDB db = new SQliteDB(this);
    String sdaysrepeat,sstarttime, sendtime;
    StringBuilder ssettingsdays;


    TimePicker timepick;
    EditText etxtstarttime;
    Button btnstarttime;
    EditText etxtendtime;
    Button btnendtime;
    Button btnmon;
    Button btntue;
    Button btnwed;
    Button btnthu;
    Button btnfri;
    Button btnsat;
    Button btnsun;
    Button btnOK;
    Button btnCancel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_preferences);
        final String spackgage = getIntent().getStringExtra("PackageName");
        timepick = (TimePicker)findViewById(R.id.timePicker);
        etxtstarttime = (EditText) findViewById(R.id.startinputtext);
        btnstarttime = (Button) findViewById(R.id.startbtn);
        etxtendtime = (EditText) findViewById(R.id.endinputtext);
        btnendtime = (Button) findViewById(R.id.endtbtn);
        btnmon = (Button) findViewById(R.id.mon);
        btntue = (Button)  findViewById(R.id.tue);
        btnwed = (Button)  findViewById(R.id.wed);
        btnthu = (Button)  findViewById(R.id.thu);
        btnfri = (Button)  findViewById(R.id.fri);
        btnsat = (Button)  findViewById(R.id.sat);
        btnsun = (Button)  findViewById(R.id.sun);
        btnOK = (Button) findViewById(R.id.OKbtn);
        btnCancel = (Button) findViewById(R.id.Cancelbtn);

        Init(spackgage);
        Apprecord daysrecord = db.getAppDetails(spackgage);
        String sdays = daysrecord.getDaysRepeat();
        if (!(sdays.equalsIgnoreCase("days"))){
            ssettingsdays = new StringBuilder(sdays);
        }
        else{
            ssettingsdays = new StringBuilder("0000000");
        }


        btnstarttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    starttimehour = timepick.getHour();
                    starttimemin = timepick.getMinute();
                }
                else
                {
                    starttimehour = timepick.getCurrentHour();
                    starttimemin = timepick.getCurrentMinute();
                }

                starttime = Integer.toString(starttimehour) + ":" + Integer.toString(starttimemin);
                etxtstarttime.setText(starttime);
            }
        });

        btnendtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    endtimehour = timepick.getHour();
                    endtimemin = timepick.getMinute();
                }
                else
                {
                    endtimehour = timepick.getCurrentHour();
                    endtimemin = timepick.getCurrentMinute();
                }

                endtime = Integer.toString(endtimehour) + ":" + Integer.toString(endtimemin);
                etxtendtime.setText(endtime);
            }
        });

        btnmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int btnmoncolor = btnmon.getCurrentTextColor();
                String smoncolor = String.format("#%06X", (0xFFFFFF & btnmoncolor));
                if (!(smoncolor.equalsIgnoreCase(setbtncolorcode)))
                {
                    btnmon.setTextColor(Color.parseColor(setbtncolorcode));
                    ssettingsdays.setCharAt(1,'1');
                }
                else
                {
                    btnmon.setTextColor(Color.parseColor(defaultcolorcode));
                }
            }
        });

        btntue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int btntuecolor = btntue.getCurrentTextColor();
                String stuecolor = String.format("#%06X", (0xFFFFFF & btntuecolor));
                if (!(stuecolor.equalsIgnoreCase(setbtncolorcode)))
                {
                    btntue.setTextColor(Color.parseColor(setbtncolorcode));
                    ssettingsdays.setCharAt(2,'1');
                }
                else
                {
                    btntue.setTextColor(Color.parseColor(defaultcolorcode));
                }
            }
        });

        btnwed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int btnwedcolor = btnwed.getCurrentTextColor();
                String swedcolor = String.format("#%06X", (0xFFFFFF & btnwedcolor));
                if (!(swedcolor.equalsIgnoreCase(setbtncolorcode)))
                {
                    btnwed.setTextColor(Color.parseColor(setbtncolorcode));
                    ssettingsdays.setCharAt(3,'1');
                }
                else
                {
                    btnwed.setTextColor(Color.parseColor(defaultcolorcode));
                }
            }
        });

        btnthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int btnthucolor = btnthu.getCurrentTextColor();
                String sthucolor = String.format("#%06X", (0xFFFFFF & btnthucolor));
                if (!(sthucolor.equalsIgnoreCase(setbtncolorcode)))
                {
                    btnthu.setTextColor(Color.parseColor(setbtncolorcode));
                    ssettingsdays.setCharAt(4,'1');
                }
                else
                {
                    btnthu.setTextColor(Color.parseColor(defaultcolorcode));
                }
            }
        });

        btnfri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int btnfricolor = btnfri.getCurrentTextColor();
                String sfricolor = String.format("#%06X", (0xFFFFFF & btnfricolor));
                if (!(sfricolor.equalsIgnoreCase(setbtncolorcode)))
                {
                    btnfri.setTextColor(Color.parseColor(setbtncolorcode));
                    ssettingsdays.setCharAt(5,'1');
                }
                else
                {
                    btnfri.setTextColor(Color.parseColor(defaultcolorcode));
                }
            }
        });

        btnsat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int btnsatcolor = btnsat.getCurrentTextColor();
                String ssatcolor = String.format("#%06X", (0xFFFFFF & btnsatcolor));
                if (!(ssatcolor.equalsIgnoreCase(setbtncolorcode)))
                {
                    btnsat.setTextColor(Color.parseColor(setbtncolorcode));
                    ssettingsdays.setCharAt(6,'1');
                }
                else
                {
                    btnsat.setTextColor(Color.parseColor(defaultcolorcode));
                }
            }
        });

        btnsun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int btnsuncolor = btnsun.getCurrentTextColor();
                String ssuncolor = String.format("#%06X", (0xFFFFFF & btnsuncolor));
                if (!(ssuncolor.equalsIgnoreCase(setbtncolorcode)))
                {
                    btnsun.setTextColor(Color.parseColor(setbtncolorcode));
                    ssettingsdays.setCharAt(0,'1');
                }
                else
                {
                    btnsun.setTextColor(Color.parseColor(defaultcolorcode));
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Apprecord app = db.getAppDetails(spackgage);
                if (!(starttime.equalsIgnoreCase("starttime"))) {
                    app.setStartTime(starttime);
                }
                if (!(endtime.equalsIgnoreCase("endtime"))) {
                    app.setEndtime(endtime);
                }
                app.setDaysRepeat(String.valueOf(ssettingsdays));
                db.updateApp(app);
                Intent mainintent = new Intent(LockPreferences.this,
                        MainActivity.class);
                startActivity(mainintent);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainintent = new Intent(LockPreferences.this,
                        MainActivity.class);
                startActivity(mainintent);
            }
        });
    }

    void Init(String spackage)
    {
        Apprecord app = db.getAppDetails(spackage);
        if (app == null)
        {
            Log.d(TAG, "Could not fetch details for package : " + spackage);
            return;
        }
        else
        {
            sdaysrepeat = app.getDaysRepeat();
            Log.d(TAG, "sdaysrepeat - " + sdaysrepeat);
            if (!(sdaysrepeat.equalsIgnoreCase("days")))
            {
                Log.d(TAG, "sdays repeat is " + sdaysrepeat);
                setdaybuttons(sdaysrepeat);
            }

            sstarttime = app.getStartTime();
            Log.d(TAG, "sstarttime - " + sstarttime);
            if (!(sstarttime.equalsIgnoreCase("starttime")))
            {
                etxtstarttime.setText(sstarttime);
            }
            sendtime = app.getEndtime();
            Log.d(TAG, "sendtime - " + sendtime);
            if (!(sendtime.equalsIgnoreCase("endtime")))
            {
                etxtendtime.setText(sendtime);
            }
        }
    }

    void setdaybuttons(String sdays)
    {
        if (sdays.charAt(0) == '1')
        {
            btnsun.setTextColor(Color.parseColor(setbtncolorcode));
        }
        if (sdays.charAt(1) == '1')
        {
            btnmon.setTextColor(Color.parseColor(setbtncolorcode));
        }
        if (sdays.charAt(2) == '1')
        {
            btntue.setTextColor(Color.parseColor(setbtncolorcode));
        }
        if (sdays.charAt(3) == '1')
        {
            btnwed.setTextColor(Color.parseColor(setbtncolorcode));
        }
        if (sdays.charAt(4) == '1')
        {
            btnthu.setTextColor(Color.parseColor(setbtncolorcode));
        }
        if (sdays.charAt(5) == '1')
        {
            btnfri.setTextColor(Color.parseColor(setbtncolorcode));
        }
        if (sdays.charAt(6) == '1')
        {
            btnsat.setTextColor(Color.parseColor(setbtncolorcode));
        }
    }
}

