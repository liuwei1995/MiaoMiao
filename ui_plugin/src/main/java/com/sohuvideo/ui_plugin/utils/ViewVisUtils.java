package com.sohuvideo.ui_plugin.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tension on 15/11/19.
 */
public class ViewVisUtils {

    private static final String TAG = "ViewUtils";

    public ViewVisUtils() {
    }

    public static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if(p == null) {
            p = new ViewGroup.LayoutParams(-1, -2);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if(lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        }

        child.measure(childWidthSpec, childHeightSpec);
    }

    public static void setVisibility(View view, int visibility) {
        if(view == null) {
        } else {
            int current = view.getVisibility();
            if(current != visibility) {
                view.setVisibility(visibility);
            }

        }
    }
}
