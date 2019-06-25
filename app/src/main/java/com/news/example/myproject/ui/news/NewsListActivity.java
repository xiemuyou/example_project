package com.news.example.myproject.ui.news;

import android.text.TextUtils;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.library.util.ImageLoadUtils;
import com.library.widgets.layoutManager.DividerItemDecoration;
import com.news.example.myproject.R;
import com.news.example.myproject.base.component.BaseRefreshActivity;
import com.news.example.myproject.global.DefaultValue;
import com.news.example.myproject.global.ParamConstants;
import com.news.example.myproject.model.news.RecommendResponse;
import com.news.example.myproject.model.user.UserInfo;
import com.news.example.myproject.ui.news.np.NewsListPresenter;
import com.news.example.myproject.ui.news.nv.NewsListView;
import com.news.example.myproject.ui.refresh.FollowActivity;

import java.util.List;

/**
 * 刷新数据 Activity
 * <p>数据上拉加载,下拉刷新</p>
 *
 * @author xiemy
 * @date 2018/3/1.
 */
public class NewsListActivity extends BaseRefreshActivity<UserInfo> implements NewsListView {

    private NewsListPresenter refreshPresenter;
    private String searchText;

    @Override
    public void initEnv() {
        searchText = getIntent().getStringExtra(ParamConstants.SEARCH_KEY);
        super.initEnv();
    }

    @Override
    public RecyclerView.Adapter getRefreshAdapter(List<UserInfo> dataList) {
        BaseQuickAdapter refreshAdapter = new BaseQuickAdapter<UserInfo, BaseViewHolder>(R.layout.item_user, dataList) {

            @Override
            protected void convert(BaseViewHolder helper, UserInfo item) {
                helper.setText(R.id.tvUserName, item.getName() + ":" + helper.getAdapterPosition());
                ImageView ivVideoBg = helper.getView(R.id.ivVideoBg);
                new ImageLoadUtils(NewsListActivity.this).commonDisplayImage(item.getAvatarUrl(), ivVideoBg, DefaultValue.BACKGROUND);

                ImageView ivHead = helper.getView(R.id.ivUserAvatar);
                new ImageLoadUtils(NewsListActivity.this).commonCircleImage(item.getAvatarUrl(), ivHead, DefaultValue.HEAD);
            }
        };
        refreshAdapter.setOnItemClickListener((adapter, view, position) -> toPage(FollowActivity.class));

        LinearLayoutManager llm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        canContentView.setLayoutManager(llm);
        DividerItemDecoration decoration = new DividerItemDecoration(this, 5, true,
                getResources().getColor(R.color.default_toast_bg));
        canContentView.addItemDecoration(decoration);
        canContentView.setAdapter(refreshAdapter);
        return refreshAdapter;
    }

    @Override
    public void refreshDataList() {
        if (refreshPresenter == null) {
            refreshPresenter = new NewsListPresenter(this);
        }
        final String searchKey = TextUtils.isEmpty(searchText) ? searchText : "1";
        canContentView.postDelayed(() -> refreshPresenter.getSearchUsers(searchKey), 2000);
    }

    @Override
    public void getDataSuccess(RecommendResponse dataRes) {
        //数据为空也需要传NULL值,
//        List<UserInfo> dataList = ObjectUtils.isNotEmpty(dataRes.getData()) ? dataRes.getData().getUser_list() : null;
//        loadDataSuccess(dataList);
    }
}
