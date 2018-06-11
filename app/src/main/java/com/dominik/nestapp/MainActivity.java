package com.dominik.nestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FlatDBHelper dbHelper;
    private FlatAdapter adapter;
    private String filter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the variables
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //populate recyclerview
        populateRecyclerView(filter);

        final Spinner opsSpin = (Spinner) findViewById(R.id.operation);
        boolean nameAsc=true;
        boolean areaAsc=true;

        Button sortButton = (Button) findViewById(R.id.sortButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            boolean nameAsc=false;
            boolean areaAsc=false;
            @Override
            public void onClick(View v) {
              if(opsSpin.getSelectedItemPosition()==1) {
                  if(!nameAsc){
                      filter="name asc";
                      nameAsc=true;
                  }else{
                      filter="name desc";
                      nameAsc=false;
                  }
              }else if(opsSpin.getSelectedItemPosition()==2){
                  if(!areaAsc){
                      filter="area asc";
                      areaAsc=true;
                  }else{
                      filter="area desc";
                      areaAsc=false;
                  }
              }
                populateRecyclerView(filter);
            }
        });
    }

    private void populateRecyclerView(String filter){
        dbHelper = FlatDBHelper.getInstanse(this);
        //dbHelper.addSomeFlats();
        adapter = new FlatAdapter(dbHelper.flatList(filter),this,mRecyclerView);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
