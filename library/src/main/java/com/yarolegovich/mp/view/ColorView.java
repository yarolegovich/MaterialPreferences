package com.yarolegovich.mp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import com.yarolegovich.mp.R;
import com.yarolegovich.mp.util.Utils;

/**
 * Created by yarolegovich on 06.05.2016.
 */
public class ColorView extends View {

    public static final int SHAPE_CIRCLE = 0;
    public static final int SHAPE_SQUARE = 1;

    private static final int PADDING_DP = 4;

    private Shape currentShape;
    private Paint shapePaint;
    private Paint borderPaint;

    public ColorView(Context context) {
        super(context);
        init(null);
    }

    public ColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        int shapeType = 0;
        int shapeColor = 0, borderColor = 0;
        int borderWidth = 0;

        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ColorView);
            try {
                shapeType = ta.getInt(R.styleable.ColorView_mp_cv_shape, SHAPE_CIRCLE);
                shapeColor = ta.getColor(R.styleable.ColorView_mp_cv_color, Color.WHITE);
                borderColor = ta.getColor(R.styleable.ColorView_mp_cv_border_color, Color.BLACK);
                borderWidth = ta.getDimensionPixelSize(R.styleable.ColorView_mp_cv_border_width, 0);
            } finally {
                ta.recycle();
            }
        }

        currentShape = shapeType == SHAPE_CIRCLE ? new CircleShape() : new SquareShape();
        shapePaint = new Paint();
        shapePaint.setStyle(Paint.Style.FILL);
        shapePaint.setColor(shapeColor);

        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setColor(borderColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int padding = Utils.dpToPixels(getContext(), PADDING_DP);
        super.onMeasure(
                MeasureSpec.makeMeasureSpec(width + padding, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height + padding, MeasureSpec.EXACTLY)
        );
    }

    public void setColor(@ColorInt int color) {
        shapePaint.setColor(color);
        invalidate();
    }

    public void setShape(int shape) {
        currentShape = shape == SHAPE_CIRCLE ? new CircleShape() : new SquareShape();
        invalidate();
    }

    public void setBorderWidth(int pixels) {
        borderPaint.setStrokeWidth(pixels);
        invalidate();
    }

    public void setBorderColor(@ColorInt int color) {
        borderPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        currentShape.measureSelf();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        currentShape.drawSelf(canvas);
    }

    private interface Shape {
        void drawSelf(Canvas canvas);
        void measureSelf();
    }

    private class SquareShape implements Shape {

        private int top, left, bottom, right;

        @Override
        public void drawSelf(Canvas canvas) {
            canvas.drawRect(left, top, right, bottom, shapePaint);
            canvas.drawRect(left, top, right, bottom, borderPaint);
        }

        @Override
        public void measureSelf() {
            int padding = Utils.dpToPixels(getContext(), PADDING_DP);
            top = padding;
            left = padding;
            bottom = getHeight() - padding;
            right = getWidth() - padding;
        }
    }

    private class CircleShape implements Shape {

        private float centerX, centerY, radius;

        @Override
        public void drawSelf(Canvas canvas) {
            canvas.drawCircle(centerX, centerY, radius, shapePaint);
            canvas.drawCircle(centerX, centerY, radius, borderPaint);
        }

        @Override
        public void measureSelf() {
            int padding = Utils.dpToPixels(getContext(), PADDING_DP);
            centerX = ((float) getWidth()) / 2f;
            centerY = ((float) getHeight()) / 2f;
            radius = ((float) Math.min(getWidth(), getHeight()) - padding) / 2;
        }
    }
}
