package com.doushi.test.myproject.ui.web

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.text.TextUtils
import android.view.View

import com.blankj.utilcode.util.ActivityUtils
import com.doushi.test.myproject.R
import com.doushi.test.myproject.base.component.BaseActivity
import com.doushi.test.myproject.global.ParamConstants

import kotlinx.android.synthetic.main.activity_fragment.*

/**
 * 默认 WebView Activity
 *
 * @author xiemy
 * @date 2018/3/6
 */
class CommonWebActivity : BaseActivity() {

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
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
        var title = intent.getStringExtra(ParamConstants.TITLE)
        title = if (title != null && title.length > 8) title.replace(title.substring(8), "...") else title
        val diverFlag = intent.getBooleanExtra(ParamConstants.DIVER_FLAG, true)
        showHead(!TextUtils.isEmpty(title), diverFlag)
        setHeadTitle(title)
        loadRootFragment(R.id.flContainer, CommonWebFragment.newInstance(intent.getStringExtra(ParamConstants.WEB_URL)))
        if (!TextUtils.isEmpty(title)) {
            ivBack.visibility = View.VISIBLE
            ivBack.setOnClickListener {
                onBackPressedSupport()
            }
        } else {
            ivBack.visibility = View.GONE
        }
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
        if (commonWebFragment.webView.canGoBack()) {
            commonWebFragment.webView.goBack()
        } else {
            super.backPressed()
            closePage()
        }
    }

    companion object {

        @JvmOverloads
        fun showClass(context: Context, webUrl: String, title: String? = "", diverFlag: Boolean? = null) {
            val intent = Intent(context, CommonWebActivity::class.java)
            val bundle = Bundle()
            bundle.putString(ParamConstants.WEB_URL, webUrl)
            bundle.putString(ParamConstants.TITLE, title)
            if (diverFlag != null) {
                bundle.putBoolean(ParamConstants.DIVER_FLAG, diverFlag)
            }
            intent.putExtras(bundle)
            ActivityUtils.startActivity(intent)
        }
    }
}
