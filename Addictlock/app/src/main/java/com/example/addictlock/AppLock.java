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
 * Created by USER on 1/30/2017.
 */
public class AppLock extends Activity {
    StringBuilder spass = new StringBuilder("");
    final String TAG = Appservice.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Inside app lock view");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_lock);
        final EditText txt_passwd = (EditText)findViewById(R.id.password);
        Button btn_one = (Button)findViewById(R.id.one);
        Button btn_two = (Button) findViewById(R.id.two);
        Button btn_three = (Button) findViewById(R.id.three);
        Button btn_four = (Button) findViewById(R.id.four);
        Button btn_five = (Button) findViewById(R.id.five);
        Button btn_six = (Button) findViewById(R.id.six);
        Button btn_seven = (Button) findViewById(R.id.seven);
        Button btn_eight = (Button) findViewById(R.id.eight);
        Button btn_nine = (Button) findViewById(R.id.nine);
        Button btn_zero = (Button) findViewById(R.id.zero);
        Button btn_OK = (Button) findViewById(R.id.zero);

        btn_one.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spass = spass.append("1");

                txt_passwd.setText(spass);
            }
        });

        btn_two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spass=spass.append("2");

                txt_passwd.setText(spass);
            }
        });

        btn_three.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spass=spass.append("3");
                txt_passwd.setText(spass);
            }
        });

        btn_four.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spass=spass.append("4");
                txt_passwd.setText(spass);
            }
        });

        btn_five.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spass=spass.append("5");
                txt_passwd.setText(spass);
            }
        });

        btn_six.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spass=spass.append("6");
                txt_passwd.setText(spass);
            }
        });

        btn_seven.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spass=spass.append("7");
                txt_passwd.setText(spass);
            }
        });

        btn_eight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spass=spass.append("8");
                txt_passwd.setText(spass);
            }
        });

        btn_nine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spass=spass.append("9");
                txt_passwd.setText(spass);
            }
        });

        btn_zero.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                spass=spass.append("0");
                txt_passwd.setText(spass);
            }
        });

        btn_OK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String passwd = spass.toString();
                finish();
            }
        });

    }
}
