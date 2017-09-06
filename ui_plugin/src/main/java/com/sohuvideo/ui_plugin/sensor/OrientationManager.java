package com.sohuvideo.ui_plugin.sensor;


import android.content.Context;
import android.view.OrientationEventListener;

public final class OrientationManager extends OrientationEventListener {

    private Side localOrientation = Side.UNKONWN;

    // 设备方向
    public enum Side {
        UNKONWN, TOP, LEFT, BOTTOM, RIGHT
    }

    public interface OnOrientationListener {
        void onOrientationChanged(Side currentSide);
    }

    private OnOrientationListener mOrientationListener;

    public OrientationManager(Context context, int rate) {
        super(context, rate);
    }

    public OrientationManager(Context context) {
        super(context);
    }

    @Override
    public void onOrientationChanged(int orientation) {
        boolean initiated = true;
        if (localOrientation == Side.UNKONWN) {
            initiated = false;
            initLocalOrientation(orientation);
        }
        if ((orientation >= 0 && orientation <= 30) || (orientation >= 330 && orientation <= 360)) {
            if ((localOrientation != Side.TOP || !initiated)
                    && (localOrientation == Side.LEFT || localOrientation == Side.RIGHT)) {
                if (mOrientationListener != null) {
                    mOrientationListener.onOrientationChanged(Side.TOP);
                }
            }
            localOrientation = Side.TOP;
        } else if ((orientation >= 60) && orientation <= 120) {
            if (localOrientation != Side.LEFT || !initiated) {
                if (mOrientationListener != null) {
                    mOrientationListener.onOrientationChanged(Side.LEFT);
                }
            }
            localOrientation = Side.LEFT;
        } else if (orientation >= 150 && orientation <= 210) {
            if (localOrientation != Side.BOTTOM || !initiated) {
                if (mOrientationListener != null) {
                    mOrientationListener.onOrientationChanged(Side.BOTTOM);
                }
            }
            localOrientation = Side.BOTTOM;
        } else if (orientation >= 240 && orientation <= 300) {
            if (localOrientation != Side.RIGHT || !initiated) {
                if (mOrientationListener != null) {
                    mOrientationListener.onOrientationChanged(Side.RIGHT);
                }
            }
            localOrientation = Side.RIGHT;
        }
    }

    private void initLocalOrientation(int orientation) {
        if ((orientation >= 0 && orientation <= 30) || (orientation >= 330 && orientation <= 360)) {
            localOrientation = Side.TOP;
        } else if ((orientation >= 60) && orientation <= 120) {
            localOrientation = Side.LEFT;
        } else if (orientation >= 150 && orientation <= 210) {
            localOrientation = Side.BOTTOM;
        } else if (orientation >= 240 && orientation <= 300) {
            localOrientation = Side.RIGHT;
        }
    }

    public void setOnOrientationListener(OnOrientationListener listener) {
        mOrientationListener = listener;
    }

    /**
     * 获取当前方向
     *
     * @return
     */
    public Side getCurrentSide() {
        if (localOrientation == Side.UNKONWN) {
            return null;
        }

        return localOrientation;
    }
}
