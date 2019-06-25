package com.library.widgets.guild;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.SizeUtils;
import com.library.R;
import com.library.util.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiemy1
 * @date 2018/8/15
 */
public class GuideView extends RelativeLayout {

    private ViewPager cvpGuildContent;
    private LinearLayout llGuildView;
    private ImageView ivGuildStart;
    private int showIndex = 0;

    public GuideView(Context context) {
        this(context, null);
    }

    public GuideView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(getContext(), R.layout.view_guild, this);
        cvpGuildContent = view.findViewById(R.id.vpGuildContent);
        llGuildView = view.findViewById(R.id.llGuildView);
        ivGuildStart = view.findViewById(R.id.ivGuildStart);
        ivGuildStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(view);
                }
            }
        });
    }

    public void setGuideData(@NonNull int[] dataList, @NonNull List<Integer> topTexts) {
        int size = dataList.length;
        List<View> guideViews = new ArrayList<>(size);
        List<Integer> iconIds = new ArrayList<>(size);
        ImageView imageView;
        for (int i = 0; i < size; i++) {
            imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            guideViews.add(View.inflate(getContext(), R.layout.view_guide_item, null));
            iconIds.add(dataList[i]);
            ImageView radioButton = new ImageView(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(SizeUtils.dp2px(36), SizeUtils.dp2px(2));
            if (i > 0) {
                layoutParams.leftMargin = SizeUtils.dp2px(6);
            }
            llGuildView.addView(radioButton, layoutParams);
            radioButton.setBackgroundResource(i == 0 ? R.drawable.ic_guide_select : R.drawable.ic_guide_default);
        }
        initPagerAdapter(guideViews, iconIds, topTexts);
    }

    private void initPagerAdapter(final @NonNull List<View> guideViews, @NonNull List<Integer> iconIds, @NonNull List<Integer> topTexts) {
        cvpGuildContent.setAdapter(new GuidePagerAdapter(guideViews, iconIds, topTexts));
        cvpGuildContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (guideViews.size() > showIndex) {
                    llGuildView.getChildAt(showIndex).setBackgroundResource(R.drawable.ic_guide_default);
                }
                if (guideViews.size() > position) {
                    llGuildView.getChildAt(position).setBackgroundResource(R.drawable.ic_guide_select);
                }
                boolean showStartGuild = position == guideViews.size() - 1;
                if (showStartGuild) {
                    ivGuildStart.setVisibility(View.VISIBLE);
                    showStartAnimator(ivGuildStart);
                } else {
                    ivGuildStart.setVisibility(View.GONE);
                }
                showIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private OnClickListener onClickListener;

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private class GuidePagerAdapter extends PagerAdapter {

        private @NonNull
        List<View> mGuideViews;

        private @NonNull
        List<Integer> mIconIds;
        private @NonNull
        List<Integer> mTopTexts;

        GuidePagerAdapter(@NonNull List<View> guideViews, @NonNull List<Integer> iconIds, @NonNull List<Integer> topTexts) {
            mGuideViews = guideViews;
            mIconIds = iconIds;
            mTopTexts = topTexts;
        }

        @Override
        public int getCount() {
            return mGuideViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mGuideViews.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View itemView = mGuideViews.get(position);
            ImageView ivTopText = itemView.findViewById(R.id.ivTopText);
            ImageView ivGuideBg = itemView.findViewById(R.id.ivGuideBg);
            ivTopText.setImageResource(mTopTexts.get(position));
            new ImageLoadUtils(itemView).commonDisplayResImage(mIconIds.get(position), ivGuideBg, mIconIds.get(position));
            container.addView(itemView);
            return itemView;
        }
    }

    private void showStartAnimator(View view) {
        final long animShowTime = 400;
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0.0F, 1.0F);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationY", 100, 0);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(translationX).with(alpha);
        animSet.setDuration(animShowTime);
        animSet.start();
    }
}