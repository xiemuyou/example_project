package com.news.example.myproject.ui.main.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SizeUtils
import com.library.util.ImageLoadUtils
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseFragment
import com.news.example.myproject.global.DefaultValue
import com.news.example.myproject.global.ParamConstants
import com.news.example.myproject.ui.main.mine.adapter.MineMenuAdapter
import com.news.example.myproject.ui.web.NoHeadCommonWebActivity
import com.news.example.myproject.znet.InterfaceConfig
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 * @author xiemy
 * @date 2018/3/16.
 */
class MineFragment : BaseFragment(), MineView {

    private var ivZoomHead: ImageView? = null
    private var ivMineUserAvatar: ImageView? = null
    private var tvMineUserName: TextView? = null
    private var rvContentView: RecyclerView? = null

    companion object {

        const val MAIN_INDEX = 2

        fun newInstance(): MineFragment {
            val args = Bundle()
            val fragment = MineFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val minePresenter: MinePresenter by lazy {
        MinePresenter(this)
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.fragment_mine
    }

    override fun initEnv() {
        rlMineHead?.alpha = 0f
        val headView = LayoutInflater.from(_mActivity).inflate(R.layout.view_mine_head, pzvMineScroll, false)
        val zoomView = LayoutInflater.from(_mActivity).inflate(R.layout.view_mine_zoom, pzvMineScroll, false)

        rvContentView = RecyclerView(_mActivity)
        rvContentView?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        if (rvContentView?.itemAnimator != null) {
            (rvContentView?.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = true
        }

        tvMineUserName = headView.findViewById(R.id.tvMineUserName)
        ivMineUserAvatar = headView.findViewById(R.id.ivMineUserAvatar)
        ivZoomHead = zoomView.findViewById(R.id.ivZoomHead)

        pzvMineScroll?.headerView = headView
        pzvMineScroll?.zoomView = zoomView
        pzvMineScroll?.setScrollContentView(rvContentView)
        pzvMineScroll?.setHeaderLayoutParams(LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(225f)))
        pzvMineScroll?.pullRootView?.setOnScrollChangeListener(changeListener)
        setMineData()
    }

    private val changeListener = NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
        if (scrollY < SizeUtils.dp2px(100f)) {
            val alpha = scrollY.toFloat() / SizeUtils.dp2px(100f)
            rlMineHead?.alpha = alpha
        } else {
            rlMineHead?.alpha = 1f
        }
    }

    private fun setMineData() {
        val radius = 13
        val sampling = 9
        ivMineUserAvatar?.setImageResource(DefaultValue.MINE_HEAD_BLUR)
        //设置高斯模糊背景
        ImageLoadUtils(_mActivity).commonBlurImage(DefaultValue.HEAD_BLUR, ivZoomHead!!, radius, sampling, DefaultValue.HEAD_BLUR)
        tvMineUserName?.setText(R.string.no_login)
        tvMineName?.setText(R.string.mine)
        val menuAdapter = MineMenuAdapter()
        rvContentView?.layoutManager = object : LinearLayoutManager(context, RecyclerView.VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        menuAdapter.setNewData(minePresenter.getMineMenuList())
        menuAdapter.bindToRecyclerView(rvContentView)
        menuAdapter.setOnItemClickListener { _, _, _ ->
            val bundle = Bundle()
            bundle.putString(ParamConstants.WEB_URL, "https://www.baidu.com")
            toPage(NoHeadCommonWebActivity::class.java, bundle)
        }
    }

    override fun loadDataFail(apiTag: InterfaceConfig.HttpHelperTag?, errorInfo: String?) {
        showNotice(errorInfo)
    }
}
