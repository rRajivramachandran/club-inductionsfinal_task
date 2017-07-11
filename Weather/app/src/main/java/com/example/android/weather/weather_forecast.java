package com.example.android.weather;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by rajiv on 7/9/2017.
 */

public class weather_forecast {
private String city_name;
    private String descrip;
    private String date;
    private float temp;
    private float wind_speed ;
    private int humidity;
    private int cod;
    public weather_forecast()
    {
        cod=0;

    }
    public void fill_dat(String data,int i) {
        try {



            JSONObject jobj = new JSONObject(data);
            JSONArray jarray = jobj.getJSONArray("list");

                JSONObject j = jarray.getJSONObject(i*8);
                JSONObject main = j.getJSONObject("main");
                temp = ((float) main.getDouble("temp")-273);
                humidity = main.getInt("humidity");
                JSONObject wind = j.getJSONObject("wind");
                wind_speed = (float) wind.getDouble("speed");
                JSONArray weather = j.getJSONArray("weather");
                JSONObject weobj = weather.getJSONObject(0);
                descrip = weobj.getString("description");
                date = j.getString("dt_txt");
                date = date.substring(0,11);

            JSONObject city = jobj.getJSONObject("city");
            city_name = city.getString("name");
            cod = jobj.getInt("cod");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String retdescrip()
    {
        return descrip;
    }
    public float rettemp()
    {
        return temp;
    }
    public int rethumidity()
    {
        return humidity;
    }
    public float retwind()
    {
        return wind_speed;
    }
    public String retcity_name()
    {
        return city_name;
    }
    public int retcod()
    {
        return cod;
    }
    public void set_cod()
    {
        cod=0;
    }
    public String ret_date()
    {
        return date;
    }


}
