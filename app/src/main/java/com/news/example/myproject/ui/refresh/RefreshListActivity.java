package com.news.example.myproject.ui.refresh;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

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
import com.news.example.myproject.model.video.VideoDetails;
import com.news.example.myproject.ui.refresh.rp.RefreshPresenter;
import com.news.example.myproject.ui.refresh.rv.RefreshListView;

import java.util.List;

/**
 * 刷新数据 Activity
 * <p>数据上拉加载,下拉刷新</p>
 *
 * @author xiemy
 * @date 2018/3/1.
 */
public class RefreshListActivity extends BaseRefreshActivity<UserInfo> implements RefreshListView {

    private RefreshPresenter refreshPresenter;
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
                new ImageLoadUtils(RefreshListActivity.this).commonDisplayImage(item.getAvatarUrl(), ivVideoBg, DefaultValue.BACKGROUND);

                ImageView ivHead = helper.getView(R.id.ivUserAvatar);
                new ImageLoadUtils(RefreshListActivity.this).commonCircleImage(item.getAvatarUrl(), ivHead, DefaultValue.HEAD);
            }
        };
        refreshAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                toPage(FollowActivity.class);
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
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
            refreshPresenter = new RefreshPresenter(this);
        }
        final String searchKey = TextUtils.isEmpty(searchText) ? searchText : "1";
        canContentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshPresenter.getSearchUsers(searchKey);
            }
        }, 2000);
    }

    @Override
    public void getDataSuccess(RecommendResponse dataRes) {
        //数据为空也需要传NULL值,
//        List<UserInfo> dataList = ObjectUtils.isNotEmpty(dataRes.getData()) ? dataRes.getData().getUser_list() : null;
//        loadDataSuccess(dataList);
    }

    @Override
    public void getVideoListSuccess(List<VideoDetails> videoList) {

    }
}
