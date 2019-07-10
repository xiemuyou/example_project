package com.news.example.myproject.global;

/**
 * 全局常量
 *
 * @author xiemy
 * @date 2018/2/28.
 */
public class Constants {
    /**
     * 极光设备ID
     */
    public static String JG_DEVICE_ID = "";
    /**
     * 默认一页加载数量
     */
    public static final int CNT_NUMBER = 10;

    /**
     * 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
     * {@link com.news.example.myproject.ui.news.np.NewsDetailsPresenter#openImage(String url)}
     */
    public static final String JS_INJECT_IMG = "javascript:(function(){" +
            "var objs = document.getElementsByTagName(\"img\"); " +
            "for(var i=0;i<objs.length;i++)  " +
            "{"
            + "    objs[i].onclick=function()  " +
            "    {  "
            + "        window.imageListener.openImage(this.src);  " +
            "    }  " +
            "}" +
            "})()";
    public static final String USER_AGENT_PC = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
}
