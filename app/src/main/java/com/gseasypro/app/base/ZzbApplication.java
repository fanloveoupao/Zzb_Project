package com.gseasypro.app.base;

import android.app.Application;

import com.gseasypro.app.picasso.ImageLoader;

/**
 * Created by fan-gk on 2017/2/3.
 */

public class ZzbApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoader.init(this);
    }
}
