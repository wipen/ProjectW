package cn.wipen.projectw.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by wipenHan on 2016/11/15.
 */
public class CircleImageVIew extends XfermodeImageView{
    public CircleImageVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CircleImageVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageVIew(Context context) {
        super(context);
    }

    @Override
    protected float mathImageScale(int drawableWidth, int drawableHeight) {
        return getWidth() * 1.0f / Math.min(drawableHeight, drawableHeight);
    }

    @Override
    protected Bitmap getMaskBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        int radius = getWidth() / 2;
        canvas.drawCircle(radius, radius, radius, paint);
        return bitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.min(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, width);
    }

}
