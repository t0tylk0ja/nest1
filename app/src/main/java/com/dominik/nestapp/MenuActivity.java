package com.dominik.nestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MenuActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        if(firebaseAuth.getCurrentUser() == null){
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, SignInActivity.class));
        }

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Button logoutButton = (Button) findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //logging out the user
                if(isOnline()){
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(getApplicationContext(), SignInActivity.class)); }else{
                    Toast.makeText(getApplicationContext(), "Internet connection required", Toast.LENGTH_SHORT).show();
                }
            }

        });

        RelativeLayout searchLayout = (RelativeLayout) findViewById(R.id.search);
        searchLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(isOnline()){
                Context viewContext= view.getContext();
                Intent intent = new Intent(viewContext, MainActivity.class);
                startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Internet connection required", Toast.LENGTH_SHORT).show();
                }
            }

        });

        RelativeLayout loveLayout = (RelativeLayout) findViewById(R.id.love);
        loveLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(isOnline()){
                Context viewContext= view.getContext();
                Intent intent = new Intent(viewContext, LovedActivity.class);
                startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Internet connection required", Toast.LENGTH_SHORT).show();
                }
            }

        });

        RelativeLayout addLayout = (RelativeLayout) findViewById(R.id.add);
        addLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

               if(isOnline()){
                   Context viewContext= view.getContext();
                   Intent intent = new Intent(viewContext, FinishActivity.class);
                   startActivity(intent);
               }else{
                   Toast.makeText(getApplicationContext(), "Internet connection required", Toast.LENGTH_SHORT).show();
               }
            }

        });
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
