package com.example.android.weather;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class forecast extends AppCompatActivity {
    public ArrayList<weather_forecast> w1 = new ArrayList<weather_forecast>();
    weather_forecast b = new weather_forecast();
    Recycleradapter adapter;
    RecyclerView recycle;
    ProgressBar spinner;
    LinearLayoutManager m;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        recycle=(RecyclerView)findViewById(R.id.cycle);
        m= new LinearLayoutManager(this);
        recycle.setLayoutManager(m);
        adapter= new Recycleradapter(w1);
        recycle.setAdapter(adapter);
        spinner = (ProgressBar) findViewById(R.id.spin);
        weather_forecast_fill x = new weather_forecast_fill();
        Bundle bundle = getIntent().getExtras();
        String cit = bundle.getString("city");
        Log.i("hell:",cit);

        message = (TextView) findViewById(R.id.mess);

        message.setText("");
        x.execute(cit);







    }

    private class weather_forecast_fill extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            HttpURLConnection con = null;
            InputStream is = null;
            String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?q=";


            try {

                publishProgress();

                con = (HttpURLConnection) (new URL(BASE_URL + params[0] + "&APPID=6e5571e7901dedc32c91877d581ec755")).openConnection();

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

                b.fill_dat(buffer.toString(),0);
                Log.i("coda:",b.retcod()+"");
                if(b.retcod()!=0)
                {
                    if(!(params[0].toLowerCase().equals(b.retcity_name().toLowerCase())))
                        return -1;
                }
                else
                    return -1;


                for(int i=0;i<5;i++)
                {
                    b.fill_dat(buffer.toString(), i);
                    w1.add(b);
                    b=new weather_forecast();



                }

                for(int i=0;i<5;i++)
                {
                    Log.i("fillingtemp"+i,w1.get(i).rettemp()+"");


                }


                return 1;







            } catch (Throwable t) {
                t.printStackTrace();
            }


            return -1;

        }

        @Override
        protected void onProgressUpdate(Void... x) {
            spinner.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onPostExecute(Integer x) {
            Log.i("x=",x+"");
            spinner.setVisibility(View.GONE);
            if(x==1)
            {
                for (int i=0;i<5;i++)
                    Log.i("temperat"+i,w1.get(i).rettemp()+"");
                adapter.notifyDataSetChanged();

                Log.i("sizes:",w1.size()+"");

            }
            else {
                message.setText("No such city");
                Log.i("size:",w1.size()+"");



            }


        }


    }
}
