package com.doushi.library.widgets.htmlTextView;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;

import java.io.InputStream;

/**
 * @author zhangw
 * @date 2017/9/1.
 * email: zhangwei@kingnet.com
 */
public class HtmlTextView extends AppCompatTextView {

    public static final String TAG = "HtmlTextView";
    public static final boolean DEBUG = true;

    public HtmlTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlTextView(Context context) {
        super(context);
    }

    /**
     * @param is
     * @return
     */
    static private String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * Parses String containing HTML to Android's Spannable format and displays
     * it in this TextView.
     *
     * @param html String containing HTML, for example: "<b>Hello world!</b>"
     */
    public void setHtmlFromString(String html) {
        Html.ImageGetter imgGetter = new NetWorkImageGetter(this, getContext());

        // this uses Android's Html class for basic parsing, and HtmlTagHandler
        setText(Html.fromHtml(html, imgGetter, new HtmlTagHandler()));

        // make links work
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void setHtmlFromString(String html, int roundPx, int uiWidth) {
        Html.ImageGetter imgGetter = new NetWorkImageGetter(this, getContext(), roundPx, uiWidth);

        // this uses Android's Html class for basic parsing, and HtmlTagHandler
        setText(Html.fromHtml(html, imgGetter, new HtmlTagHandler()));

        // make links work
        setMovementMethod(LinkMovementMethod.getInstance());
    }
}
