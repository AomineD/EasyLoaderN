package com.dagf.admobnative;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dagf.admobnativeloader.EasyFAN;
import com.dagf.admobnativeloader.EasyStartApp;
import com.dagf.admobnativeloader.NativeSAPView;
import com.startapp.android.publish.adsCommon.SDKAdPreferences;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StartAppSDK.init(this,
                "Your App ID",
                new SDKAdPreferences()
                        .setAge(35));

        final EasyStartApp easyStartApp = new EasyStartApp(this, 1);
        easyStartApp.setTest(true);
easyStartApp.loadAds();

        final NativeSAPView x = findViewById(R.id.native1);
        final NativeSAPView x2 = findViewById(R.id.native2);

        easyStartApp.setListener(new EasyStartApp.onLoadNative() {
            @Override
            public void onSuccess() {
                easyStartApp.setupNatives(x.getNativeLayout(), 0, getResources().getColor(R.color.white), getResources().getColor(R.color.black));
                easyStartApp.setupNatives(x2.getNativeLayout(), 0, getResources().getColor(R.color.white), getResources().getColor(R.color.black));
            }

            @Override
            public void onError(String errno) {

            }
        });

    }
}
