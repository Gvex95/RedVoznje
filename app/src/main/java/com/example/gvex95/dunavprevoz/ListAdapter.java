package com.example.gvex95.dunavprevoz;

import android.content.Context;
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
    private int mode = 0;
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


        View v = View.inflate(myContext,R.layout.one_ride,null);

        TextView color = (TextView)v.findViewById(R.id.boja);
        TextView mp = (TextView)v.findViewById(R.id.polazak);
        TextView md = (TextView)v.findViewById(R.id.dolazak);
        final TextView notifikacijaTekst = (TextView)v.findViewById(R.id.notifikacijaTekst);


        TextView vp = (TextView)v.findViewById(R.id.vreme_polaska);
        TextView vd = (TextView)v.findViewById(R.id.vreme_dolaska);
        final Spinner izaberiVremeNotifikacije = (Spinner)v.findViewById(R.id.vremeNotifikacije);


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
            Log.wtf("TMP",tmp);
            vremePolaska = "0" + tmp;
            Log.wtf("Dodata0",vremePolaska);
        }else
        {
            vremePolaska = Integer.toString(vreme_polaska,10);
            Log.wtf("Veceod1000",vremePolaska);

        }
        vremePolaska = vremePolaska.substring(0,2) + ":" + vremePolaska.substring(2,vremePolaska.length());
        Log.wtf("Dodata:",vremePolaska);

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

        //notifikacija text i spinner
        notifikacijaTekst.setBackgroundColor(Color.parseColor("#FF0000"));
        /*
        List<String> vremena = new ArrayList<>();
        vremena.add("Notifikacija iskljucena");
        vremena.add("15 min ranije");
        vremena.add("30 min ranije");
        vremena.add("45 min ranije");
        vremena.add("Izaberi proizvoljno");

        final ArrayAdapter<String> vremenaAdapter = new ArrayAdapter<String>(this,R.layout.sample_spinner,vremena);
        vremenaAdapter.setDropDownViewResource(R.layout.sample_spinner);
        izaberiVremeNotifikacije.setAdapter(vremenaAdapter);
*/
        //skontaj sta je stisnu u spinneru
        //ako je stisnuo neke od opcija za minute, prvomeni background color za text za notifikaciju
        //ako je stisnuo isljuceno promeni na crveno background color za text za notifikaciju
        //ako je stisnuo na izaberi proizvoljno vreme, ponudi mu dialog gde ce izabrati koliko hoce minuta, promeni boju na zeleno

        //i naravno podesi notifikaciju sto cu posle uraditi
        izaberiVremeNotifikacije.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                if (selectedItem.equals("Iskljucena"))
                {
                   mRideList.get(position).setNotifikacijaText("Notifikacija");
                   mRideList.get(position).setSpinnerIndex(0);
                   mRideList.get(position).setNotificationColor("Red");
                   notifikacijaTekst.setBackgroundColor(Color.parseColor("#FF0000"));
                }
                else if (selectedItem.equals("15 min"))
                {
                    mRideList.get(position).setNotifikacijaText("Notifikacija");
                    mRideList.get(position).setSpinnerIndex(1);
                    mRideList.get(position).setNotificationColor("Green");
                    notifikacijaTekst.setBackgroundColor(Color.parseColor("#00FF00"));

                }else if (selectedItem.equals("30 min"))
                {
                    mRideList.get(position).setNotifikacijaText("Notifikacija");
                    mRideList.get(position).setSpinnerIndex(2);
                    mRideList.get(position).setNotificationColor("Green");
                    notifikacijaTekst.setBackgroundColor(Color.parseColor("#00FF00"));
                }else
                {
                    mRideList.get(position).setNotifikacijaText("Notifikacija");
                    mRideList.get(position).setSpinnerIndex(3);
                    mRideList.get(position).setNotificationColor("Green");
                    notifikacijaTekst.setBackgroundColor(Color.parseColor("#00FF00"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        notifikacijaTekst.setText(mRideList.get(position).getNotifikacijaText());
        if(mRideList.get(position).getNotificationColor()!= null && mRideList.get(position).getNotificationColor().equals("Red"))
        {
            notifikacijaTekst.setBackgroundColor(Color.parseColor("#FF0000"));
        }
        if(mRideList.get(position).getNotificationColor()!= null && mRideList.get(position).getNotificationColor().equals("Green"))
        {
            notifikacijaTekst.setBackgroundColor(Color.parseColor("#00FF00"));
        }

        izaberiVremeNotifikacije.setSelection(mRideList.get(position).getSpinnerIndex());

        v.setTag(mRideList.get(position).getId());

        return  v;
    }

    public void updateList(List<Ride> rides) {
        mRideList.clear();
        mRideList.addAll(rides);
        this.notifyDataSetChanged();
    }
}
