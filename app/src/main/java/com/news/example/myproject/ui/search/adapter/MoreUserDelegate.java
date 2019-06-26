package com.news.example.myproject.ui.search.adapter;

//import com.doushi.bean.R;
//import com.doushi.bean.base.adapter.base.ItemViewDelegate;
//import com.doushi.bean.base.adapter.base.ViewHolder;
//import com.doushi.bean.global.Constants;
//import com.doushi.bean.listener.JumpOtherHomeEmp;
//import com.doushi.bean.model.user.UserInfo;
//import com.doushi.bean.page.user.setting.ServerWebView;
//import com.doushi.bean.utils.DefaultValue;
//import com.doushi.bean.utils.UserOperationUtil;
//import com.doushi.bean.widgets.LoginBox;
//import com.doushi.library.util.StringUtils;
//import com.doushi.library.widgets.ToastUtils;
//import com.doushi.library.widgets.textstyleplus.ClickListener;
//import com.doushi.library.widgets.textstyleplus.StyleBuilder;
//import com.doushi.library.znet.HttpHelper;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.ListIterator;
//import java.util.Map;
//import java.util.Set;


/**
 * 更多用户
 * Created by xiemy on 2017/5/21.
 */
public abstract class MoreUserDelegate/* implements ItemViewDelegate<UserInfo> */{

//    private Context context;
//    private String searchStr;
//
//    public MoreUserDelegate(@NonNull Context context, @NonNull String searchStr) {
//        this.context = context;
//        this.searchStr = searchStr;
//    }
//
//    @Override
//    public int getItemViewLayoutId() {
//        return R.layout.item_common_users;
//    }
//
//    @Override
//    public boolean isForViewType(UserInfo item, int position) {
//        return true;
//    }
//
//    @Override
//    public void convert(final RecyclerView.ViewHolder holder, final UserInfo userInfo, int position) {
//        holder.setCircleHeadBackground(context, R.id.ivCommonUserHead, userInfo.getHead(), DefaultValue.HEAD);
//        setSearchNick((TextView) holder.getView(R.id.tvCommonName), userInfo.getNick());
//        if (!TextUtils.isEmpty(userInfo.getSign())) {
//            holder.setText(R.id.tvCommonSign, userInfo.getSign());
//        } else {
//            holder.setText(R.id.tvCommonSign, context.getString(R.string.search_empty_sign));
//        }
//        switch (userInfo.getUserRelation()) {
//            case 0: //相互没有关注
//            case 2: //别人关注我
//                holder.setBackgroundRes(R.id.ivCommonRight, R.mipmap.video_focus);
//                break;
//
//            case 1: //我关注别人
//            case 3: //相互关注
//                holder.setBackgroundRes(R.id.ivCommonRight, R.mipmap.video_un_focus);
//                break;
//        }
//
//        holder.setOnClickListener(R.id.ivCommonUserHead, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new JumpOtherHomeEmp(context).jumpOtherHomeActivity(userInfo.getUid());
//            }
//        });
//        holder.setOnClickListener(R.id.ivCommonRight, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (UserOperationUtil.whetherLogin()) {
//                    if (HttpHelper.sharedInstance().getUid() != userInfo.getUid()) { //不是自己
//                        if (userInfo.getUserRelation() == 0 || userInfo.getUserRelation() == 2) {
//                            attentionUser(userInfo.getUid(), holder.getAdapterPosition());
//                        } else {
//                            unAttentionUser(userInfo.getUid(), holder.getAdapterPosition());
//                        }
//                    } else { //没有登录
//                        ToastUtils.showToast(context,R.string.focus_can_not_oneself);
//                    }
//                } else {
//                    showLogin();
//                }
//            }
//        });
//    }
//
//    private void showLogin() {
//        LoginBox loginBox = new LoginBox(context);
//        loginBox.setRequestCode(ATTENTION_USER);
//        loginBox.show();
//    }
//
//    private void setSearchNick(TextView tvSearchNick, String nick) {
//        if (!TextUtils.isEmpty(searchStr) && !TextUtils.isEmpty(nick)) {
//            StyleBuilder sb = new StyleBuilder();
//            SparseBooleanArray array = StringUtils.getPositions(searchStr, nick);
//            String[] ss = nick.split("");
//            for (int i = 0; i < array.size(); i++) {
//                if (array.get(array.keyAt(i))) {
//                    sb.addTextStyle(String.valueOf(ss[i]))
//                            .textColor(context.getResources().getColor(R.color.goldColor))
//                            .commit();
//                } else {
//                    sb.text(String.valueOf(ss[i]));
//                }
//            }
//            sb.show(tvSearchNick);
//        }
//    }
//
//    public abstract void attentionUser(int uid, int position);
//
//    public abstract void unAttentionUser(int uid, int position);
}
