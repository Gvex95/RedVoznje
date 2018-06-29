package com.example.gvex95.dunavprevoz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteAssetHelper {

    public static final String DBNAME = "database3.sqlite";
    public static  String DBLOCATION;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
        DBLOCATION = context.getFilesDir().getPath();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase()
    {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        Log.d("DATABASE HELPER", dbPath);
        if (mDatabase != null && mDatabase.isOpen())
        {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);

    }

    public void closeDatabase()
    {
        if (mDatabase != null)
        {
            mDatabase.close();
        }
    }

    public List<Ride> getAllRides(String tableName)
    {
        Ride ride = null;
        List<Ride> rideList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + tableName, null);


        if (!cursor.moveToFirst())
        {
            Log.wtf("DatabaseHelper","Kursor je prazan, mozda query nije vratio nista");
        }else
        {
            while(!cursor.isAfterLast())
            {
            ride = new Ride(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4),
                    cursor.getString(5), cursor.getString(6),cursor.getString(7));

            //Log.wtf("CitamBazu",Integer.toString(cursor.getInt(0)));
            //Log.wtf("CitamBazu",cursor.getString(6));
            //Log.wtf("CitamBazu",cursor.getString(7));
            //Log.wtf("CitamBazu","----------------");

            rideList.add(ride);
            cursor.moveToNext();
            }
        }
        cursor.close();
        closeDatabase();
        return rideList;
    }

    public List<Ride> getNextRides(int time, String day,String tableName)
    {
        Ride ride = null;
        String upit = "SELECT * FROM " + tableName + " WHERE " + time + " < POLAZAK AND DAN like " + "'%" + day + "%'";
        //Log.wtf("Upit",upit);

        List<Ride> rideList = new ArrayList<>();
        openDatabase();
        //ovo nece raditi sigurno zato proveri ovo cudo
        Cursor cursor = mDatabase.rawQuery(upit,null);
        if (!cursor.moveToFirst())
        {
            Log.wtf("DatabaseHelper","Kursor je prazan, mozda query nije vratio nista");
        }else {
            while (!cursor.isAfterLast()) {

                ride = new Ride(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6),cursor.getString(7));
                rideList.add(ride);
                cursor.moveToNext();

            }
        }
        cursor.close();
        closeDatabase();
        return rideList;

    }

    public String getString(int position, String columnName, String tableName)
    {
        String returnString = "";
        String upit = "SELECT " + columnName + " FROM " + tableName + " WHERE " + "ID = " + position;
        //Log.wtf("Upit:",upit);
        openDatabase();
        Cursor cursor = mDatabase.rawQuery(upit,null);

        if (!cursor.moveToFirst())
        {
            //Log.wtf("DatabaseHelper","Kursor je prazan, mozda query nije vratio nista");
        }else {
            while (!cursor.isAfterLast()) {
                returnString = cursor.getString(0);
            }
        }
        cursor.close();
        closeDatabase();

        return  returnString;

    }























}

