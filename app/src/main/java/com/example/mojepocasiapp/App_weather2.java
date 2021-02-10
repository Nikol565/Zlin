package com.example.mojepocasiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class App_weather2 extends AppCompatActivity {

    Button button;
    TextView longitude, latitude, humidity, sunrise, sunset, pressure, wind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_weather2);

        longitude = findViewById(R.id.Longitude);
        latitude = findViewById(R.id.Latitude);
        humidity = findViewById(R.id.Humidity);
        sunrise = findViewById(R.id.Sunrise);
        sunset = findViewById(R.id.Sunset);
        pressure = findViewById(R.id.Pressure);
        wind = findViewById(R.id.WindSpeed);

        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App_weather2.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Bundle b = getIntent().getExtras();
        String str = b.getString("lokalita");
        findWeather(str);
    }
    public void findWeather(String param1) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + param1 + "&appid=0a080f8752d7fe351f0a33800b62896e";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    //najdi zeměpisnou šířku
                    JSONObject object2 = jsonObject.getJSONObject("coord");
                    double lat_find = object2.getDouble("lat");
                    latitude.setText(lat_find+"° N");

                    //najdi zeměpisnou délku
                    JSONObject object3 = jsonObject.getJSONObject("coord");
                    double long_find = object3.getDouble("lon");
                    longitude.setText(long_find+"° E");

                    //najdi vlhkost vzduchu
                    JSONObject object4 = jsonObject.getJSONObject("main");
                    int humidity_find = object4.getInt("humidity");
                    humidity.setText(humidity_find+" %");

                    //najdi východ slunce
                    JSONObject object5 = jsonObject.getJSONObject("sys");
                    String sunrise_find = object5.getString("sunrise");
                    sunrise.setText(sunrise_find);

                    //najdi západ slunce
                    JSONObject object6 = jsonObject.getJSONObject("sys");
                    String sunset_find = object6.getString("sunset");
                    sunset.setText(sunset_find);

                    //najdi tlak
                    JSONObject object7 = jsonObject.getJSONObject("main");
                    String pressure_find = object7.getString("pressure");
                    pressure.setText(pressure_find+" hPa");

                    //najdi rychlost větru
                    JSONObject object9 = jsonObject.getJSONObject("wind");
                    String wind_find = object9.getString("speed");
                    wind.setText(wind_find+" km/h");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(App_weather2.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(App_weather2.this);
        requestQueue.add(stringRequest);
    }
}