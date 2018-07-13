SET FOREIGN_KEY_CHECKS=0;



-- ----------------------------

-- Table structure for sys_user

-- ----------------------------

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (

 `id` varchar(50) NOT NULL COMMENT '用户Id',

 `phone_number` varchar(11) DEFAULT NULL COMMENT '手机号',

 `user_name` varchar(50) DEFAULT NULL COMMENT '用户名称',

 `user_name_pinyin` varchar(100) DEFAULT NULL COMMENT '用户名拼音',

 `email` varchar(50) DEFAULT NULL COMMENT '已绑定邮箱',

 `openid` varchar(100) DEFAULT NULL COMMENT 'openid',

 `is_subscribe` varchar(2) DEFAULT NULL COMMENT '是否关注',

 `wechat` varchar(255) DEFAULT NULL COMMENT '已绑定微信号',

 `org_id` varchar(50) DEFAULT NULL COMMENT '组织机构Id',

 `org_name` varchar(100) DEFAULT NULL COMMENT '组织机构名称',

 `position` varchar(100) DEFAULT NULL COMMENT '职位',

 `tenant_id` varchar(50) DEFAULT NULL COMMENT '租户Id',

 `sex` int(11) DEFAULT NULL COMMENT '性别(0:代表男 1:代表女)',

 `photo` varchar(255) DEFAULT NULL COMMENT '用户头像源路径',

 `centralGraph` varchar(255) DEFAULT NULL COMMENT '用户头像中图',

 `thumbnails` varchar(255) DEFAULT NULL COMMENT '用户头像小图',

 `birthday` datetime DEFAULT NULL COMMENT '生日',

 `address` varchar(255) DEFAULT NULL COMMENT '居住地址',

 `status` int(11) DEFAULT NULL COMMENT '人员状态：1.已激活、2.未激活、3.已禁用、4.未分配部门、89.离职、',

 `creater` varchar(50) DEFAULT NULL COMMENT '创建人',

 `create_time` datetime DEFAULT NULL COMMENT '创建时间',

 `modifyer` varchar(50) DEFAULT NULL COMMENT '修改人',

 `modify_time` datetime DEFAULT NULL COMMENT '修改时间',

 `device_token` varchar(50) DEFAULT NULL COMMENT '设备token  手机唯一标识 web端可为空',

 `device_type` int(11) DEFAULT NULL COMMENT '设备类型0其他 1:IOS 2:Android 3:pc',

 `reserve_1` varchar(20) DEFAULT NULL COMMENT '预留字段1',

 `reserve_2` varchar(20) DEFAULT NULL COMMENT '预留字段2',

 `reserve_3` varchar(20) DEFAULT NULL COMMENT '预留字段3',

 `user_number` varchar(20) DEFAULT NULL COMMENT '员工编号',

 PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8COMMENT='系统用户表';











SET FOREIGN_KEY_CHECKS=0;



-- ----------------------------

-- Table structure for sys_user_role

-- ----------------------------

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (

 `id` varchar(50) NOT NULL COMMENT '主键',

 `creater_id` varchar(50) DEFAULT NULL,

  `create_time` datetime DEFAULT NULL,

 `modifyer_id` varchar(50) DEFAULT NULL,

 `modify_time` datetime DEFAULT NULL,

 `user_id` varchar(50) DEFAULT NULL COMMENT '用户的主键ID',

 `role_id` varchar(50) DEFAULT NULL COMMENT '角色主键ID',

 PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;







SET FOREIGN_KEY_CHECKS=0;



-- ----------------------------

-- Table structure for sys_role

-- ----------------------------

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (

 `role_id` varchar(50) NOT NULL COMMENT '角色主键id',

 `role_name` varchar(150) DEFAULT NULL COMMENT '角色名称',

 `org_code` varchar(100) DEFAULT NULL,

 `org_name` varchar(100) DEFAULT NULL COMMENT '配置名称',

 `descript` varchar(128) DEFAULT NULL,

  `sort_num`int(11) DEFAULT NULL COMMENT '排序号',

 `is_system` int(11) DEFAULT '0' COMMENT '是否为系统默认角色, 1:是; 2:否',

 `creater_id` varchar(50) DEFAULT NULL,

 `create_time` datetime DEFAULT NULL,

 `modifyer` varchar(50) DEFAULT NULL,

 `modify_time` datetime DEFAULT NULL,

 `reserve_1` varchar(20) DEFAULT NULL COMMENT '预留字段1',

 `reserve_2` varchar(20) DEFAULT NULL COMMENT '预留字段2',

 `reserve_3` varchar(20) DEFAULT NULL COMMENT '预留字段3',

 PRIMARY KEY (`role_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;







SET FOREIGN_KEY_CHECKS=0;



-- ----------------------------

-- Table structure for sys_role_permission

-- ----------------------------

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (

 `id` varchar(50) NOT NULL COMMENT '主键',

 `role_id` varchar(50) DEFAULT NULL COMMENT '角色主键ID',

 `permission_id` varchar(50) DEFAULT NULL COMMENT '权限主键ID',

 `creater_id` varchar(50) DEFAULT NULL,

 `create_time` datetime DEFAULT NULL,

 `modifyer_id` varchar(50) DEFAULT NULL,

 `modify_time` datetime DEFAULT NULL,

 PRIMARY KEY (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;





SET FOREIGN_KEY_CHECKS=0;



-- ----------------------------

-- Table structure for sys_permission

-- ----------------------------

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (

 `permission_id` varchar(50) NOT NULL COMMENT '权限主键id',

 `org_code` varchar(100) DEFAULT NULL,

 `org_name` varchar(100) DEFAULT NULL COMMENT '配置名称',

 `parent_id` varchar(50) DEFAULT NULL,

 `url` varchar(150) DEFAULT NULL,

 `display` int(11) DEFAULT NULL COMMENT '选择权限时,是否显示该权限 2:不显示, 1显示',

 `sort_number` int(11) DEFAULT NULL COMMENT '显示顺序',

 `descritp` varchar(128) DEFAULT NULL COMMENT '权限描述',

 `status` int(11) DEFAULT NULL COMMENT '有效：1；无效：0',

 `creater_id` varchar(50) DEFAULT NULL,

 `create_time` datetime DEFAULT NULL,

 `modifyer_id` varchar(50) DEFAULT NULL,

 `modify_time` datetime DEFAULT NULL,

 `reserve_1` varchar(20) DEFAULT NULL COMMENT '预留字段1',

 `reserve_2` varchar(20) DEFAULT NULL COMMENT '预留字段2',

 `reserve_3` varchar(20) DEFAULT NULL COMMENT '预留字段3',

 PRIMARY KEY (`permission_id`),

  KEY`AK_IDX_SP_DISPLAY` (`display`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;