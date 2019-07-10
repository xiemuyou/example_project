package com.news.example.myproject.ui.news

import android.graphics.PixelFormat
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ActivityUtils
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseActivity
import com.news.example.myproject.global.ParamConstants
import com.news.example.myproject.model.news.NewsInfo

/**
 * @author xiemy2
 * @date 2019/6/3
 */
class NewsDetailsActivity : BaseActivity() {

    companion object {
        fun showClass(newsInfo: NewsInfo? = null) {
            val bundle = Bundle()
            bundle.putSerializable(ParamConstants.CONTENT, newsInfo)
            ActivityUtils.startActivity(bundle, NewsDetailsActivity::class.java)
        }
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.activity_fragment
    }

    override fun initEnv() {
        window.setFormat(PixelFormat.TRANSLUCENT)
        setStatusLight(ContextCompat.getColor(this, R.color.white))
        val news = intent?.getSerializableExtra(ParamConstants.CONTENT) as? NewsInfo
        showHead(true, true)
        setHeadTitle(news?.title)
        loadRootFragment(R.id.flContainer, NewsDetailsFragment.newInstance(news))
    }
}