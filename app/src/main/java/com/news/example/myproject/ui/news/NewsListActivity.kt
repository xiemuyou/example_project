package com.news.example.myproject.ui.news

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.library.util.ImageLoadUtils
import com.library.widgets.layoutManager.DividerItemDecoration
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseRefreshActivity
import com.news.example.myproject.global.DefaultValue
import com.news.example.myproject.global.ParamConstants
import com.news.example.myproject.model.news.RecommendResponse
import com.news.example.myproject.model.user.UserInfo
import com.news.example.myproject.ui.news.np.NewsListPresenter
import com.news.example.myproject.ui.news.nv.NewsListView
import com.news.example.myproject.ui.refresh.FollowActivity

/**
 * 刷新数据 Activity
 *
 * 数据上拉加载,下拉刷新
 *
 * @author xiemy
 * @date 2018/3/1.
 */
class NewsListActivity : BaseRefreshActivity<UserInfo>(), NewsListView {

    private val refreshPresenter :NewsListPresenter by lazy {
        NewsListPresenter(this)
    }

    private var searchText: String? = null

    override fun initEnv() {
        searchText = intent.getStringExtra(ParamConstants.SEARCH_KEY)
        super.initEnv()
    }

    override fun getRefreshAdapter(dataList: List<UserInfo>): RecyclerView.Adapter<*> {
        val refreshAdapter = object : BaseQuickAdapter<UserInfo, BaseViewHolder>(R.layout.item_user, dataList) {

            override fun convert(helper: BaseViewHolder, item: UserInfo) {
                helper.setText(R.id.tvUserName, item.name + ":" + helper.adapterPosition)
                val ivVideoBg = helper.getView<ImageView>(R.id.ivVideoBg)
                ImageLoadUtils(this@NewsListActivity).commonDisplayImage(item.avatarUrl, ivVideoBg, DefaultValue.BACKGROUND)

                val ivHead = helper.getView<ImageView>(R.id.ivUserAvatar)
                ImageLoadUtils(this@NewsListActivity).commonCircleImage(item.avatarUrl, ivHead, DefaultValue.HEAD)
            }
        }
        refreshAdapter.setOnItemClickListener { _, _, _ -> toPage(FollowActivity::class.java) }

        val llm = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        canContentView.layoutManager = llm
        val decoration = DividerItemDecoration(this, 5, true,
                ContextCompat.getColor(this@NewsListActivity, R.color.default_toast_bg))
        canContentView.addItemDecoration(decoration)
        canContentView.adapter = refreshAdapter
        return refreshAdapter
    }

    override fun refreshDataList() {
        canContentView.postDelayed({ refreshPresenter?.getNewsList("", "") }, 2000)
    }

    override fun getDataSuccess(response: RecommendResponse?) {
        //数据为空也需要传NULL值,
        //        List<UserInfo> dataList = ObjectUtils.isNotEmpty(dataRes.getData()) ? dataRes.getData().getUser_list() : null;
        //        loadDataSuccess(dataList);
    }
}
