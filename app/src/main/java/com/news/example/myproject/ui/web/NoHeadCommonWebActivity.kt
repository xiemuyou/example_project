package com.news.example.myproject.ui.web

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseSwipeActivity
import com.news.example.myproject.global.ParamConstants
import kotlinx.android.synthetic.main.activity_fragment.*

/**
 *NoHeadCommonWebActivity class
 *@author chends
 *@date 2018/11/27
 */
class NoHeadCommonWebActivity : BaseSwipeActivity() {

    private var fragmentManager: FragmentManager? = null

    companion object {
        /**
         * @param context        上下文
         * @param url            H5 url
         * @param title          H5 title
         * @param isShowTitleBar 是否显示titleBar
         */
        fun showClass(context: Context, url: String) {
            val intent = Intent(context, NoHeadCommonWebActivity::class.java)
            intent.putExtra(ParamConstants.WEB_URL, url)
            intent.putExtra("isShowTitleBar", false)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        setStatusLight(ContextCompat.getColor(this, R.color.white))
        return R.layout.activity_fragment
    }

    override fun initEnv() {
        val intent = intent
        if (null == intent) {
            this.finish()
            return
        }
        fragmentManager = supportFragmentManager
        val url = intent.getStringExtra(ParamConstants.WEB_URL)
        val showBack = intent.getBooleanExtra(ParamConstants.SHOW_BACK, false)
        showHead(false, false)
        loadRootFragment(R.id.flContainer, CommonWebFragment.newInstance(url ?: ""))
        if (showBack) {
            addBackButton()
        } else {
            ivBack.visibility = View.GONE
        }
    }

    private fun addBackButton() {
        ivBack.visibility = View.VISIBLE
        ivBack.setOnClickListener {
            onBackPressedSupport()
        }
    }

    override fun onBackPressedSupport() {
        backPressed()
    }

    override fun backPressed() {
        val commonWebFragment = fragmentManager?.findFragmentById(R.id.flContainer) as CommonWebFragment
        if (commonWebFragment.webView == null) {
            super.backPressed()
            closePage()
            return
        }
        if (commonWebFragment.webView.canGoBack()) {
            commonWebFragment.webView.goBack()
        } else {
            super.backPressed()
            closePage()
        }
    }
}