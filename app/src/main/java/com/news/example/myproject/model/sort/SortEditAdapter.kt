package com.news.example.myproject.model.sort

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.news.example.myproject.R

/**
 * @author tanghao
 * @date 2018/8/28
 */
class SortEditAdapter(data: List<NewsSortInfo>?) : BaseMultiItemQuickAdapter<NewsSortInfo, BaseViewHolder>(data) {

    private var onLongPressListener: OnLongPressListener? = null
    private var mIsShowClose = false

    fun setOnLongPressListener(onLongPressListener: OnLongPressListener) {
        this.onLongPressListener = onLongPressListener
    }

    init {
        addItemType(NewsSortInfo.CHOOSE, R.layout.item_sort_choose)
        addItemType(NewsSortInfo.FIXED, R.layout.item_sort_choose)

        addItemType(NewsSortInfo.TITLE, R.layout.item_sort_title)
        addItemType(NewsSortInfo.MORE, R.layout.item_sort_more)
    }

    fun setIsShowClose(isShowClose: Boolean) {
        this.mIsShowClose = isShowClose
    }

    fun isShowClose(): Boolean {
        return mIsShowClose
    }

    override fun convert(helper: BaseViewHolder?, item: NewsSortInfo?) {
        if (helper == null || item == null) {
            return
        }
        val sortName = item.name
        val nameLen = sortName?.length ?: 0
        when (helper.itemViewType) {
            NewsSortInfo.FIXED, NewsSortInfo.CHOOSE -> {
                val tvLabel = helper.getView<TextView>(R.id.tvSortLabel)
                tvLabel.text = sortName
                val isFixed = helper.itemViewType == NewsSortInfo.FIXED
                val color = if (isFixed) Color.parseColor("#9c9c9c") else Color.parseColor("#2c2c2c")
                tvLabel.setTextColor(color)
                var textSize = 15
                if (nameLen == 4) {
                    textSize = 14
                } else if (nameLen >= 5) {
                    textSize = 12
                }
                tvLabel.textSize = textSize.toFloat()
                val visible = if (mIsShowClose && !isFixed) View.VISIBLE else View.GONE
                helper.getView<View>(R.id.ivSortClose).visibility = visible
                if (onLongPressListener == null) {
                    return
                }
                helper.itemView.setOnLongClickListener {
                    onLongPressListener!!.onLongPressed(helper, item)
                    true
                }
            }

            NewsSortInfo.MORE -> {
                val tvLabelName = helper.getView<TextView>(R.id.tv_label)
                val ivLabelAdd = helper.getView<ImageView>(R.id.iv_label_add)
                helper.setText(R.id.tv_label, sortName)
                var size = 15
                var resAdd = R.drawable.icon_add_big
                var margin = 6
                if (nameLen == 4) {
                    size = 12
                    resAdd = R.drawable.icon_add_small
                    margin = 3
                } else if (nameLen >= 5) {
                    size = 11
                    resAdd = R.drawable.icon_add_small
                    margin = 3
                }
                tvLabelName.textSize = size.toFloat()
                ivLabelAdd.setImageResource(resAdd)

                val layoutParams = tvLabelName.layoutParams as LinearLayout.LayoutParams
                layoutParams.leftMargin = SizeUtils.dp2px(margin.toFloat())
            }

            NewsSortInfo.TITLE -> {
            }

            else -> {
            }
        }
    }

    interface OnLongPressListener {
        fun onLongPressed(helper: BaseViewHolder?, item: NewsSortInfo?)
    }
}
