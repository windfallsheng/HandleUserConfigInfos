package com.windfallsheng.handleuserconfiginfos;

import android.app.Application;

import com.windfallsheng.handleuserconfiginfos.db.ObjectBox;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
    }

}
