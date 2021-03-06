package com.news.example.myproject.base.component;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.library.widgets.canrefresh.CanRecyclerViewHeaderFooter;
import com.library.widgets.canrefresh.CanRefreshLayout;
import com.library.widgets.canrefresh.LoadDataState;
import com.library.widgets.canrefresh.RefreshStateUtil;
import com.library.widgets.emptyview.EmptyEnum;
import com.library.widgets.emptyview.OtherView;
import com.library.widgets.emptyview.OtherViewHolder;
import com.news.example.myproject.R;
import com.news.example.myproject.base.mvp.BaseView;
import com.news.example.myproject.global.Constants;
import com.news.example.myproject.model.sort.SortFilter;
import com.library.widgets.canrefresh.RefreshFootView;
import com.news.example.myproject.znet.InterfaceConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 数据刷新Fragment基类
 *
 * @author xiemy
 * @date 2018/3/2
 */
public abstract class BaseRefreshFragment<T> extends BaseLazyFragment
        implements CanRefreshLayout.OnRefreshListener,
        CanRefreshLayout.OnLoadMoreListener,
        CanRecyclerViewHeaderFooter.OnLoadMoreListener, BaseView {

    /**
     * 数据容器
     */
    @BindView(R.id.can_content_view)
    public RecyclerView canContentView;
    /**
     * 刷新控件
     */
    @BindView(R.id.refresh)
    public CanRefreshLayout refresh;
    @BindView(R.id.footer)
    RefreshFootView footer;

    /**
     * 空白显示
     */
    @BindView(R.id.ovEmptyHint)
    public OtherView ovHintView;
    /**
     * 适配器
     */
    private RecyclerView.Adapter adapter;
    /**
     * 数据列表
     */
    private List<T> mDataList;
    /**
     * 数据数量
     */
    public final static int CNT = Constants.CNT_NUMBER;
    /**
     * 当前页
     */
    public int page = 0;
    /**
     * 加载数据状态
     */
    private LoadDataState loadState;

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.view_refresh;
    }

    @Override
    public void initEnv() {
        mDataList = new ArrayList<>();
        OtherViewHolder otherHolder = new OtherViewHolder(_mActivity);
        otherHolder.setOnEmptyRetryListener(() -> {
            page = 0;
            refreshDataList();
        });
        ovHintView.setHolder(otherHolder);
        ovHintView.showLoadingView();

        adapter = getRefreshAdapter(mDataList);
        canContentView.setAdapter(adapter);

        //添加下拉刷新
        refresh.setOnRefreshListener(this);
        //禁止下拉加载
        refresh.setLoadMoreEnabled(false);
        //添加到底提示
        footer.attachTo(canContentView, false);
        //到底自动加载
        footer.setLoadMoreListener(this);
        footer.setTryAgainClickListener((view) -> refreshDataList());
    }

    @Override
    public void initLazyEnv() {
        refreshDataList();
    }

    /**
     * 设置数据适配器
     *
     * @param dataList 数据列表
     * @return 数据适配器
     */
    public abstract RecyclerView.Adapter getRefreshAdapter(List<T> dataList);

    /**
     * 刷新数据
     */
    public abstract void refreshDataList();

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    @Override
    public void onLoadMore() {
        page++;
        refreshDataList();
        footer.setFootState(LoadDataState.LOAD_MORE);
    }

    @Override
    public void onRefresh() {
        page = 0;
        refreshDataList();
    }

    /**
     * 回到顶部自动刷新
     */
    public void placedTopAutoRefresh() {
        if (refresh == null || canContentView == null) {
            return;
        }
        canContentView.scrollToPosition(0);
        refresh.autoRefresh();
    }

    public void loadDataSuccess(List<T> dataList) {
        refresh.setVisibility(View.VISIBLE);
        ovHintView.showContentView();
        int getSize = 0;

        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        if (page == 0) {
            mDataList.clear();
        }
        dataList = dataToHeavy(dataList);
        if (ObjectUtils.isNotEmpty(dataList)) {
            getSize = dataList.size();
            mDataList.addAll(dataList);
        }
        adapter.notifyDataSetChanged();
        loadState = LoadDataState.SUCCESS;
        loadComplete(getSize);
    }

    private List<T> dataToHeavy(List<T> dataList) {
        if (mDataList == null) {
            return dataList;
        }
        return SortFilter.INSTANCE.dataToHeavy(mDataList, dataList);
    }

    @Override
    public void loadDataFail(InterfaceConfig.HttpHelperTag apiTag, String errorInfo) {
        loadState = LoadDataState.LOAD_FAIL;
        loadComplete(0);
    }

    /**
     * 加载完成
     *
     * @param currentSize 当前加载数据大小,加载错误为0
     */
    private void loadComplete(int currentSize) {
        footer.setVisibility(View.GONE);
        int dataSize = 0;
        if (mDataList != null) {
            dataSize = mDataList.size();
        }
        loadState = RefreshStateUtil.getLoadState(loadState, currentSize, dataSize, Constants.CNT_NUMBER);
        switch (loadState) {
            case EMPTY:
                ovHintView.showEmptyView();
                page = 0;
                break;

            case LOAD_COMPLETE:
                if (page > 0) {
                    page--;
                }
                break;

            case LOAD_MORE_FAIL:
                if (page > 0) {
                    page--;
                }
                break;

            case LOAD_FAIL:
                ovHintView.showEmptyView(EmptyEnum.NetEmpty);
                if (page > 0) {
                    page--;
                }
                break;

            default:
                break;
        }

        footer.setFootState(loadState);
        refresh.setLoadMoreEnabled(false);
        refresh.loadMoreComplete();
        refresh.refreshComplete();
        footer.loadMoreComplete();
    }
}
