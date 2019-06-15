package com.windfallsheng.handleuserconfiginfos.db.manager;

import android.text.TextUtils;
import android.util.Log;

import com.windfallsheng.handleuserconfiginfos.db.ObjectBox;
import com.windfallsheng.handleuserconfiginfos.db.entity.UserConfigEntity;
import com.windfallsheng.handleuserconfiginfos.db.entity.UserConfigEntity_;
import java.util.List;
import io.objectbox.Box;

/**
 * @author lzsheng
 *
 * {@link UserConfigEntity}的CRUD操作
 */
public class UserConfigManager {

    private static final String TAG = UserConfigManager.class.getSimpleName();
    private static volatile UserConfigManager instance = null;
    private static Box<UserConfigEntity> mUserConfigEntityBox;

    private UserConfigManager() {

    }

    public static UserConfigManager getInstance() {
        if (instance == null) {
            synchronized (UserConfigManager.class) {
                if (instance == null) {
                    instance = new UserConfigManager();
                    mUserConfigEntityBox = ObjectBox.getBoxStore().boxFor(UserConfigEntity.class);
                }
            }
        }
        return instance;
    }

    public void insertUserConfigList(List<UserConfigEntity> userConfigList) {
        Log.d(TAG, "method:insertUserConfigList#userConfigList=" + userConfigList.toString());
        if (userConfigList == null) {
            throw new IllegalArgumentException("userConfigList = null");
        }
        mUserConfigEntityBox.put(userConfigList);
    }

    public long insertOrReplaceUserConfig(UserConfigEntity userConfig) {
        Log.d(TAG, "method:insertOrReplaceUserConfig#userConfig=" + userConfig);
        if (userConfig == null) {
            throw new IllegalArgumentException("userConfig = null");
        }
        UserConfigEntity userConfigEntity = queryUserConfigByKey(userConfig.getPkey());
        if (userConfigEntity != null){
            long id = userConfigEntity.getId();
            Log.d(TAG, "method:insertOrReplaceUserConfig#userConfigEntity#id=" + id);
            userConfig.setId(id);
        }
        long putId = mUserConfigEntityBox.put(userConfig);
        Log.d(TAG, "method:insertOrReplaceUserConfig#putId=" + putId);
        return putId;
    }

    public long updateUserConfig(UserConfigEntity userConfig) {
        Log.d(TAG, "method:updateUserConfig#userConfig=" + userConfig);
        if (userConfig == null) {
            throw new IllegalArgumentException("userConfig = null");
        }
        long putId = mUserConfigEntityBox.put(userConfig);
        Log.d(TAG, "method:updateUserConfig#putId=" + putId);
        return putId;
    }

    /**
     * 查询数据
     *
     * @return
     */
    public UserConfigEntity queryUserConfigByKey(String pkey) {
        Log.d(TAG, "queryUserConfigByKey#pkey=" + pkey);
        if (TextUtils.isEmpty(pkey)) {
            return null;
        }
        UserConfigEntity userConfigEntity = mUserConfigEntityBox
                .query()
                .equal(UserConfigEntity_.pkey, pkey)
                .build()
                .findUnique();
        return userConfigEntity;
    }

    /**
     * 查询所有配置数据
     *
     * @return
     */
    public List<UserConfigEntity> queryAllUserConfig() {
        Log.d(TAG, "queryAllUserConfig");
        List<UserConfigEntity> userConfigList = mUserConfigEntityBox
                .query()
                .build()
                .find();
        return userConfigList;
    }

    public long queryCountUserConfig() {
        long count = mUserConfigEntityBox
                .query()
                .build()
                .count();
        return count;
    }

    public long deleteUserConfigByKey(String pkey) {
        Log.d(TAG, "method:deleteUserConfigByKey#pkey=" + pkey);
        if (TextUtils.isEmpty(pkey)) {
            throw new IllegalArgumentException("userConfig = null");
        }
        long removeRowId = mUserConfigEntityBox
                .query()
                .equal(UserConfigEntity_.pkey, pkey)
                .build()
                .remove();
        Log.d(TAG, "method:deleteUserConfigByKey#removeRowId=" + removeRowId);
        return removeRowId;
    }


}
