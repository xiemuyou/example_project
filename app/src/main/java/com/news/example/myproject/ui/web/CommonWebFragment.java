package com.news.example.myproject.ui.web;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.news.example.myproject.R;
import com.news.example.myproject.base.component.BaseFragment;
import com.news.example.myproject.global.ParamConstants;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * 供参考
 * https://juejin.im/entry/58db9643570c350058f35446
 *
 * @author xiemy
 * @date 2018/3/2
 */
public class CommonWebFragment extends BaseFragment {

    @BindView(R.id.webView)
    FrameLayout flWebViewContent;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    X5WebView webView;

    public static CommonWebFragment newInstance(String webUrl) {
        CommonWebFragment webFragment = new CommonWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ParamConstants.WEB_URL, webUrl);
        webFragment.setArguments(bundle);
        return webFragment;
    }

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.fragment_common_web;
    }

    @Override
    public void initEnv() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            webView = new X5WebView(_mActivity, null);
            flWebViewContent.addView(webView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT));
            String webUrl = getArguments().getString(ParamConstants.WEB_URL);
            webView.loadUrl(webUrl);
            initWebView();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            //清空缓存
            webView.clearCache(true);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                if (flWebViewContent != null) {
                    flWebViewContent.removeView(webView);
                }
                webView.removeAllViews();
                webView.destroy();
            } else {
                webView.removeAllViews();
                webView.destroy();
                if (flWebViewContent != null) {
                    flWebViewContent.removeView(webView);
                }
            }
            webView = null;
        }
    }
}
