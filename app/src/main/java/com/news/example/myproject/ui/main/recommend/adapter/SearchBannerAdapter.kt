package com.news.example.myproject.ui.main.recommend.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.library.widgets.vb.BaseBannerAdapter
import com.library.widgets.vb.VerticalBannerView
import com.news.example.myproject.R
import com.news.example.myproject.ui.search.SearchActivity

/**
 *
 *@author xiemy1
 *@date 2019/6/24
 */
class SearchBannerAdapter(banners: List<String>) : BaseBannerAdapter<String>(banners) {

    override fun getView(parent: VerticalBannerView?): View {
        val pView = View.inflate(parent?.context, R.layout.item_home_search, null)
        pView?.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return pView
    }

    override fun setItem(view: View?, item: String?) {
        (view as? TextView)?.text = item
        view?.setOnClickListener {
            //  val searchList = if (mDataList is ArrayList<String>) mDataList else ArrayList<String>
            SearchActivity.showClass(mDataList as ArrayList<String>, item ?: "")
        }
    }
}