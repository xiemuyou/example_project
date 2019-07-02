package com.news.example.myproject.model.sort;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.news.example.myproject.R;

import java.util.List;

/**
 * @author tanghao
 * @date 2018/8/28
 */
public class SortEditAdapter extends BaseMultiItemQuickAdapter<NewsSortInfo, BaseViewHolder> {

    private OnLongPressListener onLongPressListener;
    public boolean isShowClose = false;

    public void setOnLongPressListener(OnLongPressListener onLongPressListener) {
        this.onLongPressListener = onLongPressListener;
    }

    public SortEditAdapter(@Nullable List<NewsSortInfo> data) {
        super(data);
        addItemType(NewsSortInfo.CHOOSE, R.layout.item_sort_choose);
        addItemType(NewsSortInfo.FIXED, R.layout.item_sort_choose);

        addItemType(NewsSortInfo.TITLE, R.layout.item_sort_title);
        addItemType(NewsSortInfo.MORE, R.layout.item_sort_more);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NewsSortInfo item) {
        if (helper == null || item == null) {
            return;
        }
        String sortName = item.getName();
        int nameLen = sortName != null ? sortName.length() : 0;
        switch (helper.getItemViewType()) {
            case NewsSortInfo.FIXED:
            case NewsSortInfo.CHOOSE:
                TextView tvLabel = helper.getView(R.id.tvSortLabel);
                tvLabel.setText(sortName);
                boolean isFixed = helper.getItemViewType() == NewsSortInfo.FIXED;
                int color = isFixed ? Color.parseColor("#9c9c9c") : Color.parseColor("#2c2c2c");
                tvLabel.setTextColor(color);
                int textSize = 15;
                if (nameLen == 4) {
                    textSize = 14;
                } else if (nameLen >= 5) {
                    textSize = 12;
                }
                tvLabel.setTextSize(textSize);
                int visible = isShowClose && !isFixed ? View.VISIBLE : View.GONE;
                helper.getView(R.id.ivSortClose).setVisibility(visible);
                if (onLongPressListener == null) {
                    return;
                }
                helper.itemView.setOnLongClickListener(v -> {
                    onLongPressListener.onLongPressed(helper, item);
                    return true;
                });
                break;

            case NewsSortInfo.MORE:
                TextView tvLabelName = helper.getView(R.id.tv_label);
                ImageView ivLabelAdd = helper.getView(R.id.iv_label_add);
                helper.setText(R.id.tv_label, sortName);
                int size = 15;
                int resAdd = R.drawable.icon_add_big;
                int margin = 6;
                if (nameLen == 4) {
                    size = 12;
                    resAdd = R.drawable.icon_add_small;
                    margin = 3;
                } else if (item.getName().length() >= 5) {
                    size = 11;
                    resAdd = R.drawable.icon_add_small;
                    margin = 3;
                }
                tvLabelName.setTextSize(size);
                ivLabelAdd.setImageResource(resAdd);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvLabelName.getLayoutParams();
                layoutParams.leftMargin = SizeUtils.dp2px(margin);
                break;

            case NewsSortInfo.TITLE:
                break;

            default:
                break;
        }
    }

    public interface OnLongPressListener {
        void onLongPressed(BaseViewHolder helper, NewsSortInfo item);
    }
}
