package com.zhaoyao.miaomiao.util.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * Created by liuwei on 2017/8/17 11:19
 */

public class GlideUtils {

    private static Context mContext;

    public void init(Context context) {
        if (context != null){
            if (context.getApplicationContext() != null){
                mContext = context.getApplicationContext();
            }else {
                mContext = context;
            }
        }
    }

    private static GlideUtils mGlideUtils;

    public static GlideUtils newInstance() {
        if (mGlideUtils == null){
            synchronized (GlideUtils.class){
                if (mGlideUtils == null){
                    mGlideUtils = new GlideUtils();
                }
            }
        }
        return mGlideUtils;
    }
    public void load(String url,final ImageView imageView){
        if (mContext != null)
        Glide.with(mContext).load(url)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if (imageView == null) {
                                 return false;
                            }
                            if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            }
                            ViewGroup.LayoutParams params = imageView.getLayoutParams();
                            int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                            float scale = (float) vw / (float) resource.getIntrinsicWidth();
                            int vh = Math.round(resource.getIntrinsicHeight() * scale);
                            params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                            imageView.setLayoutParams(params);
                            return false;
                        }
                    })
                .into(imageView)
//                .onLoadStarted(mContext.getResources().getDrawable(R.mipmap.app_logo))
                ;
    }

    public void stop() {
        Glide.with(mContext).onStop();
    }

    public void resume() {
        Glide.with(mContext).onStart();
    }

    public void destroy() {
        Glide.with(mContext).onDestroy();
    }
}
