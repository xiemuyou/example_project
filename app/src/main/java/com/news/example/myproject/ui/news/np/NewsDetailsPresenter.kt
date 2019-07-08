package com.news.example.myproject.ui.news.np

import com.library.util.LogUtil
import com.news.example.myproject.base.mvp.BasePresenter
import com.news.example.myproject.model.base.BaseApiResponse
import com.news.example.myproject.model.news.NewsDetailsResponse
import com.news.example.myproject.ui.news.nv.NewsDetailsView
import com.news.example.myproject.znet.InterfaceConfig
import com.news.example.myproject.znet.rx.RxRequestCallback

/**
 * @author xiemy
 * @date 2018/2/28.
 */
class NewsDetailsPresenter(view: NewsDetailsView) : BasePresenter<NewsDetailsView>(view) {

    fun getNewsDetails(newsId: String) {
        val tag = InterfaceConfig.HttpHelperTag.GET_EWS_CONTENT
        tag.apiTag = "i$newsId/info/"
        RxRequestCallback().request(params = null, api = tag, presenter = this, entityClass = NewsDetailsResponse::class.java)
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag, modelRes: BaseApiResponse<*>?, params: Map<String, Any>?) {
        if (apiTag == InterfaceConfig.HttpHelperTag.GET_EWS_CONTENT) {
            val newsRes = modelRes as NewsDetailsResponse
            mvpView.getDataSuccess(newsRes.data)
        }
    }

    override fun loadDataFail(apiTag: InterfaceConfig.HttpHelperTag, errorCode: Int, params: MutableMap<String, Any>?, errorMsg: String?) {
        super.loadDataFail(apiTag, errorCode, params, errorMsg)
        LogUtil.d(TAG, errorMsg)
    }
}
