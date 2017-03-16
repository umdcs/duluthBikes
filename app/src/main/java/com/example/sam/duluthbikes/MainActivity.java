package com.example.sam.duluthbikes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Sam on 3/13/2017.
 */

public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void displayMap(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

}

