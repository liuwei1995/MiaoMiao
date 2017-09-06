package com.sohuvideo.ui_plugin.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sohu.lib.net.util.SohuImageView;
import com.sohu.lib.utils.StringUtils;
import com.sohuvideo.ui_plugin.listener.OnLoadMoreClickListener;
import com.sohuvideo.ui_plugin.manager.NetRequestManager;
import com.sohuvideo.ui_plugin.model.Column;
import com.sohuvideo.ui_plugin.model.ItemModel;
import com.sohuvideo.ui_plugin.player.PlayVideoHelper;
import com.sohuvideo.ui_plugin.R;
import com.sohuvideo.ui_plugin.utils.DpiUtil;
import com.sohuvideo.ui_plugin.utils.LayoutConstants;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class ItemColumnListModelHolder extends ItemModelHolder {
    protected Bitmap mDefaultBitmap = null;
    private Context mContext;
    private int margin;

    @Override
    public View initView(Context mContext, BaseViewHolder mBaseViewHolder, OnLoadMoreClickListener listener) {
        margin = DpiUtil.dipToPx(mContext, 5);
        final ViewHolder holder = (ViewHolder) mBaseViewHolder;
        View convertView = View.inflate(mContext, R.layout.layout_item_column_list, null);
        holder.mVideoContainerLL = (LinearLayout) convertView.findViewById(R.id.id_column_holder_list_ll);
        holder.mVideoContainerLL.setClickable(false);
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public void initData(final Context mContext, ItemModel model, BaseViewHolder mBaseViewHolder) {
        final ViewHolder holder = (ViewHolder) mBaseViewHolder;
        this.mContext = mContext;
        if (model == null)
            return;
        List<Column> columns = model.getmColumnList();
        if (columns == null || columns.isEmpty()) {
            holder.mVideoContainerLL.removeAllViews();
            holder.arrItemViewHolder = null;
            return;
        }
        int columnNum = model.getmColumnList().size();
        int numPerRow = 2;
        if (columnNum >= numPerRow) {
            columnNum = numPerRow;
        }
        if (holder.arrItemViewHolder == null || holder.arrItemViewHolder.length != columnNum) {
            holder.mVideoContainerLL.removeAllViews();
            holder.arrItemViewHolder = new ItemViewHolder[columnNum];
            for (int i = 0; i < columnNum; i++) {
                View itemView = View.inflate(mContext, R.layout.layout_item_view_holder, null);
                final Column column = model.getmColumnList().get(i);
                holder.arrItemViewHolder[i] = new ItemViewHolder(itemView);
                holder.arrItemViewHolder[i].setLayoutParams(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                params.leftMargin = margin;
                holder.mVideoContainerLL.addView(itemView, params);
                if (!column.isShow()) {
                    itemView.setVisibility(View.INVISIBLE);
                } else {
                    itemView.setVisibility(View.VISIBLE);
                    holder.arrItemViewHolder[i].loadData(column);
                    initImage(holder.arrItemViewHolder[i].iv, column);
                    holder.arrItemViewHolder[i].ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PlayVideoHelper.playSohuOnlineVideo(mContext,column.getAid(),
                                    column.getVid(),column.getSite(),0 ,PlayVideoHelper.TYPE_FOR_PGC);
                        }
                    });
                }
            }
        } else {
            for (int i = 0; i < columnNum; i++) {
                final Column column = model.getmColumnList().get(i);
                if (!column.isShow()) {
                    holder.mVideoContainerLL.getChildAt(1).setVisibility(View.INVISIBLE);
                } else {
                    holder.mVideoContainerLL.getChildAt(1).setVisibility(View.VISIBLE);
                    holder.arrItemViewHolder[i].loadData(column);
                    initImage(holder.arrItemViewHolder[i].iv,column);
                    holder.arrItemViewHolder[i].ll.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PlayVideoHelper.playSohuOnlineVideo(mContext,column.getAid(),
                                    column.getVid(),column.getSite(),0 ,PlayVideoHelper.TYPE_FOR_PGC);
                        }
                    });
                }
            }
        }
    }


    private void initImage(SohuImageView iv, Column column) {
        String imgUrl = column.getHor_w8_pic();
        if (StringUtils.isEmpty(imgUrl)) {
            imgUrl = column.getHor_high_pic();
        }
        if (StringUtils.isEmpty(imgUrl)) {
            imgUrl = column.getVer_high_pic();
        }
        iv.setTag(imgUrl);
        NetRequestManager.getRequestManager().displayImage(imgUrl, iv, getDefaultBitmap(mContext));
    }

    @Override
    public BaseViewHolder getHolder() {
        return new ViewHolder(ItemColumnListModelHolder.this);
    }

    class ViewHolder extends BaseViewHolder {
        public ViewHolder(ItemColumnListModelHolder mBaseHolderManager) {
            super(mBaseHolderManager);
        }

        private LinearLayout mVideoContainerLL;
        private ItemViewHolder[] arrItemViewHolder;
    }

    private static class ItemViewHolder {
        public LinearLayout ll;
        public SohuImageView iv;
        public TextView tv_name;
        public TextView tv_info;

        public ItemViewHolder(View convertView) {
            ll = (LinearLayout) convertView.findViewById(R.id.id_itemview_holder_list_ll);
            iv = (SohuImageView) convertView.findViewById(R.id.id_itemview_holder_iv);
            tv_info = (TextView) convertView.findViewById(R.id.id_itemview_holder_info_tv);
            tv_name = (TextView) convertView.findViewById(R.id.id_itemview_holder_name_tv);
        }

        public void loadData(Column column) {
            if (column == null) {
                return;
            }
            tv_name.setText(column.getVideo_name());
            tv_info.setText(column.getAlbum_name());
        }

        // 计算图片大小
        public void setLayoutParams(Context mContext) {
            LayoutConstants.reInitLayoutConstants(mContext);
            iv.getLayoutParams().width = LayoutConstants.sHorVideoImgWidth;
            iv.getLayoutParams().height = LayoutConstants.sHorVideoImgHeight;
        }
    }

    private int getDefaultBkId() {
        return R.drawable.video_item_default_img;
    }

    Bitmap getDefaultBitmap(Context context) {
        if (mDefaultBitmap == null) {
            mDefaultBitmap = getBitmap(context, getDefaultBkId());
        }
        return mDefaultBitmap;
    }

    private synchronized Bitmap getBitmap(Context context, int res) {
        if (context == null || res <= 0) {
            return null;
        }
        Bitmap bmp = null;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(res);
        try {
            bmp = BitmapFactory.decodeStream(is, null, opt);
        } catch (OutOfMemoryError e) {
        } catch (Throwable t) {
            t.printStackTrace();
            System.gc();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            is = null;
        }
        return bmp;
    }
}
