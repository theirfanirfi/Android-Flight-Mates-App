package com.example.samad.flightmates;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

import static android.os.Build.VERSION_CODES.M;

public class Dashboardd extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardd);




        preferences = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preferences.edit();
        String user_name = preferences.getString("user","");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(mViewPager);

       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboardd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.about:
               showAboutUs();
                break;
            case R.id.logout:
                logout();
                break;
            case R.id.ns:
                //Toast.makeText(Dashboardd.this,"Notification working",Toast.LENGTH_LONG).show();
                Intent notificationservice = new Intent(Dashboardd.this,NotificationServices.class);
                startService(notificationservice);
                break;


        }
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position)
            {
                case 0:
                    MyProfileFragment myProfileFragment = new MyProfileFragment();
                    return myProfileFragment;
                case 1:
                    MyFlightsFragment myFlightsFragment = new MyFlightsFragment();
                        return myFlightsFragment;
                case 2:
                    MyNotifications myNotifications = new MyNotifications();
                    return myNotifications;


                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "My Profile";
                case 1:
                    return "My Flights";
                case 2:
                    return "Notifications";
            }
            return null;
        }
    }
    public void showAboutUs()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("About the app");
        builder.setMessage("FlightMates gives solution to connect the passengers with each other before the flight takeoff.Our aim is to provide a better services and ease for the passengers  to\n" +
                "connect with each other. \n \n");
        builder.setCancelable(true);
        builder.show();
    }
 //  public  View resetpass(){
 //      Toast.makeText(this,"working",Toast.LENGTH_LONG).show();
//        AlertDialog.Builder Mbuilder= new AlertDialog.Builder(Dashboardd.this);
      // Inflater inflater = getLayoutInflater().inflate(R.layout.restpas,null);
//        View mview =getLayoutInflater().inflate(R.layout.restpas,null);
//       return mview;
//
//        EditText oldpass=(EditText)findViewById(R.id.ouldpasss);
//
//        EditText newpas=(EditText)findViewById(R.id.newpassword);
//
//        EditText confirmpass=(EditText)findViewById(R.id.confirmpass);



  //  }


    public void logout()
    {
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setTitle("Log Out");


        builder.setMessage("Are You sure to finsh   this session");


        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                editor.remove("pid");
                editor.remove("passenger_name");
                editor.clear();
                editor.commit();
                Intent i = new Intent(Dashboardd.this,login.class);
                finish();
                startActivity(i);

            }
        });
        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                Toast.makeText(getApplicationContext(), " you are in FlightMates", Toast.LENGTH_SHORT).show();

                dialog.cancel();
            }

        });

        // Showing Alert Message
        builder.show();

    }

}
