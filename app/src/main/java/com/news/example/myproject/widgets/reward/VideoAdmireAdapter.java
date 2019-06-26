package com.news.example.myproject.widgets.reward;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.news.example.myproject.model.search.RewardGifts;

import java.util.List;

/**
 * 打赏礼物列表
 * Created by xiemy on 2017/5/16.
 */
public class VideoAdmireAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * View充气机
     */
    private Context context;
    /**
     * 赠送礼物列表
     */
    private List<RewardGifts> giftsList;
    /**
     * 选择项
     */
    private int choicePosition = 0;
    /**
     * 是否选择
     */
    private boolean choiceFlag = false;

    /**
     * 视频打赏礼物适配器
     *
     * @param context   上下文对象
     * @param giftsList 打赏礼物列表
     */
    public VideoAdmireAdapter(Context context, List<RewardGifts> giftsList) {
        this.context = context;
        this.giftsList = giftsList;
        this.choiceFlag = false;
    }

    @Override
    public int getItemCount() {
        return giftsList != null ? giftsList.size() : 0;
    }

    public void updateItemChanged(int choicePosition) {
        this.choiceFlag = true;
        this.choicePosition = choicePosition;
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(context).inflate(R.layout.item_video_amidre, null);
//        return new AdmireViewHolder(itemView);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        RewardGifts gifts = giftsList.get(position);
//        if (gifts != null) {
//            ImageLoadUtils.displayImage(context, gifts.getImg(), holder.ivAdmirePic, DefaultValue.VIDEO_BG);
//            if (Constants.hideDuobaoBtn == 0) {
//                holder.tvAdmireGift.setVisibility(View.VISIBLE);
//                holder.tvAdmireGift.setText(gifts.getGoodsDes());
//            } else {
//                holder.tvAdmireGift.setVisibility(View.GONE);
//            }
//            holder.tvAdmirePrice.setText(context.getString(R.string.many_coin,String.valueOf(gifts.getPrice())));
//            if (choiceFlag && choicePosition == position) {
//                holder.llAdmireItemView.setBackgroundColor(context.getResources().getColor(R.color.common_horizontal_line_color));
//            } else {
//                holder.llAdmireItemView.setBackgroundColor(context.getResources().getColor(R.color.white));
//            }
//        }
    }

//    class AdmireViewHolder extends RecyclerView.ViewHolder{
//
//        //ItemView
//        private LinearLayout llAdmireItemView;
//        //打赏tupIco
//        private ImageView ivAdmirePic;
//        //打赏夺宝礼物
//        private TextView tvAdmireGift;
//        //打赏逗币价格
//        private TextView tvAdmirePrice;
//
//        private AdmireViewHolder(View itemView){
//            super(itemView);
//            llAdmireItemView = (LinearLayout) itemView.findViewById(R.id.llAdmireItemView);
//            ivAdmirePic = (ImageView) itemView.findViewById(R.id.ivAdmirePic);
//            tvAdmireGift = (TextView) itemView.findViewById(R.id.tvAdmireGift);
//            tvAdmirePrice = (TextView) itemView.findViewById(R.id.tvAdmirePrice);
//        }
//    }
}