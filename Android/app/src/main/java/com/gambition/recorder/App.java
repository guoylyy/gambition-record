package com.gambition.recorder;

import android.app.Application;

import org.litepal.LitePalApplication;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        LitePalApplication.initialize(this);
    }
}
