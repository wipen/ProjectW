package cn.wipen.projectw.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

import cn.wipen.projectw.R;

/**
 * Created by wipenHan on 2016/11/15.
 */
public class RoundImageViewByXfermode extends XfermodeImageView{
    /**
     * 圆角大小的默认值
     */
    private static final int BODER_RADIUS_DEFALUT = 10;
    /**
     * 圆角大小
     */
    private int mBorderRadius;

    public RoundImageViewByXfermode(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundImageViewByXfermode);
        int defValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                BODER_RADIUS_DEFALUT, getResources().getDisplayMetrics());
        mBorderRadius = typedArray.getDimensionPixelSize(R.styleable.RoundImageViewByXfermode_borderRadius,
                defValue);
        typedArray.recycle();
    }

    @Override
    protected float mathImageScale(int drawableWidth, int drawableHeight) {
        return Math.max(getWidth() * 1.0f / drawableWidth, getHeight() * 1.0f / drawableHeight);
    }

    @Override
    protected Bitmap getMaskBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        canvas.drawRoundRect(new RectF(0, 0, getWidth(), getHeight()),
                mBorderRadius, mBorderRadius, paint);
        return bitmap;
    }

}
