package com.example.gvex95.dunavprevoz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context myContext;
    private List<Ride> mRideList;
    public List<Ride> updateRideList;
    DatabaseHelper databaseHelper;
    public ListAdapter(Context myContext, List<Ride> mRideList)
    {
        this.myContext = myContext;
        this.mRideList = mRideList;
    }


    @Override
    public int getCount() {
        return mRideList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRideList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mRideList.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        databaseHelper = new DatabaseHelper(myContext);
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();

        View v = View.inflate(myContext,R.layout.one_ride,null);

        TextView color = (TextView)v.findViewById(R.id.boja);
        TextView mp = (TextView)v.findViewById(R.id.polazak);
        TextView md = (TextView)v.findViewById(R.id.dolazak);
        TextView notifikacijaTekst = (TextView)v.findViewById(R.id.notifikacijaTekst);
        TextView vremeDoIsteka = (TextView)v.findViewById(R.id.vremeDoIsteka);

        TextView vp = (TextView)v.findViewById(R.id.vreme_polaska);
        TextView vd = (TextView)v.findViewById(R.id.vreme_dolaska);


        notifikacijaTekst.setText("Notifikacija");

       switch(mRideList.get(position).getDan())
       {
           case "RSN":
               color.setBackgroundColor(Color.parseColor("#9cfcfc"));
               color.setText("RSN");
               break;
           case "RS":
               color.setBackgroundColor(Color.parseColor("#ffff4d"));
               color.setText("RS");
               break;
           case "RN":
               color.setBackgroundColor(Color.parseColor("#b3b300"));
               color.setText("RN");
               break;
           case "R":
               color.setBackgroundColor(Color.parseColor("#99ff99"));
               color.setText("R");
               break;
           case "SN":
               color.setBackgroundColor(Color.parseColor("#ff8000"));
               color.setText("SN");
               break;
           case "S":
               color.setBackgroundColor(Color.parseColor("#ec7979"));
               color.setText("S");
               break;
           case "N":
               color.setBackgroundColor(Color.parseColor("#ff0000"));
               color.setText("N");
               break;
           case "D":
               color.setBackgroundColor(Color.parseColor("#f8f820"));
               color.setText("D");
               break;
           case "DSN":
               color.setBackgroundColor(Color.parseColor("#595959"));
               color.setText("DSN");
               break;
       }

       //vreme polaska i procenjeno vreme dolaska
        int vreme_polaska,vreme_dolaska;
        String vremePolaska = "";
        String vremeDolaska = "";

        vreme_polaska = mRideList.get(position).getPolazak();

        vreme_dolaska = mRideList.get(position).getDolazak();

        if (vreme_polaska < 1000)
        {
            String tmp = Integer.toString(vreme_polaska,10);

            vremePolaska = "0" + tmp;

        }else
        {
            vremePolaska = Integer.toString(vreme_polaska,10);


        }
        vremePolaska = vremePolaska.substring(0,2) + ":" + vremePolaska.substring(2,vremePolaska.length());


        if (vreme_dolaska < 1000)
        {
            String tmp = Integer.toString(vreme_dolaska,10);
            vremeDolaska = "0" + tmp;
        }else
        {
            vremeDolaska = Integer.toString(vreme_dolaska,10);
        }
        vremeDolaska = vremeDolaska.substring(0,2) + ":" + vremeDolaska.substring(2,vremeDolaska.length());

        // vreme polaska i vreme dolaska
        vp.setText(vremePolaska);
        vd.setText(vremeDolaska);

        //mesta polaska,mesto dolaska
        mp.setText(mRideList.get(position).getMesto_polaska());
        md.setText(mRideList.get(position).getMesto_dolaska());

        String boja = mRideList.get(position).getNotifikacija_boja();
        Log.wtf("Boja", boja);
        switch (boja)
        {
            case "Red":
                notifikacijaTekst.setBackgroundColor(Color.parseColor("#FF0000"));
                break;
            case "Green":
                notifikacijaTekst.setBackgroundColor(Color.parseColor("#00FF00"));
                break;
        }

        String vreme = mRideList.get(position).getNotifikacija_vreme();
        Log.wtf("Vreme",vreme);
        switch (vreme)
        {
            case "Off":
                vremeDoIsteka.setText("Iskljuceno");
                break;
            case "15 min":
                vremeDoIsteka.setText("15 min");
                break;
            case "30 min":
                vremeDoIsteka.setText("30 min");
                break;
            case "45 min":
                vremeDoIsteka.setText("45 min");
                break;
            default:
                vremeDoIsteka.setText(vreme + " min");
        }





        v.setTag(mRideList.get(position).getId());

        return  v;
    }

    public void updateList(List<Ride> rides) {
        //Log.wtf("UpdateListIzAdaptera","DA");
        mRideList.clear();
        mRideList.addAll(rides);
        notifyDataSetChanged();
    }
}
