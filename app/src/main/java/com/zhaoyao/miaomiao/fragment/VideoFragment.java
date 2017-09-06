package com.zhaoyao.miaomiao.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaoyao.miaomiao.R;


/**
 * http://connect.qq.com/sdk/webtools/index.html分享的接口文档
 * @author dell
 *
 */
@SuppressLint({"Recycle", "ValidFragment"})
public class VideoFragment extends BaseNewFragment {

	public static VideoFragment newInstance() {

		Bundle args = new Bundle();

		VideoFragment fragment = new VideoFragment();
		fragment.setArguments(args);
		return fragment;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.video_activity_main, null);
		return inflate;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	public void doClick(View view) {
		switch (view.getId()) {
		default:
			break;
		}
	}
	@Override
	public void onStart() {
		super.onStart();
	}
	@Override
	public void onPause() {
		super.onPause();
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

    @Override
    public void onDestroyView() {
    	super.onDestroyView();
    }
    @Override
    public void onDestroy() {
    	super.onDestroy();
    }
}
