package com.example.samad.flightmates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SAMAD on 7/12/2017.
 */

public class MyNotifications extends Fragment {
    MyIp ip = new MyIp();
    ListView ln;
    SharedPreferences preferences;
    String pid = "";

    final String NOTIFICATION_URL = "http://"+ip.IP+"/FlightMates/notifications.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.mynotificationslayout, container, false);
        preferences= getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        pid = preferences.getString("pid","");
        ln = (ListView) rootView.findViewById(R.id.notificationList);


        Thread t = new Thread(){
            @Override
            public void run() {
               try {
                   while(true)
                   {
                       sleep(1000);
                       afterThread();
                   }
               }
               catch (Exception e)
               {
                   Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
               }
            }
        };

        t.start();

        ln.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                StringRequest requestN = new StringRequest(Request.Method.POST, "http://192.168.199.2/FlightMates/LoginnedPassengerFlight.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            Boolean isFound = object.getBoolean("isfound");
                            if(isFound)
                            {
                               String flightnumber = object.getString("fn");
                                int matesCount = Integer.parseInt(object.getString("mates"));
                                if(matesCount > 0) {
                                    Intent i = new Intent(getContext(), ViewFlightMates.class);
                                    i.putExtra("flight_number", flightnumber);
                                    startActivity(i);
                                }
                                else
                                {
                                    Toast.makeText(getContext(),"You have no flight mates",Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                    Toast.makeText(getContext(),"You have no Bound flights",Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                           // e.printStackTrace();
                            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }

            }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("pid",pid);
                        return params;
                    }
                };
                RequestQueue rqq = Volley.newRequestQueue(getContext());
                    rqq.add(requestN);
            }
        });

       return rootView;
    }

    public void afterThread()
    {
        StringRequest rq = new StringRequest(Request.Method.POST, NOTIFICATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    Boolean isFound = object.getBoolean("isFound");

                    String[] n = {"No New Notification"};
                    if(isFound)
                    {
                        final String rows = object.getString("totalmates");
                        String fn = object.getString("flight_number");
                        if(Integer.parseInt(rows) > 1) {
                            n[0] = "You have " + rows + " Mates in flight " + fn;
                        }
                        else
                            n[0] = "You have "+rows+" Mate in flight "+fn;


                    }

                    ListAdapter adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,n);
                    ln.setAdapter(adapter);
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new  HashMap<>();
                params.put("pid",pid);
                return params;
            }
        };

        RequestQueue rQ = Volley.newRequestQueue(getContext());
        rQ.add(rq);

    }


}
