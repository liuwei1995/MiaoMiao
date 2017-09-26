package com.zhaoyao.miaomiao.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.util.LogUtils;
import com.zhaoyao.miaomiao.util.permission.AndPermission;
import com.zhaoyao.miaomiao.util.permission.PermissionListener;
import com.zhaoyao.miaomiao.util.permission.RationaleListener;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static com.zhaoyao.miaomiao.R.id.iv;

public class ImageRecognitionActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 拍照
     */
    private TextView mTvOpen;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_recognition);
        initView();
    }

    //设置APPID/AK/SK
    public static final String APP_ID = "10182948";
    public static final String API_KEY = "0r8qQ62vGjViwaf2db9U27m3";
    public static final String SECRET_KEY = "YGvnZ0LbGHX5nthQLS0P4AVr90wpn2Ts";


    /**
     * @param path test.jpg
     */
    public static void main(String path) {
        // 初始化一个AipImageClassifyClient
        AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 调用接口
//        String path = "test.jpg";
        JSONObject res = client.objectDetect(path, new HashMap<String, String>());

    }

    private void initView() {
        mTvOpen = (TextView) findViewById(R.id.tv_open);
        mTvOpen.setOnClickListener(this);
        mIv = (ImageView) findViewById(iv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_open:
                AndPermission.with(this).setPermission(Manifest.permission.CAMERA).setCallback(new PermissionListener() {
                    @Override
                    protected void onSucceed(Context context, int requestCode, @NonNull List<String> grantPermissions) {
                        start();
                    }

                    @Override
                    protected void onFailed(@NonNull Context context, int requestCode, List<String> deniedPermissionsList, List<String> deniedDontRemindList, RationaleListener rationale) {
                        rationale.showSettingDialog(context,rationale,deniedDontRemindList == null || deniedDontRemindList.size() == 0 ? deniedPermissionsList : deniedDontRemindList);
                    }

                    @Override
                    protected void onCancel(Context context, int requestCode, List<String> deniedPermissionsList, List<String> deniedDontRemindList) {
                       finish();
                    }

                    @Override
                    protected void settingDialogCallBack(Context context, boolean isAgreement, String[] deniedDontRemindList) {
                       if (isAgreement){
                           start();
                       }else
                           finish();
                    }
                }).start();
                break;
        }
    }

    private void start() {
        File fos = null;
        try {
            fos = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "临时.jpg");
            Uri u = Uri.parse(fos.toString());
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            i.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
//            i.putExtra(MediaStore.EXTRA_OUTPUT, u);

            //为拍摄的图片指定一个存储的路径
            i.putExtra(MediaStore.EXTRA_OUTPUT, u);

            this.startActivityForResult(i, 9);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 9) {
                Bundle extras = data.getExtras();
                if (extras == null)return;
                for (String s : extras.keySet()) {
                    Object o = extras.get(s);
                    LogUtils.d(o+"=="+s);
                }
                Bitmap bb = extras.getParcelable("data");
                mIv.setImageBitmap(bb);
//                File bb = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "临时.jpg");
//                Uri uri = data.getData();
//                File fos = null;
//                try {
//                    fos = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "临时.jpg");
//                    Intent intent = new Intent("com.android.camera.action.CROP");
//                    intent.setDataAndType(uri, "image/*");
//                    intent.putExtra("crop", "true");//可裁剪
//                    intent.putExtra("aspectX", 1);
//                    intent.putExtra("aspectY", 1);
//                    intent.putExtra("outputX", 500);
//                    intent.putExtra("outputY", 500);
//                    intent.putExtra("scale", true);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(fos.toString()));//输出到指定的uri中
//                    intent.putExtra("return-data", false);//若为false则表示不返回数据
//                    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//                    intent.putExtra("noFaceDetection", true);
//                    startActivityForResult(intent, 7);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
            }
//
//            if (requestCode == 7) {
//                Bitmap bb = data.getParcelableExtra("data");
//                mIv.setImageBitmap(bb);
//            }
        }
    }

    private void startAlipayActivity(String url) {
//        String urlCode = "FKX085560HFWCPRCVOPHE3";
//        String urlCode = "HTTPS://QR.ALIPAY.COM/FKX04537VQKNGHMX6FVA9D";
////        String urlCode = "HTTPS://QR.ALIPAY.COM/FKX075476OWJIOKFOLHUBD";
////        String urlCode = "HTTPS://QR.ALIPAY.COM/FKX085560HFWCPRCVOPHE3";
//        startAlipayActivity("intent://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode="+urlCode+"&_t=1472443966571#Intent;scheme=alipayqr;package=com.eg.android.AlipayGphone;end");

        LogUtils.d("alipay", "startUp");
        Intent intent;
        try {
            intent = Intent.parseUri(url,
                    Intent.URI_INTENT_SCHEME);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setComponent(null);
            startActivity(intent);
            LogUtils.d("alipay", "start intent = " + intent.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.d("alipay", "error " + e.getMessage());
        }
    }

}
