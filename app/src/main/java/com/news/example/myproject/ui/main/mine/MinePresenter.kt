package com.news.example.myproject.ui.main.mine

import com.news.example.myproject.base.mvp.BasePresenter
import com.news.example.myproject.model.base.BaseApiResponse
import com.news.example.myproject.ui.main.mine.adapter.MineMenuData
import com.news.example.myproject.znet.InterfaceConfig

/**
 * @author xiemy2
 * @date 2019/7/9
 */
class MinePresenter(mineView: MineView) : BasePresenter<MineView>(mineView) {

    fun getMineMenuList(): ArrayList<MineMenuData> {
        val menus = ArrayList<MineMenuData>()
        menus.add(MineMenuData(menuName = "历史"))
        menus.add(MineMenuData(menuName = "收藏"))
        menus.add(MineMenuData(menuName = "帮助"))
        menus.add(MineMenuData(menuName = "关于"))
        return menus
    }

    override fun onLoadDataSuccess(apiTag: InterfaceConfig.HttpHelperTag?, modelRes: BaseApiResponse<*>?, params: MutableMap<String, Any>?) {

    }
}
