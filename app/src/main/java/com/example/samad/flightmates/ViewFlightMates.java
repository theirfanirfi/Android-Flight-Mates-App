package com.example.samad.flightmates;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewFlightMates extends AppCompatActivity {
    MyIp ip = new MyIp();
    final String ViewFlightMatesUrl = "http://"+ip.IP+"/FlightMates/ViewFlightMates.php";
    String bundleFlightNumber = "";
    SharedPreferences preferences;
    String pid = "";
    JSONObject object;
    JSONArray fnrrs,flightIDS,isLandedJSON,landingDate,pnr,frm,tod,tkoff,fs;
    ExpandableListView explv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flight_mates);

        Bundle bundle = getIntent().getExtras();
        bundleFlightNumber = bundle.getString("flight_number");
       // Toast.makeText(ViewFlightMates.this,bundleFlightNumber,Toast.LENGTH_LONG).show();
        preferences= getSharedPreferences("Login", Context.MODE_PRIVATE);
        pid = preferences.getString("pid","");
        explv = (ExpandableListView) findViewById(R.id.explv);
        StringRequest request = new StringRequest(Request.Method.POST, ViewFlightMatesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    object = new JSONObject(response);
                    //Toast.makeText(getContext(),object.getString("message"),Toast.LENGTH_LONG).show();
                    fnrrs =  object.getJSONArray("fn");
                    flightIDS = object.getJSONArray("flight_id");
                    isLandedJSON = object.getJSONArray("isLanded");
                    landingDate = object.getJSONArray("landingDate");

                    // Toast.makeText(getContext(),"Inside Response "+landingDate.length(),Toast.LENGTH_LONG).show();

                    pnr = object.getJSONArray("pnr");
                    frm = object.getJSONArray("from");
                    tod = object.getJSONArray("to");
                    tkoff = object.getJSONArray("takeoff");
                    fs = object.getJSONArray("flight_status");
                    MyExpandableAdapter adapter = new MyExpandableAdapter(ViewFlightMates.this,fnrrs,pnr,frm,tod,tkoff,landingDate);
                    explv.setAdapter(adapter);
                }
                catch (Exception e)
                {
                    Toast.makeText(ViewFlightMates.this,e.toString(),Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewFlightMates.this,error.toString(),Toast.LENGTH_LONG).show();
            }

    }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("pid",pid);
                params.put("fn",bundleFlightNumber);
                return params;
            }
        };


        RequestQueue rq = Volley.newRequestQueue(ViewFlightMates.this);
        rq.add(request);



    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
