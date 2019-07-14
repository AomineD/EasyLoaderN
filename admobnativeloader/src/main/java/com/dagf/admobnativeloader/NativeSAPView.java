package com.dagf.admobnativeloader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class NativeSAPView extends RelativeLayout {
    public NativeSAPView(Context context) {
        super(context);

    }

    public NativeSAPView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    public View getNativeLayout(){
        natLay = LayoutInflater.from(getContext()).inflate(R.layout.native_startapp, (ViewGroup) getRootView(), false);

        addView(natLay);
        return natLay;
    }

    public View getNativeSmall(){
        natLay = LayoutInflater.from(getContext()).inflate(R.layout.native_startapp_smart, (ViewGroup) getRootView(), false);

        addView(natLay);
        return natLay;
    }

    private View natLay;
}
