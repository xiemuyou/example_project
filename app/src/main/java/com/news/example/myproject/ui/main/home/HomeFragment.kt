package com.news.example.myproject.ui.main.home

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.library.thread.AbstractSafeThread
import com.library.thread.ThreadPool
import com.library.util.ViewUtil
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseFragment
import com.news.example.myproject.base.component.BaseRefreshFragment
import com.news.example.myproject.model.sort.NewsSortInfo
import com.news.example.myproject.model.sort.NewsSortListResponse
import com.news.example.myproject.model.sort.SortFilter
import com.news.example.myproject.ui.main.home.sort.SortEditFragment
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

    private var mSortListRes: NewsSortListResponse? = null

    override fun initEnv() {
        recommend = getString(R.string.recommend)
        homePresenter.getCategoryExtra()
        homePresenter.articleHotWords()
        vbvHomeSearch?.setOnClickListener {
            SearchActivity.showClass(ArrayList(), "")
        }
        ivSortCustom?.setOnClickListener {
            editSortList()
        }
    }

    private fun editSortList() {
        val sortList: List<NewsSortInfo>? = mSortListRes?.data
        SortEditFragment.Builder()
                .setFragmentManager(_mActivity.supportFragmentManager)
                .setSortData(this, sortListCallback, if (sortList?.isEmpty() == true) null else sortList?.get(showIndex),
                        SortFilter.divideList(sortList))
                .create().showDialog()
    }

    private val sortListCallback = SortEditFragment.EditSortListCallback { _, _, _ ->
        //    private val sortListCallback = SortEditFragment.EditSortListCallback { isChange, showIndex, sortData ->
        //        if (isChange) {
//            editHomeSortList(sortData)
//            tlHomeHF.setViewPager(vpHomePage)
//            AppDanaClient.postEventClick(_mActivity, "A#sort_1", null, "", "", "")
//        }
//        val index = showIndex
//        vpHomePage.postDelayed(Runnable {
//            setTabSelectDefaultTag(index, 0)
//            setCurrentTab(index)
//        }, 400)

        ThreadPool.execute(object : AbstractSafeThread() {
            override fun deal() {
//                val json = JsonUtil.objectToJson(sortData)
//                if (UserOperationUtil.whetherLogin()) {
//                    val sortPresenter = SortPresenter(this@HomeFragment)
//                    sortPresenter.uploadSortData(json)
//                }
//                AppSharedPreferences.INSTANCE.saveString(AppSharedPreferences.SORT_MINE, json)
            }
        })
    }

    private fun initTabLayout(res: NewsSortListResponse?) {
        ViewUtil.setMargins(clHomeRootView, 0, CommonUtil.getStatusBarHeight(_mActivity), 0, 0)
        val creator = FragmentPagerItems.with(_mActivity)
        val fragmentPagerItems = creator.create()
        val sortList = res?.data
        val sortSize = sortList?.size ?: 0
        //循环添加分类页
        for (i in 0 until sortSize) {
            val it = sortList?.get(i)
            it?.apply {
                if (recommend == it.name) {
                    fragmentPagerItems.add(PagerFragmentItem.of(recommend, RecommendFragment::class.java))
                }
                if (i < 10) {
                    it.labelType = NewsSortInfo.CHOOSE
                    val arts = Bundle()
                    arts.putString(SortFragment.SORT_NAME, it.category)
                    fragmentPagerItems.add(PagerFragmentItem.of(it.name, SortFragment::class.java, arts))
                } else {
                    it.labelType = NewsSortInfo.MORE
                }
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
            tvSearchIcon.hint = ""
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

    //手动添加推荐页
    private var recommend = ""

    override fun getCategoryExtraSuccess(res: NewsSortListResponse?) {
        mSortListRes = res
        val sortList: MutableList<NewsSortInfo>? = mSortListRes?.data
        if (sortList != null) {
            val recommendSort = NewsSortInfo()
            recommendSort.name = recommend
            recommendSort.labelType = NewsSortInfo.FIXED
            sortList.add(0, recommendSort)
        }
        initTabLayout(res)
    }

    override fun loadDataFail(apiTag: InterfaceConfig.HttpHelperTag?, errorInfo: String?) {
        showNotice(errorInfo)
    }
}


