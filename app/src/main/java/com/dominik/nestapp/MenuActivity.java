package com.dominik.nestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        RelativeLayout searchLayout = (RelativeLayout) findViewById(R.id.search);
        searchLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Context viewContext= view.getContext();
                Intent intent = new Intent(viewContext, MainActivity.class);
                startActivity(intent);
            }

        });
    }
}
