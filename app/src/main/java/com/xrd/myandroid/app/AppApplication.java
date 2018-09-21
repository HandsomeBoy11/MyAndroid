package com.xrd.myandroid.app;

//import com.jaydenxiao.androidfire.BuildConfig;
import android.app.Application;

import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.common.commonutils.LogUtils;

/**
 * APPLICATION
 */
public class AppApplication extends BaseApplication {
    private static AppApplication appApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appApplicationContext=this;
        //初始化logger
//        LogUtils.logInit(BuildConfig.LOG_DEBUG);
        LogUtils.logInit(true);
    }
    public static AppApplication getAppContext(){
        return appApplicationContext;
    }
}
