package com.doushi.test.myproject;

import android.content.Context;

import com.library.FApplication;

/**
 * @author xiemy
 * @date 2018/2/28.
 */
public class Application extends FApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        new ThirdPartyInit().init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        new ThirdPartyInit().initializeHotfix(this);
    }
}
