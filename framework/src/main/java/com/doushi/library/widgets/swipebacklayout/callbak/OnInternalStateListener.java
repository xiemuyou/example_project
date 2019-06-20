package com.doushi.library.widgets.swipebacklayout.callbak;

import androidx.annotation.FloatRange;

import com.doushi.library.widgets.swipebacklayout.widget.SlideBackLayout;

/**
 * Created by Oubowu on 2016/9/22 0022 18:22.
 */
public interface OnInternalStateListener {

    void onSlide(@FloatRange(from = 0.0,
            to = 1.0) float percent);

    void onOpen();

    void onClose(Boolean finishActivity);

    void onCheckPreActivity(SlideBackLayout slideBackLayout);

}
