package com.news.example.myproject.ui.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.blankj.utilcode.util.SizeUtils;
import com.library.util.ViewUtil;
import com.news.example.myproject.R;
import com.news.example.myproject.model.sort.NewsSortInfo;

import java.util.List;

/**
 * @author xiemy2
 * @date 2019/7/5
 */
public class SortEditAdapter extends DelegateAdapter.Adapter<SortEditAdapter.SortViewHolder> {

    private Context context;
    private List<NewsSortInfo> sortInfoList;
    private boolean isShowClose = false;
    private LayoutHelper mLayoutHelper;

    public void setIsShowClose(boolean isShowClose) {
        this.isShowClose = isShowClose;
    }

    public boolean isShowClose() {
        return isShowClose;
    }

    public SortEditAdapter(Context context, List<NewsSortInfo> sortInfoList, LayoutHelper layoutHelper) {
        this.context = context;
        this.sortInfoList = sortInfoList;
        this.mLayoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public int getItemViewType(int position) {
//        NewsSortInfo info = sortInfoList.get(position);
//        Integer itemType = info.getItemType();
        return 0; // itemType != null ? itemType : 0;
    }

    @NonNull
    @Override
    public SortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         addItemType(NewsSortInfo.CHOOSE, R.layout.item_sort_choose)
        addItemType(NewsSortInfo.FIXED, R.layout.item_sort_choose)
        addItemType(NewsSortInfo.TITLE, R.layout.item_sort_title)
        addItemType(NewsSortInfo.MORE, R.layout.item_sort_more)
         */
        SortViewHolder sortViewHolder;
        switch (viewType) {
            case NewsSortInfo.CHOOSE:
            case NewsSortInfo.FIXED:
                View itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_choose, parent, false);
                sortViewHolder = new SortViewHolder(itemView, viewType);
                break;

            case NewsSortInfo.MORE:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_more, parent, false);
                sortViewHolder = new SortViewHolder(itemView, viewType);
                break;

            default:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_sort_title, parent, false);
                sortViewHolder = new SortViewHolder(itemView, viewType);
                break;
        }
        return sortViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SortViewHolder holder, int position) {
        NewsSortInfo info = sortInfoList.get(position);
        if (info == null) {
            return;
        }
        switch (holder.getItemViewType()) {
            case NewsSortInfo.CHOOSE:
            case NewsSortInfo.FIXED:
                //设置文字颜色
                boolean isFixed = holder.getItemViewType() == NewsSortInfo.FIXED;
                int color = isFixed ? R.color.color_9C9C9C : R.color.color_2C2C2C;
                holder.tvSortLabel.setTextColor(ContextCompat.getColor(context, color));

                //设置文字大小
                String name = info.getName();
                int nameLen = name != null ? name.length() : 0;
                int textSize = 15;
                if (nameLen == 4) {
                    textSize = 14;
                } else if (nameLen >= 5) {
                    textSize = 12;
                }
                holder.tvSortLabel.setTextSize(textSize);
                holder.tvSortLabel.setText(name);
                break;

            case NewsSortInfo.MORE:
                name = info.getName();
                nameLen = name != null ? name.length() : 0;
                holder.tvLabel.setText(name);
                int size = 15;
                int resAdd = R.drawable.icon_add_big;
                int margin = 6;
                if (nameLen == 4) {
                    size = 12;
                    resAdd = R.drawable.icon_add_small;
                    margin = 3;
                } else if (nameLen >= 5) {
                    size = 11;
                    resAdd = R.drawable.icon_add_small;
                    margin = 3;
                }
                holder.tvLabel.setTextSize(size);
                holder.ivLabelAdd.setImageResource(resAdd);
                ViewUtil.setMargins(holder.tvLabel, SizeUtils.dp2px(margin), 0, 0, 0);
                break;

            case NewsSortInfo.TITLE:
                //holder.tvLabel.setText("我的频道");
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return sortInfoList != null ? sortInfoList.size() : 0;
    }

    class SortViewHolder extends RecyclerView.ViewHolder {

        TextView tvSortLabel;
        ImageView ivSortClose;

        ImageView ivLabelAdd;
        TextView tvLabel;

        SortViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if (viewType == NewsSortInfo.CHOOSE || viewType == NewsSortInfo.FIXED) {
                tvSortLabel = itemView.findViewById(R.id.tvSortLabel);
                ivSortClose = itemView.findViewById(R.id.ivSortClose);

            } else if (viewType == NewsSortInfo.MORE) {
                tvLabel = itemView.findViewById(R.id.tv_label);
                ivLabelAdd = itemView.findViewById(R.id.iv_label_add);

            }
        }
    }
}
