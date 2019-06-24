package com.news.example.myproject.ui.refresh;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.library.global.FConstants;
import com.library.util.ImageLoadUtils;
import com.library.widgets.layoutManager.DividerItemDecoration;
import com.news.example.myproject.R;
import com.news.example.myproject.base.component.BaseRefreshFragment;
import com.news.example.myproject.global.DefaultValue;
import com.news.example.myproject.global.ParamConstants;
import com.news.example.myproject.model.news.RecommendResponse;
import com.news.example.myproject.model.video.VideoDetails;
import com.news.example.myproject.ui.refresh.rp.RefreshPresenter;
import com.news.example.myproject.ui.refresh.rv.RefreshListView;

import java.util.List;

/**
 * @author xiemy
 * @date 2018/3/2
 */
public class FollowFragment extends BaseRefreshFragment<VideoDetails> implements RefreshListView {

    private RefreshPresenter followPresenter;

    public static FollowFragment newInstance(int uid) {
        FollowFragment followFragment = new FollowFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FConstants.UID, uid);
        followFragment.setArguments(bundle);
        return followFragment;
    }

    @Override
    public RecyclerView.Adapter getRefreshAdapter(List<VideoDetails> followList) {
        LinearLayoutManager llm = new LinearLayoutManager(_mActivity, LinearLayoutManager.VERTICAL, false);
        canContentView.setLayoutManager(llm);
        DividerItemDecoration decoration = new DividerItemDecoration(_mActivity, 5, true,
                getResources().getColor(R.color.default_toast_bg));
        canContentView.addItemDecoration(decoration);
        BaseQuickAdapter adapter = new BaseQuickAdapter<VideoDetails, BaseViewHolder>(R.layout.item_user, followList) {
            @Override
            protected void convert(BaseViewHolder helper, VideoDetails item) {
                helper.setText(R.id.tvUserName, item.getUserInfo().getName() + ":" + helper.getAdapterPosition());
                ImageView ivVideoBg = helper.getView(R.id.ivVideoBg);
                new ImageLoadUtils(_mActivity).commonDisplayImage(item.getImgUrl(), ivVideoBg, DefaultValue.BACKGROUND);

                ImageView ivHead = helper.getView(R.id.ivUserAvatar);
                new ImageLoadUtils(_mActivity).commonCircleImage(item.getUserInfo().getAvatarUrl(), ivHead, DefaultValue.HEAD);
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoDetails info = (VideoDetails) adapter.getItem(position);
                if (null == info) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(ParamConstants.SEARCH_KEY, info.getUserInfo().getName());
                toPage(RefreshListActivity.class, bundle);
            }
        });
        return adapter;
    }

    @Override
    public void refreshDataList() {
        if (followPresenter == null) {
            followPresenter = new RefreshPresenter(this);
        }
        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                followPresenter.getSearchUsers("测试");
            }
        }, 1000);
    }

    @Override
    public void getDataSuccess(RecommendResponse dataRes) {
        //List<UserInfo> dataList = ObjectUtils.isNotEmpty(dataRes.getData()) ? dataRes.getData().getUser_list() : null;
    }

    @Override
    public void getVideoListSuccess(List<VideoDetails> videoList) {
        loadDataSuccess(videoList);
    }
}
