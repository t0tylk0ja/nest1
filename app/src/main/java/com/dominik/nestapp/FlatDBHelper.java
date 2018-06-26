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
    private static final int DATABASE_VERSION = 8 ;
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
    public static final String COLUMN_LOVED = "loved";
    public static final String COLUMN_MINPIC = "min";
    public static final String COLUMN_MAXPIC = "max";
    public static final String COLUMN_PLANPIC = "pic";
    public static final String COLUMN_GALLERYPIC = "gallery";
    public static final String COLUMN_GALLERYPIC2 = "gallery2";

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
                COLUMN_LOVED + " INTEGER NOT NULL, " +
                COLUMN_MINPIC + " TEXT NOT NULL, " +
                COLUMN_MAXPIC + " TEXT NOT NULL, " +
                COLUMN_PLANPIC + " TEXT NOT NULL, " +
                COLUMN_GALLERYPIC + " TEXT NOT NULL, " +
                COLUMN_GALLERYPIC2 + " TEXT NOT NULL);"
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
                            int balcony, int parking, int equip,int garden,int loved,String minUrl,String maxUrl, String planUrl, String galleryUrl, String galleryUrl2) {

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
        values.put(COLUMN_LOVED, loved);
        values.put(COLUMN_MINPIC, minUrl);
        values.put(COLUMN_MAXPIC, maxUrl);
        values.put(COLUMN_PLANPIC, planUrl);
        values.put(COLUMN_GALLERYPIC, galleryUrl);
        values.put(COLUMN_GALLERYPIC2, galleryUrl2);

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

        List<Flat> flatLinkedList = new LinkedList<Flat>();
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
                flat.setLoved(cursor.getInt(cursor.getColumnIndex(COLUMN_LOVED)));
                flat.setMinUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MINPIC)));
                flat.setMaxUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MAXPIC)));
                flat.setPlanUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PLANPIC)));
                flat.setGalleryUrl(cursor.getString(cursor.getColumnIndex(COLUMN_GALLERYPIC)));
                flat.setGalleryUrl2(cursor.getString(cursor.getColumnIndex(COLUMN_GALLERYPIC2)));

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
            receivedFlat.setLoved(cursor.getInt(cursor.getColumnIndex(COLUMN_LOVED)));
            receivedFlat.setMinUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MINPIC)));
            receivedFlat.setMaxUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MAXPIC)));
            receivedFlat.setPlanUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PLANPIC)));
            receivedFlat.setGalleryUrl(cursor.getString(cursor.getColumnIndex(COLUMN_GALLERYPIC)));
            receivedFlat.setGalleryUrl2(cursor.getString(cursor.getColumnIndex(COLUMN_GALLERYPIC2)));
        }

        return receivedFlat;
    }

    public void updateLoved(int id, int value){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put("loved",value);
        db.update("Flats",cv,"_id="+id,null);
    }


    public void deleteFlatRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    public List<Flat> flatListLoved() {
        String query;
            query = "SELECT  * FROM " + TABLE_NAME + " WHERE loved > 0";


        List<Flat> flatLinkedList = new LinkedList<Flat>();
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
                flat.setLoved(cursor.getInt(cursor.getColumnIndex(COLUMN_LOVED)));
                flat.setMinUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MINPIC)));
                flat.setMaxUrl(cursor.getString(cursor.getColumnIndex(COLUMN_MAXPIC)));
                flat.setPlanUrl(cursor.getString(cursor.getColumnIndex(COLUMN_PLANPIC)));
                flat.setGalleryUrl(cursor.getString(cursor.getColumnIndex(COLUMN_GALLERYPIC)));
                flat.setGalleryUrl2(cursor.getString(cursor.getColumnIndex(COLUMN_GALLERYPIC2)));

                flatLinkedList.add(flat);
            } while (cursor.moveToNext());
        }

        return flatLinkedList;
    }

    public void addSomeFlats(){
        saveNewFlat("Nowe Żerniki","Wrocław, Nowe Żerniki","ATAL",45.5,3.0, 3,2,0,0,1,0,0,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Fnowe_zerniki.jpg?alt=media&token=242567a8-a878-4ee7-9579-4e522e3de88d",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Fnowe_zerniki.jpg?alt=media&token=b42164d8-1ea5-462f-9ac7-5a7d5ee06521",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp4.jpg?alt=media&token=e842fc06-ff8e-427f-8ce6-a09bf280dff8",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fnowe_zerniki.jpg?alt=media&token=885061ad-ab6e-4dd9-8242-ce87cdf841ca",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fnowe_zerniki_2.jpg?alt=media&token=da2faae4-c080-472c-add9-062432c8608c");
        saveNewFlat("Nowe Miasto Różanka","Wrocław, Obornicka","ATAL",25.0,5.0, 1,3,1,1,0,1,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Frozanka.jpg?alt=media&token=03f5e3b6-9000-4748-915a-7a03522ed4a4",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Frozanka.jpg?alt=media&token=3fb32305-4db1-43d6-bd45-c631bf2ba666",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp3_balkon.jpg?alt=media&token=1cb8d92f-61c6-46d8-a212-bc592a5860bd",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Frozanka.jpg?alt=media&token=6ca85a14-134c-4380-9bdb-3e4086f705e1",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Frozanka_2.jpg?alt=media&token=934eaa13-6880-4613-8f5f-2eff319cf7ab");
        saveNewFlat("Krakowska 37","Wrocław, Krakowska 37","ATAL",37.0,2.0,2,0,1,1,1,1,0,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Fkrakowska.jpg?alt=media&token=2d8f6502-4535-41ff-a060-96b8c600aeeb",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Fkrakowska.jpg?alt=media&token=cd2ca442-9a0e-47d2-bdd9-43d522fc1891",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp2_balkon.jpg?alt=media&token=168f02eb-f8b2-4752-b2fd-928aadb51436",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fkrakowska.jpg?alt=media&token=992ce1cd-cefd-4035-87a3-44d29cc3c5b0",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fkrakowska_2.jpg?alt=media&token=ec24fb2b-a507-4ac3-a386-3ea160793dbe");
        saveNewFlat("Browary Wrocławskie","Wrocław, Jedności Narodowej","ARCHICOM",57.0,2.0,3,0,1,1,1,1,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Fbrowary.jpg?alt=media&token=5d900948-ff73-4e37-95ae-07ffe81b883e",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Fbrowary.jpg?alt=media&token=b93447d3-131d-42ba-9b89-68cb3a41c293",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp1_balkon.jpg?alt=media&token=a0bb3662-72e3-4c2e-8123-8cf45ca84db6",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fbrowary.jpg?alt=media&token=137f4ff9-0af6-4442-ade6-918954fcfcc8",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fbrowary_2.jpg?alt=media&token=2298c839-206b-47a0-bace-8ac03ed1e04d");
        saveNewFlat("Olimpia Port","Wrocław, Ferdynanda Magellana","ARCHICOM",35.5,1.0,2,2,0,1,0,1,0,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Folimpia.jpg?alt=media&token=61d02068-1193-4a88-875b-a63a74b9b0d3",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Folimpia.jpg?alt=media&token=713a2aa1-0f55-4525-861a-cb0c35430c2f",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp2.jpg?alt=media&token=ae70b66d-d710-42a3-81be-edf58ab71df9",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Folimpia.jpg?alt=media&token=812b98f3-09de-4628-a773-e884552b8eea",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Folimpia_2.jpg?alt=media&token=ffded237-ad6f-4462-a635-f89428209ca0");
        saveNewFlat("River Point","Wrocław, Mieszczańska","ARCHICOM",45.5,2.5,3,0,0,1,1,1,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Friver.jpg?alt=media&token=4e1f3f6c-0778-447f-b6fc-fd9641b85261",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Friver.jpg?alt=media&token=4e9aa51e-a654-423b-9089-416825c2d5b3",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp1.jpg?alt=media&token=ae17ad79-7de9-4c4b-9dc1-46dad13014c2",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Friver.jpg?alt=media&token=71557ff9-9c91-4a06-8ba0-0e354ae97097",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Friver_2.jpg?alt=media&token=bd947af2-cda5-4223-8f41-06c159e721f7");
        saveNewFlat("Apartamenty Klasztorna","Wrocław, Klasztorna","MURAPOL",27.0,1.0,1,1,0,1,1,1,0,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Fklasztorna.jpg?alt=media&token=fe429587-4645-441c-a24b-2c0a8a6fd5ed",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Fklasztorna.jpg?alt=media&token=5a54a48f-c42f-4437-84a5-9ba9e04de0f8",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp3.jpg?alt=media&token=724856ea-1c82-492f-a907-8a53fc70aa2b",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fklasztorna.jpg?alt=media&token=ad9d06ef-97ca-4085-918a-8ac33b33bcff",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fklasztorna_2.jpg?alt=media&token=f25cf822-aef0-403f-9db1-55fe796b47c3");
        saveNewFlat("Nowa Toskania","Wrocław, Buforowa","MURAPOL",40.0,1.0,2,3,1,1,1,1,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Ftoskania.jpg?alt=media&token=c6f6c20a-c57f-4dca-a843-cca9f1a12671",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Ftoskania.jpg?alt=media&token=70843f0c-5e8f-4a27-8004-4628c2f8e858",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp2_balkon.jpg?alt=media&token=168f02eb-f8b2-4752-b2fd-928aadb51436",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Ftoskania.jpg?alt=media&token=135691f8-b3de-40a5-9380-4d0537114ed3",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Ftoskania_2.jpg?alt=media&token=6a904e9f-989b-4475-9efe-2aaa505d6b8c");
        saveNewFlat("Promenady Wrocławskie","Wrocław, Zakładowa","VANTAGE",25.5,1.0,1,5,1,1,0,1,0,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Fpromenady.jpg?alt=media&token=3961f4d0-7934-40a6-a637-7b338592f81e",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Fpromenady.jpg?alt=media&token=4992eb8d-2dec-4a1e-8382-e72b376d57fc",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp3_balkon.jpg?alt=media&token=1cb8d92f-61c6-46d8-a212-bc592a5860bd",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fpromenady.jpg?alt=media&token=9de69d82-77fc-4e25-a3aa-81b384f895f2",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fpromenady_2.jpg?alt=media&token=03ae018c-80e4-4673-a67b-ddf4a98c1b8b");
        saveNewFlat("Dorzecze Legnickiej","Wrocław, Małopanewska","VANTAGE",37.0,2.0,1,4,0,1,1,0,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Fdorzecze.jpg?alt=media&token=8a70f74f-0cb4-48f2-917f-c25db8a4aa28",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Fdorzecze.jpg?alt=media&token=94209bb8-37d5-44de-bd26-3259d2977f0f",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp2.jpg?alt=media&token=ae70b66d-d710-42a3-81be-edf58ab71df9",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fdorzecze.jpg?alt=media&token=dcccc8be-8fb0-4199-ae59-225f588ef80a",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fdorzecze_2.jpg?alt=media&token=e6816c99-62f2-431e-8f25-112a358058b7");
        saveNewFlat("LogIn City","Wrocław, Grabiszyńska","VANTAGE",61.0,5.0,3,4,1,1,1,1,0,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Flogin.jpg?alt=media&token=755bf6e8-41fd-46dd-8c5b-be39ea71ed46",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Flogin.jpg?alt=media&token=960ac829-3d71-4c0f-8da2-db5f86cfb102",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp1_balkon.jpg?alt=media&token=a0bb3662-72e3-4c2e-8123-8cf45ca84db6",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Flogin.jpg?alt=media&token=5b590ce8-e776-40b9-807b-2e30310ede3b",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Flogin_2.jpg?alt=media&token=46748344-fb78-424e-beb9-0cf41fcd46d6");
        saveNewFlat("Di Trevi","Wrocław, Armii Krajowej","LOKUM",45.0,2.0,3,2,1,0,0,1,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Fditrevi.jpg?alt=media&token=1277791c-ae86-4dbd-a012-0ff38d6caad2",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Fditrevi.jpg?alt=media&token=14c9d568-e67f-480f-bf1e-ed363d2aa8af",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp4_balkon.jpg?alt=media&token=caf51bf0-79fc-4ef9-b9a7-eddc9db528d6",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fditrevi.jpg?alt=media&token=fb561273-7ee1-4b04-9f47-4be92ba490b4",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fditrevi_2.jpg?alt=media&token=febfd2bb-af64-4b9b-a995-45d92f595da1");
        saveNewFlat("Primera","Wrocław, Gagarina","PCG",50.0,5.0,3,4,0,1,1,1,1,
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/mini%2Fprimera.jpg?alt=media&token=71a6df48-b8e2-415e-a455-39be3e42bb1e",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/gradientPhotos%2Fprimera.jpg?alt=media&token=48d35cae-5b05-4bd0-9dbe-ee99554578aa",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/plans%2Fp4.jpg?alt=media&token=e842fc06-ff8e-427f-8ce6-a09bf280dff8",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fprimera.jpg?alt=media&token=eb838107-ab92-4575-8f58-79700a312279",
                "https://firebasestorage.googleapis.com/v0/b/nest1-e6f6b.appspot.com/o/Photos%2Fprimera_2.jpg?alt=media&token=06b6bf01-3a05-48f3-b8db-ed7649bc19e2");
    }




}

