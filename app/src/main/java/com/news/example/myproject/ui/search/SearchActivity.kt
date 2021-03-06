package com.news.example.myproject.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ActivityUtils
import com.library.util.PreferencesUtils
import com.library.widgets.emptyview.EmptyEnum
import com.library.widgets.emptyview.OtherViewHolder
import com.library.widgets.statusbar.StatusBarCompat
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseActivity
import com.news.example.myproject.global.ParamConstants
import com.news.example.myproject.ui.search.presenter.SearchPresenter
import com.news.example.myproject.ui.search.view.SearchView
import com.news.example.myproject.ui.web.CommonWebActivity
import com.news.example.myproject.widgets.flow.FlowLayout
import com.news.example.myproject.widgets.flow.MyTagAdapter
import com.news.example.myproject.widgets.flow.TagFlowLayout
import com.news.example.myproject.znet.InterfaceConfig
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.include_search_fast.*

class SearchActivity : BaseActivity(), SearchView,
        TagFlowLayout.OnTagClickListener, View.OnKeyListener {

    private var hotSearchList: ArrayList<String>? = null
    private var historySearch: ArrayList<String>? = null
    private var searchKey: String? = null
    private var searchHotKey: String? = null
    private var historyAdapter: MyTagAdapter? = null

    companion object {
        @JvmOverloads
        fun showClass(searchList: ArrayList<String> = ArrayList(), searchKey: String = "") {
            val bundle = Bundle()
            bundle.putStringArrayList(ParamConstants.DATA_LIST, searchList)
            bundle.putString(ParamConstants.SEARCH_KEY, searchKey)
            ActivityUtils.startActivity(bundle, SearchActivity::class.java)
        }
    }

    private val searchPresenter by lazy {
        SearchPresenter(this)
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white))
        return R.layout.activity_search
    }

    override fun initEnv() {
        initData()
        initView()
    }

    private fun initData() {
        historySearch = ArrayList()
        hotSearchList = intent?.getStringArrayListExtra(ParamConstants.DATA_LIST)
        searchHotKey = intent?.getStringExtra(ParamConstants.SEARCH_KEY)
        val searchKayHint = if (!TextUtils.isEmpty(searchHotKey))
            getString(R.string.search_ever_one, searchHotKey) else getString(R.string.search_hint)
        etSearchMsg?.hint = searchKayHint
        ivSearchClear?.visibility = View.GONE
    }

    private fun initView() {
        val ovHolder = OtherViewHolder(this@SearchActivity)
        ovHolder.setOnEmptyRetryListener {
            searchPresenter.search(searchKey)
        }
        ovEmptyHint?.setHolder(ovHolder)

        historyAdapter = historySearch?.let { MyTagAdapter(this@SearchActivity, it) }
        tflSearchHistory?.adapter = historyAdapter
        tflSearchHistory?.setOnTagClickListener(this@SearchActivity)

        if (hotSearchList?.size ?: 0 <= 0) {
            tflSearchHot.visibility = View.GONE
            tvHotSearch.visibility = View.GONE
        } else {
            val hotSearchAdapter = MyTagAdapter(this@SearchActivity, hotSearchList)
            tflSearchHot?.adapter = hotSearchAdapter
            tflSearchHot?.setOnTagClickListener(this)
        }

        etSearchMsg.addTextChangedListener(textWatcher)
        etSearchMsg.setOnKeyListener(this)
        notifyChangedHistorySearchKey()

        ivSearchRemoveHistory?.setOnClickListener {
            searchPresenter.removerHistorySearchKeys()
        }
        ivSearchClear?.setOnClickListener {
            etSearchMsg.setText("")
        }
        btSearchCancel?.setOnClickListener {
            ActivityUtils.finishActivity(this@SearchActivity)
        }
    }

    override fun notifyChangedHistorySearchKey() {
        historySearch?.clear()
        val hList = searchPresenter.getHistorySearchKeys()
        historySearch?.addAll(hList)
        if (historySearch?.size ?: 0 <= 0) {
            flSearchHistory.visibility = View.GONE
            tflSearchHistory?.visibility = View.GONE
        } else {
            flSearchHistory.visibility = View.VISIBLE
            tflSearchHistory?.visibility = View.VISIBLE
            historySearch?.reverse()
            historyAdapter?.notifyDataChanged()
            openSoftInput(ivSearchClear)
        }
    }

    override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
        if (parent === tflSearchHot) {
            searchKey = hotSearchList?.get(position)
            etSearchMsg.setText(searchKey)
            searchPresenter.search(searchKey)
            hideSoftKeyboard()

        } else if (parent === tflSearchHistory) {
            if (view is ImageView) {
                tflSearchHistory.setMaxLineLimit()
                tflSearchHistory.onChanged()
            } else {
                searchKey = historySearch?.get(position)
                etSearchMsg.setText(searchKey)
                searchPresenter.search(searchKey)
                hideSoftKeyboard()
            }
        }
        return true
    }

    private val textWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val matchStr = s.toString()
            val searchLen = matchStr.trim { it <= ' ' }.length
            if (searchLen > 0) {
                //将光标移至文字末尾
                etSearchMsg.setSelection(searchLen)
                ivSearchClear.visibility = View.VISIBLE
            } else if (TextUtils.isEmpty(matchStr)) {
                ovEmptyHint.showContentView()
                svFastSearch.visibility = View.VISIBLE
                ivSearchClear.visibility = View.GONE
            }
        }
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val etSearchKey = etSearchMsg.text.toString()
            if (!TextUtils.isEmpty(etSearchKey.trim { it <= ' ' })) {
                searchKey = etSearchKey
                searchPresenter.search(etSearchKey)
            } else if (etSearchKey.isEmpty()) {
                etSearchMsg.setText(searchHotKey)
                searchPresenter.search(searchHotKey)
            } else {
                etSearchMsg.setText("")
            }
            hideSoftKeyboard()
        }
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val etSearchKey = etSearchMsg.text.toString()
            if (!TextUtils.isEmpty(etSearchKey.trim { it <= ' ' })) {
                searchKey = etSearchKey
                searchPresenter.search(etSearchKey)
            } else if (etSearchKey.isEmpty()) {
                etSearchMsg.setText(searchHotKey)
                searchPresenter.search(searchHotKey)
            } else {
                etSearchMsg.setText("")
            }
            hideSoftKeyboard()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun getSearchSuccess(res: String?) {
        //返回不为空,缓存成功
        if (TextUtils.isEmpty(res)) {
            ovEmptyHint?.showEmptyView(EmptyEnum.SearchEmpty)
        } else {
            PreferencesUtils.setStringPreferences(ParamConstants.CONTENT, res)
            CommonWebActivity.showClass(webUrl = "", title = searchKey, contentKey = ParamConstants.CONTENT, diverFlag = true)
        }
    }

    override fun loadDataFail(apiTag: InterfaceConfig.HttpHelperTag?, errorInfo: String?) {
        showNotice(errorInfo)
        ovEmptyHint?.showEmptyView(EmptyEnum.NetEmpty)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchPresenter.saveHistorySearchKeys()
    }
}