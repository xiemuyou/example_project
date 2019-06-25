package com.news.example.myproject.ui.welcome;

import android.os.Bundle;

import com.library.widgets.guild.GuideView;
import com.news.example.myproject.R;
import com.news.example.myproject.base.component.BaseActivity;
import com.news.example.myproject.ui.main.MainActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author xiemy1
 * @date 2018/8/13
 */
public class GuildActivity extends BaseActivity {

    @BindView(R.id.gvContent)
    GuideView gvContent;

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_guild;
    }

    @Override
    public void initEnv() {
        int[] guildList = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        List<Integer> topTextRes = Arrays.asList(R.drawable.guid_top1, R.drawable.guid_top2, R.drawable.guid_top3);
        gvContent.setGuideData(guildList, topTextRes);
        gvContent.setOnClickListener(v -> {
            toPage(MainActivity.class);
            closePage();
        });
    }
}
