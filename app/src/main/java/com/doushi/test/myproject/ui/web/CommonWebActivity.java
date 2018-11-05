package com.doushi.test.myproject.ui.web;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.doushi.test.myproject.R;
import com.doushi.test.myproject.base.component.BaseActivity;
import com.doushi.test.myproject.global.Constants;
import com.doushi.test.myproject.global.ParamConstants;

import butterknife.BindView;

/**
 * 默认 WebView Activity
 *
 * @author xiemy
 * @date 2018/3/6
 */
public class CommonWebActivity extends BaseActivity {

    protected FragmentManager fragmentManager;

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_fragment;
    }

    @Override
    public void initEnv() {
        //网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置：Activity在onCreate时需要设置:
        //（这个对宿主没什么影响，建议声明）
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        if (null == intent) {
            this.finish();
            return;
        }
        showHead(true, true);
        setHeadTitle(R.string.help);
        loadRootFragment(R.id.flContainer, CommonWebFragment.newInstance(intent.getStringExtra(ParamConstants.WEB_URL)));
    }


    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
        closePage();
    }

    @Override
    public void backPressed() {
        CommonWebFragment commonWebFragment = (CommonWebFragment) fragmentManager.findFragmentById(R.id.flContainer);
        if (null == commonWebFragment || null == commonWebFragment.webView) {
            super.backPressed();
            closePage();
            return;
        }
        if (commonWebFragment.webView.canGoBack()) {
            commonWebFragment.webView.goBack();
        } else {
            super.backPressed();
            closePage();
        }
    }
}
