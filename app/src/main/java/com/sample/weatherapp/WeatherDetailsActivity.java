package com.sample.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sample.weatherapp.utils.IConstants;
import com.sample.weatherapp.utils.MyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherDetailsActivity extends AppCompatActivity implements View.OnClickListener {

   String CITYDATA="";
   TextView cloudyTextView;
   TextView cloudyTextView_Value;
   TextView clearTextValue;
   TextView rainTextValue;
   String FEELS_LIKE;
   String CLOUD;
   String DESC;
    String clearTemp;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://api.openweathermap.org/data/2.5/forecast?q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        Intent intent=getIntent();
        CITYDATA=intent.getStringExtra(IConstants.CITY_NAME);
        getSupportActionBar().setTitle(CITYDATA);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();

        if (MyUtils.isConnected(WeatherDetailsActivity.this)) {
            sendAndRequestResponse();
        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }

    private void initViews(){
        cloudyTextView=findViewById(R.id.textView_cloudyTemp);
        cloudyTextView_Value=findViewById(R.id.textView_cloudyTempValue);
        clearTextValue=findViewById(R.id.textView_clearTempValue);
        rainTextValue=findViewById(R.id.textView_rainTempValue);
        cloudyTextView.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(WeatherDetailsActivity.this,WeatherDetailPage.class);
        intent.putExtra(IConstants.CITY_NAME,CITYDATA);
        intent.putExtra(IConstants.TEMP,clearTemp);
        intent.putExtra(IConstants.FEELS_LIKE,FEELS_LIKE);
        intent.putExtra(IConstants.MAIND,CLOUD);
        intent.putExtra(IConstants.DESC,DESC);
        startActivity(intent);
    }
    private void sendAndRequestResponse() {

        ProgressDialog progressDialog=new ProgressDialog(WeatherDetailsActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url+""+CITYDATA+""+IConstants.APP_ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.cancel();

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray(IConstants.LIST);
                    for (int i = 0; i <1 ; i++) {
                        JSONObject jo=jsonArray.getJSONObject(i);
                        JSONObject jo2=jo.getJSONObject(IConstants.MAIN);

                         clearTemp=jo2.getString("temp");
                        String cloudTemp=jo2.getString("temp_min");
                        String rainTemp=jo2.getString("temp_max");
                        FEELS_LIKE=jo2.getString("feels_like");

                        cloudyTextView_Value.setText(FEELS_LIKE);
                        clearTextValue.setText(clearTemp);
                        rainTextValue.setText(rainTemp);

                        JSONArray weaArray=jo.getJSONArray("weather");
                        for (int j = 0; j < weaArray.length(); j++) {
                            JSONObject jo3=weaArray.getJSONObject(j);

                            CLOUD=jo3.getString("main");
                            DESC=jo3.getString("description");

                        }





                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                Toast.makeText(WeatherDetailsActivity.this, "Error : "+error.toString(), Toast.LENGTH_SHORT).show();


            }
        });

        mRequestQueue.add(mStringRequest);
    }
}