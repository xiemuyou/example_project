package com.news.example.myproject.ui.main.home.sort

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseRefreshFragment
import com.news.example.myproject.global.ParamConstants
import com.news.example.myproject.model.news.NewsInfo
import com.news.example.myproject.model.news.RecommendResponse
import com.news.example.myproject.ui.news.NewsDetailsActivity
import com.news.example.myproject.ui.news.np.NewsListPresenter
import com.news.example.myproject.ui.news.nv.NewsListView
import com.news.example.myproject.widgets.news.InformationItemContentView

/**
 * @author xiemy
 * @date 2018/3/19.
 */
class SortFragment : BaseRefreshFragment<NewsInfo>(), NewsListView {

    var category = ""
    private var concernId = ""

    private val followPresenter: NewsListPresenter by lazy {
        NewsListPresenter(this)
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.fragment_sort
    }

    override fun initEnv() {
        super.initEnv()
        category = arguments?.getString(ParamConstants.CATEGORY) ?: ""
        concernId = arguments?.getString(ParamConstants.CONCERN_ID) ?: ""
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
            info.let { NewsDetailsActivity.showClass(it) }
        }
        return adapter
    }

    override fun refreshDataList() {
        followPresenter.getNewsList(category, concernId, page++, CNT)
    }

    override fun getDataSuccess(response: RecommendResponse?) {
        loadDataSuccess(response?.data)
    }
}
