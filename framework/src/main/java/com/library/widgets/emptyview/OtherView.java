package com.library.widgets.emptyview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.library.R;

/**
 * @author zhangw
 * @date 2017/7/28.
 * email: zhangwei@kingnet.com
 * 自定义空白页面
 * <p>
 * <p>
 * ##1.初始化 OtherView
 * ##2.初始化viewholder
 * ## OtherViewHolder otherHolder = new OtherViewHolder(mActivity);
 * ##3.需要设置按钮点击重试的设置监听回调事件
 * ##otherHolder.setOnEmptyRetryListener(new OtherViewHolder.EmptyRetryListener() {
 * <p>
 * ## @Override public void onEmptyRetryListener() {
 * ##todo 重试事件
 * ##}
 * ##});
 * ##4.设置
 * ##mOvHint.setHolder(otherHolder);
 */
public class OtherView extends LinearLayout {

    private final int KEY_NORMAL = 0;
    private final int KEY_LOADING = 1;
    private final int KEY_EMPTY = 2;
    private final int KEY_RETRY = 3;
    private int mViewType = KEY_NORMAL;
    private OtherHolder mHolder;

    public OtherView(Context context) {
        super(context);
    }

    public OtherView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置holder，可根据holder定制
     *
     * @param holder
     */
    public void setHolder(OtherHolder holder) {
        mHolder = holder;
        initView();
    }

    public boolean hasHolder() {
        return mHolder != null;
    }

    private void initView() {
        addView(mHolder.mLoadingView, 0);
        addView(mHolder.mEmptyView, 0);
        setShowOrHide();
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mHolder.mLoadingView.setLayoutParams(ll);
        mHolder.mEmptyView.setLayoutParams(ll);
    }

    /**
     * 显示加载loading
     */
    public void showLoadingView() {
        if (mViewType == KEY_LOADING) {
            return;
        }
        mViewType = KEY_LOADING;
        setShowOrHide();
    }

    /**
     * 显示空白页面
     */
    public void showEmptyView() {
        showEmptyView(EmptyEnum.UniteEmpty);
    }

    /**
     * 自定义显示空页面
     *
     * @param emptyType 枚举类型 可自定义设置空白资源图片与空白提示
     */
    public void showEmptyView(EmptyEnum emptyType) {
        if (mViewType == KEY_EMPTY) {
            return;
        }
        mViewType = KEY_EMPTY;
        setShowOrHide(emptyType);
    }

    /**
     * 显示正常内容，即隐藏其他不相关view
     */
    public void showContentView() {
        if (mViewType == KEY_NORMAL) {
            return;
        }
        mViewType = KEY_NORMAL;
        setShowOrHide();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mViewType != KEY_NORMAL;
    }

    /**
     * 根据状态隐藏view
     */
    private void setShowOrHide() {
        setShowOrHide(null);
    }

    /**
     * 根据状态隐藏view 设置空白
     *
     * @param emptyType 空白显示类型
     */
    private void setShowOrHide(EmptyEnum emptyType) {
        if (mHolder == null) {
            throw new RuntimeException("OtherView::请先设置OtherHolder");
        }
        if (emptyType != null) {
            mHolder.mIvEmpty.setImageResource(emptyType.getRes() == 0 ? R.drawable.other_unite_empty : emptyType.getRes());
            mHolder.mTvEmpty.setText(emptyType.getText() == 0 ? R.string.other_unite_empty : emptyType.getText());
            if (emptyType.getBtnText() == 0) {
                mHolder.mTvEmptyRetry.setVisibility(GONE);
            } else {
                mHolder.mTvEmptyRetry.setVisibility(VISIBLE);
                mHolder.mTvEmptyRetry.setText(emptyType.getBtnText());
            }
        }
        mHolder.mLoadingView.setVisibility(mViewType == KEY_LOADING ? VISIBLE : GONE);
        mHolder.setLoadingAnim(mViewType == KEY_LOADING);
        mHolder.mEmptyView.setVisibility(mViewType == KEY_EMPTY ? VISIBLE : GONE);
    }
}
