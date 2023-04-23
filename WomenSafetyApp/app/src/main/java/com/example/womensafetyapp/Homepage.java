package com.example.womensafetyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import contactcard.contactpage;
import locationcard.locationpage;
import phonecard.callpage;
import phonecard.helppage;

public class Homepage extends AppCompatActivity {

    CardView phonecard,locationcard,contactcard,blogcard,helpcard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        phonecard=(CardView) findViewById(R.id.phonecard);
        locationcard=(CardView) findViewById(R.id.locationcard);
        contactcard=(CardView) findViewById(R.id.contactcard);
        blogcard=(CardView) findViewById(R.id.blogcard);
        helpcard=(CardView) findViewById(R.id.helpcard);

        blogcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Homepage.this,blogpage.class);
                startActivity(intent);
            }
        });
        phonecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this, callpage.class);
                startActivity(intent);
            }
        });
        helpcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this, helppage.class);
                startActivity(intent);
            }
        });
       contactcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this, contactpage.class);
                startActivity(intent);
            }
        });
     locationcard.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(Homepage.this, locationpage.class);
               startActivity(intent);
           }
       });
    }
}