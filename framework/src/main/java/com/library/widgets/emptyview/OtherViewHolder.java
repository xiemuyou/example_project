package com.library.widgets.emptyview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.library.R;

/**
 * @author zhangw
 * @date 2017/7/30
 * Email: zhangwei@kingnet.com
 * Description:
 */
public class OtherViewHolder extends OtherHolder {
    /**
     *
     */
    private Context mContext;
    /**
     * 空白页面图片
     */
    public ImageView mIvEmpty;
    /**
     * 空白页面文字
     */
    public TextView mTvEmpty;

    /**
     * 空白页面重试
     */
    public TextView mTvEmptyRetry;

    private AnimationDrawable refreshAnim;

    /**
     * 空白页面点击重试
     */
    private EmptyRetryListener mEmptyRetryListener;

    public OtherViewHolder(Context context) {
        super(context);
        mContext = context;
    }

    public void setOnEmptyRetryListener(EmptyRetryListener emptyRetryListener) {
        this.mEmptyRetryListener = emptyRetryListener;
    }

    @Override
    public View setEmpty(Context context) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.base_empty, null);
        mIvEmpty = (ImageView) emptyView.findViewById(R.id.ivEmpty);
        mTvEmpty = (TextView) emptyView.findViewById(R.id.tvEmpty);
        mTvEmptyRetry = (TextView) emptyView.findViewById(R.id.tvEmptyRetry);
        mTvEmptyRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmptyRetryListener != null) {
                    mEmptyRetryListener.onEmptyRetryListener();
                }
            }
        });
        return emptyView;
    }


    @Override
    public View setLoading(Context context) {
        View loadingView = LayoutInflater.from(context).inflate(R.layout.base_loading, null);
        ImageView ivRefresh = (ImageView) loadingView.findViewById(R.id.ivRefresh);
        // 1. 设置动画
        try {
            ivRefresh.setBackgroundResource(R.drawable.refresh_anim_list);
        } catch (Error e) {
            e.printStackTrace();
        }
        // 2. 获取动画对象
        refreshAnim = (AnimationDrawable) ivRefresh.getBackground();
        return loadingView;
    }

    @Override
    public ImageView setIvEmpty(Context context) {
        return mIvEmpty;
    }

    @Override
    public TextView setTvEmpty(Context context) {
        return mTvEmpty;
    }

    @Override
    public TextView setTvEmptyRetry(Context context) {
        return mTvEmptyRetry;
    }

    /**
     * 空白页面加载重试监听
     */
    public interface EmptyRetryListener {
        void onEmptyRetryListener();
    }

    @Override
    public void setLoadingAnim(boolean showLoading) {
        if (showLoading) {
            refreshAnim.stop();
            // 3. 启动动画
            refreshAnim.start();
        } else {
            if (refreshAnim != null) {
                refreshAnim.stop();
            }
        }
    }
}
