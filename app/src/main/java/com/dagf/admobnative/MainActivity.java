package com.dagf.admobnative;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dagf.admobnativeloader.EasyFAN;
import com.dagf.admobnativeloader.EasyNativeLoader;
import com.dagf.admobnativeloader.exitad.ExitAd;
import com.dagf.admobnativeloader.utils.TypeAd;
import com.facebook.ads.AdSettings;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import com.dagf.admobnative.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    ArrayList<UnifiedNativeAd> unifiedNativeAds = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


      /* ArrayList<String> idsbanner = new ArrayList<>();

        idsbanner.add("2505373932857364_3418640408197374");

        AdSettings.setDebugBuild(true);

        final EasyFAN easyFAN = new EasyFAN(this, idsbanner);

        easyFAN.loadAds();

        easyFAN.setDebug(true);*/
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

    //    final EasyFAN easyNativeLoader = new EasyFAN(this, );



       final View v = findViewById(R.id.banner_native);

     /*  AdLoader.Builder builder = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110");

       builder.forUnifiedNativeAd(easyNativeLoader.setupAdapterNatives());

      AdLoader  adLoader = builder.build();
      adLoader.loadAds(new AdRequest.Builder().build(), 4);

      easyNativeLoader.adLoader = adLoader;*/
        ExitAd exitAd = new ExitAd(this, TypeAd.ADMOB, "ca-app-pub-3940256099942544/2247696110", "ca-app-pub-3940256099942544/6300978111");

        exitAd.showExitAd();
    }


}
