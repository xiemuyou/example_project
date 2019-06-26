package com.news.example.myproject.ui.search.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.library.widgets.wheel.util.ScreenUtils;

import butterknife.ButterKnife;

/**
 * 用户ViewHolder
 * Created by xiemy on 2017/5/19.
 */
public class UserInfoViewHolder extends RecyclerView.ViewHolder {

//    @BindView(R.id.flSearchUserHead)
//    FrameLayout flSearchUserHead;
//
//    @BindView(R.id.tvMoreUser)
//    TextView tvMoreUser;
//
//    @BindView(R.id.ivCommonUserHead)
//    ImageView ivCommonUserHead;
//
//    @BindView(R.id.tvCommonName)
//    TextView tvCommonName;
//
//    @BindView(R.id.tvCommonSign)
//    TextView tvCommonSign;
//
//    @BindView(R.id.ivCommonRight)
//    ImageView ivCommonRight;

    public UserInfoViewHolder(View itemView) {
        super(itemView);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(ScreenUtils.widthPixels(itemView.getContext()),RecyclerView.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this, itemView);
    }
}
