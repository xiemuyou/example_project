package com.doushi.library.widgets.emptyview;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.doushi.library.R;

/**
 * @author zhangw
 * @date 2017/8/31.
 * email: zhangwei@kingnet.com
 * <p>
 * 空白页面显示
 */
public enum EmptyEnum {

    /**
     * 没有收藏任何视频
     */
    FavorEmpty(R.drawable.other_favor_empty, R.string.other_favor_empty),

    /**
     * 没有任何消息
     */
    MessageEmpty(R.drawable.other_message_empty, R.string.other_message_empty),
    /**
     * 没有缓存视频
     */
    CacheVideoEmpty(R.drawable.other_cache_empty, R.string.other_cache_video_empty),
    /**
     * 没有购买视频
     */
    BuyVideoEmpty(R.drawable.other_buy_video_empty, R.string.other_buy_video_empty),
    /**
     * 没有搜索到内容
     */
    SearchEmpty(R.drawable.other_search_empty, R.string.other_search_empty),
    /**
     * 未点亮任何头衔
     */
    TitleEmpty(R.drawable.other_title_empty, R.string.other_title_empty),
    /**
     * 没有人看过你
     */
    SawEmpty(R.drawable.other_saw_empty, R.string.other_saw_empty),
    /**
     * 没有师父或徒弟
     */
    MasterPupilEmpty(R.drawable.other_master_pupli_empty, R.string.other_master_pupil_empty),
    /**
     * 没有师父
     */
    MasterEmpty(R.drawable.other_master_pupli_empty, R.string.other_master_empty),
    /**
     * 没有徒弟
     */
    PupilEmpty(R.drawable.other_master_pupli_empty, R.string.other_pupil_empty),
    /**
     * 没有任何动态
     */
    DynamicEmpty(R.drawable.other_dynamic_empty, R.string.other_dynamic_empty),
    /**
     * 统一空白页面
     */
    UniteEmpty(R.drawable.other_unite_empty, R.string.other_unite_empty),
    /**
     * 统一空白页面(可点击重试)
     */
    UniteClickEmpty(R.drawable.other_unite_empty, R.string.other_unite_empty, R.string.other_click_retry),
    /**
     * 无网络
     */
    NetEmpty(R.drawable.other_no_network_empty, R.string.load_error, R.string.other_click_retry),

    /**
     * 收藏页面未登录
     */
    FavorUnloginEmpty(R.drawable.other_unlogin_empty, R.string.other_unlogin, R.string.login);

    /**
     * 图片资源
     */
    private
    @DrawableRes
    int res;
    /**
     * 空白文字提示
     */
    private
    @StringRes
    int text;

    /**
     * 点击重试按钮提示
     */
    private
    @StringRes
    int btnText;

    EmptyEnum() {
    }

    EmptyEnum(int res, int text) {
        this.res = res;
        this.text = text;
    }

    EmptyEnum(int res, int text, int btnText) {
        this.res = res;
        this.text = text;
        this.btnText = btnText;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public int getBtnText() {
        return btnText;
    }

    public void setBtnText(int btnText) {
        this.btnText = btnText;
    }
}
