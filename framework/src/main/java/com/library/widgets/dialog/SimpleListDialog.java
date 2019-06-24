package com.library.widgets.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.library.R;
import com.library.widgets.Notice;

public class SimpleListDialog extends Notice implements OnItemClickListener {

    private ListView mLvDisplay;
    private BaseAdapter mAdapter;
    private onSimpleListItemClickListener mOnSimpleListItemClickListener;

    public SimpleListDialog(Context context) {
        super(context, R.style.CustomerDialog);
        setContentView(R.layout.view_dialog_simplelist);
        mLvDisplay = findViewById(R.id.dialog_simplelist_list);
        mLvDisplay.setOnItemClickListener(this);

        //getWindow().getAttributes().width = Screen.getInstance().widthPixels - 60;
        Window dialogWindow = this.getWindow();
        if (dialogWindow != null) {
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.CENTER);
            //   alpha在0.0f到1.0f之间。1.0完全不透明，0.0f完全透明
            lp.alpha = 1.0f;
            dialogWindow.setAttributes(lp);
        }
    }

    public void setAdapter(BaseAdapter adapter) {
        mAdapter = adapter;
        if (mAdapter != null) {
            mLvDisplay.setAdapter(mAdapter);
        }
    }

    public void notifyDataSetChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setOnSimpleListItemClickListener(onSimpleListItemClickListener listener) {
        mOnSimpleListItemClickListener = listener;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        if (mOnSimpleListItemClickListener != null) {
            mOnSimpleListItemClickListener.onItemClick(arg2);
            dismiss();
        }
    }

    public interface onSimpleListItemClickListener {
        void onItemClick(int position);
    }
}
