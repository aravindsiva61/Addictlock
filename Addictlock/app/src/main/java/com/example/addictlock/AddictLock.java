package com.example.addictlock;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by USER on 11/12/2017.
 */
public class AddictLock extends Activity{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.addict_lock);
            final String smillisecs = getIntent().getStringExtra("MilliSeconds");
            long lms = Long.parseLong(smillisecs);
            final TextView mTextField = (TextView) findViewById(R.id.txt);

            new CountDownTimer(lms, 1000) {

                public void onTick(long milliSeconds) {
                    int hours = (int) ((milliSeconds / (1000 * 60 * 60)) % 24);
                    int minutes = (int) ((milliSeconds / (1000 * 60)) % 60);
                    int seconds = (int) (milliSeconds / 1000) % 60;

                    mTextField.setText(String.format("%02d", hours)
                            + ":" + String.format("%02d", minutes)
                            + ":" + String.format("%02d", seconds));
                }

                public void onFinish() {
                    mTextField.setText("done!");
                }

            }.start();
        }
}
