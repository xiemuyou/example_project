package com.doushi.library;

import com.doushi.library.widgets.swipebacklayout.ActivityHelper;

import org.litepal.LitePalApplication;

/**
 * @author xiemy
 * @date 2017/4/13.
 */
public class FApplication extends LitePalApplication {

    private static FApplication dApplication;
    private ActivityHelper mActivityHelper;

    public FApplication() {
        FApplication.dApplication = this;

        mActivityHelper = new ActivityHelper();
        registerActivityLifecycleCallbacks(mActivityHelper);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static FApplication getFApplication() {
        return dApplication;
    }

    public static ActivityHelper getActivityHelper() {
        return dApplication.mActivityHelper;
    }
}
