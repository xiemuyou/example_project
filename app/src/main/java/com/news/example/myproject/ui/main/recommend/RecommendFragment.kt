package com.news.example.myproject.ui.main.recommend

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseRefreshFragment
import com.news.example.myproject.model.news.NewsInfo
import com.news.example.myproject.model.news.RecommendResponse
import com.news.example.myproject.ui.main.home.HomeFragment
import com.news.example.myproject.ui.news.np.NewsListPresenter
import com.news.example.myproject.ui.news.nv.NewsListView
import com.news.example.myproject.ui.web.NoHeadCommonWebActivity
import com.news.example.myproject.widgets.news.InformationItemContentView
import com.news.example.myproject.znet.InterfaceConfig

/**
 * @author xiemy
 * @date 2018/3/19.
 */
class RecommendFragment : BaseRefreshFragment<NewsInfo>(), NewsListView {

    var category = ""

    companion object {
        const val SORT_NAME = "sortName"
    }

    private val followPresenter by lazy {
        NewsListPresenter(this)
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
        (parentFragment as HomeFragment?)?.checkHotSearchList()
    }

    override fun getDataSuccess(response: RecommendResponse?) {
        loadDataSuccess(response?.data)
    }

    override fun loadDataFail(apiTag: InterfaceConfig.HttpHelperTag, errorInfo: String) {

    }
}
