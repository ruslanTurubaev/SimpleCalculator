package com.example.simplecalculator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

public class AnimatedView extends View {
    private static final long ANIMATION_DURATION = 500L;
    private float radius;
    private Paint paint = new Paint();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            postDelayed(this, ANIMATION_DURATION);
            setRadius(0f);
        }
    };

    public AnimatedView(Context context) {
        super(context);
    }

    public AnimatedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private float getMaxRadius(){
        int x = getWidth() / 2;
        int y = getHeight();
        return (float) Math.sqrt(Math.pow(x, 2d) + Math.pow(y, 2d));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setAntiAlias(true);
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);

        paint.setColor(typedValue.data);
        int x = getWidth() / 2;
        int y = getHeight();
        canvas.drawCircle(x, y, radius, paint);
        super.onDraw(canvas);
    }

    public void runAnimation(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "radius", 0, getMaxRadius());
        objectAnimator.setDuration(ANIMATION_DURATION);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
        post(runnable);
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public long getAnimationDuration() {
        return ANIMATION_DURATION;
    }
}
