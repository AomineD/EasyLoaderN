package com.dagf.admobnative;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dagf.admobnativeloader.EasyFAN;
import com.facebook.ads.AdSettings;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import com.dagf.admobnative.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<String> idsbanner = new ArrayList<>();

        idsbanner.add("410359413142447_626423458202707");

        //AdSettings.setDebugBuild(true);

        final EasyFAN easyFAN = new EasyFAN(this, idsbanner);

        easyFAN.loadBannerAds();

        easyFAN.setDebug(true);
       final View v = findViewById(R.id.banner_native);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       easyFAN.setupNativeView(v, 0, getResources().getColor(R.color.white),getResources().getColor(R.color.black) );
                    }
                });
            }
        }, 2000);

    }
}
