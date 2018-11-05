package com.doushi.test.myproject.ui.main.home;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.doushi.test.myproject.R;
import com.doushi.test.myproject.base.component.BaseFragment;
import com.doushi.test.myproject.base.component.BaseRefreshFragment;
import com.doushi.test.myproject.ui.main.home.child.FirstFragment;
import com.doushi.test.myproject.ui.refresh.FollowFragment;
import com.doushi.test.myproject.widgets.tab.PagerFragmentItem;
import com.doushi.test.myproject.widgets.tab.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;

/**
 * @author xiemy
 * @date 2018/3/16.
 */
public class HomeFragment extends BaseFragment {

    public static final int MAIN_INDEX = 0;

    @BindView(R.id.tlHomeHF)
    SmartTabLayout tlHomeHF;
    @BindView(R.id.ablHomePage)
    AppBarLayout ablHomePage;
    @BindView(R.id.viewpager)
    ViewPager vpHomePage;

    private int showIndex = 0;
    private FragmentPagerItemAdapter fAdapter;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public void initEnv() {
        initTabLayout();
    }

    private void initTabLayout() {
        FragmentPagerItems.Creator creator = FragmentPagerItems.with(_mActivity);
        FragmentPagerItems fragmentPagerItems = creator.create();
        fragmentPagerItems.add(PagerFragmentItem.of("FirstFragment", FirstFragment.class));
        fragmentPagerItems.add(PagerFragmentItem.of("Fragment2", FollowFragment.class));
        fragmentPagerItems.add(PagerFragmentItem.of("FollowFragment", FollowFragment.class));
        fragmentPagerItems.add(PagerFragmentItem.of("Fragment4", FollowFragment.class));
        fragmentPagerItems.add(PagerFragmentItem.of("Fragment5", FollowFragment.class));
        fragmentPagerItems.add(PagerFragmentItem.of("Fragment6", FollowFragment.class));
        fragmentPagerItems.add(PagerFragmentItem.of("Fragment7", FollowFragment.class));
        fAdapter = new FragmentPagerItemAdapter(getChildFragmentManager(), fragmentPagerItems);
        vpHomePage.setAdapter(fAdapter);
        vpHomePage.setOffscreenPageLimit(7);
        tlHomeHF.setViewPager(vpHomePage);
        vpHomePage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        if (fAdapter == null) {
            return;
        }
        Fragment fragment = fAdapter.getPage(showIndex);
        if (fragment != null && fragment instanceof BaseRefreshFragment) {
            ((BaseRefreshFragment) fragment).placedTopAutoRefresh();
        }
    }
}


