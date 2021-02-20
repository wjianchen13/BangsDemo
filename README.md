# android 系统沉浸式相关技能

View.SYSTEM_UI_FLAG_LAYOUT_STABLE：全屏显示时保证尺寸不变,经过测试好像没什么用
View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN：Activity全屏显示，状态栏显示在Activity页面上面。状态栏不隐藏
View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION：效果同View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
View.SYSTEM_UI_FLAG_HIDE_NAVIGATION：隐藏导航栏
View.SYSTEM_UI_FLAG_FULLSCREEN：Activity全屏显示，且状态栏被隐藏覆盖掉。
View.SYSTEM_UI_FLAG_VISIBLE：Activity非全屏显示，显示状态栏和导航栏。
View.INVISIBLE：Activity伸展全屏显示，隐藏状态栏。 测试这个标志的效果和SYSTEM_UI_FLAG_FULLSCREEN效果一样，部分手机如果非全屏显示，状态栏区域还是会有黑边
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

# 谷歌刘海屏适配方案
https://developer.android.com/guide/topics/display-cutout?hl=zh-cn  
注意 layoutInDisplayCutoutMode的下面三个参数  
```
LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT - 这是默认行为，如上所述。在竖屏模式下，内容会呈现到刘海区域中；但在横屏模式下，内容会显示黑边。
LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES - 在竖屏模式和横屏模式下，内容都会呈现到刘海区域中。
LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER - 内容从不呈现到刘海区域中。
```

Android 刘海屏适配全攻略
https://www.jianshu.com/p/561f7241153b/

# 问题记录  
1.三星 Galaxy S20 5G 进入直播间顶部有黑边  
是由于进入Activity是没有设置LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES导致的，设置这个参数可以解决问题   
Galaxy S20 5G 设置全屏显示：设置->显示->全荧幕应用程式->点击需要设置的应用->选择全荧幕

2.Vivo X21进入直播间有黑边  
这个问题暂时没有发现兼容方案，客服的回复是需要我们自己做好水滴屏的兼容之后，然后告诉他们，让他们在后台设置全屏显示  
目前这个项目里面是没有处理的，包括Welcom启动页和直播间都有黑边，在设置--显示与亮度--第三方应用显示比例设置全屏显示  
可以实现全屏显示的功能。  




