package com.dominik.nestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
                firebaseAuth.signOut();
                //closing activity
                finish();
                //starting login activity
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }

        });

        RelativeLayout searchLayout = (RelativeLayout) findViewById(R.id.search);
        searchLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Context viewContext= view.getContext();
                Intent intent = new Intent(viewContext, MainActivity.class);
                startActivity(intent);
            }

        });

        RelativeLayout loveLayout = (RelativeLayout) findViewById(R.id.love);
        loveLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Context viewContext= view.getContext();
                Intent intent = new Intent(viewContext, LovedActivity.class);
                startActivity(intent);
            }

        });
    }
}
