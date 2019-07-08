package com.news.example.myproject.widgets.scrollview;


import androidx.core.widget.NestedScrollView;

/**
 * @author zhangw
 * @date 2018/5/21.
 */
public interface OnScrollListener {

    int SCROLL_STATE_IDLE = 0;

    int SCROLL_STATE_TOUCH_SCROLL = 1;

    int SCROLL_STATE_FLING = 2;

    void onScrollStateChanged(NestedScrollView view, int scrollState);

    void onScroll(NestedScrollView view, boolean isTouchScroll, int l, int t, int oldl, int oldt);
}
