package com.doushi.test.myproject.ui.main.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.doushi.library.util.ImageLoadUtils;
import com.doushi.library.widgets.pullzoom.PullToZoomScrollViewEx;
import com.doushi.test.myproject.R;
import com.doushi.test.myproject.base.component.BaseFragment;
import com.doushi.test.myproject.global.DefaultValue;
import com.doushi.test.myproject.global.ParamConstants;
import com.doushi.test.myproject.ui.main.mine.adapter.MineMenuData;
import com.doushi.test.myproject.ui.main.mine.adapter.MineMenuAdapter;
import com.doushi.test.myproject.ui.web.CommonWebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author xiemy
 * @date 2018/3/16.
 */
public class MineFragment extends BaseFragment {

    public static final int MAIN_INDEX = 2;

    @BindView(R.id.rlMineHead)
    RelativeLayout rlMineHead;
    @BindView(R.id.tvMineName)
    TextView tvMineName;
    @BindView(R.id.mPullToZoomScrollViewEx)
    PullToZoomScrollViewEx mPullToZoomScrollViewEx;

    private ImageView ivZoomHead, ivMineUserAvatar;
    private TextView tvMineUserName;
    RecyclerView rvContentView;

    private final int radius = 13;
    private final int sampling = 9;

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
        rlMineHead.setAlpha(0f);
        View headView = LayoutInflater.from(_mActivity).inflate(R.layout.view_mine_head, mPullToZoomScrollViewEx, false);
        View zoomView = LayoutInflater.from(_mActivity).inflate(R.layout.view_mine_zoom, mPullToZoomScrollViewEx, false);

        rvContentView = new RecyclerView(_mActivity);
        ViewGroup.LayoutParams vglp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rvContentView.setLayoutParams(vglp);
        if (rvContentView.getItemAnimator() != null) {
            ((DefaultItemAnimator) rvContentView.getItemAnimator()).setSupportsChangeAnimations(true);
        }

        tvMineUserName = headView.findViewById(R.id.tvMineUserName);
        ivMineUserAvatar = headView.findViewById(R.id.ivMineUserAvatar);
        ivZoomHead = zoomView.findViewById(R.id.ivZoomHead);

        mPullToZoomScrollViewEx.setHeaderView(headView);
        mPullToZoomScrollViewEx.setZoomView(zoomView);
        mPullToZoomScrollViewEx.setScrollContentView(rvContentView);
        mPullToZoomScrollViewEx.setHeaderLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(225F)));
        mPullToZoomScrollViewEx.getPullRootView().setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, sx, sy, osx, osy) -> {
            if (sy < SizeUtils.dp2px(100F)) {
                float alpha = ((float) sy) / SizeUtils.dp2px(100F);
                rlMineHead.setAlpha(alpha);
            } else {
                rlMineHead.setAlpha(1f);
            }
        });
        setMineData();
    }

    private void setMineData() {
        ivMineUserAvatar.setImageResource(DefaultValue.MINE_HEAD_BLUR);
        //设置高斯模糊背景
        new ImageLoadUtils(_mActivity).commonBlurImage(DefaultValue.HEAD_BLUR, ivZoomHead, radius, sampling, DefaultValue.HEAD_BLUR);
        tvMineUserName.setText(R.string.no_login);
        tvMineName.setText(R.string.mine);

        MineMenuAdapter menuAdapter = new MineMenuAdapter();
        List<MineMenuData> menuList = new ArrayList<>(15);
        for (int i = 0; i < 15; i++) {
            menuList.add(new MineMenuData("跳转" + i));
        }
        rvContentView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        menuAdapter.setNewData(menuList);
        menuAdapter.bindToRecyclerView(rvContentView);
        menuAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString(ParamConstants.WEB_URL, "https://www.baidu.com");
            toPage(CommonWebActivity.class, bundle);
        });
    }
}
