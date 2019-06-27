package com.windfallsheng.handleuserconfiginfos.db;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.windfallsheng.handleuserconfiginfos.Constants;
import com.windfallsheng.handleuserconfiginfos.Util.ResUtils;
import com.windfallsheng.handleuserconfiginfos.db.entity.TableVersionEntity;
import com.windfallsheng.handleuserconfiginfos.db.entity.UserConfigEntity;
import com.windfallsheng.handleuserconfiginfos.db.manager.TableVersionManager;
import com.windfallsheng.handleuserconfiginfos.db.manager.UserConfigManager;

import java.util.List;
import java.util.Map;

/**
 * @author lzsheng
 * 设置表的相关业务操作包括：
 * 初始化用户设置的原始数据；更新数据库中对应的表的版本号等
 */
public class UserConfigService {
    private static final String TAG = UserConfigService.class.getSimpleName();
    private Context mContext;

    private static volatile UserConfigService instance = null;

    private UserConfigService(Context context) {
        this.mContext = context;
    }

    public static UserConfigService getInstance(Context context) {
        if (instance == null) {
            synchronized (UserConfigService.class) {
                if (instance == null) {
                    instance = new UserConfigService(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    /**
     * 初始化用户设置的原始数据，业务操作与设置表的版本号相关
     */
    public void initUserConfig() {
        Log.d(TAG, "method:initUserConfig#in");
        printData();
        // 获取配置文件中最新的版本信息
        Map<String, Integer> newTableVersionMap = parseNewTableVersionMap();
        // 获取json配置文件中最新的版本号
        int newTableVersionCode = newTableVersionMap.get(Constants.TABLE_NAME_USER_CONFIG);
        // 获取数据库中上一个版本信息
        TableVersionEntity tableVersionEntity = TableVersionManager.getInstance().queryTableInfosByTableName(Constants.TABLE_NAME_USER_CONFIG);
        Log.d(TAG, "method:initUserConfig#tableVersionEntity=" + tableVersionEntity + ", newVersionCode=" + newTableVersionCode + ", newTableVersionMap=" + newTableVersionMap);
        // 当数据库中没有该表的版本号，说明还没有插入过任何数据，则可以直接向数据库插入所有数据
        if (tableVersionEntity == null) {
            List<UserConfigEntity> newUserConfigList = parseNewUserConfigList();
            // 剔除不需要新增或者修改的对象
            int size = newUserConfigList.size();
            for (int i = 0; i < size; i++) {
                UserConfigEntity userConfigEntity = newUserConfigList.get(i);
                if (userConfigEntity.getOperationType() != Constants.USER_CONFIG_FIELD_OPERATION_TYPE_INSERT_OR_UPDATE) {
                    newUserConfigList.remove(i);
                    i--;
                    size--;
                }
            }
            UserConfigManager.getInstance().insertUserConfigList(newUserConfigList);
            // 更新数据库中的版本信息为最新版本
//            if (...) { // 这里可以做一些验证，保证数据库的操作都正确之后再更新这个表的字段
            updateTableVersion(0, Constants.TABLE_NAME_USER_CONFIG, newTableVersionCode);
//            }
            printData();
            return;
        }
        // 当数据库中有该表的版本号时，则需要判断版本号的大小，是否需要处理新数据
        int lastTableVersionCode = tableVersionEntity.getVersionCode();
        Log.d(TAG, "method:initUserConfig#lastTableVersionCode=" + lastTableVersionCode + ", newTableVersionCode=" + newTableVersionCode);
        // 当前数据库中的数据版本和最新数据的版本号相同，不需要处理新数据
        if (newTableVersionCode == lastTableVersionCode) {
            printData();
            Log.d(TAG, "method:initUserConfig#return#newTableVersionCode == lastTableVersionCode");
            return;
        }
        List<UserConfigEntity> newUserConfigList = parseNewUserConfigList();
        // 升级操作：当前数据库中的数据版本小于最新数据的版本，需要处理新数据
        if (newTableVersionCode > lastTableVersionCode) {
            for (int i = 0; i < newUserConfigList.size(); i++) {
                UserConfigEntity newUserConfig = newUserConfigList.get(i);
                // 该数据的版本号
                int newFieldVersionCode = newUserConfig.getVersion();
                // 该数据的版本号大于上一个版本时，根据操作类型修改，或者删除
                if (newFieldVersionCode > lastTableVersionCode) {
                    //根据数据的操作类型调用相应的修改和删除操作等
                    handleUserConfigByOperationType(newUserConfig);
                }
            }
            // 更新数据库中的版本信息为最新版本
//            if (...) { // 这里可以做一些验证，保证数据库的操作都正确之后再更新这个表的字段
            updateTableVersion(tableVersionEntity.getId(), Constants.TABLE_NAME_USER_CONFIG, newTableVersionCode);
//            }
            printData();
            Log.d(TAG, "method:initUserConfig#return#newTableVersionCode > lastTableVersionCode");
            return;
        }
        // 降级操作：当前数据库中的数据版本大于最新数据的版本，需要处理新数据
        if (newTableVersionCode < lastTableVersionCode) {
            for (int i = 0; i < newUserConfigList.size(); i++) {
                UserConfigEntity newUserConfig = newUserConfigList.get(i);
                // 该数据的版本号
                int newFieldVersionCode = newUserConfig.getVersion();
                // 该数据的版本号小于上一个版本时，根据操作类型修改，或者删除
                if (newFieldVersionCode < lastTableVersionCode) {
                    //根据数据的操作类型调用相应的修改和删除操作等
                    handleUserConfigByOperationType(newUserConfig);
                }
            }
            // 更新数据库中的版本信息为最新版本
//            if (...) { // 这里可以做一些验证，保证数据库的操作都正确之后再更新这个表的字段
            updateTableVersion(tableVersionEntity.getId(), Constants.TABLE_NAME_USER_CONFIG, newTableVersionCode);
//            }
        }
        printData();
        Log.d(TAG, "method:initUserConfig#newTableVersionCode < lastTableVersionCode");
        Log.d(TAG, "method:initUserConfig#out");
    }

    private void printData() {
        Log.d(TAG, "method:printData#in");
        List<TableVersionEntity> tableVersionList = TableVersionManager.getInstance().queryAllTableVersion();
        Log.d(TAG, "method:printData#tableVersionList=" + tableVersionList);
        long count = UserConfigManager.getInstance().queryCountUserConfig();
        Log.d(TAG, "method:printData#count=" + count);
        List<UserConfigEntity> userConfigList = UserConfigManager.getInstance().queryAllUserConfig();
        Log.d(TAG, "method:printData#userConfigList=" + userConfigList);
        Log.d(TAG, "method:printData#out");
    }


    /**
     * 根据字段的操作类型调用相应的修改、删除等操作
     *
     * @param userConfigEntity
     */
    private long handleUserConfigByOperationType(UserConfigEntity userConfigEntity) {
        Log.d(TAG, "method:handleUserConfiByType#in#arguments:newUserConfig=" + userConfigEntity);
        // 该字段的操作类型
        int operationType = userConfigEntity.getOperationType();
        if (operationType == Constants.USER_CONFIG_FIELD_OPERATION_TYPE_INSERT_OR_UPDATE) {
            long rowId = UserConfigManager.getInstance().insertOrReplaceUserConfig(userConfigEntity);
            Log.d(TAG, "method:handleUserConfiByType#rowId=" + rowId);
            return rowId;
        }
        if (operationType == Constants.USER_CONFIG_FIELD_OPERATION_TYPE_DELETE) {
            String pkey = userConfigEntity.getPkey();
            long removeRowId = UserConfigManager.getInstance().deleteUserConfigByKey(pkey);
            Log.d(TAG, "method:handleUserConfiByType#removeRowId=" + removeRowId);
            return removeRowId;
        }
        Log.d(TAG, "method:handleUserConfiByType#out#return value:0");
        return 0;
    }

    /**
     * 修改本地数据库中表对应的版本号
     *
     * @return
     */
    private void updateTableVersion(long id, String tableName, int versionCode) {
        Log.d(TAG, "method:updateTableVersion#in#arguments:tableName=" + tableName + ", versionCode=" + versionCode);
        TableVersionEntity tableVersion = new TableVersionEntity(id, tableName, versionCode);
        TableVersionManager.getInstance().insertOrReplaceTableVersion(tableVersion);
        Log.d(TAG, "method:updateTableVersion#out");
    }

    /**
     * 解析出json文件中的所有最新的配置相关的数据
     *
     * @return
     */
    public List<UserConfigEntity> parseNewUserConfigList() {
        Log.d(TAG, "method:parseNewUserConfigList#in");
        List<UserConfigEntity> userConfigList = null;
        Map<String, Integer> version = null;
        try {
            String jsonStr = ResUtils.getJsonStr(mContext, "user_config.json");
            Log.d(TAG, "method:parseNewUserConfigList#jsonStr=" + jsonStr);
            Gson gson = new Gson();
            userConfigList = gson.fromJson(jsonStr, new TypeToken<List<UserConfigEntity>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "method:parseNewUserConfigList#out#return value:userConfigList=" + userConfigList);
        return userConfigList;
    }

    /**
     * 解析出json文件中的所有最新的表相关的数据
     *
     * @return
     */
    private Map<String, Integer> parseNewTableVersionMap() {
        Log.d(TAG, "method:parseNewTableVersionMap#in");
        Map<String, Integer> newTableVersionMap = null;
        try {
            String jsonStr = ResUtils.getJsonStr(mContext, "table_version.json");
            Log.d(TAG, "method:parseNewTableVersionMap#jsonStr=" + jsonStr);
            Gson gson = new Gson();
            newTableVersionMap = gson.fromJson(jsonStr, new TypeToken<Map<String, Integer>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "method:parseNewTableVersionMap#out#return value:newTableVersionMap=" + newTableVersionMap);
        return newTableVersionMap;
    }
}
