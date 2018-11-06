package com.doushi.test.myproject.ui.refresh.rp;

import android.support.annotation.NonNull;

import com.doushi.test.myproject.base.mvp.BasePresenter;
import com.doushi.test.myproject.global.ParamConstants;
import com.doushi.test.myproject.model.base.BaseApiResponse;
import com.doushi.test.myproject.model.search.SearchUserResponse;
import com.doushi.test.myproject.model.video.VideoDetails;
import com.doushi.test.myproject.ui.refresh.rv.RefreshListView;
import com.doushi.test.myproject.znet.InterfaceConfig;
import com.doushi.test.myproject.znet.request.RxRequestCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiemy
 * @date 2018/2/28.
 */
public class RefreshPresenter extends BasePresenter<RefreshListView> {

    public RefreshPresenter(@NonNull RefreshListView view) {
        super(view);
    }

    public void getSearchUsers(int page, int cnt, String searchKey) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParamConstants.CNT, cnt);
        int from = page * cnt;
        params.put(ParamConstants.FROM, from);
        params.put(ParamConstants.SEARCH_KEY, searchKey);
        new RxRequestCallback().doRequestData(params, InterfaceConfig.HttpHelperTag.HTTPHelperTag_GetSearchUsers, SearchUserResponse.class, this);
    }

    @Override
    public void onLoadDataSuccess(InterfaceConfig.HttpHelperTag apiTag, BaseApiResponse modelRes, Map<String, Object> params) {
        switch (apiTag) {
            case HTTPHelperTag_GetSearchUsers:
                SearchUserResponse searchRes = (SearchUserResponse) modelRes;
                getMvpView().getDataSuccess(searchRes);
                break;

            default:
                break;
        }
    }

    public void getSearchVideoList(int page, int cnt, String searchKey) {
        List<VideoDetails> videoList = new ArrayList<>();
        if (page < 5) {
            for (int i = 0; i < cnt; i++) {
                int vid = (page + 1) + i;
                String head = "http://ouo48szbk.bkt.clouddn.com/202562011584790528?imageView2/2/w/100/h/100/format/jpg/q/75";
                String nick = searchKey + i;
                String imgUrl = "http://ouo48szbk.bkt.clouddn.com/208013676391694336?imageView2/2/w/720/h/405/format/jpg/q/75";
                String mp4Url = "http://ouo4fr79u.bkt.clouddn.com/720_208013665369063424.mp4";
                videoList.add(new VideoDetails(vid, head, nick, imgUrl, mp4Url));
            }
        }
        if (getMvpView() != null) {
            getMvpView().getVideoListSuccess(videoList);
        }
    }
}
