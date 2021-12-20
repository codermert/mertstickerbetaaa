package com.viztushar.stickers.app;

import androidx.multidex.MultiDexApplication;


import com.orhanobut.hawk.Hawk;

public class Application extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
    }
}
