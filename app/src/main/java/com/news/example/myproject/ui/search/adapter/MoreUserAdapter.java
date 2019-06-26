package com.news.example.myproject.ui.search.adapter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.news.example.myproject.model.user.UserInfo;

import java.util.List;
//import android.support.annotation.NonNull;
//
//import com.doushi.bean.base.adapter.MultiItemTypeAdapter;
//import com.doushi.bean.model.user.CommonResponse;
//import com.doushi.bean.model.user.UserInfo;
//import com.doushi.bean.page.user.relevant.presenter.AttentionUserPresenter;
//import com.doushi.bean.page.user.relevant.view.AttentionUserView;
//import com.doushi.library.widgets.ToastUtils;
//
//import java.util.List;

/**
 * 更多用户适配器
 * Created by xiemy on 2017/5/21.
 */
public class MoreUserAdapter/* extends MultiItemTypeAdapter<UserInfo> implements AttentionUserView*/ {

//    private AttentionUserPresenter userPresenter;
    private int requestPosition;
    private boolean isRequest;
    private Context context;
    private List<UserInfo> userInfoList;
    public static final int ATTENTION_USER = 1;

    /**
     * 更多用户适配器
     *
     * @param context      上下文对象
     * @param userInfoList 用户列表
     */
    public MoreUserAdapter(@NonNull Context context, List<UserInfo> userInfoList, @NonNull String searchStr) {
//        super(context, userInfoList);
        initEnv(context);
        this.userInfoList = userInfoList;
        this.context = context;
//        addItemViewDelegate(new MoreUserDelegate(context, searchStr) {
//
//            @Override
//            public void attentionUser(int uid, int position) {
//                if (!isRequest) {
//                    isRequest = true;
//                    requestPosition = position;
//                    userPresenter.attentionUser(uid);
//                }
//            }
//
//            @Override
//            public void unAttentionUser(int uid, int position) {
//                if (!isRequest) {
//                    isRequest = true;
//                    requestPosition = position;
//                    userPresenter.unAttentionUser(uid);
//                }
//            }
//        });
    }

    private void initEnv(Context context) {
//        userPresenter = new AttentionUserPresenter(context, MoreUserAdapter.this);
    }

//    @Override
//    public void attentSuccess(CommonResponse comRes) {
//        refresh();
//    }
//
//    @Override
//    public void unAttentSuccess(CommonResponse comRes) {
//        refresh();
//    }
//
//    @Override
//    public void attentsSuccess(CommonResponse comRes) {
//
//    }
//
//    @Override
//    public void loadDataFail(String errorInfo) {
//        isRequest = false;
//        ToastUtils.showToast(context, errorInfo, ToastUtils.ToastType.ERROR_TYPE);
//    }

    private void refresh() {
//        UserInfo userInfo = userInfoList.get(requestPosition);
//        if (userInfo.getUserRelation() == 0) { //相互没有关注
//            userInfo.setUserRelation(1);
//        } else if (userInfo.getUserRelation() == 1) { //我关注别人
//            userInfo.setUserRelation(0);
//        } else if (userInfo.getUserRelation() == 2) { //别人关注我
//            userInfo.setUserRelation(3);
//        } else if (userInfo.getUserRelation() == 3) { //相互关注
//            userInfo.setUserRelation(2);
//        }
//        notifyItemChanged(requestPosition);
        isRequest = false;
    }
}
