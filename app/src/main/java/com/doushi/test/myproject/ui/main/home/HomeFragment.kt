package com.doushi.test.myproject.ui.main.home

import android.os.Bundle
import android.support.v4.view.ViewPager

import com.doushi.test.myproject.R
import com.doushi.test.myproject.base.component.BaseFragment
import com.doushi.test.myproject.base.component.BaseRefreshFragment
import com.doushi.test.myproject.model.sort.NewsSortListResponse
import com.doushi.test.myproject.ui.main.home.child.RecommendFragment
import com.doushi.test.myproject.ui.main.mp.MainPresenter
import com.doushi.test.myproject.ui.main.mv.MainView
import com.doushi.test.myproject.widgets.tab.PagerFragmentItem
import com.doushi.test.myproject.znet.InterfaceConfig
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems

import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author xiemy
 * @date 2018/3/16.
 */
class HomeFragment : BaseFragment(), MainView {

    private var showIndex = 0
    private var fAdapter: FragmentPagerItemAdapter? = null

    private val homePresenter by lazy {
        MainPresenter(this)
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.fragment_home
    }

    override fun initEnv() {
        homePresenter.getCategoryExtra()
    }

    private fun initTabLayout(res: NewsSortListResponse) {
        val creator = FragmentPagerItems.with(_mActivity)
        val fragmentPagerItems = creator.create()
        val sortList = res.data
        if (sortList != null && sortList.isNotEmpty()) {
            sortList.forEach {
                val arts = Bundle()
                arts.putString(RecommendFragment.SORT_NAME, it.category)
                fragmentPagerItems.add(PagerFragmentItem.of(it.name, RecommendFragment::class.java, arts))
            }
        }
        fAdapter = FragmentPagerItemAdapter(childFragmentManager, fragmentPagerItems)
        viewpager.adapter = fAdapter
        viewpager.offscreenPageLimit = 7
        tlHomeHF.setViewPager(viewpager)
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                showIndex = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    /**
     * 刷新数据
     */
    fun refreshData() {
        if (fAdapter == null) {
            return
        }
        val fragment = fAdapter!!.getPage(showIndex)
        if (fragment is BaseRefreshFragment<*>) {
            fragment.placedTopAutoRefresh()
        }
    }

    companion object {

        const val MAIN_INDEX = 0

        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getCategoryExtraSuccess(res: NewsSortListResponse?) {
        if (res != null) {
            initTabLayout(res)
        }
    }

    override fun loadDataFail(apiTag: InterfaceConfig.HttpHelperTag?, errorInfo: String?) {
        showNotice(errorInfo)
    }
}


