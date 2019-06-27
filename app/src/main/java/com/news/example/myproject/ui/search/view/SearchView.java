package com.news.example.myproject.ui.search.view;


import com.news.example.myproject.base.mvp.BaseView;

/**
 * 搜索
 *
 * @author xiemy
 * @date 2017/5/19
 */
public interface SearchView extends BaseView {

    /**
     * 成功获取搜索数据
     *
     * @param res html 字符串
     */
    void getSearchSuccess(String res);

    /**
     * 更新历史搜索
     */
    void notifyChangedHistorySearchKey();
}
