package com.dagf.admobnativeloader.exitad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dagf.admobnativeloader.EasyNativeLoader;
import com.dagf.admobnativeloader.R;
import com.dagf.admobnativeloader.utils.TypeAd;
import com.dagf.hmk.ads.NativeHMKLayout;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator2;

public class ExitAdView extends AppCompatActivity {

    private RecyclerView recyclerView;
    NativeAdapterExit nativeAdapterExit;
    private LinearLayout bannerCont;
    private CardView buttonQuit, buttonMoreApps;
    private CircleIndicator2 indicator;
    private int count;

    public static final String key_ids_audience = "SDASWQFMGGPFDL";

    public static String id_admob_native;
    public static String id_huawei_native;
    public static ArrayList<String> ids_audience_network = new ArrayList<>();

    public static String id_admob_banner;
    public static String id_huawei_banner;
    public static String id_audience_banner;

    public static TypeAd typeAd = TypeAd.ADMOB;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        id_admob_native = "";
        id_huawei_native = "";
        id_admob_banner = "";
        id_huawei_banner = "";
        id_audience_banner = "";
        ids_audience_network = new ArrayList<>();
        typeAd = TypeAd.ADMOB;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_ad_view);
        if(getIntent() != null && getIntent().getStringArrayListExtra(key_ids_audience) != null){
            ids_audience_network = getIntent().getStringArrayListExtra(key_ids_audience);
        }

        recyclerView = findViewById(R.id.rec_natives);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    actualPos = getCurrentItem();
                   // onPageChanged(position);
                }
            }
        });

        nativeAdapterExit = new NativeAdapterExit();
        recyclerView.setAdapter(nativeAdapterExit);


        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);

        indicator = findViewById(R.id.indicator);
        indicator.attachToRecyclerView(recyclerView, pagerSnapHelper);



        buttonMoreApps = findViewById(R.id.moreapps_btn);
        bannerCont = findViewById(R.id.adBannerContainer);
        buttonQuit = findViewById(R.id.button_quit);

        buttonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switch (typeAd){
            case ADMOB:
                configureAdmob();
                break;
            case HUAWEI:
                configureHuawei();
                break;
            case AUDIENCE:
                configureAudience();
                break;
        }
    }

    private void configureAudience() {

    }

    private void configureHuawei() {

    }

    private EasyNativeLoader easyNativeLoader;
    private ArrayList<UnifiedNativeAd> unifiedNativeAds = new ArrayList<>();
    private void configureAdmob() {
        count = 0;
        easyNativeLoader = new EasyNativeLoader(this);
        AdLoader adLoader = new AdLoader.Builder(this, id_admob_native).forUnifiedNativeAd(easyNativeLoader.setupAdapterNatives()).build();

        easyNativeLoader.adLoader = adLoader;
        unifiedNativeAds.clear();
        adLoader.loadAds(new AdRequest.Builder().build(), 3);
        easyNativeLoader.setNativeListener(new EasyNativeLoader.NativeListener() {
            @Override
            public void onLoadNative(int pos) {
                unifiedNativeAds.add(easyNativeLoader.getNat(pos));
                count++;
                nativeAdapterExit.notifyDataSetChanged();
            }

            @Override
            public void onFailed(String erno) {

            }
        });
nativeAdapterExit = new NativeAdapterExit();

        recyclerView.setAdapter(nativeAdapterExit);
        nativeAdapterExit.registerAdapterDataObserver(indicator.getAdapterDataObserver());
        //BANNER
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(id_admob_banner);
        adView.loadAd(new AdRequest.Builder().build());
        bannerCont.removeAllViews();
        bannerCont.addView(adView);
        configMovement();
    }


    private int actualPos;
    private void configMovement(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(recyclerView.getAdapter() != null && recyclerView.getAdapter().getItemCount() > 0){
                            actualPos++;
                            if(actualPos >= 3){
                                actualPos = 0;
                            }
                         recyclerView.smoothScrollToPosition(actualPos);
                        }
                    }
                });
            }
        }, 2500, 2500);
    }

    private int getCurrentItem(){
        return ((LinearLayoutManager)recyclerView.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    /** ADAPTER WE **/
    private class NativeAdapterExit extends RecyclerView.Adapter<NativeAdapterExit.NativeAdapterHolder>{

        @NonNull
        @Override
        public NativeAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = null;
            switch (typeAd){
                case ADMOB:
v = LayoutInflater.from(ExitAdView.this).inflate(R.layout.native_ad, parent, false);
                    break;
                case HUAWEI:
                    v = LayoutInflater.from(ExitAdView.this).inflate(R.layout.native_ad_huawei, parent, false);
                    break;
                case AUDIENCE:
                    v = LayoutInflater.from(ExitAdView.this).inflate(R.layout.native_ad_facebook, parent, false);
                    break;

            }
            return new NativeAdapterHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull NativeAdapterHolder holder, int position) {
holder.config(position);
        }

        @Override
        public int getItemCount() {
            return count;
        }

        public class NativeAdapterHolder extends RecyclerView.ViewHolder{
//ADMOB
            private UnifiedNativeAdView unifiedNativeAdView;
       //HUAWEI
       private NativeHMKLayout nativeHMKLayout;
       //AUDIENCE
            private View native_ad_facebook;
            public NativeAdapterHolder(@NonNull View itemView) {
                super(itemView);
                itemView.setPadding(25, 0, 25, 0);
                switch (typeAd){
                    case ADMOB:
unifiedNativeAdView = itemView.findViewById(R.id.native_adgoogle);

                        break;
                    case HUAWEI:
nativeHMKLayout = itemView.findViewById(R.id.native_huawei);
                        break;
                    case AUDIENCE:
native_ad_facebook = itemView.findViewById(R.id.banner_container);
                        break;
                }
            }

            public void config(int pos) {
                switch (typeAd){
                    case ADMOB:
                   UnifiedNativeAd ad = easyNativeLoader.getSomeNative(pos);
                   if(ad != null){
                       easyNativeLoader.populateUnifiedNativeAdView(ad, unifiedNativeAdView);
                   }
                        break;
                    case HUAWEI:
                        nativeHMKLayout = itemView.findViewById(R.id.native_huawei);
                        break;
                    case AUDIENCE:
                        native_ad_facebook = itemView.findViewById(R.id.banner_container);
                        break;
                }
            }
        }
    }
}