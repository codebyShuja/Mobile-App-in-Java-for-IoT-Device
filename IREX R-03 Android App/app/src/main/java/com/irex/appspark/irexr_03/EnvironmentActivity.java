package com.irex.appspark.irexr_03;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.lzyzsd.circleprogress.ArcProgress;

public class EnvironmentActivity extends AppCompatActivity {

    private int progressStatus = 0;
    private Handler handler = new Handler();
    public ArcProgress pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pb = (ArcProgress) findViewById(R.id.arc_progress);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 65){
                    // Update the progress status
                    progressStatus +=1;

                    // Try to sleep the thread for 20 milliseconds
                    try{
                        Thread.sleep(50);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pb.setProgress(progressStatus);

                        }
                    });
                }
            }
        }).start(); // Start the operation
    }}