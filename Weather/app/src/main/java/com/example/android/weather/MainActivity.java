package com.example.android.weather;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView temp;
    TextView city_name;
    TextView description;
    TextView wind_speed,message;
    TextView humidity;
    EditText name;
    String S;
    ProgressBar spin;
    weather w;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp = (TextView) findViewById(R.id.temp);
        city_name = (TextView) findViewById(R.id.city_name);
        description = (TextView) findViewById(R.id.descrip);
        wind_speed = (TextView) findViewById(R.id.wind_speed);
        humidity = (TextView) findViewById(R.id.humidity);
        name = (EditText) findViewById(R.id.city);
        message=(TextView) findViewById(R.id.message);
        spin = (ProgressBar) findViewById(R.id.spin);
        spin.setVisibility(View.INVISIBLE);



    }
    public void get(View view)
    {


        message.setText("");
        S = name.getText().toString();

        name.setText("");
        weatherfill we = new weatherfill();
        we.execute(S);

    }

    public void getforecast(View view)
    {
       //send intent

        S=name.getText().toString();
        city_name.setText("");
        description.setText("");
        humidity.setText("");
        wind_speed.setText("");
        temp.setText("");
        name.setText("");
        message.setText("");
        Log.i("String:",S);
        Intent q = new Intent(this,forecast.class);
        q.putExtra("city",S);
        startActivity(q);


    }



    private class weatherfill extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            HttpURLConnection con = null;
            InputStream is = null;
            String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";


            try {
                publishProgress();

                con = (HttpURLConnection) (new URL(BASE_URL + params[0] + "&APPID=bf821832d98654dd766d3674d1013dd2")).openConnection();

                con.setRequestMethod("GET");
                con.setDoInput(true);
                // con.setDoOutput(true);
                con.connect();

                // Let's read the response
                StringBuffer buffer = new StringBuffer();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = br.readLine()) != null)
                    buffer.append(line + "rn");

                is.close();
                br.close();
                con.disconnect();

                w=new weather();
                Log.i("hi", buffer.toString());
                w.fill_data(buffer.toString());
                if(w.getCod()!=0)
                {
                    if(params[0].toLowerCase().equals(w.retCity_name().toLowerCase()))
                        return 1;
                    else
                        return -1;
                }
                else
                    return -1;



            } catch (Throwable t) {
                t.printStackTrace();
            }


            return -1;

        }
        @Override
        protected void onProgressUpdate(Void...x)
        {
          spin.setVisibility(View.VISIBLE);
        }


        @Override
        protected void onPostExecute(Integer x) {

            spin.setVisibility(View.INVISIBLE);

            if(x==-1)
            {
                message.setText("Sorry no such city");
                city_name.setText("");
                description.setText("");
                humidity.setText("");
                wind_speed.setText("");
                temp.setText("");
            }
            else {
                temp.setText("Temp: " + w.getTemp() + " C");
                city_name.setText("Name: " + w.retCity_name());
                description.setText("Condition: " + w.getDescrip());
                humidity.setText("Humidity: " + w.getHumidity() + "%");
                wind_speed.setText("Wind speed: " + w.getWind_speed() + " km/hr");
            }

             name.setText("");


        }
    }





    }
