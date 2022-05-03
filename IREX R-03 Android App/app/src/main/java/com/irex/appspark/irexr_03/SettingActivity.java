package com.irex.appspark.irexr_03;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends AppCompatActivity {

    public Button saveIap;
    public EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        saveIap = (Button) findViewById(R.id.saveIp);
        et = (EditText) findViewById(R.id.sendIp);

        saveIap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               Intent intent = new Intent(getApplicationContext(),VoiceControl.class);
                intent.putExtra("EdiTtEXTvALUE", et.getText().toString());
                startActivity(intent);

            }
        });
    }

}
