package com.news.example.myproject.utils.share;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.library.util.JsonUtil;
import com.library.util.LogUtil;
import com.library.widgets.ToastUtils;
import com.news.example.myproject.R;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 *
 * @author tanghao
 * @date 2018/5/21
 */
public class ShareUtil {

    /**
     * 分享成功
     */
    public static final int SHARE_SUCCESS = 0x003;
    /**
     * 分享失败
     */
    public static final int SHARE_FAILURE = 0x004;
    /**
     * 分享取消
     */
    public static final int SHARE_CANCEL = 0x005;

    /**
     * 项目名称
     */
    private String appName = "五条";
    /**
     * 分享标题
     */
    private String shareTitle = "";
    /**
     * 分享介绍
     */
    private String shareText = "";
    /**
     * 分享图片链接
     */
    private String shareImageUrl;
    /**
     * 分享链接
     */
    private String shareUrl;

    private String itemid;

    private OnShareListener shareListener;

    public Context context;

    private String pos = "";

    private String successTip = "";


    String defaultImageUrl = "http://image.5tiao.net/share_default.png";

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText;
    }

    public void setShareImageUrl(String shareImageUrl) {
        this.shareImageUrl = shareImageUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public void setSuccessTip(String successTip) {
        this.successTip = successTip;
    }

    public void setShareListener(OnShareListener shareListener) {
        this.shareListener = shareListener;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ShareUtil(Context context) {
        this.context = context;
    }

    public ShareUtil(Context context, String itemid, String shareTitle, String shareText, String shareImageUrl, String shareUrl, OnShareListener shareListener) {
        this.itemid = itemid;
        this.shareTitle = shareTitle;
        this.shareText = shareText;
        this.shareImageUrl = shareImageUrl;
        this.shareUrl = shareUrl;
        this.shareListener = shareListener;
        this.context = context;
    }

    public void setOnShareSuccessListener(OnShareListener shareListener) {
        this.shareListener = shareListener;
    }

    /**
     * @param id
     * @param shareType H5UrlSharedPreferences.Share_Article,
     *                  H5UrlSharedPreferences.Share_Video
     */
    public static String createShareUrl(String id, String shareType) {
        String key = shareType;
//        String url = H5UrlSharedPreferences.INSTANCE.getString(key, "");
        StringBuffer sb = new StringBuffer();
//        sb.append(url);
        sb.append("?id=");
        sb.append(id);
        return sb.toString();
    }


    public void showShare(final String platformToShare) {
        showShare(platformToShare, Platform.SHARE_WEBPAGE, true);
    }

    private String doMainUrl(String shareUrl, boolean isDomain) {
        if (!isDomain) {
            return shareUrl;
        }

        try {
            String json = "";
            //TODO
            //GuideConsultSharedPresences.INSTANCE.getString(GuideConsultSharedPresences.Do_main, "");
//            if (json.isEmpty()) {
//                return null;
//            }
            TypeToken<String> type = new TypeToken<String>() {
            };
            List<String> list = (List<String>) JsonUtil.jsonToList(json, type);

            int i = new Random().nextInt(list.size());
            return replaceDomainAndPort(list.get(i), null, shareUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return shareUrl;
        }
    }

    private static String replaceDomainAndPort(String domain, String port, String url) throws Exception {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        String doubleSlash = "//";
        String slash = "/";
        StringBuilder urlBak = new StringBuilder();
        if (url.contains(doubleSlash)) {
            String[] splitTemp = url.split(doubleSlash);
            urlBak = new StringBuilder(splitTemp[0] + doubleSlash);
            if (port != null) {
                urlBak.append(domain).append(":").append(port);
            } else {
                urlBak.append(domain);
            }

            if (splitTemp[1].contains(slash)) {
                String[] urlTemp2 = splitTemp[1].split(slash);
                if (urlTemp2.length > 1) {
                    for (int i = 1; i < urlTemp2.length; i++) {
                        urlBak.append(slash).append(urlTemp2[i]);
                    }
                }
                LogUtil.d("url_bak:" + urlBak);
            } else {
                LogUtil.d("url_bak:" + urlBak);
            }
        }
        return urlBak.toString();
    }

    /**
     * @param platformToShare 分享类型平台
     * @param shareType       Platform.SHARE_IMAGE
     */
    public void showShare(final String platformToShare, int shareType, boolean isDomain) {
        Platform.ShareParams params = new Platform.ShareParams();
        params.setShareType(shareType);

        //视频分享+水印
//        String videoBaseUrl = H5UrlSharedPreferences.INSTANCE.getString(H5UrlSharedPreferences.Share_Video, "");
//        if (!videoBaseUrl.equals("") && shareUrl != null && shareUrl.startsWith(videoBaseUrl)) {
//            if (platformToShare.equals(Wechat.NAME) || platformToShare.equals(WechatMoments.NAME)) {
//                params.setShareType(Platform.SHARE_VIDEO);
//            }
//        }

        String sUrl = doMainUrl(shareUrl, isDomain);

        params.setUrl(sUrl);
        params.setTitle(dealCharHtml(shareTitle));
        params.setTitleUrl(sUrl);
        params.setText(delHtmlTag(shareText));
        //oks.setImagePath("/sdcard/test-pic.jpg");  //分享sdcard目录下的图片
        if (StringUtils.isEmpty(shareImageUrl) || !shareImageUrl.startsWith("http")) {
            params.setImageUrl(defaultImageUrl);
        } else {
            params.setImageUrl(shareImageUrl);
        }
        //我对这条分享的评论，仅在人人网和QQ空间使用，否则可以不提供
        params.setComment(appName);
        //QZone分享完之后返回应用时提示框上显示的名称
        params.setSite(appName);
        //QZone分享参数
        params.setSiteUrl(sUrl);
        params.setVenueName(appName);
        params.setVenueDescription(shareText);


        final Platform platform = ShareSDK.getPlatform(platformToShare);
        if (!platform.isClientValid()) {
            if (platformToShare.equals(Wechat.NAME)/* || platformToShare.equals(WechatMoments.NAME)*/) {
                ToastUtils.showToast(context, context.getString(R.string.no_wechat));
                return;
            }
        }
        platform.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                if (shareListener != null) {
                    shareListener.shareComplete(SHARE_SUCCESS);
                }

                String shareSuccess = TextUtils.isEmpty(successTip) ? "分享成功" : successTip;
                ToastUtils.showToast(ActivityUtils.getTopActivity(), shareSuccess);
            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                if (shareListener != null) {
                    shareListener.shareComplete(SHARE_CANCEL);
                }
            }

            @Override
            public void onError(Platform arg0, int arg1, Throwable t) {
                if (shareListener != null) {
                    shareListener.shareComplete(SHARE_FAILURE);
                }

                ToastUtils.showToast(context, "分享失败");
            }
        });
        platform.share(params);

        //分享的回调必须要回到本应用才能执行，所以我在分享之前统计了分享
//        platform.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
//
//            @Override
//            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
//                if (Wechat.NAME.equals(platform.getName()) || WechatMoments.NAME.equals(platform.getName())) {
//                    paramsToShare.setShareType(Platform.SHARE_VIDEO);
//                }
//            }
//        });
//        Bitmap logo = BitmapFactory.decodeResource(Application.getContext().getResources(), R.mipmap.ic_launcher);
//        String label = appName;
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        };
//        platform.setCustomerLogo(logo, label, listener);
//        oks.show(context);
    }


    /**
     * @author zhangw
     * @date 2018/1/17.
     * <p>
     * 分享dialog监听回调
     */
    public interface OnShareListener {

        /**
         * 分享结束
         *
         * @param result {@link ShareUtil#SHARE_SUCCESS,ShareUtil#SHARE_FAILURE,ShareUtil#SHARE_CANCEL}
         */
        void shareComplete(int result);

    }

    private static String dealCharHtml(String content) {
        if (content.contains("&")) {
            content = content.replace("&#039;", "'");
            content = content.replace("&quot;", "\"");
            content = content.replace("&lt;", "<");
            content = content.replace("&gt;", ">");
            content = content.replace("&amp;", "&");
        }
        return content;
    }

    private static String delHtmlTag(String htmlStr) {
        //定义script的正则表达式
        String regExScript = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        //定义style的正则表达式
        String regExStyle = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式
        String regExHtml = "<[^>]+>";

        Pattern pScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
        Matcher mScript = pScript.matcher(htmlStr);
        //过滤script标签doMainUrl
        htmlStr = mScript.replaceAll("");

        Pattern pStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
        Matcher mStyle = pStyle.matcher(htmlStr);
        //过滤style标签
        htmlStr = mStyle.replaceAll("");

        Pattern pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
        Matcher mHtml = pHtml.matcher(htmlStr);
        //过滤html标签
        htmlStr = mHtml.replaceAll("");
        //返回文本字符串
        return htmlStr.trim();
    }
}
