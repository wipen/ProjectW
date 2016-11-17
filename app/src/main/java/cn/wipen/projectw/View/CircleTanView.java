package cn.wipen.projectw.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import cn.wipen.projectw.R;

/**
 * Created by wipenHan on 2016/11/11.
 */
public class CircleTanView extends View {

    private Bitmap mBitmap;
    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mAnimatorValue;
    private float mLenth;
    private Matrix matrix;

    public CircleTanView(Context context) {
        super(context);
    }

    public CircleTanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPathMeasure = new PathMeasure();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath = new Path();
        mPath.addCircle(120, 120, 100, Path.Direction.CW);//center of circle x,y, radius, clockwise
        mPathMeasure.setPath(mPath, true);
        mLenth = mPathMeasure.getLength();

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow_right);
        matrix = new Matrix();

        initAnimator();
    }

    private void initAnimator() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();//reDraw the View
            }
        });
        valueAnimator.setDuration(2000);//Sets the length of the animation.
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    public CircleTanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float stop = mLenth * mAnimatorValue;
        float[] pos = new float[2];
        float[] tan = new float[2];
        mPathMeasure.getPosTan(stop, pos, tan);
        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);//计算旋转角度
        matrix.reset();
        int width = mBitmap.getWidth();
        int halfHeight = mBitmap.getHeight() / 2;
        matrix.postRotate(degrees, width, halfHeight);
        matrix.postTranslate(pos[0] - halfHeight, pos[1] - halfHeight);
        canvas.drawBitmap(mBitmap, matrix, null);
        canvas.drawPath(mPath, mPaint);
    }
}
