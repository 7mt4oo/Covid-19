package com.reports.covid_19tracker.Post;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.reports.covid_19tracker.AppLocationService;
import com.reports.covid_19tracker.LocationAddress;
import com.reports.covid_19tracker.MyActivity;
import com.reports.covid_19tracker.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class PostData extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private ProgressDialog progress;
    TextView textView;
    String phoneNumberWithCountryCode = "+201096430213";
    String message = "عايز ابلغ عن حالة مصابه بكرونا";
    TextView tvName;
    TextView tvCountry;
    Button button;
    Button buttonws;
    Button fbsend;
    String name;
    String country;
    TextView tvAddress;
    Button btnShowAddress;
    Button btnGPSShowLocation;
    AppLocationService appLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        appLocationService = new AppLocationService(
                PostData.this);


        tvAddress = (TextView) findViewById(R.id.tvAddress);


        button = (Button) findViewById(R.id.btn_signup);
        btnShowAddress = (Button) findViewById(R.id.btnShowAddress);
        buttonws = (Button) findViewById(R.id.wssend);
        fbsend = (Button) findViewById(R.id.fbsend);
        tvName = (EditText) findViewById(R.id.input_name);
        tvCountry = (EditText) findViewById(R.id.input_Classroom);

        btnShowAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Location location = appLocationService
                        .getLocation(LocationManager.GPS_PROVIDER);

                //you can hard-code the lat & long if you have issues with getting it
                //remove the below if-condition and use the following couple of lines
                //double latitude = 37.422005;
                //double longitude = -122.084095

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getApplicationContext(), new GeocoderHandler());
                } else {
                    Toast.makeText(PostData.this, "Open GPS", Toast.LENGTH_SHORT).show();

//                    showSettingsAlert();
                }

            }
        });


        fbsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = getPackageManager();

                if (isPackageExisted("com.facebook.orca")) {
                    Uri uri = Uri.parse("fb-messenger://user/");
                    String text = "YOUR TEXT HERE";
                    uri = ContentUris.withAppendedId(uri, Long.parseLong("100013683048069"));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(PostData.this, "Messanger not Installed", Toast.LENGTH_SHORT).show();
                }
            }


        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = tvName.getText().toString();
                country = tvCountry.getText().toString();
                if (tvName.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PostData.this, "Enter Your Name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tvCountry.getText().toString().trim().length() <= 0) {
                    Toast.makeText(PostData.this, "Enter Your Report!", Toast.LENGTH_SHORT).show();
                    return;
                }

                new SendRequest().execute();
                //   Toast.makeText(PostData.this, "Sucess", Toast.LENGTH_SHORT).show();

                finish();

            }

        });
        buttonws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = PostData.this.getPackageManager();

                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
//                    String text = "Hai Good Morning";
                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.whatsapp");
//                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(
                                    String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                                            phoneNumberWithCountryCode, message))));
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(PostData.this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }




    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {
        }

        protected String doInBackground(String... arg0) {

            try {
                //Change your web app deployed URL or u can use this for attributes (name, country)
                URL url = new URL("https://script.google.com/macros/s/AKfycbzZoAgDtQldTS2rtaLZdgewAAufumunyeNJPlmrrFc2fSTrHk0Z/exec");

                JSONObject postDataParams = new JSONObject();
//
//                int i;
//                for(i=1;i<=70;i++)


//                    String usn = Integer.toString(i);

                String id = "1NejpA67eBLK9YLMFFrsyF_m3gbCPgllJQKI8DNuA86c";
                postDataParams.put("id", id);
                postDataParams.put("name", name);
                postDataParams.put("country", country);


                Log.e("params", postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_LONG).show();

        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    private boolean isPackageExisted(String s) {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(s, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            tvCountry.setText( "I want to report a case of corona in this address: \n" + locationAddress);

        }
    }
}



