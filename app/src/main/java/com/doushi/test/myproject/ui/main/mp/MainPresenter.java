package com.doushi.test.myproject.ui.main.mp;

import com.doushi.test.myproject.base.mvp.BasePresenter;
import com.doushi.test.myproject.model.BaseApiResponse;
import com.doushi.test.myproject.ui.main.mv.MainView;
import com.doushi.test.myproject.znet.InterfaceConfig;

/**
 * @author xiemy
 * @date 2018/2/28.
 */
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view) {
        super(view);
    }

    public void getData() {
        //new RxRequest<>().doRequestData(null, InterfaceConfig.HttpHelperTag.HTTPHelperTag_ReportCrashLog, BaseApiResponse.class, this);
    }

    @Override
    public void onLoadDataSuccess(InterfaceConfig.HttpHelperTag apiTag, BaseApiResponse modelRes) {
        switch (apiTag) {
            case HTTPHelperTag_ReportCrashLog:
                if (isViewAttached(modelRes)) {
                    getMvpView().getDataSuccess(modelRes.getErrcode() == 0);
                }
                break;

            default:
                break;
        }
    }
}
