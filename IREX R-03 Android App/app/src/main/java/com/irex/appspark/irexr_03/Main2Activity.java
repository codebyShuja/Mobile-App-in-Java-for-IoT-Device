package com.irex.appspark.irexr_03;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    public Button voiceControl;

    private static final int REQUEST_WRITE_PERMISSION = 786;
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION : {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent_status  =  new Intent(getApplicationContext(), Main2Activity.class);
                    startActivity(intent_status);

                    finish();

                }
                return;
            }
        }
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        try {
            requestPermission();
        }
        catch (Exception e)
        {
            Toast.makeText(Main2Activity.this, "error "+e, Toast.LENGTH_SHORT).show();
        }
        voiceControl = (Button) findViewById(R.id.voiceControl);
        voiceControl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                try {
                    Intent myIntent = new Intent(Main2Activity.this, VoiceControl.class);
                    startActivity(myIntent);
                } catch (Exception e) {
                    Log.d("TAG", "Please try again");
                }


            }

        });


    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(Main2Activity.this, new String[] { Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_SMS,Manifest.permission.READ_CONTACTS,Manifest.permission.CALL_PHONE}, REQUEST_WRITE_PERMISSION);
        }
    }

}