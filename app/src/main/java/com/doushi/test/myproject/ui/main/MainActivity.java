package com.doushi.test.myproject.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ObjectUtils;
import com.doushi.test.myproject.R;
import com.doushi.test.myproject.base.component.BaseActivity;
import com.doushi.test.myproject.ui.main.home.HomeFragment;
import com.doushi.test.myproject.ui.main.mine.MineFragment;
import com.doushi.test.myproject.ui.main.video.VideoFragment;

import butterknife.BindView;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 主页面
 *
 * @author xiemy
 * @date 2018/3/1.
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.ivMainHome)
    ImageView ivMainHome;
    @BindView(R.id.ivMainVideo)
    ImageView ivMainVideo;
    @BindView(R.id.ivMainMine)
    ImageView ivMainMine;

    private SupportFragment[] mFragments = new SupportFragment[3];

    private int showTabIndex = HomeFragment.MAIN_INDEX;

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initEnv() {
        SupportFragment firstFragment = findFragment(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[HomeFragment.MAIN_INDEX] = HomeFragment.newInstance();
            mFragments[VideoFragment.MAIN_INDEX] = VideoFragment.newInstance();
            mFragments[MineFragment.MAIN_INDEX] = MineFragment.newInstance();
            loadMultipleRootFragment(R.id.flHomContainer,
                    showTabIndex,
                    mFragments[HomeFragment.MAIN_INDEX],
                    mFragments[VideoFragment.MAIN_INDEX],
                    mFragments[MineFragment.MAIN_INDEX]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[HomeFragment.MAIN_INDEX] = firstFragment;
            mFragments[VideoFragment.MAIN_INDEX] = findFragment(VideoFragment.class);
            mFragments[MineFragment.MAIN_INDEX] = findFragment(MineFragment.class);
        }
        switchTabView(showTabIndex);
    }

    @OnClick({R.id.ivMainHome, R.id.ivMainVideo, R.id.ivMainMine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivMainHome:
                switchTabView(HomeFragment.MAIN_INDEX);
                break;

            case R.id.ivMainVideo:
                switchTabView(VideoFragment.MAIN_INDEX);
                break;

            case R.id.ivMainMine:
                switchTabView(MineFragment.MAIN_INDEX);
                break;

            default:
                break;
        }
    }

    public void switchTabView(int index) {
        //显示为首页,再次点击首页刷新数据
        boolean isRefreshData = index == showTabIndex;
        if (!isRefreshData) {
            refreshHomeTabView();
        }
        switch (index) {
            case HomeFragment.MAIN_INDEX:
                ivMainHome.setImageResource(R.drawable.home_select);
                if (isRefreshData) {
                    HomeFragment homeFragment = findFragment(HomeFragment.class);
                    if (ObjectUtils.isNotEmpty(homeFragment)) {
                        homeFragment.refreshData();
                    }
                } else {
                    showHideFragment(mFragments[HomeFragment.MAIN_INDEX], mFragments[showTabIndex]);
                }
                break;

            case VideoFragment.MAIN_INDEX:
                if (!isRefreshData) {
                    ivMainVideo.setImageResource(R.drawable.video_select);
                    showHideFragment(mFragments[VideoFragment.MAIN_INDEX], mFragments[showTabIndex]);
                }
                break;

            case MineFragment.MAIN_INDEX:
                if (!isRefreshData) {
                    ivMainMine.setImageResource(R.drawable.mine_select);
                    showHideFragment(mFragments[MineFragment.MAIN_INDEX], mFragments[showTabIndex]);
                }
                break;

            default:
                break;
        }
        showTabIndex = index;
    }

    private void refreshHomeTabView() {
        ivMainHome.setImageResource(R.drawable.home_unselect);
        ivMainVideo.setImageResource(R.drawable.video_unselect);
        ivMainMine.setImageResource(R.drawable.mine_unselect);
    }
}
