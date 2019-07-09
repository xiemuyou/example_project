package com.news.example.myproject.ui.refresh

import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.library.global.FConstants
import com.library.util.ImageLoadUtils
import com.library.widgets.layoutManager.DividerItemDecoration
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseRefreshFragment
import com.news.example.myproject.global.DefaultValue
import com.news.example.myproject.global.ParamConstants
import com.news.example.myproject.model.news.NewsInfo
import com.news.example.myproject.model.news.RecommendResponse
import com.news.example.myproject.ui.news.NewsListActivity
import com.news.example.myproject.ui.news.np.NewsListPresenter
import com.news.example.myproject.ui.news.nv.NewsListView

/**
 * @author xiemy
 * @date 2018/3/2
 */
class FollowFragment : BaseRefreshFragment<NewsInfo>(), NewsListView {

    private var followPresenter: NewsListPresenter? = null

    override fun getRefreshAdapter(followList: List<NewsInfo>): RecyclerView.Adapter<*> {
        val llm = LinearLayoutManager(_mActivity, RecyclerView.VERTICAL, false)
        canContentView.layoutManager = llm
        val decoration = DividerItemDecoration(_mActivity, 5, true, ContextCompat.getColor(_mActivity, R.color.default_toast_bg))
        canContentView.addItemDecoration(decoration)
        val adapter = object : BaseQuickAdapter<NewsInfo, BaseViewHolder>(R.layout.item_user, followList) {
            override fun convert(helper: BaseViewHolder, item: NewsInfo?) {
                helper.setText(R.id.tvUserName, item?.userInfo?.name + ":" + helper.adapterPosition)
                val ivVideoBg = helper.getView<ImageView>(R.id.ivVideoBg)
                val bgUrl = item?.images?.get(0) ?: ""
                ImageLoadUtils(_mActivity).commonDisplayImage(bgUrl, ivVideoBg, DefaultValue.BACKGROUND)

                val ivHead = helper.getView<ImageView>(R.id.ivUserAvatar)
                val headUrl = item?.userInfo?.avatarUrl ?: ""
                ImageLoadUtils(_mActivity).commonCircleImage(headUrl, ivHead, DefaultValue.HEAD)
            }
        }
        adapter.setOnItemClickListener { adapter1, _, position ->
            val info = adapter1.getItem(position) as NewsInfo?
            if (null != info) {
                val bundle = Bundle()
                bundle.putString(ParamConstants.SEARCH_KEY, info.userInfo?.name)
                toPage(NewsListActivity::class.java, bundle)
            }
        }
        return adapter
    }

    override fun refreshDataList() {
        if (followPresenter == null) {
            followPresenter = NewsListPresenter(this)
        }
        refresh.postDelayed({ followPresenter?.getNewsList("测试", "") }, 1000)
    }

    override fun getDataSuccess(response: RecommendResponse?) {
        loadDataSuccess(response?.data)
    }

    companion object {

        fun newInstance(uid: Int): FollowFragment {
            val followFragment = FollowFragment()
            val bundle = Bundle()
            bundle.putInt(FConstants.UID, uid)
            followFragment.arguments = bundle
            return followFragment
        }
    }
}
