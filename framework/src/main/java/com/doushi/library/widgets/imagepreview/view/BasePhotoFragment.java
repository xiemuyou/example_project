package com.doushi.library.widgets.imagepreview.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.SafeKeyGenerator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.EmptySignature;
import com.doushi.library.R;
import com.doushi.library.thread.AbstractSafeThread;
import com.doushi.library.thread.ThreadPool;
import com.doushi.library.util.FileUtils;
import com.doushi.library.widgets.ToastUtils;
import com.doushi.library.widgets.imagepreview.GPreviewActivity;
import com.doushi.library.widgets.imagepreview.enitity.DataCacheKey;
import com.doushi.library.widgets.imagepreview.enitity.IThumbViewInfo;
import com.doushi.library.widgets.imagepreview.photoview.PhotoViewAttacher;
import com.doushi.library.widgets.imagepreview.progress.CircleProgressView;
import com.doushi.library.widgets.imagepreview.progress.OnProgressListener;
import com.doushi.library.widgets.imagepreview.progress.ProgressManager;
import com.doushi.library.widgets.imagepreview.wight.SmoothImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;


/**
 * author yangc
 * date 2017/4/26
 * E-Mail:yangchaojiang@outlook.com
 * Deprecated: 图片预览单个图片的fragment
 */
public class BasePhotoFragment extends Fragment {
    /**
     * 预览图片 类型
     */
    private static final String KEY_TRANS_PHOTO = "is_trans_photo";
    private static final String KEY_SING_FILING = "isSingleFling";
    private static final String KEY_PATH = "key_item";
    private static final String KEY_DRAG = "isDrag";
    private static final String KEY_STRATEGY = "isStrategy";
    private static final String KEY_CIRCLE = "isCircle";

    private IThumbViewInfo beanViewInfo;
    private boolean isTransPhoto = false;
    protected SmoothImageView imageView;
    protected View rootView;
    protected CircleProgressView loading;
    private boolean isLoadError = false;
    private boolean isStrategy = false;
    private boolean isCircle = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_photo, container, false);
    }

    public static BasePhotoFragment getInstance(Class<? extends BasePhotoFragment> fragmentClass, IThumbViewInfo item,
                                                boolean currentIndex, boolean isSingleFling, boolean isDrag, boolean isStrategy, boolean isCircle) {
        BasePhotoFragment fragment;
        try {
            fragment = fragmentClass.newInstance();
        } catch (Exception e) {
            fragment = new BasePhotoFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(BasePhotoFragment.KEY_PATH, item);
        bundle.putBoolean(BasePhotoFragment.KEY_TRANS_PHOTO, currentIndex);
        bundle.putBoolean(BasePhotoFragment.KEY_SING_FILING, isSingleFling);
        bundle.putBoolean(BasePhotoFragment.KEY_DRAG, isDrag);
        bundle.putBoolean(BasePhotoFragment.KEY_STRATEGY, isStrategy);
        bundle.putBoolean(BasePhotoFragment.KEY_CIRCLE, isCircle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @CallSuper
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initDate();
    }

    @CallSuper
    @Override
    public void onStop() {
        Glide.with(getContext()).onStop();
        super.onStop();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        release();
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity() != null) {
            Glide.get(getActivity()).clearMemory();
        }
    }

    public void release() {
        if (imageView != null) {
            imageView.setImageBitmap(null);
            imageView.setOnViewTapListener(null);
            imageView.setOnPhotoTapListener(null);
            imageView.setAlphaChangeListener(null);
            imageView.setTransformOutListener(null);
            imageView.transformIn(null);
            imageView.transformOut(null);
            imageView.setOnLongClickListener(null);
            imageView = null;
            rootView = null;
            isTransPhoto = false;
        }
    }

    /**
     * 初始化控件
     */
    private void initView(View view) {
        loading = view.findViewById(R.id.loading);
        imageView = view.findViewById(R.id.photoView);
        rootView = view.findViewById(R.id.rootView);
        rootView.setDrawingCacheEnabled(false);
        imageView.setDrawingCacheEnabled(false);
    }

    /**
     * 初始化数据
     */
    private void initDate() {
        Bundle bundle = getArguments();
        boolean isSingleFling = true;
        if (bundle != null) {
            isSingleFling = bundle.getBoolean(KEY_SING_FILING);

            isCircle = bundle.getBoolean(KEY_CIRCLE);
            isStrategy = bundle.getBoolean(KEY_STRATEGY);
            //地址
            beanViewInfo = bundle.getParcelable(KEY_PATH);
            //位置
            if (beanViewInfo == null) {
                return;
            }
            imageView.setThumbRect(beanViewInfo.getBounds());
            imageView.setDrag(bundle.getBoolean(KEY_DRAG));
            //是否展示动画
            isTransPhoto = bundle.getBoolean(KEY_TRANS_PHOTO, false);

            /*
             * 判断是否使用图片策略
             */
            if (isStrategy) {
                loadImageStrategy();
            } else {
                loadImageNormal();
            }
        }
        boolean isCache;
        if (isStrategy) {
            isCache = isCacheGlide(beanViewInfo.get360Url()) || isCacheGlide(beanViewInfo.getUrl()) || isCacheGlide(beanViewInfo.get720Url());
        } else {
            isCache = isCacheGlide(beanViewInfo.get720Url());
        }
        // 非动画进入的Fragment，默认背景为黑色
        if (!isTransPhoto || !isCache) {
            rootView.setBackgroundColor(Color.BLACK);
        } else {
            imageView.setMinimumScale(0.7f);
        }
        if (isSingleFling) {
            imageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    if (imageView.checkMinScale() && getActivity() instanceof GPreviewActivity) {
                        ((GPreviewActivity) getActivity()).transformOut();
                    }
                }
            });
        } else {
            imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    if (imageView.checkMinScale() && getActivity() instanceof GPreviewActivity) {
                        ((GPreviewActivity) getActivity()).transformOut();
                    }
                }

                @Override
                public void onOutsidePhotoTap() {

                }
            });
        }
        imageView.setAlphaChangeListener(new SmoothImageView.OnAlphaChangeListener() {
            @Override
            public void onAlphaChange(int alpha) {
                rootView.setBackgroundColor(getColorWithAlpha(alpha / 255f, Color.BLACK));
            }
        });
        imageView.setTransformOutListener(new SmoothImageView.OnTransformOutListener() {
            @Override
            public void onTransformOut() {
                if (imageView.checkMinScale() && getActivity() instanceof GPreviewActivity) {
                    ((GPreviewActivity) getActivity()).transformOut();
                }
            }
        });
    }

    private void loadImageNormal() {
        if (getActivity() != null) {
            ProgressManager.addListener(beanViewInfo.get720Url(), new OnProgressListener() {
                @Override
                public void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes) {
                    if (isComplete) {
                        if (loading != null) {
                            loading.setVisibility(View.GONE);
                        }
                    } else {
                        if (percentage > 1) {
                            if (loading != null) {
                                loading.setVisibility(View.VISIBLE);
                                loading.setProgress(percentage);
                            }
                        }

                    }
                }
            });
            RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(true);
            if (isCircle) {
                requestOptions = requestOptions.transform(new CircleCrop());
            }
            Glide.with(getActivity())
                    .load(beanViewInfo.get720Url())
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if (loading != null) {
                                loading.setVisibility(View.GONE);
                            }
                            isLoadError = true;
                            ToastUtils.showToast(getContext(), getString(R.string.load_image_fail));
                            if (rootView != null) {
                                rootView.setBackgroundColor(Color.BLACK);
                                rootView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (getActivity() != null && getActivity() instanceof GPreviewActivity) {
                                            ((GPreviewActivity) getActivity()).loadFail();
                                        }
                                    }
                                });
                            }
                            ProgressManager.removeListener(beanViewInfo.get720Url());
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (loading != null) {
                                loading.setVisibility(View.GONE);
                            }
                            isLoadError = false;
                            ProgressManager.removeListener(beanViewInfo.get720Url());
                            return false;
                        }
                    })
                    .into(imageView);
        }
    }

    /**
     * 图片策略加载
     */
    private void loadImageStrategy() {
        if (getActivity() != null) {
            ProgressManager.addListener(beanViewInfo.get720Url(), new OnProgressListener() {
                @Override
                public void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes) {
                    if (isComplete) {
                        loading.setVisibility(View.GONE);
                    } else {
                        if (percentage > 1) {
                            loading.setVisibility(View.VISIBLE);
                            loading.setProgress(percentage);
                        }

                    }
                }
            });
            RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).skipMemoryCache(true);
            if (isCircle) {
                requestOptions = requestOptions.transform(new CircleCrop());
            }

            Glide.with(getActivity())
                    .load(beanViewInfo.get720Url())
                    .thumbnail(Glide.with(getActivity()).load(beanViewInfo.get360Url()).apply(new RequestOptions().fitCenter()).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (loading != null) {
                                loading.setVisibility(View.VISIBLE);
                            }
                            return false;
                        }
                    }))
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if (loading != null) {
                                loading.setVisibility(View.GONE);
                            }

                            isLoadError = true;
                            if (rootView != null) {
                                rootView.setBackgroundColor(Color.BLACK);
                                rootView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (getActivity() != null && getActivity() instanceof GPreviewActivity) {
                                            ((GPreviewActivity) getActivity()).loadFail();
                                        }
                                    }
                                });
                            }
                            ToastUtils.showToast(getContext(), getString(R.string.load_image_fail));
                            ProgressManager.removeListener(beanViewInfo.get720Url());
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (loading != null) {
                                loading.setVisibility(View.GONE);
                            }
                            isLoadError = false;
                            ProgressManager.removeListener(beanViewInfo.get720Url());
                            return false;
                        }
                    })
                    .into(imageView);
        }
    }

    public boolean isCacheGlide(String id) {
        if (getActivity() == null) {
            return false;
        }
        DataCacheKey dataCacheKey = new DataCacheKey(new GlideUrl(id), EmptySignature.obtain());
        SafeKeyGenerator safeKeyGenerator = new SafeKeyGenerator();
        String safeKey = safeKeyGenerator.getSafeKey(dataCacheKey);
        try {
            int cacheSize = 100 * 1000 * 1000;
            DiskLruCache diskLruCache = DiskLruCache.open(new File(getActivity().getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR), 1, 1, cacheSize);
            DiskLruCache.Value value = diskLruCache.get(safeKey);
            if (value != null) {
                return value.getFile(0) != null;
            }
            return false;
        } catch (Throwable ex) {
            return false;
        }
    }


    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

    public void transformIn() {
        if (imageView != null && rootView != null) {
            imageView.transformIn(new SmoothImageView.onTransformListener() {
                @Override
                public void onTransformCompleted(SmoothImageView.Status status) {
                    rootView.setBackgroundColor(Color.BLACK);
                }
            });
        }
    }

    public void transformOut(SmoothImageView.onTransformListener listener) {
        if (imageView != null) {
            imageView.transformOut(listener);
        }
    }

    public void resetMatrix() {
        if (imageView != null) {
            imageView.resetMatrix();
        }
    }

    public void changeBg(int color) {
        if (rootView != null) {
            rootView.setBackgroundColor(color);
        }
    }

    public IThumbViewInfo getBeanViewInfo() {
        return beanViewInfo;
    }

    public void saveBitmap() {
        Drawable drawable = imageView.getDrawable();
        String imagePath;
        final String dirPath = FileUtils.getInstance().getDesFilePath(FileUtils.FilePathType.IMG);
        final String fileName;
        if (drawable instanceof GifDrawable) {
            fileName = System.currentTimeMillis() + ".gif";
            imagePath = dirPath + fileName;
        } else {
            fileName = System.currentTimeMillis() + ".jpg";
            imagePath = dirPath + fileName;
        }
        final String finalImagePath = imagePath;
        ThreadPool.execute(new AbstractSafeThread() {
            @Override
            public void deal() {
                //必须在子线程中进行
                String path = getImagePath();
                copyFile(path, finalImagePath);
                if (getActivity() != null) {
                    String extension = MimeTypeMap.getFileExtensionFromUrl(finalImagePath);
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    MediaScannerConnection.scanFile(getActivity().getApplicationContext(),
                            new String[]{finalImagePath},
                            new String[]{mimeType},
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                }
                            });
                }
            }
        });
    }

    /**
     * Glide 获得图片缓存路径
     */
    private String getImagePath() {
        String path = null;
        FutureTarget<File> future = Glide.with(this)
                .load(beanViewInfo.getUrl())
                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        try {
            File cacheFile = future.get();
            path = cacheFile.getAbsolutePath();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return path;
    }

    public void copyFile(String oldPath, final String newPath) {
        try {
            int byteread;
            File oldfile = new File(oldPath);
            //文件存在时
            if (oldfile.exists()) {
                //读入原文件
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    //字节数 文件大小
//                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    ToastUtils.showToast(getContext(), "保存在" + newPath);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public boolean getLoadingVisiable() {
        if (loading != null && imageView != null) {
            return loading.getVisibility() == View.VISIBLE || isLoadError || imageView.getDrawable() == null;
        }
        return true;
    }
}
