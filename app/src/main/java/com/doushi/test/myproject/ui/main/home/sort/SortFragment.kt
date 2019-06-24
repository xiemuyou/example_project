package com.doushi.test.myproject.ui.main.home.sort

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.doushi.test.myproject.R
import com.doushi.test.myproject.base.component.BaseRefreshFragment
import com.doushi.test.myproject.model.news.NewsInfo
import com.doushi.test.myproject.model.news.RecommendResponse
import com.doushi.test.myproject.model.video.VideoDetails
import com.doushi.test.myproject.ui.refresh.rp.RefreshPresenter
import com.doushi.test.myproject.ui.refresh.rv.RefreshListView
import com.doushi.test.myproject.ui.web.NoHeadCommonWebActivity
import com.doushi.test.myproject.widgets.news.InformationItemContentView
import com.doushi.test.myproject.znet.InterfaceConfig

/**
 * @author xiemy
 * @date 2018/3/19.
 */
class SortFragment : BaseRefreshFragment<NewsInfo>(), RefreshListView {
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
        canContentView.layoutManager = LinearLayoutManager(_mActivity, RecyclerView.VERTICAL, false)
        val adapter = object : BaseQuickAdapter<NewsInfo, BaseViewHolder>(R.layout.item_information, dataList) {
            override fun convert(helper: BaseViewHolder, item: NewsInfo) {
                val informationItemView = helper.getView<InformationItemContentView>(R.id.informationItemView)
                informationItemView.setInformationContent(mContext, item)
            }
        }
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { mAdapter, _, position ->
            val info = mAdapter.getItem(position) as NewsInfo? ?: return@OnItemClickListener
            info.display_url?.let { NoHeadCommonWebActivity.showClass(_mActivity, it) }
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
