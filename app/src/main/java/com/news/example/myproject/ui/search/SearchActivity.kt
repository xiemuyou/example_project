package com.news.example.myproject.ui.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.library.util.ViewUtil
import com.library.widgets.canrefresh.CanRecyclerViewHeaderFooter
import com.news.example.myproject.R
import com.news.example.myproject.base.component.BaseActivity
import com.news.example.myproject.global.Constants
import com.news.example.myproject.global.ParamConstants
import com.news.example.myproject.model.base.BaseApiResponse
import com.news.example.myproject.model.base.BaseMultiItemEntity
import com.news.example.myproject.model.user.UserInfo
import com.news.example.myproject.model.video.VideoDetails
import com.news.example.myproject.ui.search.adapter.SearchAdapter
import com.news.example.myproject.ui.search.presenter.SearchPresenter
import com.news.example.myproject.ui.search.view.SearchView
import com.news.example.myproject.widgets.layoutmanager.CustomLinearLayoutManager
import com.news.example.myproject.widgets.layoutmanager.DividerLine
import com.news.example.myproject.znet.InterfaceConfig
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.view_foot_more_loading.*
import kotlinx.android.synthetic.main.view_refresh.*

class SearchActivity : BaseActivity(), SearchView, CanRecyclerViewHeaderFooter.OnLoadMoreListener {

    /**
     * 热门搜索
     */
    private var hotSearchList: ArrayList<String>? = null

    /**
     * 搜索关键词
     */
    private var searchKey: String? = null

    private val marginSize = SizeUtils.dp2px(6F)
    private val rowWidth = (ScreenUtils.getScreenWidth() - marginSize * 3) / 2
    private var cllManager: CustomLinearLayoutManager? = null

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
        return R.layout.activity_search
    }

    override fun initEnv() {
        initData()
        initHistoryView()
        initView()
    }

    private fun initData() {
        hotSearchList = intent?.getStringArrayListExtra(ParamConstants.DATA_LIST)
        searchKey = intent?.getStringExtra(ParamConstants.SEARCH_KEY)
    }

    private fun initHistoryView() {
        val searchHistoryList = hotSearchList
        //searchPresenter.searchHistoryList
        if (searchHistoryList?.isNotEmpty() == true) {
            val hSize = searchHistoryList.size
            var tableRow = TableRow(this@SearchActivity)
            for (i in 0 until hSize) {
                val historyView = LayoutInflater.from(this@SearchActivity).inflate(R.layout.view_search_history, null)
                val tvHistory = historyView.findViewById<TextView>(R.id.tvSearchHistory)
                tvHistory.text = searchHistoryList[i]
                historyView.layoutParams = TableRow.LayoutParams(rowWidth, RecyclerView.LayoutParams.WRAP_CONTENT)
                tvHistory.setOnClickListener(searchHistoryListener)
                tableRow.addView(tvHistory)
                ViewUtil.setMargins(tvHistory, if (i % 2 != 0) marginSize else 0, marginSize, 0, 0)
                val needAddView = (i % 2 != 0 || i == hSize - 1 && hSize % 2 != 0)
                if (needAddView) {
                    tlSearchHistory.addView(tableRow)
                    tableRow = TableRow(this@SearchActivity)
                }
            }
        }
    }

    private fun initView() {
        refresh.setLoadMoreEnabled(false)
        refresh.setRefreshEnabled(false)
        cllManager = CustomLinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
        can_content_view.layoutManager = cllManager
        val divider = DividerLine(RecyclerView.VERTICAL)
        divider.setColor(ContextCompat.getColor(this@SearchActivity, R.color.color_E8E8E8))
        divider.setSize(1)
        can_content_view.addItemDecoration(divider)
        searchDataList = java.util.ArrayList()
        searchAdapter = SearchAdapter(this, searchDataList)
//        searchAdapter.setFullscreenBack(canContentView, object : PlaybackControlLayer.FullscreenCallback() {
//            fun onGoToFullscreen(originalPos: IntArray) {
//                rlSearchInputTop.visibility = View.GONE
//                ivSearchTopLine.visibility = View.GONE
//                llMoreHistory.visibility = View.GONE
//                ivMoreVideoBack.visibility = View.VISIBLE
//                can_content_view.scrollBy(0, originalPos[1] - (statusBarHeight + rlSearchInputTop.height + 1))
//                cllManager?.setSpeedRatio(0.0)
//            }
//
//            fun onReturnFromFullscreen() {
//                rlSearchInputTop.visibility = View.VISIBLE
//                ivSearchTopLine.visibility = View.VISIBLE
//                ivMoreVideoBack.visibility = View.GONE
//                cllManager?.setSpeedRatio(1.0)
//            }
//        })
//        searchAdapter.setOnMoreUserListener(View.OnClickListener {
//            val bundle = Bundle()
//            bundle.putSerializable(Constants.USER_LIST, moreUserList as Serializable)
//            bundle.putString(Constants.COMMON_STR, etSearchInput.text.toString())
//            bundle.putInt(Constants.CNT, uCount)
//            toPage(SearchMoreUserActivity::class.java, bundle)
//        })
        can_content_view.adapter = searchAdapter
//        can_content_view.setOnVideoPlayerListener(searchAdapter)

        footer.setBackgroundColor(ContextCompat.getColor(this@SearchActivity, R.color.white))
        //设置到底自动加载
        footer.attachTo(can_content_view, false)
        footer.setLoadMoreListener(this)
    }

    private val searchHistoryListener = View.OnClickListener {
        if (it is TextView) {
            etSearchInput.setText(it.text)
            if (check()) {
                pbDefaultLoading.visibility = View.VISIBLE
                tlSearchHistory.visibility = View.GONE
                searchPresenter.search(it.text.toString())
            }
        }
    }

    private fun check(): Boolean {
        return true
    }

    override fun onLoadMore() {
        //    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            if (check()) {
                searchStr = etSearchInput.text.toString()
                llMoreHistory.visibility = View.GONE
                pbDefaultLoading.visibility = View.VISIBLE
                if (searchPresenter.search(searchStr)) {
                    searchPresenter.attachView(this)
                }
                return false
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun searchDataList(searchRes: BaseApiResponse<*>?) {

    }

    override fun loadDataFail(apiTag: InterfaceConfig.HttpHelperTag?, errorInfo: String?) {
        showNotice(errorInfo)
    }

    override fun getSearchedVideoSuccess(searchVideoList: MutableList<VideoDetails>?) {
    }
}

private var searchDataList: List<BaseMultiItemEntity>? = null
private val moreUserList: List<UserInfo>? = null
private var searchAdapter: SearchAdapter? = null
//    private CustomLinearLayoutManager cllManager;

//视频项网络请求实现类
//    private VideoItemPresenter itemPresenter;
private val USER_LIST_SIZE = 3
//数据数量
private val cnt = Constants.CNT_NUMBER
//当前页
private val page = 0
//搜索到用户数量
private val uCount: Int = 0
private val searchInput: String? = null

private var searchStr: String? = null
//
//    private int rowWidth = (Screen.getInstance().widthPixels - marginSize * 3) / 2;


//    @Override
//    fun getLayoutId(save: Bundle): Int {
//在进入有VideoView界面的Activity时会出现闪黑屏的情况 ,http://blog.5ibc.net/p/78526.html
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
//        return 0 //R.layout.activity_search;
//    }

//    @Override
//    fun initEnv() {
//        hindHead();
//        itemPresenter = new VideoItemPresenter();
//        searchPresenter = new SearchPresenter(this);
//        etSearchInput.setOnKeyListener(this);
//        initHistoryView();
//        initView();
//    }

//    private fun initView() {
//        refresh.setLoadMoreEnabled(false);
//        refresh.setRefreshEnabled(false);
//
//        cllManager = new CustomLinearLayoutManager(
//                this, LinearLayoutManager.VERTICAL, false);
//
//        canContentView.setLayoutManager(cllManager);
//        DividerLine divider = new DividerLine(RecyclerView.VERTICAL);
//        divider.setColor(getResources().getColor(R.color.common_horizontal_line_color));
//        divider.setSize(1);
//        canContentView.addItemDecoration(divider);
//        searchDataList = new ArrayList<>();
//        searchAdapter = new SearchAdapter1(this, searchDataList, itemPresenter, new OnRewardGiftsListener() {
//            @Override
//            public void onRewardGifts(RewardGifts gifts) {
//                llRewardGiftContent.setVisibility(View.VISIBLE);
//                llRewardGiftContent.startRewardGiftAnimation(gifts, new RewardGiftsLinearLayout.OnRewardAnimationEndListener() {
//                    @Override
//                    public void onRewardAnimationEnd(RewardGifts gifts) {
//                        handler.sendEmptyMessage(REWARD_GIFTS_END);
//                    }
//                });
//            }
//        });
//        searchAdapter.setFullscreenBack(canContentView, new PlaybackControlLayer.FullscreenCallback() {
//            @Override
//            public void onGoToFullscreen(int[] originalPos) {
//                rlSearchInputTop.setVisibility(View.GONE);
//                ivSearchTopLine.setVisibility(View.GONE);
//                llMoreHistory.setVisibility(View.GONE);
//                ivMoreVideoBack.setVisibility(View.VISIBLE);
//                canContentView.scrollBy(0, originalPos[1] - (getStatusBarHeight() + rlSearchInputTop.getHeight() + 1));
//                cllManager.setSpeedRatio(0);
//            }
//
//            @Override
//            public void onReturnFromFullscreen() {
//                rlSearchInputTop.setVisibility(View.VISIBLE);
//                ivSearchTopLine.setVisibility(View.VISIBLE);
//                ivMoreVideoBack.setVisibility(View.GONE);
//                cllManager.setSpeedRatio(1);
//            }
//        });
//        searchAdapter.setOnMoreUserListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(Constants.USER_LIST, (Serializable) moreUserList);
//                bundle.putString(Constants.COMMON_STR, etSearchInput.getText().toString());
//                bundle.putInt(Constants.CNT, uCount);
//                toPage(SearchMoreUserActivity.class, bundle);
//            }
//        });
//        canContentView.setAdapter(searchAdapter);
//        canContentView.setOnVideoPlayerListener(searchAdapter);
//
//        footer.setBackgroundColor(getResources().getColor(R.color.white));
//        //设置到底自动加载
//        footer.attachTo(canContentView, false);
//        footer.setLoadMoreListener(this);
//    }
//
//    @Override
//    public void onLoadMore() {
//        if (check()) {
//            page++;
//            searchPresenter.getSearchedVideos(page, cnt, etSearchInput.getText().toString());
//        }
//    }
//
//    /**
//     * 监听软件盘Search点击
//     */
//    @Override
//    public boolean onKey(View v, int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_ENTER) {
//            if (check()) {
//                searchStr = etSearchInput.getText().toString();
//                llMoreHistory.setVisibility(View.GONE);
//                pbDefaultLoading.setVisibility(View.VISIBLE);
//                if (searchPresenter.search(searchStr)) {
//                    searchPresenter.attachView(this);
//                }
//            }
//        }
//        return false;
//    }
//
//    private boolean check() {
//        searchInput = etSearchInput.getText().toString();
//        if (TextUtils.isEmpty(searchInput)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public void loadDataFail(String errorInfo) {
//        showNotice(errorInfo, ToastUtils.ToastType.ERROR_TYPE);
//    }
//
//    @Override
//    public void searchDataList(SearchResponse searchRes) {
//        searchPresenter.saveSearchHistory(searchStr);
//        hideSoftKeyboard();
//        llMoreHistory.setVisibility(View.GONE);
//        tlSearchHistory.setVisibility(View.GONE);
//        pbDefaultLoading.setVisibility(View.GONE);
//        if (searchDataList == null) {
//            searchDataList = new ArrayList<>();
//        }
//        searchDataList.clear();
//
//        boolean showMoreUser = false;
//        boolean isLoadMore = true;
//        //添加用户列表
//        if (searchRes.getSearchedUserCnt() > 0) {
//            if (searchRes.getSearchedUserCnt() > USER_LIST_SIZE
//                    && searchRes.getUserInfoList() != null) {
//                searchDataList.addAll(getSearchUserInfoList(searchRes));
//                showMoreUser = true;
//            } else {
//                searchDataList.addAll(searchRes.getUserInfoList());
//            }
//            uCount = searchRes.getSearchedUserCnt();
//            moreUserList = searchRes.getUserInfoList();
//        }
//        //添加视频列表
//        if (searchRes.getSearchedVideoCnt() > 0) {
//            //添加头部显示
//            searchDataList.add(searchRes.getSearchedVideoCnt());
//            searchDataList.addAll(searchRes.getVideoInfoList());
//            isLoadMore = (searchRes.getSearchedVideoCnt() < searchRes.getVideoInfoList().size());
//        }
//        if (!searchDataList.isEmpty()) {
//            canContentView.setVisibility(View.VISIBLE);
//            tvSearchEmpty.setVisibility(View.GONE);
//            searchAdapter.updateDataList(searchDataList, showMoreUser, searchInput);
//            refresh.setLoadMoreEnabled(false);
//            showNoMorePage(isLoadMore);
//        } else {
//            llMoreHistory.setVisibility(View.GONE);
//            canContentView.setVisibility(View.GONE);
//            tvSearchEmpty.setVisibility(View.VISIBLE);
//            showNoMorePage(false);
//        }
//        networkRequest();
//    }
//
//    @Override
//    public void getSearchedVideoSuccess(List<VideoDetails> searchVideoList) {
//        hideSoftKeyboard();
//        pbDefaultLoading.setVisibility(View.GONE);
//        int newSize = 0;
//        if (searchVideoList != null && searchVideoList.size() > 0) {
//            newSize = searchVideoList.size();
//            searchDataList.addAll(searchVideoList);
//            searchAdapter.notifyDataSetChanged();
//        }
//
//        refresh.setLoadMoreEnabled(false);
//        showNoMorePage(newSize < cnt);
//        networkRequest();
//        pbDefaultLoading.setVisibility(View.GONE);
//    }
//
//    /**
//     * 获取搜索服务器返回用户列表
//     *
//     * @param searchRes 服务器返回数据
//     * @return 用户列表不为空大于3, 返回3/返回null
//     */
//    private List<UserInfo> getSearchUserInfoList(SearchResponse searchRes) {
//        List<UserInfo> userInfoList = new ArrayList<>(USER_LIST_SIZE);
//        for (int i = 0; i < USER_LIST_SIZE; i++) {
//            userInfoList.add(searchRes.getUserInfoList().get(i));
//        }
//        return userInfoList;
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
//    /**
//     * 请求完成
//     */
//    private void networkRequest() {
//        refresh.loadMoreComplete();
//        footer.loadMoreComplete();
//    }
//
//    @OnClick({R.id.tvSearchCancel, R.id.ivSearchHistoryEmpty, R.id.ivMoreVideoBack})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tvSearchCancel:
//                closePage();
//                break;
//
//            case R.id.ivSearchHistoryEmpty:
//                searchPresenter.removeSearchHistory();
//                tlSearchHistory.setVisibility(View.GONE);
//                break;
//
//            case R.id.ivMoreVideoBack:
//                if (PlaybackControlLayer.isFullscreen && searchAdapter != null) {
//                    searchAdapter.setFullscreen(false);
//                }
//                break;
//        }
//    }
//
//    private final int REWARD_GIFTS_END = 1;
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == REWARD_GIFTS_END) {
//                llRewardGiftContent.setVisibility(View.GONE);
//            }
//        }
//    };
//
//    @Override
//    public void onBackPressed() {
//        if (PlaybackControlLayer.isFullscreen && searchAdapter != null) {
//            searchAdapter.setFullscreen(false);
//            return;
//        }
//        closePage();
//        super.onBackPressed();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == IntConstant.LOGIN) {
//                if (check()) {
//                    pbDefaultLoading.setVisibility(View.VISIBLE);
//                    searchPresenter.search(etSearchInput.getText().toString());
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (searchAdapter != null) {
//            searchAdapter.videoPause();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (searchAdapter != null) {
//            searchAdapter.videoRelease();
//            searchAdapter.notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (searchAdapter != null) {
//            searchAdapter.detachViews();
//            searchAdapter.videoRelease();
//        }
//    }
//    }
//}
