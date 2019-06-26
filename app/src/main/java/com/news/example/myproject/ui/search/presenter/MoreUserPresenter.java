package com.news.example.myproject.ui.search.presenter;

import com.news.example.myproject.base.mvp.BasePresenter;
import com.news.example.myproject.model.base.BaseApiResponse;
import com.news.example.myproject.ui.search.view.MoreUserView;
import com.news.example.myproject.znet.InterfaceConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 搜索类实现
 * Created by xiemy on 2017/5/19.
 */
public class MoreUserPresenter extends BasePresenter<MoreUserView> {

    public MoreUserPresenter(MoreUserView moreUserView) {
        attachView(moreUserView);
    }

    @Override
    public void onLoadDataSuccess(InterfaceConfig.HttpHelperTag apiTag, BaseApiResponse modelRes, Map<String, Object> params) {
//        switch (tag) {
//            case HTTPHelperTag_GetSearchedUsers:
//                UserInfoList videosRes = (UserInfoList) JsonUtil.jsonToObject(responseObj, UserInfoList.class);
//                if (videosRes.getErrcode() == 0) {
//                    if (isViewAttached())
//                        getMvpView().getMoreUserSuccess(videosRes.getData());
//                } else {
//                    loadDataFail(videosRes.getErrmsg());
//                }
//                break;
//        }
    }

    /**
     * 搜索更多用户列表
     *
     * @param inputStr 用户输入要字符串
     */
    public void getSearchedUsers(int page, int cnt, String inputStr) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", inputStr);
        int fromCount = page * cnt;
//        map.put(Constants.FROM, fromCount);
//        map.put(Constants.CNT, cnt);
//        ZZAllService.sharedInstance().serviceQueryByObj(map, serviceHelperDelegate, InterfaceConfig.HttpHelperTag.HTTPHelperTag_GetSearchedUsers);
    }
}
