package com.example.samad.flightmates;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.*;
import static java.security.AccessController.getContext;

/**
 * Created by SAMAD on 7/12/2017.
 */

public class MyFlightsFragment extends Fragment {
    MyIp ip = new MyIp();
    final String FLIGHTS_URL = "http://"+ip.IP+"/FlightMates/fetchflights.php";
    final String LANDED_URL = "http://192.168.199.2/FlightMates/landflight.php";
    final String DELETE_URL = "http://192.168.199.2/FlightMates/deleteflight.php";

    List<String> fnr;
    JSONArray fnrrs,pnr,frm,tod,tkoff,flightIDS,isLandedJSON,landingDate,fs;
    HashMap<String,String> fids;
    SharedPreferences preferences;
    JSONObject object;
    ListAdapter adapter;
    ListView lv;
    AbsoluteLayout layout;
    String pid = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.myflightslayout, container, false);
         lv = (ListView) rootView.findViewById(R.id.flightsListView);
        preferences= getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        pid = preferences.getString("pid","");

        Thread t = new Thread()
        {
            @Override
            public void run() {
                try {
                    while(true)
                    {
                        sleep(1000);
                        displayFlights();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        };

        t.start();
//        StringRequest request = new StringRequest(Request.Method.POST, FLIGHTS_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    object = new JSONObject(response);
//                    //Toast.makeText(getContext(),object.getString("message"),Toast.LENGTH_LONG).show();
//                    fnrrs =  object.getJSONArray("fn");
//                    flightIDS = object.getJSONArray("flight_id");
//                    isLandedJSON = object.getJSONArray("isLanded");
//                    landingDate = object.getJSONArray("landingDate");
//
//                 // Toast.makeText(getContext(),"Inside Response "+landingDate.length(),Toast.LENGTH_LONG).show();
//
//                    pnr = object.getJSONArray("pnr");
//                    frm = object.getJSONArray("from");
//                    tod = object.getJSONArray("to");
//                    tkoff = object.getJSONArray("takeoff");
//                    fs = object.getJSONArray("flight_status");
//
//                    String[] fnrs = new String[fnrrs.length()];
//                    try {
//                        int[] iv = {R.drawable.flighticons, R.drawable.flighticons};
//                        adapter = new MyAdapter(getContext(), fnrs,fnrrs,flightIDS,landingDate, pnr, frm, tod, tkoff,fs, iv);
//                        lv.setAdapter(adapter);
//                    }catch(Exception e)
//                    {
//                        Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
//                    }
//                    //Toast.makeText(getContext(),fnrrs.getString(0),Toast.LENGTH_LONG).show();
//
//
//
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(getContext(),"MY EXCEPTION "+e.toString(),Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
//            }
//
//    }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String,String> params = new HashMap<>();
//                params.put("pid",pid);
//                return params;
//            }
//        };
//
//        RequestQueue rq = Volley.newRequestQueue(getContext());
//        rq.add(request);

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
            TextView tx = (TextView) view.findViewById(R.id.flightidTextView);
            String id = tx.getText().toString();
            final TextView ftx = (TextView) view.findViewById(R.id.flightNoTextView);

            final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Choose Action");
            builder.setPositiveButton("View Mates", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Bundle bundle = new Bundle();
                    bundle.putString("flight_number",ftx.getText().toString());
                    Intent mates = new Intent(getContext(),ViewFlightMates.class);
                    mates.putExtras(bundle);
                    startActivity(mates);
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.setIcon(R.drawable.flighticons);
            builder.show();

        }
    });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {

                final TextView tx = (TextView) view.findViewById(R.id.flightidTextView);
                TextView fs = (TextView) view.findViewById(R.id.flightStatus);
                String flightStatus = fs.getText().toString();
                String id = tx.getText().toString();
              //  TextView ftx = (TextView) view.findViewById(R.id.flightNoTextView);
               //Toast.makeText(getContext(),"Contains: "+id,Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Choose Action");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, final int i) {
//
//                    lv.removeViewAt(i);
//                    adapter.notify();
                    StringRequest deleteRequest = new StringRequest(Request.Method.POST, DELETE_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject dobj = new JSONObject(response);
                                String msg = dobj.getString("message");



                                Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
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
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("fid",tx.getText().toString());
                            return params;
                        }
                    };

                    RequestQueue drq = Volley.newRequestQueue(getContext());
                    drq.add(deleteRequest);

                }
            });
              if(flightStatus.equals("0")) {

                  builder.setNegativeButton("Landed", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          final TextView tx = (TextView) view.findViewById(R.id.flightidTextView);
                          final TextView fls = (TextView) view.findViewById(R.id.flightStatus);
                          final TextView landedtextview = (TextView) view.findViewById(R.id.landingDateTextView);
                          //Toast.makeText(getContext(),"ID LANDED : "+tx.getText().toString(),Toast.LENGTH_LONG).show();

                          StringRequest landRequest = new StringRequest(Request.Method.POST, LANDED_URL, new Response.Listener<String>() {
                              @Override
                              public void onResponse(String response) {
                                  try {
                                      JSONObject objLanded = new JSONObject(response);
                                      String msg = objLanded.getString("message");
                                      fls.setText("1");
                                      landedtextview.setText("Landed");
                                      landedtextview.setTextColor(Color.RED);

                                      Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                                  } catch (JSONException e) {
                                      //e.printStackTrace();
                                      Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                                  }
                              }
                          }, new Response.ErrorListener() {
                              @Override
                              public void onErrorResponse(VolleyError error) {
                                  Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                              }

                          }) {
                              @Override
                              protected Map<String, String> getParams() throws AuthFailureError {
                                  Map<String, String> params = new HashMap<String, String>();
                                  params.put("fid", tx.getText().toString());
                                  return params;
                              }
                          };

                          RequestQueue rq2 = Volley.newRequestQueue(getContext());
                          rq2.add(landRequest);
                      }
                  });
              }

            builder.setIcon(R.drawable.flighticons);
            builder.show();

                return false;
            }
        });



        return rootView;
    }

    public void displayFlights()
    {
        StringRequest request = new StringRequest(Request.Method.POST, FLIGHTS_URL, new Response.Listener<String>() {
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

                    String[] fnrs = new String[fnrrs.length()];
                    try {
                        int[] iv = {R.drawable.flighticons, R.drawable.flighticons};
                        adapter = new MyAdapter(getContext(), fnrs,fnrrs,flightIDS,landingDate, pnr, frm, tod, tkoff,fs, iv);
                        lv.setAdapter(adapter);
                    }catch(Exception e)
                    {
                        Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                    //Toast.makeText(getContext(),fnrrs.getString(0),Toast.LENGTH_LONG).show();





                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"MY EXCEPTION "+e.toString(),Toast.LENGTH_LONG).show();
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

                Map<String,String> params = new HashMap<>();
                params.put("pid",pid);
                return params;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(getContext());
        rq.add(request);
    }


}
