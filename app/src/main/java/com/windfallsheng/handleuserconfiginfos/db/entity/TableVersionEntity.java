package com.windfallsheng.handleuserconfiginfos.db.entity;

import java.io.Serializable;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Unique;

/**
 * @author lzsheng
 * 数据表及版本号
 */
@Entity
public class TableVersionEntity implements Serializable {

    @Id
    private long id;
    @Index
    @NameInDb("table_name")
    /**
     *  表名，每个表对应唯一的表名
     */
    @Unique
    private String tableName;
    /**
     * 该表对应的当前版本
     */
    @NameInDb("version_code")
    private int versionCode;

    public TableVersionEntity() {
    }

    public TableVersionEntity(long id, String tableName, int versionCode) {
        this.id = id;
        this.tableName = tableName;
        this.versionCode = versionCode;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    public String toString() {
        return "TableVersionEntity{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", versionCode=" + versionCode +
                '}';
    }
}
