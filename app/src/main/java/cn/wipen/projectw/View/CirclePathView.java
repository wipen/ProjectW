package cn.wipen.projectw.View;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wipenHan on 2016/11/11.
 */
public class CirclePathView extends View {

    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mAnimatorValue;
    private Path mDst;
    private float mLenth;

    public CirclePathView(Context context) {
        super(context);
    }

    public CirclePathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPathMeasure = new PathMeasure();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath = new Path();
        mPath.addCircle(110, 110, 100, Path.Direction.CW);//center of circle x,y, radius, clockwise
        mPathMeasure.setPath(mPath, true);
        mLenth = mPathMeasure.getLength();
        mDst = new Path();

        initAnimator();
    }

    private void initAnimator() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
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

    public CirclePathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDst.reset();
        mDst.lineTo(0, 0);
        float stop = mLenth * mAnimatorValue;
        float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLenth));
        mPathMeasure.getSegment(start, stop, mDst, true);
        canvas.drawPath(mDst, mPaint);
    }
}
