package com.dominik.nestapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class FlatDBHelper  extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "people.db";
    private static final int DATABASE_VERSION = 1 ;
    public static final String TABLE_NAME = "People";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "age";
    public static final String COLUMN_DEV = "dev";

    private static FlatDBHelper mInstance = null;


    public FlatDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

public static FlatDBHelper getInstanse(Context ctx){
    if (mInstance == null) {
        mInstance = new FlatDBHelper(ctx.getApplicationContext());
        mInstance.addSomeFlats();
    }
    return mInstance;
}

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_ADDRESS + " TEXT NOT NULL, " +
                COLUMN_DEV + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }
    /**create record**/
    public void saveNewFlat(String name, String address, String dev) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_DEV, dev);

        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    /**Query records, give options to filter results**/
    public List<Flat> peopleList(String filter) {
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

                flatLinkedList.add(flat);
            } while (cursor.moveToNext());
        }

        return flatLinkedList;
    }

    /**Query only 1 record**/
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
        }

        return receivedFlat;
    }


    /**delete record**/
    public void deleteFlatRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    public void addSomeFlats(){
        saveNewFlat("Alabama","Słowackiego","ATAL");
        saveNewFlat("Chicago","Grunwaldzka","MURAPOL");
        saveNewFlat("Miami","Piastowska","ATAL");
        //saveNewFlat("Las Vegas","Wyszyńskiego","ARCHIKOM");
        //saveNewFlat("Idaho","Mickiewicza","VANTAGE");
    }


//    public void updateFlatRecord(long personId, Context context, Flat updatedFlat) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        //you can use the constants above instead of typing the column names
//        db.execSQL("UPDATE  "+TABLE_NAME+" SET name ='"+ updatedFlat.getName() + "', address ='" + updatedFlat.getAddress() + "'  WHERE _id='" + personId + "'");
//        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
//    }




}

