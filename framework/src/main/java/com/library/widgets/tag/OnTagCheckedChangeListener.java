package com.library.widgets.tag;

import android.widget.CompoundButton;

public interface OnTagCheckedChangeListener {
    void onTagCheckedChanged(CompoundButton compoundButton, boolean b, int position, Object tag);
}