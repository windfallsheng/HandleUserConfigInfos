# HandleUserConfigInfos
![图片说明1](https://github.com/windfallsheng/HandleUserConfigInfos/blob/master/device-2019-06-15-120716.png)

通过另一种方式实现将一些默认数据存入本地数据库中，支持用户的修改操作，同时希望在应用的不同版本开发过程中，能够尽量方便、简单的处理这些数据的增删除改查操作。

将用户的默认配置信息写入在 json 文件中，这些数据会在应用启动用户登录后写入到数据库，

并且在以后应用的各版本开发当中，只需要操作json文件，就可以实现在数据库中增加、修改、删除某些配置信息，

数据库对应的操作业务基本一般不需要再作其它修改。

json文件示例：

[
  {
    "pkey": "user_setting",
    "ckey": "",
    "value": 0,
    "version": 1,
    "operationType": 1
  },
  {
    "pkey": "app_setting",
    "ckey": "",
    "value": 0,
    "version": 1,
    "operationType": 1
  },
  {
    "pkey": "notify",
    "ckey": "app_setting",
    "value": 1,
    "version": 1,
    "operationType": 1
  },
  {
    "pkey": "voice",
    "ckey": "user_setting",
    "value": 1,
    "version": 1,
    "operationType": 1
  },
  {
    "pkey": "vibrate",
    "ckey": "user_setting",
    "value": 1,
    "version": 1,
    "operationType": 1
  }
]
