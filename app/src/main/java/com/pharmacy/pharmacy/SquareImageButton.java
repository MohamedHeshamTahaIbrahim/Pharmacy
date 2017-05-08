package com.pharmacy.pharmacy;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

public class SquareImageButton extends ImageButton{

    public SquareImageButton(Context context) {
        super(context);
    }

    public SquareImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SquareImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dims = 0;
        if (widthMeasureSpec > heightMeasureSpec) {
            dims = widthMeasureSpec;
        } else
        {
            dims = heightMeasureSpec;
        }
        setMeasuredDimension(dims, dims);
    }
}