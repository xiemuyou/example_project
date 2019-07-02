package com.news.example.myproject.ui.main.home

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.viewpager.widget.ViewPager
import com.blankj.utilcode.util.ObjectUtils
import com.library.thread.AbstractSafeThread
import com.library.thread.ThreadPool
import com.library.util.ViewUtil
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseFragment
import com.news.example.myproject.base.component.BaseRefreshFragment
import com.news.example.myproject.model.sort.NewsSortInfo
import com.news.example.myproject.model.sort.SortInfoData
import com.news.example.myproject.ui.main.home.sort.SortEditFragment
import com.news.example.myproject.ui.main.home.sort.SortFragment
import com.news.example.myproject.ui.main.home.sort.sp.SortPresenter
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
    private var fpItems: FragmentPagerItems? = null
    private var mSortData: SortInfoData? = null
    private var sortInfoList: MutableList<NewsSortInfo> = ArrayList()

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

    private val sortPresenter by lazy {
        SortPresenter()
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.fragment_home
    }

    override fun initEnv() {
        sortPresenter.recommend = getString(R.string.recommend)
        homePresenter.getCategoryExtra()
        homePresenter.articleHotWords()
        fpItems = FragmentPagerItems.with(_mActivity).create()
        setOnClickListener()
    }

    private fun setOnClickListener() {
        vbvHomeSearch?.setOnClickListener {
            SearchActivity.showClass(ArrayList(), "")
        }
        ivSortCustom?.setOnClickListener {
            SortEditFragment.Builder()
                    .setFragmentManager(_mActivity.supportFragmentManager)
                    .setSortData(this, sortListCallback, sortInfoList[showIndex], mSortData)
                    .create().showDialog()
        }
    }

    private val sortListCallback = SortEditFragment.EditSortListCallback { isChange, showIndex, sortData ->
        if (isChange) {
            editHomeSortList(sortData)
            tlHomeHF.setViewPager(viewpager)
        }
        val index = showIndex
        viewpager.postDelayed({
            viewpager.currentItem = index
        }, 400)

        ThreadPool.execute(object : AbstractSafeThread() {
            override fun deal() {
                SortPresenter().saveSortData(sortData)
            }
        })
    }

    /**
     * 编辑用户编辑好的数据更新首页分类列表
     *
     * @param sortData 用户编辑好的数据集合
     */
    private fun editHomeSortList(sortData: SortInfoData?) {
        if (sortData != null) {
            mSortData?.clearAllList()
            mSortData?.fixedList = sortData.fixedList
            mSortData?.chooseList = sortData.chooseList
            mSortData?.editList = sortData.editList
            sortInfoList.clear()
            sortInfoList.addAll(sortData.fixedList)
            sortInfoList.addAll(sortData.chooseList)
            getFragmentItems(sortInfoList)
            fAdapter?.notifyDataSetChanged()
        }
    }

    private fun initTabLayout(res: SortInfoData?) {
        ViewUtil.setMargins(clHomeRootView, 0, CommonUtil.getStatusBarHeight(_mActivity), 0, 0)
        //循环添加分类页
        res?.getAllList()?.forEach {
            when {
                sortPresenter.recommend == it.name -> {
                    fpItems?.add(PagerFragmentItem.of(it.name, RecommendFragment::class.java))
                    sortInfoList.add(it)
                }
                it.itemType == NewsSortInfo.FIXED || it.itemType == NewsSortInfo.CHOOSE -> {
                    sortInfoList.add(it)
                    val arts = Bundle()
                    arts.putString(SortFragment.SORT_NAME, it.category)
                    fpItems?.add(PagerFragmentItem.of(it.name, SortFragment::class.java, arts))
                }

                else -> {
                }
            }
        }
        fAdapter = FragmentPagerItemAdapter(childFragmentManager, fpItems)
        viewpager.adapter = fAdapter!!
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

    private fun getFragmentItems(@NonNull sortList: List<NewsSortInfo>) {
        if (ObjectUtils.isNotEmpty(sortList) && null != fAdapter) {
            fpItems?.clear()
            fAdapter?.notifyDataSetChanged()
        }
        for (sort in sortList) {
            if (ObjectUtils.equals(sortPresenter.recommend, sort.name)) {
                fpItems?.add(PagerFragmentItem.of(sort.name, RecommendFragment::class.java))
            } else if (sort.itemType == NewsSortInfo.FIXED || sort.itemType == NewsSortInfo.CHOOSE) {
                val arts = Bundle()
                arts.putString(SortFragment.SORT_NAME, sort.category)
                fpItems?.add(PagerFragmentItem.of(sort.name, SortFragment::class.java, arts))
            }
        }
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

    override fun getCategoryExtraSuccess(res: MutableList<NewsSortInfo>?) {
        mSortData = sortPresenter.compareList(res)
        initTabLayout(mSortData)
    }

    override fun loadDataFail(apiTag: InterfaceConfig.HttpHelperTag?, errorInfo: String?) {
        showNotice(errorInfo)
    }
}


