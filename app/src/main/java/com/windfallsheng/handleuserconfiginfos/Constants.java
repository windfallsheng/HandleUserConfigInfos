package com.windfallsheng.handleuserconfiginfos;

import com.windfallsheng.handleuserconfiginfos.db.entity.TableVersionEntity;
import com.windfallsheng.handleuserconfiginfos.db.entity.UserConfigEntity;

public class Constants {

    /**
     * 用户配置表名，其值必须与 "table_version.json" 中配置的key一致，也与{@link TableVersionEntity#tableName}保持一致
     */
    public static final String TABLE_NAME_USER_CONFIG = "user_config_entity";
    /**
     * 用户配置表数据修改操作，其值必须与 "user_config.json" 中配置的operationType一致，也与{@link UserConfigEntity#operationType}保持一致
     */
    public static final int USER_CONFIG_FIELD_OPERATION_TYPE_INSERT_OR_UPDATE = 1;
    /**
     * 用户配置表数据删除操作，其值必须与 "user_config.json" 中配置的operationType一致，也与{@link UserConfigEntity#operationType}保持一致
     */
    public static final int USER_CONFIG_FIELD_OPERATION_TYPE_DELETE = 2;
    /**
     * 用户配置表数据修改操作，其值必须与 "user_config.json" 中配置的operationType一致，也与{@link UserConfigEntity#operationType}保持一致
     */
    public static final int USER_CONFIG_FIELD_OPERATION_TYPE_UPDATE = 3;
    /**
     * 用户配置表开关状态：1为打开，2为关闭
     */
    public static final int SWITCH_ON = 1;
    /**
     * 用户配置表开关状态：1为打开，2为关闭
     */
    public static final int SWITCH_OFF = 2;
    /**
     * 配置相关，其值必须与 "user_config.json" 中配置的pkey一致，也与{@link UserConfigEntity#pkey}保持一致
     */
    public static final String CONFIG_KEY_NOTIFY = "notify";
    /**
     * 配置相关，其值必须与 "user_config.json" 中配置的pkey一致，也与{@link UserConfigEntity#pkey}保持一致
     */
    public static final String CONFIG_KEY_VOICE = "voice";
    /**
     * 配置相关，其值必须与 "user_config.json" 中配置的pkey一致，也与{@link UserConfigEntity#pkey}保持一致
     */
    public static final String CONFIG_KEY_VIBRATE = "vibrate";

}
