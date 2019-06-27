package com.news.example.myproject.widgets.flow;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.news.example.myproject.R;

import java.util.List;

/**
 * 标签适配器
 * Created by xiemy on 2017/8/15.
 */
public class MyTagAdapter extends TagAdapter<String> {

    private LayoutInflater inflater;
    private Context context;

    public MyTagAdapter(Context context, List<String> tagList) {
        super(tagList);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        if (TextUtils.isEmpty(s)) {
            return null;
        }
        TextView tvSearchTest = (TextView) inflater.inflate(R.layout.item_search_tag, null);
        tvSearchTest.setBackgroundResource(R.drawable.et_search_bg);
        tvSearchTest.setText(s);
        return tvSearchTest;
    }

    @Override
    public View getMoreView(int viewHeight) {
        int dp10 = SizeUtils.px2dp(10F);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(dp10, viewHeight);
        lp.setMargins(dp10, dp10, dp10, dp10);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(lp);
        imageView.setBackgroundResource(R.drawable.item_more);
        return imageView;
    }
}
