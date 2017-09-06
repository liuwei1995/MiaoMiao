package com.sohuvideo.ui_plugin.player;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.sohuvideo.api.SohuPlayerDefinition;
import com.sohuvideo.api.SohuPlayerSetting;
import com.sohuvideo.ui_plugin.R;
import com.sohuvideo.ui_plugin.utils.FormatUtils;
import com.sohuvideo.ui_plugin.utils.ViewVisUtils;

import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MediaController implements View.OnClickListener {


    private static final int AUTO_SHOWN_SPAN = 5000;
    private static final int MSG_HIDE = 0;

    private boolean mIsFullScreen = false;// 当前是否是全屏

    private Context mContext;
    private View mContentView;

    private RelativeLayout mLitePlayerContainer;
    private ImageView mLitePlayPauseImg;
    private TextView mLiteCurrentTime;
    private TextView mLiteTotalTime;
    private SeekBar mLiteSeekBar;
    private ImageView mLiteFullscreenImg;
    private TextView mLiteMediaTitle;
    private ImageView mLiteBackImag;

    private RelativeLayout mFullPlayerContainer;
    private FrameLayout mFullPlayPuseLayout;
    private ImageView mFullPlayPauseImg;
    private TextView mFullCurrentTime;
    private TextView mFullTotalTime;
    private SeekBar mFullSeekBar;
    private ImageView mFullBackImag;
    private TextView mFullMediaTitle;

    private RelativeLayout mAdvertPlayerContainer;
    private RelativeLayout mAdvertTimeLayout;
    private TextView mRemainTimeText;
    private ImageView mAdFullscreenImgview;
    private RelativeLayout mAdGoDetail;

    private FrameLayout mFullCurrentDefinitionLayout;
    private TextView mFullCurrentDefinitionText;
    private LinearLayout mFullAllDefinitionLayout;
    private RelativeLayout mFullFluencyDefinitionLayout;
    private TextView mFullFluencyDefinitionText;
    private RelativeLayout mFullHighDefinitionLayout;
    private TextView mFullHighDefinitionText;
    private RelativeLayout mFullSuperDefinitionLayout;
    private TextView mFullSuperDefinitionText;
    private RelativeLayout mFullOriginalDefinitionLayout;
    private TextView mFullOriginalDefinitionText;
    private FrameLayout mFullNextLayout;

    private ViewGroup mGestureProgressGroup;
    private ImageView mGestureForwardImg;
    private TextView mGestureTipTxt;
    private ImageView mGestureBackwardImg;
    private TextView mGestureCurProgressTxt;
    private TextView mGestureTotalProgressTxt;

    private ViewGroup mGestureVolumnGroup;
    private ImageView mVolumnIconImg;
    private TextView mVolumnPercentTxt;


    //手势
    private GestureDetector mGestureDetector;
    private MyGestureListener mGestureListener;
    private int gestureType = TYPE_UNKNOWN;
    public static final int TYPE_HORIZONTAL = 1;
    public static final int TYPE_VERTICAL = 2;
    public static final int TYPE_UNKNOWN = -1;
    public static final int MIN_DISTANCE = 10;
    private int seekPosition = 0;


    private OnMediaControllerClickListener mListener;


    private int mCurrent; //当前进度值
    private int mDuration;//总时间
    private boolean mDragging;//seekBar是否再被拖动

    private final static HashMap<Integer, String> map;

    static {
        map = new HashMap<Integer, String>();
        map.put(SohuPlayerDefinition.PE_DEFINITION_NOR, "标清");
        map.put(SohuPlayerDefinition.PE_DEFINITION_HIGH, "高清");
        map.put(SohuPlayerDefinition.PE_DEFINITION_SUPER, "超清");
        map.put(SohuPlayerDefinition.PE_DEFINITION_ORIGINAL, "原画");
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_HIDE: {
                    hideControl();
                    break;
                }
            }
        }
    };

    /**
     * 发送延迟消息隐藏 控制栏
     *
     * @param sec
     */
    private void hideDelayed(int sec) {
        mHandler.removeMessages(MSG_HIDE);
        if (sec > 0) {
            mHandler.sendEmptyMessageDelayed(MSG_HIDE, sec);
        }
    }

    public MediaController(Context context, OnMediaControllerClickListener listener) {
        this.mContext = context;
        this.mListener = listener;
        initView();
        initViewListener();
    }


    private void initView() {
        mContentView = View.inflate(mContext, R.layout.media_screen_controller, null);

        mLitePlayerContainer = (RelativeLayout) mContentView.findViewById(R.id.layout_lite_media_controller);

        mLitePlayPauseImg = (ImageView) mContentView.findViewById(R.id.lite_media_play_pause_img);
        mLiteCurrentTime = (TextView) mContentView.findViewById(R.id.lite_media_current_time_text);
        mLiteTotalTime = (TextView) mContentView.findViewById(R.id.lite_media_total_time_text);
        mLiteSeekBar = (SeekBar) mContentView.findViewById(R.id.lite_media_seekbar);
        mLiteFullscreenImg = (ImageView) mContentView.findViewById(R.id.lite_media_fullscreen_imgview);
        mLiteMediaTitle = (TextView) mContentView.findViewById(R.id.lite_media_title_text);
        mLiteBackImag = (ImageView) mContentView.findViewById(R.id.lite_media_title_back);


        mFullPlayerContainer = (RelativeLayout) mContentView.findViewById(R.id.layout_fullscreen_media_controller);
        mFullPlayPuseLayout = (FrameLayout) mContentView.findViewById(R.id.full_media_play_pause_layout);
        mFullPlayPauseImg = (ImageView) mContentView.findViewById(R.id.full_media_play_pause_img);

        mFullCurrentTime = (TextView) mContentView.findViewById(R.id.full_media_current_time_text);
        mFullTotalTime = (TextView) mContentView.findViewById(R.id.full_media_total_time_text);
        mFullSeekBar = (SeekBar) mContentView.findViewById(R.id.full_media_seekbar);
        mFullBackImag = (ImageView) mContentView.findViewById(R.id.full_back_img);
        mFullMediaTitle = (TextView) mContentView.findViewById(R.id.full_media_title_text);
        mFullNextLayout = (FrameLayout) mContentView.findViewById(R.id.full_next_layout);


        //清晰度
        mFullCurrentDefinitionLayout = (FrameLayout) mContentView.findViewById(R.id.full_current_definition_layout);
        mFullCurrentDefinitionText = (TextView) mContentView.findViewById(R.id.full_current_definition_tv);

        mFullAllDefinitionLayout = (LinearLayout) mContentView.findViewById(R.id.full_all_definition_layout);

        mFullFluencyDefinitionLayout = (RelativeLayout) mContentView.findViewById(R.id.full_definition_fluency_layout);
        mFullFluencyDefinitionText = (TextView) mContentView.findViewById(R.id.full_definition_fluency_text);

        mFullHighDefinitionLayout = (RelativeLayout) mContentView.findViewById(R.id.full_definition_high_layout);
        mFullHighDefinitionText = (TextView) mContentView.findViewById(R.id.full_definition_high_text);

        mFullSuperDefinitionLayout = (RelativeLayout) mContentView.findViewById(R.id.full_definition_super_layout);
        mFullSuperDefinitionText = (TextView) mContentView.findViewById(R.id.full_definition_super_text);

        mFullOriginalDefinitionLayout = (RelativeLayout) mContentView.findViewById(R.id.full_definition_original_layout);
        mFullOriginalDefinitionText = (TextView) mContentView.findViewById(R.id.full_definition_original_text);

        //手势
        mGestureProgressGroup = (ViewGroup) mContentView.findViewById(R.id.gesture_layout_progress);
        mGestureForwardImg = (ImageView) mContentView.findViewById(R.id.gesture_forward_progress);
        mGestureTipTxt = (TextView) mContentView.findViewById(R.id.gesture_tip_progress);
        mGestureBackwardImg = (ImageView) mContentView.findViewById(R.id.gesture_backward_progress);
        mGestureCurProgressTxt = (TextView) mContentView.findViewById(R.id.gesture_cur_progress);
        mGestureTotalProgressTxt = (TextView) mContentView.findViewById(R.id.gesture_total_progress);
        mGestureVolumnGroup = (ViewGroup) mContentView.findViewById(R.id.gesture_layout_volumn);
        mVolumnIconImg = (ImageView) mContentView.findViewById(R.id.gesture_icon_volumn);
        mVolumnPercentTxt = (TextView) mContentView.findViewById(R.id.gesture_percent_volumn);

        //广告
        mAdvertPlayerContainer = (RelativeLayout) mContentView.findViewById(R.id.layout_advert_media_controller);
        mAdvertTimeLayout = (RelativeLayout) mContentView.findViewById(R.id.advert_time_layout);
        mRemainTimeText = (TextView) mContentView.findViewById(R.id.remain_time_text);
        mAdFullscreenImgview = (ImageView) mContentView.findViewById(R.id.ad_fullscreen_imgview);
        mAdGoDetail = (RelativeLayout) mContentView.findViewById(R.id.ad_go_detail);
    }

    private void initViewListener() {
        mLitePlayerContainer.setOnClickListener(this);
        mLitePlayPauseImg.setOnClickListener(this);
        mLiteSeekBar.setOnSeekBarChangeListener(mProgressSeekBarChangeListener);
        mLiteFullscreenImg.setOnClickListener(this);
        mLiteBackImag.setOnClickListener(this);

        mFullPlayerContainer.setOnClickListener(this);
        mFullPlayPuseLayout.setOnClickListener(this);
        mFullSeekBar.setOnSeekBarChangeListener(mProgressSeekBarChangeListener);
        mFullBackImag.setOnClickListener(this);
        mFullCurrentDefinitionLayout.setOnClickListener(this);
        mFullNextLayout.setOnClickListener(this);

        mAdFullscreenImgview.setOnClickListener(this);
        mAdGoDetail.setOnClickListener(this);

        mFullFluencyDefinitionLayout.setOnClickListener(mOnDefinitionItemClick);
        mFullHighDefinitionLayout.setOnClickListener(mOnDefinitionItemClick);
        mFullSuperDefinitionLayout.setOnClickListener(mOnDefinitionItemClick);
        mFullOriginalDefinitionLayout.setOnClickListener(mOnDefinitionItemClick);

        mGestureListener = new MyGestureListener(mContext);
        mGestureDetector = new GestureDetector(mContext, mGestureListener);
        mFullPlayerContainer.setOnTouchListener(mFullTouchListener);

    }

    public View getContentView() {
        return mContentView;
    }

    public void setFullScreenMode(boolean isFullScreen) {
        mIsFullScreen = isFullScreen;
    }

    /**
     * 更新播放暂停按钮的图片
     *
     * @param isPlay
     */
    public void updatePlayPauseState(boolean isPlay) {
        mLitePlayPauseImg.setImageResource(isPlay ? R.drawable.player_icon_pause : R.drawable.player_icon_play);
        mFullPlayPauseImg.setImageResource(isPlay ? R.drawable.player_icon_pause : R.drawable.player_icon_play);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        mLiteMediaTitle.setText(title);
        mFullMediaTitle.setText(title);
    }

    /**
     * 当前清晰度
     */
    public void updateDefinition() {
        int definition = SohuPlayerSetting.getPreferDefinition();
        if (mListener != null) {
            definition = mListener.getCurrentDefinition();
        }
        if (mFullCurrentDefinitionText != null && map.get(definition) != null) {
            mFullCurrentDefinitionText.setText(map.get(definition));
        }
    }

    /**
     * 设置进度信息，如果可见，则更新进度条
     *
     * @param current
     * @param duration
     */
    public void setProgress(int current, int duration) {
        if (!mDragging) {
            this.mCurrent = current / 1000;
            this.mDuration = duration / 1000;
            //if (isShowing()) {
            updateProgressView();
            //}
        }
    }

    /**
     * 更新进度
     */
    private void updateProgressView() {
        mLiteCurrentTime.setText(formatTime(mCurrent) + "/");
        mLiteTotalTime.setText(formatTime(mDuration));

        mFullCurrentTime.setText(formatTime(mCurrent));
        mFullTotalTime.setText(formatTime(mDuration));

        if (mDuration == 0) {
            mLiteSeekBar.setProgress(0);
            mFullSeekBar.setProgress(0);
        } else {
            mLiteSeekBar.setMax(mDuration);
            mLiteSeekBar.setProgress(mCurrent);

            mFullSeekBar.setMax(mDuration);
            mFullSeekBar.setProgress(mCurrent);
        }

    }

    /**
     * 格式化时间
     */
    private String formatTime(int seconds) {
        int second = seconds % 60;
        int minutes = (seconds / 60) % 60;
        int hours = seconds / 3600;
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        mFormatBuilder.setLength(0);
        return mFormatter.format("%d:%02d:%02d", hours, minutes, second)
                .toString();
    }

    private void clickDefinition() {
        if (!hideDefinitionSelector()) {
            setDefinitionLayout();
        }
    }

    private boolean hideDefinitionSelector() {
        if (mFullAllDefinitionLayout.getVisibility() == View.VISIBLE) {
            mFullAllDefinitionLayout.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    /**
     * 设置清晰度选项框布局位置
     */
    private void setDefinitionLayout() {

        int left = 0;
        if (mFullCurrentDefinitionLayout != null) {
            int[] location = new int[2];
            mFullCurrentDefinitionLayout.getLocationOnScreen(location);
            left = location[0];
        }
        if (mFullAllDefinitionLayout != null) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mFullAllDefinitionLayout.getLayoutParams();
            if (params != null) {
                params.leftMargin = left;
            }
        }
        int cur = mListener.getCurrentDefinition();
        List<Integer> definitions = mListener.getSupportDefinitions();
        mFullFluencyDefinitionLayout.setVisibility(View.GONE);
        mFullHighDefinitionLayout.setVisibility(View.GONE);
        mFullSuperDefinitionLayout.setVisibility(View.GONE);
        mFullOriginalDefinitionLayout.setVisibility(View.GONE);
        for (Integer definition : definitions) {
            if (definition != cur) {
                if (definition == SohuPlayerDefinition.PE_DEFINITION_NOR) {
                    mFullFluencyDefinitionLayout.setVisibility(View.VISIBLE);
                    mFullFluencyDefinitionLayout.setTag(definition);
                } else if (definition == SohuPlayerDefinition.PE_DEFINITION_HIGH) {
                    mFullHighDefinitionLayout.setVisibility(View.VISIBLE);
                    mFullHighDefinitionLayout.setTag(definition);
                } else if (definition == SohuPlayerDefinition.PE_DEFINITION_SUPER) {
                    mFullSuperDefinitionLayout.setVisibility(View.VISIBLE);
                    mFullSuperDefinitionLayout.setTag(definition);
                } else if (definition == SohuPlayerDefinition.PE_DEFINITION_ORIGINAL) {
                    mFullOriginalDefinitionLayout.setVisibility(View.VISIBLE);
                    mFullOriginalDefinitionLayout.setTag(definition);
                }
            }
        }
        //如果只支持一个清晰度，并且为当前清晰度，则不显示清晰度选项框
        if (definitions.size() == 1 && definitions.get(0) == cur){
            mFullAllDefinitionLayout.setVisibility(View.GONE);
        }else {
            mFullAllDefinitionLayout.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 清晰度选项框item点击事件
     */
    private final View.OnClickListener mOnDefinitionItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            mFullAllDefinitionLayout.setVisibility(View.GONE);
            int definition = (Integer) view.getTag();
            SohuPlayerSetting.setPreferDefinition(definition);
            mListener.changeDefinition(definition);
            hideControl();

//            if (view == mFullFluencyDefinitionLayout){
//                SohuPlayerSetting.setPreferDefinition(SohuPlayerDefinition.PE_DEFINITION_FLUENCY);
//            }
//            else if (view == mFullHighDefinitionLayout){
//                SohuPlayerSetting.setPreferDefinition(SohuPlayerDefinition.PE_DEFINITION_HIGH);
//            }
//            else if (view == mFullSuperDefinitionLayout){
//                SohuPlayerSetting.setPreferDefinition(SohuPlayerDefinition.PE_DEFINITION_SUPER);
//            }
//            else if (view == mFullOriginalDefinitionLayout){
//                SohuPlayerSetting.setPreferDefinition(SohuPlayerDefinition.PE_DEFINITION_ORIGINAL);
//            }
        }
    };

    /**
     * SeekBar拖动事件
     */
    private final SeekBar.OnSeekBarChangeListener mProgressSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
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

            if (mListener != null) {
                mListener.onSeekTo(mCurrent * 1000);
            }
            mDragging = false;
            hideDelayed(AUTO_SHOWN_SPAN);
        }
    };


    /**
     * 外部设置回调
     */
    public interface OnMediaControllerClickListener {

        void onPlayPauseClicked();

        void onSeekTo(int pos);

        void onFullScreenClicked();

        void onFullBackClicked();

        int getCurrentDefinition();

        List<Integer> getSupportDefinitions();

        void changeDefinition(int definition);

        boolean isAdvertInPlayback();

        int getCurrentPosition();

        int getDuration();

        void onPlayNextClicked();

        void onLiteBackClicked();

        void onAdGoDetailClicked();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.layout_lite_media_controller || id == R.id.layout_fullscreen_media_controller) {
            hideControl();
        } else if (id == R.id.lite_media_play_pause_img || id == R.id.full_media_play_pause_layout) {
            //点击 播放键
            if (mListener != null) {
                mListener.onPlayPauseClicked();
            }
        } else if (id == R.id.lite_media_fullscreen_imgview) {
            //点击小屏幕的全屏按键
            if (mListener != null) {
                mListener.onFullScreenClicked();
            }
        } else if (id == R.id.full_back_img) {
            //全屏点击返回键-->变小屏幕
            if (mListener != null) {
                mListener.onFullBackClicked();
            }
        } else if (id == R.id.full_current_definition_layout) {
            //点击清晰度按钮
            clickDefinition();//设置清晰度选择框的位置
            hideDelayed(AUTO_SHOWN_SPAN);
        } else if (id == R.id.full_next_layout) {
            //点击全屏控制栏的下一个
            if (mListener != null) {
                mListener.onPlayNextClicked();
            }
        }else if (id == R.id.lite_media_title_back) {
            //小屏幕点击返回键--退出该播放页面
            if (mListener != null) {
                mListener.onLiteBackClicked();
            }
        }else if (id == R.id.ad_fullscreen_imgview) {
            if (mListener != null) {
                mListener.onFullScreenClicked();
            }
        }else if (id == R.id.ad_go_detail) {
            if (mListener != null) {
                mListener.onAdGoDetailClicked();
            }
        }
    }

    public void displayStateAdRemainText(int time){
        String timeString = mContext.getString(R.string.remain_time_text, time);
        mRemainTimeText.setText(timeString);
    }
    // 隐藏屏幕控制器
    public void hideControl() {
        if (mLitePlayerContainer.getVisibility() == View.VISIBLE) {
            ViewVisUtils.setVisibility(mLitePlayerContainer, View.GONE);
        }
        if (mFullPlayerContainer.getVisibility() == View.VISIBLE) {
            ViewVisUtils.setVisibility(mFullPlayerContainer, View.GONE);
        }

    }

    public void showController() {
        if (mIsFullScreen) {
            ViewVisUtils.setVisibility(mLitePlayerContainer, View.GONE);
            ViewVisUtils.setVisibility(mFullPlayerContainer, View.VISIBLE);
            if (mFullAllDefinitionLayout.getVisibility() == View.VISIBLE) {
                mFullAllDefinitionLayout.setVisibility(View.GONE);
            }
        } else {
            ViewVisUtils.setVisibility(mFullPlayerContainer, View.GONE);
            ViewVisUtils.setVisibility(mLitePlayerContainer, View.VISIBLE);
        }
        updateDefinition();
        hideDelayed(AUTO_SHOWN_SPAN);
    }

    public void showAdvertControl() {
        ViewVisUtils.setVisibility(mAdvertPlayerContainer, View.VISIBLE);
    }

    public void hideAdvertControl() {
        if (mAdvertPlayerContainer.getVisibility() == View.VISIBLE) {
            ViewVisUtils.setVisibility(mAdvertPlayerContainer, View.GONE);
        }
    }


    //手势识别
    private final View.OnTouchListener mFullTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mListener.isAdvertInPlayback()) {
                return false;
            }
            mGestureDetector.onTouchEvent(event);
            if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
                //处理弹起
                if (gestureType == TYPE_UNKNOWN) {
                    hideControl();
                } else if (gestureType == TYPE_HORIZONTAL) {
                    hideDelayed(AUTO_SHOWN_SPAN);
                    mListener.onSeekTo(seekPosition);
                }
                mGestureProgressGroup.setVisibility(View.GONE);
                mGestureVolumnGroup.setVisibility(View.GONE);
            }

            return true;
        }
    };

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int FIVE_MIN = 5 * 60 * 1000;
        private static final int FORTY_FIVE_MIN = 45 * 60 * 1000;
        private static final int NINTY_MIN = 90 * 60 * 1000;

        private float myDistanceX = 0;
        private float myDistanceY = 0;
        private float downX = 0f;
        private float downY = 0f;
        private final Context myContext;
        private int mVolume = -1;
        private int mMaxVolume = 0;
        private final AudioManager mAudioManager;
        private int mScreenWidth = 0;
        private int mScreenHeight = 0;
        private int mMaxProgress = 0;
        private int mProgress = -1;
        private int factor = 1;

        private MyGestureListener(Context context) {
            this.myContext = context;
            WindowManager windowManager = (WindowManager) myContext.getSystemService(Context.WINDOW_SERVICE);
            mAudioManager = (AudioManager) myContext.getSystemService(Context.AUDIO_SERVICE);
            mScreenWidth = windowManager.getDefaultDisplay().getWidth();
            mScreenHeight = windowManager.getDefaultDisplay().getHeight();
        }

        @Override
        public boolean onDown(MotionEvent e) {
            myDistanceX = 0f;
            myDistanceY = 0f;
            downX = e.getX();
            downY = e.getY();
            gestureType = TYPE_UNKNOWN;
            hideDelayed(0);
            //处理按下的逻辑
            downVideo(e);
            return true;
        }

        private void downVideo(MotionEvent e) {
            mVolume = -1;
            mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0) {
                mVolume = 0;
            }
            mProgress = -1;
            if (mListener != null) {
                mProgress = mListener.getCurrentPosition();
                mMaxProgress = mListener.getDuration();
                if (mMaxProgress > NINTY_MIN) {
                    factor = 8;
                } else if (mMaxProgress > FORTY_FIVE_MIN) {
                    factor = 7;
                } else if (mMaxProgress > FIVE_MIN) {
                    factor = 5;
                } else {
                    factor = 1;
                }
            }
            if (mProgress < 0) {
                mProgress = 0;
            }
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            myDistanceX = e2.getX() - downX;
            myDistanceY = downY - e2.getY();

            if (gestureType == TYPE_UNKNOWN) {
                if (Math.abs(myDistanceY) > MIN_DISTANCE
                        && Math.abs(myDistanceY) > Math.abs(myDistanceX)) {
                    gestureType = TYPE_VERTICAL;
                } else if (Math.abs(myDistanceX) > MIN_DISTANCE
                        && Math.abs(myDistanceX) > Math.abs(myDistanceY)) {
                    gestureType = TYPE_HORIZONTAL;
                }
            }


            if (gestureType == TYPE_HORIZONTAL) {
                //处理横滑
                horizontalVideo(myDistanceX);
            } else if (gestureType == TYPE_VERTICAL) {
                //处理竖滑
                verticalVideo(myDistanceY);
            } else {
                return false;
            }
            return true;
        }

        private void horizontalVideo(float distance) {
            seekPosition = mProgress + (int) (distance * mMaxProgress / (mScreenWidth * factor));
            if (seekPosition < 0) {
                seekPosition = 0;
            } else if (seekPosition > mMaxProgress) {
                seekPosition = mMaxProgress;
            }

            mGestureProgressGroup.setVisibility(View.VISIBLE);
            if (distance > 0) {
                mGestureForwardImg.setVisibility(View.VISIBLE);
                mGestureBackwardImg.setVisibility(View.GONE);
                mGestureTipTxt.setText("快进");
                mGestureCurProgressTxt.setText(FormatUtils
                        .secondToString(seekPosition / 1000));
                mGestureTotalProgressTxt.setText(FormatUtils
                        .secondToString(mMaxProgress / 1000));
            } else {
                mGestureForwardImg.setVisibility(View.GONE);
                mGestureBackwardImg.setVisibility(View.VISIBLE);
                mGestureTipTxt.setText("快退");
                mGestureCurProgressTxt.setText(FormatUtils
                        .secondToString(seekPosition / 1000));
                mGestureTotalProgressTxt.setText(FormatUtils
                        .secondToString(mMaxProgress / 1000));
            }

        }

        private void verticalVideo(float distance) {
            mGestureVolumnGroup.setVisibility(View.VISIBLE);
            int curVolume = mVolume
                    + (int) (distance * mMaxVolume / mScreenHeight);
            if (curVolume < 0) {
                curVolume = 0;
            } else if (curVolume > mMaxVolume) {
                curVolume = mMaxVolume;
            }
            if (curVolume == 0) {
                mVolumnIconImg.setImageResource(R.drawable.player_gesture_silence);
                mVolumnPercentTxt.setText("0%");
            } else {
                mVolumnIconImg.setImageResource(R.drawable.player_gesture_volume);
                mVolumnPercentTxt.setText(String.format("%d%s",
                        (curVolume * 100 / mMaxVolume), "%"));
            }
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume,
                    0);
        }

    }
}