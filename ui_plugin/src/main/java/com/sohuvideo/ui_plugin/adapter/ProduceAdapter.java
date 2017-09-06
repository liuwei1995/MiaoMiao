package com.sohuvideo.ui_plugin.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sohu.lib.net.util.SohuImageView;
import com.sohuvideo.ui_plugin.R;
import com.sohuvideo.ui_plugin.manager.NetRequestManager;
import com.sohuvideo.ui_plugin.model.ProducerVideoData;
import com.sohuvideo.ui_plugin.utils.BitmapUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * TAB 出品人
 */
public class ProduceAdapter extends BaseAdapter {
    private static final String TAG = ProduceAdapter.class.getSimpleName();
    private Context mContext;
    private List<ProducerVideoData> videos;
    protected Bitmap mDefaultBitmap = null;

    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;

    private OnButtonClickListener mOnButtonClickListener;

    public ProduceAdapter(Context mContext) {
        this.mContext = mContext;
        this.videos = new ArrayList<ProducerVideoData>();
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int position) {
        return videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.layout_item_produce_list, null);
            holder = new ViewHolder();
            holder.produce_videoview_rl = (RelativeLayout) convertView.findViewById(R.id.id_produce_videoview_rl);

            holder.produce_control_layout = (RelativeLayout) convertView.findViewById(R.id.id_produce_control_layout);
            holder.produce_control_play_pause_img = (ImageView) convertView.findViewById(R.id.id_produce_play_pause_img);
            holder.produce_control_seekbar = (SeekBar) convertView.findViewById(R.id.id_produce_seekbar);
            holder.produce_control_total_time = (TextView) convertView.findViewById(R.id.id_produce_total_time_text);
            holder.produce_control_current_time = (TextView) convertView.findViewById(R.id.id_produce_current_time_text);

            holder.produce_sohu_image = (SohuImageView) convertView.findViewById(R.id.id_produce_iv);

            holder.produce_start_play = (ImageView) convertView.findViewById(R.id.id_produce_start_play);

            holder.produce_progress_bar = (ProgressBar) convertView.findViewById(R.id.id_produce_progress_bar);

            holder.produce_time = (TextView) convertView.findViewById(R.id.id_produce_time_tv);
            holder.produce_tip = (TextView) convertView.findViewById(R.id.id_produce_tip_tv);

            holder.produce_title = (TextView) convertView.findViewById(R.id.id_produce_title_tv);
            holder.produce_playcount = (TextView) convertView.findViewById(R.id.id_produce_playcount_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final ProducerVideoData data = videos.get(position);
        holder.produce_time.setText(data.getPublish_time());
        holder.produce_tip.setText(data.getVideo_name());

        holder.produce_title.setText(data.getAlbum_name());
        dealCount(holder, data);

        holder.produce_control_seekbar.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        holder.produce_videoview_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnButtonClickListener.onSohuScreenClick(view);
            }
        });
        holder.produce_control_play_pause_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnButtonClickListener.onPlayPauseClick(view);
            }
        });
        holder.produce_start_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnButtonClickListener.onStartClick(view, position);
            }
        });


        NetRequestManager.getRequestManager().displayImage(data.getHor_w8_pic(), holder.produce_sohu_image, getDefaultBitmap());
        return convertView;
    }


    private void dealCount(ViewHolder holder, ProducerVideoData data) {
        double playCount;
        if (data.getPlay_count() >= 100000000) {
            playCount = data.getPlay_count() * 10 / 100000000 / 10.0;
            holder.produce_playcount.setText(playCount + "亿播放");
        } else if (data.getPlay_count() < 100000000 && data.getPlay_count() > 10000) {
            playCount = data.getPlay_count() * 10 / 10000 / 10.0;
            holder.produce_playcount.setText(playCount + "万播放");
        } else {
            holder.produce_playcount.setText(data.getPlay_count() + "播放");
        }
    }

    public void addData(List<ProducerVideoData> videos) {
        if (this.videos != null) {
            this.videos.addAll(videos);
            notifyDataSetChanged();
        }
    }


    public static class ViewHolder {

        public RelativeLayout produce_videoview_rl;

        public RelativeLayout produce_control_layout;
        public ImageView produce_control_play_pause_img;
        public SeekBar produce_control_seekbar;
        public TextView produce_control_total_time;
        public TextView produce_control_current_time;

        public SohuImageView produce_sohu_image;
        public ImageView produce_start_play;
        public ProgressBar produce_progress_bar;

        public TextView produce_time;
        public TextView produce_tip;

        public TextView produce_title;
        public TextView produce_playcount;
    }

    private Bitmap getDefaultBitmap() {
        if (mDefaultBitmap == null && mContext != null) {
            mDefaultBitmap = getBitmap(mContext, getDefaultBkId());
        }
        return mDefaultBitmap;
    }

    private Bitmap getBitmap(Context mContext2, int defaultBkId) {
        return BitmapUtil.getBitmapFromResource(mContext2, defaultBkId);
    }

    private int getDefaultBkId() {
        return R.drawable.video_item_default_img;
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener listener) {
        this.mOnSeekBarChangeListener = listener;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.mOnButtonClickListener = onButtonClickListener;
    }

    public interface OnButtonClickListener {
        void onPlayPauseClick(View view);

        void onStartClick(View view, int position);

        void onSohuScreenClick(View view);
    }
}
