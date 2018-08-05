package com.example.samad.flightmates;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by SAMAD on 5/15/2017.
 */

public class Splash_Screen  extends AppCompatActivity
{
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);







                    String passenger_id = preferences.getString("pid","");
                    if(passenger_id.isEmpty())
                    {

                        Thread logoTimer = new Thread() {
                            public void run(){
                                try{
                                    int logoTimer = 0;
                                    while(logoTimer < 5000){
                                        sleep(100);
                                        logoTimer = logoTimer +100;
                                    };

                                    Intent intent= new Intent(Splash_Screen.this,login.class);
                                    startActivity(intent);
                                }

                                catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                finally{
                                    finish();
                                }
                            }
                        };



                        logoTimer.start();
                    }
                    else
                    {

                        Intent intent= new Intent(Splash_Screen.this,Dashboardd.class);
                        startActivity(intent);
                    }


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }
}



