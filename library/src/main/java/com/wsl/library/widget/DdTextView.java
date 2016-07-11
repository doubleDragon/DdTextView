package com.wsl.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wsl on 16-7-11.
 */
public class DdTextView extends View {

    private static final int LOCATION_LEFT = 0;
    private static final int LOCATION_TOP = 1;
    private static final int LOCATION_RIGHT = 2;
    private static final int LOCATION_BOTTOM = 3;

    private String text;
    private Paint textPaint;
    private Drawable drawable;
    private int drawableLocation;
    private int drawableDimension;

    private Rect contentRect;

    public DdTextView(Context context) {
        this(context, null);
    }

    public DdTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DdTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DdTextView);
        text = typedArray.getString(R.styleable.DdTextView_android_text);
        int textColor = typedArray.getColor(R.styleable.DdTextView_android_textColor, Color.RED);
        int textSize = typedArray.getDimensionPixelOffset(R.styleable.DdTextView_android_textSize, 30);
        drawable = typedArray.getDrawable(R.styleable.DdTextView_drawable);
        drawableLocation = typedArray.getInt(R.styleable.DdTextView_drawable_location, LOCATION_LEFT);
        drawableDimension = typedArray.getDimensionPixelOffset(R.styleable.DdTextView_drawable_dimension, 0);

        typedArray.recycle();

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.LEFT);

        contentRect = new Rect();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        setupBounds(w, h);
    }

    private void setupBounds(int width, int height) {
        contentRect.set(getPaddingLeft(), getPaddingTop(), width - getPaddingRight(),
                height - getPaddingBottom());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(contentRect.left, contentRect.top);

        float textWidth = textPaint.measureText(text);

        int drawableWidth = drawable.getIntrinsicWidth();
        int drawableHeight = drawable.getIntrinsicHeight();
        int drawableLeft = (int) ((contentRect.width() - textWidth - drawableDimension - drawableWidth) / 2);
        int drawableTop = (contentRect.height() - drawableHeight) / 2;


        //draw drawable
        drawable.setBounds(drawableLeft, drawableTop, drawableLeft + drawableWidth, drawableTop + drawableHeight);
        drawable.draw(canvas);

        //draw text
        float textHeight = textPaint.descent() - textPaint.ascent();
        float verticalTextOffset = (textHeight / 2) - textPaint.descent();

        canvas.drawText(text, drawableLeft + drawableWidth + drawableDimension,
                contentRect.height() / 2 + verticalTextOffset, textPaint);

        canvas.restore();
    }
}