package com.prizmcore.edgetasking;

import android.graphics.drawable.Drawable;

public class Adapter {
    private Drawable appimage;
    private String apppackage;

    public Adapter(Drawable appimage, String apppackage) {
        this.appimage = appimage;
        this.apppackage = apppackage;
    }

    public Drawable getappimage() {
        return appimage;
    }

    public String getapppackage()
    {
       return apppackage;
    }


}