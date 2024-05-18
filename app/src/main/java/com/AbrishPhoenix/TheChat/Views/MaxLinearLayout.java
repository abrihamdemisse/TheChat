package com.AbrishPhoenix.TheChat;

import android.widget.LinearLayout;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;


public class MaxLinearLayout extends LinearLayout {
    
    private int maxWidth = 0;

    public MaxLinearLayout(Context context) {
        super(context);
    }

    public MaxLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MaxLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MaxLinearLayout);
        maxWidth = ta.getDimensionPixelSize(R.styleable.MaxLinearLayout_maxWidth, 0);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (maxWidth > 0 && measuredWidth > maxWidth) {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, measureMode);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        requestLayout();
    }

    public int getMaxWidth() {
        return maxWidth;
    }
}