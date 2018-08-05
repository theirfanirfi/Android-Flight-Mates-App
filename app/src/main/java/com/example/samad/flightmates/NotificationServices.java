package com.example.samad.flightmates;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
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
 * Created by Irfan Irfi on 9/13/2017.
 */

public class NotificationServices extends Service {
    Thread t;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RemoteViews remoteViews;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        Toast.makeText(getApplicationContext(),"ServiceStarted",Toast.LENGTH_LONG).show();
        t = new Thread()
        {
            @Override
            public void run() {

                while(true)
                {
                    try {

                        NotificationShow();
                        sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        t.start();


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //SERVICE STOPED
    }

    private void NotificationShow()
    {
        preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
       final String id = preferences.getString("pid","");
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.199.2/FlightMates/pushnotification.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    Boolean isFound = object.getBoolean("isfound");
                    if(isFound)
                    {
                        String fn = object.getString("fn");
                        String mates = object.getString("mates");
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                        builder.setSmallIcon(R.drawable.flighticons);
                        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        builder.setSound(sound);
                        builder.setContentTitle("Flight Mates");
                        //remoteViews.setTextViewText(R.id.flightMatesTopNotificationView,"you have "+mates+ " Mates in Flight "+fn);
                        //builder.setCustomBigContentView(remoteViews);
                        Intent i = new Intent(getApplicationContext(),ViewFlightMates.class);
                        i.putExtra("flight_number",fn);
                        PendingIntent j = PendingIntent.getActivity(getApplicationContext(),0,i,0);
                        builder.setContentIntent(j);
                        builder.setContentText("you have "+mates+ " Mates in Flight "+fn);
                        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        nm.notify(0,builder.build());

                    }
                } catch (JSONException e) {
                    //e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }

    }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("pid",id);
                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(request);





    }

}
