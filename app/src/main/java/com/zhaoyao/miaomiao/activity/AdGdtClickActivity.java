package com.zhaoyao.miaomiao.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qq.e.ads.banner.BannerView;
import com.zhaoyao.miaomiao.BaseApplication;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.handler.TaskHandler;
import com.zhaoyao.miaomiao.handler.TaskHandlerImpl;
import com.zhaoyao.miaomiao.service.InstallerAccessibilityService;
import com.zhaoyao.miaomiao.util.Constants;
import com.zhaoyao.miaomiao.util.OpencvUtils;
import com.zhaoyao.miaomiao.util.ToastUtil;
import com.zhaoyao.miaomiao.util.commonly.LogUtils;
import com.zhaoyao.miaomiao.util.permission.PermissionNewActivity;
import com.zhaoyao.miaomiao.util.preferences.DataAccessSharedPreferencesUtils;
import com.zhaoyao.miaomiao.views.imageview.BannerViewClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.InstallCallbackInterface;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdGdtClickActivity extends BaseNewActivity implements View.OnClickListener
        , LoaderCallbackInterface
        , PermissionNewActivity.PermissionNewActivityCallBack, TaskHandler, BannerViewClick.ExposureListener {

    private static final String TAG = "AdGdtClickActivity";
    /**
     * 0
     */
    private TextView mTvExposure;
    /**
     * 0
     */
    private EditText mEtSrc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_gdt_click);
        currentGroup = getIntent().getIntExtra("AD_GROUP", 0);
        initView(currentGroup);
        PermissionNewActivity.startActivityForResult(this, null, null, null, this
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , "android.permission.INJECT_EVENTS"
        );
    }

    private Map<String, BannerView> mapGdt = new HashMap<>();

    private final List<File> fileList = new ArrayList<>();

    private synchronized void addGdtBannerView() {
        fileList.clear();
        String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MiaoMiao/";
        File file = new File(s);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            fileList.addAll(Arrays.asList(files));
        }

        doCloseBanner();
        mapGdt.clear();
        if (currentGroup == 0) {
            addAdView1();
        } else if (currentGroup == 1) {
            addAdView2();
        } else if (currentGroup == 2) {
            addAdView3();
        }
        for (String key : mapGdt.keySet()) {
            mapGdt.get(key).loadAD();
        }
    }

    private void addAdView1() {
        LinearLayout ll_gdt = (LinearLayout) findViewById(R.id.ll_gdt);
        for (int i = 0; i < ll_gdt.getChildCount(); i++) {
            View childAt = ll_gdt.getChildAt(i);
            if (childAt instanceof LinearLayout) {
                LinearLayout view = (LinearLayout) childAt;
                BannerViewClick bannerView = null;
                if (i == 0) {
                    bannerView = initBanner(Constants.BannerPosID);
                } else if (i == 1) {
                    bannerView = initBanner(Constants.BannerPosID2);
                } else if (i == 2) {
                    bannerView = initBanner(Constants.BannerPosID3);
                } else if (i == 3) {
                    bannerView = initBanner(Constants.BannerPosID4);
                } else if (i == 4) {
                    bannerView = initBanner(Constants.BannerPosID5);
                } else if (i == 5) {
                    bannerView = initBanner(Constants.BannerPosID6);
                } else if (i == 6) {
                    bannerView = initBanner(Constants.BannerPosID7);
                } else if (i == 7) {
                    bannerView = initBanner(Constants.BannerPosID8);
                } else if (i == 8) {
                    bannerView = initBanner(Constants.BannerPosID9);
                } else if (i == 9) {
                    bannerView = initBanner(Constants.BannerPosID10);
                }
                if (bannerView != null) {
                    bannerView.setGroup(0);
                    bannerView.setCurrentGroup(currentGroup);
                    view.removeAllViews();
                    view.addView(bannerView);
                    mapGdt.put(bannerView.getBannerPosID(), bannerView);
                }
            }
        }
    }

    private void addAdView2() {
        LinearLayout ll_gdt2 = (LinearLayout) findViewById(R.id.ll_gdt2);
        for (int i = 0; i < ll_gdt2.getChildCount(); i++) {
            View childAt = ll_gdt2.getChildAt(i);
            if (childAt instanceof LinearLayout) {
                LinearLayout view = (LinearLayout) childAt;
                BannerViewClick bannerView = null;
                if (i == 0) {
                    bannerView = initBanner(Constants.BannerPosID11);
                } else if (i == 1) {
                    bannerView = initBanner(Constants.BannerPosID12);
                } else if (i == 2) {
                    bannerView = initBanner(Constants.BannerPosID13);
                } else if (i == 3) {
                    bannerView = initBanner(Constants.BannerPosID14);
                } else if (i == 4) {
                    bannerView = initBanner(Constants.BannerPosID15);
                } else if (i == 5) {
                    bannerView = initBanner(Constants.BannerPosID16);
                } else if (i == 6) {
                    bannerView = initBanner(Constants.BannerPosID17);
                } else if (i == 7) {
                    bannerView = initBanner(Constants.BannerPosID18);
                } else if (i == 8) {
                    bannerView = initBanner(Constants.BannerPosID19);
                } else if (i == 9) {
                    bannerView = initBanner(Constants.BannerPosID20);
                }
                if (bannerView != null) {
                    bannerView.setGroup(1);
                    bannerView.setCurrentGroup(currentGroup);
                    view.removeAllViews();
                    view.addView(bannerView);
                    mapGdt.put(bannerView.getBannerPosID(), bannerView);
                }
            }
        }
    }

    private void addAdView3() {
        LinearLayout ll_gdt3 = (LinearLayout) findViewById(R.id.ll_gdt3);
        for (int i = 0; i < ll_gdt3.getChildCount(); i++) {
            View childAt = ll_gdt3.getChildAt(i);
            if (childAt instanceof LinearLayout) {
                LinearLayout view = (LinearLayout) childAt;
                BannerViewClick bannerView = null;
                if (i == 0) {
                    bannerView = initBanner(Constants.BannerPosID21);
                } else if (i == 1) {
                    bannerView = initBanner(Constants.BannerPosID22);
                } else if (i == 2) {
                    bannerView = initBanner(Constants.BannerPosID23);
                } else if (i == 3) {
                    bannerView = initBanner(Constants.BannerPosID24);
                } else if (i == 4) {
                    bannerView = initBanner(Constants.BannerPosID25);
                } else if (i == 5) {
                    bannerView = initBanner(Constants.BannerPosID26);
                } else if (i == 6) {
                    bannerView = initBanner(Constants.BannerPosID27);
                } else if (i == 7) {
                    bannerView = initBanner(Constants.BannerPosID28);
                } else if (i == 8) {
                    bannerView = initBanner(Constants.BannerPosID29);
                } else if (i == 9) {
                    bannerView = initBanner(Constants.BannerPosID30);
                }
                if (bannerView != null) {
                    bannerView.setGroup(2);
                    bannerView.setCurrentGroup(currentGroup);
                    view.removeAllViews();
                    view.addView(bannerView);
                    mapGdt.put(bannerView.getBannerPosID(), bannerView);
                }
            }
        }
    }


    private BannerViewClick initBanner(String BannerPosID) {
        return new BannerViewClick(this, BannerPosID, this);
    }


    private void doCloseBanner() {
        for (String key : mapGdt.keySet()) {
            BannerView bannerView = mapGdt.get(key);
            ViewParent parent = bannerView.getParent();
            if (parent instanceof LinearLayout) {
                LinearLayout view = (LinearLayout) parent;
                view.removeAllViews();
            }
            bannerView.destroy();
        }
    }


    private void initView(int position) {
        mTvExposure = (TextView) findViewById(R.id.tvExposure);
        mEtSrc = (EditText) findViewById(R.id.etSrc);
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnFilter).setOnClickListener(this);
        findViewById(R.id.btnObtain).setOnClickListener(this);

        FrameLayout fl_ad = (FrameLayout) findViewById(R.id.fl_ad);
        String[] stringArray = getResources().getStringArray(R.array.ad_spinner);
        if (stringArray.length == 0) return;
        View[] views = new View[stringArray.length];
        for (int i = 0; i < fl_ad.getChildCount(); i++) {
            View childAt = fl_ad.getChildAt(i);
            if (childAt.getId() == R.id.ll_gdt) {
                views[0] = childAt;
            } else if (childAt.getId() == R.id.ll_gdt2) {
                views[1] = childAt;
            } else if (childAt.getId() == R.id.ll_gdt3) {
                views[2] = childAt;
            }
        }
        fl_ad.removeViewInLayout(views[position]);
        fl_ad.addView(views[position]);
        fl_ad.invalidate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btnFilter:
                LinearLayout childAtFilter = null;
                if (currentGroup == 0) {
                    childAtFilter = (LinearLayout) ((LinearLayout) findViewById(R.id.ll_gdt)).getChildAt(Integer.parseInt(mEtSrc.getText().toString().trim()));
                }else if (currentGroup == 1){
                    childAtFilter = (LinearLayout) ((LinearLayout) findViewById(R.id.ll_gdt2)).getChildAt(Integer.parseInt(mEtSrc.getText().toString().trim()));
                }else if (currentGroup == 2){
                    childAtFilter = (LinearLayout) ((LinearLayout) findViewById(R.id.ll_gdt3)).getChildAt(Integer.parseInt(mEtSrc.getText().toString().trim()));
                }
                if (childAtFilter == null)return;

                BannerViewClick bannerViewFilter = (BannerViewClick) childAtFilter.getChildAt(0);
                if (bannerViewFilter == null)return;
                long l = System.currentTimeMillis() - bannerViewFilter.getTime();
                if (l > 20 * 1000) {
                    LogUtils.d("System.currentTimeMillis() - bannerViewFilter.getTime()=\t" + l);
                    return;
                }
                ToastUtil.toastSome(this, "过滤成功");
                new SaveBitmapAsyncTask(this).execute(bannerViewFilter);
                break;
            case R.id.btnDisplay:
                break;
            case R.id.btnObtain:

//                new MyAsyncTask(this).execute();
                break;
            case R.id.btnStart:
                boolean b = InstallerAccessibilityService.startActivitySettings(this);
                if (b) {
                    Button button = (Button) v;
                    mHandler.removeMessages(MSG_START);
                    Message msg = mHandler.obtainMessage();
                    msg.what = MSG_START;
                    if ("开始".equals(button.getText().toString().trim())) {
                        msg.obj = true;
                        button.setText("停止");
                        ToastUtil.toastSome(this, "5 秒后 开始");
                        mHandler.sendMessage(msg);
                    } else if ("停止".equals(button.getText().toString().trim())) {
                        button.setText("开始");
                        ToastUtil.toastSome(this, " 停止");
                        msg.obj = false;
                        mHandler.sendMessageDelayed(msg, 5 * 1000);
                    }
                }
                break;
        }
    }

    public class SaveBitmapAsyncTask extends AsyncTask<View, Void, Uri> {

        private Context context;

        public SaveBitmapAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected Uri doInBackground(View... views) {
            if (views == null || views.length == 0) return null;
            Bitmap cacheBitmapFromView = getCacheBitmapFromView(views[0]);
            File share = saveBitmap(cacheBitmapFromView);
            if (share == null) return null;
            return Uri.fromFile(share);
        }

        @Override
        protected void onPostExecute(Uri uri) {

        }
    }

    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }


    private Handler mHandler = new TaskHandlerImpl(this);

    private final int MSG_START = 11;

    @Override
    public void handleMessage(WeakReference weakReference, Message msg) {
        if (msg.what == MSG_START) {
            try {
                isStart = Boolean.parseBoolean(msg.obj.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void simulateClick(View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        float x = rect.centerX();
        float y = rect.centerY();
        MotionEvent downEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_DOWN, x, y, 0);
        // 可以不用在 Activity 中增加任何处理，各 Activity 都可以响应
        Instrumentation inst = new Instrumentation();
        inst.sendPointerSync(downEvent);
        MotionEvent upEvent = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
                MotionEvent.ACTION_UP, x, y, 0);
        inst.sendPointerSync(upEvent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.d("onNewIntent");
    }


    public String getLauncherPackageName(Context context) {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        if (res.activityInfo == null) {
            return "";
        }
        //如果是不同桌面主题，可能会出现某些问题，这部分暂未处理
        if (res.activityInfo.packageName.equals("android")) {
            return "";
        } else {
            return res.activityInfo.packageName;
        }
    }

    @Override
    protected void onDestroy() {
        doCloseBanner();
        mapGdt.clear();
        mExecutorService.shutdownNow();
        super.onDestroy();
    }

    static {
        System.loadLibrary("opencv_java3");
    }

    @Override
    public void onManagerConnected(int status) {
        ToastUtil.toastSome(this, "onManagerConnected:\t" + status);
    }

    @Override
    public void onPackageInstall(int operation, InstallCallbackInterface callback) {

    }

    @Override
    public void callBackResult(boolean agreement) {
        if (agreement) {
            addGdtBannerView();
        } else {
            ToastUtil.toastSome(this, "权限不够");
            finish();
        }
    }

    private final List<BannerView> bannerViewList = new ArrayList<>();

    private boolean isClick = false;

    private ExecutorService mExecutorService = Executors.newFixedThreadPool(1);

    private int currentGroup = 0;

    private boolean isOnPause = false;

    private boolean isStop = false;

    private boolean isStart = false;

    @Override
    protected void onResume() {
        super.onResume();
        isOnPause = false;
        isClick = false;
        if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, this);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            this.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        SharedPreferences sharedPreferences = DataAccessSharedPreferencesUtils.newInstance().getSharedPreferences("BannerViewClick_" + yyyyMMdd.format(new Date()));
        Map<String, ?> all = sharedPreferences.getAll();
        if (all != null) {
            int number = 0;
            for (String s : all.keySet()) {
                try {
                    Object o = all.get(s);
                    int i = Integer.parseInt(o.toString());
                    number += i;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            mTvExposure.setText(("" + number));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnPause = true;
    }

    @Override
    public void onADExposure(BannerView bannerView) {
        if (!isStart || isOnPause) {
            return;
        }
        BannerViewClick bannerViewClick = (BannerViewClick) bannerView;
        if (currentGroup != bannerViewClick.getGroup()) {
            return;
        }
        if (!isClick) {
            if (mExecutorService.isShutdown()) {
                mExecutorService = Executors.newFixedThreadPool(1);
            }
            mExecutorService.execute(new BannerViewRunnable(bannerViewClick));
        }
    }

    @Override
    public void onADClicked(BannerView bannerView) {
        String bannerPosID = ((BannerViewClick) bannerView).getBannerPosID();
        Integer integer = BaseApplication.map.get(bannerPosID);
        LogUtils.d(bannerPosID + "\t:\t" + integer);
    }

    private void addFileList(File file) {
        synchronized (fileList) {
            fileList.add(file);
        }
    }

    private void removeFileList(File file) {
        synchronized (fileList) {
            fileList.remove(file);
        }
    }

    public class BannerViewRunnable implements Runnable {

        private BannerViewClick bannerViewClick;

        public BannerViewRunnable(BannerViewClick bannerViewClick) {
            this.bannerViewClick = bannerViewClick;
        }

        @Override
        public void run() {
            if (isStop) return;
            if (!isClick) {
                synchronized (bannerViewList) {
                    if (isStop) return;
                    if (!isClick) {
                        if (bannerViewClick == null || bannerViewClick.getVisibility() != View.VISIBLE) {
                            return;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        long time = bannerViewClick.getTime();
                        if (System.currentTimeMillis() - time >= 10 * 1000) {
                            return;
                        }
                        Bitmap bitmap_src = getCacheBitmapFromView(bannerViewClick);
                        if (bitmap_src == null) return;
                        if (fileList.size() == 0) {
                            saveBitmap(bitmap_src);
                            isClick = true;
                            simulateClick(bannerViewClick);
                        } else {
                            for (int i = 0; i < fileList.size(); i++) {
                                File file = fileList.get(i);
                                if (isStop) break;
                                if (!file.exists() || !file.isFile()) {
                                    removeFileList(file);
                                    break;
                                }
                                Bitmap bitmap_des = BitmapFactory.decodeFile(file.getAbsolutePath());
                                if (bitmap_des == null) continue;
                                OpencvUtils opencvUtils = OpencvUtils.newInstance();
                                double compareSimilarity = opencvUtils.compareSimilarity(bitmap_src, bitmap_des);
                                if (compareSimilarity >= 0.5) {
                                    bitmap_des.recycle();
                                    LogUtils.d("compareSimilarity >= 0.5\t:\t" + compareSimilarity);
                                    return;
                                }

                                if (i == fileList.size() - 1) {
                                    if (bannerViewClick == null || bannerViewClick.getVisibility() != View.VISIBLE) {
                                        bitmap_des.recycle();
                                        break;
                                    }
                                    LogUtils.d("compareSimilarity < 0.5\t:\t" + compareSimilarity);
                                    saveBitmap(bitmap_src);
                                    isClick = true;
                                    simulateClick(bannerViewClick);
                                    bitmap_des.recycle();
                                    break;
                                }
                                bitmap_des.recycle();
                            }
                        }
                        bitmap_src.recycle();
                    }
                }
            }
        }
    }

    private synchronized File saveBitmap(Bitmap bitmap_src) {
        FileOutputStream out = null;
        try {
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MiaoMiao/" + "click_" + System.currentTimeMillis() + ".jpg";
            File f = new File(dir);
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                f.createNewFile();
            }
            out = new FileOutputStream(f);
            bitmap_src.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            addFileList(f);
            return f;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public class SaveClickBitmapAsyncTask extends AsyncTask<View, Void, Uri> {

        private Context context;

        public SaveClickBitmapAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected Uri doInBackground(View... views) {
            if (views == null || views.length == 0) return null;
            Bitmap bm = getCacheBitmapFromView(views[0]);
            FileOutputStream out = null;
            try {
                String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MiaoMiao/" + "click_" + System.currentTimeMillis() + ".jpg";
                File f = new File(dir);
                if (!f.exists()) {
                    f.getParentFile().mkdirs();
                    f.createNewFile();
                }
                out = new FileOutputStream(f);
                bm.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                return Uri.fromFile(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Uri uri) {

        }
    }

    /**
     * 在线获取过滤图片
     */
    public class ObtainAsyncTask extends AsyncTask<Void, Void, List<File>> {

        private Context context;

        public ObtainAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected List<File> doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(List<File> hashMaps) {
            LogUtils.d("MyAsyncTask", hashMaps);
        }
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, JSONArray> {
        private Context context;

        public MyAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {
            return getItems2j(context);
        }

        @Override
        protected void onPostExecute(JSONArray hashMaps) {
            LogUtils.d("MyAsyncTask", hashMaps);
        }
    }


    public JSONArray getItems2j(Context context) {
        PackageManager pckMan = context.getPackageManager();
        JSONArray items = new JSONArray();
        List<PackageInfo> packageInfo = pckMan.getInstalledPackages(0);
        for (PackageInfo pInfo : packageInfo) {
            JSONObject item = new JSONObject();
            try {
                item.put("appimage", pInfo.applicationInfo.loadIcon(pckMan));
                item.put("packageName", pInfo.packageName);
                item.put("versionCode", pInfo.versionCode);
                item.put("versionName", pInfo.versionName);
                item.put("appName", pInfo.applicationInfo.loadLabel(pckMan).toString());
                items.put(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return items;
    }

    public class BitmapAsyncTask extends AsyncTask<Void, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(Void... voids) {
            return null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Activity.RESULT_OK) {
            addGdtBannerView();
        } else {
            ToastUtil.toastSome(this, "权限不够");
            finish();
        }
    }

    /**
     * 分享图片
     *
     * @param uri
     */
    private void shareBitmap(Uri uri) {
        if (uri != null) {
            Intent share_intent = new Intent();
            share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
            share_intent.setType("image/*");  //设置分享内容的类型
            share_intent.putExtra(Intent.EXTRA_STREAM, uri);
            //创建分享的Dialog
            share_intent = Intent.createChooser(share_intent, "广告图片分享");
            startActivity(share_intent);
        } else {
            ToastUtil.toastSome(this, "图片保存失败");
        }
    }


}
