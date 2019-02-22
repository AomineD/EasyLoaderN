package com.dagf.admobnativeloader;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.ArrayList;

public class EasyNativeLoader {

    private String ad_unit;

    private Context contexto;

    public EasyNativeLoader(Context mm, String ad){
this.ad_unit = ad;
        this.contexto = mm;
    }


    public interface EasyListener{
        void OnClosed();

        void OnFailed(String errno);
    }

    private EasyListener easyListener;

    public void setEasyListener(EasyListener k){
        this.easyListener = k;
    }

    public void SetupIntersticial(final String ad_unit_int){
        interstitialAd = new InterstitialAd(contexto);
        interstitialAd.setAdUnitId(ad_unit_int);
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                easyListener.OnClosed();
                SetupIntersticial(ad_unit_int);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                easyListener.OnFailed("Error code: "+i);
            }
        });
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public boolean isInterstitialLoaded(){
        return interstitialAd != null && interstitialAd.isLoaded();
    }

    public void showIntersticial(){
        if(interstitialAd.isLoaded()){
            interstitialAd.show();
        }
    }

// =========================================== VARIABLES NORMALS ======================================================= //

    private InterstitialAd interstitialAd;
    private AdLoader nativos;
    private UnifiedNativeAd[] adsNativosEx;
    public static boolean LoadedNative;
    private ArrayList<Integer> list_d = new ArrayList<>();

    // ============================================ VARIABLES VIEWS ============================================ //

    private RelativeLayout[] banner_container;
    private UnifiedNativeAdView[] adViewUnif;
    private TextView[] action;
    private TextView[] title_ad;
    private TextView[] sponsor;
    private CardView[] button_action;
    private CardView[] background;
    private ImageView[] iconView;
    private ImageView[] ad_choices;
    private MediaView[] mediaviu;

    // ========================================================================================================= //

    private int AdCount;

    public void newInstance(View mainview, int adcount){
this.AdCount = adcount;
        SetCountView(adcount);

if(adcount == 0){
    return;
}else if(adcount == 1)
{
View nat1 = mainview.findViewById(list_d.get(0));
SetupViewsAnother(nat1);
}else if (adcount == 2){

    View nat1 = mainview.findViewById(list_d.get(0));
    SetupViewsAnother(nat1);
    View nat2 = mainview.findViewById(list_d.get(1));
    SetupViewsAnother(nat2);

}else if (adcount == 3){

    View nat1 = mainview.findViewById(list_d.get(0));
    SetupViewsAnother(nat1);
    View nat2 = mainview.findViewById(list_d.get(1));
    SetupViewsAnother(nat2);
    View nat3 = mainview.findViewById(list_d.get(2));
    SetupViewsAnother(nat3);

}else if (adcount == 4){

    View nat1 = mainview.findViewById(list_d.get(0));
    SetupViewsAnother(nat1);
    View nat2 = mainview.findViewById(list_d.get(1));
    SetupViewsAnother(nat2);
    View nat3 = mainview.findViewById(list_d.get(2));
    SetupViewsAnother(nat3);
    View nat4 = mainview.findViewById(list_d.get(3));
    SetupViewsAnother(nat4);

}else if (adcount == 5){

    View nat1 = mainview.findViewById(list_d.get(0));
    SetupViewsAnother(nat1);
    View nat2 = mainview.findViewById(list_d.get(1));
    SetupViewsAnother(nat2);
    View nat3 = mainview.findViewById(list_d.get(2));
    SetupViewsAnother(nat3);
    View nat4 = mainview.findViewById(list_d.get(3));
    SetupViewsAnother(nat4);
    View nat5 = mainview.findViewById(list_d.get(4));
    SetupViewsAnother(nat5);

}

if(adcount > 0){
    SetupAnotherNatives();
}

    }

    public void SetViewsId(ArrayList<Integer> ids){
        list_d.clear();
        list_d.addAll(ids);
    }






    // ======================================= ASIGNAR VIEWS ======================================== //
   private int d = 0;
    private void SetupViewsAnother(View views) {

        banner_container[d] = views.findViewById(R.id.banner_container);
        mediaviu[d] = views.findViewById(R.id.mediaView);
        adViewUnif[d] = views.findViewById(R.id.unified);

        background[d] = views.findViewById(R.id.card);
        iconView[d] = views.findViewById(R.id.ad_icon_view);
        action[d] = views.findViewById(R.id.callto);
        title_ad[d] = views.findViewById(R.id.title_ad);
        sponsor[d] = views.findViewById(R.id.sponsor_ad);
        button_action[d] = views.findViewById(R.id.button_action);
        ad_choices[d] = views.findViewById(R.id.ad_choices);

        d++;

    }


// ================================= ESTO ES PARA SETEAR LA CANTIDAD DE ANUNCIOS EN VIEW ================================ //
    private void SetCountView(int adcount) {

        adsNativosEx = new UnifiedNativeAd[adcount];
        banner_container = new RelativeLayout[adcount];
        adViewUnif = new UnifiedNativeAdView[adcount];
        action = new TextView[adcount];
        sponsor = new TextView[adcount];
        title_ad = new TextView[adcount];
        button_action = new CardView[adcount];
        background = new CardView[adcount];
        iconView = new ImageView[adcount];
        ad_choices = new ImageView[adcount];
        mediaviu = new MediaView[adcount];
        
    }

    // ======================================= SETEAR DATOS ===================================== //

    private void SetupGods(int isdd) {

        // if(!nativeLoader_1.isLoading()) {
        String title = adsNativosEx[isdd].getHeadline();//MainActivity.nativeBannerAd[m3us.get(position).getId_native()].getAdHeadline();
        String provider = adsNativosEx[isdd].getAdvertiser(); //MainActivity.nativeBannerAd[m3us.get(position).getId_native()].getSponsoredTranslation();
        String boton_action = adsNativosEx[isdd].getCallToAction(); //MainActivity.nativeBannerAd[m3us.get(position).getId_native()].getAdCallToAction();
        String patrocinador = adsNativosEx[isdd].getBody(); //MainActivity.nativeBannerAd[m3us.get(position).getId_native()].getAdBodyText();

        title_ad[isdd].setText(title);
        sponsor[isdd].setText(patrocinador);
        action[isdd].setText(boton_action);
        if(adsNativosEx[isdd].getIcon() != null)
            iconView[isdd].setImageDrawable(adsNativosEx[isdd].getIcon().getDrawable());

        //  Picasso.get().load(MainActivity.nativeOrig.getAdChoicesInfo().getImages().get(0).getUri()).fit().into(iconView_1);

        adsNativosEx[isdd].setUnconfirmedClickListener(new UnifiedNativeAd.UnconfirmedClickListener() {
            @Override
            public void onUnconfirmedClickReceived(String s) {
                //  Toast.makeText(getContext(), "CLICK!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnconfirmedClickCancelled() {

            }
        });

        adViewUnif[isdd].setCallToActionView(action[isdd]);
        adViewUnif[isdd].setCallToActionView(action[isdd]);
        adViewUnif[isdd].setBodyView(sponsor[isdd]);
        adViewUnif[isdd].setIconView(iconView[isdd]);
        adViewUnif[isdd].setHeadlineView(title_ad[isdd]);
        adViewUnif[isdd].setMediaView(mediaviu[isdd]);
        adViewUnif[isdd].setNativeAd(adsNativosEx[isdd]);

        // }

    }


    // =============================================================================================================== //
    private int isdd;


    private void SetupAnotherNatives(){



        nativos = new AdLoader.Builder(contexto, ad_unit).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                adsNativosEx[isdd] = unifiedNativeAd;
                SetupGods(isdd);
                isdd++;
                // Log.e("MAIN", "onUnifiedNativeAdLoaded: GOES");
            }
        }).withAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("MAIN", "onAdLoaded: LOADED PAPI");
            }

            @Override
            public void onAdFailedToLoad(int i) {
                Log.e("MAIN", "onAdFailedToLoad: CODE = "+i);
                super.onAdFailedToLoad(i);
            }
        }).build();

        nativos.loadAds(new AdRequest.Builder().build(), AdCount);

    }


}
