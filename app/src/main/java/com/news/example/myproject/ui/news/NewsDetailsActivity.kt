package com.news.example.myproject.ui.news

import android.annotation.SuppressLint
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.library.FApplication
import com.library.thread.ThreadPool
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseActivity
import com.news.example.myproject.global.ParamConstants
import com.news.example.myproject.ui.web.ConsultJsDataItem
import com.news.example.myproject.ui.web.ConsultWebCallback
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_news_details.*
import java.io.File

/**
 * @author xiemy2
 * @date 2019/6/3
 */
class NewsDetailsActivity : BaseActivity(), ConsultWebCallback.ConsultCallBack {

    private var displayUrl = ""

    private val webViewHandler = ActivityHandler(this)

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.activity_news_details
    }

    override fun initEnv() {
        displayUrl = intent.getStringExtra(ParamConstants.WEB_URL)
        wvNewsContent.loadUrl(displayUrl)
    }

    /**
     * 加载H5页面数据
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun loadJsBridgeWebView(content: String) {
        wvNewsContent.settings.builtInZoomControls = false
        wvNewsContent.settings.setSupportZoom(false)
        wvNewsContent.settings.displayZoomControls = false
        //解决华为手机P10，底部闪烁的问题
        //TODO 崩溃 ,先注释
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && RomUtils.isEmui()) {
//            JsBridgeWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
//        }

        //TODO 崩溃 ,先注释
//        wvNewsContent.settings.javaScriptEnabled = true
        wvNewsContent.addJavascriptInterface(this, "App")
        wvNewsContent.addJavascriptInterface(ConsultWebCallback(this), "SuperCall")
        wvNewsContent.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)

        //允许webview对文件的操作
        wvNewsContent.settings.setAllowUniversalAccessFromFileURLs(true)
        wvNewsContent.settings.allowFileAccess = true
        wvNewsContent.settings.setAllowFileAccessFromFileURLs(true)
        wvNewsContent.settings.allowContentAccess = true

        wvNewsContent.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
                return true
            }

            override fun shouldInterceptRequest(p0: WebView?, p1: String?): WebResourceResponse? {
                if (p1 != null && p1.startsWith("http") && p1.endsWith("x-oss-process=image/resize,w_720")) {
                    try {
                        ThreadPool.execute {
                            val future: FutureTarget<File> = Glide.with(FApplication.getContext()).downloadOnly().load(p1).submit()
                            val file = future.get()
                            val local = "content://com.kingnet.fiveline.consutl.image" + file.absolutePath
                            val call = "javascript:replaceSrc(\"$p1\",\"$local\")"
                            //解决图片不显示的问题 具体原因不明
                            webViewHandler.postDelayed({
                                wvNewsContent.loadUrl(call)
                            }, 300)
                        }
                        return WebResourceResponse("image/jpeg", "base64", assets.open("wwwResource/loading.png"))
                    } catch (ex: Throwable) {
                    }
                }
                return WebResourceResponse()
            }

            override fun onPageFinished(p0: WebView?, p1: String?) {
                wvNewsContent.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)")
                wvNewsContent.loadUrl("javascript:clearEmpty()")
                super.onPageFinished(p0, p1)
            }
        }
    }

    override fun showImageView(index: Int, data: MutableList<ConsultJsDataItem.DataBean>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}