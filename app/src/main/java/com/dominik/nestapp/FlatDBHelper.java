package com.dominik.nestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FlatDBHelper  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "flats.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = "Flats";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "age";
    public static final String COLUMN_DEV = "dev";
    public static final String COLUMN_MINPIC = "min";
    public static final String COLUMN_MAXPIC = "max";

    private static FlatDBHelper mInstance = null;


    public FlatDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

public static FlatDBHelper getInstanse(Context ctx){
    if (mInstance == null) {
        mInstance = new FlatDBHelper(ctx.getApplicationContext());

        File f = ctx.getDatabasePath(DATABASE_NAME);
        long dbSize = f.length();
        if(dbSize==0){
        mInstance.addSomeFlats();}
    }
    return mInstance;
}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL, " +
                COLUMN_DEV + " TEXT NOT NULL, " +
                COLUMN_MINPIC + " TEXT NOT NULL, " +
                COLUMN_MAXPIC + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
    /**create record**/
    public void saveNewFlat(String name, String address, String dev, String minUrl,String maxUrl) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_DEV, dev);
        values.put(COLUMN_MINPIC, minUrl);
        values.put(COLUMN_MAXPIC, maxUrl);

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    /**Query records, give options to filter results**/
    public List<Flat> flatList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        List<Flat> flatLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Flat flat;

        if (cursor.moveToFirst()) {
            do {
                flat = new Flat();

                flat.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                flat.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                flat.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                flat.setDev(cursor.getString(cursor.getColumnIndex(COLUMN_DEV)));
                flat.setMinUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MINPIC)));
                flat.setMaxUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MAXPIC)));

                flatLinkedList.add(flat);
            } while (cursor.moveToNext());
        }

        return flatLinkedList;
    }

    public Flat getFlat(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Flat receivedFlat = new Flat();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedFlat.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            receivedFlat.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
            receivedFlat.setDev(cursor.getString(cursor.getColumnIndex(COLUMN_DEV)));
            receivedFlat.setMinUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MINPIC)));
            receivedFlat.setMaxUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MAXPIC)));
        }

        return receivedFlat;
    }


    public void deleteFlatRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    public void addSomeFlats(){
        saveNewFlat("Alabama"," Wrocław, Krawiecka 1","ATAL",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FAlabama.jpg?alt=media&token=6c70f9ff-097d-4605-b5f5-27a1f016fec4",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2FAlabama_max.jpg?alt=media&token=cd91735f-ec08-4785-a349-897f93d1019f");
        saveNewFlat("Chicago"," Wrocław, Pilczycka 101","MURAPOL",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FChicago.jpg?alt=media&token=565aa1e4-961d-4092-8af6-eb5ee6742a00",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2FChicago_max.jpg?alt=media&token=40896244-d922-451e-acb7-e2c1bccafd7a");
        saveNewFlat("Miami","Wrocław, Grunwaldzka 98","ATAL",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FMiami.jpg?alt=media&token=4f9fe693-160f-4684-9088-0ac077878492",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2FMiami_max.jpg?alt=media&token=03f5f1b5-2c3e-42ee-9903-2d872443a732");

    }




}

