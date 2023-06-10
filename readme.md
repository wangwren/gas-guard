# 燃气卫士
## [接口文档地址](https://console-docs.apipost.cn/preview/b82c28231f72a07f/957e8d17e84167f7)

## Log
- 2023-05-30 项目搭建完成
- 2023-05-31 分页demo
- 2023-06-01 登录登出、权限相关完成
- 2023-06-02 导出Excel测试接口完成
- 2023-06-03 接入swagger，ApiPost接口地址，完成
- 2023-06-03 参数配置 --> 数据字典管理接口完成
- 2023-06-04 参数配置 --> 预警类型配置接口完成
- 2023-06-04 参数配置 --> 离线规则配置接口完成
- 2023-06-05 参数配置 --> 系统参数配置接口完成
- 2023-06-05 参数配置 --> 屏蔽策略配置接口完成
- 2023-06-05 档案管理 --> 监测点位建档接口完成
- 2023-06-06 档案管理 --> 监测设备建档接口，查询接口完成，待测
- 2023-06-07 档案管理 --> 监测设备建档接口完成
- 2023-06-08 档案管理 --> 监测点位管理接口完成
- 2023-06-08 档案管理 --> 监测设备管理接口完成
## 待开发功能

### ~~参数配置~~
#### ~~数据字典管理~~
#### ~~预警类型配置~~
#### ~~离线规则配置~~
#### ~~系统参数配置~~
#### ~~屏蔽策略配置~~

### 档案管理

#### ~~监测点位建档，待自测，~~ 可能还有一些功能，需要根据设备进行关联开发
- ~~如果点位上绑有设备，不允许删除(不管点位状态)~~
- ~~不展示 档案状态 已通过 数据 ，待测~~
- ~~增加一个查询出所有状态的接口 ，待测~~

#### ~~监测设备建档~~
- ~~设备提交后，点位状态变为待审核 待测~~
- ~~监测设备建档导出字段，列表页为准~~
- ~~监测设备建档列表页面，需要带出监测点位数据中的字段(供气企业，燃气种类，用户种类)，待测~~
- ~~新增设备建档，设备编号唯一，保存时需要判断 待测~~
- ~~不展示 档案状态 已通过 数据，待测~~
- ~~列表查询页,搜索条件 点位类型 供气企业，燃气种类，用户种类，待测~~

#### ~~监测点位管理~~
- ~~点位上绑有设备，不允许删除~~
- ~~无设备和多设备查询出来的是已通过的数据吗，还有多设备是指一个及一个以上，还是一个以上~~
  - ~~无设备和多设备查出来的不限制状态~~
  - ~~多设备指的是一个以上，一个不算~~

#### ~~监测设备管理~~
- ~~设备提交后，点位状态变为待审核~~
- ~~批量设备恢复，设备状态变为正常，点位状态变为正常~~

#### 设备档案审核
- 只展示待审核设备
- 点击审核，通过设备带出点位信息
- ~~增加一个审核信息表，字段为，设备id，点位id，审核状态(通过，不通过)，审核反馈，创建时间，修改时间~~
  - 审核反馈中，通过不必填，不通过必填
- 审核状态，退回(不通过)，设备档案状态变为未通过，点位档案状态变为未通过
- 审核状态，通过，设备档案状态变为已通过，点位档案状态变为已通过

#### 天然气档案审核
#### 液化气档案审核
#### 正式档案管理
- 只展示 档案状态 已通过 数据

## 开发完成功能
### 登录
### 权限控制
- 使用 Apache Shiro 框架完成登录登出，以及菜单权限校验功能。

### 分页
### 数据导出