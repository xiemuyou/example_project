package com.news.example.myproject.ui.search;

import android.os.Bundle;

import com.news.example.myproject.base.component.BaseActivity;

public class SearchMoreUserActivity extends BaseActivity {
    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public void initEnv() {

    }
}
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.doushi.bean.R;
//import com.doushi.bean.base.BaseActivity;
//import com.doushi.bean.global.Constants;
//import com.doushi.bean.model.user.UserInfo;
//import com.doushi.bean.page.search.adapter.MoreUserAdapter;
//import com.doushi.bean.page.search.presenter.MoreUserPresenter;
//import com.doushi.bean.page.search.view.MoreUserView;
//import com.doushi.bean.player.VideoRecyclerView;
//import com.doushi.bean.widgets.layoutManager.CustomLinearLayoutManager;
//import com.doushi.bean.widgets.layoutManager.DividerLine;
//import com.doushi.library.widgets.canrefresh.CanRecyclerViewHeaderFooter;
//import com.doushi.library.widgets.canrefresh.CanRefreshLayout;
//import com.doushi.library.widgets.canrefresh.ClassicRefreshView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * 搜索更多用户
// * Created by xiemy on 2017/5/21.
// */
//public class SearchMoreUserActivity extends BaseActivity implements
//        MoreUserView,
//        CanRecyclerViewHeaderFooter.OnLoadMoreListener {
//
//    @BindView(R.id.can_content_view)
//    VideoRecyclerView canContentView;
//    @BindView(R.id.pbFootLoading)
//    ProgressBar pbFootLoading;
//    @BindView(R.id.tvFootLoadMore)
//    TextView tvFootLoadMore;
//    @BindView(R.id.footer)
//    CanRecyclerViewHeaderFooter footer;
//    @BindView(R.id.refresh)
//    CanRefreshLayout refresh;
//
//    @BindView(R.id.tvSearchStr)
//    TextView tvSearchStr;
//    @BindView(R.id.tvSearchCount)
//    TextView tvSearchCount;
//
//    //数据数量
//    private int cnt = Constants.CNT_NUMBER;
//    //当前页
//    private int page = 0;
//    private List<UserInfo> moreUserInfoList;
//    private String inputStr;
//    private int uCount;
//    private MoreUserPresenter userPresenter;
//    private MoreUserAdapter moreUserAdapter;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_search_more_user;
//    }
//
//    @Override
//    public void initEnv() {
//        hindHead();
//        initData();
//
//        tvSearchStr.setText(inputStr);
//        tvSearchCount.setText(getString(R.string.search_user_count, String.valueOf(uCount)));
//
//        refresh.setRefreshEnabled(false);
//        refresh.setLoadMoreEnabled(false);
//        CustomLinearLayoutManager cllManager = new CustomLinearLayoutManager(
//                this, LinearLayoutManager.VERTICAL, false);
//        canContentView.setLayoutManager(cllManager);
//
//        DividerLine divider = new DividerLine(RecyclerView.VERTICAL);
//        divider.setColor(getResources().getColor(R.color.search_bg_color));
//        divider.setSize(1);
//        canContentView.addItemDecoration(divider);
//
//        moreUserAdapter = new MoreUserAdapter(this, moreUserInfoList, inputStr);
//        canContentView.setAdapter(moreUserAdapter);
//
//        footer.setBackgroundColor(getResources().getColor(R.color.white));
//        //设置到底自动加载
//        footer.attachTo(canContentView, false);
//        footer.setLoadMoreListener(this);
//        footer.setVisibility(View.GONE);
//        userPresenter.getSearchedUsers(page, cnt, inputStr);
//    }
//
//    private void initData() {
//        Intent intent = getIntent();
//        if (intent != null) {
//            moreUserInfoList = (List<UserInfo>) intent.getSerializableExtra(Constants.USER_LIST);
//            inputStr = intent.getStringExtra(Constants.COMMON_STR);
//            uCount = intent.getIntExtra(Constants.CNT, 0);
//        }
//        if (moreUserInfoList == null) {
//            moreUserInfoList = new ArrayList<>();
//        }
//        userPresenter = new MoreUserPresenter(this);
//    }
//
//    private void refreshSearchedUserList() {
//        if (!TextUtils.isEmpty(inputStr)) {
//            userPresenter.getSearchedUsers(page, cnt, inputStr);
//        }
//    }
//
//    @Override
//    public void onLoadMore() {
//        if (!TextUtils.isEmpty(inputStr)) {
//            page++;
//            refreshSearchedUserList();
//        }
//    }
//
//    @Override
//    public void getMoreUserSuccess(List<UserInfo> userList) {
//        if (userList != null && userList.size() > 0) {
//            if (null == moreUserInfoList) {
//                moreUserInfoList = new ArrayList<>(userList.size());
//            } else if (page == 0) {
//                moreUserInfoList.clear();
//            }
//            moreUserInfoList.addAll(userList);
//            moreUserAdapter.notifyDataSetChanged();
//            if (moreUserInfoList.size() < 9) {
//                footer.setLoadEnable(false);
//                pbFootLoading.setVisibility(View.GONE);
//                tvFootLoadMore.setVisibility(View.GONE);
//                footer.setBackgroundColor(getResources().getColor(R.color.white));
//            } else if (userList.size() < cnt) {
//                showNoMorePage(true);
//            } else {
//                showNoMorePage(false);
//            }
//        } else {
//            if (page == 0) {
//                canContentView.setVisibility(View.GONE);
//            } else {
//                page--;
//                showNoMorePage(true);
//            }
//        }
//        refresh.setLoadMoreEnabled(false);
//        refresh.loadMoreComplete();
//        footer.loadMoreComplete();
//    }
//
//    @Override
//    public void loadDataFail(String errorInfo) {
//        showNotice(errorInfo);
//    }
//
//    /**
//     * 设置显示加载更多
//     *
//     * @param flag 是否显示
//     */
//    private void showNoMorePage(boolean flag) {
//        if (flag) {
//            footer.setLoadEnable(false);
//            pbFootLoading.setVisibility(View.GONE);
//        } else {
//            footer.setLoadEnable(true);
//            pbFootLoading.setVisibility(View.VISIBLE);
//            tvFootLoadMore.setVisibility(View.GONE);
//        }
//    }
//
//    @OnClick(R.id.ivSearchUserBack)
//    public void onViewClicked() {
//        closePage();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == MoreUserAdapter.ATTENTION_USER) {
//            page = 0;
//            refreshSearchedUserList();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (userPresenter != null) {
//            userPresenter.detachView();
//        }
//        if (moreUserAdapter != null) {
//            moreUserAdapter.detachViews();
//        }
//    }
//
//}
