package cn.wipen.projectw.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Xfermode相当于一个蒙版，具体各种模式如何运用还需要学习
 * 运用蒙版绘制边框的过程需要再深入学习
 * Created by wipenHan on 2016/11/15.
 */
public abstract class XfermodeImageView extends ImageView{
    private Paint mPaint;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    private Bitmap mMaskBitmap;

    private WeakReference<Bitmap> mWeakBitmap;

    public XfermodeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public XfermodeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public XfermodeImageView(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = mWeakBitmap == null ? null : mWeakBitmap.get();

        if (null == bitmap || bitmap.isRecycled()) {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                int dWidth = drawable.getIntrinsicWidth();
                int dHeight = drawable.getIntrinsicHeight();
                bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

                Canvas drawCanvas = new Canvas(bitmap);
                float scale = mathImageScale(dWidth, dHeight);
                drawable.setBounds(0, 0, (int)(scale * dWidth), (int)(scale * dHeight));
                drawable.draw(drawCanvas);

                if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
                    mMaskBitmap = getMaskBitmap();
                }
                //绘制边框
                mPaint.reset();
                mPaint.setFilterBitmap(false);
                mPaint.setXfermode(mXfermode);
                drawCanvas.drawBitmap(mMaskBitmap, 0, 0, mPaint);
                mPaint.setXfermode(null);
                //绘制图片
                canvas.drawBitmap(bitmap, 0, 0, null);
                mWeakBitmap = new WeakReference<Bitmap>(bitmap);
            }
        } else {
            mPaint.setXfermode(null);
            canvas.drawBitmap(bitmap, 0, 0, mPaint);
        }
    }

    /**
     * 计算图片缩放比例
     * @param drawableWidth
     * @param drawableHeight
     * @return
     */
    protected abstract float mathImageScale(int drawableWidth, int drawableHeight);

    /**
     * 图片边框的绘制
     * @return
     */
    protected abstract Bitmap getMaskBitmap();

    @Override
    public void invalidate() {
        mWeakBitmap = null;
        if (mMaskBitmap != null) {
            mMaskBitmap.recycle();
            mMaskBitmap = null;
        }
        super.invalidate();
    }
}
