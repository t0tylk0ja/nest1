package com.dominik.nestapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class LovedActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FlatDBHelper dbHelper;
    private FlatAdapter adapter;
    private String filter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loved);


        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView2);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //populate recyclerview
        populateRecyclerView(filter);
    }

    private void populateRecyclerView(String filter){
        dbHelper = FlatDBHelper.getInstanse(this);
        //dbHelper.addSomeFlats();
        adapter = new FlatAdapter(dbHelper.flatListLoved(),this,mRecyclerView);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
