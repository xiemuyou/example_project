package com.news.example.myproject.ui.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.library.FApplication
import com.library.thread.ThreadPool
import com.library.util.PreferencesUtils
import com.news.example.myproject.base.component.BaseFragment
import com.news.example.myproject.global.ParamConstants
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.fragment_common_web.*
import java.io.File


/**
 * 供参考
 * https://juejin.im/entry/58db9643570c350058f35446
 *
 * @author xiemy
 * @date 2018/3/2
 */
class CommonWebFragment : BaseFragment(), ConsultWebCallback.ConsultCallBack {

    var webView: X5WebView? = null

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return com.news.example.myproject.R.layout.fragment_common_web
    }

    override fun initEnv() {
        webView = X5WebView(_mActivity, null)
        flWebViewContent?.addView(webView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
        val webUrl = arguments?.getString(ParamConstants.WEB_URL)
        val contentKey = arguments?.getString(ParamConstants.CONTENT_KEY)
        if (!TextUtils.isEmpty(contentKey)) {
            val webContent = PreferencesUtils.getStringPreferences(contentKey, "")
            loadJsBridgeWebView(webContent)
            PreferencesUtils.remove(contentKey)
        } else {
            webView?.loadUrl(webUrl)
            initWebView()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView?.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        //loadUrl(url);
        webView?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar?.visibility = View.VISIBLE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webView?.loadUrl(request.toString())
                } else {
                    webView?.loadUrl(request?.url.toString())
                }
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar?.visibility = View.GONE
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                progressBar?.visibility = View.GONE
            }
        }
    }

    /**
     * 加载H5页面数据
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun loadJsBridgeWebView(content: String) {
        val settings = webView?.settings
        settings?.builtInZoomControls = false
        settings?.setSupportZoom(false)
        settings?.displayZoomControls = false
        //解决华为手机P10，底部闪烁的问题
        settings?.javaScriptEnabled = true
        webView?.addJavascriptInterface(this, "App")
        webView?.addJavascriptInterface(ConsultWebCallback(this), "SuperCall")
        webView?.loadDataWithBaseURL(null, content, "text/html", "utf-8", null)

        //允许webview对文件的操作
        settings?.setAllowUniversalAccessFromFileURLs(true)
        settings?.allowFileAccess = true
        settings?.setAllowFileAccessFromFileURLs(true)
        settings?.allowContentAccess = true

        webView?.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    webView?.loadUrl(request.toString())
                } else {
                    webView?.loadUrl(request?.url.toString())
                }
                return true
            }

            override fun shouldInterceptRequest(p0: WebView?, p1: String?): WebResourceResponse? {
                if (p1 != null && p1.startsWith("http")) {
                    try {
                        ThreadPool.execute {
                            val future: FutureTarget<File> = Glide.with(FApplication.getContext()).downloadOnly().load(p1).submit()
                            val file = future.get()
                            val local = "content://com.news.example.myproject.consutl.image" + file.absolutePath
                            val call = "javascript:replaceSrc(\"$p1\",\"$local\")"
                            //解决图片不显示的问题
                            Handler().postDelayed({
                                webView?.loadUrl(call)
                            }, 300)
                        }
                        return WebResourceResponse("image/jpeg", "base64", _mActivity.assets.open("loading.png"))
                    } catch (ex: Throwable) {
                    }
                }
                return WebResourceResponse()
            }

            override fun onPageFinished(p0: WebView?, p1: String?) {
                webView?.loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)")
                webView?.loadUrl("javascript:clearEmpty()")
                super.onPageFinished(p0, p1)
            }
        }
    }

    override fun showImageView(index: Int, data: MutableList<ConsultJsDataItem.DataBean>?) {
//        computeBoundsBackward(data)
//        GPreviewBuilder.from(this)
//                .setData(mThumbViewInfoList)
//                .setCurrentIndex(index)
//                .setSingleFling(true)
//                .setType(GPreviewBuilder.IndicatorType.Number)
//                .start()
    }

    override fun onPause() {
        super.onPause()
        webView?.onPause()
    }

    override fun onResume() {
        super.onResume()
        webView?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        //清空缓存
        webView?.clearCache(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            flWebViewContent?.removeView(webView)
            webView?.removeAllViews()
            webView?.destroy()
        } else {
            webView?.removeAllViews()
            webView?.destroy()
            flWebViewContent?.removeView(webView)
        }
        webView = null
    }

    companion object {

        fun newInstance(webUrl: String? = null, webContent: String? = null): CommonWebFragment {
            val webFragment = CommonWebFragment()
            val bundle = Bundle()
            bundle.putString(ParamConstants.WEB_URL, webUrl)
            bundle.putString(ParamConstants.CONTENT_KEY, webContent)
            webFragment.arguments = bundle
            return webFragment
        }
    }
}
