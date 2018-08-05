package com.example.samad.flightmates;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Irfan Ullah on 8/15/2017.
 */

public class MyAdapter extends ArrayAdapter<String> {
    private JSONArray pnrV;
    private JSONArray fromV,toV,takeoffV,fnrss,fidds,ldate,fss;
    private int[] ivv;
    public MyAdapter(@NonNull Context context, String[] fn, JSONArray fnrs,JSONArray fids,JSONArray landingDate, JSONArray pnr, JSONArray from, JSONArray to, JSONArray takeoff,JSONArray fs, int[] iv) {
        super(context, R.layout.mycustom, fn);
        this.pnrV = pnr;
        this.ldate = landingDate;
        this.fnrss = fnrs;
        this.fromV = from;
        this.toV = to;
        this.fidds = fids;
        this.takeoffV = takeoff;
        this.fss = fs;
        this.ivv = iv;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
       View myView =  inflater.inflate(R.layout.mycustom,parent,false);
        TextView flightNumberText = (TextView) myView.findViewById(R.id.flightNoTextView);
        TextView fn = (TextView) myView.findViewById(R.id.flightNo);
        TextView ld = (TextView) myView.findViewById(R.id.landingDateTextView);
        TextView fids = (TextView) myView.findViewById(R.id.flightidTextView);
        TextView pnr = (TextView) myView.findViewById(R.id.pnrV);
        TextView from = (TextView) myView.findViewById(R.id.fromV);
        TextView to = (TextView) myView.findViewById(R.id.toV);
        TextView takeoff = (TextView) myView.findViewById(R.id.takeV);
        TextView fls = (TextView) myView.findViewById(R.id.flightStatus);

        ImageView iv = (ImageView) myView.findViewById(R.id.imageView);
        String fnumber = getItem(position);



        try {
            fn.setText("Flight Number: "+this.fnrss.getString(position));
            fids.setText(this.fidds.getString(position));
            fids.setVisibility(View.GONE);
            flightNumberText.setText(this.fnrss.getString(position));
            flightNumberText.setVisibility(View.GONE);
            ld.setText("Landed: "+this.ldate.getString(position));
            pnr.setText("PNR: " + this.pnrV.getString(position));
            //Toast.makeText(getContext(),"This is Adapter "+this.pnrV.get(position).toString(),Toast.LENGTH_LONG).show();
            from.setText("FROM : " + this.fromV.getString(position));
            fls.setText(this.fss.getString(position));
            fls.setVisibility(View.GONE);
            to.setText("TO: " + this.toV.getString(position));
            takeoff.setText("Take off: " + this.takeoffV.getString(position));
        }
        catch(Exception e)
        {
           // Toast.makeText(getContext(),"This is Adapter "+e.toString(),Toast.LENGTH_LONG).show();
        }
        iv.setImageResource(R.drawable.flighticons);


        return myView;
    }
}
