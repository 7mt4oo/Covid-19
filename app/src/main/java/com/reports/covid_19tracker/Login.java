package com.reports.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.reports.covid_19tracker.Post.PostData;

public class Login extends AppCompatActivity {
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         username = (EditText) findViewById(R.id.input_name);
         password = (EditText) findViewById(R.id.password);

    }

    public void login(View view) {
        if (username.getText().toString().equals("corona2020") && password.getText().toString().equals("corona2020")) {
            Toast.makeText(Login.this, "Correct Password", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainPage.class);
            startActivity(intent);

        } else {
            Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_LONG).show();


        }
    }
}