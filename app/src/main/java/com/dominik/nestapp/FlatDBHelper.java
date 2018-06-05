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
    private static final int DATABASE_VERSION = 4 ;
    public static final String TABLE_NAME = "Flats";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "age";
    public static final String COLUMN_DEV = "dev";
    public static final String COLUMN_AREA = "area";
    public static final String COLUMN_STORAGE = "storage";
    public static final String COLUMN_ROOMS = "rooms";
    public static final String COLUMN_FLOOR = "floor";
    public static final String COLUMN_BALCONY = "balcony";
    public static final String COLUMN_PARKING = "parking";
    public static final String COLUMN_EQUIP = "equip";
    public static final String COLUMN_GARDEN = "garden";
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
                COLUMN_AREA + " REAL NOT NULL, " +
                COLUMN_STORAGE + " REAL NOT NULL, " +
                COLUMN_ROOMS + " INTEGER NOT NULL, " +
                COLUMN_FLOOR + " INTEGER NOT NULL, " +
                COLUMN_BALCONY + " INTEGER NOT NULL, " +
                COLUMN_PARKING + " INTEGER NOT NULL, " +
                COLUMN_EQUIP + " INTEGER NOT NULL, " +
                COLUMN_GARDEN + " INTEGER NOT NULL, " +
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
    public void saveNewFlat(String name, String address, String dev,double area,double storage, int rooms, int floor,
                            int balcony, int parking, int equip,int garden,String minUrl,String maxUrl) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_DEV, dev);
        values.put(COLUMN_AREA, area);
        values.put(COLUMN_STORAGE,storage);
        values.put(COLUMN_ROOMS, rooms);
        values.put(COLUMN_FLOOR, floor);
        values.put(COLUMN_BALCONY, balcony);
        values.put(COLUMN_PARKING,parking);
        values.put(COLUMN_EQUIP, equip);
        values.put(COLUMN_GARDEN, garden);
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
                flat.setArea(cursor.getDouble(cursor.getColumnIndex(COLUMN_AREA)));
                flat.setStorage(cursor.getDouble(cursor.getColumnIndex(COLUMN_STORAGE)));
                flat.setRooms(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOMS)));
                flat.setFloor(cursor.getInt(cursor.getColumnIndex(COLUMN_FLOOR)));
                flat.setBalcony(cursor.getInt(cursor.getColumnIndex(COLUMN_BALCONY)));
                flat.setParking(cursor.getInt(cursor.getColumnIndex(COLUMN_PARKING)));
                flat.setEquip(cursor.getInt(cursor.getColumnIndex(COLUMN_EQUIP)));
                flat.setGarden(cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN)));
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
            receivedFlat.setArea(cursor.getDouble(cursor.getColumnIndex(COLUMN_AREA)));
            receivedFlat.setStorage(cursor.getDouble(cursor.getColumnIndex(COLUMN_STORAGE)));
            receivedFlat.setRooms(cursor.getInt(cursor.getColumnIndex(COLUMN_ROOMS)));
            receivedFlat.setFloor(cursor.getInt(cursor.getColumnIndex(COLUMN_FLOOR)));
            receivedFlat.setBalcony(cursor.getInt(cursor.getColumnIndex(COLUMN_BALCONY)));
            receivedFlat.setParking(cursor.getInt(cursor.getColumnIndex(COLUMN_PARKING)));
            receivedFlat.setEquip(cursor.getInt(cursor.getColumnIndex(COLUMN_EQUIP)));
            receivedFlat.setGarden(cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN)));
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
        saveNewFlat("Nowe Żerniki","Wrocław, Nowe Żerniki","ATAL",45.5,3.0, 3,2,1,0,1,0,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FAlabama.jpg?alt=media&token=6c70f9ff-097d-4605-b5f5-27a1f016fec4",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2F1jpg.jpg?alt=media&token=2885d38b-431e-4597-ac04-e3f60a61ed18");
        saveNewFlat("Nowe Miasto Różanka","Wrocław, Obornicka","ATAL",25.0,5.0, 1,3,0,1,0,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FChicago.jpg?alt=media&token=565aa1e4-961d-4092-8af6-eb5ee6742a00",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2F2.jpg?alt=media&token=b71bf834-0285-4e8c-b647-83dad698ecf4");
        saveNewFlat("Krakowska 37","Wrocław, Krakowska 37","ATAL",37.0,2.0,2,0,1,1,1,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FMiami.jpg?alt=media&token=4f9fe693-160f-4684-9088-0ac077878492",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2F3.jpg?alt=media&token=9acfe027-379b-4522-893c-cb8275cb93a6");

        saveNewFlat("Browary Wrocławskie","Wrocław, Jedności Narodowej","ARCHICOM",57.0,2.0,2,0,1,1,1,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FMiami.jpg?alt=media&token=4f9fe693-160f-4684-9088-0ac077878492",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2F1jpg.jpg?alt=media&token=2885d38b-431e-4597-ac04-e3f60a61ed18");
        saveNewFlat("Olimpia Port","Wrocław, Ferdynanda Magellana","ARCHICOM",35.5,1.0,1,2,0,1,0,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FMiami.jpg?alt=media&token=4f9fe693-160f-4684-9088-0ac077878492",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2F2.jpg?alt=media&token=b71bf834-0285-4e8c-b647-83dad698ecf4");
        saveNewFlat("River Point","Wrocław, Mieszczańska","ARCHICOM",45.5,2.5,2,0,1,1,1,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FMiami.jpg?alt=media&token=4f9fe693-160f-4684-9088-0ac077878492",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2F3.jpg?alt=media&token=9acfe027-379b-4522-893c-cb8275cb93a6");

        saveNewFlat("Apartamenty Klasztorna","Wrocław, Klasztorna","MURAPOL",27.0,1.0,1,1,0,1,1,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FMiami.jpg?alt=media&token=4f9fe693-160f-4684-9088-0ac077878492",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2F1jpg.jpg?alt=media&token=2885d38b-431e-4597-ac04-e3f60a61ed18");
        saveNewFlat("Nowa Toskania","Wrocław, Buforowa","MURAPOL",40.0,1.0,2,3,1,1,1,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FMiami.jpg?alt=media&token=4f9fe693-160f-4684-9088-0ac077878492",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2F2.jpg?alt=media&token=b71bf834-0285-4e8c-b647-83dad698ecf4");
        saveNewFlat("Promenady Wrocławskie","Wrocław, Zakładowa","VANTAGE",35.5,1.0,1,5,0,1,0,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FMiami.jpg?alt=media&token=4f9fe693-160f-4684-9088-0ac077878492",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2F3.jpg?alt=media&token=9acfe027-379b-4522-893c-cb8275cb93a6");

        saveNewFlat("Dorzecze Legnickiej","Wrocław, Małopanewska","VANTAGE",37.0,2.0,1,4,0,1,1,0,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FMiami.jpg?alt=media&token=4f9fe693-160f-4684-9088-0ac077878492",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2F2.jpg?alt=media&token=b71bf834-0285-4e8c-b647-83dad698ecf4");
        saveNewFlat("LogIn City","Wrocław, Grabiszyńska","VANTAGE",61.0,5.0,2,4,1,1,1,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/small%2FMiami.jpg?alt=media&token=4f9fe693-160f-4684-9088-0ac077878492",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/big%2F3.jpg?alt=media&token=9acfe027-379b-4522-893c-cb8275cb93a6");
    }




}

