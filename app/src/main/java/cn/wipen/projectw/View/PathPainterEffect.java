package cn.wipen.projectw.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by wipenHan on 2016/11/11.
 */
public class PathPainterEffect extends View {
    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private PathEffect mEffect;
    private float fraction = 0;
    private ValueAnimator mAnimator;

    public PathPainterEffect(Context context) {
        super(context);
    }

    public PathPainterEffect(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(0, 400);
        mPath.lineTo(300, 200);
        mPath.close();

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPathMeasure = new PathMeasure(mPath, false);
        final float length = mPathMeasure.getLength();

        mAnimator = ValueAnimator.ofFloat(1, 0);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(2000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (float) animation.getAnimatedValue();
                //用来绘制虚线的
                mEffect = new DashPathEffect(new float[]{length, length}, fraction * length);
                mPaint.setPathEffect(mEffect);
                invalidate();
            }
        });
        mAnimator.start();
    }

    public PathPainterEffect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }
}
