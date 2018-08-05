package com.example.samad.flightmates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class login extends AppCompatActivity {
   Button btmr;
    EditText email, password;
    Button  sign , signup;

    final  String ROOT_URL = "http://192.168.199.2/FlightMates/";
    final String  LOG = ROOT_URL+"login.php";
    String username = "";
    String userid = "";
    String emailS = "";
    String passwordd = "";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

/*
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SIGN IN");*/
        signup=(Button)findViewById(R.id.signup);
        sign = (Button) findViewById(R.id.login);
            email = (EditText) findViewById(R.id.loginuser);
            password = (EditText) findViewById(R.id.pswrdd);
        preferences = getSharedPreferences("Login",Context.MODE_PRIVATE);
        editor = preferences.edit();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this,MainActivity.class);
                startActivity(i);
            }
        });


        sign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String em = email.getText().toString();
                final String pass = password.getText().toString();

                try {
                    StringRequest request = new StringRequest(Request.Method.POST, LOG, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                                JSONObject object = new JSONObject(response);
                                String msg = object.getString("message");
                                boolean isFound = object.getBoolean("isFound");

                                if(isFound){
                                    String pid = object.getString("pid");
                                    String passenger_name = object.getString("full_name");
                                    editor.putString("pid",pid);
                                    editor.putString("passenger_name",object.getString("full_name"));
                                    editor.commit();

                                    Intent i = new Intent(login.this,Dashboardd.class);
                                    finish();
                                    startActivity(i);

                                }else
                                {
                                    Toast.makeText(login.this,msg.toString(),Toast.LENGTH_LONG).show();
                                }



                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(login.this,e.toString()+" this is object catch bolck",Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(login.this, error.toString() + "this is volley error", Toast.LENGTH_LONG).show();
                        }

                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("password", password.getText().toString());
                            params.put("email", email.getText().toString());

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(login.this);
                    requestQueue.add(request);
                }catch(Exception e)
                {
                    Toast.makeText(login.this, e.toString(),Toast.LENGTH_LONG).show();
                }
            }

        });



    }


}
