package com.example.simplecalculator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class CustomButton extends AppCompatButton {
    private static final long ANIMATION_DURATION = 500L;
    private int circleAlpha = 0;
    private boolean isEqualButton;
    private Paint paint = new Paint();
    private float radius;
    private int x;
    private int y;

    public CustomButton(@NonNull Context context) {
        super(context);
    }

    public CustomButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton);
        this.isEqualButton = typedArray.getBoolean(0, false);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        setBackgroundColor(getResources().getColor(R.color.transparent));
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();

        if(isEqualButton){
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            setTextColor(typedValue.data);
            theme.resolveAttribute(R.attr.colorPrimaryVariant, typedValue, true);
        }
        else {
            theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true);
        }

        paint.setAntiAlias(true);
        paint.setColor(typedValue.data);
        paint.setAlpha(circleAlpha);
        canvas.drawCircle(x,y,radius,paint);
        super.onDraw(canvas);
    }

    public void runAnimation(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "circleAlpha", 255,0);
        objectAnimator.setDuration(ANIMATION_DURATION);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        x = getWidth() / 2;
        y = getHeight() / 2;
        radius = Math.min(x, y);
        setTextSize(radius / 3);
    }

    public void setCircleAlpha(int circleAlpha) {
        this.circleAlpha = circleAlpha;
        invalidate();
    }
}
