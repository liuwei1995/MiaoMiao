package com.zhaoyao.miaomiao.util.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.zhaoyao.miaomiao.R;

import java.util.ArrayList;
import java.util.List;

//import com.xinaliu.navigation.R;

/**
 * 权限Activity  别忘记添加  PermissionNewActivity
 * Created by liuwei on 2017/5/3
 * @author liuwei
 *  <p>{
         <activity
             android:name=".util.permission.PermissionNewActivity"
             android:configChanges="keyboardHidden|orientation|screenSize"
             android:theme="@android:style/Theme.Translucent.NoTitleBar"
             android:windowSoftInputMode="stateHidden|stateAlwaysHidden">
         </activity>
     </p>
 *
 * <li>启动方式</li>
 * @see  #startActivityForResult(Activity, String, Integer, Integer, PermissionNewActivityCallBack, String...)
 * @see  #startActivityForResult(Fragment, String, Integer, Integer, PermissionNewActivityCallBack, String...)
 * <li>返回结果 </li>
 * @see Activity#onActivityResult(int, int, Intent)
 * <p>
 *      <li>权限拒绝</li>
 *      <li>取得没有同意的权限</li>
 *      @see Intent#getExtras() {@link Bundle#getStringArray(String)}
 *      <li>String 的 key 为 {@link #KEY_INPUT_PERMISSIONS}</li>
 * </p>
 * <p>
 *     <li>权限同意</li>
 *     <li>
 *         resultCode 默认为 {@link #PERMISSION_REQUEST_CODE_OK}
 *         @see #startActivityForResult(Activity, String, Integer, Integer, PermissionNewActivityCallBack, String...) resultCode
 *     </li>
 * </p>
 *
 * <li>示例： PermissionNewActivity.startActivityForResult(this, null, null, null, Manifest.permission.CAMERA);</li>
 */

public class PermissionNewActivity extends Activity {
    /**
     * 权限拒绝后取出结果的key
     *  @see Intent#getExtras() {@link Bundle#getStringArray(String)}
     */
    public static final String KEY_INPUT_PERMISSIONS = "KEY_INPUT_PERMISSIONS";
    /**成功 result*/
    public static final int PERMISSION_RESULT_CODE_OK = Activity.RESULT_OK;
    /**成功 request*/
    public static final int PERMISSION_REQUEST_CODE_OK = 2;//
    /**不可以 result*/
    public static final int PERMISSION_RESULT_CODE_NOT = -2;//申请

    public static final String MESSAGE_KEY = "MESSAGE_KEY";

    public static final String RESULT_CODE_KEY = "RESULT_CODE_KEY";

    public static final String MESSAGE_DEFAULT = "当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。\n\n最后点击取消按钮，即可返回。";

    /**
     * 启动这个界面
     * @param activity Activity
     * @param message 弹窗的提示信息  默认{@link #MESSAGE_DEFAULT}
     * @param requestCode If >= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param resultCode 成功后要求放回的resultCode
     *                   @see Activity#onActivityResult(int, int, Intent)
     * @param permissions 需要申请的权限
     */
    public static void startActivityForResult(Activity activity, String message, Integer requestCode, Integer resultCode, PermissionNewActivityCallBack mPermissionNewActivityCallBack, String... permissions) {
        if (activity == null || permissions == null || permissions.length <= 0) return;

        ArrayList<String>  listApply = new ArrayList<>();
        int targetSdkVersion = getTargetSdkVersion(activity);
        for (String permission : permissions) {
            if (!checkPermission(activity, permission, targetSdkVersion)) {
                listApply.add(permission);
            }
        }
        if (listApply.size() == 0){
            if (mPermissionNewActivityCallBack != null){
                mPermissionNewActivityCallBack.callBackResult(true);
            }
            return;
        }
        Intent intent = new Intent(activity, PermissionNewActivity.class);
        Bundle bundle = new Bundle();

        if (message != null)
        bundle.putString(MESSAGE_KEY, message);
        bundle.putStringArray(KEY_INPUT_PERMISSIONS, permissions);

        if (resultCode != null){
            bundle.putInt(RESULT_CODE_KEY, resultCode);
        }
        intent.putExtras(bundle);
        if (requestCode == null){
            activity.startActivityForResult(intent, PERMISSION_REQUEST_CODE_OK);
        }else {
            activity.startActivityForResult(intent, requestCode);
        }
    }
    /**
     * 启动这个界面
     * @param fragment fragment
     * @param message 弹窗的提示信息  默认{@link #MESSAGE_DEFAULT}
     * @param requestCode If >= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param resultCode 成功后要求放回的resultCode
     *                   <li> 注意 {@link Activity#onActivityResult(int, int, Intent)}  必须重写父类的onActivityResult 方法</li>
     *                   @see Fragment#onActivityResult(int, int, Intent)
     * @param permissions 需要申请的权限
     */
    public static void startActivityForResult(Fragment fragment, String message, Integer requestCode, Integer resultCode, PermissionNewActivityCallBack mPermissionNewActivityCallBack, String... permissions) {
        if (fragment == null || permissions == null || permissions.length == 0) return;

        ArrayList<String>  listApply = new ArrayList<>();
        int targetSdkVersion = getTargetSdkVersion(fragment.getContext());
        for (String permission : permissions) {
            if (!checkPermission(fragment.getContext(), permission, targetSdkVersion)) {
                listApply.add(permission);
            }
        }
        if (listApply.size() == 0){
            if (mPermissionNewActivityCallBack != null){
                mPermissionNewActivityCallBack.callBackResult(true);
            }
            return;
        }

        Intent intent = new Intent(fragment.getContext(), PermissionNewActivity.class);
        Bundle bundle = new Bundle();

        if (message != null)
            bundle.putString(MESSAGE_KEY, message);
        bundle.putStringArray(KEY_INPUT_PERMISSIONS, permissions);

        if (resultCode != null){
            bundle.putInt(RESULT_CODE_KEY, resultCode);
        }
        intent.putExtras(bundle);

        if (requestCode == null){
            fragment.startActivityForResult(intent, PERMISSION_REQUEST_CODE_OK);
        }else {
            fragment.startActivityForResult(intent, requestCode);
        }
    }


    private String[] all;// 全部

    private String message;//弹窗提示信息

    private int resultCode;//成功返回的code

    private static int targetSdkVersion = -1;

    public static int getTargetSdkVersion(Context mContext){
        int targetSdkVersion = 1;
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return targetSdkVersion;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != savedInstanceState) {
            doStartApplicationWithPackageName(getPackageName());
//            Intent intent = new Intent(this, WelcomeActivity.class);//这个界面去申请权限  权限打开有关闭 或 把已经授权的关闭了  程序就会重启   重启要跳转的界面
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
            return;
        }

        Bundle extras = getIntent().getExtras();
        if (extras == null){//默认同意权限
            setResult(PERMISSION_RESULT_CODE_OK);
            onBackPressed();
            return;
        }
        all = extras.getStringArray(KEY_INPUT_PERMISSIONS);
        message = extras.getString(MESSAGE_KEY , MESSAGE_DEFAULT);
        resultCode = extras.getInt(RESULT_CODE_KEY, PERMISSION_RESULT_CODE_OK);


        if (all == null || all.length == 0 || Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setResult(this.resultCode);
            onBackPressed();
            return;
        }

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<String>  listApply = new ArrayList<>();
        for (String permission : all) {//判断 数据
            if (!checkPermission(this, permission, targetSdkVersion)) {
                listApply.add(permission);
            }
        }

        if (listApply.size() == 0) {
            setResult(this.resultCode);
            onBackPressed();
            return;
        }

        String[] permission = listApply.toArray(new String[]{});
        //进行权限请求
        ActivityCompat.requestPermissions(this, permission, 10);
    }


    /**
     * 判断权限是否获取
     *
     * @param permission 权限名称
     * @return 已授权返回true，未授权返回false
     */
    public static boolean checkPermission(Context mContext, String permission) {
        return checkPermission(mContext, permission, targetSdkVersion);
    }

    /**
     * 判断权限是否获取
     *
     * @param permission 权限名称
     * @return 已授权返回true，未授权返回false
     */
    public static boolean checkPermission(Context mContext, String permission, int targetSdkVersion) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        } else {
            if (targetSdkVersion == -1){
                targetSdkVersion = getTargetSdkVersion(mContext);
            }
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                return ActivityCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
            } else {
                return ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED;
            }
        }
    }


    /**
     * 显示缺失权限提示
     * @param requestCode r
     */
    private void showMissingPermissionDialog(final int requestCode) {
//        注意
        //这个Style 必须写一个 要么 Activity 的 Style  是 Theme.AppCompat
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        final AlertDialog alertDialog = builder.create();
        builder.setMessage(message);
        builder.setCancelable(false);

        // 拒绝, 退出应用
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                onBackPressed();
            }
        });

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                startAppSettings(requestCode);
            }
        });

        builder.show();
    }

    /**
     * 启动应用的设置
     *
     * @param requestCode r
     */
    public void startAppSettings(final int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> listApply = new ArrayList<>();

        for (String permission : all) {
            if (!checkPermission(this, permission, targetSdkVersion)) {
                listApply.add(permission);
            }
        }

        if (listApply.size() == 0) {
            setResult(this.resultCode);
            onBackPressed();
        }else {
            Intent intent = new Intent();

            Bundle bundle = new Bundle();
            bundle.putStringArray(KEY_INPUT_PERMISSIONS, listApply.toArray(new String[]{}));
            intent.putExtras(bundle);

            setResult(PERMISSION_RESULT_CODE_NOT, intent);
            onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> list = new ArrayList<>();

        for (String permission : permissions) {
            if (!checkPermission(this, permission, targetSdkVersion)) {
                //用户不同意，向用户展示该权限作用
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    showMissingPermissionDialog(requestCode);
                    return;
                }else {
                    list.add(permission);
                }
            }
        }
        if (list.size() == 0){
            setResult(this.resultCode);
            onBackPressed();
        }else {
            Intent intent = new Intent();

            Bundle bundle = new Bundle();
            bundle.putStringArray(KEY_INPUT_PERMISSIONS, list.toArray(new String[]{}));
            intent.putExtras(bundle);

            setResult(PERMISSION_RESULT_CODE_NOT, intent);
            onBackPressed();
        }
    }

    public interface PermissionNewActivityCallBack {

        void callBackResult(boolean agreement);

    }


    /*打开app*/
    @TargetApi(Build.VERSION_CODES.DONUT)
    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);

            startActivity(intent);
            finish();
        }
    }
}
