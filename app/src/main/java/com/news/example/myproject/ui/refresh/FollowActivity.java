package com.news.example.myproject.ui.refresh;


import android.content.Intent;
import android.os.Bundle;

import com.news.example.myproject.R;
import com.news.example.myproject.base.component.BaseActivity;

/**
 * 测试
 *
 * @author ciemy
 * @date 2018/2/29
 */
public class FollowActivity extends BaseActivity {

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_fragment;
    }

    @Override
    public void initEnv() {
        Intent intent = getIntent();
        int uid = intent.getIntExtra("100", -1);
        loadRootFragment(R.id.flContainer, FollowFragment.newInstance(uid));
    }
}
