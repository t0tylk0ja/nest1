package com.dominik.nestapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;

public class FlatActivity extends AppCompatActivity {


    Bundle extras;
    HashMap<String,Dev> devList;
    Dev devToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat);

        extras = getIntent().getExtras();

        TextView tv1=(TextView) findViewById(R.id.textView);
        tv1.setText(extras.getString("name"));

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView);
        String maxUrl = extras.getString("maxUrl");

        Glide
                .with(this)
                .load(maxUrl)
                .into(imageView1);

        devList=new DevList().returnDevList();
        devToShow=devList.get(extras.getString("dev"));
        TextView tvDevName=(TextView) findViewById(R.id.devName);
        ImageView tvDevLogo = (ImageView) findViewById(R.id.devLogo);
        tvDevName.setText(devToShow.getDevName());

        Glide
                .with(this)
                .load(devToShow.getDevLogo())
                .into(tvDevLogo);
    }


}
