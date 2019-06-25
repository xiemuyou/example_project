package com.news.example.myproject.ui.refresh;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.library.global.FConstants;
import com.library.util.ImageLoadUtils;
import com.library.widgets.layoutManager.DividerItemDecoration;
import com.news.example.myproject.R;
import com.news.example.myproject.base.component.BaseRefreshFragment;
import com.news.example.myproject.global.DefaultValue;
import com.news.example.myproject.global.ParamConstants;
import com.news.example.myproject.model.news.NewsInfo;
import com.news.example.myproject.model.news.RecommendResponse;
import com.news.example.myproject.model.video.VideoDetails;
import com.news.example.myproject.ui.news.NewsListActivity;
import com.news.example.myproject.ui.news.np.NewsListPresenter;
import com.news.example.myproject.ui.news.nv.NewsListView;

import java.util.List;

/**
 * @author xiemy
 * @date 2018/3/2
 */
public class FollowFragment extends BaseRefreshFragment<NewsInfo> implements NewsListView {

    private NewsListPresenter followPresenter;

    public static FollowFragment newInstance(int uid) {
        FollowFragment followFragment = new FollowFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FConstants.UID, uid);
        followFragment.setArguments(bundle);
        return followFragment;
    }

    @Override
    public RecyclerView.Adapter getRefreshAdapter(List<NewsInfo> followList) {
        LinearLayoutManager llm = new LinearLayoutManager(_mActivity, RecyclerView.VERTICAL, false);
        canContentView.setLayoutManager(llm);
        DividerItemDecoration decoration = new DividerItemDecoration(_mActivity, 5, true,
                getResources().getColor(R.color.default_toast_bg));
        canContentView.addItemDecoration(decoration);
        BaseQuickAdapter adapter = new BaseQuickAdapter<NewsInfo, BaseViewHolder>(R.layout.item_user, followList) {
            @Override
            protected void convert(BaseViewHolder helper, NewsInfo item) {
                helper.setText(R.id.tvUserName, item.getUserInfo().getName() + ":" + helper.getAdapterPosition());
                ImageView ivVideoBg = helper.getView(R.id.ivVideoBg);
                new ImageLoadUtils(_mActivity).commonDisplayImage(item.getImages().get(0), ivVideoBg, DefaultValue.BACKGROUND);

                ImageView ivHead = helper.getView(R.id.ivUserAvatar);
                new ImageLoadUtils(_mActivity).commonCircleImage(item.getUserInfo().getAvatarUrl(), ivHead, DefaultValue.HEAD);
            }
        };
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            VideoDetails info = (VideoDetails) adapter1.getItem(position);
            if (null == info) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString(ParamConstants.SEARCH_KEY, info.getUserInfo().getName());
            toPage(NewsListActivity.class, bundle);
        });
        return adapter;
    }

    @Override
    public void refreshDataList() {
        if (followPresenter == null) {
            followPresenter = new NewsListPresenter(this);
        }
        refresh.postDelayed(() -> followPresenter.getSearchUsers("测试"), 1000);
    }

    @Override
    public void getDataSuccess(RecommendResponse dataRes) {
        loadDataSuccess(dataRes.getData());
    }
}
