package com.example.samad.flightmates;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
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

public class MainActivity extends AppCompatActivity {
      EditText email, username , password , mobileNumber, address;
      TextView signup ,  lin;

    final String ROOT_URL = "http://192.168.199.2/FlightMates/";
    final String REG = ROOT_URL + "registration.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

       /* Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SIGN UP");
*/


        lin = (TextView) findViewById(R.id.lin);
        signup = (TextView) findViewById(R.id.sign);
        email = (EditText) findViewById(R.id.emailAddress);
       username= (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pass);
        mobileNumber = (EditText) findViewById(R.id.mobphone);
         address = (EditText) findViewById(R.id.address);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/LatoLight.ttf");
        Typeface custom_font1 = Typeface.createFromAsset(getAssets(), "fonts/LatoRegular.ttf");
           lin.setTypeface(custom_font1);
        email.setTypeface(custom_font);
        username.setTypeface(custom_font);
        password.setTypeface(custom_font);
        mobileNumber.setTypeface(custom_font);
        address.setTypeface(custom_font);

        lin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,login.class);
        startActivity(intent);

    }
});

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String uname = username.getText().toString();
                final String e_mail = email.getText().toString();
                final String pass = password.getText().toString();

                final String mobb = mobileNumber.getText().toString();
                final String contry = address.getText().toString();

                    try {
                        StringRequest request = new StringRequest(Request.Method.POST, REG, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject object = new JSONObject(response);
                                    String msg = object.getString("message");
                                    Boolean isRegistered = object.getBoolean("redirect");
                                    if (isRegistered) {
                                        Intent intent = new Intent(MainActivity.this, login.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(MainActivity.this, "Something went wrong, Please try again later. ", Toast.LENGTH_LONG).show();
                                    }


                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity.this, e.toString() + " this cn object catch bolck", Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, error.toString() + "this is volley error", Toast.LENGTH_LONG).show();
                            }

                        }) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("fullname", uname);
                                params.put("email", e_mail);
                                params.put("password",pass);
                                params.put("mob", mobb);

                                params.put("country", contry);
                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                        requestQueue.add(request);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }

            }

        });


    }



}





