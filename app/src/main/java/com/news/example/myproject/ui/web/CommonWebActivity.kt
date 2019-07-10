package com.news.example.myproject.ui.web

import android.graphics.PixelFormat
import android.os.Bundle
import android.text.TextUtils
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ActivityUtils
import com.library.widgets.statusbar.StatusBarCompat
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseActivity
import com.news.example.myproject.global.ParamConstants

/**
 * 默认 WebView Activity
 *
 * @author xiemy
 * @date 2018/3/6
 */
open class CommonWebActivity : BaseActivity() {

    companion object {

        @JvmOverloads
        fun showClass(webUrl: String, title: String? = "", contentKey: String? = "", diverFlag: Boolean? = null) {
            val bundle = Bundle()
            bundle.putString(ParamConstants.WEB_URL, webUrl)
            bundle.putString(ParamConstants.TITLE, title)
            bundle.putString(ParamConstants.CONTENT_KEY, contentKey)
            if (diverFlag != null) {
                bundle.putBoolean(ParamConstants.DIVER_FLAG, diverFlag)
            }
            ActivityUtils.startActivity(bundle, CommonWebActivity::class.java)
        }
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white))
        return R.layout.activity_fragment
    }

    override fun initEnv() {
        //网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置：Activity在onCreate时需要设置:
        //（这个对宿主没什么影响，建议声明）
        window.setFormat(PixelFormat.TRANSLUCENT)
        setStatusLight(ContextCompat.getColor(this, R.color.white))
        val intent = intent
        if (null == intent) {
            this.finish()
            return
        }
        setWebTitle(intent.getStringExtra(ParamConstants.TITLE))
        val url = intent.getStringExtra(ParamConstants.WEB_URL)
        val content = intent.getStringExtra(ParamConstants.CONTENT_KEY)
        loadRootFragment(R.id.flContainer, CommonWebFragment.newInstance(webUrl = url, webContent = content))
    }

    fun setWebTitle(title: String?) {
        val mTitle = if (title != null && title.length > 8) title.replace(title.substring(8), "...") else title
        val diverFlag = intent.getBooleanExtra(ParamConstants.DIVER_FLAG, true)
        showHead(!TextUtils.isEmpty(mTitle), diverFlag)
        setHeadTitle(mTitle)
    }

    override fun onBackPressedSupport() {
        super.onBackPressedSupport()
        closePage()
    }

    override fun backPressed() {
        val commonWebFragment = supportFragmentManager.findFragmentById(R.id.flContainer) as CommonWebFragment
        if (null == commonWebFragment.webView) {
            super.backPressed()
            closePage()
            return
        }
        if (commonWebFragment.webView?.canGoBack() == true) {
            commonWebFragment.webView?.goBack()
        } else {
            super.backPressed()
            closePage()
        }
    }
}
