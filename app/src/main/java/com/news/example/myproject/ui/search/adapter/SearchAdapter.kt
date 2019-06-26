package com.news.example.myproject.ui.search.adapter

import android.content.Context
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.news.example.myproject.R
import com.news.example.myproject.model.base.BaseMultiItemEntity

/**
 * @author xiemy2
 * @date 2019/6/26
 */
class SearchAdapter
/**
 * Same as QuickAdapter#QuickAdapter(Context,int) but with
 * some initialization data.
 *
 * @param data A new list is created out of this one to avoid mutable list
 */
(context: Context, data: List<BaseMultiItemEntity>?) : BaseMultiItemQuickAdapter<BaseMultiItemEntity, BaseViewHolder>(data) {

    private val SEARCH_USER_INTO = 101
    private val SEARCH_VIDEO_HEAD = 102
    private val SEARCH_VIDEO_INFO = 103

    init {
        addItemType(SEARCH_USER_INTO, R.layout.item_search)
        addItemType(SEARCH_VIDEO_HEAD, R.layout.item_search)
        addItemType(SEARCH_VIDEO_INFO, R.layout.item_search)
    }

    override fun convert(helper: BaseViewHolder?, item: BaseMultiItemEntity?) {
        helper?.setText(R.id.tvSearchItem, "搜索" + helper.adapterPosition)
    }
}