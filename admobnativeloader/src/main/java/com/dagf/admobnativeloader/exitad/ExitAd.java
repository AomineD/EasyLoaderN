package com.dagf.admobnativeloader.exitad;

import android.content.Context;
import android.content.Intent;

import com.dagf.admobnativeloader.utils.TypeAd;

import java.util.ArrayList;

public class ExitAd {

    private Context context;
    private String idBanner, idNative;
    private TypeAd typeAd;
    private ArrayList<String> idsAudience = new ArrayList<>();

    public ExitAd(Context m, TypeAd typeAda, String idNativea, String idBannera){
this.context = m;
this.typeAd = typeAda;
this.idNative = idNativea;
this.idBanner = idBannera;
    }

    public void showExitAd(){
        if(typeAd == TypeAd.ADMOB) {
            ExitAdView.id_admob_banner = idBanner;

        ExitAdView.id_admob_native = idNative;

        }else if(typeAd == TypeAd.HUAWEI){
            ExitAdView.id_huawei_banner = idBanner;
            ExitAdView.id_huawei_native = idNative;
        }

        context.startActivity(new Intent(context, ExitAdView.class));
    }

    public void showExitAdFacebook(){



        context.startActivity(new Intent(context, ExitAdView.class));

    }


}
