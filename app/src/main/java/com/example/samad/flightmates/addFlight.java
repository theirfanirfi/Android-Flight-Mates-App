package com.example.samad.flightmates;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class addFlight extends AppCompatActivity  {
    Button addFlight,pickDate;
    EditText fn,pnr,from,to,editDate;
    SharedPreferences preferences;
    DatePicker date,dp;
    SingleDateAndTimePicker sdtp;
    String FLIGHT_TAKE_OFF_DATE = "";
    String ttt;
    String ADD_FLIGHT_URL = "http://192.168.199.2/FlightMates/addFlight.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);
        Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        toolbar2.setTitle("Add New Flight");
        setSupportActionBar(toolbar2);
        addFlight = (Button) findViewById(R.id.addFlightFormBtn);
        preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String pid = preferences.getString("pid","");
        fn = (EditText) findViewById(R.id.fn);
        pnr = (EditText) findViewById(R.id.pnr);
        from = (EditText) findViewById(R.id.from);
        to = (EditText) findViewById(R.id.to);
       /* to.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
               // Toast.makeText(addFlight.this,"Focus Changed from to",Toast.LENGTH_LONG).show();
                ttt = to.getText().toString();
                if(ttt.isEmpty())
                {
                    to.setBackgroundColor(R.color.danger_color);
                    //to.setForeground(Color.WHITE);
                    //to.setTextColor(Color.WHITE);
                    //to.setBackground(Color.BLACK);
                }
                else
                {
                    to.setBackgroundColor(Color.WHITE);
                }
            }
        });

        to.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                ttt = to.getText().toString();
                if(ttt.isEmpty())
                {
                    //to.setBackgroundColor(0XFDEDE);
                    to.setBackgroundColor(R.color.danger_color);
                    //to.setTextColor(Color.WHITE);
                }
                else
                {
                    to.setBackgroundColor(Color.WHITE);
                }
                return false;
            }
        });*/
        sdtp = (SingleDateAndTimePicker) findViewById(R.id.sdtp);




        sdtp.setListener(new SingleDateAndTimePicker.Listener() {

            @Override
            public void onDateChanged(String displayed, Date date) {

                FLIGHT_TAKE_OFF_DATE = displayed;
            }
        });







        addFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                StringRequest request = new StringRequest(Request.Method.POST, ADD_FLIGHT_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            boolean isAdded = object.getBoolean("isAdded");
                            //String msg = object.getString("message");
                            //Toast.makeText(addFlight.this,msg,Toast.LENGTH_LONG).show();
                            if(isAdded)
                            {
                                Snackbar.make(view,"Flight Added",Snackbar.LENGTH_LONG).show();
                            }
                            else {
                                Snackbar.make(view,"Error Occurred, Try Again.",Snackbar.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(addFlight.this,e.toString(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(addFlight.this,error.toString(),Toast.LENGTH_LONG).show();
                    }

            }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> myparameters = new HashMap<String, String>();
                        myparameters.put("fn",fn.getText().toString());
                        myparameters.put("pnr",pnr.getText().toString());
                        myparameters.put("from",from.getText().toString());
                        myparameters.put("to",to.getText().toString());
                        myparameters.put("pid",pid);
                        myparameters.put("takeoff_date",FLIGHT_TAKE_OFF_DATE);
                        return myparameters;
                    }
                };

                RequestQueue rq = Volley.newRequestQueue(addFlight.this);
                rq.add(request);
            }
        });
    }


}
