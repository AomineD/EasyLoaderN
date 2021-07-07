package com.dagf.admobnativeloader.utils;

import android.content.Context;
import android.view.View;

import androidx.cardview.widget.CardView;

import com.dagf.admobnativeloader.R;
import com.facebook.ads.NativeAdViewAttributes;
import com.mopub.nativeads.AdapterHelper;
import com.mopub.nativeads.FacebookTemplateRenderer;
import com.mopub.nativeads.GooglePlayServicesAdRenderer;
import com.mopub.nativeads.GooglePlayServicesViewBinder;
import com.mopub.nativeads.MoPubNative;
import com.mopub.nativeads.MoPubStaticNativeAdRenderer;
import com.mopub.nativeads.NativeAd;
import com.mopub.nativeads.NativeErrorCode;
import com.mopub.nativeads.RequestParameters;
import com.mopub.nativeads.ViewBinder;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;

public class MoPubNativeUtil {

    private static Context context;
    public static void initialize(Context contexta){
        context = contexta;
    }

    private static ArrayList<NativeAd> moPubNatives = new ArrayList<>();

    public static void loadMoPub(int countAd, String id){
        moPubNatives.clear();

        if(id.isEmpty()){
            return;
        }

        for (int i = 0; i < countAd; i++) {

       MoPubNative     moPubNative = new MoPubNative(context, id, new MoPubNative.MoPubNativeNetworkListener() {
                @Override
                public void onNativeLoad(NativeAd nativeAd) {
                    moPubNatives.add(nativeAd);
                }

                @Override
                public void onNativeFail(NativeErrorCode nativeErrorCode) {

                }
            });

            ViewBinder viewBinder = new ViewBinder.Builder(R.layout.native_ad_mopub)
                    .mainImageId(R.id.native_main_image)
                    .iconImageId(R.id.native_icon_image)
                    .titleId(R.id.native_title)
                    .textId(R.id.native_text)
                    .privacyInformationIconImageId(R.id.native_privacy_information_icon_image)
                    .sponsoredTextId(R.id.native_sponsored_text_view)
                    .build();

            MoPubStaticNativeAdRenderer moPubStaticNativeAdRenderer = new MoPubStaticNativeAdRenderer(viewBinder);

            FacebookTemplateRenderer facebookAdRenderer = new FacebookTemplateRenderer(new NativeAdViewAttributes(context));


            final GooglePlayServicesAdRenderer googlePlayServicesAdRenderer = new GooglePlayServicesAdRenderer(
                    new GooglePlayServicesViewBinder.Builder(R.layout.native_ad)
                            .mediaLayoutId(R.id.ad_media) // bind to your `com.mopub.nativeads.MediaLayout` element
                            .iconImageId(R.id.ad_app_icon)
                            .titleId(R.id.ad_headline)
                            .textId(R.id.ad_body)
                            .callToActionId(R.id.ad_call_to_action)
                            .privacyInformationIconImageId(R.id.ad_advertiser)
                            .build());

            moPubNative.registerAdRenderer(moPubStaticNativeAdRenderer);

            moPubNative.registerAdRenderer(facebookAdRenderer);
            moPubNative.registerAdRenderer(googlePlayServicesAdRenderer);

            EnumSet<RequestParameters.NativeAdAsset> desiredAssets = EnumSet.of(
                    RequestParameters.NativeAdAsset.TITLE,
                    RequestParameters.NativeAdAsset.TEXT,
                    RequestParameters.NativeAdAsset.CALL_TO_ACTION_TEXT,
                    RequestParameters.NativeAdAsset.MAIN_IMAGE,
                    RequestParameters.NativeAdAsset.ICON_IMAGE,
                    RequestParameters.NativeAdAsset.STAR_RATING
            );


            RequestParameters mRequestParameters = new RequestParameters.Builder()
                    .desiredAssets(desiredAssets)
                    .build();

            moPubNative.makeRequest(mRequestParameters);
        }

    }

    public static void showNative(CardView layout){
        if(moPubNatives.size()  < 1)
            return;

        int index = new Random().nextInt(moPubNatives.size());

        NativeAd nativeAd = moPubNatives.get(index);

    AdapterHelper adapterHelper = new AdapterHelper(context, 0, 3);


        View v = adapterHelper.getAdView(null, layout, nativeAd, new ViewBinder.Builder(0).build());
        // Set the native event listeners (onImpression, and onClick).
        nativeAd.setMoPubNativeEventListener(new NativeAd.MoPubNativeEventListener() {
            @Override
            public void onImpression(View view) {

            }

            @Override
            public void onClick(View view) {

            }
        });
        // Add the ad view to our view hierarchy
        layout.addView(v);
    }
}
