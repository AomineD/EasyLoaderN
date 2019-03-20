package com.dagf.admobnativeloader;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EasyFAN {
    private ArrayList<View> clickables = new ArrayList<>();
    private ArrayList<NativeAd> nativeAd = new ArrayList<>();
    private Context context;
    private ArrayList<String> idsnat = new ArrayList<>();
    
    public EasyFAN(Context c, ArrayList<String> ad_unit){
        this.context = c;
        this.idsnat.addAll(ad_unit);
    
        for(int i=0; i < ad_unit.size(); i++){
            NativeAd n = new NativeAd(c, ad_unit.get(i));
            n.loadAd();
           nativeAd.add(n);
            
        }
    
    }
    
    public boolean allLoaded(){
        return nativeAd.get(nativeAd.size() - 1) != null && nativeAd.get(nativeAd.size() - 1).isAdLoaded();
    }
    
    
    public void setupViews(View itemView, int i){

        TextView action;
        TextView title_ad;
        ImageView ad_choices;
        TextView sponsor;
        CardView button_action;
        CardView background;
        ImageView iconView;
        RelativeLayout banner_container;

        MediaView mediaView;


        iconView = itemView.findViewById(R.id.ad_icon_view);
        action = itemView.findViewById(R.id.callto);
        title_ad = itemView.findViewById(R.id.title_ad);
        sponsor = itemView.findViewById(R.id.sponsor_ad);
  //      normal_view = itemView.findViewById(R.id.normal_view);

        button_action = itemView.findViewById(R.id.button_action);
        mediaView = itemView.findViewById(R.id.media_view);

        banner_container = itemView.findViewById(R.id.banner_container);
        background = itemView.findViewById(R.id.card);
        ad_choices = itemView.findViewById(R.id.ad_choices);

        if ( nativeAd.get(i) != null && nativeAd.get(i).isAdLoaded()) {

            String title = nativeAd.get(i).getAdvertiserName();
            String provider = nativeAd.get(i).getSponsoredTranslation();
            String boton_action = nativeAd.get(i).getAdCallToAction();
            String patrocinador = nativeAd.get(i).getAdBodyText();
            String sp = nativeAd.get(i).getAdTranslation();

          //  com.facebook.ads.AdChoicesView adChoicesView = new com.facebook.ads.AdChoicesView(context, nativeAd, true);
if(nativeAd.get(i).getAdChoicesImageUrl() != null)
            Picasso.get().load(Uri.parse(nativeAd.get(i).getAdChoicesImageUrl())).fit().into(ad_choices);

            title_ad.setText(title);
            sponsor.setText(sp);
        //    ad_choices.addView(adChoicesView, 0);
            action.setText(boton_action);


            if (clickables.size() < 1) {
                clickables.add(button_action);
                clickables.add(title_ad);
                clickables.add(sponsor);
                clickables.add(mediaView);
                clickables.add(action);
            } else if (!clickables.contains(button_action)) {
                clickables.add(button_action);
                clickables.add(title_ad);
                clickables.add(mediaView);
                clickables.add(sponsor);
                clickables.add(action);
            }


            nativeAd.get(i).registerViewForInteraction(banner_container, mediaView, iconView, clickables);
        }
    }
}
