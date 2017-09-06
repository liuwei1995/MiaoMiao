package com.sohuvideo.ui_plugin.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sohu.lib.net.parse.CommonDataParser;
import com.sohu.lib.net.request.DataRequest;
import com.sohu.lib.net.request.listener.DefaultDataResponse;
import com.sohu.lib.net.util.ErrorType;
import com.sohu.lib.net.util.SohuImageView;
import com.sohuvideo.api.SohuPlayerItemBuilder;
import com.sohuvideo.api.SohuPlayerMonitor;
import com.sohuvideo.api.SohuPlayerSDK;
import com.sohuvideo.api.SohuPlayerSetting;
import com.sohuvideo.api.SohuScreenView;
import com.sohuvideo.api.SohuVideoPlayer;
import com.sohuvideo.ui_plugin.R;
import com.sohuvideo.ui_plugin.adapter.ProduceAdapter;
import com.sohuvideo.ui_plugin.control.ResponseDataWrapperSet;
import com.sohuvideo.ui_plugin.manager.NetRequestManager;
import com.sohuvideo.ui_plugin.model.ProducerAlbumData;
import com.sohuvideo.ui_plugin.model.ProducerAlbumListData;
import com.sohuvideo.ui_plugin.model.ProducerInfoData;
import com.sohuvideo.ui_plugin.model.ProducerVideoData;
import com.sohuvideo.ui_plugin.model.ProducerVideoListData;
import com.sohuvideo.ui_plugin.net.URLFactory;
import com.sohuvideo.ui_plugin.player.PlayVideoHelper;
import com.sohuvideo.ui_plugin.utils.BitmapUtil;
import com.sohuvideo.ui_plugin.utils.DpiUtil;
import com.sohuvideo.ui_plugin.utils.LayoutConstants;
import com.sohuvideo.ui_plugin.utils.LogManager;
import com.sohuvideo.ui_plugin.utils.ViewVisUtils;
import com.sohuvideo.ui_plugin.view.CircleImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * 流式播放
 */
public class SohuStreamFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = SohuStreamFragment.class.getSimpleName();

    private ProducerInfoData data;    //出品人信息
    private List<ProducerAlbumData> albums;  //出品人所有专辑
    private List<ProducerVideoData> videos; //出品人视频信息
    private List<ProducerVideoData> allVideos;

    private ListView mListView;
    private View view;
    private View headerView;
    private View mFooterView;
    private View mFootLoadingView;   //滚动加载更多的进度条
    private View mEndView;
    private LinearLayout netErrorView;
    private LinearLayout mLoadingView;
    private Button mTryBt;

    private Context mContext;
    private ProduceAdapter adapter;


    private boolean isLoadingMore = false;
    private int mCurrentPage = 1;
    private int pageCount = 20;
    private int margin;

    private SohuVideoPlayer mPlayer;
    private int mCurrent; //当前进度值
    private int mDuration;//总时间
    private boolean mDragging;//seekBar是否再被拖动

    private boolean needAutoPlay = false;//页面进入后台的时候记录是否返回需要自动播放

    private int mCurrentPlayPositon = 0;
    private ProduceAdapter.ViewHolder mCurrentHolder;
    private static final int AUTO_SHOWN_SPAN = 5000;
    private static final int MSG_HIDE = 0;

    private static final int MSG_HIDE_PLAY_CONTROLER = 1000;
    private static final int MSG_WAIT_PLAY = 1001;

    private PlayHandler mHandler;

    static class PlayHandler extends Handler {

        private final WeakReference<SohuStreamFragment> mWeakReference;

        public PlayHandler(SohuStreamFragment fragment) {
            mWeakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            SohuStreamFragment fragment = mWeakReference.get();
            if (fragment == null) {
                return;
            }
            switch (msg.what) {
                case MSG_HIDE:
                    fragment.hideControl();
                    break;
                case MSG_WAIT_PLAY:
                    int playPostion = (int) msg.obj;
                    fragment.playVideoAtPositon(playPostion);
                    break;
            }

        }
    }

    public SohuStreamFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (Context) activity;
        mHandler = new PlayHandler(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sohu_stream, container, false);
        Log.d("tension", "onCreateView");
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initHeaderView();
        initFootView();
        setUpViews();
        initEvent();

        loadDataFromNet();
    }

    @Override
    public void onResume() {
        super.onResume();
        initPlayer();
        if (needAutoPlay) {
            Log.d("lishan", "onResume needAutoPlay = " + needAutoPlay);
            sendDelayPlayMsg(mListView.getFirstVisiblePosition());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer != null && mPlayer.isPlaybackState()) {
            needAutoPlay = true;
        }
        if (mPlayer != null) {
            mPlayer.stop(false);
        }
        releasePlayer();

        Log.d("lishan", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("lishan", "onStop");
    }


    private void initEvent() {
        mTryBt.setOnClickListener(this);
    }

    private void initData() {
        margin = DpiUtil.dipToPx(mContext, 5);
        adapter = new ProduceAdapter(mContext);
        allVideos = new ArrayList<ProducerVideoData>();
    }

    private void setUpViews() {
        netErrorView = (LinearLayout) view.findViewById(R.id.net_error);
        mTryBt = (Button) netErrorView.findViewById(R.id.id_try_bt);
        mLoadingView = (LinearLayout) view.findViewById(R.id.id_lodingdata);
        mListView = (ListView) view.findViewById(R.id.id_producer_lv);
        mListView.addHeaderView(headerView);
        mListView.addFooterView(mFooterView);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                          @Override
                                          public void onScrollStateChanged(AbsListView view, int scrollState) {
                                              if (view == null)
                                                  return;
                                              if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                                                  if (view.getLastVisiblePosition() + 1 == view.getCount()) {
                                                      if (isLoadingMore) {
                                                          return;
                                                      }
                                                      mFooterView.setVisibility(View.VISIBLE);
                                                      loadVideoData();
                                                  }
                                              }

                                              switch (scrollState) {
                                                  case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: //空闲状态
                                                      LogManager.e(TAG, "SCROLL_STATE_IDLE");
                                                      if (mPlayer != null && !mPlayer.isPlaybackState()) {
                                                          sendDelayPlayMsg(mListView.getFirstVisiblePosition());
                                                          return;
                                                      }
                                                      if (mListView.getFirstVisiblePosition() == mCurrentPlayPositon) {
                                                          return;
                                                      }
                                                      resetHolderState();
                                                      sendDelayPlayMsg(mListView.getFirstVisiblePosition());
                                                      break;
                                              }
                                          }

                                          @Override
                                          public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                              if (firstVisibleItem > 0) {
                                                  if (mFooterView != null) {
                                                      mFooterView.setVisibility(View.VISIBLE);
                                                  }
                                              }
                                              if (firstVisibleItem >= mCurrentPlayPositon + 2 || firstVisibleItem <= mCurrentPlayPositon - 2) {
                                                  resetHolderState();
                                              }
                                          }
                                      }

        );
        adapter.setOnSeekBarChangeListener(createOnSeekBarChangeListener());
        adapter.setOnButtonClickListener(onButtonClickListener);
        mListView.setAdapter(adapter);
    }

    private void resetHolderState() {
        LogManager.e(TAG, "resetHolderState ");
        //停止当前的，并且UI恢复
        if (mPlayer != null) {
            mPlayer.stop(false);
        }
        if (mCurrentHolder != null) {
            LogManager.e(TAG, "mCurrentHolder !=null resetHolderState");
            ViewVisUtils.setVisibility(mCurrentHolder.produce_start_play, View.VISIBLE);
            ViewVisUtils.setVisibility(mCurrentHolder.produce_sohu_image, View.VISIBLE);
            ViewVisUtils.setVisibility(mCurrentHolder.produce_control_layout, View.GONE);
            ViewVisUtils.setVisibility(mCurrentHolder.produce_progress_bar, View.GONE);
            removeVideoView();
        } else {
            LogManager.e(TAG, "mCurrentHolder  ==null");
        }
    }


    private void stateReset() {
        mListView.setVisibility(View.GONE);
        netErrorView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
    }

    protected void showErrorView() {
        netErrorView.setVisibility(View.VISIBLE);
    }

    /**
     * 显示正在读取数据
     */
    protected void showLoadingDataView() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void initFootView() {
        mFooterView = LayoutInflater.from(getActivity()).inflate(
                R.layout.listview_footer_end_channel, null);
        mFootLoadingView = mFooterView.findViewById(R.id.more_loading);
        mEndView = mFooterView.findViewById(R.id.more_end);
    }

    private void initHeaderView() {
        if (mContext == null)
            return;
        headerView = View.inflate(mContext, R.layout.layout_sohu_stream, null);
    }

    private void loadDataFromNet() {
        {
            stateReset();
            showLoadingDataView();
            DataRequest dataRequest = new DataRequest(URLFactory.getProducerDaraUrl());
            LogManager.e(TAG, URLFactory.getProducerDaraUrl());
            dataRequest.setResultParser(new CommonDataParser(ResponseDataWrapperSet.ProducerDataWrapper.class));
            DefaultDataResponse response = new DefaultDataResponse() {
                @Override
                public void onSuccess(Object result, boolean b) {
                    LogManager.e(TAG, result.toString());
                    if (result != null) {
                        ResponseDataWrapperSet.ProducerDataWrapper wrapper = (ResponseDataWrapperSet.ProducerDataWrapper) result;
                        data = wrapper.getData();
                        if (data == null) {
                            stateReset();
                            showErrorView();
                            return;
                        }
                        updateHeaderView();
                        loadAlbumData();
                        loadVideoData();
                    }
                }

                @Override
                public void onFailure(ErrorType errorType) {
                    LogManager.e("MTH___loadDataFromNet -SohuStreamFragment.onFailure");
                    stateReset();
                    showErrorView();
                }
            };
            NetRequestManager.getRequestManager().startDataRequestAsync(dataRequest, response);
        }
    }

    /**
     * 显示出品人信息
     */
    private void updateHeaderView() {
        if (headerView == null) {
            return;
        }

        CircleImageView header = (CircleImageView) headerView.findViewById(R.id.id_header_iv);
        TextView name = (TextView) headerView.findViewById(R.id.id_producername_tv);
        TextView videoCount = (TextView) headerView.findViewById(R.id.id_videocount_tv);
        TextView fansCount = (TextView) headerView.findViewById(R.id.id_fanscount_tv);
        TextView playCount = (TextView) headerView.findViewById(R.id.id_playcount_tv);

        if (data != null) {
            videoCount.setText(data.getTotal_video_count_tip());
            videoCount.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            fansCount.setText(data.getTotal_fans_count_tip());
            fansCount.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            playCount.setText(data.getTotal_play_count_tip());
            playCount.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            name.setText(data.getNickname());
            NetRequestManager.getRequestManager().displayImage(data.getSmall_pic(), header, null);
        }
    }

    /**
     * 出品人专辑
     */
    private void loadAlbumData() {
        DataRequest dataRequest = new DataRequest(URLFactory.getProducerAlbumUrl(data.getUser_id()));
        LogManager.e(TAG, URLFactory.getProducerAlbumUrl(data.getUser_id()));
        dataRequest.setResultParser(new CommonDataParser(ResponseDataWrapperSet.ProducerAlbumListDataWrapper.class));
        DefaultDataResponse response = new DefaultDataResponse() {
            @Override
            public void onSuccess(Object result, boolean b) {
                ResponseDataWrapperSet.ProducerAlbumListDataWrapper wrapper = (ResponseDataWrapperSet.ProducerAlbumListDataWrapper) result;
                ProducerAlbumListData data = wrapper.getData();
                albums = data.getAlbums();
                if (albums == null) {
                    return;
                }
                updateAlbumView();
                updateAlbumContentView();
            }

            @Override
            public void onFailure(ErrorType errorType) {
                LogManager.e("MTH---loadAlbumData -SohuStreamFragment.onFailure");
                stateReset();
                showErrorView();
            }
        };
        NetRequestManager.getRequestManager().startDataRequestAsync(dataRequest, response);
    }

    private void updateAlbumView() {
        if (headerView != null) {
            TextView albumCount = (TextView) headerView.findViewById(R.id.id_albumcount_tv);
            albumCount.setText("专辑(" + albums.size() + ")");
        }
    }

    /**
     * 获得出品人所有视频
     */
    private void loadVideoData() {
        isLoadingMore = true;
        mFootLoadingView.setVisibility(View.VISIBLE);
        DataRequest dataRequest = new DataRequest(URLFactory.getProducerVideoUrl(data.getUser_id(), mCurrentPage, pageCount));
        LogManager.e(TAG, URLFactory.getProducerVideoUrl(data.getUser_id(), mCurrentPage, pageCount));
        dataRequest.setResultParser(new CommonDataParser(ResponseDataWrapperSet.ProducerVideoListDataWrapper.class));
        DefaultDataResponse response = new DefaultDataResponse() {
            @Override
            public void onSuccess(Object result, boolean b) {
                ResponseDataWrapperSet.ProducerVideoListDataWrapper wrapper = (ResponseDataWrapperSet.ProducerVideoListDataWrapper) result;
                ProducerVideoListData data = wrapper.getData();
                videos = data.getVideos();
                if (allVideos != null
                        && allVideos.size() >= data.getCount()) {
                    setNoMoreLoadingDataView();
                    return;
                }
                if (videos == null) {
                    return;
                }
                allVideos.addAll(videos);
                mCurrentPage++;
                isLoadingMore = false;
                updateVideoUi();
            }

            @Override
            public void onFailure(ErrorType errorType) {
                LogManager.e("MTH---loadVideoData -SohuStreamFragment.onFailure");
                isLoadingMore = false;
                stateReset();
                showErrorView();
            }
        };
        NetRequestManager.getRequestManager().startDataRequestAsync(dataRequest, response);
    }

    private void updateVideoUi() {
        stateReset();
        showLv();
        adapter.addData(videos);

        //第一页读取完毕，且第一个视频在展示
        if (mCurrentPage == 2) {
            if (mListView.getFirstVisiblePosition() == 0 && allVideos != null && allVideos.size() > 0) {
                mListView.setSelection(0);
                Log.d("lishan", "getFirstVisiblePosition = " + mListView.getFirstVisiblePosition());
                Log.d("lishan", "updateVideoUi");
                Log.d("lishan", "mCurrentPlayPositon = " + mCurrentPlayPositon);
                sendDelayPlayMsg(mListView.getFirstVisiblePosition());
            }

        }
    }

    private void sendDelayPlayMsg(int pos) {
        mHandler.removeMessages(MSG_WAIT_PLAY);
        Message msg = mHandler.obtainMessage(MSG_WAIT_PLAY);
        msg.obj = pos;//0
        mHandler.sendMessageDelayed(msg, 200);
    }

    protected void setNoMoreLoadingDataView() {
        mFootLoadingView.setVisibility(View.GONE);
        mEndView.setVisibility(View.VISIBLE);
    }

    protected void showLv() {
        mListView.setVisibility(View.VISIBLE);
    }

    /**
     * 添加专辑信息
     */
    private void updateAlbumContentView() {
        LinearLayout ll = (LinearLayout) headerView.findViewById(R.id.id_album_ll);
        for (int i = 0; i < albums.size(); i++) {
            final ProducerAlbumData album = albums.get(i);
            View view = View.inflate(getActivity(), R.layout.layout_item_produce_album, null);
            LinearLayout.LayoutParams paramsItemView = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            paramsItemView.leftMargin = margin;
            //图片
            SohuImageView img = (SohuImageView) view.findViewById(R.id.id_album_iv);
            NetRequestManager.getRequestManager().displayImage(album.getHor_w16_pic(), img, BitmapUtil.getBitmapFromResource(getActivity(), R.drawable.video_item_default_img));
            //名称
            TextView albumName = (TextView) view.findViewById(R.id.id_album_tv);
            albumName.setText(album.getAlbum_name());
            //信息
            TextView albumInfo = (TextView) view.findViewById(R.id.id_album_info_tv);
            albumInfo.setText(album.getPublish_time() + " " + album.getVideo_name());
            updateImgSize(img, albumName, albumInfo);

            ll.addView(view, paramsItemView);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayVideoHelper.playSohuOnlineVideo(mContext, album.getAid(),
                            album.getVid(), album.getSite(), 0, PlayVideoHelper.TYPE_FOR_PGC);
                }
            });
        }
    }

    private void updateImgSize(SohuImageView view, TextView albumName, TextView albumInfo) {
        view.getLayoutParams().width = LayoutConstants.sHorVideoImgWidth;
        view.getLayoutParams().height = LayoutConstants.sHorVideoImgHeight;
        albumName.getLayoutParams().width = LayoutConstants.sHorVideoImgWidth;
        albumInfo.getLayoutParams().width = LayoutConstants.sHorVideoImgWidth;
    }

    @Override
    public void onClick(View v) {
        if (v == mTryBt) {
            loadDataFromNet();
        }
    }

    private SohuPlayerMonitor monitor = new SohuPlayerMonitor() {

        @Override
        public void onPlay() {
            if (mPlayer != null && !mPlayer.isAdvertInPlayback()) {
                showControl();
            }
            if (mCurrentHolder != null) {
                updatePlayPauseState(true);
            }
            super.onPlay();
        }

        @Override
        public void onBuffering(int progress) {
            if (mCurrentHolder != null && mCurrentHolder.produce_progress_bar != null) {
                mCurrentHolder.produce_progress_bar.setVisibility(progress == 100 ? View.GONE : View.VISIBLE);
            }
            super.onBuffering(progress);
        }

        @Override
        public void onProgressUpdated(int currentPosition, int duration) {
            setProgress(currentPosition, duration);
            super.onProgressUpdated(currentPosition, duration);
        }

        @Override
        public void onPause() {
            if (mCurrentHolder != null) {
                updatePlayPauseState(false);
            }
            super.onPause();
        }

        @Override
        public void onStop() {
            if (mCurrentHolder != null) {
                ViewVisUtils.setVisibility(mCurrentHolder.produce_sohu_image, View.VISIBLE);
                ViewVisUtils.setVisibility(mCurrentHolder.produce_start_play, View.VISIBLE);
                hideControl();
                updatePlayPauseState(false);
            }
            super.onStop();
        }

        @Override
        public void onComplete() {
            playNext();
            super.onComplete();
        }
    };

    public void initPlayer() {
        SohuPlayerSetting.setNeedAutoNext(false);
        //小窗口流式播放，设置为广告自行绘制倒计时
        SohuPlayerSDK.showAdCountDown(true);
        if (mPlayer == null) {
            mPlayer = new SohuVideoPlayer();
            mPlayer.setSohuPlayerMonitor(monitor);

        }
    }

    public void releasePlayer() {
        mPlayer.release();
        mPlayer = null;
    }

    /**
     * FirstVisiblePosition 第一个完全可见的item 不包含header 从0开始
     * ChildCount  有几个child显示，包含header，包含不完全显示的。
     *
     * @param position
     */
    public void playVideoAtPositon(int position) {
        if (mCurrentPlayPositon != position) {
            mCurrentPlayPositon = position;
        }
        if (mPlayer != null && allVideos != null && allVideos.size() > position) {
            int playItemPosition = getHitRectBigItem(mListView);
            LogManager.e(TAG, "playItemPosition-----" + playItemPosition);
            mCurrentHolder = (ProduceAdapter.ViewHolder) mListView.getChildAt(playItemPosition).getTag();
            if (mCurrentHolder == null) {
                return;
            }

            if (mListView.getChildAt(playItemPosition+1) != null){
                ProduceAdapter.ViewHolder nextHolder = (ProduceAdapter.ViewHolder) mListView.getChildAt(playItemPosition+1).getTag();
                if (nextHolder.produce_start_play.getVisibility() == View.GONE){
                    ViewVisUtils.setVisibility(nextHolder.produce_start_play,View.VISIBLE);
                }
                if (nextHolder.produce_sohu_image.getVisibility() == View.GONE){
                    ViewVisUtils.setVisibility(nextHolder.produce_sohu_image,View.VISIBLE);
                }
            }

            clearHolderTime();
            ProducerVideoData videoData = allVideos.get(position);
            SohuPlayerItemBuilder mCurrentBuilder =
                    new SohuPlayerItemBuilder("", videoData.getAid(), videoData.getVid(), videoData.getSite());
            mPlayer.setDataSource(mCurrentBuilder);
            ViewVisUtils.setVisibility(mCurrentHolder.produce_start_play, View.GONE);
            ViewVisUtils.setVisibility(mCurrentHolder.produce_sohu_image, View.GONE);
            addVideoView();
            mPlayer.setSohuScreenView((SohuScreenView) mCurrentHolder.produce_videoview_rl.getChildAt(0));
            mPlayer.play();
        }

    }

    private void addVideoView() {
        if (getActivity() == null) {
            return;
        }
        if (mCurrentHolder != null) {
            removeVideoView();
            SohuScreenView mPlayVideoView = new SohuScreenView(getActivity());
            mPlayVideoView.setBackgroundColor(Color.BLACK);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            mCurrentHolder.produce_videoview_rl.addView(mPlayVideoView, params);
        }
    }

    private void removeVideoView() {
        if (mCurrentHolder != null && mCurrentHolder.produce_videoview_rl.getChildCount() > 0) {
            mCurrentHolder.produce_videoview_rl.removeAllViews();
            LogManager.e(TAG, "removeVideoView");
        }
    }

    private void playNext() {
        if (mPlayer != null) {
            mPlayer.stop(false);
            mListView.smoothScrollToPositionFromTop(mCurrentPlayPositon + mListView.getHeaderViewsCount() + 1, mListView.getTop());
        }
    }

    public void setProgress(int current, int duration) {
        if (!mDragging) {
            this.mCurrent = current / 1000;
            this.mDuration = duration / 1000;

            updateProgressView();
        }
    }

    private void updateProgressView() {
        if (mCurrentHolder != null) {
            mCurrentHolder.produce_control_current_time.setText(formatTime(mCurrent) + "/");
            mCurrentHolder.produce_control_total_time.setText(formatTime(mDuration));

            if (mDuration == 0) {
                mCurrentHolder.produce_control_seekbar.setProgress(0);
            } else {
                mCurrentHolder.produce_control_seekbar.setMax(mDuration);
                mCurrentHolder.produce_control_seekbar.setProgress(mCurrent);
            }
        }
    }

    private void updatePlayPauseState(boolean isPlaying) {
        if (mCurrentHolder != null) {
            mCurrentHolder.produce_control_play_pause_img.setImageResource(
                    isPlaying ? R.drawable.player_icon_pause : R.drawable.player_icon_play
            );
        }
    }

    private void clearHolderTime() {
        setProgress(0, 0);
    }

    public void hideControl() {
        if (mCurrentHolder != null && mCurrentHolder.produce_control_layout.getVisibility() == View.VISIBLE) {
            ViewVisUtils.setVisibility(mCurrentHolder.produce_control_layout, View.GONE);
        }
    }

    private void showControl() {
        if (mCurrentHolder != null && mPlayer != null && !mPlayer.isAdvertInPlayback()) {
            ViewVisUtils.setVisibility(mCurrentHolder.produce_control_layout, View.VISIBLE);
            hideDelayed(AUTO_SHOWN_SPAN);
        }
    }

    private void hideDelayed(int sec) {
        mHandler.removeMessages(MSG_HIDE);
        if (sec > 0) {
            mHandler.sendEmptyMessageDelayed(MSG_HIDE, sec);
        }
    }

    private ProduceAdapter.OnButtonClickListener onButtonClickListener = new ProduceAdapter.OnButtonClickListener() {
        @Override
        public void onPlayPauseClick(View view) {
            if (mPlayer != null && mCurrentHolder != null && view == mCurrentHolder.produce_control_play_pause_img) {
                if (mPlayer.isPlaybackState()) {
                    mPlayer.pause();
                } else {
                    mPlayer.play();
                }
            }
        }

        @Override
        public void onStartClick(View view, int position) {

            if (allVideos.size() > position && allVideos.get(position) != null) {
                mListView.smoothScrollToPositionFromTop(position + mListView.getHeaderViewsCount(), mListView.getTop() + 10);
            }
        }

        @Override
        public void onSohuScreenClick(View view) {
            if (mCurrentHolder != null && view == mCurrentHolder.produce_videoview_rl) {
                if (mCurrentHolder.produce_control_layout.getVisibility() == View.VISIBLE) {
                    hideControl();
                } else {
                    showControl();
                }
            }
        }
    };


    //seekBar拖动监听
    private SeekBar.OnSeekBarChangeListener createOnSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mCurrent = progress;
                    updateProgressView();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mDragging = true;
                hideDelayed(0);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mPlayer != null) {
                    mPlayer.seekTo(mCurrent * 1000);
                }
                mDragging = false;
                hideDelayed(AUTO_SHOWN_SPAN);
            }
        };
    }

    private int getHitRectBigItem(ListView listView) {

        if (!(listView.getChildAt(0).getTag() instanceof ProduceAdapter.ViewHolder)) {
            return 1;
        }

        int first_pos = 0;
        Rect first_rect = new Rect();
        if (listView.getChildAt(0) != null) {
            listView.getChildAt(0).getGlobalVisibleRect(first_rect);
        } else {
            View view = listView.getAdapter().getView(first_pos, null, listView);
            view.getGlobalVisibleRect(first_rect);
        }

        Rect second_rect = new Rect();
        if (listView.getChildAt(1) != null) {
            listView.getChildAt(1).getGlobalVisibleRect(second_rect);
        } else {
            View view = listView.getAdapter().getView(first_pos + 1, null, listView);
            view.getGlobalVisibleRect(second_rect);
        }

        if (second_rect.bottom - second_rect.top > first_rect.bottom - first_rect.top) {
            first_pos = first_pos + 1;
        }
        return first_pos;
    }

    private String formatTime(int seconds) {
        int second = seconds % 60;
        int minutes = (seconds / 60) % 60;
        int hours = seconds / 3600;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        mFormatBuilder.setLength(0);
        if (hours >= 1) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, second).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, second).toString();
        }

    }
}
