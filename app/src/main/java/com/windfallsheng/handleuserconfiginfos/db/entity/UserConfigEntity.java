package com.windfallsheng.handleuserconfiginfos.db.entity;

import java.io.Serializable;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.NameInDb;

/**
 * @author lzsheng
 * 用户个人的设置数据表
 */
@Entity
public class UserConfigEntity implements Serializable {

    @Id
    private long id;
    /**
     * 设置字段的key
     */
    @Index
    @NameInDb("pkey")
    private String pkey;
    /**
     * 关联主键{@link UserConfigEntity#pkey}的字段
     */
    @NameInDb("ckey")
    private String ckey;
    /**
     * 设置字段的value
     */
    @NameInDb("value")
    private int value;
    /**
     * 最近修改时间
     */
    @NameInDb("modify_time")
    private long modifyTime;
    /**
     * 该数据做修改操作时对应的表版本号
     */
    @NameInDb("version")
    private int version;
    /**
     * 对该字段进行的操作的类型：修改、删除等
     */
    @NameInDb("operation_type")
    private int operationType;

    public UserConfigEntity() {
    }

    public UserConfigEntity(long id, String pkey, String ckey, int value, long modifyTime, int version, int operationType) {
        this.id = id;
        this.pkey = pkey;
        this.ckey = ckey;
        this.value = value;
        this.modifyTime = modifyTime;
        this.version = version;
        this.operationType = operationType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public String getCkey() {
        return ckey;
    }

    public void setCkey(String ckey) {
        this.ckey = ckey;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    @Override
    public String toString() {
        return "UserConfigEntity{" +
                "id=" + id +
                ", pkey='" + pkey + '\'' +
                ", ckey='" + ckey + '\'' +
                ", value=" + value +
                ", modifyTime=" + modifyTime +
                ", version=" + version +
                ", operationType=" + operationType +
                '}';
    }
}
