package com.sohuvideo.ui_plugin.ui;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.sohuvideo.ui_plugin.R;
import com.sohuvideo.ui_plugin.fragment.SohuStreamFragment;

public class SohuStreamActivity extends FragmentActivity {
    private FragmentManager fragmentManager;
    private SohuStreamFragment mStreamFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sohu_stream);
        initData();
    }

    private void initData() {
        mStreamFragment = new SohuStreamFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.id_sohustream_rl, mStreamFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        reset();
    }

    private void reset() {
        mStreamFragment = null;
        fragmentManager = null;
    }
}
