package com.doushi.test.myproject.ui.main.home.child

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.doushi.library.util.ImageLoadUtils
import com.doushi.library.widgets.layoutManager.DividerItemDecoration
import com.doushi.test.myproject.R
import com.doushi.test.myproject.base.component.BaseRefreshFragment
import com.doushi.test.myproject.global.DefaultValue
import com.doushi.test.myproject.global.ParamConstants
import com.doushi.test.myproject.model.news.NewsInfo
import com.doushi.test.myproject.model.news.RecommendResponse
import com.doushi.test.myproject.model.search.SearchUserResponse
import com.doushi.test.myproject.model.sort.NewsSortListResponse
import com.doushi.test.myproject.model.video.VideoDetails
import com.doushi.test.myproject.ui.main.home.HomeFragment
import com.doushi.test.myproject.ui.refresh.RefreshListActivity
import com.doushi.test.myproject.ui.refresh.rp.RefreshPresenter
import com.doushi.test.myproject.ui.refresh.rv.RefreshListView
import com.doushi.test.myproject.znet.InterfaceConfig

/**
 * @author xiemy
 * @date 2018/3/19.
 */
class FirstFragment : BaseRefreshFragment<NewsInfo>(), RefreshListView {
    var category = ""

    companion object {
        const val SORT_NAME = "sortName"
    }

    private val followPresenter by lazy {
        RefreshPresenter(this)
    }

    override fun initEnv() {
        super.initEnv()
        category = arguments?.getString(SORT_NAME) ?: ""
    }

    override fun getRefreshAdapter(dataList: List<NewsInfo>): RecyclerView.Adapter<*> {
        val llm = LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false)
        canContentView.layoutManager = llm
        val decoration = DividerItemDecoration(_mActivity, 5, true,
                ContextCompat.getColor(_mActivity, R.color.default_toast_bg))
        canContentView.addItemDecoration(decoration)
        val adapter = object : BaseQuickAdapter<NewsInfo, BaseViewHolder>(R.layout.item_search_user, dataList) {
            override fun convert(helper: BaseViewHolder, item: NewsInfo) {
                helper.setText(R.id.tvUserName, item.userInfo?.name)
                helper.setText(R.id.tvContent, item.content)
                val ivVideoBg = helper.getView<ImageView>(R.id.ivVideoBg)
                var imgUrl = ""
                if (item.images != null && item.images!!.isNotEmpty()) {
                    imgUrl = item.images?.get(0).toString()
                }
                ImageLoadUtils(this@FirstFragment).commonDisplayImage("http://p9-tt.byteimg.com/list/300x196/pgc-image/RRe1UEV9TqSEYb.webp", ivVideoBg, DefaultValue.BACKGROUND)

                val ivHead = helper.getView<ImageView>(R.id.ivUserAvatar)

                val avatarUrl = item.userInfo?.avatarUrl ?: ""
                ImageLoadUtils(this@FirstFragment).commonCircleImage("http://p9.pstatp.com/thumb/ff9a000030618970b882", ivHead, DefaultValue.HEAD)
            }
        }
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { mAdapter, _, position ->
            val info = mAdapter.getItem(position) as NewsInfo? ?: return@OnItemClickListener
            val bundle = Bundle()
            bundle.putString(ParamConstants.SEARCH_KEY, info.userInfo?.name)
            toPage(RefreshListActivity::class.java, bundle)
        }
        return adapter
    }

    override fun refreshDataList() {
        followPresenter.getSearchUsers(category)
    }

    override fun getDataSuccess(dataRes: RecommendResponse) {
        val dataList = dataRes.data
        if (dataList != null)
            loadDataSuccess(dataList)
    }

    override fun getVideoListSuccess(videoList: List<VideoDetails>) {
        //loadDataSuccess(videoList)
    }

    override fun loadDataFail(apiTag: InterfaceConfig.HttpHelperTag, errorInfo: String) {

    }
}
