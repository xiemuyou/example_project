package com.doushi.library.widgets.imagepreview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.doushi.library.R;
import com.doushi.library.widgets.ToastUtils;
import com.doushi.library.widgets.imagepreview.enitity.IThumbViewInfo;
import com.doushi.library.widgets.imagepreview.progress.ProgressManager;
import com.doushi.library.widgets.imagepreview.view.BasePhotoFragment;
import com.doushi.library.widgets.imagepreview.wight.BezierBannerView;
import com.doushi.library.widgets.imagepreview.wight.PhotoViewPager;
import com.doushi.library.widgets.imagepreview.wight.SmoothImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Deprecated:图片预览页面
 */
public class GPreviewActivity extends FragmentActivity {

    private boolean isTransformOut = false;
    /*** 图片的地址***/
    private List<IThumbViewInfo> imgUrls;
    /*** 当前图片的位置 ***/
    private int currentIndex;
    /*** 图片的展示的Fragment***/
    private List<BasePhotoFragment> fragments = new ArrayList<>();
    /*** 展示图片的viewPager ***/
    private PhotoViewPager viewPager;
    /*** 显示图片数**/
    private TextView ltAddDot;
    /***图片保存**/
    private TextView mTextSave;
    /***指示器控件**/
    private BezierBannerView bezierBannerView;
    /***指示器类型枚举***/
    private GPreviewBuilder.IndicatorType type;
    /***默认显示***/
    private boolean isShow = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (setContentLayout() == 0) {
            setContentView(R.layout.activity_image_preview_photo);
        } else {
            setContentView(setContentLayout());
        }
        initData();
        initView();
    }

// TODO: 2019/6/20
//    @CallSuper
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (setContentLayout() == 0) {
//            setContentView(R.layout.activity_image_preview_photo);
//        } else {
//            setContentView(setContentLayout());
//        }
//        initData();
//        initView();
//    }

    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        if (viewPager != null) {
            viewPager.setAdapter(null);
            viewPager.clearOnPageChangeListeners();
            viewPager.removeAllViews();
            viewPager = null;
        }
        if (fragments != null) {
            fragments.clear();
            fragments = null;
        }
        if (imgUrls != null) {
            imgUrls.clear();
            imgUrls = null;
        }
        ProgressManager.clear();
        super.onDestroy();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        imgUrls = getIntent().getParcelableArrayListExtra("imagePaths");
        currentIndex = getIntent().getIntExtra("position", -1);
        type = (GPreviewBuilder.IndicatorType) getIntent().getSerializableExtra("type");
        isShow = getIntent().getBooleanExtra("isShow", true);
        int duration = getIntent().getIntExtra("duration", 230);
        try {
            SmoothImageView.setDuration(duration);
            Class<? extends BasePhotoFragment> sss;
            sss = (Class<? extends BasePhotoFragment>) getIntent().getSerializableExtra("className");
            iniFragment(imgUrls, currentIndex, sss);
        } catch (Exception e) {
            iniFragment(imgUrls, currentIndex, BasePhotoFragment.class);
        }

    }

    /**
     * 初始化
     *
     * @param imgUrls      集合
     * @param currentIndex 选中索引
     * @param className    显示Fragment
     **/
    protected void iniFragment(List<IThumbViewInfo> imgUrls, int currentIndex, Class<? extends BasePhotoFragment> className) {
        if (imgUrls != null) {
            int size = imgUrls.size();
            boolean s = getIntent().getBooleanExtra("isSingleFling", false);
            boolean isDrag = getIntent().getBooleanExtra("isDrag", false);
            boolean isStrategy = getIntent().getBooleanExtra("isStrategy", false);
            boolean isCircle = getIntent().getBooleanExtra("isCircle", false);
            for (int i = 0; i < size; i++) {
                fragments.add(BasePhotoFragment.getInstance(className, imgUrls.get(i), currentIndex == i, s, isDrag, isStrategy, isCircle));
            }
        } else {
            finish();
        }
    }

    /**
     * 初始化控件
     */
    @SuppressLint("StringFormatMatches")
    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        //viewPager的适配器
        PhotoPagerAdapter adapter = new PhotoPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentIndex);
        viewPager.setOffscreenPageLimit(1);
        bezierBannerView = findViewById(R.id.bezierBannerView);
        ltAddDot = findViewById(R.id.ltAddDot);
        mTextSave = findViewById(R.id.mTextSave);
        if (type == GPreviewBuilder.IndicatorType.Dot) {
            bezierBannerView.setVisibility(View.VISIBLE);
            bezierBannerView.attachToViewpager(viewPager);
        } else {
            ltAddDot.setVisibility(View.VISIBLE);
            ltAddDot.setText(getString(R.string.string_count, (currentIndex + 1), imgUrls.size()));
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    //当被选中的时候设置小圆点和当前位置
                    if (ltAddDot != null) {
                        ltAddDot.setText(getString(R.string.string_count, (position + 1), imgUrls.size()));
                    }
                    currentIndex = position;
                    viewPager.setCurrentItem(currentIndex, true);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        if (fragments.size() == 1) {
            if (!isShow) {
                bezierBannerView.setVisibility(View.GONE);
                ltAddDot.setVisibility(View.GONE);
            }
        }
        viewPager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                viewPager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (fragments != null && fragments.size() > currentIndex) {
                    BasePhotoFragment fragment = fragments.get(currentIndex);
                    fragment.transformIn();
                }
            }
        });

        //点击保存功能
        mTextSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT >= 21) {
                        boolean writeExternalPermission = getPackageManager().checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;
                        if (!writeExternalPermission) {
                            ToastUtils.showToast(GPreviewActivity.this, "请授权写入权限");
                            ActivityCompat.requestPermissions(GPreviewActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1002);

                        } else {
                            if (fragments.get(currentIndex).isAdded()) {
                                fragments.get(currentIndex).saveBitmap();
                            }
                        }
                    } else {
                        if (fragments.get(currentIndex).isAdded()) {
                            fragments.get(currentIndex).saveBitmap();
                        }
                    }
                } catch (Throwable ex) {
                    ToastUtils.showToast(GPreviewActivity.this, "保存失败");
                }
            }
        });
    }

    /***退出预览的动画***/
    public void transformOut() {
        if (isTransformOut) {
            exit();
            return;
        }
        isTransformOut = true;
        int currentItem = viewPager.getCurrentItem();
        if (currentItem < imgUrls.size()) {
            BasePhotoFragment fragment = fragments.get(currentItem);
            if (ltAddDot != null) {
                ltAddDot.setVisibility(View.GONE);
            } else {
                bezierBannerView.setVisibility(View.GONE);
            }
            if (mTextSave != null) {
                mTextSave.setVisibility(View.GONE);
            }
            fragment.changeBg(Color.TRANSPARENT);
            fragment.transformOut(new SmoothImageView.onTransformListener() {
                @Override
                public void onTransformCompleted(SmoothImageView.Status status) {
                    exit();
                }
            });
            if (fragment.getLoadingVisiable()) {
                exit();
            }
        } else {
            exit();
        }
    }


    /***
     * 得到PhotoFragment集合
     * @return List
     * **/
    public List<BasePhotoFragment> getFragments() {
        return fragments;
    }

    /**
     * 关闭页面
     */
    private void exit() {
        finish();
        overridePendingTransition(0, 0);
    }

    public void loadFail() {
//        if (currentIndex == 0) {
//            exit();
//        }
        exit();
    }

    /***
     * 得到PhotoViewPager
     * @return PhotoViewPager
     * **/
    public PhotoViewPager getViewPager() {
        return viewPager;
    }

    /***
     * 自定义布局内容
     * @return int
     ***/
    public int setContentLayout() {
        return 0;
    }

    @Override
    public void onBackPressed() {
        transformOut();
    }

    /**
     * pager的适配器
     */
    private class PhotoPagerAdapter extends FragmentPagerAdapter {

        PhotoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }


}
