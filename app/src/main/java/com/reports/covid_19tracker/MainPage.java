package com.reports.covid_19tracker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.reports.covid_19tracker.Post.PostData;


public class MainPage extends AppCompatActivity {


    Button getData;
    Button sendData;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        if (ContextCompat.checkSelfPermission(MainPage.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainPage.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(MainPage.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                ActivityCompat.requestPermissions(MainPage.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
//
//        ActionBar actionBar = getSupportActionBar();
//        if (null != actionBar) {
//            actionBar.hide();
//        }

        sendData = (Button) findViewById(R.id.insertUser);
        getData = (Button) findViewById(R.id.viewUser);


        getData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                finish();

            }

        });
        sendData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), PostData.class);
                startActivity(intent);
            }

        });


    }


    public void scClick(View view) {

        Intent intent = new Intent(getApplicationContext(), Coronalive.class);
        startActivity(intent);
    }


    public void help(View view) {
        Intent intentMain = new Intent(MainPage.this, HelpActivity.class);
        startActivity(intentMain);
    }
//    public void gotoadd(View view) {
//        Intent intentMain = new Intent(MainPage.this, MyActivity.class);
//        startActivity(intentMain);
//    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        }
}


