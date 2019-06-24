package com.news.example.myproject.ui.main.video

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.library.util.ViewUtil

import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseRefreshFragment
import com.news.example.myproject.model.video.VideoDetails
import com.news.example.myproject.ui.main.video.vp.VideoPresenter
import com.news.example.myproject.ui.main.video.vv.VideoView
import com.news.example.myproject.widgets.video.VideoContentView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.CommonUtil

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

    override fun initEnv() {
        super.initEnv()
        ViewUtil.setMargins(refresh, 0, CommonUtil.getStatusBarHeight(_mActivity), 0, 0)
    }

    override fun getRefreshAdapter(dataList: MutableList<VideoDetails>?): RecyclerView.Adapter<*> {
        canContentView.layoutManager = LinearLayoutManager(_mActivity, RecyclerView.VERTICAL, false)
        canContentView.addOnScrollListener(scrollListener)
        val adapter = object : BaseQuickAdapter<VideoDetails, BaseViewHolder>(R.layout.item_video, dataList) {
            override fun convert(helper: BaseViewHolder, item: VideoDetails) {
                val videoView = helper.getView<VideoContentView>(R.id.vvContent)
                videoView.setItemVideoContent(item, this@VideoFragment, true, true)
            }
        }
//        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { mAdapter, _, position ->
//            val info = mAdapter.getItem(position) as NewsInfo? ?: return@OnItemClickListener
//            info.display_url?.let { NoHeadCommonWebActivity.showClass(_mActivity, it) }
//        }
        return adapter
    }


    private val scrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
//                val lastVisibleItem = firstVisibleItem + visibleItemCount
//                //大于0说明有播放
//                if (GSYVideoManager.instance().playPosition >= 0) {
//                    //当前播放的位置
//                    val position = GSYVideoManager.instance().playPosition
//                    //对应的播放列表TAG
//                    if (GSYVideoManager.instance().playTag == ListNormalAdapter.TAG && (position < firstVisibleItem || position > lastVisibleItem)) {
//                        if (GSYVideoManager.isFullState(_mActivity)) {
//                            return
//                        }
//                        //如果滑出去了上面和下面就是否，和今日头条一样
//                        GSYVideoManager.releaseAllVideos()
//                        adapter.notifyDataSetChanged()
//                    }
//                }
        }
    }

    override fun refreshDataList() {
        videoPresenter.getTodayVideoList("")
    }

    override fun getVideoListSuccess(videoList: MutableList<VideoDetails>?) {
        loadDataSuccess(videoList)
    }

    /**
     * 刷新数据
     */
    fun refreshData() {
        placedTopAutoRefresh()
    }

    override fun onBackPressedSupport(): Boolean {
        if (GSYVideoManager.backFromWindowFull(_mActivity)) {
            return false
        }
        return super.onBackPressedSupport()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }
}
