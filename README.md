# android 系统沉浸式相关技能

View.SYSTEM_UI_FLAG_LAYOUT_STABLE：全屏显示时保证尺寸不变,经过测试好像没什么用
View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，状态栏显示在Activity页面上面。状态栏不隐藏
View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：隐藏导航栏
View.SYSTEM_UI_FLAG_FULLSCREEN：Activity全屏显示，且状态栏被隐藏覆盖掉。
View.SYSTEM_UI_FLAG_VISIBLE：Activity非全屏显示，显示状态栏和导航栏。
View.INVISIBLE：Activity伸展全屏显示，隐藏状态栏。
View.SYSTEM_UI_LAYOUT_FLAGS：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY：必须配合View.SYSTEM_UI_FLAG_FULLSCREEN 和View.SYSTEM_UI_FLAG_HIDE_NAVIGATION组合使用，达到的效果是拉出状态栏和导航栏后显示一会儿消失。

下面的代码是实现布局区域能够延伸到刘海屏的刘海区域：
```Java
WindowManager.LayoutParams lp = getWindow().getAttributes();
lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
getWindow().setAttributes(lp);
```

下面代码达到的效果是拉出状态栏和导航栏后显示一会儿消失
```Java
@Override
public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus && Build.VERSION.SDK_INT >= 19) {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | // 稳定布局
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | // 布局能延伸到导航栏，导航栏并不会隐藏
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | // 布局能延伸到导航栏状态栏，状态栏不隐藏
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | // 隐藏导航栏
                        View.SYSTEM_UI_FLAG_FULLSCREEN | // 隐藏状态栏
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); // 拉出状态栏和导航栏后显示一会儿消失
    }
}
```

Android 刘海屏适配全攻略
https://www.jianshu.com/p/561f7241153b/

