package com.doushi.library.thread;

import android.os.Message;

/**
 * @author xiemy2
 * @date 2019/4/4
 */
public interface HandleMessageCallback {
    /**
     * handle回调
     *
     * @param msg 回调信息
     */
    void handleMessage(Message msg);
}
