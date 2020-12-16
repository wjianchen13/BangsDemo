package com.example.bangsdemo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        System.out.println("============> bang1: " + NotchScreenUtils.isAndroidP(this));
        getNotchParams();
        fullScreen(true);
//
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//        getWindow().setAttributes(lp);
//
////         全屏展示
////        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
//
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void onTest(View v) {
        int a =0;
        Toast.makeText(this, "" + a, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        System.out.println("============> bang3: " + NotchScreenUtils.isAndroidP(this));
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | // 稳定布局
//                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | // 布局能延伸到导航栏，导航栏并不会隐藏
//                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | // 布局能延伸到导航栏状态栏，状态栏不隐藏
//                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | // 隐藏导航栏
//                            View.SYSTEM_UI_FLAG_FULLSCREEN | // 隐藏状态栏
//                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); // 拉出状态栏和导航栏后显示一会儿消失
//        }
//    }

    public void fullScreen(boolean isFull) {//控制是否全屏显示
        if (isFull) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                getWindow().setAttributes(lp);
                // 设置页面全屏显示
                final View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            showNavigationBar();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void showNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    @TargetApi(28)
    public void getNotchParams() {
        final View decorView = getWindow().getDecorView();

        decorView.post(new Runnable() {
            @Override
            public void run() {
                System.out.println("============> bang2: " + NotchScreenUtils.hasNotchScreen(MainActivity.this));
                if (decorView != null && android.os.Build.VERSION.SDK_INT < 28){
                    return ;
                }
                WindowInsets rootWindowInsets = decorView.getRootWindowInsets();
                if (rootWindowInsets == null) {
                    Log.e("TAG", "rootWindowInsets为空了");
                    return;
                }
                DisplayCutout displayCutout = rootWindowInsets.getDisplayCutout();
                if(displayCutout != null) {
                    Log.e("TAG", "安全区域距离屏幕左边的距离 SafeInsetLeft:" + displayCutout.getSafeInsetLeft());
                    Log.e("TAG", "安全区域距离屏幕右部的距离 SafeInsetRight:" + displayCutout.getSafeInsetRight());
                    Log.e("TAG", "安全区域距离屏幕顶部的距离 SafeInsetTop:" + displayCutout.getSafeInsetTop());
                    Log.e("TAG", "安全区域距离屏幕底部的距离 SafeInsetBottom:" + displayCutout.getSafeInsetBottom());

                    List<Rect> rects = displayCutout.getBoundingRects();
                    if (rects == null || rects.size() == 0) {
                        Log.e("TAG", "不是刘海屏");
                    } else {
                        Log.e("TAG", "刘海屏数量:" + rects.size());
                        for (Rect rect : rects) {
                            Log.e("TAG", "刘海屏区域：" + rect);
                        }
                    }
                }
            }
        });
    }

}