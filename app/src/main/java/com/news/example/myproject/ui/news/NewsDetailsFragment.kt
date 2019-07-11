package com.news.example.myproject.ui.news

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseFragment
import com.news.example.myproject.global.Constants
import com.news.example.myproject.global.ParamConstants
import com.news.example.myproject.model.news.NewsInfo
import com.news.example.myproject.ui.news.np.NewsDetailsPresenter
import com.news.example.myproject.ui.news.nv.NewsDetailsView
import com.news.example.myproject.znet.InterfaceConfig
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.fragment_news_detail.*

/**
 * @author xiemy2
 * @date 2019/7/9
 */
class NewsDetailsFragment : BaseFragment(), NewsDetailsView {

    private var newsInfo: NewsInfo? = null
    private var mediaId = ""
    private var mediaName = ""
    private var shareUrl = ""

    private val presenter: NewsDetailsPresenter by lazy {
        NewsDetailsPresenter(this)
    }

    companion object {

        fun newInstance(newsInfo: NewsInfo? = null): NewsDetailsFragment {
            val webFragment = NewsDetailsFragment()
            val bundle = Bundle()
            bundle.putSerializable(ParamConstants.CONTENT, newsInfo)
            webFragment.arguments = bundle
            return webFragment
        }
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.fragment_news_detail
    }

    override fun initEnv() {
        initData()
        initView()
    }

    private fun initData() {
        newsInfo = arguments?.getSerializable(ParamConstants.CONTENT) as NewsInfo?
        mediaId = newsInfo?.userInfo?.userId ?: ""
        mediaName = newsInfo?.userInfo?.name ?: ""
        shareUrl = newsInfo?.display_url ?: ""
        presenter.getNewsDetails(newsInfo?.newsId)
    }

    private fun initView() {
        initWebClient()
        scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, _, _, _ ->
            onHideLoading()
        })

        progressBar.indeterminateDrawable.setColorFilter(
                ContextCompat.getColor(_mActivity, R.color.colorPrimary),
                PorterDuff.Mode.MULTIPLY)
        progressBar.show()

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(_mActivity, R.color.colorPrimary))
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = true }
            presenter.getNewsDetails(newsInfo?.newsId)
        }
        setHasOptionsMenu(true)
    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun initWebClient() {
        val settings = webView.settings
        settings.javaScriptEnabled = true
        // 缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        settings.builtInZoomControls = false
        // 缓存
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        // 开启DOM storage API功能
        settings.domStorageEnabled = true
        // 开启application Cache功能
        settings.setAppCacheEnabled(true)
        // 判断是否为无图模式
        //settings.blockNetworkImage = SettingUtil.getInstance().getIsNoPhotoMode()
        //设置webView里字体大小
        settings.textSize = WebSettings.TextSize.LARGEST

        // 不调用第三方浏览器即可进行页面反应
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (!TextUtils.isEmpty(url)) {
                    view.loadUrl(url)
                }
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                onHideLoading()
                // 注入 js 函数(图片点击)监听
                webView.loadUrl(Constants.JS_INJECT_IMG)
                webView.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)")
                webView.loadUrl("javascript:clearEmpty()")
                super.onPageFinished(view, url)
            }
        }

        webView.setOnKeyListener { _, _, keyEvent ->
            if (keyEvent.keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                webView.goBack()
                return@setOnKeyListener true
            }
            false
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress >= 90) {
                    onHideLoading()
                } else {
                    onShowLoading()
                }
            }
        }
        webView.addJavascriptInterface(presenter, "imageListener")
    }

    override fun onSetWebView(content: String?, flag: Boolean?) {
        onHideLoading()
        if (flag == true) {
            webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)
        } else {
            /*
               ScrollView 嵌套 WebView, 导致部分网页无法正常加载
               如:https://temai.snssdk.com/article/feed/index/?id=11754971
               最佳做法是去掉 ScrollView, 或使用 NestedScrollWebView
             */
            if (shareUrl.contains("temai.snssdk.com")) {
                webView.settings.userAgentString = Constants.USER_AGENT_PC
            }
            webView.loadUrl(shareUrl)
        }
    }

    fun onShowLoading() {
        progressBar?.show()
    }

    fun onHideLoading() {
        progressBar?.hide()
        swipeRefreshLayout?.post { swipeRefreshLayout?.isRefreshing = false }
    }

    override fun loadDataFail(apiTag: InterfaceConfig.HttpHelperTag?, errorInfo: String?) {
        showNotice(errorInfo)
        onSetWebView(null, false)
    }
}
