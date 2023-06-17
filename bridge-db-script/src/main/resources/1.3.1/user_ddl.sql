---------------------------------------------------------------------------
--用户模块数据库
---------------------------------------------------------------------------


/*==============================================================*/
/* Table: tb_user                                               */
/*==============================================================*/
drop table IF EXISTS tb_user;

create table tb_user (
   id                   SERIAL not null,
   username             VARCHAR(32)          null,
   password             VARCHAR(64)          null,
   gender               INT2                 null,
   phone                varchar(32)          null,
   real_name            VARCHAR(32)          null,
   pet_name             VARCHAR(32)          null,
   email                VARCHAR(64)          null,
   create_user_id       INT4                 null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_USER primary key (id)
);

comment on table tb_user is
'用户信息表';

comment on column tb_user.id is
'主键id';

comment on column tb_user.username is
'账号';

comment on column tb_user.password is
'密码';

comment on column tb_user.gender is
'性别';

comment on column tb_user.phone is
'电话号码';

comment on column tb_user.real_name is
'真实姓名';

comment on column tb_user.pet_name is
'昵称';

comment on column tb_user.email is
'邮箱';

comment on column tb_user.create_user_id is
'创建者';

comment on column tb_user.status is
'状态';

comment on column tb_user.create_time is
'创建时间';

comment on column tb_user.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_role                                               */
/*==============================================================*/
drop table IF EXISTS tb_role;

create table tb_role (
   id                   SERIAL not null,
   name                 VARCHAR(16)          null,
   english_name         VARCHAR(16)          null,
   remarks              VARCHAR(64)          null,
   constraint PK_TB_ROLE primary key (id)
);

comment on table tb_role is
'角色表';

comment on column tb_role.id is
'主键id';

comment on column tb_role.name is
'角色名称';

comment on column tb_role.english_name is
'英文名';

comment on column tb_role.remarks is
'备注';


/*==============================================================*/
/* Table: tb_user_role_rel                                      */
/*==============================================================*/
drop table IF EXISTS tb_user_role_rel;

create table tb_user_role_rel (
   id                   SERIAL not null,
   user_id              INT4                 null,
   role_id              INT4                 null,
   constraint PK_TB_USER_ROLE_REL primary key (id)
);

comment on table tb_user_role_rel is
'用户角色关联';

comment on column tb_user_role_rel.id is
'主键id';

comment on column tb_user_role_rel.user_id is
'用户id';

comment on column tb_user_role_rel.role_id is
'角色id';


/*==============================================================*/
/* Table: tb_power                                              */
/*==============================================================*/
drop table IF EXISTS tb_power;

create table tb_power (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   remarks              VARCHAR(64)          null,
   router_url           VARCHAR(128)         null,
   parent_id            INT4                 null,
   is_active            INT2                 null,
   priority             INT4                 null,
   business             INT2                 null,
   type                 VARCHAR(16)          null,
   constraint PK_TB_POWER primary key (id)
);

comment on table tb_power is
'权限表';

comment on column tb_power.id is
'主键id';

comment on column tb_power.name is
'权限名';

comment on column tb_power.remarks is
'备注';

comment on column tb_power.router_url is
'模块url';

comment on column tb_power.parent_id is
'父级模块id';

comment on column tb_power.is_active is
'是否是行为权限(0=不是，1=是)';

comment on column tb_power.priority is
'顺序';

comment on column tb_power.business is
'是否指派业务';

comment on column tb_power.type is
'行为类型';


/*==============================================================*/
/* Table: tb_role_power_rel                                     */
/*==============================================================*/
drop table IF EXISTS tb_role_power_rel;

create table tb_role_power_rel (
   id                   SERIAL not null,
   role_id              INT4                 null,
   power_id             INT4                 null,
   constraint PK_TB_ROLE_POWER_REL primary key (id)
);

comment on table tb_role_power_rel is
'角色权限关联表';

comment on column tb_role_power_rel.id is
'主键id';

comment on column tb_role_power_rel.role_id is
'角色id';

comment on column tb_role_power_rel.power_id is
'权限id';


/*==============================================================*/
/* Table: tb_user_power_rel                                     */
/*==============================================================*/
drop table IF EXISTS tb_user_power_rel;

create table tb_user_power_rel (
   id                   SERIAL not null,
   user_id              INT4                 null,
   power_id             INT4                 null,
   constraint PK_TB_USER_POWER_REL primary key (id)
);

comment on table tb_user_power_rel is
'用户权限关联';

comment on column tb_user_power_rel.id is
'主键id';

comment on column tb_user_power_rel.user_id is
'用户id';

comment on column tb_user_power_rel.power_id is
'权限id';


/*==============================================================*/
/* Table: tb_app_user                                           */
/*==============================================================*/
drop table IF EXISTS tb_app_user;

create table tb_app_user (
   id                   SERIAL not null,
   username             VARCHAR(32)          null,
   password             VARCHAR(64)          null,
   phone                varchar(32)          null,
   real_name            VARCHAR(32)          null,
   user_id              INT4                 null,
   create_user_id       INT4                 null,
   device_id            VARCHAR(128)         null,
   token                VARCHAR(256)         null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_APP_USER primary key (id)
);

comment on table tb_app_user is
'app用户表';

comment on column tb_app_user.id is
'主键id';

comment on column tb_app_user.username is
'账号';

comment on column tb_app_user.password is
'密码';

comment on column tb_app_user.phone is
'电话号码';

comment on column tb_app_user.real_name is
'真实姓名';

comment on column tb_app_user.user_id is
'所属承接单位主用户';

comment on column tb_app_user.create_user_id is
'创建者';

comment on column tb_app_user.device_id is
'设备编号';

comment on column tb_app_user.token is
'token令牌';

comment on column tb_app_user.status is
'状态';

comment on column tb_app_user.create_time is
'创建时间';

comment on column tb_app_user.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_app_user_role_rel                                  */
/*==============================================================*/
drop table IF EXISTS tb_app_user_role_rel;

create table tb_app_user_role_rel (
   id                   SERIAL not null,
   app_user_id          INT4                 null,
   app_role_id          INT4                 null,
   constraint PK_TB_APP_USER_ROLE_REL primary key (id)
);

comment on table tb_app_user_role_rel is
'app用户角色关联表';

comment on column tb_app_user_role_rel.id is
'主键id';

comment on column tb_app_user_role_rel.app_user_id is
'app用户id';

comment on column tb_app_user_role_rel.app_role_id is
'app角色id';


/*==============================================================*/
/* Table: tb_app_role                                           */
/*==============================================================*/
drop table IF EXISTS tb_app_role;

create table tb_app_role (
   id                   SERIAL not null,
   name                 VARCHAR(16)          null,
   english_name         VARCHAR(16)          null,
   remarks              VARCHAR(64)          null,
   constraint PK_TB_APP_ROLE primary key (id)
);

comment on table tb_app_role is
'app角色表';

comment on column tb_app_role.id is
'主键id';

comment on column tb_app_role.name is
'角色名称';

comment on column tb_app_role.english_name is
'英文名';

comment on column tb_app_role.remarks is
'备注';
