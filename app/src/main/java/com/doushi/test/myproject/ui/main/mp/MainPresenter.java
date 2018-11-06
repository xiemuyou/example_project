package com.doushi.test.myproject.ui.main.mp;

import com.doushi.test.myproject.base.mvp.BasePresenter;
import com.doushi.test.myproject.model.base.BaseApiResponse;
import com.doushi.test.myproject.ui.main.mv.MainView;
import com.doushi.test.myproject.znet.InterfaceConfig;

import java.util.Map;

/**
 * @author xiemy
 * @date 2018/2/28.
 */
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view) {
        super(view);
    }

    @Override
    public void onLoadDataSuccess(InterfaceConfig.HttpHelperTag apiTag, BaseApiResponse modelRes, Map<String, Object> params) {
        switch (apiTag) {
            case HTTPHelperTag_ReportCrashLog:
                getMvpView().getDataSuccess(modelRes.isError());
                break;

            default:
                break;
        }
    }
}
