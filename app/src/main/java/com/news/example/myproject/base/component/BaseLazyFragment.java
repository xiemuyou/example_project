package com.news.example.myproject.base.component;

import android.os.Bundle;
import androidx.annotation.Nullable;

/**
 * 懒加载 Fragment基类
 *
 * @author xiemy
 * @date 2018/2/28
 */
public abstract class BaseLazyFragment extends BaseFragment {

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initLazyEnv();
    }

    @Override
    public void initEnv() {
    }

    /**
     * 懒加载,加载数据
     */
    public abstract void initLazyEnv();
}
