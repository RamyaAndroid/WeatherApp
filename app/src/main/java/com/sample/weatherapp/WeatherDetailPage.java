package com.sample.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.sample.weatherapp.utils.IConstants;

public class WeatherDetailPage extends AppCompatActivity {
String CITYDATA="";
    String FEELS_LIKE="";
    String CLOUD="";
    String DESC="";
    String clearTemp="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail_page);

        Intent intent=getIntent();
        CITYDATA=intent.getStringExtra(IConstants.CITY_NAME);
        clearTemp=intent.getStringExtra(IConstants.TEMP);
        CLOUD=intent.getStringExtra(IConstants.MAIND);
        FEELS_LIKE=intent.getStringExtra(IConstants.FEELS_LIKE);
        DESC=intent.getStringExtra(IConstants.DESC);
        getSupportActionBar().setTitle(CITYDATA);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView temp=findViewById(R.id.textView_ctemp);
        TextView fl=findViewById(R.id.textView_fl);
        TextView main=findViewById(R.id.textView_main);
        TextView desc=findViewById(R.id.textView_desc);

        temp.setText(clearTemp);
        fl.setText("Feels Like : "+FEELS_LIKE);
        main.setText(CLOUD);
        desc.setText(DESC);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}