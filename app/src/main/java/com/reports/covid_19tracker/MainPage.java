package com.reports.covid_19tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.reports.covid_19tracker.Post.PostData;


public class MainPage extends AppCompatActivity {


    Button getData;
    Button sendData;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

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

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
}

