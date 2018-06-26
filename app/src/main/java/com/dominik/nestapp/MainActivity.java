package com.dominik.nestapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SearchView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FlatDBHelper dbHelper;
    private FlatAdapter adapter;
    private String filter = "";
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//obsluga actionbara
        TextView tv = new TextView(getApplicationContext());
        tv.setText("nest");
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setTextSize(26);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Pacifico.ttf");
        tv.setTypeface(type);
        GradientDrawable gradient = new GradientDrawable();
        gradient.setColors(new int[]{
                Color.parseColor("#5574ac"),
                Color.parseColor("#31507d"),
                Color.parseColor("#5b247a"),
        });
        gradient.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gradient.setShape(GradientDrawable.RECTANGLE);
        gradient.setOrientation(GradientDrawable.Orientation.BL_TR);
        //gradient.setStroke(2, Color.parseColor("#0098a6"));
        gradient.setAlpha(255);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(tv);
        getSupportActionBar().setBackgroundDrawable(gradient);


        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
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
        adapter = new FlatAdapter(dbHelper.flatList(filter),this,mRecyclerView);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    boolean nameAsc=false;
    boolean areaAsc=false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            case R.id.order_name:
                if(!nameAsc){
                    filter="name asc";
                    nameAsc=true;
                }else{
                    filter="name desc";
                    nameAsc=false;
                }
                populateRecyclerView(filter);
                return true;
            case R.id.order_area:
                if(!areaAsc){
                    filter="area asc";
                    areaAsc=true;
                }else{
                    filter="area desc";
                    areaAsc=false;
                }
                populateRecyclerView(filter);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
