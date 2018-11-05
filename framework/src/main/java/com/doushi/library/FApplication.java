package com.doushi.library;

import org.litepal.LitePalApplication;

/**
 * @author xiemy
 * @date 2017/4/13.
 */
public class FApplication extends LitePalApplication {

    private static FApplication dApplication;

    public FApplication() {
        FApplication.dApplication = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static FApplication getFApplication() {
        return dApplication;
    }
}
