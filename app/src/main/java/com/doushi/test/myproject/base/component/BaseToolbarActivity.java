package com.doushi.test.myproject.base.component;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doushi.test.myproject.R;

import java.lang.reflect.Field;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * email: zhangwei@kingnet.com
 * 带toolbar的activity
 *
 * @author zhangw
 * @date 2017/8/14.
 */
public abstract class BaseToolbarActivity extends SupportActivity {

    /**
     * 头部Toolbar控件
     */
    private Toolbar tbHead;
    /**
     * 标题
     */
    private TextView tvHeadTitle;
    private TextView tvHeadRight;
    private View tbDiver;

    private RelativeLayout rlHeadRight;
    private ImageView ivHeadRight;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_toolbar);
    }

    /**
     * 初始化头部Toolbar控件
     */
    private void initToolbar() {
        tbHead = findViewById(R.id.toolbar);
        tvHeadTitle = tbHead.findViewById(R.id.tvHeadTitle);
        tvHeadRight = tbHead.findViewById(R.id.tvHeadRight);
        rlHeadRight = tbHead.findViewById(R.id.rlHeadRight);
        ivHeadRight = tbHead.findViewById(R.id.ivHeadRight);
        tbDiver = findViewById(R.id.tbDiver);
        tbHead.setTitle("");
        if (tbHead != null) {
            setSupportActionBar(tbHead);
            tbHead.setNavigationIcon(R.drawable.back_black);
            tbHead.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backPressed();
                }
            });
        }
    }

    public Toolbar getTbHead() {
        return tbHead;
    }

    /**
     * 通过反射获取titleTextView来设置监听
     */
    public TextView getToolbarTitle() {
        try {
            //获取成员变量
            Field f = tbHead.getClass().getDeclaredField("mTitleTextView");
            //设置可访问
            f.setAccessible(true);
            //获取到mSubtitleTextView的实例
            //这里使用final是为了方便下面在匿名内部类里使用
            //传入的是toolbar实例
            final TextView tv = (TextView) f.get(tbHead);
            return tv;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 显示头部
     *
     * @param tbFlag    是否顯头部标题
     * @param diverFlag 是否顯示顶部线
     */
    public void showHead(boolean tbFlag, boolean diverFlag) {
        tbHead.setVisibility(tbFlag ? View.VISIBLE : View.GONE);
        tbDiver.setVisibility(diverFlag ? View.VISIBLE : View.GONE);
    }

    public Toolbar getToolbar() {
        return tbHead;
    }

    public TextView getTvHeadRight() {
        rlHeadRight.setVisibility(View.VISIBLE);
        tvHeadRight.setVisibility(View.VISIBLE);
        return tvHeadRight;
    }

    public ImageView getIvHeadRight() {
        ivHeadRight.setVisibility(View.VISIBLE);
        return ivHeadRight;
    }

    public RelativeLayout getLayoutRight() {
        rlHeadRight.setVisibility(View.VISIBLE);
        return rlHeadRight;
    }

    public void setHeadTitle(@StringRes int titleStr) {
        setHeadTitle(0, getString(titleStr));
    }

    /**
     * 设置头部背景颜色,头部标题
     *
     * @param headColor 背景颜色
     * @param titleStr  头部标题
     */
    public void setHeadTitle(@ColorRes int headColor, String titleStr) {
        if (headColor > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tbHead.setBackgroundColor(getResources().getColor(headColor, null));
            } else {
                tbHead.setBackgroundColor(getResources().getColor(headColor));
            }
        }
        tvHeadTitle.setText(titleStr);
    }

    /**
     * 获取手机屏幕状态栏高度
     *
     * @return 状态栏高度
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 关闭页面
     */
    public void backPressed() {
        //finish();
        super.onBackPressedSupport();
    }

    @Override
    public void setContentView(int layoutId) {
        setContentView(View.inflate(this, layoutId, null));
    }

    @Override
    public void setContentView(View view) {
        LinearLayout llRootView = findViewById(R.id.llRootView);
        if (llRootView == null) {
            return;
        }
        llRootView.addView(view,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initToolbar();
    }
}
