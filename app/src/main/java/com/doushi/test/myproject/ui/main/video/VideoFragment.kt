package com.doushi.test.myproject.ui.main.video

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

import com.doushi.test.myproject.R
import com.doushi.test.myproject.base.component.BaseFragment
import com.doushi.test.myproject.base.component.BaseRefreshFragment
import com.doushi.test.myproject.model.news.NewsInfo
import com.doushi.test.myproject.model.video.VideoDetails
import com.doushi.test.myproject.ui.main.home.HomeFragment
import com.doushi.test.myproject.ui.web.NoHeadCommonWebActivity
import com.doushi.test.myproject.widgets.news.InformationItemContentView
import com.doushi.test.myproject.widgets.video.VideoContentView

/**
 * @author xiemy
 * @date 2018/3/16.
 */
class VideoFragment : BaseRefreshFragment<VideoDetails>() {

    override fun getRefreshAdapter(dataList: MutableList<VideoDetails>?): RecyclerView.Adapter<*> {
        canContentView.layoutManager = LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false)
        val adapter = object : BaseQuickAdapter<VideoDetails, BaseViewHolder>(R.layout.item_video, dataList) {
            override fun convert(helper: BaseViewHolder, item: VideoDetails) {
                val videoView = helper.getView<VideoContentView>(R.id.vvContent)
                videoView.setItemVideoContent(item, true, true)
            }
        }
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { mAdapter, _, position ->
            val info = mAdapter.getItem(position) as NewsInfo? ?: return@OnItemClickListener
            info.display_url?.let { NoHeadCommonWebActivity.showClass(_mActivity, it) }
        }
        return adapter
    }

    override fun refreshDataList() {

    }

    override fun initEnv() {

    }

    companion object {

        val MAIN_INDEX = 1

        fun newInstance(): VideoFragment {
            val args = Bundle()
            val fragment = VideoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}