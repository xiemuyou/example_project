package com.news.example.myproject.ui.main.mp

import com.news.example.myproject.base.mvp.BasePresenter
import com.news.example.myproject.model.base.BaseApiResponse
import com.news.example.myproject.model.base.StringListResponse
import com.news.example.myproject.model.sort.NewsSortInfo
import com.news.example.myproject.model.sort.NewsSortListResponse
import com.news.example.myproject.ui.main.mv.MainView
import com.news.example.myproject.znet.InterfaceConfig
import com.news.example.myproject.znet.rx.RxRequestCallback
import org.json.JSONException
import org.json.JSONObject

/**
 * @author xiemy
 * @date 2018/2/28.
 */
class MainPresenter(view: MainView) : BasePresenter<MainView>(view) {

    /**
     * 热搜
     * https://is.snssdk.com/2/article/hot_words/
     */
    fun articleHotWords() {
        RxRequestCallback().request(params = null, api = InterfaceConfig.HttpHelperTag.ARTICLE_HOT_WORDS, entityClass = StringListResponse::class.java, presenter = this)
    }

    /**
     * 分类
     */
    fun getCategoryExtra() {
        RxRequestCallback().request(params = null, api = InterfaceConfig.HttpHelperTag.ARTICLE_CATEGORY_GET_EXTRA_V1, presenter = this)
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag, modelRes: BaseApiResponse<*>, params: Map<String, Any>?) {
        when (apiTag) {
            InterfaceConfig.HttpHelperTag.ARTICLE_HOT_WORDS -> {
                val res: StringListResponse = modelRes as StringListResponse
                mvpView.getSortListSuccess(res.data)
            }

            else -> {
            }
        }
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag, res: String, params: Map<String, Any>?) {
        when (apiTag) {
            InterfaceConfig.HttpHelperTag.ARTICLE_CATEGORY_GET_EXTRA_V1 -> {
                val success = parseJson(res)
                mvpView.getCategoryExtraSuccess(success)
            }

            else -> {
            }
        }
    }

    private fun parseJson(resStr: String): NewsSortListResponse {
        val response = JSONObject(resStr)
        val res = NewsSortListResponse()
        try {
            val obj = response.getJSONObject("data")
            res.version = obj.getString("version")
            val array = obj.getJSONArray("data")
            val list = ArrayList<NewsSortInfo>(array.length())
            for (i in 0 until array.length()) {
                val sort = NewsSortInfo()
                val sortObj: JSONObject = array[i] as JSONObject
                sort.name = sortObj.getString("name")
                sort.category = sortObj.getString("category")
                list.add(sort)
            }
            res.data = list
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return res
    }
}
