package com.sample.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sample.weatherapp.utils.IConstants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button lookupButton;
    EditText cityEditText;
    String cityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        lookupButton = findViewById(R.id.button_lookup);
        cityEditText = findViewById(R.id.editText_cityName);
        lookupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        cityData = cityEditText.getText().toString();

        if(cityData!=null&&!cityData.isEmpty()){
            Intent intent=new Intent(MainActivity.this,WeatherDetailsActivity.class);
            intent.putExtra(IConstants.CITY_NAME,cityData);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Please Enter CityName", Toast.LENGTH_SHORT).show();
        }




    }
}