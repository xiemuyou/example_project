package com.library.widgets.imagepreview.view;

import android.content.Context;

import com.library.R;


/**
 *         date 2017/11/22
 *         Deprecated:
 */

public class ImageUtils {
    /**
     * 取状态栏高度
     *
     * @param mApplicationContent mApplicationContent
     * @return int
     */
    public static int getStatusBarHeight(Context mApplicationContent) {
        int result = mApplicationContent.getResources().getDimensionPixelSize(R.dimen.px_48);
        int resourceId = mApplicationContent.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mApplicationContent.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static boolean isLongPressed(float lastX, float lastY, float thisX, float thisY, long lastDownTime, long thisEventTime,long longPressTime) {

        float offsetX = Math.abs(thisX - lastX);

        float offsetY = Math.abs(thisY - lastY);

        long intervalTime = thisEventTime - lastDownTime;

        if (offsetX <= 10 && offsetY <= 10 && intervalTime >= longPressTime) {

            return true;

        }

        return false;

    }
}
