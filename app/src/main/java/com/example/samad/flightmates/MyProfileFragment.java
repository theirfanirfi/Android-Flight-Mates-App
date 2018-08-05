package com.example.samad.flightmates;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SAMAD on 7/12/2017.
 */

public class MyProfileFragment extends Fragment {
    String PROFILE_URL = "http://192.168.199.2/FlightMates/myprofile.php";
    SharedPreferences preferences;
    Button addFlight;
    EditText flightN , PNR, To ,From;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_dashboardd, container, false);
                 addFlight=(Button)rootView.findViewById(R.id.addflight);
        addFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(getActivity(),addFlight.class);
                startActivity(go);
            }
        });




        preferences= getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        final String pid = preferences.getString("pid","");
        flightN=(EditText)rootView.findViewById(R.id.fn);
        PNR=(EditText)rootView.findViewById(R.id.pnr);
        To=(EditText)rootView.findViewById(R.id.to);
        From=(EditText)rootView.findViewById(R.id.from);


        try {
            StringRequest request = new StringRequest(Request.Method.POST, PROFILE_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        Boolean isProfile = object.getBoolean("isProfile");
                        String fullname = object.getString("full_name");
                        String emailP = object.getString("p_email");
                        String countryP = object.getString("p_country");
                        String mob = object.getString("p_mob");
                     String[] myProfileValues = {"Email:"+emailP,"Country:"+countryP,"Mobile:"+mob};

                        ListView pfL = (ListView) rootView.findViewById(R.id.profile);
                        ArrayAdapter adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,myProfileValues);
                        pfL.setAdapter(adapter);
                       // String name = preferences.getString("passenger_name","");
                        if(isProfile)
                        {

                            TextView tx = (TextView) rootView.findViewById(R.id.fullSizeName);

                            tx.setText(fullname);


                            //Toast.makeText(getContext(),"Profile Found",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(getContext(),"Profile not Found",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
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
                    Map<String,String> params = new HashMap<>();
                    params.put("pid", pid);
                    return params;
                }
            };

            RequestQueue r =  Volley.newRequestQueue(getActivity());
            r.add(request);

        }catch (Exception e)
        {
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

        return rootView;


    }

public  void go(){
    Intent i = new Intent(getActivity(),addFlight.class);
    startActivity(i);
}


}
