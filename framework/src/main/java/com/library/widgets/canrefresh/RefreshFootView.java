package com.library.widgets.canrefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.library.R;

/**
 * @author xiemy2
 * @date 2019/7/3
 */
public class RefreshFootView extends CanRecyclerViewHeaderFooter {

    private ProgressBar pbFootLoading;
    private TextView tvFootText;
    private Context context;

    public RefreshFootView(Context context) {
        this(context, null);
    }

    public RefreshFootView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View foot = View.inflate(context, R.layout.view_refresh_foot, this);
        this.pbFootLoading = foot.findViewById(R.id.pbFootLoading);
        this.tvFootText = foot.findViewById(R.id.tvFootText);
        this.context = context;
    }

    private View.OnClickListener mClickListener;

    public void setTryAgainClickListener(View.OnClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public void setFootState(LoadDataState loadState) {
        setOnClickListener(null);
        switch (loadState) {
            case EMPTY:
                setVisibility(GONE);
                break;

            case LOAD_COMPLETE:
                setLoadEnable(false);
                setVisibility(VISIBLE);
                pbFootLoading.setVisibility(GONE);
                tvFootText.setVisibility(VISIBLE);
                tvFootText.setTextColor(ContextCompat.getColor(context, R.color.darker_gray));
                tvFootText.setText(R.string.not_more_data);
                break;

            case LOAD_MORE:
                setVisibility(VISIBLE);
                pbFootLoading.setVisibility(VISIBLE);
                tvFootText.setVisibility(VISIBLE);
                tvFootText.setTextColor(ContextCompat.getColor(context, R.color.black));
                tvFootText.setText(R.string.loading_more);
                setLoadEnable(true);
                break;

            case LOAD_MORE_FAIL:
                setVisibility(VISIBLE);
                pbFootLoading.setVisibility(GONE);
                tvFootText.setVisibility(VISIBLE);
                tvFootText.setTextColor(ContextCompat.getColor(context, R.color.red_back_color));
                tvFootText.setText(R.string.load_fail_cry_again);
                setOnClickListener(mClickListener);
                setLoadEnable(false);
                break;

            case LOAD_FAIL:
                setVisibility(GONE);
                break;

            default:
                setVisibility(GONE);
                break;
        }
    }
}
