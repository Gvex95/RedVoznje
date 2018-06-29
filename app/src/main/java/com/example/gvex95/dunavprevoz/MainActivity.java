package com.example.gvex95.dunavprevoz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.gvex95.dunavprevoz.DatabaseHelper.DBNAME;

public class MainActivity extends AppCompatActivity {
    private Button search;
    private Button swapButton;
    private Spinner list1;
    private Spinner list2;
    private Intent showResult;
    private DatabaseHelper mDBHelper;
    private String prvi;
    private String drugi;
    private Bundle extras;
    private Switch switch1;
    private Switch switch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.pretrazi_id);
        list1 = findViewById(R.id.lista1);
        list2 = findViewById(R.id.lista2);
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        swapButton = findViewById(R.id.swapButton);

        mDBHelper = new DatabaseHelper(this);
        extras = new Bundle();

        //ubaci listu gradova
        List<String > gradovi1 = new ArrayList<String >();
        gradovi1.add("Putujem od:");
        gradovi1.add("Novi Sad");
        gradovi1.add("Backa Palanka");
        gradovi1.add("Mladenovo");

        List<String > gradovi2 = new ArrayList<String >();
        gradovi2.add("Putujem do:");
        gradovi2.add("Novi Sad");
        gradovi2.add("Backa Palanka");
        gradovi2.add("Mladenovo");

        final ArrayAdapter<String > spinnerAdapter1 = new ArrayAdapter<String>(this,R.layout.sample_spinner,gradovi1)
        {
            @Override
            public boolean isEnabled(int position)
            {
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        final ArrayAdapter<String > spinnerAdapter2 = new ArrayAdapter<String>(this,R.layout.sample_spinner,gradovi2)
        {
            @Override
            public boolean isEnabled(int position)
            {
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerAdapter1.setDropDownViewResource(R.layout.sample_spinner);
        spinnerAdapter2.setDropDownViewResource(R.layout.sample_spinner);
        list1.setAdapter(spinnerAdapter1);
        list2.setAdapter(spinnerAdapter2);



        showResult = new Intent(MainActivity.this,ShowResult.class);




        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(!database.exists()) {
            mDBHelper.getReadableDatabase(); //OVO CE SAMO DA KOPIRA BAZU U NEKI FOLDER VIDECEMO GDE
        }


        //zameni pocetni i krajnji grad
        swapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position1 = list1.getSelectedItemPosition();
                int position2 = list2.getSelectedItemPosition();


                if (list1.getAdapter().equals(spinnerAdapter1)) {
                    list1.setAdapter(spinnerAdapter2);
                    list2.setAdapter(spinnerAdapter1);
                } else {
                    list2.setAdapter(spinnerAdapter1);
                    list1.setAdapter(spinnerAdapter2);
                }
                list1.setSelection(position2);
                list2.setSelection(position1);
            }

        });


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch1.isChecked() || switch2.isChecked())
                {
                    prvi = list1.getSelectedItem().toString();
                    drugi = list2.getSelectedItem().toString();


                    extras.putString("Prvi",prvi);
                    extras.putString("Drugi",drugi);
                    if (switch1.isChecked())
                    {
                        extras.putInt("Switch", 1);
                    }
                    if (switch2.isChecked())
                    {
                        extras.putInt("Switch",2);
                    }

                    showResult.putExtras(extras);
                    startActivity(showResult);
                }else
                {
                    Toast.makeText(MainActivity.this,"Morate odabrati neku od opcija", Toast.LENGTH_LONG).show();
                }

            }
        });


        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true)
                {
                    switch2.setChecked(false);
                }
            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true)
                {
                    switch1.setChecked(false);
                }
            }
        });


    }

}
