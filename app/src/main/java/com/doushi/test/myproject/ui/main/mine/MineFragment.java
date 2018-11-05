package com.doushi.test.myproject.ui.main.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doushi.test.myproject.R;
import com.doushi.test.myproject.base.component.BaseFragment;
import com.doushi.test.myproject.global.ParamConstants;
import com.doushi.test.myproject.ui.main.home.HomeFragment;
import com.doushi.test.myproject.ui.main.video.VideoFragment;
import com.doushi.test.myproject.ui.web.CommonWebActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xiemy
 * @date 2018/3/16.
 */
public class MineFragment extends BaseFragment {

    public static final int MAIN_INDEX = 2;

    @BindView(R.id.tvMineHelp)
    TextView tvMineHelp;

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.fragment_mine;
    }

    @Override
    public void initEnv() {

    }

    @OnClick({R.id.tvMineHelp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvMineHelp:
                Bundle bundle = new Bundle();
                bundle.putString(ParamConstants.WEB_URL, "http://www.3tong.com/app/help/help_pupil.html");
                toPage(CommonWebActivity.class, bundle);
                break;

            default:
                break;
        }
    }
}
