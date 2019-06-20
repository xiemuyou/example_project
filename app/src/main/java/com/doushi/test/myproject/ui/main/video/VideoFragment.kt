package com.doushi.test.myproject.ui.main.video

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

import com.doushi.test.myproject.R
import com.doushi.test.myproject.base.component.BaseRefreshFragment
import com.doushi.test.myproject.model.news.NewsInfo
import com.doushi.test.myproject.model.video.VideoDetails
import com.doushi.test.myproject.ui.main.video.vp.VideoPresenter
import com.doushi.test.myproject.ui.main.video.vv.VideoView
import com.doushi.test.myproject.ui.web.NoHeadCommonWebActivity
import com.doushi.test.myproject.widgets.video.VideoContentView

/**
 * @author xiemy
 * @date 2018/3/16.
 */
class VideoFragment : BaseRefreshFragment<VideoDetails>(), VideoView {

    private val videoPresenter by lazy {
        VideoPresenter(this@VideoFragment)
    }

    companion object {
        const val MAIN_INDEX = 1
        fun newInstance(): VideoFragment {
            val args = Bundle()
            val fragment = VideoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getRefreshAdapter(dataList: MutableList<VideoDetails>?): RecyclerView.Adapter<*> {
        canContentView.layoutManager = LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false)
        val adapter = object : BaseQuickAdapter<VideoDetails, BaseViewHolder>(R.layout.item_video, dataList) {
            override fun convert(helper: BaseViewHolder, item: VideoDetails) {
                val videoView = helper.getView<VideoContentView>(R.id.vvContent)
                videoView.setItemVideoContent(item, this@VideoFragment, true, true)
            }
        }
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { mAdapter, _, position ->
            val info = mAdapter.getItem(position) as NewsInfo? ?: return@OnItemClickListener
            info.display_url?.let { NoHeadCommonWebActivity.showClass(_mActivity, it) }
        }
        return adapter
    }

    override fun refreshDataList() {
        videoPresenter.getTodayVideoList("")
    }

    override fun getVideoListSuccess(videoList: MutableList<VideoDetails>?) {
        loadDataSuccess(videoList)
    }
}
