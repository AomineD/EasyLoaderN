package com.dagf.admobnativeloader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import androidx.cardview.widget.CardView;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

public class EasyNativeLoader {


    private Context contexto;

    public EasyNativeLoader(Context mm){
        this.contexto = mm;
    }


    public interface ReceiveListener{
        void OnReceive(UnifiedNativeAd ad);

        void OnFailed();
    }


    public interface EasyListener{
        void OnClosed();

        void OnFailed(String errno);
    }

    public interface NativeListener{
        void onLoadNative(int pos);
        void onFailed(String erno);
    }

    private EasyListener easyListener;

    public void setEasyListener(EasyListener k){
        this.easyListener = k;
    }

    public void setNativeListener(NativeListener nativeListener) {
        this.nativeListener = nativeListener;
    }

    private NativeListener nativeListener;



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

  /*  public void newInstance(View mainview, int adcount){
this.AdCount = adcount;
d = 0;
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
*/
    public void SetViewsId(ArrayList<Integer> ids){
        list_d.clear();
        list_d.addAll(ids);
    }






    // ======================================= ASIGNAR VIEWS ======================================== //
   private int d = 0;
 /*   private void SetupViewsAnother(View views) {

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

    }*/


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



       /* nativos = new AdLoader.Builder(contexto, ad_unit).forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                Log.e("MAIN", "onUnifiedNativeAdLoaded: GOES"+isdd);
                adsNativosEx[isdd] = unifiedNativeAd;

                SetupGods(isdd);
                isdd++;

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
        Log.e("MAIN", "EasyNativeLoader: Cantidad = "+AdCount);
*/
    }


    // ============================================ SETUP NATIVES ADAPTER ================================================ //
  // ===================================================================================================================== //
    public ArrayList<UnifiedNativeAd> nativeAdsAdapter = new ArrayList<>();
    public boolean isLoading = true;

    public AdLoader adLoader;
    public UnifiedNativeAd.OnUnifiedNativeAdLoadedListener setupAdapterNatives(){

       return new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
               Log.e("MAIN", "onUnifiedNativeAdLoaded: "+adLoader.isLoading() );

                nativeAdsAdapter.add(unifiedNativeAd);
                if(nativeListener != null){
nativeListener.onLoadNative(nativeAdsAdapter.size() - 1);
                }
                isLoading = adLoader.isLoading();
            }
        };
    }

    public UnifiedNativeAd getSomeNative(int index){
        if(nativeAdsAdapter.size() > index && nativeAdsAdapter.get(index) != null){
            return nativeAdsAdapter.get(index);
        }else{
            return null;
        }
    }

    /**
     =====================================
     RECUERDA QUE EL NORMAL LAYOUT DEBE TENER COMO ID
     normal_view
     =====================================

    public void SetupHolder(UnifiedNativeAdView view, int wht){

        RelativeLayout banner_container;
        RelativeLayout norml;
  //      UnifiedNativeAdView adViewUnif;
        TextView action;
        TextView title_ad;
        TextView sponsor;
        ImageView iconView;
        MediaView mediaviu;



        banner_container = view.findViewById(R.id.banner_container);
        norml = view.findViewById(R.id.normal_view);
        mediaviu = view.findViewById(R.id.mediaView);
     //   adViewUnif = view.findViewById(R.id.unified);
        view.setMediaView(mediaviu);

        iconView = view.findViewById(R.id.ad_icon_view);
        action = view.findViewById(R.id.callto);
        title_ad = view.findViewById(R.id.title_ad);
        sponsor = view.findViewById(R.id.sponsor_ad);


        banner_container.setVisibility(View.VISIBLE);
        norml.setVisibility(View.GONE);

        if(nativeAdsAdapter.size() > 0 && nativeAdsAdapter.get(wht) != null){


            if(nativeAdsAdapter.get(wht).getMediaContent() != null){

                view.getMediaView().setMediaContent(nativeAdsAdapter.get(wht).getMediaContent());
                Log.e("MAIN", "SetupHolder: todo bien "+(nativeAdsAdapter.get(wht).getMediaContent().getAspectRatio()) );

            }else{

                Log.e("MAIN", "SetupHolder: es null mk" );
                //nativeAdsAdapter.get(wht).getMediaContent().getMainImage();
            }


            String title = nativeAdsAdapter.get(wht).getHeadline();//MainActivity.nativeBannerAd[m3us.get(position).getId_native()].getAdHeadline();
       //     String provider = nativeAdsAdapter.get(wht).getAdvertiser(); //MainActivity.nativeBannerAd[m3us.get(position).getId_native()].getSponsoredTranslation();
            String boton_action = nativeAdsAdapter.get(wht).getCallToAction(); //MainActivity.nativeBannerAd[m3us.get(position).getId_native()].getAdCallToAction();
            String patrocinador = nativeAdsAdapter.get(wht).getBody(); //MainActivity.nativeBannerAd[m3us.get(position).getId_native()].getAdBodyText();

            title_ad.setText(title);
            sponsor.setText(patrocinador);
            action.setText(boton_action);
            Log.e("MAIN", "SetupHolder: "+(nativeAdsAdapter.get(wht).getIcon() != null) );
            if(nativeAdsAdapter.get(wht).getIcon() != null) {
              //  iconView.setImageDrawable(nativeAdsAdapter.get(wht).getIcon().getDrawable());
             //   Log.e("MAIN", "SetupHolder: "+nativeAdsAdapter.get(wht).getIcon().getUri().toString() );
                Picasso.get().load(nativeAdsAdapter.get(wht).getIcon().getUri()).fit().into(iconView);
            }
            else{
               // Log.e("MAIN", "SetupHolder: "+nativeAdsAdapter.get(wht).getImages().size() );
               // Log.e("MAIN", "SetupHolder: url "+nativeAdsAdapter.get(wht).getImages().get(0).getUri().toString() );
            }


            //  Picasso.get().load(MainActivity.nativeOrig.getAdChoicesInfo().getImages().get(0).getUri()).fit().into(iconView_1);

            nativeAdsAdapter.get(wht).setUnconfirmedClickListener(new UnifiedNativeAd.UnconfirmedClickListener() {
                @Override
                public void onUnconfirmedClickReceived(String s) {
                    //  Toast.makeText(getContext(), "CLICK!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onUnconfirmedClickCancelled() {

                }
            });

            view.setCallToActionView(action);
            view.setCallToActionView(action);
            view.setBodyView(sponsor);
            view.setIconView(iconView);
            view.setHeadlineView(title_ad);

            view.setNativeAd(nativeAdsAdapter.get(wht));



        }

    }

     **/

    public  UnifiedNativeAd getNat(int index){
       return nativeAdsAdapter.get(index);
    }

    public  void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view.
        adView.setVisibility(View.VISIBLE);
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));


        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
      adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
       /*VideoController vc = nativeAd.getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
            videoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    refresh.setEnabled(true);
                    videoStatus.setText("Video status: Video playback has ended.");
                    super.onVideoEnd();
                }
            });
        } else {
            videoStatus.setText("Video status: Ad does not contain a video asset.");
            refresh.setEnabled(true);
        }*/
    }


    public static String getSha1(Context c){


        String hash = "";
        try {
            PackageInfo info = c.getPackageManager().getPackageInfo(
                    c.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                hash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("MAIN", "getSha1: NameNotFoundException =  "+e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.e("MAIN", "getSha1: NoSuchAlgorithmException = "+e.getMessage());
        }

        return hash;
    }

    public static String getSha1(String c, Context cpm){


        String hash = "";
        try {
            PackageInfo info = cpm.getPackageManager().getPackageInfo(
                    c,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                hash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("MAIN", "getSha1: NameNotFoundException =  "+e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            Log.e("MAIN", "getSha1: NoSuchAlgorithmException = "+e.getMessage());
        }

        Log.e("MAIN", "getSha1: "+c+" hahah "+cpm.getPackageName());

        return hash;
    }

}
