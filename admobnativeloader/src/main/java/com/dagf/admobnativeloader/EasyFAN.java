package com.dagf.admobnativeloader;

import android.content.Context;
import android.net.Uri;
import androidx.cardview.widget.CardView;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class EasyFAN {
    private ArrayList<View> clickables = new ArrayList<>();
    private ArrayList<NativeAd> nativeAd = new ArrayList<>();
    private ArrayList<NativeBannerAd> nativeBannerAd = new ArrayList<>();
    private Context context;

    public interface OnNativeLoadInterface {
        void OnSuccess();

        void OnFail(String ss);
    }

    public void setRadius(boolean radius) {
        isRadius = radius;
    }

    private boolean isRadius;
    private OnNativeLoadInterface intre;

    public void setInterface(OnNativeLoadInterface dd) {
        this.intre = dd;
    }

    private boolean isDebug;

    public void setDebug(boolean s){
        this.isDebug = s;
    }

    private ArrayList<String> idsnat = new ArrayList<>();

    public EasyFAN(Context c, ArrayList<String> ad_unit) {
        this.context = c;
        this.idsnat.addAll(ad_unit);


    }


    public void loadAds(){
        isError = new boolean[idsnat.size()];

        for (int i = 0; i < idsnat.size(); i++) {
            NativeAd n = new NativeAd(context, idsnat.get(i));
            final int finalI = i;
            n.setAdListener(new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {

                }

                @Override
                public void onError(Ad ad, AdError adError) {


                    if(isDebug)
                    Log.e("MAIN", "NATIVOS onError: "+adError.getErrorMessage() + " el index => " + finalI);

                    if (intre != null)
                        intre.OnFail(adError.getErrorMessage() + " el index => " + finalI);


                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (intre != null) {
                        intre.OnSuccess();
                    }

                    if(isDebug){
                        Log.e("MAIN", "NATIVOS onAdLoaded: "+finalI);
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });
            n.loadAd();
            nativeAd.add(n);

            if(isDebug)
            Log.e("MAIN", "loadAds: loading native "+finalI);
        }
    }
    public boolean allLoaded() {
        return nativeAd.get(nativeAd.size() - 1) != null && nativeAd.get(nativeAd.size() - 1).isAdLoaded();
    }


    private MediaView mediaView;
    private AdIconView iconView1;
    private ImageView iconView;

    public void setupViews(final View banner_container, final int i, final int colorbck, final int textco) {

        final TextView action;
        final TextView title_ad;
        final TextView desc_ad;
        final ImageView ad_choices;
        final TextView sponsor;
        final CardView button_action;
        final CardView background;


        action = banner_container.findViewById(R.id.callto);
        title_ad = banner_container.findViewById(R.id.title_ad);
        sponsor = banner_container.findViewById(R.id.sponsor_ad);
        desc_ad = banner_container.findViewById(R.id.sponsor_adw);
        //      normal_view = itemView.findViewById(R.id.normal_view);

        button_action = banner_container.findViewById(R.id.button_action);
        try {
            mediaView = banner_container.findViewById(R.id.media_view);
            iconView = banner_container.findViewById(R.id.ad_icon_view);
            Log.e("MAIN", "setupViews: "+(mediaView!=null) );
        } catch (Exception e) {
            iconView1 = banner_container.findViewById(R.id.ad_icon_view);
          //  Log.e("MAIN", "setupViews: "+e.getMessage() );
        }

        background = banner_container.findViewById(R.id.card);
        ad_choices = banner_container.findViewById(R.id.ad_choices);
        background.setCardBackgroundColor(colorbck);
        desc_ad.setTextColor(textco);
        title_ad.setTextColor(textco);
        sponsor.setTextColor(textco);
        button_action.setCardBackgroundColor(colorbck);
        action.setTextColor(textco);

        if (isRadius) {
            background.setRadius(0);
        }

        if (nativeAd.get(i) != null && nativeAd.get(i).isAdLoaded()) {

            String title = nativeAd.get(i).getAdvertiserName();
            String provider = nativeAd.get(i).getSponsoredTranslation();
            String boton_action = nativeAd.get(i).getAdCallToAction();
            String patrocinador = nativeAd.get(i).getAdBodyText();
            String sp = nativeAd.get(i).getAdTranslation();

            //  com.facebook.ads.AdChoicesView adChoicesView = new com.facebook.ads.AdChoicesView(context, nativeAd, true);
            if (nativeAd.get(i).getAdChoicesImageUrl() != null)
                Picasso.get().load(Uri.parse(nativeAd.get(i).getAdChoicesImageUrl())).fit().into(ad_choices);

            title_ad.setText(title);
            desc_ad.setText(patrocinador);

            sponsor.setText(sp);

            //    ad_choices.addView(adChoicesView, 0);
            //  Log.e("MAIN", "setupViews: COLOR => "+textco);
            action.setText(boton_action);


            if (clickables.size() < 1) {
                clickables.add(button_action);
                // clickables.add(title_ad);
                // clickables.add(sponsor);
               /* if(mediaView!=null)
                clickables.add(mediaView);*/
                clickables.add(action);
            } else if (!clickables.contains(button_action)) {
                clickables.add(button_action);
                //       clickables.add(title_ad);
                //     if(mediaView!=null)
                //   clickables.add(mediaView);
                // clickables.add(sponsor);
                clickables.add(action);
            }

            if (mediaView != null)
                nativeAd.get(i).registerViewForInteraction(banner_container, mediaView, iconView, clickables);
            else {
             if(iconView1 != null)
                nativeAd.get(i).registerViewForInteraction(banner_container, iconView1, clickables);
                  }
        } else {

            nativeAd.get(i).setAdListener(new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                    // ============== NATIVO CARGO ================== //

                    String title = nativeAd.get(i).getAdvertiserName();
                    String provider = nativeAd.get(i).getSponsoredTranslation();
                    String boton_action = nativeAd.get(i).getAdCallToAction();
                    String patrocinador = nativeAd.get(i).getAdBodyText();
                    String sp = nativeAd.get(i).getAdTranslation();
                    //  com.facebook.ads.AdChoicesView adChoicesView = new com.facebook.ads.AdChoicesView(context, nativeAd, true);
                    if (nativeAd.get(i).getAdChoicesImageUrl() != null)
                        Picasso.get().load(Uri.parse(nativeAd.get(i).getAdChoicesImageUrl())).fit().into(ad_choices);

                    title_ad.setText(title);
                    desc_ad.setText(patrocinador);
                    sponsor.setText(sp);

                    //    ad_choices.addView(adChoicesView, 0);
                    //  Log.e("MAIN", "setupViews: COLOR => "+textco);
                    action.setText(boton_action);

                    if (clickables.size() < 1) {
                        clickables.add(button_action);
                        // clickables.add(title_ad);
                        // clickables.add(sponsor);
                        //if(mediaView!=null)
                        //       clickables.add(mediaView);
                        clickables.add(action);
                    } else if (!clickables.contains(button_action)) {
                        clickables.add(button_action);
                        //clickables.add(title_ad);
                        //if(mediaView!=null)
                        //  clickables.add(mediaView);
                        //clickables.add(sponsor);
                        clickables.add(action);
                    }

                    if (mediaView != null)
                        nativeAd.get(i).registerViewForInteraction(banner_container, mediaView, iconView, clickables);
                    else
                        nativeAd.get(i).registerViewForInteraction(banner_container, iconView1, clickables);
                }

                @Override
                public void onError(Ad ad, AdError adError) {

                    isError[i] = true;
                  /*  String title = nativeAd.get(i).getAdvertiserName();
                    String provider = nativeAd.get(i).getSponsoredTranslation();
                    String boton_action = nativeAd.get(i).getAdCallToAction();
                    String patrocinador = nativeAd.get(i).getAdBodyText();
                    String sp = nativeAd.get(i).getAdTranslation();

                    //  com.facebook.ads.AdChoicesView adChoicesView = new com.facebook.ads.AdChoicesView(context, nativeAd, true);
                    if(nativeAd.get(i).getAdChoicesImageUrl() != null)
                        Picasso.get().load(Uri.parse(nativeAd.get(i).getAdChoicesImageUrl())).fit().into(ad_choices);
*/


                }

                @Override
                public void onAdLoaded(Ad ad) {

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });

            if (isError.length > 0 && isError[i]) {
                background.setCardBackgroundColor(colorbck);
                title_ad.setText("Error");
                desc_ad.setText("Error on load");
                desc_ad.setTextColor(textco);
                title_ad.setTextColor(textco);
                sponsor.setText("Ad - Anuncio");
                sponsor.setTextColor(textco);
                //    ad_choices.addView(adChoicesView, 0);
                //  Log.e("MAIN", "setupViews: COLOR => "+textco);
                action.setText("Error");
                button_action.setCardBackgroundColor(colorbck);
                action.setTextColor(textco);
            }

        }
    }

    private boolean[] isError;


    private void loadBannerAgain(final int index){

        NativeBannerAd bannerAd = new NativeBannerAd(context, nativeBannerAd.get(index).getPlacementId());



     //   Log.e("MAIN", "loadBannerAgain: "+index );

        bannerAd.setAdListener(new NativeAdListener(){
            @Override
            public void onMediaDownloaded(Ad ad) {

                for(int i=0; i < nativeViewObjs.size(); i++){
                    if(nativeViewObjs.get(i).index_native == index){
                        NativeViewObj obj = nativeViewObjs.get(i);

                        setupNativeView(obj.native_view, index, obj.colorBack, obj.colorText);
                    }
                }
       //         Log.e("MAIN", "now load: "+index );
            }

            @Override
            public void onError(Ad ad, AdError adError) {
          //     Log.e("MAIN", "loadBannerAgain error: "+index );
//new Timer().schedule(canReload(), 7000);
            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });


        bannerAd.loadAd();

        nativeBannerAd.set(index, bannerAd);
    }

    // Banner nativo aaa ===============================================


    public void loadBannerAds(){
        isError = new boolean[idsnat.size()];

        for (int i = 0; i < idsnat.size(); i++) {
            NativeBannerAd n = new NativeBannerAd(context, idsnat.get(i));
            final int finalI = i;
            n.setAdListener(new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {

                }

                @Override
                public void onError(Ad ad, AdError adError) {


                    if(isDebug)
                        Log.e("MAIN", "NATIVOS onError: "+adError.getErrorMessage() + " el index => " + finalI);

                    if (intre != null)
                        intre.OnFail(adError.getErrorMessage() + " el index => " + finalI);


                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (intre != null) {
                        intre.OnSuccess();
                    }

                    if(isDebug){
                        Log.e("MAIN", "NATIVOS onAdLoaded: "+finalI);
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });
            n.loadAd();
            nativeBannerAd.add(n);

            if(isDebug)
                Log.e("MAIN", "loadAds: loading native "+finalI);
        }
    }

    public boolean isLoadedBanner(int i){
        if(nativeBannerAd.get(i) != null && nativeBannerAd.get(i).isAdLoaded()){
            return true;
        }else{
            return false;
        }
    }

ArrayList<NativeViewObj> nativeViewObjs = new ArrayList<>();

    public void setupNativeView(final View banner_container, final int i, final int colorbck, final int textco) {

        final TextView action;
        final TextView title_ad;
        final TextView desc_ad;
        final ImageView ad_choices;
        final TextView sponsor;
        final CardView button_action;
        final CardView background;


        action = banner_container.findViewById(R.id.callto);
        title_ad = banner_container.findViewById(R.id.title_ad);
        sponsor = banner_container.findViewById(R.id.sponsor_ad);
        desc_ad = banner_container.findViewById(R.id.sponsor_adw);
        //      normal_view = itemView.findViewById(R.id.normal_view);

        button_action = banner_container.findViewById(R.id.button_action);
            iconView = banner_container.findViewById(R.id.ad_icon_view);


        background = banner_container.findViewById(R.id.card);
        ad_choices = banner_container.findViewById(R.id.ad_choices);
        background.setCardBackgroundColor(colorbck);
        desc_ad.setTextColor(textco);
        title_ad.setTextColor(textco);
        sponsor.setTextColor(textco);
        button_action.setCardBackgroundColor(context.getResources().getColor(R.color.blue_fb));
        action.setTextColor(colorbck);

        if (isRadius) {
            background.setRadius(0);
        }

        if(nativeBannerAd.get(i) == null){
            return;
        }

        if (nativeBannerAd.get(i) != null && nativeBannerAd.get(i).isAdLoaded()) {

            String title = nativeBannerAd.get(i).getAdvertiserName();
            String provider = nativeBannerAd.get(i).getSponsoredTranslation();
            String boton_action = nativeBannerAd.get(i).getAdCallToAction();
            String patrocinador = nativeBannerAd.get(i).getAdBodyText();
            String sp = nativeBannerAd.get(i).getAdTranslation();

            //  com.facebook.ads.AdChoicesView adChoicesView = new com.facebook.ads.AdChoicesView(context, nativeAd, true);
            if (nativeBannerAd.get(i).getAdChoicesImageUrl() != null)
                Picasso.get().load(Uri.parse(nativeBannerAd.get(i).getAdChoicesImageUrl())).fit().into(ad_choices);

            title_ad.setText(title);
            desc_ad.setText(patrocinador);

            sponsor.setText(sp);

            //    ad_choices.addView(adChoicesView, 0);
            //  Log.e("MAIN", "setupViews: COLOR => "+textco);
            action.setText(boton_action);


            if (clickables.size() < 1) {
                clickables.add(button_action);
                // clickables.add(title_ad);
                // clickables.add(sponsor);
               /* if(mediaView!=null)
                clickables.add(mediaView);*/
                clickables.add(action);
            } else if (!clickables.contains(button_action)) {
                clickables.add(button_action);
                //       clickables.add(title_ad);
                //     if(mediaView!=null)
                //   clickables.add(mediaView);
                // clickables.add(sponsor);
                clickables.add(action);
            }


                    nativeBannerAd.get(i).registerViewForInteraction(banner_container, iconView, clickables);

        } else {

            nativeBannerAd.get(i).setAdListener(new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                    // ============== NATIVO CARGO ================== //

                    String title = nativeBannerAd.get(i).getAdvertiserName();
                    String provider = nativeBannerAd.get(i).getSponsoredTranslation();
                    String boton_action = nativeBannerAd.get(i).getAdCallToAction();
                    String patrocinador = nativeBannerAd.get(i).getAdBodyText();
                    String sp = nativeBannerAd.get(i).getAdTranslation();
                    //  com.facebook.ads.AdChoicesView adChoicesView = new com.facebook.ads.AdChoicesView(context, nativeAd, true);
                    if (nativeBannerAd.get(i).getAdChoicesImageUrl() != null)
                        Picasso.get().load(Uri.parse(nativeBannerAd.get(i).getAdChoicesImageUrl())).fit().into(ad_choices);

                    title_ad.setText(title);
                    desc_ad.setText(patrocinador);
                    sponsor.setText(sp);

                    //    ad_choices.addView(adChoicesView, 0);
                    //  Log.e("MAIN", "setupViews: COLOR => "+textco);
                    action.setText(boton_action);

                    if (clickables.size() < 1) {
                        clickables.add(button_action);
                        // clickables.add(title_ad);
                        // clickables.add(sponsor);
                        //if(mediaView!=null)
                        //       clickables.add(mediaView);
                        clickables.add(action);
                    } else if (!clickables.contains(button_action)) {
                        clickables.add(button_action);
                        //clickables.add(title_ad);
                        //if(mediaView!=null)
                        //  clickables.add(mediaView);
                        //clickables.add(sponsor);
                        clickables.add(action);
                    }
                        nativeBannerAd.get(i).registerViewForInteraction(banner_container, iconView, clickables);
                }

                @Override
                public void onError(Ad ad, AdError adError) {

                    isError[i] = true;
                  /*  String title = nativeAd.get(i).getAdvertiserName();
                    String provider = nativeAd.get(i).getSponsoredTranslation();
                    String boton_action = nativeAd.get(i).getAdCallToAction();
                    String patrocinador = nativeAd.get(i).getAdBodyText();
                    String sp = nativeAd.get(i).getAdTranslation();

                    //  com.facebook.ads.AdChoicesView adChoicesView = new com.facebook.ads.AdChoicesView(context, nativeAd, true);
                    if(nativeAd.get(i).getAdChoicesImageUrl() != null)
                        Picasso.get().load(Uri.parse(nativeAd.get(i).getAdChoicesImageUrl())).fit().into(ad_choices);
*/
                   loadBannerAgain(i);

                }

                @Override
                public void onAdLoaded(Ad ad) {

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });

            if (isError.length > 0 && isError[i]) {
                background.setCardBackgroundColor(colorbck);
                title_ad.setText("Error");
                desc_ad.setText("Error on load");
                desc_ad.setTextColor(textco);
                title_ad.setTextColor(textco);
                sponsor.setText("Ad - Anuncio");
                sponsor.setTextColor(textco);
                //    ad_choices.addView(adChoicesView, 0);
                //  Log.e("MAIN", "setupViews: COLOR => "+textco);
                action.setText("Error");
                //button_action.setCardBackgroundColor(colorbck);
                action.setTextColor(colorbck);
            }

        /*    new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    canReload(i);
                }
            }, 4000);
-*/

        }

        NativeViewObj viewObj = new NativeViewObj();
        viewObj.colorBack = colorbck;
        viewObj.colorText = textco;
        viewObj.native_view = banner_container;
        viewObj.index_native = i;

    nativeViewObjs.add(viewObj);

    }


    private void canReload(final int index){


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if(!nativeBannerAd.get(index).isAdLoaded()){
                    loadBannerAgain(index);
                }

                return null;
            }
        }.execute();


    }


}
