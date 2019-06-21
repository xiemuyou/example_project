package com.doushi.test.myproject.ui.main.mine.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.doushi.test.myproject.R

/**
 * @author xiemy2
 * @date 2019/6/21
 */
class MineMenuAdapter : BaseQuickAdapter<MineMenuData, BaseViewHolder>(R.layout.item_mine_menu) {
    override fun convert(helper: BaseViewHolder?, item: MineMenuData?) {
        helper?.setText(R.id.tvMenuContent, item?.menuName)
    }
}