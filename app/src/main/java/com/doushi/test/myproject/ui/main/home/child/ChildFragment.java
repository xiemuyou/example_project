package com.doushi.test.myproject.ui.main.home.child;

import android.os.Bundle;
import android.widget.TextView;

import com.doushi.test.myproject.R;
import com.doushi.test.myproject.base.component.BaseFragment;

import butterknife.BindView;

/**
 * @author xiemy
 * @date 2018/3/19.
 */
public class ChildFragment extends BaseFragment {

    @BindView(R.id.tvInfo)
    TextView tvInfo;

    private static final String INFO = "info";

    public static ChildFragment newInstance(String info) {
        Bundle args = new Bundle();
        ChildFragment fragment = new ChildFragment();
        args.putString(INFO, info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.fragment_child;
    }

    @Override
    public void initEnv() {
        if (getArguments() != null) {
            tvInfo.setText(getArguments().getString(INFO));
        }
    }
}
