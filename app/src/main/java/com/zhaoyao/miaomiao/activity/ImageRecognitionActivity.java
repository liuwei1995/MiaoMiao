package com.zhaoyao.miaomiao.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.zhaoyao.miaomiao.R;
import com.zhaoyao.miaomiao.util.commonly.LogUtils;
import com.zhaoyao.miaomiao.util.permission.PermissionNewActivity;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import static com.zhaoyao.miaomiao.R.id.iv;

public class ImageRecognitionActivity extends AppCompatActivity implements View.OnClickListener, PermissionNewActivity.PermissionNewActivityCallBack {

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
                PermissionNewActivity.startActivityForResult(this, null, 100, 100, this, Manifest.permission.CAMERA);
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
                if (extras == null) return;
                for (String s : extras.keySet()) {
                    Object o = extras.get(s);
                    LogUtils.d(o + "==" + s);
                }
                Bitmap bb = extras.getParcelable("data");
                mIv.setImageBitmap(bb);
//
            }
        }
        if (requestCode == 100) {
            if (resultCode == 100) {
                start();
            } else
                finish();
        }
    }

    private void startAlipayActivity(String url) {
//        String urlCode = "FKX085560HFWCPRCVOPHE3";
//        String urlCode = "HTTPS://QR.ALIPAY.COM/FKX04537VQKNGHMX6FVA9D";
////        String urlCode = "HTTPS://QR.ALIPAY.COM/FKX075476OWJIOKFOLHUBD";
////        String urlCode = "HTTPS://QR.ALIPAY.COM/FKX085560HFWCPRCVOPHE3";
//        startAlipayActivity("intent://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode="+urlCode+"&_t=1472443966571#Intent;scheme=alipayqr;package=com.eg.android.AlipayGphone;
// end");

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

    @Override
    public void callBackResult(boolean agreement) {
        if (agreement) {
            start();
        } else
            finish();
    }
}
