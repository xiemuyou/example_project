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
import com.news.example.myproject.widgets.refresh.RefreshFootView;
import com.news.example.myproject.znet.InterfaceConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 刷新数据 Activity 基类
 * <p>数据上拉加载,下拉刷新</p>
 * 没有太完善,还需要优化,建议使用Fragment继承 BaseRefreshFragment (2018/3/16)
 *
 * @author xiemy
 * @date 2018/2/28.
 */
public abstract class BaseRefreshActivity<T> extends BaseActivity implements
        CanRefreshLayout.OnRefreshListener,
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

    private RecyclerView.Adapter mAdapter;
    private List<T> mDataList;

    /**
     * 数据数量
     */
    public final static int CNT = Constants.CNT_NUMBER;
    /**
     * 当前页
     */
    public int page = 0;
    private LoadDataState loadState;

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.view_refresh;
    }

    @Override
    public void initEnv() {
        mDataList = new ArrayList<>();
        OtherViewHolder otherHolder = new OtherViewHolder(this);
        otherHolder.setOnEmptyRetryListener(() -> {
            page = 0;
            refreshDataList();
        });
        ovHintView.setHolder(otherHolder);
        ovHintView.showLoadingView();

        mAdapter = getRefreshAdapter(mDataList);
        canContentView.setAdapter(mAdapter);

        //添加下拉刷新
        refresh.setOnRefreshListener(this);
        //禁止下拉加载
        refresh.setLoadMoreEnabled(false);
        //添加到底提示
        footer.attachTo(canContentView, false);
        //到底自动加载
        footer.setLoadMoreListener(this);
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

    public void loadDataSuccess(List<T> dataList) {
        refresh.setVisibility(View.VISIBLE);
        ovHintView.showContentView();
        int getSize = 0;

        if (this.mDataList == null) {
            this.mDataList = new ArrayList<>();
        }
        if (page == 0) {
            this.mDataList.clear();
        }

        if (ObjectUtils.isNotEmpty(dataList)) {
            getSize = dataList.size();
            this.mDataList.addAll(dataList);
        }
        mAdapter.notifyDataSetChanged();
        loadState = LoadDataState.SUCCESS;
        loadComplete(getSize);
    }

    @Override
    public void loadDataFail(InterfaceConfig.HttpHelperTag apiTag, String errorInfo) {
        loadState = LoadDataState.LOAD_FAIL;
        loadComplete(0);
    }

    /**
     * 加载成功
     */
    private void loadComplete(int currentSize) {
        footer.setVisibility(View.GONE);
        loadState = RefreshStateUtil.getLoadState(loadState, currentSize, mDataList.size(), CNT);
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
