package com.example.android.weather;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rajiv on 7/10/2017.
 */

public class Recycleradapter extends RecyclerView.Adapter<Recycleradapter.Holder> {
        private ArrayList<weather_forecast> w1;

    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView temp, wind_speed, humidity, description,date;
        private weather_forecast y;

        public Holder(View v) {
            super(v);
            temp = (TextView) v.findViewById(R.id.temp);
            date = (TextView) v.findViewById(R.id.date);

            wind_speed = (TextView) v.findViewById(R.id.wind_speed);
            humidity = (TextView) v.findViewById(R.id.humidity);
            description = (TextView) v.findViewById(R.id.description);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }

        public void binder(weather_forecast x) {
            y=x;

            temp.setText("Temperature:" + String.format("%.2f",x.rettemp())+ " C");
            date.setText("Date:"+ x.ret_date());

            wind_speed.setText("Wind Speed:" + x.retwind()+ "km/hr");
            humidity.setText("Humidity:" + x.rethumidity()+ "%");
            description.setText("Description:" + x.retdescrip());
        }
    }
        public Recycleradapter(ArrayList<weather_forecast> y)
        {
               w1=y;
        }

    @Override
    public Recycleradapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter,parent,false);
        return new Holder(inflatedView);

    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        weather_forecast w = w1.get(position);
        holder.binder(w);

    }



    @Override
    public int getItemCount() {
        return w1.size();
    }
}
