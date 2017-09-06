package com.sohuvideo.ui_plugin.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.sohu.lib.net.parse.CommonDataParser;
import com.sohu.lib.net.request.DataRequest;
import com.sohu.lib.net.request.listener.DataResponseListener;
import com.sohu.lib.net.util.ErrorType;
import com.sohuvideo.api.SohuAdvertAPI;
import com.sohuvideo.api.SohuPlayerAdvertCallback;
import com.sohuvideo.api.SohuPlayerDefinition;
import com.sohuvideo.api.SohuPlayerError;
import com.sohuvideo.api.SohuPlayerItemBuilder;
import com.sohuvideo.api.SohuPlayerLoadFailure;
import com.sohuvideo.api.SohuPlayerMonitor;
import com.sohuvideo.api.SohuPlayerSDK;
import com.sohuvideo.api.SohuPlayerSetting;
import com.sohuvideo.api.SohuScreenView;
import com.sohuvideo.api.SohuVideoPlayer;
import com.sohuvideo.ui_plugin.R;
import com.sohuvideo.ui_plugin.control.ResponseDataWrapperSet;
import com.sohuvideo.ui_plugin.manager.NetRequestManager;
import com.sohuvideo.ui_plugin.model.AlbumData;
import com.sohuvideo.ui_plugin.model.AlbumListData;
import com.sohuvideo.ui_plugin.model.Video;
import com.sohuvideo.ui_plugin.net.URLFactory;
import com.sohuvideo.ui_plugin.player.MediaController;
import com.sohuvideo.ui_plugin.player.PlayVideoHelper;
import com.sohuvideo.ui_plugin.sensor.OrientationManager;
import com.sohuvideo.ui_plugin.utils.DpiUtil;
import com.sohuvideo.ui_plugin.utils.LogManager;
import com.sohuvideo.ui_plugin.view.FinalVideoLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayerActivity extends Activity implements
        OrientationManager.OnOrientationListener
        ,View.OnClickListener
{

    private final static String TAG = PlayerActivity.class.getSimpleName();

    private AudioManager mAm;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;

    private FinalVideoLayout mVideoLayout;
    private SohuScreenView mSohuScreenView;
    private FrameLayout mMediaControllerLayout;
    private MediaController mMediaController;
    private ImageView mWindowPic;
    private ImageView mStartPlayBtn;
    private ProgressBar mProgressBar;

    private SharedPreferences mRecord;

    private boolean needAutoPlay = false;
    private boolean isDataSourceLoading = false;
    private boolean mIsFullScreen = false;// 当前是否是全屏

    private static SohuVideoPlayer mSohuVideoPlayer;


    private SohuPlayerItemBuilder currentBuilder;
    private int pgcCurrentVideoIndex;
    private int pgcCurrentBtnIndex;


    private int mType;
    //底部数据
    private AlbumData mAlbumData;
    private long mAid;
    private List<Video> mVideoInfos;
    private int realCount;

    private int totalTitle;
    private int loadPage = 1;

    private static final int BOTTOM_LOADING_TYPE = 1;
    private static final int BOTTOM_SUCCESS_TYPE = 2;
    private static final int BOTTOM_FAIL_TYPE = 3;
    //底部布局
    private RelativeLayout mBottomLayout;
    private RelativeLayout mBottomLoadingLayout;
    private LinearLayout mBottomFailLayout;
    private Button mFailTryButton;
    private ScrollView mScrollViewLayout;

    //PGC
    private boolean isSingleLine = true;//pgc专辑描述是否是singleLine
    private LinearLayout pgc_video_item_list;
    private LinearLayout layout_bottom_pgc;
    private TextView pgc_album_name;
    private TextView pgc_album_play_count;
    private TextView pgc_album_desc;
    private LinearLayout pgc_album_info_puckup_layout;
    private ImageView pgc_album_info_puckup;
    private LinearLayout pgc_video_title_list;
    private HorizontalScrollView pgc_horizontal_scroll_view;


    //搜狐出品
    private LinearLayout layout_bottom_sohu_make;
    private TextView sohumake_album_name;
    private TextView sohumake_play_count;

    private LinearLayout sohumake_update_notification_layout;
    private TextView sohumake_update_notification;

    private LinearLayout sohumake_actor_layout;
    private TextView sohumake_actor;

    private LinearLayout sohumake_more_album_info;

    private LinearLayout sohumake_director_layout;
    private TextView sohumake_director;

    private LinearLayout sohumake_year_layout;
    private TextView sohumake_year;

    private TextView sohumake_score;
    private TextView sohumake_second_cate_name;
    private TextView sohumake_album_desc;
    private ImageView sohumake_album_info_puckup;
    //搜狐出品集数列表
    private LinearLayout sohumake_album_videos_layout;
    private Map<Integer, Button> btnMap = new HashMap<Integer, Button>();
    private static final int SOHU_MAKE_BTN_MARGIN = 3;

    private int sohuMakeOldBtnIndex;

    private boolean onlyFullScreen;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private OrientationManager mOrientationManager;// 旋转监听


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO) {
            setTheme(android.R.style.Theme_Black_NoTitleBar);
        }

        initView();
        SohuPlayerSDK.showAdCountDown(false);
        mSohuVideoPlayer = new SohuVideoPlayer();

        mSohuVideoPlayer.setSohuScreenView(mSohuScreenView);
        mSohuVideoPlayer.setSohuPlayerMonitor(mSohuPlayerMonitor);

        Intent intent = getIntent();

        initData(intent);

        needAutoPlay = true;

        initClickScreen();

        SohuPlayerSetting.setNeedAutoNext(false);
        registerNetReceiver();
        if (onlyFullScreen) {
            setFullScreenMode(OrientationManager.Side.TOP);
        } else {
            setLiteScreenMode();
        }

        initListener();
    }

    private NetworkConnectChangedReceiver mNetworkConnectChangedReceiver;

    public void registerNetReceiver(){
        IntentFilter filter = new IntentFilter();
        //监听wifi连接（手机与路由器之间的连接）
//        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        //监听互联网连通性（也就是是否已经可以上网了），当然只是指wifi网络的范畴
//        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        //这个是监听网络状态的，包括了wifi和移动网络。
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkConnectChangedReceiver = new NetworkConnectChangedReceiver(), filter);
    }


    private void initListener() {
        mOrientationManager = new OrientationManager(getApplicationContext());
        mOrientationManager.setOnOrientationListener(this);
    }

    private void initView() {
        setContentView(R.layout.activity_play);
        mVideoLayout = (FinalVideoLayout) findViewById(R.id.videoLayout);
        mSohuScreenView = (SohuScreenView) findViewById(R.id.videoView);
        mMediaControllerLayout = (FrameLayout) findViewById(R.id.mediaControllerLayout);
        mWindowPic = (ImageView) findViewById(R.id.windowPic);
        mStartPlayBtn = (ImageView) findViewById(R.id.btn_start_play);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        mMediaController = new MediaController(this, mMediaControllerClickListener);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        mMediaControllerLayout.addView(mMediaController.getContentView(), lp);

        mBottomLayout = (RelativeLayout) findViewById(R.id.bottomLayout);
        mBottomLoadingLayout = (RelativeLayout) findViewById(R.id.layout_bottom_loading);
        mBottomFailLayout = (LinearLayout) findViewById(R.id.layout_bottom_fail);
        mFailTryButton = (Button) findViewById(R.id.fail_try_btn);
        mScrollViewLayout = (ScrollView) findViewById(R.id.layout_scrollview);

        mFailTryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBottomData(getIntent());
            }
        });
        mStartPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSohuVideoPlayer != null) {
                    mSohuVideoPlayer.play();
                }
                if (mBottomFailLayout.getVisibility() == View.VISIBLE) {
                    setBottomData(getIntent());
                }
            }
        });
    }

    /**
     * 初始化数据
     *
     * @param intent
     */
    private void initData(Intent intent) {

        if (intent == null) {
            return;
        }
        setPlayerData(intent);
        if (!onlyFullScreen) {
            setBottomData(intent);
        }

    }

    /**
     * 设置播放数据
     *
     * @param intent
     */
    private void setPlayerData(Intent intent) {
        if (intent == null) {
            return;
        }
        if (mRecord == null) {
            mRecord = getSharedPreferences("record", Activity.MODE_PRIVATE);
        }
        long recordVid = mRecord.getLong("vid", 0);
        long recordAid = mRecord.getLong("aid", 0);
        int recordSite = mRecord.getInt("site", 0);
        int recordPosition = mRecord.getInt("recordPosition", 0);

        LogManager.d(TAG, "recordPosition = " + recordPosition);

        int site = intent.getIntExtra(PlayVideoHelper.EXTRA_SITE, 0);
        long aid = intent.getLongExtra(PlayVideoHelper.EXTRA_AID, 0);
        long vid = intent.getLongExtra(PlayVideoHelper.EXTRA_VID, 0);
        currentBuilder = new SohuPlayerItemBuilder("0", aid, vid, site);

        if (recordPosition > 0 && recordVid != 0 && recordVid == vid && recordSite != 0 && recordSite == site) {
            currentBuilder.setStartPosition(recordPosition);
        }

        if (currentBuilder != null) {
            mSohuVideoPlayer.setDataSource(currentBuilder);
            SohuAdvertAPI.setOnAdvertListener(advertCallback);
        }

        if (aid == 0) {
            //大屏幕播放
            playVideoInFullScreen();
        }
    }

    /**
     * 大屏幕播放
     */
    private void playVideoInFullScreen() {
        onlyFullScreen = true;
    }

    /**
     * 设置新的数据源
     *
     * @param video
     */
    private void setNewPlayerData(Video video) {
        LogManager.d(TAG, "setNewPlayerData stop");
        mSohuVideoPlayer.stop(false);
        if (video == null) {
            return;
        }
        currentBuilder = new SohuPlayerItemBuilder("0", video.getAid(), video.getVid(), video.getSite());

        if (currentBuilder != null) {
            mSohuVideoPlayer.setDataSource(currentBuilder);
            SohuAdvertAPI.setOnAdvertListener(advertCallback);
        }
        mSohuVideoPlayer.play();
        if (mMediaController != null) {
            mMediaController.setProgress(0, 0);
        }
    }

    /**
     * 设置底部专辑数据
     *
     * @param intent
     */
    public void setBottomData(Intent intent) {
        if (intent == null) {
            return;
        }
        mType = intent.getIntExtra(PlayVideoHelper.EXTRA_TYPE, 0);
        mAid = intent.getLongExtra(PlayVideoHelper.EXTRA_AID, 0);
        if (mType == 0) {
            return;
        }

        if (mType == PlayVideoHelper.TYPE_FOR_PGC) {
            layout_bottom_pgc = (LinearLayout) findViewById(R.id.layout_bottom_pgc);
            layout_bottom_pgc.setVisibility(View.VISIBLE);
            pgc_album_name = (TextView) findViewById(R.id.pgc_album_name);
            pgc_album_play_count = (TextView) findViewById(R.id.pgc_album_play_count);
            pgc_album_desc = (TextView) findViewById(R.id.pgc_album_desc);
            pgc_album_info_puckup_layout = (LinearLayout) findViewById(R.id.pgc_album_info_puckup_layout);
            pgc_album_info_puckup = (ImageView) findViewById(R.id.pgc_album_info_puckup);

            pgc_album_info_puckup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSingleLine) {
                        pgc_album_desc.setSingleLine(false);
                        pgc_album_info_puckup.setImageResource(R.drawable.player_icon_packup);
                        isSingleLine = false;
                    } else {
                        pgc_album_desc.setSingleLine(true);
                        pgc_album_info_puckup.setImageResource(R.drawable.player_icon_unfold);
                        isSingleLine = true;
                    }


                }
            });
            pgc_video_item_list = (LinearLayout) findViewById(R.id.pgc_video_item_list);
            pgc_video_title_list = (LinearLayout) findViewById(R.id.pgc_video_title_list);
            pgc_horizontal_scroll_view = (HorizontalScrollView) findViewById(R.id.pgc_horizontal_scroll_view);

        } else if (mType == PlayVideoHelper.TYPE_FOR_SOHU) {
            layout_bottom_sohu_make = (LinearLayout) findViewById(R.id.layout_bottom_sohu_make);
            layout_bottom_sohu_make.setVisibility(View.VISIBLE);
            sohumake_album_name = (TextView) findViewById(R.id.sohumake_album_name);
            sohumake_play_count = (TextView) findViewById(R.id.sohumake_play_count);

            sohumake_update_notification_layout = (LinearLayout) findViewById(R.id.sohumake_update_notification_layout);
            sohumake_update_notification = (TextView) findViewById(R.id.sohumake_update_notification);

            sohumake_actor_layout = (LinearLayout) findViewById(R.id.sohumake_actor_layout);
            sohumake_actor = (TextView) findViewById(R.id.sohumake_actor);

            sohumake_more_album_info = (LinearLayout) findViewById(R.id.sohumake_more_album_info);

            sohumake_director_layout = (LinearLayout) findViewById(R.id.sohumake_director_layout);
            sohumake_director = (TextView) findViewById(R.id.sohumake_director);

            sohumake_year_layout = (LinearLayout) findViewById(R.id.sohumake_year_layout);
            sohumake_year = (TextView) findViewById(R.id.sohumake_year);

            sohumake_score = (TextView) findViewById(R.id.sohumake_score);
            sohumake_second_cate_name = (TextView) findViewById(R.id.sohumake_second_cate_name);
            sohumake_album_desc = (TextView) findViewById(R.id.sohumake_album_desc);
            sohumake_album_info_puckup = (ImageView) findViewById(R.id.sohumake_album_info_puckup);
            sohumake_album_info_puckup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sohumake_more_album_info.getVisibility() == View.GONE) {
                        sohumake_more_album_info.setVisibility(View.VISIBLE);
                        sohumake_album_info_puckup.setImageResource(R.drawable.player_icon_packup);
                    } else {
                        sohumake_more_album_info.setVisibility(View.GONE);
                        sohumake_album_info_puckup.setImageResource(R.drawable.player_icon_unfold);
                    }
                }
            });
            sohumake_album_videos_layout = (LinearLayout) findViewById(R.id.sohumake_album_videos_layout);

        }

        updateBottomUI(BOTTOM_LOADING_TYPE);
        loadAlbumInfoData(mAid);

    }

    /**
     * 设置为小屏模式
     */
    private void setLiteScreenMode() {

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mMediaController.hideControl();//隐藏状态栏
        mVideoLayout.setFullScreen(false);

        mIsFullScreen = false;
        mMediaController.setFullScreenMode(mIsFullScreen);

//        if (mSohuVideoPlayer != null) {
//            mSohuVideoPlayer.removePadBySelf();
//        }
    }

    /**
     * 设置为大屏幕模式
     */
    private void setFullScreenMode(OrientationManager.Side orientation) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (orientation == OrientationManager.Side.LEFT) {
            if (Build.VERSION.SDK_INT > 8) {
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            } else {
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        } else {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        mMediaController.hideControl();//隐藏状态栏
        mVideoLayout.setFullScreen(true);

        mIsFullScreen = true;
        mMediaController.setFullScreenMode(mIsFullScreen);
    }

    /**
     * 初始化手势
     */
    private void initClickScreen() {
        mSohuScreenView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickView();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoPlayIfNeeded();
        requestAudioRequest(getApplicationContext());

        if (mOrientationManager != null) {
            mOrientationManager.enable();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        needAutoPlay = mSohuVideoPlayer.isPlaybackState();
        if (mOrientationManager != null) {
            mOrientationManager.disable();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSohuVideoPlayer.stop(true);
        abandonAudioFocus();
    }

    @Override
    protected void onDestroy() {
        if (mDialog != null)mDialog.dismiss();
        if (mNetworkConnectChangedReceiver != null)
            unregisterReceiver(mNetworkConnectChangedReceiver);
        super.onDestroy();
    }

    private void autoPlayIfNeeded() {
        if ((isDataSourceLoading || needAutoPlay)) {
            mSohuVideoPlayer.play();
            needAutoPlay = false;
            if (isDataSourceLoading) {
                updateLoadingUI();
            }
        }
    }


    /**
     * 屏幕点击事件
     */
    private void clickView() {

        mStartPlayBtn.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mWindowPic.setVisibility(View.GONE);
        if (mProgressBar != null
                && mProgressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        if (!mSohuVideoPlayer.isAdvertInPlayback()) {
            showController();
        }
    }

    /**
     * 显示控制器
     */
    private void showController() {
        mMediaController.showController();
    }

    /**
     * 计算播放下一个的逻辑
     */
    private void playNext() {
        if (mVideoInfos == null || mVideoInfos.size() <= 1) {
            if (currentBuilder != null) {
                Video v = new Video();
                v.setAid(currentBuilder.getAid());
                v.setVid(currentBuilder.getVid());
                v.setSite(currentBuilder.getSite());
                setNewPlayerData(v);
            } else {
                Toast.makeText(this, "没有下一集", Toast.LENGTH_SHORT).show();
                finish();
            }

            return;
        }

        //搜狐出品
        if (mType == PlayVideoHelper.TYPE_FOR_SOHU) {
            if ((sohuMakeOldBtnIndex + 1) >= mVideoInfos.size()) {
                //播放第一集
                btnMap.get(0).performClick();
            } else {
                //播放下一集
                btnMap.get(sohuMakeOldBtnIndex + 1).performClick();
            }
        }

        //PGC布局
        if (mType == PlayVideoHelper.TYPE_FOR_PGC) {
            int nextPlayIndex = 0;
            if (currentBuilder.getVid() == mVideoInfos.get(mVideoInfos.size() - 1).getVid()) {
                //如果是最后一集，那么回头播放第一集
                nextPlayIndex = 0;
            } else {
                for (int i = 0; i < mVideoInfos.size(); i++) {
                    if (currentBuilder.getVid() == mVideoInfos.get(i).getVid()) {
                        nextPlayIndex = i + 1;
                    }
                }
            }
            //播放下一集
            setNewPlayerData(mVideoInfos.get(nextPlayIndex));
            //改变UI
            if (pgc_video_title_list == null || pgc_video_title_list.getChildCount() <= (nextPlayIndex / 30)) {
                return;
            }
            pgc_video_title_list.getChildAt(nextPlayIndex / 30).performClick();
        }
    }

    /**
     * 控制器回调
     */
    private MediaController.OnMediaControllerClickListener mMediaControllerClickListener = new MediaController.OnMediaControllerClickListener() {

        @Override
        public void onPlayPauseClicked() {
            if (mSohuVideoPlayer != null) {
                if (mSohuVideoPlayer.isPlaybackState()) {
                    mSohuVideoPlayer.pause();
                } else {
                    mSohuVideoPlayer.play();
                }

            }
        }

        @Override
        public void onSeekTo(int pos) {
            if (mSohuVideoPlayer != null) {
                mSohuVideoPlayer.seekTo(pos);
            }
        }

        @Override
        public void onFullScreenClicked() {
            if (!mIsFullScreen) {
                setFullScreenMode(OrientationManager.Side.TOP);
            } else {
                setLiteScreenMode();
            }
        }

        @Override
        public void onFullBackClicked() {
            if (onlyFullScreen && mIsFullScreen) {
                finish();
            }
            if (mIsFullScreen && !onlyFullScreen) {
                setLiteScreenMode();
            }
        }

        @Override
        public int getCurrentDefinition() {
            if (mSohuVideoPlayer != null) {
                return mSohuVideoPlayer.getCurrentDefinition();
            }
            return SohuPlayerDefinition.PE_DEFINITION_NOR;
        }

        @Override
        public List<Integer> getSupportDefinitions() {
            if (mSohuVideoPlayer != null) {
                return mSohuVideoPlayer.getSupportDefinitions();
            }
            return null;
        }

        @Override
        public void changeDefinition(int definition) {
            if (mSohuVideoPlayer != null) {
                mSohuVideoPlayer.changeDefinition(definition);
            }
        }

        @Override
        public boolean isAdvertInPlayback() {
            if (mSohuVideoPlayer != null) {
                return mSohuVideoPlayer.isAdvertInPlayback();
            }
            return true;
        }

        @Override
        public int getCurrentPosition() {
            if (mSohuVideoPlayer != null) {
                return mSohuVideoPlayer.getCurrentPosition();
            }
            return 0;
        }

        @Override
        public int getDuration() {
            if (mSohuVideoPlayer != null) {
                return mSohuVideoPlayer.getDuration();
            }
            return 0;
        }

        @Override
        public void onPlayNextClicked() {
            playNext();
        }

        @Override
        public void onLiteBackClicked() {
            finish();
        }

        @Override
        public void onAdGoDetailClicked() {
            SohuAdvertAPI.notifyAdvertClicked();
        }
    };


    /**
     * 播放器回调
     */
    private final SohuPlayerMonitor mSohuPlayerMonitor = new SohuPlayerMonitor() {

        @Override
        public void onPreparing() {
            LogManager.d(TAG, "onPreparing");
//            mProgressBar.setVisibility(View.VISIBLE);
//            mWindowPic.setVisibility(View.VISIBLE);
            mStartPlayBtn.setVisibility(View.GONE);
            if (mSohuVideoPlayer.isAdvertInPlayback()) {
                mMediaController.hideControl();
            }
            super.onPreparing();
        }

        @Override
        public void onPrepared() {
            LogManager.d(TAG, "onPrepared");
            mProgressBar.setVisibility(View.GONE);
            mWindowPic.setVisibility(View.GONE);
            mStartPlayBtn.setVisibility(View.GONE);
            if (!mSohuVideoPlayer.isAdvertInPlayback() && mMediaController != null) {
                mMediaController.hideAdvertControl();
                mMediaController.showController();

            }
            if (mSohuVideoPlayer.isAdvertInPlayback() && mMediaController != null) {
                mMediaController.hideControl();
                mMediaController.showAdvertControl();
            }

            super.onPrepared();
        }

        @Override
        public void onStop() {
            LogManager.d(TAG, "onStop");
            mProgressBar.setVisibility(View.GONE);
            mWindowPic.setVisibility(View.VISIBLE);
            mStartPlayBtn.setVisibility(View.VISIBLE);
            if (mMediaController != null) {
                mMediaController.updatePlayPauseState(false);
            }
            super.onStop();
        }

        @Override
        public void onPlayOver(SohuPlayerItemBuilder sohuPlayerItemBuilder) {
            LogManager.d("tension", "onPlayOver" + "  " + sohuPlayerItemBuilder.getTitle());
            super.onPlayOver(sohuPlayerItemBuilder);
        }

        @Override
        public void onComplete() {
            LogManager.d("tension", "onComplete");
            playNext();
            super.onComplete();
        }

        @Override
        public void onBuffering(int progress) {
            LogManager.d(TAG, "onBuffering");
            mProgressBar.setVisibility(progress == 100 ? View.GONE
                    : View.VISIBLE);
            super.onBuffering(progress);
        }

        @Override
        public void onPlay() {
            LogManager.d(TAG, "onPlay");
            mWindowPic.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            mStartPlayBtn.setVisibility(View.GONE);
            if (!mSohuVideoPlayer.isAdvertInPlayback() && mMediaController != null) {
                mMediaController.showController();
            }
            if (mMediaController != null) {
                mMediaController.updatePlayPauseState(true);
            }
            super.onPlay();

        }

        @Override
        public void onStartLoading() {
            LogManager.d(TAG, "onStartLoading");
            isDataSourceLoading = true;
            updateLoadingUI();
            super.onStartLoading();
        }

        @Override
        public void onLoadSuccess() {
            LogManager.d(TAG, "onLoadSuccess");
            isDataSourceLoading = false;
            super.onLoadSuccess();
        }

        @Override
        public void onLoadFail(SohuPlayerLoadFailure sohuPlayerLoadFailure) {
            LogManager.d(TAG, "onLoadFail = " + sohuPlayerLoadFailure);
            String text = new String();
            switch (sohuPlayerLoadFailure) {
                case IP_LIMIT:
                    text = "由于版权限制，该视频无法播放。";
                    break;
                case UNREACHED:
                    text = "网络发生错误，点击重试。";
                    break;
            }
            if (!TextUtils.isEmpty(text)) {
                handleError(text);
            }
            if (isFinishing()) {
                return;
            }
            super.onLoadFail(sohuPlayerLoadFailure);
        }

        @Override
        public void onError(SohuPlayerError sohuPlayerError) {
            LogManager.d(TAG, "onError = " + sohuPlayerError);
            String text = new String();
            switch (sohuPlayerError) {
                case INTERNAL:
                    text = "播放器发生错误，点击重试。";
                    break;
                case NETWORK:
                    text = "播放器网络发生错误，点击重试。";
                    break;
            }
            if (!TextUtils.isEmpty(text)) {
                handleError(text);
            }
            super.onError(sohuPlayerError);
        }

        @Override
        public void onPause() {
            LogManager.d(TAG, "onPause ");
            if (mMediaController != null) {
                mMediaController.updatePlayPauseState(false);
            }
            super.onPause();
        }

        @Override
        public void onPausedAdvertShown() {
            LogManager.d(TAG, "onPausedAdvertShown ");
            if (mMediaController != null) {
                mMediaController.hideControl();
            }
            super.onPausedAdvertShown();
        }

        @Override
        public void onProgressUpdated(int currentPosition, int duration) {
            LogManager.d(TAG, "onProgressUpdated ");
            if (mMediaController != null) {
                mMediaController.setProgress(currentPosition, duration);

            }
            super.onProgressUpdated(currentPosition, duration);
        }


        @Override
        public void onPlayItemChanged(SohuPlayerItemBuilder sohuPlayerItemBuilder, int index) {

            LogManager.d(TAG, "onPlayItemChanged ");
            currentBuilder = sohuPlayerItemBuilder;

            if (mMediaController != null) {
                mMediaController.setTitle(sohuPlayerItemBuilder.getTitle());
                mMediaController.updateDefinition();
            }

        }

        @Override
        public void onDefinitionChanged() {
            LogManager.d(TAG, "onDefinitionChanged ");
            if (mMediaController != null)

                super.onDefinitionChanged();
        }
    };

    private void handleError(String text) {
        mWindowPic.setVisibility(View.VISIBLE);
        mStartPlayBtn.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(PlayerActivity.this, text, Toast.LENGTH_SHORT).show();
        isDataSourceLoading = false;
    }


    private void updateLoadingUI() {
        mWindowPic.setVisibility(View.VISIBLE);
        mStartPlayBtn.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Audio控制
     */
    public void requestAudioRequest(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            getAudioManager(context).requestAudioFocus(
                    getAudioFocusChangeListener(), AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN);
        }
    }

    public AudioManager getAudioManager(Context context) {
        if (mAm == null) {
            mAm = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
        }
        return mAm;
    }

    public AudioManager.OnAudioFocusChangeListener getAudioFocusChangeListener() {
        if (mOnAudioFocusChangeListener == null) {
            mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {// 短暂失去


                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) { // 长时间失去


                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) { // 获得


                    }
                }
            };
        }
        return mOnAudioFocusChangeListener;
    }

    private void abandonAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            // 释放音频焦点
            if (mAm != null) {
                if (mOnAudioFocusChangeListener != null) {
                    mAm.abandonAudioFocus(getAudioFocusChangeListener());
                }
            }
        }
    }

    private SohuPlayerAdvertCallback advertCallback = new SohuPlayerAdvertCallback() {
        @Override
        public void onFetchAdUrl(String s) {
            Log.d("lishan", "ad onFetchAdUrl = " + s);
        }

        @Override
        public void onAdsCompleted() {

        }

        @Override
        public void onAdsStart() {

        }

        @Override
        public void onAdProgressUpdate(int current, int duration) {
            Log.d("lishan", "ad current = " + current);
            Log.d("lishan", "ad duration = " + duration);
            if (mMediaController != null) {
                mMediaController.displayStateAdRemainText(current);
            }
        }
    };

    //***************************
    //搜狐出品
    //***************************
    private void setSohuMakeAlbumInfo(AlbumData albumData) {
        sohumake_album_name.setText(albumData.getAlbum_name());
        double playCount;
        if (albumData.getPlay_count() >= 100000000) {
            playCount = albumData.getPlay_count() * 10 / 100000000 / 10.0;
            sohumake_play_count.setText(playCount + "亿次");
        } else if (albumData.getPlay_count() < 100000000 && albumData.getPlay_count() > 10000) {
            playCount = albumData.getPlay_count() * 10 / 10000 / 10.0;
            sohumake_play_count.setText(playCount + "万次");
        } else {
            sohumake_play_count.setText(albumData.getPlay_count() + "次");
        }

        //更新
        if (TextUtils.isEmpty(albumData.getUpdate_notification())) {
            sohumake_update_notification_layout.setVisibility(View.GONE);
        } else {
            sohumake_update_notification.setText(albumData.getUpdate_notification());
        }

        //主演
        if (TextUtils.isEmpty(albumData.getActor())) {
            sohumake_actor_layout.setVisibility(View.GONE);
        } else {
            sohumake_actor.setText(albumData.getActor());
        }

        //导演
        if (TextUtils.isEmpty(albumData.getDirector())) {
            sohumake_director_layout.setVisibility(View.GONE);
        } else {
            sohumake_director.setText(albumData.getDirector());
        }

        if (albumData.getYear() == 0) {
            sohumake_year_layout.setVisibility(View.GONE);
        } else {
            sohumake_year.setText(albumData.getYear() + "");
        }

        sohumake_score.setText(albumData.getScore() + "分");
        sohumake_second_cate_name.setText(albumData.getSecond_cate_name());
        sohumake_album_desc.setText("  " + albumData.getAlbum_desc());
        loadVideoListData(mAid, loadPage, 50);

    }

    private void setSohuMakeVideoListTitleLayout() {
        int margin = DpiUtil.dipToPx(this, SOHU_MAKE_BTN_MARGIN);
        LinearLayout line = null;
        for (int i = 0; i < mVideoInfos.size(); i++) {
            if (line == null) {
                line = new LinearLayout(this);
                line.setOrientation(LinearLayout.HORIZONTAL);
            }
            Button button = new Button(this);
            button.setTag(i);
            button.setOnClickListener(sohuMakeItemBtnOnclickListener);
            btnMap.put(i, button);
            button.setTextColor(getResources().getColor(R.color.color_player_dark_text));
            if (mVideoInfos.get(i).getVid() == currentBuilder.getVid()) {
                button.setBackgroundResource(R.drawable.player_item_bg_playing);
                button.setText("");
                sohuMakeOldBtnIndex = i;
            } else {
                button.setText(i + 1 + "");
                button.setBackgroundResource(R.drawable.player_item_bg_played);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(margin, margin, margin, margin);
            params.weight = 1;
            line.addView(button, params);
            if (line.getChildCount() == 5) {
                sohumake_album_videos_layout.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                line = null;
            }
        }

        if (line != null) {
            // 为了补全,让weight生效
            int childCount = line.getChildCount();
            for (int i = 0; i < (5 - childCount); i++) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(margin, margin, margin, margin);
                params.weight = 1;
                line.addView(new View(this), params);
            }
            sohumake_album_videos_layout.addView(line, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

    }

    private View.OnClickListener sohuMakeItemBtnOnclickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (btnMap.get(sohuMakeOldBtnIndex) == (Button) v) {
                return;
            } else {
                btnMap.get(sohuMakeOldBtnIndex).setText(sohuMakeOldBtnIndex + 1 + "");
                btnMap.get(sohuMakeOldBtnIndex).setBackgroundResource(R.drawable.player_item_bg_played);
                sohuMakeOldBtnIndex = (int) v.getTag();
                ((Button) v).setText("");
                ((Button) v).setBackgroundResource(R.drawable.player_item_bg_playing);
                // TODO: 15/11/29
                setNewPlayerData(mVideoInfos.get(sohuMakeOldBtnIndex));

            }
        }
    };


    /**
     * 加载专辑详情
     */
    private void loadAlbumInfoData(long aid) {
        String albumDataUrl = URLFactory.getAlbumInfoUrl(aid);
        DataRequest dataRequest = new DataRequest(albumDataUrl);
        LogManager.e(TAG, albumDataUrl + "");
        dataRequest.setResultParser(new CommonDataParser(ResponseDataWrapperSet.AlbumDataWrapper.class));
        NetRequestManager.getRequestManager().startDataRequestAsync(dataRequest, new DataResponseListener() {

            @Override
            public void onSuccess(Object o, boolean b) {
                ResponseDataWrapperSet.AlbumDataWrapper wrapper = (ResponseDataWrapperSet.AlbumDataWrapper) o;
                mAlbumData = wrapper.getData();
                LogManager.d(TAG, "mType = " + mType);
                if (mAlbumData != null) {
                    if (mType == PlayVideoHelper.TYPE_FOR_PGC) {
                        setPGCAlbumInfo(mAlbumData);
                    } else if (mType == PlayVideoHelper.TYPE_FOR_SOHU) {
                        setSohuMakeAlbumInfo(mAlbumData);
                    }

                }
            }

            @Override
            public void onFailure(ErrorType errorType) {
                updateBottomUI(BOTTOM_FAIL_TYPE);
                LogManager.d(TAG, "onFailure");
            }

            @Override
            public void onCancelled() {
                LogManager.d(TAG, "onCancelled");
            }
        });
    }

    /**
     * 获取专辑内的剧集列表
     *
     * @param aid
     * @param page
     * @param pageSize
     */
    private void loadVideoListData(long aid, final int page, int pageSize) {
        String albumListDataUrl = URLFactory.getAlbumListUrl(aid, page, pageSize);
        DataRequest dataRequest = new DataRequest(albumListDataUrl);
        LogManager.e(TAG, albumListDataUrl + "");
        dataRequest.setResultParser(new CommonDataParser(ResponseDataWrapperSet.AlbumListDataWrapper.class));
        NetRequestManager.getRequestManager().startDataRequestAsync(dataRequest, new DataResponseListener() {

            @Override
            public void onSuccess(Object o, boolean b) {
                ResponseDataWrapperSet.AlbumListDataWrapper wrapper = (ResponseDataWrapperSet.AlbumListDataWrapper) o;
                AlbumListData albumListData = wrapper.getData();
                if (albumListData != null && albumListData.getVideos() != null) {
                    realCount = albumListData.getCount();
                    if (mVideoInfos == null) {
                        mVideoInfos = new ArrayList<Video>();
                    }
                    mVideoInfos.addAll(albumListData.getVideos());

                    //继续加载
                    if (realCount > 50 * loadPage) {
                        loadPage = loadPage + 1;
                        loadVideoListData(mAid, loadPage, 50);
                        return;
                    }

                    if (mType == PlayVideoHelper.TYPE_FOR_PGC) {
                        setPGCVideoListTitleLayout();
                    } else if (mType == PlayVideoHelper.TYPE_FOR_SOHU) {
                        setSohuMakeVideoListTitleLayout();
                    }
                    updateBottomUI(BOTTOM_SUCCESS_TYPE);
                } else {
                    updateBottomUI(BOTTOM_FAIL_TYPE);
                }


            }

            @Override
            public void onFailure(ErrorType errorType) {
                updateBottomUI(BOTTOM_FAIL_TYPE);
            }

            @Override
            public void onCancelled() {

            }
        });
    }

    //***************************
    //PGC出品
    //***************************
    private void setPGCAlbumInfo(AlbumData albumInfo) {

        pgc_album_name.setText(albumInfo.getAlbum_name());

        double playCount;
        if (albumInfo.getPlay_count() >= 100000000) {
            playCount = albumInfo.getPlay_count() * 10 / 100000000 / 10.0;
            pgc_album_play_count.setText(playCount + "亿次");
        } else if (albumInfo.getPlay_count() < 100000000 && albumInfo.getPlay_count() > 10000) {
            playCount = albumInfo.getPlay_count() * 10 / 10000 / 10.0;
            pgc_album_play_count.setText(playCount + "万次");
        } else {
            pgc_album_play_count.setText(albumInfo.getPlay_count() + "次");
        }

        if (TextUtils.isEmpty(albumInfo.getAlbum_desc())) {
            pgc_album_desc.setVisibility(View.GONE);
            pgc_album_info_puckup_layout.setVisibility(View.GONE);
        } else {
            pgc_album_desc.setText(albumInfo.getAlbum_desc());
        }

        loadVideoListData(mAid, loadPage, 50);
    }


    private void setPGCVideoListTitleLayout() {

        LogManager.d(TAG, "realCount = " + realCount + "  aid = " + mAid);

        if (mVideoInfos.size() % 30 == 0) {
            totalTitle = mVideoInfos.size() / 30;
        } else {
            totalTitle = mVideoInfos.size() / 30 + 1;
        }

        LogManager.d(TAG, "totalTitle = " + totalTitle + "  aid = " + mAid);

        for (int i = 0; i < totalTitle; i++) {
            Button btn = new Button(PlayerActivity.this);
            btn.setTextColor(getResources().getColor(R.color.color_player_light_text));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                btn.setBackground(null);
            }
            btn.setTag(i);

            if (i != totalTitle - 1) {
                btn.setText((i * 30 + 1) + "-" + ((i + 1) * 30));

            } else {
                if (i * 30 + 1 == mVideoInfos.size()) {
                    btn.setText(mVideoInfos.size() + "");

                } else {
                    btn.setText((i * 30 + 1) + "-" + realCount);
                }
            }

            btn.setOnClickListener(titleBtnOnClickListener);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            pgc_video_title_list.addView(btn, i, params);
        }

        for (int i = 0; i < mVideoInfos.size(); i++) {
            if (mVideoInfos.get(i).getVid() == currentBuilder.getVid()) {
                pgcCurrentVideoIndex = i;
                LogManager.d(TAG, "pgcCurrentVideoIndex = " + pgcCurrentVideoIndex);
            }
        }

        if ((pgcCurrentVideoIndex + 1) % 30 == 0) {
            pgcCurrentBtnIndex = (pgcCurrentVideoIndex + 1) / 30 - 1;
        } else {
            pgcCurrentBtnIndex = (pgcCurrentVideoIndex + 1) / 30;
        }
        LogManager.d(TAG, "pgcCurrentBtnIndex = " + pgcCurrentBtnIndex);
        ((Button) pgc_video_title_list.getChildAt(pgcCurrentBtnIndex)).setTextColor(Color.RED);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pgc_horizontal_scroll_view.smoothScrollTo(pgc_video_title_list.getChildAt(pgcCurrentBtnIndex).getLeft(), 0);
            }
        }, 500);


        setVideoListDetailLayout(pgcCurrentBtnIndex);

    }

    private View.OnClickListener titleBtnOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int clickBtnIndex = (int) v.getTag();
            ((Button) pgc_video_title_list.getChildAt(pgcCurrentBtnIndex)).setTextColor(getResources().getColor(R.color.color_player_light_text));
            ((Button) v).setTextColor(Color.RED);

            pgc_horizontal_scroll_view.smoothScrollTo(v.getLeft(), 0);

            pgcCurrentBtnIndex = clickBtnIndex;
            setVideoListDetailLayout(pgcCurrentBtnIndex);

        }
    };


    /**
     * 设置底部item数据
     *
     * @param currentBtnIndex
     */
    private void setVideoListDetailLayout(int currentBtnIndex) {

        List<Video> showPageVideos = new ArrayList<Video>();

        if ((currentBtnIndex + 1) * 30 > mVideoInfos.size()) {

            for (int i = currentBtnIndex * 30; i < mVideoInfos.size(); i++) {
                showPageVideos.add(mVideoInfos.get(i));
                LogManager.e(TAG, mVideoInfos.get(i).toString());
            }
        } else {
            for (int i = currentBtnIndex * 30; i < (currentBtnIndex + 1) * 30; i++) {
                showPageVideos.add(mVideoInfos.get(i));
                LogManager.e(TAG, mVideoInfos.get(i).toString());
            }
        }
        int margin = DpiUtil.dipToPx(this, 5);
        int itemHight = DpiUtil.dipToPx(this, 35);
        pgc_video_item_list.removeAllViews();
        for (int i = 0; i < showPageVideos.size(); i++) {

            LinearLayout view = (LinearLayout) View.inflate(this, R.layout.layout_bottom_pgv_item, null);
            view.setTag(showPageVideos.get(i));
            ((TextView) view.getChildAt(0)).setText(showPageVideos.get(i).getVideo_name());

            // ((LinearLayout.LayoutParams) view.getLayoutParams()).setMargins(margin,margin,margin,0);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, itemHight);
            params.setMargins(margin, margin, margin, 0);
            if (showPageVideos.get(i).getVid() == currentBuilder.getVid()) {
                ((TextView) view.getChildAt(0)).setTextColor(Color.RED);
                view.getChildAt(1).setVisibility(View.VISIBLE);
            } else {
                ((TextView) view.getChildAt(0)).setTextColor(getResources().getColor(R.color.color_player_dark_text));
                view.getChildAt(1).setVisibility(View.GONE);
            }
            view.setOnClickListener(mPgcItemOnClickListener);
            pgc_video_item_list.addView(view, params);
        }

    }

    private View.OnClickListener mPgcItemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Video video = (Video) view.getTag();

            if (video.getVid() == currentBuilder.getVid()) {
                return;
            }
            setNewPlayerData(video);
            //改变pgc Item的颜色
            setVideoListDetailLayout(pgcCurrentBtnIndex);
        }
    };


    /**
     * 更改底部UI
     *
     * @param type
     */
    private void updateBottomUI(int type) {
        switch (type) {
            case BOTTOM_LOADING_TYPE:
                mBottomLoadingLayout.setVisibility(View.VISIBLE);
                mScrollViewLayout.setVisibility(View.GONE);
                mBottomFailLayout.setVisibility(View.GONE);
                break;
            case BOTTOM_SUCCESS_TYPE:
                mBottomLoadingLayout.setVisibility(View.GONE);
                mScrollViewLayout.setVisibility(View.VISIBLE);
                mBottomFailLayout.setVisibility(View.GONE);
                break;
            case BOTTOM_FAIL_TYPE:
                mBottomLoadingLayout.setVisibility(View.GONE);
                mScrollViewLayout.setVisibility(View.GONE);
                mBottomFailLayout.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {

            if (onlyFullScreen && mIsFullScreen) {
                finish();
                return true;
            }
            if (mIsFullScreen && !onlyFullScreen) {
                setLiteScreenMode();
                return true;
            } else {
                finish();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        //关闭播放页面，存储该次视频的播放进去，若下次进入为该视频，则续播
        if (currentBuilder != null && mSohuVideoPlayer != null) {
            long vid = currentBuilder.getVid();
            long aid = currentBuilder.getAid();
            int site = currentBuilder.getSite();
            int currentPosition = mSohuVideoPlayer.getCurrentPosition();
            LogManager.d(TAG, "finish currentPosition = " + currentPosition);
            if (currentPosition != 0) {
                if (mRecord == null) {
                    mRecord = getSharedPreferences("record", Activity.MODE_PRIVATE);
                }
                SharedPreferences.Editor editor = mRecord.edit();
                editor.putLong("vid", vid);
                editor.putLong("aid", aid);
                editor.putInt("site", site);
                editor.putInt("recordPosition", currentPosition);
                editor.commit();

                LogManager.d(TAG, "finish currentPosition = " + currentPosition);
            }
        }
        super.finish();
    }

    @Override
    public void onOrientationChanged(OrientationManager.Side currentSide) {
        switch (currentSide) {
            case TOP:
            case BOTTOM: {
                break;
            }
            case LEFT: {
                setFullScreenMode(OrientationManager.Side.LEFT);
                break;
            }
            case RIGHT: {
                setFullScreenMode(OrientationManager.Side.RIGHT);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (mDialog != null)mDialog.dismiss();
        if (v.getId() == R.id.tv_cancel){
            finish();
        }else if (v.getId() == R.id.tv_continue){
            if (mSohuVideoPlayer.isPauseState()){
                mSohuVideoPlayer.play();
            }
        }
    }


    public class NetworkConnectChangedReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
                NetworkInfo extra_network_info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (extra_network_info != null){
                    NetworkInfo.State state = extra_network_info.getState();
                    int type = extra_network_info.getType();
                    String typeName = extra_network_info.getTypeName();
                    if ("MOBILE".equals(typeName) && type == 0){
//                        showAlertDialog(null);
                        if ("CONNECTED".equals(state.toString())){
                            mSohuVideoPlayer.pause();
                            showAlertDialog("当前处于移动数据流量是否继续播放");
                        }else if ("DISCONNECTED".equals(state.toString())){
                            dismissAlertDialog();
                            if (mSohuVideoPlayer.isPauseState()){
                                mSohuVideoPlayer.play();
                            }
                        }
                    }else if ("WIFI".equals(typeName) && type == 1){
                        if ("CONNECTED".equals(state.toString())){
                            dismissAlertDialog();
                            if (mSohuVideoPlayer.isPauseState()){
                                mSohuVideoPlayer.play();
                            }
                        }else if ("DISCONNECTED".equals(state.toString())){
                            if (mSohuVideoPlayer.isPauseState()){
                                mSohuVideoPlayer.play();
                            }
                        }
                    }
                }
            }
        }
    }
    private AlertDialog  mDialog;
    private TextView tvDialogTitle;

    private synchronized void dismissAlertDialog() {
        if (mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
    private synchronized void showAlertDialog(String title) {
        if (mDialog == null){
            AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(this);
            View view = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.hint_network, null);
            tvDialogTitle = (TextView) view.findViewById(R.id.tv_title);
            if (!TextUtils.isEmpty(title)){
                tvDialogTitle.setText(title);
            }
            view.findViewById(R.id.tv_cancel).setOnClickListener(this);
            view.findViewById(R.id.tv_continue).setOnClickListener(this);
            mAlertDialog.setView(view);
            mAlertDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    finish();
                    return false;
                }
            });
            mAlertDialog.setCancelable(false);
            mDialog = mAlertDialog.show();
            mDialog.setCanceledOnTouchOutside(false);
        }else {
            if (!mDialog.isShowing()){
                if (!TextUtils.isEmpty(title)){
                    tvDialogTitle.setText(title);
                }
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();
            }
        }
    }

}
