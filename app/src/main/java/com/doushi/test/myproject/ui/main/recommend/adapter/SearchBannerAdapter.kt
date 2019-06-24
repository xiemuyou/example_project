package com.doushi.test.myproject.ui.main.recommend.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.doushi.library.widgets.vb.BaseBannerAdapter
import com.doushi.library.widgets.vb.VerticalBannerView
import com.doushi.test.myproject.R

/**
 *
 *@author xiemy1
 *@date 2019/6/24
 */
class SearchBannerAdapter(banners: List<String>) : BaseBannerAdapter<String>(banners) {

    override fun getView(parent: VerticalBannerView?): View {
        val pView = View.inflate(parent?.context, R.layout.item_home_search, null)
        pView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return pView
    }

    override fun setItem(view: View?, data: String?) {
        if (view is TextView) {
            view.text = data
        }
        //view?.findViewById<TextView>(R.id.tvHomeSearch)?.text =
        //view?.context?.getString(R.string.invite_friend_award, data?.nickname, data?.reward_value)
    }
}