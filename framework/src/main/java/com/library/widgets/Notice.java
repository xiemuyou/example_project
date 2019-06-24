package com.library.widgets;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.library.R;

public class Notice extends Dialog {

    public Notice(Context context) {
        super(context, R.style.Theme_Light_FullScreenDialogAct);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    public Notice(Context context, int style) {
        super(context, style);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }
}