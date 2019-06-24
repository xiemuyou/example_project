package com.library.widgets.canrefresh;

/**
 * 加载数据状态
 *
 * @author xiemy
 * @date 2017/7/31.
 */
public enum LoadDataState {

    //空数据 enough
    EMPTY,
    //没有一页
    NO_ENOUGH_PAGE,
    //加载成功
    SUCCESS,
    //加载完成(没有更多数据)
    LOAD_COMPLETE,
    //加载完成(还有数据)
    LOAD_MORE,
    //加载失败
    LOAD_FAIL,
    //加载更多失败
    LOAD_MORE_FAIL
}
