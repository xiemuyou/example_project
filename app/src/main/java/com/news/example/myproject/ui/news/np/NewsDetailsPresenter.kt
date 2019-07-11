package com.news.example.myproject.ui.news.np

import android.webkit.JavascriptInterface
import com.news.example.myproject.base.mvp.BasePresenter
import com.news.example.myproject.global.DefaultValue
import com.news.example.myproject.model.base.BaseApiResponse
import com.news.example.myproject.model.news.NewsDetails
import com.news.example.myproject.model.news.NewsDetailsResponse
import com.news.example.myproject.ui.news.nv.NewsDetailsView
import com.news.example.myproject.znet.InterfaceConfig
import com.news.example.myproject.znet.rx.RxRequestCallback
import org.jsoup.Jsoup
import java.util.*
import java.util.regex.Pattern

/**
 * @author xiemy
 * @date 2018/2/28.
 */
class NewsDetailsPresenter(view: NewsDetailsView) : BasePresenter<NewsDetailsView>(view) {

    fun getNewsDetails(newsId: String?) {
        val tag = InterfaceConfig.HttpHelperTag.GET_EWS_CONTENT
        if (newsId.isNullOrEmpty()) {
            mvpView.loadDataFail(tag, DefaultValue.ERROR_MSG)
            return
        }
        tag.apiTag = "i$newsId/info/"
        RxRequestCallback().request(api = tag, presenter = this, entityClass = NewsDetailsResponse::class.java)
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag, modelRes: BaseApiResponse<*>?, params: Map<String, Any>?) {
        if (apiTag == InterfaceConfig.HttpHelperTag.GET_EWS_CONTENT) {
            val newsRes = modelRes as? NewsDetailsResponse
            val content = getNewContent(getHTML(newsRes?.data))
            mvpView.onSetWebView(content, true)
        }
    }

    private fun getHTML(news: NewsDetails?): String? {
        val title = news?.title
        val content = news?.content
        if (content != null) {
            val css = "<link rel=\"stylesheet\" href=\"file:///android_asset/toutiao_light.css\" type=\"text/css\">"
            return "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">" +
                    css +
                    "<body>\n" +
                    "<article class=\"article-container\">\n" +
                    "    <div class=\"article__content article-content\">" +
                    "<h1 class=\"article-title\">" +
                    title +
                    "</h1>" +
                    content +
                    "    </div>\n" +
                    "</article>\n" +
                    "</body>\n" +
                    "</html>"
        } else {
            return null
        }
    }

    /**
     * 将html文本内容中包含img标签的图片，宽度变为屏幕宽度，高度根据宽度比例自适应
     **/
    private fun getNewContent(htmlText: String?): String {
        if (htmlText.isNullOrEmpty()) {
            return ""
        }
        val doc = Jsoup.parse(htmlText)
        val elements = doc.getElementsByTag("img")
        elements.forEach {
            it.attr("width", "100%").attr("height", "auto")
        }
        return doc.toString()
    }

    /**
     * js 通信接口，定义供 JavaScript 调用的交互接口
     * 点击图片启动新的 Activity，并传入点击图片对应的 url 和页面所有图片
     * 对应的 url
     *
     * @param url 点击图片对应的 url
     */
    @JavascriptInterface
    fun openImage(url: String) {
        //ToastUtils.showLong("图片点击")

//        if (!TextUtils.isEmpty(url)) {
//            val list = getAllImageUrlFromHtml(html)
//            if (list.size > 0) {
//                ImageBrowserActivity.start(InitApp.AppContext, url, list)
//                Log.d(TAG, "openImage: $list")
//            }
//        }
    }

    /***
     * 获取页面所有图片对应的地址对象，
     * 例如 <img src="http://sc1.hao123img.com/data/f44d0aab7bc35b8767de3c48706d429e"></img>
     */
    private fun getAllImageUrlFromHtml(html: String): ArrayList<String> {
        val regex = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>"
        val matcher = Pattern.compile(regex).matcher(html)
        val imgUrlList = ArrayList<String>()
        while (matcher.find()) {
            val count = matcher.groupCount()
            if (count >= 1) {
                imgUrlList.add(matcher.group(1))
            }
        }
        return imgUrlList
    }
}
