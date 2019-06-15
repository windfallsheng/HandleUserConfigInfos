package com.windfallsheng.handleuserconfiginfos.db;

import android.content.Context;
import android.util.Log;
import com.windfallsheng.handleuserconfiginfos.BuildConfig;
import com.windfallsheng.handleuserconfiginfos.db.entity.MyObjectBox;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

public class ObjectBox {

    private static final String TAG = ObjectBox.class.getSimpleName();
    private static BoxStore boxStore;

    public static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();

        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(context.getApplicationContext());
            Log.d(TAG, String.format("Using ObjectBox %s (%s)",
                    BoxStore.getVersion(), BoxStore.getVersionNative()));
        }
    }

    public static BoxStore getBoxStore() {
        return boxStore;
    }
}
