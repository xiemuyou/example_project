package com.news.example.myproject.ui.main.home

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.library.util.ViewUtil
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseFragment
import com.news.example.myproject.base.component.BaseRefreshFragment
import com.news.example.myproject.model.sort.NewsSortListResponse
import com.news.example.myproject.ui.main.home.sort.SortFragment
import com.news.example.myproject.ui.main.mp.MainPresenter
import com.news.example.myproject.ui.main.mv.MainView
import com.news.example.myproject.ui.main.recommend.RecommendFragment
import com.news.example.myproject.ui.main.recommend.adapter.SearchBannerAdapter
import com.news.example.myproject.ui.search.SearchActivity
import com.news.example.myproject.widgets.tab.PagerFragmentItem
import com.news.example.myproject.znet.InterfaceConfig
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author xiemy
 * @date 2018/3/16.
 */
class HomeFragment : BaseFragment(), MainView {

    private var showIndex = 0
    private var fAdapter: FragmentPagerItemAdapter? = null
    private var bannerAdapter: SearchBannerAdapter? = null
    private var hotSearchList: MutableList<String>? = null

    companion object {
        const val MAIN_INDEX = 0
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val homePresenter by lazy {
        MainPresenter(this)
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.fragment_home
    }

    override fun initEnv() {
        homePresenter.getCategoryExtra()
        homePresenter.articleHotWords()
        vbvHomeSearch?.setOnClickListener {
            SearchActivity.showClass(ArrayList(), "")
        }
    }

    private fun initTabLayout(res: NewsSortListResponse) {
        ViewUtil.setMargins(clHomeRootView, 0, CommonUtil.getStatusBarHeight(_mActivity), 0, 0)
        val creator = FragmentPagerItems.with(_mActivity)
        val fragmentPagerItems = creator.create()
        val sortList = res.data
        //手动添加推荐页
        val recommend = getString(R.string.recommend)
        fragmentPagerItems.add(PagerFragmentItem.of(recommend, RecommendFragment::class.java))
        //循环添加分类页
        if (sortList != null && sortList.isNotEmpty()) {
            sortList.forEach {
                val arts = Bundle()
                arts.putString(RecommendFragment.SORT_NAME, it.category)
                fragmentPagerItems.add(PagerFragmentItem.of(it.name, SortFragment::class.java, arts))
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
        val fragment = fAdapter?.getPage(showIndex)
        if (fragment is BaseRefreshFragment<*>) {
            fragment.placedTopAutoRefresh()
        }
        checkHotSearchList()
    }

    fun checkHotSearchList() {
        if (hotSearchList == null) {
            homePresenter.articleHotWords()
        }
    }

    override fun getSortListSuccess(hotList: MutableList<String>?) {
        if (hotList != null && hotList.isNotEmpty()) {
            hotSearchList = hotList
            vbvHomeSearch?.setOnClickListener(null)
            if (bannerAdapter == null) {
                bannerAdapter = SearchBannerAdapter(hotList)
                vbvHomeSearch.setAdapter(bannerAdapter)
                vbvHomeSearch.start()
            } else {
                bannerAdapter?.setData(hotList)
            }
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


