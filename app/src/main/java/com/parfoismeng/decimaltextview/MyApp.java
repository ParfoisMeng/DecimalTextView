package com.parfoismeng.decimaltextview;

import android.app.Application;

/**
 * <pre>
 *     author : parfoismeng
 *     e-mail : youshi520000@163.com
 *     time   : 2017/11/13
 *     desc   : do_
 *     version: 1.0
 * </pre>
 */
public class MyApp extends Application {
    /**
     * Application对象
     */
    private MyApp mInstance;

    public MyApp getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

}