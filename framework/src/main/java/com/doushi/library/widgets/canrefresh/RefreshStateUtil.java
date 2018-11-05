package com.doushi.library.widgets.canrefresh;

/**
 * 获取刷新状态工具类
 *
 * @author xiemy
 * @date 2018/3/9
 */
public class RefreshStateUtil {

    /**
     * 下拉刷新,上拉加载更多.获取加载类型供显示使用
     *
     * @param state       成功/失败 LoadDataState.SUCCESS/LoadDataState.LOAD_FAIL
     * @param currentSize 当前请求加载数
     * @param dataSize    加载总数
     * @param cnt         一次请求数
     * @return 加载数据状态<p>根据不同状态显示不同的页面<p/>
     */
    public static LoadDataState getLoadState(LoadDataState state, int currentSize, int dataSize, int cnt) {
        if (state == LoadDataState.SUCCESS) {
            if (dataSize > 0) {
                //小于请求数,表示加载完成.否则可以继续加载更多
                state = currentSize < cnt ? LoadDataState.LOAD_COMPLETE : LoadDataState.LOAD_MORE;
            } else {
                //空页面
                state = LoadDataState.EMPTY;
            }
            //加载失败
        } else if (state == LoadDataState.LOAD_FAIL) {
            if (dataSize > 0) {
                //加载更多失败
                state = LoadDataState.LOAD_MORE_FAIL;
            }
        }
        return state;
    }
}
