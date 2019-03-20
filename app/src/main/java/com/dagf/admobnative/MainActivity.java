package com.dagf.admobnative;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dagf.admobnativeloader.EasyFAN;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<String> ids = new ArrayList<>();
        ids.add("615153802242783_615156668909163");
final EasyFAN easyFAN = new EasyFAN(this, ids);
        final View x = findViewById(R.id.native1);
new Timer().schedule(new TimerTask() {
    @Override
    public void run() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("MAIN", "run: s "+(x != null));

                easyFAN.setupViews(x, 0, getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorPrimary));

            }
        });
    }
}, 9500);

    }
}
