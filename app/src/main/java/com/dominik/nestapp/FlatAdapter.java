package com.dominik.nestapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.module.GlideModule;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;


public class FlatAdapter extends RecyclerView.Adapter<FlatAdapter.ViewHolder> {
    private List<Flat> mFlatList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    FirebaseStorage storage = FirebaseStorage.getInstance();


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView flatNameTxtV;
        public TextView flatAddressTxtV;
        public TextView flatDevTxtV;
        public TextView flatAreaTxtV;
        public ImageView flatImage;
        Flat mFlat;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.flat_row_layout, parent, false));
            itemView.setOnClickListener(this);

            flatNameTxtV = (TextView) itemView.findViewById(R.id.name);
            flatAddressTxtV = (TextView) itemView.findViewById(R.id.address);
            flatDevTxtV = (TextView) itemView.findViewById(R.id.dev);
            flatAreaTxtV = (TextView) itemView.findViewById(R.id.area);
            flatImage = (ImageView) itemView.findViewById(R.id.image);
        }

        public void bind(Flat flat) {
            mFlat = flat;
            flatNameTxtV.setText(flat.getName());
            flatAddressTxtV.setText(flat.getAddress());
            flatDevTxtV.setText(flat.getDev());
            flatAreaTxtV.setText(String.valueOf(flat.getArea())+"m\u00B2");

        }

        @Override
        public void onClick(View view) {
             Context viewContext= view.getContext();
            Intent intent = new Intent(viewContext, FlatActivity.class);
            putContent(intent);
            viewContext.startActivity(intent);

        }

        public void putContent(Intent intent){
            intent.putExtra("name", mFlat.getName());
            intent.putExtra("area", mFlat.getArea());
            intent.putExtra("storage", mFlat.getStorage());
            intent.putExtra("rooms",mFlat.getRooms());
            intent.putExtra("floor",mFlat.getFloor());
            intent.putExtra("maxUrl",mFlat.getMaxUrl());
            intent.putExtra("address",mFlat.getAddress());
            intent.putExtra("dev",mFlat.getDev());
            intent.putExtra("balcony",mFlat.getBalcony());
            intent.putExtra("parking",mFlat.getParking());
            intent.putExtra("equip",mFlat.getEquip());
            intent.putExtra("garden",mFlat.getGarden());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FlatAdapter(List<Flat> myDataset, Context context, RecyclerView recyclerView) {
        mFlatList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
        setHasStableIds(true);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FlatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater, parent);

    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Flat flat = mFlatList.get(position);
        holder.bind(flat);
        String minUrl=flat.getMinUrl();



        Glide
                .with(mContext)
                .load(minUrl)
                .into(holder.flatImage);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mFlatList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}



