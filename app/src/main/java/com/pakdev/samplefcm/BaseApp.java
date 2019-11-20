package com.pakdev.samplefcm;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class BaseApp extends MultiDexApplication {


    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
    }
}
