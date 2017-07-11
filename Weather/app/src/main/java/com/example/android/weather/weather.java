package com.example.android.weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rajiv Ramachandran on 05-07-2017.
 */

public class weather {
private String city_name;
    private String descrip,pressure;
    private float temp,wind_speed;
    private int humidity,cod;
    public weather()
    {
        cod=0;
        city_name="";
    }
    public void fill_data(String data)
    {
        try {
            JSONObject jobj = new JSONObject(data);
            JSONArray array = jobj.getJSONArray("weather");
            JSONObject weather_obj = array.getJSONObject(0);
            descrip=weather_obj.getString("description");
            JSONObject main=jobj.getJSONObject("main");
            temp=(float) (main.getDouble("temp")-273);
            JSONObject wind= jobj.getJSONObject("wind");
            wind_speed=(float)wind.getDouble("speed");
            pressure=main.getInt("pressure")+"hPa";
            humidity=main.getInt("humidity");
            cod=jobj.getInt("cod");
            city_name=jobj.getString("name");

        }catch (JSONException e) {
            e.printStackTrace();
        }



    }
    public String getDescrip()
    {
        return descrip;
    }
    public String getPressure()
    {
        return pressure;
    }
    public float getTemp()
    {
        return temp;
    }
    public float getWind_speed()
    {
        return wind_speed;
    }
    public int getHumidity()
    {
       return humidity;
    }
    public int getCod(){return cod;}

    public String retCity_name() {
        return city_name;
    }
    public void setCod()
    {
        cod=0;
    }

}
