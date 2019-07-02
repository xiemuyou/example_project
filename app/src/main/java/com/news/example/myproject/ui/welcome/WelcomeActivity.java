package com.news.example.myproject.ui.welcome;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.library.thread.AbstractSafeThread;
import com.library.util.AppIntroUtil;
import com.library.widgets.statusbar.StatusBarCompat;
import com.news.example.myproject.R;
import com.news.example.myproject.ui.main.MainActivity;
import com.news.example.myproject.znet.InterfaceConfig;

/**
 * 欢迎页/程序入口
 *
 * @author xiemy
 * @date 2018-2-27
 */
public class WelcomeActivity extends AppCompatActivity implements WelcomeView {
    /**
     * 显示引导页次数
     * home键退出,修改再次会进入引导页的bug
     */
    private static int showGuideCnt = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
        super.onCreate(savedInstanceState);
        new WelcomePresenter(this).loginByToken();
    }

    @Override
    public void loginSuccess(int showWelcome) {
        toNextPage();
    }

    @Override
    public void loadDataFail(InterfaceConfig.HttpHelperTag apiTag, String errorInfo) {
        toNextPage();
    }

    private void toNextPage() {
        new Handler().postDelayed(new AbstractSafeThread() {
            @Override
            public void deal() {
                //首次启动,更新版本,进入引导页
                if (showGuideCnt == 0 && AppIntroUtil.getInstance().isFirstOpen()) {
                    showGuideCnt++;
                    ActivityUtils.startActivity(GuildActivity.class);
                    //浏览器打开app,跳转相关页面
                } else {
                    ActivityUtils.startActivity(MainActivity.class);
                }
                finish();
            }
        }, 3000);
    }
}
