package com.news.example.myproject.ui.search.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

/**
 * 用户ViewHolder
 *
 * @author xiemy
 * @date 2017/5/19
 */
public class SearchVideoHeadViewHolder extends RecyclerView.ViewHolder {

//    @BindView(R.id.tvVideoCount)
//    TextView tvVideoCount;

    public SearchVideoHeadViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
