package com.example.sam.duluthbikes;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Sam on 3/22/2017.
 */

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String userName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // R.id.toolbar = in menu_bar.xml
        //setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    /**
     * Gets the account username from file.
     * @param filePath The filepath of the file the username is stored on
     * @return username The username of the account
     * @throws IOException Just in case there's an issue with the buffered reader
     */
    public String getUserName (String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fin = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        sb.append(line);
        String username = sb.toString();
        reader.close();
        fin.close();

        return username;
    }

    /**
     * Checks if a user account exists, otherwise starts the CreateAccount activity.
     */
    public void initializeUser() {
        File file = this.getFileStreamPath("account.txt");
        if (file == null || !file.exists()) {
            Intent createAccount = new Intent(this, CreateAccountActivity.class);
            startActivity(createAccount);
        } else {
            try {
                userName = getUserName(file.toString());
                Log.d("username on main", userName);
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startMainActivity(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_start) {
            Intent startIntent = new Intent(this, MapsActivity.class);
            startActivity(startIntent);
        } else if (id == R.id.nav_events) {
            Intent eventIntent = new Intent(this, EventsActivity.class);
            startActivity(eventIntent);
        } else if (id == R.id.nav_report) {
            Intent reportIntent = new Intent(this, ReportActivity.class);
            startActivity(reportIntent);
        } else if (id == R.id.nav_discount) {
            Intent discountIntent = new Intent(this, DiscountActivity.class);
            startActivity(discountIntent);
        } else if (id == R.id.nav_tours) {
            Intent tourIntent = new Intent(this, ToursActivity.class);
            startActivity(tourIntent);
        } else if (id == R.id.nav_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
        } else if (id == R.id.nav_location) {
            Intent locationIntent = new Intent(this, PracticeMaps.class);
            startActivity(locationIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}