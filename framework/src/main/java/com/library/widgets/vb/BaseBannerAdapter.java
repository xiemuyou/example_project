package com.library.widgets.vb;

import android.view.View;

import com.library.util.VerificationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 * <p/>
 *
 * @author rowandjj(chuyi)<br />
 * @date 16/1/6<br/>
 * Time: 下午4:59<br/>
 */
@SuppressWarnings("unused")
public abstract class BaseBannerAdapter<T> {

    protected List<T> mDataList;
    private OnDataChangedListener mOnDataChangedListener;

    public BaseBannerAdapter(List<T> dataList) {
        mDataList = dataList;
        if (dataList == null || dataList.isEmpty()) {
            throw new RuntimeException("nothing to show");
        }
    }

    public BaseBannerAdapter(T[] dataList) {
        mDataList = new ArrayList<>(Arrays.asList(dataList));
    }

    /**
     * 设置banner填充的数据
     */
    public void setData(List<T> dataList) {
        //列表数据相等, 不做更新
        if (VerificationUtils.isListEquals(mDataList, dataList)) {
            return;
        }
        this.mDataList = dataList;
        notifyDataChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    private void notifyDataChanged() {
        mOnDataChangedListener.onChanged();
    }

    public T getItem(int position) {
        return mDataList.get(position);
    }

    /**
     * * 设置banner的样式
     *
     * @param parent 父控件
     * @return 父控件
     */
    public abstract View getView(VerticalBannerView parent);

    /**
     * 设置banner的数据
     *
     * @param view itemView
     * @param data banner列表数据
     */
    public abstract void setItem(View view, T data);

    interface OnDataChangedListener {
        /**
         * 切换监听
         */
        void onChanged();
    }
}