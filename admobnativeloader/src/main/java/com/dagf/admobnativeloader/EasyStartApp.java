package com.dagf.admobnativeloader;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.startapp.android.publish.ads.nativead.NativeAdDetails;
import com.startapp.android.publish.ads.nativead.NativeAdPreferences;
import com.startapp.android.publish.ads.nativead.StartAppNativeAd;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class EasyStartApp {


    private int listing;

    private Context mContext;

    public EasyStartApp(Context c, int cantity){
this.listing = cantity;
this.mContext = c;
    }


    private ArrayList<NativeAdDetails> nativos = new ArrayList<>();

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    private boolean isTest;
    public interface onLoadNative{
        void onSuccess();
        void onError(String errno);
    }

    private onLoadNative listener;

    public void setDelay(long del){
        this.delayout = del;
    }

    private long delayout = 7;
    public void setListener(onLoadNative listener1){
        this.listener = listener1;
    }

    public void loadAds(){

        final StartAppNativeAd nativeAd = new StartAppNativeAd(mContext);

        NativeAdPreferences preferences = new NativeAdPreferences();

        preferences.setAdsNumber(listing);
        preferences.setPrimaryImageSize(2);
        preferences.setSecondaryImageSize(5);

        AdEventListener adEventListener = new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {

                //
                if(isTest){
                    Log.e("MAIN", "onReceiveAd STARTAPP: "+ad.isReady() );
                }
                if(listener != null){
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ((Activity)mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onSuccess();
                            }
                        });
                    }
                }, delayout * 1000);

                }

nativos = nativeAd.getNativeAds();
            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
if(isTest){
    Log.e("MAIN", "onFailedToReceiveAd: "+ad.getErrorMessage() );
}

                if(listener != null){
                    listener.onError(ad.getErrorMessage());
                }
            }
        };

        nativeAd.loadAd(preferences, adEventListener);

    }



    public void setupNatives(View banner_container, int i, int colorbck, int textco) {

        if(isTest)
        Log.e("MAIN", "setupNatives: "+nativos.size() );
        if(nativos.size() < 1){
            return;
        }

        final TextView action;
        final TextView title_ad;
        final TextView desc_ad;
        final ImageView ad_choices;
        final ImageView ad_icon;
        final TextView sponsor;
        final CardView button_action;
        final CardView background;
        final ImageView ad_icon_play;


        action = banner_container.findViewById(R.id.callto);
        title_ad = banner_container.findViewById(R.id.title_ad);
        sponsor = banner_container.findViewById(R.id.sponsor_ad);
        desc_ad = banner_container.findViewById(R.id.sponsor_adw);
        //      normal_view = itemView.findViewById(R.id.normal_view);

        button_action = banner_container.findViewById(R.id.button_action);

        background = banner_container.findViewById(R.id.card);
        ad_choices = banner_container.findViewById(R.id.media_view);
        ad_icon = banner_container.findViewById(R.id.ad_icon_view);
        ad_icon_play = banner_container.findViewById(R.id.ad_choices);
        background.setCardBackgroundColor(colorbck);
        desc_ad.setTextColor(textco);
        title_ad.setTextColor(textco);
        sponsor.setTextColor(textco);
        button_action.setCardBackgroundColor(colorbck);
        action.setTextColor(textco);


        if(nativos.get(i) != null){

            NativeAdDetails n = nativos.get(i);

            title_ad.setText(nativos.get(i).getTitle());
            desc_ad.setText(nativos.get(i).getDescription());
            if(n.getImageUrl() != null && !n.getImageUrl().isEmpty())
            {
                Picasso.get().load(Uri.parse(n.getImageUrl())).into(ad_icon);
            }

            if(n.getSecondaryImageUrl() != null && !n.getSecondaryImageUrl().isEmpty() && ad_choices != null)
            {
                Picasso.get().load(Uri.parse(n.getSecondaryImageUrl())).fit().into(ad_choices);
            }

            if(n.getCampaignAction() == StartAppNativeAd.CampaignAction.OPEN_MARKET){
                Picasso.get().load(R.drawable.google_play).into(ad_icon_play);
                String rat =  n.getRating()+"★"+"\nGoogle Play";
                sponsor.setText(rat);
                action.setText("Install");
            }else{
                action.setText("Open");
                sponsor.setText("Anuncio");
            }




            ArrayList<View> s = new ArrayList<>();
            s.add(button_action);
            s.add(action);
            s.add(title_ad);
            n.registerViewForInteraction(banner_container, s);

if(isTest){
    Log.e(TAG, "setupNatives: "+n.getTitle() );
}

        }

        if(isTest)
        Log.e(TAG, "setupNatives: final => "+(nativos.get(i) != null));
    }

    private String TAG = "MAIN";
}
