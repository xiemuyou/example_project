package com.news.example.myproject.ui.web;

import android.webkit.JavascriptInterface;

import com.library.util.JsonUtil;

import java.util.List;

public class ConsultWebCallback extends Object {


    private ConsultCallBack mWebCallback;

    public ConsultWebCallback(ConsultCallBack webCallback) {
        mWebCallback = webCallback;
    }

    @JavascriptInterface
    public void postMessage(String jsonStr) {
        try {
            ConsultJsDataItem consultJsDataItem = (ConsultJsDataItem) JsonUtil.jsonToObject(jsonStr, ConsultJsDataItem.class);
            if (mWebCallback != null) {
                mWebCallback.showImageView(consultJsDataItem.getIndex(),
                        consultJsDataItem.getData());
            }
        } catch (Throwable e) {

        }
    }

    public interface ConsultCallBack {

        void showImageView(int index, List<ConsultJsDataItem.DataBean> data);
    }
}
