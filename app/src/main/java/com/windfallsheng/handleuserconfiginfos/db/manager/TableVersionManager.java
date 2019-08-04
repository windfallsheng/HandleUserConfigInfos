package com.windfallsheng.handleuserconfiginfos.db.manager;

import android.text.TextUtils;
import android.util.Log;

import com.windfallsheng.handleuserconfiginfos.db.ObjectBox;
import com.windfallsheng.handleuserconfiginfos.db.entity.TableVersionEntity;
import com.windfallsheng.handleuserconfiginfos.db.entity.TableVersionEntity_;

import java.util.List;

import io.objectbox.Box;

/**
 * @author lzsheng
 * <p>
 * {@link TableVersionEntity}的CRUD操作
 */
public class TableVersionManager {

    private static final String TAG = TableVersionManager.class.getSimpleName();
    private static volatile TableVersionManager instance = null;
    private static Box<TableVersionEntity> mUserConfigEntityBox;


    private TableVersionManager() {

    }

    public static TableVersionManager getInstance() {
        if (instance == null) {
            synchronized (TableVersionManager.class) {
                if (instance == null) {
                    instance = new TableVersionManager();
                    mUserConfigEntityBox = ObjectBox.getBoxStore().boxFor(TableVersionEntity.class);
                }
            }
        }
        return instance;
    }

    public void insertOrReplaceTableVersion(TableVersionEntity tableVersion) {
        Log.d(TAG, "insertOrReplaceTableVersion#tableVersion=" + tableVersion);
        if (tableVersion == null) {
            throw new IllegalArgumentException("tableVersion = null");
        }
        long putId = mUserConfigEntityBox
                .put(tableVersion);
    }

    /**
     * 查询数据
     *
     * @return
     */
    public TableVersionEntity queryTableInfosByTableName(String tableName) {
        Log.d(TAG, "method:queryTableInfosByTableName#argument:tableName=" + tableName);
        if (TextUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException("tableName is empty");
        }
        TableVersionEntity tableVersionEntity = mUserConfigEntityBox
                .query()
                .equal(TableVersionEntity_.tableName, tableName)
                .build()
                .findUnique();
        return tableVersionEntity;
    }

    public List<TableVersionEntity> queryAllTableVersion() {
        Log.d(TAG, "queryAllTableVersion");
        List<TableVersionEntity> tableVersionList = mUserConfigEntityBox
                .query()
                .build()
                .find();
        return tableVersionList;
    }

    public void deleteTableVersionByTableName(String tableName) {
        Log.d(TAG, "deleteTableVersionByTableName#tableName=" + tableName);
        if (TextUtils.isEmpty(tableName)) {
            throw new IllegalArgumentException("tableName is empty");
        }
        long removeId = mUserConfigEntityBox
                .query()
                .equal(TableVersionEntity_.tableName, tableName)
                .build()
                .remove();
    }

}
