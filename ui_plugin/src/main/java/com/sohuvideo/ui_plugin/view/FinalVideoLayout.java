package com.sohuvideo.ui_plugin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class FinalVideoLayout extends RelativeLayout {
    private boolean mFullScreen = false;

    public FinalVideoLayout(Context context) {
        super(context);
    }

    public FinalVideoLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FinalVideoLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = 0;
        if (mFullScreen) {
            childHeightSize = getMeasuredHeight();
        } else {
            childHeightSize = childWidthSize * 9 / 16;
        }
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean isFullScreen() {
        return mFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.mFullScreen = fullScreen;
        android.view.ViewGroup.LayoutParams lp = getLayoutParams();
        if (mFullScreen) {
            if (lp.height != android.view.ViewGroup.LayoutParams.MATCH_PARENT) {
                lp.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
                setLayoutParams(lp);
                requestLayout();
            }
        } else {
            if (lp.height != android.view.ViewGroup.LayoutParams.WRAP_CONTENT) {
                lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
                setLayoutParams(lp);
                requestLayout();
            }
        }
    }

}
