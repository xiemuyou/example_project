package com.doushi.test.myproject.ui.main.video;

import android.os.Bundle;

import com.doushi.test.myproject.R;
import com.doushi.test.myproject.base.component.BaseFragment;
import com.doushi.test.myproject.ui.main.home.HomeFragment;

/**
 * @author xiemy
 * @date 2018/3/16.
 */
public class VideoFragment extends BaseFragment{

    public static final int MAIN_INDEX = 1;

    public static VideoFragment newInstance() {
        Bundle args = new Bundle();
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.fragment_video;
    }

    @Override
    public void initEnv() {

    }
}
