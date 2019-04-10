package com.doushi.library.thread;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @author xiemy2
 * @date 2019/4/4
 */
public class TaskHandler<T extends HandleMessageCallback> extends Handler {

    private WeakReference<T> msgCallbacks;

    public TaskHandler(T msgCallback) {
        msgCallbacks = new WeakReference<>(msgCallback);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msgCallbacks == null || msgCallbacks.get() == null) {
            return;
        }
        msgCallbacks.get().handleMessage(msg);
    }
}
