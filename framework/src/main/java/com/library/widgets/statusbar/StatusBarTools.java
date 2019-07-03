package com.library.widgets.statusbar;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * @author 黄浩杭
 * @version 2017-11-21 0.7
 * @since 2017-11-21 0.7
 */
public class StatusBarTools {

    /**
     * Return the height of the status bar
     *
     * @param fragment The fragment
     * @return Height of the status bar
     */
    public static int getStatusBarHeight(@NonNull Fragment fragment) {
        return getStatusBarHeight(fragment.getResources());
    }

    /**
     * Return the height of the status bar
     *
     * @param context The context
     * @return Height of the status bar
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        return getStatusBarHeight(context.getResources());
    }

    /**
     * Return the height of the status bar
     *
     * @param res The res
     * @return Height of the status bar
     */
    private static int getStatusBarHeight(@NonNull Resources res) {
        int statusBarHeight = 0;
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
