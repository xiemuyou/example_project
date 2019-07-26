package com.library.widgets.htmlTextView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;

/**
 * @author zhangw
 * @date 2017/9/1.
 * email: zhangwei@kingnet.com
 */

public class NetWorkImageGetter implements Html.ImageGetter {

    private Context mContext;
    private TextView container;
    private int width;
    private int roundPx;

    /**
     * @param tv
     * @param
     */
    public NetWorkImageGetter(TextView tv, Context context) {
        this.mContext = context;
        this.container = tv;
        width = ScreenUtils.getScreenWidth();
    }

    public NetWorkImageGetter(TextView tv, Context context, int round, int uiWidth) {
        this.mContext = context;
        this.container = tv;
        width = uiWidth;
        this.roundPx = round;
    }

    @Override
    public Drawable getDrawable(String bitmapUrl) {
        final UrlDrawable urlDrawable = new UrlDrawable();
//        ImageLoadUtils.getBitmapByListener(mContext, source, R.drawable.home_small_bg,
//                new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition transition) {
//                        float scaleWidth = ((float) width) / resource.getWidth();
//                        Matrix matrix = new Matrix();
//                        matrix.postScale(scaleWidth, scaleWidth);
//                        resource = Bitmap.createBitmap(resource, 0, 0,
//                                resource.getWidth(), resource.getHeight(),
//                                matrix, true);
//                        urlDrawable.bitmap = resource;
//                        urlDrawable.setBounds(0, 0, resource.getWidth(),
//                                resource.getHeight());
//                        container.invalidate();
//                        container.setText(container.getText());
//                    }
//                });

        return urlDrawable;
    }

    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Bitmap bitmap;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            // override the draw to facilitate refresh function later
            if (bitmap != null) {
                canvas.drawBitmap(getRoundedCornerBitmap(bitmap), 0, 0, getPaint());
            }
        }
    }

    /**
     * 生成圆角图片  drawRoundRect
     */
    private Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rf = new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rf, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }
}
