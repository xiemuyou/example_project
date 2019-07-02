package com.news.example.myproject.ui.main

import android.os.Bundle
import android.widget.Toast
import com.blankj.utilcode.util.ObjectUtils
import com.library.widgets.statusbar.StatusBarCompat
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseActivity
import com.news.example.myproject.ui.main.home.HomeFragment
import com.news.example.myproject.ui.main.mine.MineFragment
import com.news.example.myproject.ui.main.video.VideoFragment
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.SupportFragment

/**
 * 主页面
 *
 * @author xiemy
 * @date 2018/3/1.
 */
class MainActivity : BaseActivity() {

    private val mFragments = arrayOfNulls<SupportFragment>(3)

    private var showTabIndex = HomeFragment.MAIN_INDEX

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        setStatusTransparent()
        return R.layout.activity_main
    }

    override fun initEnv() {
        // 状态栏颜色修改
        StatusBarCompat.setLightStatusBar(window, true)
        val firstFragment = findFragment(HomeFragment::class.java)
        if (firstFragment == null) {
            mFragments[HomeFragment.MAIN_INDEX] = HomeFragment.newInstance()
            mFragments[VideoFragment.MAIN_INDEX] = VideoFragment.newInstance()
            mFragments[MineFragment.MAIN_INDEX] = MineFragment.newInstance()
            loadMultipleRootFragment(R.id.flHomContainer,
                    showTabIndex,
                    mFragments[HomeFragment.MAIN_INDEX],
                    mFragments[VideoFragment.MAIN_INDEX],
                    mFragments[MineFragment.MAIN_INDEX])
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[HomeFragment.MAIN_INDEX] = firstFragment
            mFragments[VideoFragment.MAIN_INDEX] = findFragment(VideoFragment::class.java)
            mFragments[MineFragment.MAIN_INDEX] = findFragment(MineFragment::class.java)
        }
        switchTabView(showTabIndex)
        setTabClickListener()
    }

    private fun setTabClickListener() {
        ivMainHome.setOnClickListener {
            switchTabView(HomeFragment.MAIN_INDEX)
        }
        ivMainVideo.setOnClickListener {
            switchTabView(VideoFragment.MAIN_INDEX)
        }
        ivMainMine.setOnClickListener {
            switchTabView(MineFragment.MAIN_INDEX)
        }
    }

    private fun switchTabView(index: Int) {
        //显示为首页,再次点击首页刷新数据
        val isRefreshData = index == showTabIndex
        if (!isRefreshData) {
            refreshHomeTabView()
        }
        when (index) {
            HomeFragment.MAIN_INDEX -> {
                ivMainHome.setImageResource(R.drawable.home_select)
                if (isRefreshData) {
                    val homeFragment = findFragment(HomeFragment::class.java)
                    if (ObjectUtils.isNotEmpty(homeFragment)) {
                        homeFragment.refreshData()
                    }
                } else {
                    showHideFragment(mFragments[HomeFragment.MAIN_INDEX], mFragments[showTabIndex])
                }
            }

            VideoFragment.MAIN_INDEX -> {
                ivMainVideo.setImageResource(R.drawable.video_select)
                if (isRefreshData) {
                    val videoFragment = findFragment(VideoFragment::class.java)
                    if (ObjectUtils.isNotEmpty(videoFragment)) {
                        videoFragment.refreshData()
                    }
                } else {
                    showHideFragment(mFragments[VideoFragment.MAIN_INDEX], mFragments[showTabIndex])
                }
            }

            MineFragment.MAIN_INDEX -> if (!isRefreshData) {
                ivMainMine.setImageResource(R.drawable.mine_select)
                showHideFragment(mFragments[MineFragment.MAIN_INDEX], mFragments[showTabIndex])
            }

            else -> {
            }
        }
        showTabIndex = index
    }

    private fun refreshHomeTabView() {
        ivMainHome.setImageResource(R.drawable.home_unselect)
        ivMainVideo.setImageResource(R.drawable.video_unselect)
        ivMainMine.setImageResource(R.drawable.mine_unselect)
    }

    private var exitTime: Long = 0

    override fun onBackPressedSupport() {
        if (showTabIndex != HomeFragment.MAIN_INDEX) {
            switchTabView(HomeFragment.MAIN_INDEX)
            return
        }
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, R.string.again_accord_exit, Toast.LENGTH_SHORT).show()
            exitTime = System.currentTimeMillis()
            return
        } else {
            moveTaskToBack(true)
        }
        super.onBackPressedSupport()
    }
}
