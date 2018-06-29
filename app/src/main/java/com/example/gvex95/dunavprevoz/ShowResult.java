package com.example.gvex95.dunavprevoz;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class ShowResult extends Activity{
    private ListView rideLst;
    private List<Ride> mRideList;
    private ListAdapter listAdapter;
    private DatabaseHelper databaseHelper;
    private Button swap;
    private Intent biceDialog;
    private Bundle extras;
    private Bundle extrasDialog;

    private String tableName;
    private int opcija;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        rideLst = findViewById(R.id.lista);
        swap = findViewById(R.id.swap);

        extrasDialog = new Bundle();

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.getReadableDatabase();

        //preuzimanje mesta pocetka i mesta dolaska, iz bundla poslatog preko intenta
        extras = getIntent().getExtras();
        String mestoPolaska = extras.getString("Prvi");
        String mestoDolaska = extras.getString("Drugi");
        opcija = extras.getInt("Switch");

        tableName = getTableName(mestoPolaska,mestoDolaska);

        if(opcija == 1)
        {
            mRideList = databaseHelper.getAllRides(tableName); //preuzmi sve iz ove tabele
            listAdapter = new ListAdapter(this,mRideList); //napravi adapter sa ovom listom
            rideLst.setAdapter(listAdapter);
        }

        if (opcija == 2)
        {
            int time = getTime();
            String day = getDay();
            mRideList = databaseHelper.getNextRides(time,day,tableName);
            listAdapter = new ListAdapter(this,mRideList); //napravi adapter sa ovom listom
            rideLst.setAdapter(listAdapter);
        }

            //swap button
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(opcija == 1)
                {
                    String newTable = tableName.substring(2,tableName.length()) + tableName.substring(0,2);
                    tableName = newTable;
                    mRideList = databaseHelper.getAllRides(tableName); //preuzmi sve iz ove tabele
                    listAdapter.updateList(mRideList);
                }

                if (opcija == 2)
                {
                    int time = getTime();
                    String day = getDay();
                    String newTable = tableName.substring(2,tableName.length()) + tableName.substring(0,2);
                    tableName = newTable;
                    mRideList = databaseHelper.getNextRides(time,day,tableName);
                    listAdapter.updateList(mRideList);
                }
            }
        });

        biceDialog = new Intent(ShowResult.this, BiceDialog.class);

        rideLst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                extrasDialog.putString("Pozicija", Integer.toString(i));
                extrasDialog.putString("ImeTabele",tableName);
                biceDialog.putExtras(extrasDialog);
                startActivity(biceDialog);
            }
        });



    }





    @Override
    protected void onResume() {
        super.onResume();

        //Log.wtf("OnResumePozvan","DA");
        if(opcija == 1)
        {
            mRideList = databaseHelper.getAllRides(tableName); //preuzmi sve iz ove tabele
            listAdapter.updateList(mRideList);
        }

        if (opcija == 2)
        {
            int time = getTime();
            String day = getDay();
            mRideList = databaseHelper.getNextRides(time,day,tableName);
            listAdapter.updateList(mRideList);
        }
    }

    private int getTime()
    {
        //moras saznati koliko je trenutno sati
        calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int time = 0;
        String vreme;
        if (minutes < 10)
        {
            String minute = String.format("%02d", minutes);

            vreme = Integer.toString(hours) + minute;

        }else
        {

            vreme = Integer.toString(hours) + Integer.toString(minutes);
        }
        time = Integer.parseInt(vreme);
        return  time;
    }

    private String getDay()
    {
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String tmp = "";
        switch (day)
        {
            case Calendar.MONDAY:
                tmp = "R";
                break;
            case Calendar.TUESDAY:
                tmp = "R";
                break;
            case Calendar.WEDNESDAY:
                tmp = "R";
                break;
            case Calendar.THURSDAY:
                tmp = "R";
                break;
            case Calendar.FRIDAY:
                tmp = "R";
                break;
            case Calendar.SATURDAY:
                tmp = "S";
                break;
            case Calendar.SUNDAY:
                tmp = "N";
                break;
        }
        return tmp;
    }

    private String getTableName(String mestoPolaska, String mestoDolaska)
    {
        String tableName = "";
        switch (mestoPolaska)
        {
            case "Novi Sad":
                switch (mestoDolaska)
                {
                    case "Backa Palanka":
                        tableName = "NSBP";
                        break;

                }
                break;
            case "Backa Palanka":
                switch (mestoDolaska)
                {
                    case "Novi Sad":
                        tableName = "BPNS";
                        break;
                    case "Mladenovo":
                        tableName = "BPML";
                        break;
                }
                break;
            case "Mladenovo":
                switch (mestoDolaska)
                {
                    case "Backa Palanka":
                        tableName = "MLBP";
                        break;
                }
                break;

        }
        return  tableName;
    }


}
