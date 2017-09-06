package com.zhaoyao.miaomiao.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Method;


/**
 * Created by liuwei on 2017/3/17
 */

public abstract class BaseNewActivity extends AppCompatActivity implements View.OnClickListener,Toolbar.OnMenuItemClickListener{


    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public <T extends View> T $(int viewID) {
        return (T) findViewById(viewID);
    }
    public <T extends View> T $(View view,int viewID) {
        return (T) view.findViewById(viewID);
    }
    public void setOnClickListener(View ...views){
        if(views != null)
            for (int i = 0; i < views.length; i++) {
                views[i].setOnClickListener(this);
            }
    }
//    public void setNewToolbar(@DrawableRes Integer rightResId,String title) {
//        setNewToolbar(null,rightResId,title);
//    }
//    public void setNewToolbar(@DrawableRes Integer leftResId ,@DrawableRes Integer rightResId ,String title) {
//        setTitle("");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        tvTitle = (TextView) findViewById(R.id.title);
//        if(tvTitle != null && !TextUtils.isEmpty(title))
//            tvTitle.setText(title);
////        if (leftResId != null){
////            ImageButton ib_left = (ImageButton) findViewById(R.id.ib_left);
////            if (ib_left != null){
////                ib_left.setOnClickListener(this);
////                ib_left.setVisibility(View.VISIBLE);
////                if (leftResId != null)
////                ib_left.setImageResource(leftResId);
////            }
////        }
////        if (rightResId != null){
////            ImageButton ib_right = (ImageButton) findViewById(R.id.ib_right);
////            if (ib_right != null){
////                ib_right.setOnClickListener(this);
////                ib_right.setVisibility(View.VISIBLE);
////                ib_right.setImageResource(rightResId);
////            }
////        }
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(this);// v.getId = -1
//        toolbar.setOnMenuItemClickListener(this);
//    }
    public void setOnMenuItemClickListener(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            View customView = actionBar.getCustomView();
            if (customView != null && customView instanceof Toolbar)
                ((Toolbar)customView).setOnMenuItemClickListener(this);
        }
    }
//    public void setToolbar(@DrawableRes Integer resId ,String title) {
//        setTitle("");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        tvTitle = (TextView) findViewById(R.id.title);
//        setSupportActionBar(toolbar);
//        if(resId == null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }else {
//            toolbar.setNavigationIcon(resId);
//        }
//        toolbar.setNavigationOnClickListener(this);// v.getId = -1
//        if(tvTitle!=null)
//        tvTitle.setText(title);
//        toolbar.setOnMenuItemClickListener(this);
//    }
//    public void setToolbar(@DrawableRes Integer resId ,@StringRes int id) {
//        setTitle("");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        tvTitle = (TextView) findViewById(R.id.title);
//        setSupportActionBar(toolbar);
//        if(resId == null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }else {
//            toolbar.setNavigationIcon(resId);
//        }
//        toolbar.setNavigationOnClickListener(this);// v.getId = -1
//        if(tvTitle!=null)
//            tvTitle.setText(id);
//        toolbar.setOnMenuItemClickListener(this);
//    }

//    public void setToolbar(@DrawableRes Integer resId ) {
//        setToolbar(resId,null);
//    }
//    public void setToolbar() {
//        setToolbar(null,null);
//    }
//
//    public void setToolbarTitle (String title){
//        if(tvTitle != null){
//            tvTitle.setText(title);
//        }
//    }
//    public void setToolbarTitle (@StringRes int id){
//        if(tvTitle!=null)
//            tvTitle.setText(id);
//    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

        super.onCreate(savedInstanceState, persistentState);
    }

    //这里是在登录界面label上右上角添加三个点，里面可添加其他功能
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
      if(menuRes > 0){
          getMenuInflater().inflate(menuRes, menu);//这里是调用menu文件夹中的main.xml，在登陆界面label右上角的三角里显示其他功能
      }
        return true;
    }

    /**
     * 利用反射来显示溢出菜单的icon
     * @param view
     * @param menu
     * @return
     */
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName() , "_____________onMenuOpened...unable to set icons for overflow menu" + e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    private @MenuRes int menuRes = -1;

    public void setMenu(@MenuRes int menuRes){
        this.menuRes = menuRes;
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onClick(View v) {

    }

    /**
     *
     * @param manager
     * @param containerViewId  R.id.fragment_container
     * @param fragment
     * @param stackName
     * @return
     */
    protected int addFragment(@NonNull FragmentManager manager, @IdRes int containerViewId, Fragment fragment, String stackName) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(containerViewId, fragment);
        transaction.addToBackStack(stackName);
        return transaction.commit();
    }

    /**
     * 返回上一个Fragment
     * @param manager
     */
    protected void popBackStack(@NonNull FragmentManager manager) {
        manager.popBackStack();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
