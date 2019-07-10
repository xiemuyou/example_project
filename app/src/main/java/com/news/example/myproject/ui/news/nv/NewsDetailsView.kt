package com.news.example.myproject.ui.news.nv

import com.news.example.myproject.base.mvp.BaseView

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
     * @param url 连接
     * @param flag 标记
     */
    fun onSetWebView(content: String?, flag: Boolean?)
}