package com.library.widgets.emptyview;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author zhangw
 * @date 2017/7/30
 * Email: zhangwei@kingnet.com
 * Description:
 */
public abstract class OtherHolder {

    public View mLoadingView;
    public View mEmptyView;
    public ImageView mIvEmpty;
    public TextView mTvEmpty;
    public TextView mTvEmptyRetry;

    public OtherHolder(Context context) {
        init(context);
    }

    private void init(Context context) {
        mEmptyView = setEmpty(context);
        mLoadingView = setLoading(context);
        mIvEmpty = setIvEmpty(context);
        mTvEmpty = setTvEmpty(context);
        mTvEmptyRetry = setTvEmptyRetry(context);
    }

    public abstract View setEmpty(Context context);

    public abstract View setLoading(Context context);

    public abstract ImageView setIvEmpty(Context context);

    public abstract TextView setTvEmpty(Context context);

    public abstract TextView setTvEmptyRetry(Context context);

    /**
     * 开始/停止加载动画
     *
     * @param showLoading 开始/停止
     */
    public abstract void setLoadingAnim(boolean showLoading);
}
