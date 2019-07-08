package com.news.example.myproject.ui.news.nv

import com.news.example.myproject.base.mvp.BaseView
import com.news.example.myproject.model.news.NewsDetails

/**
 * 刷新页UI更新
 *
 * @author xiemy
 * @date 2017/7/26.
 */
interface NewsDetailsView : BaseView {

    /**
     * 成功获取数据,允许数据为NULL
     *
     * @param response 测试
     */
    fun getDataSuccess(response: NewsDetails?)
}