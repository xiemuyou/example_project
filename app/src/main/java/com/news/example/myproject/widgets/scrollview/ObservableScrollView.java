package com.news.example.myproject.widgets.scrollview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.library.widgets.fcnestedscrollview.FCNestedScrollView;

import java.lang.ref.WeakReference;

/**
 * 滑动scrollView
 * <p>
 * Created by livvy on 18-5-17.
 */

public class ObservableScrollView extends FCNestedScrollView {

    private static final int CHECK_SCROLL_STOP_DELAY_MILLIS = 80;
    private static final int MSG_SCROLL = 1;

    private boolean mIsTouched = false;
    private int mScrollState = OnScrollListener.SCROLL_STATE_IDLE;

    private OnScrollListener mOnScrollListener;

    private NoLeakHandler mHandler = new NoLeakHandler(this);


    private static class NoLeakHandler extends Handler {
        private WeakReference<ObservableScrollView> scrollViewWeakReference;
        private int mLastY = Integer.MIN_VALUE;

        NoLeakHandler(ObservableScrollView scrollView) {
            scrollViewWeakReference = new WeakReference<>(scrollView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_SCROLL) {
                final int scrollY = scrollViewWeakReference.get().getScrollY();
                if (!scrollViewWeakReference.get().mIsTouched && mLastY == scrollY) {
                    mLastY = Integer.MIN_VALUE;
                    scrollViewWeakReference.get().setScrollState(OnScrollListener.SCROLL_STATE_IDLE);
                } else {
                    mLastY = scrollY;
                    scrollViewWeakReference.get().restartCheckStopTiming();
                }
            }
        }
    }

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        mOnScrollListener = onScrollListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mIsTouched) {
            setScrollState(OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
        } else {
            setScrollState(OnScrollListener.SCROLL_STATE_FLING);
            restartCheckStopTiming();
        }
        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(this, mIsTouched, l, t, oldl, oldt);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        handleDownEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        handleUpEvent(ev);
        return super.onTouchEvent(ev);
    }

    private void handleUpEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsTouched = false;
                restartCheckStopTiming();
                break;

            default:
                break;
        }
    }

    private void restartCheckStopTiming() {
        if (mHandler != null) {
            mHandler.removeMessages(MSG_SCROLL);
            mHandler.sendEmptyMessageDelayed(MSG_SCROLL, CHECK_SCROLL_STOP_DELAY_MILLIS);
        }

    }

    /**
     * 防止内存泄漏
     */
    public void clear() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    private void handleDownEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mIsTouched = true;
        }
    }

    private void setScrollState(int state) {
        if (mScrollState != state) {
            mScrollState = state;
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrollStateChanged(this, state);
            }
        }
    }
}
