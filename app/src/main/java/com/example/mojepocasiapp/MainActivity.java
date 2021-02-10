package com.example.mojepocasiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button, button2;
    ImageView imageView;
    TextView country_yt, city_yt, temp_yt, longitude, latitude, humidity, sunrise, sunset, pressure, wind, country, city, time;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        temp_yt = findViewById(R.id.temperature);

        longitude = findViewById(R.id.Longitude);
        latitude = findViewById(R.id.Latitude);
        humidity = findViewById(R.id.Humidity);
        sunrise = findViewById(R.id.Sunrise);
        sunset = findViewById(R.id.Sunset);
        pressure = findViewById(R.id.Pressure);
        wind = findViewById(R.id.WindSpeed);
        country = findViewById(R.id.Country);
        city = findViewById(R.id.City);
        time = findViewById(R.id.Time);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,App_weather2.class);
                Bundle b = new Bundle();
                b.putString("lokalita", String.valueOf(city.getText()));
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findWeather();
            }
             });
        }

            public void findWeather() {
        String cName = editText.getText().toString();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cName + "&appid=0a080f8752d7fe351f0a33800b62896e";
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest (Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                    //volání api

                    try {
                        JSONObject jsonObjectsys = response.getJSONObject("sys");
                        JSONObject jsonObjectmain = response.getJSONObject("main");
                        JSONObject jsonObjectcoord = response.getJSONObject("coord");
                        JSONObject jsonObjectwind = response.getJSONObject("wind");

                        //najdi zemi
                        String country_find = jsonObjectsys.getString( "country");
                        country.setText(country_find);

                        //najdi město
                        String city_find = response.getString( "name");
                        city.setText(city_find);

                        //najdi teplotu
                        double temp = jsonObjectmain.getDouble ("temp");
                        temp = temp - 273.15;
                        temp_yt.setText(df2.format(temp)+"°C");

                        //najdi datum a čas
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat std = new SimpleDateFormat("dd/MM/yyyy \nHH:mm:ss");
                        String date = std.format(calendar.getTime());
                        time.setText(date);

                        //najdi zeměpisnou šířku
                        double lat_find = jsonObjectcoord.getDouble("lat");
                        latitude.setText(lat_find+"° N");

                        //najdi zeměpisnou délku
                        double long_find = jsonObjectcoord.getDouble("lon");
                        longitude.setText(long_find+"° E");

                        //najdi vlhkost vzduchu
                        int humidity_find = jsonObjectmain.getInt("humidity");
                        humidity.setText(humidity_find+" %");

                        //najdi východ slunce
                        String sunrise_find = jsonObjectsys.getString("sunrise");
                        sunrise.setText(sunrise_find);

                        //najdi západ slunce
                        String sunset_find = jsonObjectsys.getString("sunset");
                        sunset.setText(sunset_find);

                        //najdi tlak
                        String pressure_find = jsonObjectmain.getString("pressure");
                        pressure.setText(pressure_find+" hPa");

                        //najdi rychlost větru
                        String wind_find = jsonObjectwind.getString("speed");
                        wind.setText(wind_find+" km/h");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                requestQueue.add(jsonObjectRequest);
            }
}


