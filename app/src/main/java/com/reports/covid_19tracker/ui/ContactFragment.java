package com.reports.covid_19tracker.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.reports.covid_19tracker.R;

public class ContactFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_contact, container, false);

        //Define an object from HomeFragment due to use addUnderlineText method
        HomeFragment homeFragment = new HomeFragment();

        final TextView gmailText = root.findViewById(R.id.gmail_text);
//        final TextView linkedInText = root.findViewById(R.id.linkedin_text);
        final TextView btnCall = root.findViewById(R.id.btn_call);


        homeFragment.addUnderlineText(gmailText);
//        homeFragment.addUnderlineText(linkedInText);
        homeFragment.addUnderlineText(btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               call();
            }
        });

        gmailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });

//        linkedInText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openLinkedIn();
//            }
//        });

        return root;
    }

    private void call() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:01096430213"));
        startActivity(intent);

    }

//    private void openLinkedIn(){
//
//        String profile_url = "https://www.linkedin.com/in/omar-shafei-261808160/";
//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(profile_url));
//            intent.setPackage("com.linkedin.android");
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        } catch (Exception e) {
//            //if the user doesn't have a LinkedIn account, open an intent to a browser
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(profile_url)));
//        }
//    }

    private void sendMail(){
        String recipientList = "virus.corona.reports@gmail.com";
        String[] recipients = recipientList.split(",");

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }


}