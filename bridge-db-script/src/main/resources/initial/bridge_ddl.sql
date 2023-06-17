-----------------------------------
--用户模块
-----------------------------------


/*==============================================================*/
/* Table: tb_power                                              */
/*==============================================================*/
drop table if exists tb_power;
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

comment on table tb_power is '权限表';
comment on column tb_power.id is '主键id';
comment on column tb_power.name is '权限名';
comment on column tb_power.remarks is '备注';
comment on column tb_power.router_url is '模块url';
comment on column tb_power.parent_id is '父级模块id';
comment on column tb_power.is_active is '是否是行为权限(0=不是，1=是)';
comment on column tb_power.priority is '顺序';
comment on column tb_power.business is '是否指派业务';
comment on column tb_power.type is '行为类型';

/*==============================================================*/
/* Table: tb_role_power_rel                                     */
/*==============================================================*/
drop table if exists tb_role_power_rel;
create table tb_role_power_rel (
   id                   SERIAL not null,
   role_id              INT4                 null,
   power_id             INT4                 null,
   constraint PK_TB_ROLE_POWER_REL primary key (id)
);

comment on table tb_role_power_rel is '角色权限关联表';
comment on column tb_role_power_rel.id is '主键id';
comment on column tb_role_power_rel.role_id is '角色id';
comment on column tb_role_power_rel.power_id is '权限id';


/*==============================================================*/
/* Table: tb_role                                               */
/*==============================================================*/
drop table if exists tb_role;
create table tb_role (
   id                   SERIAL not null,
   name                 VARCHAR(16)          null,
   english_name         VARCHAR(16)          null,
   remarks              VARCHAR(64)          null,
   constraint PK_TB_ROLE primary key (id)
);

comment on table tb_role is '角色表';
comment on column tb_role.id is '主键id';
comment on column tb_role.name is '角色名称';
comment on column tb_role.english_name is '英文名';
comment on column tb_role.remarks is '备注';


/*==============================================================*/
/* Table: tb_unit                                               */
/*==============================================================*/
drop table if exists tb_unit;
create table tb_unit (
   id                   SERIAL not null,
   phone                varchar(32)          null,
   name                 VARCHAR(64)          null,
   role_id              INT4                 null,
   parent_id            INT4                 null,
   create_user_id       INT4                 null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_UNIT primary key (id)
);

comment on table tb_unit is '企业单位表';
comment on column tb_unit.id is '主键id';
comment on column tb_unit.phone is '电话号码';
comment on column tb_unit.name is '单位名称';
comment on column tb_unit.role_id is '单位类型';
comment on column tb_unit.parent_id is '父级单位';
comment on column tb_unit.create_user_id is '创建者';
comment on column tb_unit.status is '状态';
comment on column tb_unit.create_time is '创建时间';
comment on column tb_unit.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_unit_power_rel                                     */
/*==============================================================*/
drop table if exists tb_unit_power_rel;
create table tb_unit_power_rel (
   id                   SERIAL not null,
   unit_id              INT4                 null,
   power_id             INT4                 null,
   constraint PK_TB_UNIT_POWER_REL primary key (id)
);

comment on table tb_unit_power_rel is '单位权限关联';
comment on column tb_unit_power_rel.id is '主键id';
comment on column tb_unit_power_rel.unit_id is '单位id';
comment on column tb_unit_power_rel.power_id is '权限id';


/*==============================================================*/
/* Table: tb_user_role_rel                                      */
/*==============================================================*/
drop table if exists tb_user_role_rel;
create table tb_user_role_rel (
   id                   SERIAL not null,
   user_id              INT4                 null,
   role_id              INT4                 null,
   constraint PK_TB_USER_ROLE_REL primary key (id)
);

comment on table tb_user_role_rel is '用户角色关联';
comment on column tb_user_role_rel.id is '主键id';
comment on column tb_user_role_rel.user_id is '用户id';
comment on column tb_user_role_rel.role_id is '角色id';


/*==============================================================*/
/* Table: tb_user                                               */
/*==============================================================*/
drop table if exists tb_user;
create table tb_user (
   id                   SERIAL not null,
   username             VARCHAR(32)          null,
   password             VARCHAR(64)          null,
   gender               INT2                 null,
   phone                varchar(32)          null,
   real_name            VARCHAR(32)          null,
   pet_name             VARCHAR(32)          null,
   email                VARCHAR(64)          null,
   unit_id              INT4                 null,
   create_user_id       INT4                 null,
   device_id            VARCHAR(128)         null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_USER primary key (id)
);

comment on table tb_user is '用户信息表';
comment on column tb_user.id is '主键id';
comment on column tb_user.username is '账号';
comment on column tb_user.password is '密码';
comment on column tb_user.gender is '性别';
comment on column tb_user.phone is '电话号码';
comment on column tb_user.real_name is '真实姓名';
comment on column tb_user.pet_name is '昵称';
comment on column tb_user.email is '邮箱';
comment on column tb_user.unit_id is '所属单位id';
comment on column tb_user.create_user_id is '创建者';
comment on column tb_user.device_id is '设备编号';
comment on column tb_user.status is '状态';
comment on column tb_user.create_time is '创建时间';
comment on column tb_user.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_user_app_role_rel                                  */
/*==============================================================*/
drop table if exists tb_user_app_role_rel;
create table tb_user_app_role_rel (
   id                   SERIAL not null,
   app_role_id          INT4                 null,
   user_id              INT4                 null,
   constraint PK_TB_USER_APP_ROLE_REL primary key (id)
);

comment on table tb_user_app_role_rel is '用户app角色关联表';
comment on column tb_user_app_role_rel.id is '主键id';
comment on column tb_user_app_role_rel.app_role_id is 'app角色id';
comment on column tb_user_app_role_rel.user_id is '用户id';


/*==============================================================*/
/* Table: tb_app_role                                           */
/*==============================================================*/
drop table if exists tb_app_role;
create table tb_app_role (
   id                   SERIAL not null,
   name                 VARCHAR(16)          null,
   english_name         VARCHAR(16)          null,
   remarks              VARCHAR(64)          null,
   constraint PK_TB_APP_ROLE primary key (id)
);

comment on table tb_app_role is 'app角色表';
comment on column tb_app_role.id is '主键id';
comment on column tb_app_role.name is '角色名称';
comment on column tb_app_role.english_name is '英文名';
comment on column tb_app_role.remarks is '备注';

-----------------------------------
--通用模块
-----------------------------------

/*==============================================================*/
/* Table: tb_project                                            */
/*==============================================================*/
drop table if exists tb_project;
create table tb_project (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   synopsis             VARCHAR(256)         null,
   unit_id              INT4                 null,
   create_user_id       INT4                 null,
   charge_person        VARCHAR(32)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_PROJECT primary key (id)
);

comment on table tb_project is '项目信息表';
comment on column tb_project.id is '主键id';
comment on column tb_project.name is '项目名称';
comment on column tb_project.synopsis is '项目简介';
comment on column tb_project.unit_id is '所属业主单位id';
comment on column tb_project.create_user_id is '创建者id';
comment on column tb_project.charge_person is '项目联系人';
comment on column tb_project.status is '状态';
comment on column tb_project.create_time is '创建时间';
comment on column tb_project.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_project_structure_rel                              */
/*==============================================================*/
drop table if exists tb_project_structure_rel;
create table tb_project_structure_rel (
   id                   SERIAL not null,
   project_id           INT4                 null,
   structure_id         INT4                 null,
   constraint PK_TB_PROJECT_STRUCTURE_REL primary key (id)
);

comment on table tb_project_structure_rel is '项目结构物关联表';
comment on column tb_project_structure_rel.id is '主键id';
comment on column tb_project_structure_rel.project_id is '项目id';
comment on column tb_project_structure_rel.structure_id is '结构物id';

drop table if exists tb_structure;

/*==============================================================*/
/* Table: tb_structure                                          */
/*==============================================================*/
create table tb_structure (
   id                   SERIAL not null,
   code                 VARCHAR(32)          null,
   name                 VARCHAR(32)          null,
   number               VARCHAR(32)          null,
   province_id          INT4                 null,
   city_id              INT4                 null,
   county_id            INT4                 null,
   charge_name          VARCHAR(32)          null,
   charge_phone         VARCHAR(32)          null,
   road_name            VARCHAR(32)          null,
   build_time           TIMESTAMP            null,
   maintain_category    INT2                 null,
   maintain_grade       INT2                 null,
   maintain_department  VARCHAR(32)          null,
   maintain_depart_phone VARCHAR(32)          null,
   design_load          VARCHAR(32)          null,
   span_type            VARCHAR(32)          null,
   longitude            DECIMAL(10,6)        null,
   latitude             DECIMAL(10,6)        null,
   structure_type       INT2                 null,
   bridge_type          INT2                 null,
   running_department   VARCHAR(32)          null,
   supervision_department VARCHAR(32)          null,
   building_department  VARCHAR(32)          null,
   construction_department VARCHAR(32)          null,
   design_department    VARCHAR(32)          null,
   unit_id              INT4                 null,
   create_user_id       INT4                 null,
   technology           VARCHAR(16)          null,
   grade                VARCHAR(16)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_STRUCTURE primary key (id)
);

comment on table tb_structure is '结构物信息表';
comment on column tb_structure.id is '主键id';
comment on column tb_structure.code is '结构物代码';
comment on column tb_structure.name is '结构物名称';
comment on column tb_structure.number is '结构物编号';
comment on column tb_structure.province_id is '所在省';
comment on column tb_structure.city_id is '所在市';
comment on column tb_structure.county_id is '所在区(县)';
comment on column tb_structure.charge_name is '相关责任人';
comment on column tb_structure.charge_phone is '责任人电话';
comment on column tb_structure.road_name is '路线名称';
comment on column tb_structure.build_time is '建成年月';
comment on column tb_structure.maintain_category is '1:I类 2:II类 3:III类 4:IV类 5:V类';
comment on column tb_structure.maintain_grade is '养护等级 1: I等 2:II等 3:III等';
comment on column tb_structure.maintain_department is '养护单位';
comment on column tb_structure.maintain_depart_phone is '养护单位电话';
comment on column tb_structure.design_load is '设计荷载';
comment on column tb_structure.span_type is '跨径分类';
comment on column tb_structure.longitude is '经度';
comment on column tb_structure.latitude is '纬度';
comment on column tb_structure.structure_type is '1:桥梁 2:隧道';
comment on column tb_structure.bridge_type is '桥梁类型 1:梁桥 2:拱桥 3:刚架桥 4:悬索桥 5:斜拉桥 6:钢管混凝土拱桥';
comment on column tb_structure.running_department is '运行管理单位';
comment on column tb_structure.supervision_department is '监理单位';
comment on column tb_structure.building_department is '建设单位';
comment on column tb_structure.construction_department is '施工单位';
comment on column tb_structure.design_department is '设计单位';
comment on column tb_structure.unit_id is '所属业主单位id';
comment on column tb_structure.create_user_id is '创建者id';
comment on column tb_structure.technology is '技术状况';
comment on column tb_structure.grade is '状况等级';
comment on column tb_structure.status is '状态';
comment on column tb_structure.create_time is '创建时间';
comment on column tb_structure.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_bridge_info                                        */
/*==============================================================*/
drop table if exists tb_bridge_info;
create table tb_bridge_info (
   id                   SERIAL not null,
   structure_id         INT4                 null,
   cost                 DECIMAL(10,2)        null,
   road_grade           VARCHAR(32)          null,
   posting_standard     VARCHAR(32)          null,
   quake_intensity      VARCHAR(32)          null,
   skew_angle           VARCHAR(32)          null,
   span_num             INT4                 null,
   span_combination     VARCHAR(32)          null,
   area                 FLOAT4               null,
   total_length         FLOAT4               null,
   total_width          FLOAT4               null,
   roadway_width        FLOAT4               null,
   sidewalk_width       FLOAT4               null,
   streamway_grade      VARCHAR(32)          null,
   highest_stage        FLOAT4               null,
   usual_stage          FLOAT4               null,
   up_main_beam_form    VARCHAR(32)          null,
   up_main_beam_size    VARCHAR(32)          null,
   up_main_beam_quantity INT4                 null,
   up_cross_beam_form   VARCHAR(32)          null,
   clearance_span       FLOAT4               null,
   lower_limit          FLOAT4               null,
   up_rise_span         VARCHAR(32)          null,
   up_bearing_form      VARCHAR(32)          null,
   up_bearing_num       INT4                 null,
   up_deck_composition  VARCHAR(32)          null,
   up_pavement_land     FLOAT4               null,
   up_expansion_form    VARCHAR(32)          null,
   up_expansion_quantity INT4                 null,
   up_main_longitudinal_slope FLOAT4               null,
   up_main_cross_slope  FLOAT4               null,
   up_approach_longitudinal_slope FLOAT4               null,
   up_approach_cross_slope FLOAT4               null,
   pier_form            VARCHAR(32)          null,
   pier_num             INT4                 null,
   pier_elevation       FLOAT4               null,
   pier_cap_size        VARCHAR(32)          null,
   pier_base_elevation  FLOAT4               null,
   pier_floor_size      VARCHAR(32)          null,
   pier_pile_size       VARCHAR(32)          null,
   pier_pile_num        INT4                 null,
   abutment_form        VARCHAR(32)          null,
   abutment_num         INT4                 null,
   abutment_elevation   FLOAT4               null,
   abutment_base_elevation FLOAT4               null,
   abutment_cap_size    VARCHAR(32)          null,
   abutment_baseboard_size VARCHAR(32)          null,
   abutment_pile_size   VARCHAR(32)          null,
   abutment_pile_num    INT4                 null,
   abutment_retain_thick FLOAT4               null,
   abutment_wing_wall_form VARCHAR(32)          null,
   abutment_wing_wall_length FLOAT4               null,
   auxiliary_gulley_size VARCHAR(32)          null,
   auxiliary_gulley_num INT4                 null,
   water_drain_pipe_size VARCHAR(32)          null,
   water_drain_pipe_length FLOAT4               null,
   auxiliary_rail_length FLOAT4               null,
   auxiliary_rail_struction VARCHAR(32)          null,
   auxiliary_boundary_size VARCHAR(32)          null,
   auxiliary_revetment_type VARCHAR(32)          null,
   auxiliary_barricade_type VARCHAR(32)          null,
   pipeline_water       VARCHAR(32)          null,
   pipeline_gas         VARCHAR(32)          null,
   pipeline_electricity VARCHAR(32)          null,
   pipeline_cable       VARCHAR(32)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_BRIDGE_INFO primary key (id)
);

comment on table tb_bridge_info is '桥梁详情信息表';
comment on column tb_bridge_info.id is '主键id';
comment on column tb_bridge_info.structure_id is '结构物id';
comment on column tb_bridge_info.cost is '总造价';
comment on column tb_bridge_info.road_grade is '道路等级';
comment on column tb_bridge_info.posting_standard is '限载标准';
comment on column tb_bridge_info.quake_intensity is '抗震烈度';
comment on column tb_bridge_info.skew_angle is '正斜交角';
comment on column tb_bridge_info.span_num is '桥梁跨度';
comment on column tb_bridge_info.span_combination is '跨径组合';
comment on column tb_bridge_info.area is '桥梁面积';
comment on column tb_bridge_info.total_length is '桥梁总长';
comment on column tb_bridge_info.total_width is '桥梁总宽';
comment on column tb_bridge_info.roadway_width is '车行道净宽';
comment on column tb_bridge_info.sidewalk_width is '人行道净宽';
comment on column tb_bridge_info.streamway_grade is '河道等级';
comment on column tb_bridge_info.highest_stage is '最高水位';
comment on column tb_bridge_info.usual_stage is '常水位';
comment on column tb_bridge_info.up_main_beam_form is '上部结构_主梁型式';
comment on column tb_bridge_info.up_main_beam_size is '上部结构_主梁尺寸';
comment on column tb_bridge_info.up_main_beam_quantity is '上部结构_主梁数量';
comment on column tb_bridge_info.up_cross_beam_form is '上部结构_横梁型式';
comment on column tb_bridge_info.clearance_span is '上部结构_主跨桥下净空';
comment on column tb_bridge_info.lower_limit is '上部结构_桥下限高';
comment on column tb_bridge_info.up_rise_span is '上部结构_跨桥矢跨比';
comment on column tb_bridge_info.up_bearing_form is '上部结构_支座型式';
comment on column tb_bridge_info.up_bearing_num is '上部结构_支座数量';
comment on column tb_bridge_info.up_deck_composition is '上部结构_桥面结构';
comment on column tb_bridge_info.up_pavement_land is '上部结构_桥面铺装厚度';
comment on column tb_bridge_info.up_expansion_form is '上部结构_伸缩缝型式';
comment on column tb_bridge_info.up_expansion_quantity is '上部结构_伸缩缝数量';
comment on column tb_bridge_info.up_main_longitudinal_slope is '上部结构_主桥纵坡';
comment on column tb_bridge_info.up_main_cross_slope is '上部结构_主桥横坡';
comment on column tb_bridge_info.up_approach_longitudinal_slope is '上部结构_引桥纵坡';
comment on column tb_bridge_info.up_approach_cross_slope is '上部结构_引桥横坡';
comment on column tb_bridge_info.pier_form is '桥墩_型式';
comment on column tb_bridge_info.pier_num is '桥墩_数量';
comment on column tb_bridge_info.pier_elevation is '桥墩_标高';
comment on column tb_bridge_info.pier_cap_size is '桥墩_盖梁尺寸';
comment on column tb_bridge_info.pier_base_elevation is '桥墩_基底标高（m）';
comment on column tb_bridge_info.pier_floor_size is '桥墩_底板尺寸';
comment on column tb_bridge_info.pier_pile_size is '桥墩_基桩尺寸';
comment on column tb_bridge_info.pier_pile_num is '桥墩_基桩根数';
comment on column tb_bridge_info.abutment_form is '桥台_型式';
comment on column tb_bridge_info.abutment_num is '桥台_数量';
comment on column tb_bridge_info.abutment_elevation is '桥台_标高';
comment on column tb_bridge_info.abutment_base_elevation is '桥台_基底标高（m）';
comment on column tb_bridge_info.abutment_cap_size is '桥台_台帽尺寸';
comment on column tb_bridge_info.abutment_baseboard_size is '桥台_底板尺寸';
comment on column tb_bridge_info.abutment_pile_size is '桥台_基桩尺寸';
comment on column tb_bridge_info.abutment_pile_num is '桥台_基桩根数';
comment on column tb_bridge_info.abutment_retain_thick is '桥台_挡土板厚度';
comment on column tb_bridge_info.abutment_wing_wall_form is '桥台_翼墙型式';
comment on column tb_bridge_info.abutment_wing_wall_length is '桥台_翼墙长度';
comment on column tb_bridge_info.auxiliary_gulley_size is '附属工程_集水口尺寸';
comment on column tb_bridge_info.auxiliary_gulley_num is '附属工程_集水口数量';
comment on column tb_bridge_info.water_drain_pipe_size is '附属工程-泄水管尺寸';
comment on column tb_bridge_info.water_drain_pipe_length is '附属工程-泄水管长度';
comment on column tb_bridge_info.auxiliary_rail_length is '附属工程_栏杆总长';
comment on column tb_bridge_info.auxiliary_rail_struction is '附属工程_栏杆结构';
comment on column tb_bridge_info.auxiliary_boundary_size is '附属工程_端柱尺寸';
comment on column tb_bridge_info.auxiliary_revetment_type is '附属工程_护岸类型';
comment on column tb_bridge_info.auxiliary_barricade_type is '附属工程-引坡挡墙类型';
comment on column tb_bridge_info.pipeline_water is '附挂管线_给水管';
comment on column tb_bridge_info.pipeline_gas is '附挂管线_燃气管';
comment on column tb_bridge_info.pipeline_electricity is '附挂管线_电力缆';
comment on column tb_bridge_info.pipeline_cable is '附挂管线_通讯电缆';
comment on column tb_bridge_info.status is '状态';
comment on column tb_bridge_info.create_time is '创建时间';
comment on column tb_bridge_info.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_tunnel_info                                        */
/*==============================================================*/
drop table if exists tb_tunnel_info;
create table tb_tunnel_info (
   id                   SERIAL not null,
   structure_id         INT4                 null,
   road_code            VARCHAR(32)          null,
   road_grade           VARCHAR(32)          null,
   center_mileage       VARCHAR(32)          null,
   length               FLOAT4               null,
   width                FLOAT4               null,
   road_width           FLOAT4               null,
   height               FLOAT4               null,
   stratum_geology      VARCHAR(32)          null,
   wall_rock_types      VARCHAR(32)          null,
   lining_type          VARCHAR(32)          null,
   lining_thick         FLOAT4               null,
   portal_form          VARCHAR(32)          null,
   road_type            VARCHAR(32)          null,
   lighting_installation VARCHAR(32)          null,
   air_installation     VARCHAR(32)          null,
   fire_installation    VARCHAR(32)          null,
   watch_installation   VARCHAR(32)          null,
   electric_installation VARCHAR(32)          null,
   tunnel_longitudinal_slope VARCHAR(32)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_TUNNEL_INFO primary key (id)
);

comment on table tb_tunnel_info is'隧道信息表';
comment on column tb_tunnel_info.id is'主键id';
comment on column tb_tunnel_info.structure_id is'结构结构物id';
comment on column tb_tunnel_info.road_code is'路线编号';
comment on column tb_tunnel_info.road_grade is'路线等级';
comment on column tb_tunnel_info.center_mileage is'中心桩号';
comment on column tb_tunnel_info.length is'长度';
comment on column tb_tunnel_info.width is'宽度';
comment on column tb_tunnel_info.road_width is'路面宽度';
comment on column tb_tunnel_info.height is'净高';
comment on column tb_tunnel_info.stratum_geology is'岩层地质';
comment on column tb_tunnel_info.wall_rock_types is'围岩分类';
comment on column tb_tunnel_info.lining_type is'衬砌类型';
comment on column tb_tunnel_info.lining_thick is'衬砌厚度';
comment on column tb_tunnel_info.portal_form is'洞门型式';
comment on column tb_tunnel_info.road_type is'路面类型';
comment on column tb_tunnel_info.lighting_installation is'照明设施';
comment on column tb_tunnel_info.air_installation is'通风设施';
comment on column tb_tunnel_info.fire_installation is'消防与救援设施';
comment on column tb_tunnel_info.watch_installation is'监控设施';
comment on column tb_tunnel_info.electric_installation is'供配电设施';
comment on column tb_tunnel_info.tunnel_longitudinal_slope is'洞内纵坡';
comment on column tb_tunnel_info.status is'状态';
comment on column tb_tunnel_info.create_time is'创建时间';
comment on column tb_tunnel_info.modify_time is'修改时间';


/*==============================================================*/
/* Table: tb_project_appoint                                    */
/*==============================================================*/
drop table if exists tb_project_appoint;
create table tb_project_appoint (
   id                   SERIAL not null,
   project_id           INT4                 null,
   appoint_unit_id      INT4                 null,
   receive_unit_id      INT4                 null,
   start_time           TIMESTAMP            null,
   end_time             TIMESTAMP            null,
   create_user_id       INT4                 null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_PROJECT_APPOINT primary key (id)
);

comment on table tb_project_appoint is '项目指派记录表';
comment on column tb_project_appoint.id is '主键id';
comment on column tb_project_appoint.project_id is '项目id';
comment on column tb_project_appoint.appoint_unit_id is '指派方id';
comment on column tb_project_appoint.receive_unit_id is '接收方id';
comment on column tb_project_appoint.start_time is '开始时间';
comment on column tb_project_appoint.end_time is '结束时间';
comment on column tb_project_appoint.create_user_id is '创建者id';
comment on column tb_project_appoint.status is '状态';
comment on column tb_project_appoint.create_time is '创建时间';
comment on column tb_project_appoint.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_project_appoint_power_rel                          */
/*==============================================================*/
drop table if exists tb_project_appoint_power_rel;
create table tb_project_appoint_power_rel (
   id                   SERIAL not null,
   project_appoint_id   INT4                 null,
   power_id             INT4                 null,
   constraint PK_TB_PROJECT_APPOINT_POWER_RE primary key (id)
);

comment on column tb_project_appoint_power_rel.id is '主键id';
comment on column tb_project_appoint_power_rel.project_appoint_id is '项目指派记录id';
comment on column tb_project_appoint_power_rel.power_id is '业务权限id';


/*==============================================================*/
/* Table: tb_app_notice                                         */
/*==============================================================*/
drop table if exists tb_app_notice;
create table tb_app_notice (
   id                   SERIAL not null,
   title                VARCHAR(32)          null,
   content              VARCHAR(256)         null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_APP_NOTICE primary key (id)
);

comment on table tb_app_notice is 'App通知表';
comment on column tb_app_notice.id is '主键id';
comment on column tb_app_notice.title is '公告标题';
comment on column tb_app_notice.content is '公告内容';
comment on column tb_app_notice.status is '状态';
comment on column tb_app_notice.create_time is '创建时间';
comment on column tb_app_notice.modify_time is '修改时间';

drop table if exists tb_user_noticet_rel;

/*==============================================================*/
/* Table: tb_user_noticet_rel                                   */
/*==============================================================*/
create table tb_user_notice_rel (
   id                   SERIAL not null,
   notice_id            INT4                 null,
   user_id              INT4                 null,
   status               INT2                 null,
   constraint PK_TB_USER_NOTICET_REL primary key (id)
);

comment on table tb_user_notice_rel is '用户通知关联表';
comment on column tb_user_notice_rel.id is '主键id';
comment on column tb_user_notice_rel.notice_id is '通知id';
comment on column tb_user_notice_rel.user_id is '用户id';
comment on column tb_user_notice_rel.status is '0:未读 1:已读';


/*==============================================================*/
/* Table: tb_video                                              */
/*==============================================================*/
drop table if exists tb_video;
create table tb_video (
   id                   SERIAL not null,
   structure_id         INT4                 null,
   part_id              INT4                 null,
   component_id         INT4                 null,
   monitor_name         VARCHAR(32)          null,
   type                 INT2                 null,
   ezopen_url           VARCHAR(128)         null,
   ip                   VARCHAR(32)          null,
   port                 INT4                 null,
   app_key              VARCHAR(64)          null,
   password             VARCHAR(32)          null,
   video_coding         VARCHAR(32)          null,
   x_axis               FLOAT4               null,
   y_axis               FLOAT4               null,
   z_axis               FLOAT4               null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_VIDEO primary key (id)
);

comment on table tb_video is '视频表';
comment on column tb_video.id is '主键id';
comment on column tb_video.structure_id is '结构物id';
comment on column tb_video.part_id is '部位id';
comment on column tb_video.component_id is '构件id';
comment on column tb_video.monitor_name is '监控视角名称';
comment on column tb_video.type is '设备类型 1:萤石 2:海康威视';
comment on column tb_video.ezopen_url is '萤石云监控地址';
comment on column tb_video.ip is '视频ip地址';
comment on column tb_video.port is '视频端口号';
comment on column tb_video.app_key is '监控appkey';
comment on column tb_video.password is '监控密码';
comment on column tb_video.video_coding is '监控视频码';
comment on column tb_video.x_axis is 'X坐标';
comment on column tb_video.y_axis is 'Y坐标';
comment on column tb_video.z_axis is 'Z坐标';
comment on column tb_video.status is '状态';
comment on column tb_video.create_time is '创建时间';
comment on column tb_video.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_announcement                                       */
/*==============================================================*/
drop table if exists tb_announcement;
create table tb_announcement (
   id                   SERIAL not null,
   title                VARCHAR(32)          null,
   content              VARCHAR(256)         null,
   create_user_id       INT4                 null,
   creator              VARCHAR(32)          null,
   unit_id              INT4                 null,
   effect_time          TIMESTAMP            null,
   expires_time         TIMESTAMP            null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_ANNOUNCEMENT primary key (id)
);

comment on table tb_announcement is '公告表';
comment on column tb_announcement.id is '主键id';
comment on column tb_announcement.title is '公告标题';
comment on column tb_announcement.content is '公告内容';
comment on column tb_announcement.create_user_id is '创建者id';
comment on column tb_announcement.creator is '创建者';
comment on column tb_announcement.unit_id is '所属单位id';
comment on column tb_announcement.effect_time is '生效时间';
comment on column tb_announcement.expires_time is '到期时间';
comment on column tb_announcement.status is '0:未发布 1:已发布';
comment on column tb_announcement.create_time is '创建时间';
comment on column tb_announcement.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_user_announcement_rel                              */
/*==============================================================*/
drop table if exists tb_user_announcement_rel;
create table tb_user_announcement_rel (
   id                   SERIAL not null,
   announcement_id      INT4                 null,
   user_id              INT4                 null,
   status               INT2                 null,
   constraint PK_TB_USER_ANNOUNCEMENT_REL primary key (id)
);

comment on table tb_user_announcement_rel is '用户公告关联表';
comment on column tb_user_announcement_rel.id is '主键id';
comment on column tb_user_announcement_rel.announcement_id is '公告id';
comment on column tb_user_announcement_rel.user_id is '用户id';
comment on column tb_user_announcement_rel.status is '0:未读 1:已读';


/*==============================================================*/
/* Table: tb_photo                                              */
/*==============================================================*/
drop table if exists tb_photo;
create table tb_photo (
   id                   SERIAL not null,
   type                 INT2                 null,
   target_id            INT4                 null,
   name                 VARCHAR(64)          null,
   path                 VARCHAR(128)         null,
   terrain              INT2                 null,
   remarks              VARCHAR(256)         null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_PHOTO primary key (id)
);

comment on table tb_photo is '图片表';
comment on column tb_photo.id is '主键id';
comment on column tb_photo.type is '1:桥梁图片 2:隧道图片 3:桥梁附件 4:隧道附件 5:日常巡检 6.7.8 维养前中后';
comment on column tb_photo.target_id is '目标id';
comment on column tb_photo.name is '文件名';
comment on column tb_photo.path is '存储位置';
comment on column tb_photo.terrain is '是否加载地形';
comment on column tb_photo.remarks is '备注';
comment on column tb_photo.status is '状态';
comment on column tb_photo.create_time is '创建时间';
comment on column tb_photo.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_component_info                                     */
/*==============================================================*/
drop table if exists tb_component_info;
create table tb_component_info (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   english_name         VARCHAR(32)          null,
   part_id              INT4                 null,
   constraint PK_TB_COMPONENT_INFO primary key (id)
);

comment on table tb_component_info is '巡查维养构件表';
comment on column tb_component_info.id is '主键id';
comment on column tb_component_info.name is '构件名称';
comment on column tb_component_info.english_name is '英文名';
comment on column tb_component_info.part_id is '1:桥面系 2:上部结构 3:上部结构 4:人行地下通道';


/*==============================================================*/
/* Table: tb_report                                             */
/*==============================================================*/
drop table if exists tb_report;
create table tb_report (
   id                   SERIAL not null,
   project_id           INT4                 null,
   name                 VARCHAR(32)          null,
   user_id              INT4                 null,
   type                 INT2                 null,
   specific             INT2                 null,
   path                 VARCHAR(64)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_REPORT primary key (id)
);

comment on table tb_report is '报表信息';
comment on column tb_report.id is '主键id';
comment on column tb_report.project_id is '项目id';
comment on column tb_report.name is '名称';
comment on column tb_report.user_id is '上传者id';
comment on column tb_report.type is '1:监测报表 2:巡查报表 3:维养报表';
comment on column tb_report.specific is '1:日报 2:月报 3:年报';
comment on column tb_report.path is '路径';
comment on column tb_report.status is '状态';
comment on column tb_report.create_time is '创建时间';
comment on column tb_report.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_part_info                                          */
/*==============================================================*/
drop table if exists tb_part_info;
create table tb_part_info (
   id                   SERIAL not null,
   name                 VARCHAR(16)          null,
   english_name         VARCHAR(32)          null,
   constraint PK_TB_PART_INFO primary key (id)
);

comment on table tb_part_info is '部位信息表';
comment on column tb_part_info.id is '主键id';
comment on column tb_part_info.name is '部位名称';
comment on column tb_part_info.english_name is '英文名';


/*==============================================================*/
/* Table: tb_electronic_archives                                */
/*==============================================================*/
drop table if exists tb_electronic_archives;
create table tb_electronic_archives (
   id                   SERIAL not null,
   structure_id         INT4                 null,
   file_name            VARCHAR(32)          null,
   code                 VARCHAR(32)          null,
   type                 INT2                 null,
   summary              VARCHAR(256)         null,
   user_id              INT4                 null,
   path                 VARCHAR(64)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_ELECTRONIC_ARCHIVES primary key (id)
);

comment on table tb_electronic_archives is '电子档案资料表';
comment on column tb_electronic_archives.id is '主键id';
comment on column tb_electronic_archives.structure_id is '结构物id';
comment on column tb_electronic_archives.file_name is '文件名称';
comment on column tb_electronic_archives.code is '文件编号';
comment on column tb_electronic_archives.type is '1:设计档案 2:施工文件 3:合同文件 4:基本资料 5:养护档案 6:其他';
comment on column tb_electronic_archives.summary is '文件摘要';
comment on column tb_electronic_archives.user_id is '创建者id';
comment on column tb_electronic_archives.path is '路径';
comment on column tb_electronic_archives.status is '状态';
comment on column tb_electronic_archives.create_time is '创建时间';
comment on column tb_electronic_archives.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_area                                               */
/*==============================================================*/
drop table if exists tb_area;
create table tb_area (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   parent               INT4                 null,
   code                 VARCHAR(32)          null,
   constraint PK_TB_AREA primary key (id)
);

comment on table tb_area is '省市区表';
comment on column tb_area.id is '主键id';
comment on column tb_area.name is '名称';
comment on column tb_area.parent is '父级id';
comment on column tb_area.code is '编码';


-----------------------------------
--巡查维养模块
-----------------------------------


/*==============================================================*/
/* Table: tb_plan_info                                          */
/*==============================================================*/
drop table if exists tb_plan_info;
create table tb_plan_info (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   project_id           INT4                 null,
   plan_time            TIMESTAMP            null,
   budget               DECIMAL(10,2)        null,
   expenditure          DECIMAL(10,2)        null,
   type                 INT2                 null,
   user_id              INT4                 null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_PLAN_INFO primary key (id)
);

comment on table tb_plan_info is '巡查维养计划表';
comment on column tb_plan_info.id is '主键id';
comment on column tb_plan_info.name is '计划名称';
comment on column tb_plan_info.project_id is '所属项目';
comment on column tb_plan_info.plan_time is '巡查/维修月份';
comment on column tb_plan_info.budget is '预算金额';
comment on column tb_plan_info.expenditure is '实际金额';
comment on column tb_plan_info.type is '1:日常巡查 2:经常检查 3:特殊检查 4:维修养护';
comment on column tb_plan_info.user_id is '创建者id';
comment on column tb_plan_info.status is '0:未执行 1:执行中 2:已完成 ';
comment on column tb_plan_info.create_time is '创建时间';
comment on column tb_plan_info.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_plan_detail                                        */
/*==============================================================*/
drop table if exists tb_plan_detail;
create table tb_plan_detail (
   id                   SERIAL not null,
   plan_id              INT4                 null,
   structure_id         INT4                 null,
   inspector            VARCHAR(32)          null,
   inspection_unit      VARCHAR(32)          null,
   file_save_collator   VARCHAR(32)          null,
   inspection_time      TIMESTAMP            null,
   weather              VARCHAR(8)           null,
   longitude            DECIMAL(12,8)        null,
   latitude             DECIMAL(12,8)        null,
   location             VARCHAR(64)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_PLAN_DETAIL primary key (id)
);

comment on table tb_plan_detail is '计划详情';
comment on column tb_plan_detail.id is '主键id';
comment on column tb_plan_detail.plan_id is '计划id';
comment on column tb_plan_detail.structure_id is '结构物id';
comment on column tb_plan_detail.inspector is '巡查人员';
comment on column tb_plan_detail.inspection_unit is '巡查单位';
comment on column tb_plan_detail.file_save_collator is '核对存档人';
comment on column tb_plan_detail.inspection_time is '巡查日期';
comment on column tb_plan_detail.weather is '天气';
comment on column tb_plan_detail.longitude is '经度';
comment on column tb_plan_detail.latitude is '纬度';
comment on column tb_plan_detail.location is '位置信息';
comment on column tb_plan_detail.status is '0:未完成 1:已完成 2:已超时';
comment on column tb_plan_detail.create_time is '创建日期';
comment on column tb_plan_detail.modify_time is '修改日期';


/*==============================================================*/
/* Table: tb_inspection_disease_instance                        */
/*==============================================================*/
drop table if exists tb_inspection_disease_instance;
create table tb_inspection_disease_instance (
   id                   SERIAL not null,
   plan_detail_id       INT4                 null,
   inspection_disease_id INT4                 null,
   quantity             INT2                 null,
   strategy             INT2                 null,
   exception_part       VARCHAR(64)          null,
   exception_type       INT2                 null,
   remarks              VARCHAR(256)         null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_INSPECTION_DISEASE_INSTA primary key (id)
);

comment on table tb_inspection_disease_instance is '巡检病害实例表';
comment on column tb_inspection_disease_instance.id is '主键id';
comment on column tb_inspection_disease_instance.plan_detail_id is '计划详情id';
comment on column tb_inspection_disease_instance.inspection_disease_id is '巡检病害id';
comment on column tb_inspection_disease_instance.quantity is '病害数量';
comment on column tb_inspection_disease_instance.strategy is '1:观察 2:报修 3:监测 4:即修 5:更换 6:增设 7.跟踪监测 8.维修处置 9.定期或专项检查';
comment on column tb_inspection_disease_instance.exception_part is '异常部位';
comment on column tb_inspection_disease_instance.exception_type is '判定';
comment on column tb_inspection_disease_instance.remarks is '备注';
comment on column tb_inspection_disease_instance.status is '0:未加入维养计划 1:待修 2:已修';
comment on column tb_inspection_disease_instance.create_time is '创建时间';
comment on column tb_inspection_disease_instance.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_inspection_disease                                 */
/*==============================================================*/
drop table if exists tb_inspection_disease;
create table tb_inspection_disease (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   disease_part         VARCHAR(32)          null,
   check_item           VARCHAR(32)          null,
   damage_type          VARCHAR(32)          null,
   unit                 VARCHAR(8)           null,
   structure_type       INT2                 null,
   type                 INT2                 null,
   constraint PK_TB_INSPECTION_DISEASE primary key (id)
);

comment on table tb_inspection_disease is '巡检病害表';
comment on column tb_inspection_disease.id is '主键id';
comment on column tb_inspection_disease.name is '病害名称';
comment on column tb_inspection_disease.disease_part is '病害部位';
comment on column tb_inspection_disease.check_item is '检查项';
comment on column tb_inspection_disease.damage_type is '损害类型';
comment on column tb_inspection_disease.unit is '单位';
comment on column tb_inspection_disease.structure_type is '1:桥梁病害 2:隧道病害';
comment on column tb_inspection_disease.type is '1:常规 2:多选 3:输入框';


/*==============================================================*/
/* Table: tb_bridge_type_disease_rel                            */
/*==============================================================*/
drop table if exists tb_bridge_type_disease_rel;
create table tb_bridge_type_disease_rel (
   id                   SERIAL not null,
   inspection_disease_id INT4                 null,
   bridge_type_id       INT4                 null,
   seq_num              INT4                 null,
   constraint PK_TB_BRIDGE_TYPE_DISEASE_REL primary key (id)
);

comment on table tb_bridge_type_disease_rel is '结构物类型巡检病害关联表';
comment on column tb_bridge_type_disease_rel.id is '主键id';
comment on column tb_bridge_type_disease_rel.inspection_disease_id is '巡检病害表id';
comment on column tb_bridge_type_disease_rel.bridge_type_id is '桥梁类型表id';
comment on column tb_bridge_type_disease_rel.seq_num is '顺序';


/*==============================================================*/
/* Table: tb_inspection_disease_option                          */
/*==============================================================*/
drop table if exists tb_inspection_disease_option;
create table tb_inspection_disease_option (
   id                   SERIAL not null,
   inspection_disease_id INT4                 null,
   name                 VARCHAR(32)          null,
   seq_num              INT2                 null,
   option_status        INT2                 null,
   constraint PK_TB_INSPECTION_DISEASE_OPTIO primary key (id)
);

comment on table tb_inspection_disease_option is '巡检病害选项表';
comment on column tb_inspection_disease_option.id is '主键id';
comment on column tb_inspection_disease_option.inspection_disease_id is '巡检病害id';
comment on column tb_inspection_disease_option.name is '名称';
comment on column tb_inspection_disease_option.seq_num is '排序,下标从1开始';
comment on column tb_inspection_disease_option.option_status is '0:病害 1:完好';


/*==============================================================*/
/* Table: tb_disease_instance_option_rel                        */
/*==============================================================*/
drop table if exists tb_disease_instance_option_rel;
create table tb_disease_instance_option_rel (
   id                   SERIAL not null,
   inspection_disease_instance_id INT4                 not null,
   inspection_disease_option_id INT4                 null,
   constraint PK_TB_DISEASE_INSTANCE_OPTION_ primary key (id)
);

comment on table tb_disease_instance_option_rel is '巡查病害实例选项关联表';
comment on column tb_disease_instance_option_rel.id is '主键id';
comment on column tb_disease_instance_option_rel.inspection_disease_instance_id is '病害实例表id';
comment on column tb_disease_instance_option_rel.inspection_disease_option_id is '病害选项表id';


/*==============================================================*/
/* Table: tb_maintain_item                                      */
/*==============================================================*/
drop table if exists tb_maintain_item;
create table tb_maintain_item (
   id                   SERIAL not null,
   plan_id              INT4                 null,
   structure_id         INT4                 null,
   disease_instance_id  INT4                 null,
   name                 VARCHAR(32)          null,
   type                 INT2                 null,
   proposed_time        TIMESTAMP            null,
   maintain_time        TIMESTAMP            null,
   creator              VARCHAR(32)          null,
   content              VARCHAR(256)         null,
   method               VARCHAR(256)         null,
   result               VARCHAR(256)         null,
   maintenance_unit     VARCHAR(32)          null,
   quantities           VARCHAR(32)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_MAINTAIN_ITEM primary key (id)
);

comment on table tb_maintain_item is '维修项表';
comment on column tb_maintain_item.id is '主键id';
comment on column tb_maintain_item.plan_id is '计划id';
comment on column tb_maintain_item.structure_id is '结构物id';
comment on column tb_maintain_item.disease_instance_id is '病害实例id';
comment on column tb_maintain_item.name is '维修项名称';
comment on column tb_maintain_item.type is '0:日常保养 1:小修 2:中修 3:大修';
comment on column tb_maintain_item.proposed_time is '拟定日期';
comment on column tb_maintain_item.maintain_time is '維修日期';
comment on column tb_maintain_item.creator is '维修人员';
comment on column tb_maintain_item.content is '维修内容';
comment on column tb_maintain_item.method is '维修方法';
comment on column tb_maintain_item.result is '维修结果';
comment on column tb_maintain_item.maintenance_unit is '维修单位';
comment on column tb_maintain_item.quantities is '工程量';
comment on column tb_maintain_item.status is '0:未完成 1:已完成 2:已超时';
comment on column tb_maintain_item.create_time is '创建日期';
comment on column tb_maintain_item.modify_time is '修改日期';

-----------------------------------
--检测评估模块
-----------------------------------

/*==============================================================*/
/* Table: tb_monitor_plan                                       */
/*==============================================================*/
drop table if exists tb_monitor_plan;
create table tb_monitor_plan (
   id                   SERIAL not null,
   type                 INT2                 null,
   project_info_id      INT4                 null,
   start_time           TIMESTAMP            null,
   end_time             TIMESTAMP            null,
   upload               INT2                 null,
   create_user_id       INT4                 null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_MONITOR_PLAN primary key (id)
);

comment on table tb_monitor_plan is '监测计划';
comment on column tb_monitor_plan.id is 'id主键';
comment on column tb_monitor_plan.type is '监测类型1：定期监测 2：特殊检测 3：静载实验';
comment on column tb_monitor_plan.project_info_id is '项目id';
comment on column tb_monitor_plan.start_time is '开始日期';
comment on column tb_monitor_plan.end_time is '结束日期';
comment on column tb_monitor_plan.upload is '0未上传 1已上传';
comment on column tb_monitor_plan.create_user_id is '创建者用户id';
comment on column tb_monitor_plan.status is '状态';
comment on column tb_monitor_plan.create_time is '创建日期';
comment on column tb_monitor_plan.modify_time is '修改日期';


/*==============================================================*/
/* Table: tb_monitor_plan_structure_rel                         */
/*==============================================================*/
drop table if exists tb_monitor_plan_structure_rel;
create table tb_monitor_plan_structure_rel (
   id                   SERIAL not null,
   monitor_plan_id      INT4                 null,
   structure_info_id    INT4                 null,
   structure_name       VARCHAR(32)          null,
   file_name            VARCHAR(64)          null,
   file_url             VARCHAR(128)         null,
   is_delete            INT2                 null,
   constraint PK_TB_MONITOR_PLAN_STRUCTURE_R primary key (id)
);

comment on table tb_monitor_plan_structure_rel is '监测计划结构物关联表';
comment on column tb_monitor_plan_structure_rel.id is 'id主键';
comment on column tb_monitor_plan_structure_rel.monitor_plan_id is '计划id';
comment on column tb_monitor_plan_structure_rel.structure_info_id is '结构物id';
comment on column tb_monitor_plan_structure_rel.structure_name is '结构物名称';
comment on column tb_monitor_plan_structure_rel.file_name is '方案名称';
comment on column tb_monitor_plan_structure_rel.file_url is '方案url';
comment on column tb_monitor_plan_structure_rel.is_delete is '0:否1:是 (为空也是否)';


/*==============================================================*/
/* Table: tb_monitor_plan_original_record                       */
/*==============================================================*/
drop table if exists tb_monitor_plan_original_record;
create table tb_monitor_plan_original_record (
   id                   SERIAL not null,
   monitor_plan_structure_rel_id INT4                 not null,
   instrumentation      VARCHAR(200)         null,
   project_location     VARCHAR(100)         not null,
   test_basis           VARCHAR(100)         null,
   record_number        VARCHAR(100)         null,
   create_user_id       INT4                 null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_MONITOR_PLAN_ORIGINAL_RE primary key (id)
);

comment on table tb_monitor_plan_original_record is '监测计划原始记录表';
comment on column tb_monitor_plan_original_record.monitor_plan_structure_rel_id is '监测计划结构物关联id';
comment on column tb_monitor_plan_original_record.instrumentation is '检测仪器';
comment on column tb_monitor_plan_original_record.project_location is '工程地点';
comment on column tb_monitor_plan_original_record.test_basis is '检测依据';
comment on column tb_monitor_plan_original_record.record_number is '记录编号';
comment on column tb_monitor_plan_original_record.create_user_id is '创建者id';
comment on column tb_monitor_plan_original_record.status is '状态';
comment on column tb_monitor_plan_original_record.create_time is '创建时间';
comment on column tb_monitor_plan_original_record.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_attachment                                         */
/*==============================================================*/
drop table if exists tb_attachment;
create table tb_attachment (
   id                   SERIAL not null,
   name                 VARCHAR(64)          null,
   path                 VARCHAR(64)          null,
   type                 INT2                 null,
   target_id            INT4                 null,
   part_type            INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   status               INT2                 null,
   constraint PK_TB_ATTACHMENT primary key (id)
);

comment on table tb_attachment is '附件表';
comment on column tb_attachment.id is '主键id';
comment on column tb_attachment.name is '附件名称';
comment on column tb_attachment.path is '附件地址';
comment on column tb_attachment.type is '附件类型 1：线路图片 2：桥垮图片 3：部位图片 4：构件图片 5：病害图片';
comment on column tb_attachment.target_id is '目标id';
comment on column tb_attachment.part_type is '1：桥面系 2：上部结构 3：下部结构';
comment on column tb_attachment.create_time is '创建时间';
comment on column tb_attachment.modify_time is '修改时间';
comment on column tb_attachment.status is '1：正常 0：删除 2:选中';


/*==============================================================*/
/* Table: tb_bridge_road                                        */
/*==============================================================*/
drop table if exists tb_bridge_road;
create table tb_bridge_road (
   id                   SERIAL not null,
   name                 VARCHAR(16)          null,
   monitor_plan_structure_rel_id INT4                 null,
   bridge_type_id       INT4                 null,
   span_number          INT4                 null,
   main_span_number     INT4                 null,
   stairway_number      INT4                 null,
   stairway_span_number INT4                 null,
   report_path          VARCHAR(256)         null,
   creator              VARCHAR(16)          null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   status               INT2                 null,
   constraint PK_TB_BRIDGE_ROAD primary key (id)
);

comment on table tb_bridge_road is '桥梁线路表';
comment on column tb_bridge_road.id is '主键id';
comment on column tb_bridge_road.name is '线路名称';
comment on column tb_bridge_road.monitor_plan_structure_rel_id is '计划结构物id';
comment on column tb_bridge_road.bridge_type_id is '桥梁类型id';
comment on column tb_bridge_road.span_number is '桥跨数量';
comment on column tb_bridge_road.main_span_number is '主跨数量';
comment on column tb_bridge_road.stairway_number is '梯道数';
comment on column tb_bridge_road.stairway_span_number is '梯道跨数';
comment on column tb_bridge_road.report_path is '报告路径';
comment on column tb_bridge_road.creator is '创建者';
comment on column tb_bridge_road.create_time is '创建时间';
comment on column tb_bridge_road.modify_time is '修改时间';
comment on column tb_bridge_road.status is '1：正常 0：被回收';


/*==============================================================*/
/* Table: tb_bridge_span                                        */
/*==============================================================*/
drop table if exists tb_bridge_span;
create table tb_bridge_span (
   id                   SERIAL not null,
   bridge_road_id       INT4                 null,
   bridge_shape         INT2                 null,
   span_code            VARCHAR(16)          null,
   length               FLOAT8               null,
   outer_arc_length     FLOAT8               null,
   inner_arc_length     FLOAT8               null,
   convex               INT2                 null,
   creator              VARCHAR(16)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_BRIDGE_SPAN primary key (id)
);

comment on table tb_bridge_span is '桥跨表';
comment on column tb_bridge_span.id is '主键id';
comment on column tb_bridge_span.bridge_road_id is '线路id';
comment on column tb_bridge_span.bridge_shape is '桥梁形状';
comment on column tb_bridge_span.span_code is '桥跨编号';
comment on column tb_bridge_span.length is '桥跨长度';
comment on column tb_bridge_span.outer_arc_length is '外弧长';
comment on column tb_bridge_span.inner_arc_length is '内弧长';
comment on column tb_bridge_span.convex is '0：凹  1：凸';
comment on column tb_bridge_span.creator is '创建者';
comment on column tb_bridge_span.status is '1：正常 0：回收';
comment on column tb_bridge_span.create_time is '创建时间';
comment on column tb_bridge_span.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_bridge_deck_system                                 */
/*==============================================================*/
drop table if exists tb_bridge_deck_system;
create table tb_bridge_deck_system (
   id                   SERIAL not null,
   bridge_span_id       INT4                 null,
   direction            VARCHAR(8)           null,
   tilt_angle           FLOAT8               null,
   sidewalk             INT2                 null,
   sidewalk_width       FLOAT8               null,
   left_sidewalk_width  FLOAT8               null,
   right_sidewalk_width FLOAT8               null,
   lane_width           FLOAT8               null,
   midle_barrier_width  FLOAT8               null,
   left_rail_width      FLOAT8               null,
   right_rail_width     FLOAT8               null,
   stairway             VARCHAR(32)          null,
   stairway_shadow_length FLOAT8               null,
   stairway_length      FLOAT8               null,
   stairway_width       FLOAT8               null,
   platform_number      INT4                 null,
   expansion_joint      INT2                 null,
   bridge_head_board_length FLOAT8               null,
   monitor_diagram      VARCHAR(64)          null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   creator              VARCHAR(32)          null,
   status               INT2                 null,
   constraint PK_TB_BRIDGE_DECK_SYSTEM primary key (id)
);

comment on table tb_bridge_deck_system is '桥面系信息表';
comment on column tb_bridge_deck_system.id is '主键id';
comment on column tb_bridge_deck_system.bridge_span_id is '桥跨id';
comment on column tb_bridge_deck_system.direction is '桥梁走向 ：东 南 西 北 东南 西南 东北 西北';
comment on column tb_bridge_deck_system.tilt_angle is '倾斜角度';
comment on column tb_bridge_deck_system.sidewalk is '有无人行道';
comment on column tb_bridge_deck_system.sidewalk_width is '人行道宽';
comment on column tb_bridge_deck_system.left_sidewalk_width is '人行道宽(左)';
comment on column tb_bridge_deck_system.right_sidewalk_width is '人行道宽(右)';
comment on column tb_bridge_deck_system.lane_width is '行车道宽';
comment on column tb_bridge_deck_system.midle_barrier_width is '中间隔离栏宽度';
comment on column tb_bridge_deck_system.left_rail_width is '护栏(左)';
comment on column tb_bridge_deck_system.right_rail_width is '护栏(右)';
comment on column tb_bridge_deck_system.stairway is '梯道';
comment on column tb_bridge_deck_system.stairway_shadow_length is '梯道长(投影)';
comment on column tb_bridge_deck_system.stairway_length is '梯道长';
comment on column tb_bridge_deck_system.stairway_width is '梯道宽';
comment on column tb_bridge_deck_system.platform_number is '梯台数';
comment on column tb_bridge_deck_system.expansion_joint is '0：无 1：有';
comment on column tb_bridge_deck_system.bridge_head_board_length is '桥头搭板长';
comment on column tb_bridge_deck_system.monitor_diagram is '模型地址';
comment on column tb_bridge_deck_system.create_time is '创建时间';
comment on column tb_bridge_deck_system.modify_time is '修改时间';
comment on column tb_bridge_deck_system.creator is '创建者';
comment on column tb_bridge_deck_system.status is '1：正常 0：回收';


/*==============================================================*/
/* Table: tb_bridge_supstructure                                */
/*==============================================================*/
drop table if exists tb_bridge_supstructure;
create table tb_bridge_supstructure (
   id                   SERIAL not null,
   bridge_span_id       INT4                 null,
   beam_type            INT2                 null,
   beam_number          INT4                 null,
   hanging_beam         INT4                 null,
   hanging_beam_support INT4                 null,
   anti_falling_beam    INT4                 null,
   truss                INT4                 null,
   primary_node         INT4                 null,
   stringer             INT4                 null,
   cross_beam           INT4                 null,
   connector            INT4                 null,
   arch_ring            INT4                 null,
   horizonta_connection INT4                 null,
   arch_structure       INT4                 null,
   exterior_trim_panel  INT4                 null,
   monitor_diagram      VARCHAR(64)          null,
   creator              VARCHAR(32)          null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   status               INT2                 null,
   constraint PK_TB_BRIDGE_SUPSTRUCTURE primary key (id)
);

comment on table tb_bridge_supstructure is '桥梁上部结构';
comment on column tb_bridge_supstructure.id is 'id';
comment on column tb_bridge_supstructure.bridge_span_id is '桥跨id';
comment on column tb_bridge_supstructure.beam_type is '梁类型';
comment on column tb_bridge_supstructure.beam_number is '梁数量';
comment on column tb_bridge_supstructure.hanging_beam is '挂梁数';
comment on column tb_bridge_supstructure.hanging_beam_support is '挂梁支座数';
comment on column tb_bridge_supstructure.anti_falling_beam is '防落梁装置数';
comment on column tb_bridge_supstructure.truss is '桁片数';
comment on column tb_bridge_supstructure.primary_node is '主节点数';
comment on column tb_bridge_supstructure.stringer is '纵梁数';
comment on column tb_bridge_supstructure.cross_beam is '横梁数';
comment on column tb_bridge_supstructure.connector is '连接件数';
comment on column tb_bridge_supstructure.arch_ring is '主拱圈数';
comment on column tb_bridge_supstructure.horizonta_connection is '横向联系数';
comment on column tb_bridge_supstructure.arch_structure is '拱上构造数';
comment on column tb_bridge_supstructure.exterior_trim_panel is '外部装饰板数';
comment on column tb_bridge_supstructure.monitor_diagram is '模型地址';
comment on column tb_bridge_supstructure.creator is '创建者';
comment on column tb_bridge_supstructure.create_time is '创建时间';
comment on column tb_bridge_supstructure.modify_time is '修改时间';
comment on column tb_bridge_supstructure.status is '1：正常 0：回收';


/*==============================================================*/
/* Table: tb_bridge_substructure                                */
/*==============================================================*/
drop table if exists tb_bridge_substructure;
create table tb_bridge_substructure (
   id                   SERIAL not null,
   bridge_span_id       INT4                 null,
   single_row_dun       INT4                 null,
   single_row_support   INT4                 null,
   arch_foot            INT4                 null,
   exterior_trim_panel  INT4                 null,
   monitor_diagram      VARCHAR(64)          null,
   creator              VARCHAR(32)          null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   status               INT2                 null,
   constraint PK_TB_BRIDGE_SUBSTRUCTURE primary key (id)
);

comment on table tb_bridge_substructure is '桥梁下部结构';
comment on column tb_bridge_substructure.id is '主键id';
comment on column tb_bridge_substructure.bridge_span_id is '桥跨id';
comment on column tb_bridge_substructure.single_row_dun is '单排敦数';
comment on column tb_bridge_substructure.single_row_support is '单排支座数';
comment on column tb_bridge_substructure.arch_foot is '拱脚数';
comment on column tb_bridge_substructure.exterior_trim_panel is '外部装饰板数';
comment on column tb_bridge_substructure.monitor_diagram is '模型地址';
comment on column tb_bridge_substructure.creator is '创建者';
comment on column tb_bridge_substructure.create_time is '创建时间';
comment on column tb_bridge_substructure.modify_time is '修改时间';
comment on column tb_bridge_substructure.status is '1：正常 0：回收';


/*==============================================================*/
/* Table: tb_bridge_other_structure                             */
/*==============================================================*/
drop table if exists tb_bridge_other_structure;
create table tb_bridge_other_structure (
   id                   SERIAL not null,
   bridge_span_id       INT4                 null,
   descript             VARCHAR(512)         null,
   creator              VARCHAR(32)          null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   status               INT2                 null,
   constraint PK_TB_BRIDGE_OTHER_STRUCTURE primary key (id)
);

comment on column tb_bridge_other_structure.id is 'id主键';
comment on column tb_bridge_other_structure.bridge_span_id is '桥跨id';
comment on column tb_bridge_other_structure.descript is '描述';
comment on column tb_bridge_other_structure.creator is '创建者';
comment on column tb_bridge_other_structure.create_time is '创建时间';
comment on column tb_bridge_other_structure.modify_time is '修改时间';
comment on column tb_bridge_other_structure.status is '1：正常 0：回收';


/*==============================================================*/
/* Table: tb_bridge_deck_component                              */
/*==============================================================*/
drop table if exists tb_bridge_deck_component;
create table tb_bridge_deck_component (
   id                   SERIAL not null,
   bridge_deck_system_id INT4                 null,
   component_id         INT4                 null,
   code                 VARCHAR(32)          null,
   initial_weight       FLOAT8               null,
   actual_weight        FLOAT8               null,
   deduct               FLOAT8               null,
   serious              INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   creator              VARCHAR(32)          null,
   status               INT2                 null,
   constraint PK_TB_BRIDGE_DECK_COMPONENT primary key (id)
);

comment on table tb_bridge_deck_component is '桥面系构件实例';
comment on column tb_bridge_deck_component.id is '主键id';
comment on column tb_bridge_deck_component.bridge_deck_system_id is '桥梁桥面系id';
comment on column tb_bridge_deck_component.component_id is '构件类型id';
comment on column tb_bridge_deck_component.code is '结构编号';
comment on column tb_bridge_deck_component.initial_weight is '原始权重';
comment on column tb_bridge_deck_component.actual_weight is '实际权重';
comment on column tb_bridge_deck_component.deduct is '扣分';
comment on column tb_bridge_deck_component.serious is '病害是否带*(0：不带 1：带*)';
comment on column tb_bridge_deck_component.create_time is '创建时间';
comment on column tb_bridge_deck_component.modify_time is '修改时间';
comment on column tb_bridge_deck_component.creator is '创建者';
comment on column tb_bridge_deck_component.status is '1：正常 0：回收';


/*==============================================================*/
/* Table: tb_bridge_supcomponent                                */
/*==============================================================*/
drop table if exists tb_bridge_supcomponent;
create table tb_bridge_supcomponent (
   id                   SERIAL not null,
   supstructure_id      INT4                 null,
   component_id         INT4                 null,
   code                 VARCHAR(32)          null,
   initial_weight       FLOAT8               null,
   actual_weight        FLOAT8               null,
   deduct               FLOAT8               null,
   web_plate_hight      FLOAT8               null,
   wing_plate_width     FLOAT8               null,
   baseplate_width      FLOAT8               null,
   diaphragms_height    FLOAT8               null,
   diaphragms_number    INT4                 null,
   cantilever_lenght    FLOAT8               null,
   hanging_beam_length  FLOAT8               null,
   serious              INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   creator              VARCHAR(32)          null,
   status               INT2                 null,
   constraint PK_TB_BRIDGE_SUPCOMPONENT primary key (id)
);

comment on table tb_bridge_supcomponent is '桥梁上部构件实例';
comment on column tb_bridge_supcomponent.id is '主键id';
comment on column tb_bridge_supcomponent.supstructure_id is '桥梁上部结构id';
comment on column tb_bridge_supcomponent.component_id is '构件类型id';
comment on column tb_bridge_supcomponent.code is '结构编号';
comment on column tb_bridge_supcomponent.initial_weight is '原始权重';
comment on column tb_bridge_supcomponent.actual_weight is '实际权重';
comment on column tb_bridge_supcomponent.deduct is '扣分';
comment on column tb_bridge_supcomponent.web_plate_hight is '腹板高度';
comment on column tb_bridge_supcomponent.wing_plate_width is '翼板宽度';
comment on column tb_bridge_supcomponent.baseplate_width is '底板宽度';
comment on column tb_bridge_supcomponent.diaphragms_height is '横隔板高度';
comment on column tb_bridge_supcomponent.diaphragms_number is '横隔板道数';
comment on column tb_bridge_supcomponent.cantilever_lenght is '悬臂长度';
comment on column tb_bridge_supcomponent.hanging_beam_length is '挂梁长度';
comment on column tb_bridge_supcomponent.serious is '病害是否带*(0：不带 1：带*)';
comment on column tb_bridge_supcomponent.create_time is '创建时间';
comment on column tb_bridge_supcomponent.modify_time is '修改时间';
comment on column tb_bridge_supcomponent.creator is '创建者';
comment on column tb_bridge_supcomponent.status is '1：正常 0：回收';


/*==============================================================*/
/* Table: tb_bridge_subcomponent                                */
/*==============================================================*/
drop table if exists tb_bridge_subcomponent;
create table tb_bridge_subcomponent (
   id                   SERIAL not null,
   bridge_substructure_id INT4                 null,
   component_id         INT4                 null,
   code                 VARCHAR(32)          null,
   initial_weight       FLOAT8               null,
   actual_weight        FLOAT8               null,
   deduct               FLOAT8               null,
   cap_beams_height     FLOAT8               null,
   cap_beams_width      FLOAT8               null,
   cap_beams_thick      FLOAT8               null,
   abutment_height      FLOAT8               null,
   abutment_width       FLOAT8               null,
   abutment_thick       FLOAT8               null,
   abutment_cap_height  FLOAT8               null,
   pier_shape           INT2                 null,
   pier_height          FLOAT8               null,
   pier_width           FLOAT8               null,
   pier_thick           FLOAT8               null,
   pier_radius          FLOAT8               null,
   is_abutment          INT2                 null,
   pier_abutment_id     INT4                 null,
   serious              INT2                 null,
   creator              VARCHAR(32)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_BRIDGE_SUBCOMPONENT primary key (id)
);

comment on table tb_bridge_subcomponent is '桥梁下部构件实例';
comment on column tb_bridge_subcomponent.id is '主键id';
comment on column tb_bridge_subcomponent.bridge_substructure_id is '桥梁下部结构id';
comment on column tb_bridge_subcomponent.component_id is '构件类型id';
comment on column tb_bridge_subcomponent.code is '结构编号';
comment on column tb_bridge_subcomponent.initial_weight is '原始权重';
comment on column tb_bridge_subcomponent.actual_weight is '实际权重';
comment on column tb_bridge_subcomponent.deduct is '扣分';
comment on column tb_bridge_subcomponent.cap_beams_height is '盖梁高度';
comment on column tb_bridge_subcomponent.cap_beams_width is '盖梁宽度';
comment on column tb_bridge_subcomponent.cap_beams_thick is '盖梁厚度';
comment on column tb_bridge_subcomponent.abutment_height is '台身高度';
comment on column tb_bridge_subcomponent.abutment_width is '台身宽度';
comment on column tb_bridge_subcomponent.abutment_thick is '台身厚度';
comment on column tb_bridge_subcomponent.abutment_cap_height is '台帽高度';
comment on column tb_bridge_subcomponent.pier_shape is '墩身形状 1:矩形 2:原型';
comment on column tb_bridge_subcomponent.pier_height is '墩身高度';
comment on column tb_bridge_subcomponent.pier_width is '墩身宽度';
comment on column tb_bridge_subcomponent.pier_thick is '墩身厚度';
comment on column tb_bridge_subcomponent.pier_radius is '墩身半径';
comment on column tb_bridge_subcomponent.is_abutment is '有无桥台 1:有 0:没有';
comment on column tb_bridge_subcomponent.pier_abutment_id is '墩台id';
comment on column tb_bridge_subcomponent.serious is '病害是否带*(0：不带 1：带*)';
comment on column tb_bridge_subcomponent.creator is '创建者';
comment on column tb_bridge_subcomponent.status is '1：正常 0：被删除';
comment on column tb_bridge_subcomponent.create_time is '创建时间';
comment on column tb_bridge_subcomponent.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_disease_instance                                   */
/*==============================================================*/
drop table if exists tb_disease_instance;
create table tb_disease_instance (
   id                   SERIAL not null,
   part_type            INT2                 null,
   target_id            INT4                 null,
   disease_id           INT4                 null,
   length               FLOAT8               null,
   width                FLOAT8               null,
   depth                FLOAT8               null,
   seam_length          FLOAT8               null,
   seam_width           FLOAT8               null,
   angle                FLOAT8               null,
   degree               VARCHAR(16)          null,
   x_axis               FLOAT8               null,
   y_axis               FLOAT8               null,
   remark               VARCHAR(64)          null,
   height_difference    FLOAT8               null,
   deflection_value     FLOAT8               null,
   trend                VARCHAR(16)          null,
   number               FLOAT8               null,
   crack_axis           TEXT                 null,
   deduct               FLOAT8               null,
   sort                 INT4                 null,
   creator              VARCHAR(32)          null,
   is_delete            INT2                 null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_DISEASE_INSTANCE primary key (id)
);

comment on table tb_disease_instance is '桥梁检测病害实例';
comment on column tb_disease_instance.id is '主键id';
comment on column tb_disease_instance.part_type is '1：桥面系构件 2：上部构件 3：下部构件';
comment on column tb_disease_instance.target_id is '构件实例id';
comment on column tb_disease_instance.disease_id is '病害id';
comment on column tb_disease_instance.length is '长度';
comment on column tb_disease_instance.width is '宽度';
comment on column tb_disease_instance.depth is '深度';
comment on column tb_disease_instance.seam_length is '缝长';
comment on column tb_disease_instance.seam_width is '缝宽';
comment on column tb_disease_instance.angle is '角度';
comment on column tb_disease_instance.degree is '程度';
comment on column tb_disease_instance.x_axis is 'X坐标';
comment on column tb_disease_instance.y_axis is 'Y坐标';
comment on column tb_disease_instance.remark is '备注';
comment on column tb_disease_instance.height_difference is '高度差';
comment on column tb_disease_instance.deflection_value is '下挠值';
comment on column tb_disease_instance.trend is '走向';
comment on column tb_disease_instance.number is '数量';
comment on column tb_disease_instance.crack_axis is '裂缝坐标';
comment on column tb_disease_instance.deduct is '扣分值';
comment on column tb_disease_instance.sort is '序号';
comment on column tb_disease_instance.creator is '创建者';
comment on column tb_disease_instance.is_delete is '是否删除操作';
comment on column tb_disease_instance.status is '1：正常 0：回收';
comment on column tb_disease_instance.create_time is '创建时间';
comment on column tb_disease_instance.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_disease_instance_record                            */
/*==============================================================*/
drop table if exists tb_disease_instance_record;
create table tb_disease_instance_record (
   id                   SERIAL not null,
   tdi_id               INT4                 not null,
   part_type            INT2                 null,
   target_id            INT4                 null,
   disease_id           INT4                 null,
   length               FLOAT8               null,
   width                FLOAT8               null,
   depth                FLOAT8               null,
   seam_length          FLOAT8               null,
   seam_width           FLOAT8               null,
   angle                FLOAT8               null,
   degree               VARCHAR(16)          null,
   x_axis               FLOAT8               null,
   y_axis               FLOAT8               null,
   remark               VARCHAR(64)          null,
   height_difference    FLOAT8               null,
   deflection_value     FLOAT8               null,
   trend                INT2                 null,
   number               FLOAT8               null,
   deduct               FLOAT8               null,
   sort                 INT4                 null,
   crack_axis           TEXT                 null,
   creator              VARCHAR(32)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_DISEASE_INSTANCE_RECORD primary key (id)
);

comment on table tb_disease_instance_record is '桥梁检测病害实例记录表';
comment on column tb_disease_instance_record.id is '主键id';
comment on column tb_disease_instance_record.tdi_id is '桥梁检测病害实例id';
comment on column tb_disease_instance_record.part_type is '1：桥面系构件 2：上部构件 3：下部构件';
comment on column tb_disease_instance_record.target_id is '构件实例id';
comment on column tb_disease_instance_record.disease_id is '病害id';
comment on column tb_disease_instance_record.length is '长度';
comment on column tb_disease_instance_record.width is '宽度';
comment on column tb_disease_instance_record.depth is '深度';
comment on column tb_disease_instance_record.seam_length is '缝长';
comment on column tb_disease_instance_record.seam_width is '缝宽';
comment on column tb_disease_instance_record.angle is '角度';
comment on column tb_disease_instance_record.degree is '程度';
comment on column tb_disease_instance_record.x_axis is 'X坐标';
comment on column tb_disease_instance_record.y_axis is 'Y坐标';
comment on column tb_disease_instance_record.remark is '备注';
comment on column tb_disease_instance_record.height_difference is '高度差';
comment on column tb_disease_instance_record.deflection_value is '下挠值';
comment on column tb_disease_instance_record.trend is '走向';
comment on column tb_disease_instance_record.number is '数量';
comment on column tb_disease_instance_record.deduct is '扣分值';
comment on column tb_disease_instance_record.sort is '序号';
comment on column tb_disease_instance_record.crack_axis is '裂缝坐标';
comment on column tb_disease_instance_record.creator is '创建者';
comment on column tb_disease_instance_record.status is '1：正常 0：回收';
comment on column tb_disease_instance_record.create_time is '创建时间';
comment on column tb_disease_instance_record.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_bridge_score                                       */
/*==============================================================*/
drop table if exists tb_bridge_score;
create table tb_bridge_score (
   id                   SERIAL not null,
   target_id            INT4                 null,
   type                 INT2                 null,
   bridge_condition_index FLOAT8               null,
   bridge_structure_index FLOAT8               null,
   rating_level         VARCHAR(8)           null,
   evaluation_unit      VARCHAR(32)          null,
   evaluation_time      TIMESTAMP            null,
   evaluator_id         INT4                 null,
   score                FLOAT8               null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   status               INT2                 null,
   constraint PK_TB_BRIDGE_SCORE primary key (id)
);

comment on table tb_bridge_score is '桥梁评分表';
comment on column tb_bridge_score.id is '主键id';
comment on column tb_bridge_score.target_id is '目标id';
comment on column tb_bridge_score.type is '1：结构物评分 2：线路评分 3：桥面系 4：上部结构 5：下部结构 6：墩台评分 7：要素 8：跨';
comment on column tb_bridge_score.bridge_condition_index is 'BCI分数';
comment on column tb_bridge_score.bridge_structure_index is 'BSI分数';
comment on column tb_bridge_score.rating_level is '评定等级';
comment on column tb_bridge_score.evaluation_unit is '评价单位';
comment on column tb_bridge_score.evaluation_time is '评价时间';
comment on column tb_bridge_score.evaluator_id is '评价人员id（对应用户id）';
comment on column tb_bridge_score.score is '评分';
comment on column tb_bridge_score.create_time is '创建时间';
comment on column tb_bridge_score.modify_time is '修改时间';
comment on column tb_bridge_score.status is '状态';


/*==============================================================*/
/* Table: tb_pier_abutment                                      */
/*==============================================================*/
drop table if exists tb_pier_abutment;
create table tb_pier_abutment (
   id                   SERIAL not null,
   bridge_substructure_id INT4                 null,
   code                 VARCHAR(16)          null,
   constraint PK_TB_PIER_ABUTMENT primary key (id)
);

comment on table tb_pier_abutment is '墩台表';
comment on column tb_pier_abutment.id is 'id主键';
comment on column tb_pier_abutment.bridge_substructure_id is '所属下部结构id';
comment on column tb_pier_abutment.code is '墩台号';


/*==============================================================*/
/* Table: tb_bridge_weight                                      */
/*==============================================================*/
drop table if exists tb_bridge_weight;
create table tb_bridge_weight (
   id                   SERIAL not null,
   bridge_type_id       INT4                 not null,
   part_type            INT2                 not null,
   weight               FLOAT8               not null,
   constraint PK_TB_BRIDGE_WEIGHT primary key (id)
);

comment on table tb_bridge_weight is '桥梁部位权重表';
comment on column tb_bridge_weight.bridge_type_id is '桥梁类型id';
comment on column tb_bridge_weight.part_type is '1：桥面系 2：上部结构 3：下部结构';
comment on column tb_bridge_weight.weight is '权重';


drop table if exists tb_bridge_type;

/*==============================================================*/
/* Table: tb_bridge_type                                        */
/*==============================================================*/
create table tb_bridge_type (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   constraint PK_TB_BRIDGE_TYPE primary key (id)
);

comment on table tb_bridge_type is '桥梁类型表';
comment on column tb_bridge_type.id is '主键id';
comment on column tb_bridge_type.name is '名称';


/*==============================================================*/
/* Table: tb_bridge_type_component_rel                          */
/*==============================================================*/
drop table if exists tb_bridge_type_component_rel;
create table tb_bridge_type_component_rel (
   id                   SERIAL not null,
   bridge_type_id       INT4                 null,
   component_id         INT4                 null,
   part_type            INT2                 null,
   pier_abutment        INT2                 null,
   initial_weight       FLOAT8               null,
   constraint PK_TB_BRIDGE_TYPE_COMPONENT_RE primary key (id)
);

comment on table tb_bridge_type_component_rel is '桥梁类型构件关联表';
comment on column tb_bridge_type_component_rel.id is '主键id';
comment on column tb_bridge_type_component_rel.bridge_type_id is '桥梁类型id';
comment on column tb_bridge_type_component_rel.component_id is '构件id';
comment on column tb_bridge_type_component_rel.part_type is '1：桥面系 2：上部结构 3：下部结构';
comment on column tb_bridge_type_component_rel.pier_abutment is '0：桥墩  1：桥台';
comment on column tb_bridge_type_component_rel.initial_weight is '原始权重';


/*==============================================================*/
/* Table: tb_component                                          */
/*==============================================================*/
drop table if exists tb_component;
create table tb_component (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   need_judge           INT2                 null,
   constraint PK_TB_COMPONENT primary key (id)
);

comment on table tb_component is '桥梁构件类型表';
comment on column tb_component.id is '主键id';
comment on column tb_component.name is '构件名称';
comment on column tb_component.need_judge is '1：首次添加需判断是否创建 0：首次添加无需判断';


/*==============================================================*/
/* Table: tb_component_disease_rel                              */
/*==============================================================*/
drop table if exists tb_component_disease_rel;
create table tb_component_disease_rel (
   id                   SERIAL not null,
   component_id         INT4                 null,
   bridge_type_id       INT4                 null,
   part_type            INT2                 null,
   disease_id           INT4                 null,
   groups               INT4                 null,
   constraint PK_TB_COMPONENT_DISEASE_REL primary key (id)
);

comment on table tb_component_disease_rel is '构件病害关联表';
comment on column tb_component_disease_rel.id is '主键id';
comment on column tb_component_disease_rel.component_id is '桥梁构件_id';
comment on column tb_component_disease_rel.bridge_type_id is '桥梁类型id';
comment on column tb_component_disease_rel.part_type is '部位类型';
comment on column tb_component_disease_rel.disease_id is '病害id';
comment on column tb_component_disease_rel.groups is '属性组组别';


/*==============================================================*/
/* Table: tb_bridge_disease                                     */
/*==============================================================*/
drop table if exists tb_bridge_disease;
create table tb_bridge_disease (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   code                 VARCHAR(8)           null,
   constraint PK_TB_BRIDGE_DISEASE primary key (id)
);

comment on table tb_bridge_disease is '桥梁病害表';
comment on column tb_bridge_disease.id is '主键id';
comment on column tb_bridge_disease.name is '病害名称';
comment on column tb_bridge_disease.code is '病害编码缩写';


/*==============================================================*/
/* Table: tb_deduction                                          */
/*==============================================================*/
drop table if exists tb_deduction;
create table tb_deduction (
   id                   SERIAL not null,
   component_id         INT4                 null,
   disease_id           INT4                 null,
   degress              VARCHAR(16)          null,
   lower                INT4                 null,
   upper                INT4                 null,
   value                INT4                 null,
   type                 INT2                 null,
   level_limit          INT2                 null,
   constraint PK_TB_DEDUCTION primary key (id)
);

comment on table tb_deduction is '桥梁病害扣分表';
comment on column tb_deduction.id is '主键id';
comment on column tb_deduction.component_id is '构件id';
comment on column tb_deduction.disease_id is '病害id';
comment on column tb_deduction.degress is '程度';
comment on column tb_deduction.upper is '范围上限';
comment on column tb_deduction.value is '扣分值';
comment on column tb_deduction.type is '1：百分比或范围 2：个数 3：字符串';
comment on column tb_deduction.level_limit is '最高等级不得超过D级 0：不限制  1：限制';


/*==============================================================*/
/* Table: tb_property_group                                     */
/*==============================================================*/
drop table if exists tb_property_group;
create table tb_property_group (
   id                   SERIAL not null,
   property_id          INT4                 null,
   groups               INT4                 null,
   accord               INT2                 null,
   constraint PK_TB_PROPERTY_GROUP primary key (id)
);

comment on table tb_property_group is '属性组表';
comment on column tb_property_group.id is '主键id';
comment on column tb_property_group.property_id is '属性id';
comment on column tb_property_group.groups is '组别';
comment on column tb_property_group.accord is '扣分依据';


/*==============================================================*/
/* Table: tb_property_group_option_rel                          */
/*==============================================================*/
drop table if exists tb_property_group_option_rel;
create table tb_property_group_option_rel (
   id                   SERIAL not null,
   property_group_id    INT4                 null,
   property_option_id   INT4                 null,
   component_disease_rel INT4                 null,
   seq_num              INT4                 null,
   constraint PK_TB_PROPERTY_GROUP_OPTION_RE primary key (id)
);

comment on table tb_property_group_option_rel is '属性组选项关联表';
comment on column tb_property_group_option_rel.id is '主键id';
comment on column tb_property_group_option_rel.property_group_id is '属性组id';
comment on column tb_property_group_option_rel.property_option_id is '选项id';
comment on column tb_property_group_option_rel.component_disease_rel is '构件病害关联表id';
comment on column tb_property_group_option_rel.seq_num is '顺序';


/*==============================================================*/
/* Table: tb_property_rel                                       */
/*==============================================================*/
drop table if exists tb_property_rel;
create table tb_property_rel (
   id                   SERIAL not null,
   target_id            INT4                 null,
   property_id          INT4                 null,
   target_type          INT2                 null,
   part_type            INT2                 null,
   bridge_type_id       INT4                 null,
   constraint PK_TB_PROPERTY_REL primary key (id)
);

comment on table tb_property_rel is '属性关联表';
comment on column tb_property_rel.id is '主键id';
comment on column tb_property_rel.target_id is '目标id';
comment on column tb_property_rel.property_id is '属性id';
comment on column tb_property_rel.target_type is '1：部位 2：桥梁构件关联 ';
comment on column tb_property_rel.part_type is '1：桥面系 2：上部结构 3：下部结构';
comment on column tb_property_rel.bridge_type_id is '桥梁类型id';


/*==============================================================*/
/* Table: tb_property                                           */
/*==============================================================*/
drop table if exists tb_property;
create table tb_property (
   id                   SERIAL not null,
   name                 VARCHAR(16)          null,
   english_name         VARCHAR(32)          null,
   unit                 VARCHAR(16)          null,
   type                 INT2                 null,
   constraint PK_TB_PROPERTY primary key (id)
);

comment on table tb_property is '桥属性表';
comment on column tb_property.id is '主键id';
comment on column tb_property.name is '属性名';
comment on column tb_property.english_name is '属性名英文';
comment on column tb_property.unit is '单位';
comment on column tb_property.type is '1：常规属性 2：单选框类型 1代表有，0代表无  3：下拉框类型 4：多选';


/*==============================================================*/
/* Table: tb_option_property_rel                                */
/*==============================================================*/
drop table if exists tb_option_property_rel;
create table tb_option_property_rel (
   id                   SERIAL not null,
   property_option_rel_id INT4                 null,
   property_id          INT4                 null,
   constraint PK_TB_OPTION_PROPERTY_REL primary key (id)
);

comment on table tb_option_property_rel is '选项子属性关联表';
comment on column tb_option_property_rel.id is '主键id';
comment on column tb_option_property_rel.property_option_rel_id is '属性选项关联id';
comment on column tb_option_property_rel.property_id is '属性id';


/*==============================================================*/
/* Table: tb_property_option_rel                                */
/*==============================================================*/
drop table if exists tb_property_option_rel;
create table tb_property_option_rel (
   id                   SERIAL not null,
   property_options_id  INT4                 null,
   property_rel_id      INT4                 null,
   property_id          INT4                 null,
   constraint PK_TB_PROPERTY_OPTION_REL primary key (id)
);

comment on table tb_property_option_rel is '属性选项关联表';
comment on column tb_property_option_rel.id is '主键id';
comment on column tb_property_option_rel.property_options_id is '属性选项id';
comment on column tb_property_option_rel.property_rel_id is '属性关联表id';
comment on column tb_property_option_rel.property_id is '属性表id';


/*==============================================================*/
/* Table: tb_property_option                                    */
/*==============================================================*/
drop table if exists tb_property_option;
create table tb_property_option (
   id                   SERIAL not null,
   option_name          VARCHAR(16)          null,
   constraint PK_TB_PROPERTY_OPTION primary key (id)
);

comment on table tb_property_option is '属性选项表';
comment on column tb_property_option.id is '主键id';
comment on column tb_property_option.option_name is '选项名称';


-----------------------------------
--在线监测模块
-----------------------------------

/*==============================================================*/
/* Table: tb_company                                            */
/*==============================================================*/
drop table if exists tb_company;
create table tb_company (
   id                   SERIAL not null,
   company              VARCHAR(32)          null,
   remarks              VARCHAR(64)          null,
   constraint PK_TB_COMPANY primary key (id)
);

comment on table tb_company is '产商表';
comment on column tb_company.id is '主键id';
comment on column tb_company.company is '产商';
comment on column tb_company.remarks is '备注';


/*==============================================================*/
/* Table: tb_sensor_type                                        */
/*==============================================================*/
drop table if exists tb_sensor_type;
create table tb_sensor_type (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   remarks              VARCHAR(64)          null,
   constraint PK_TB_SENSOR_TYPE primary key (id)
);

comment on table tb_sensor_type is '传感器类型表';
comment on column tb_sensor_type.id is '主键id';
comment on column tb_sensor_type.name is '名称';
comment on column tb_sensor_type.remarks is '备注';


/*==============================================================*/
/* Table: tb_sensor_details                                     */
/*==============================================================*/
drop table if exists tb_sensor_details;
create table tb_sensor_details (
   id                   SERIAL not null,
   detail_name          VARCHAR(16)          null,
   unit                 VARCHAR(16)          null,
   precision            FLOAT4               null,
   constraint PK_TB_SENSOR_DETAILS primary key (id)
);

comment on table tb_sensor_details is '传感器细项表';
comment on column tb_sensor_details.id is '主键id';
comment on column tb_sensor_details.detail_name is '细项名称';
comment on column tb_sensor_details.unit is '单位';
comment on column tb_sensor_details.precision is '默认精度';


/*==============================================================*/
/* Table: tb_sensor_principle                                   */
/*==============================================================*/
drop table if exists tb_sensor_principle;
create table tb_sensor_principle (
   id                   SERIAL not null,
   principle            VARCHAR(16)          null,
   remarks              VARCHAR(64)          null,
   constraint PK_TB_SENSOR_PRINCIPLE primary key (id)
);

comment on table tb_sensor_principle is '传感器原理表';
comment on column tb_sensor_principle.id is '主键id';
comment on column tb_sensor_principle.principle is '原理名称';
comment on column tb_sensor_principle.remarks is '备注';


/*==============================================================*/
/* Table: tb_means_point                                        */
/*==============================================================*/
drop table if exists tb_means_point;
create table tb_means_point (
   id                   SERIAL not null,
   component_id         INT4                 null,
   company_id           INT4                 null,
   structure_id         INT4                 null,
   sensor_type_id       INT4                 null,
   name                 VARCHAR(32)          null,
   describe             VARCHAR(64)          null,
   deployment_date      TIMESTAMP            null,
   section_position     VARCHAR(32)          null,
   collector_information VARCHAR(256)         null,
   x_axis               FLOAT4               null,
   y_axis               FLOAT4               null,
   z_axis               FLOAT4               null,
   sampling_frequency   VARCHAR(16)          null,
   production_date      TIMESTAMP            null,
   production_coding    VARCHAR(64)          null,
   photo_url            VARCHAR(64)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_MEANS_POINT primary key (id)
);

comment on table tb_means_point is '测点信息表';
comment on column tb_means_point.id is '主键id';
comment on column tb_means_point.component_id is '构件id';
comment on column tb_means_point.company_id is '产商id';
comment on column tb_means_point.structure_id is '结构物id';
comment on column tb_means_point.sensor_type_id is '传感器类型id';
comment on column tb_means_point.name is '测点编号';
comment on column tb_means_point.describe is '测点编号说明';
comment on column tb_means_point.deployment_date is '部署日期';
comment on column tb_means_point.section_position is '截面位置';
comment on column tb_means_point.collector_information is '采集器信息';
comment on column tb_means_point.x_axis is 'X坐标';
comment on column tb_means_point.y_axis is 'Y坐标';
comment on column tb_means_point.z_axis is 'Z坐标';
comment on column tb_means_point.sampling_frequency is '采样频率';
comment on column tb_means_point.production_date is '出产日期';
comment on column tb_means_point.production_coding is '出产编号';
comment on column tb_means_point.photo_url is '图片位置';
comment on column tb_means_point.status is '状态 0：离线 1：正常 2: 故障';
comment on column tb_means_point.create_time is '创建时间';
comment on column tb_means_point.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_sensor                                             */
/*==============================================================*/
drop table if exists tb_sensor;
create table tb_sensor (
   id                   SERIAL not null,
   means_point_id       INT4                 null,
   sensor_details_id    INT4                 null,
   sensor_principle_id  INT4                 null,
   sensor_coding        VARCHAR(64)          null,
   initial_value        FLOAT4               null,
   warning_interval     INT4                 null,
   first_warning_upper  FLOAT4               null,
   first_warning_lower  FLOAT4               null,
   second_warning_upper FLOAT4               null,
   second_warning_lower FLOAT4               null,
   third_warning_upper  FLOAT4               null,
   third_warning_lower  FLOAT4               null,
   unit                 VARCHAR(16)          null,
   precision            FLOAT4               null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_SENSOR primary key (id)
);

comment on table tb_sensor is '传感器细项信息表';
comment on column tb_sensor.id is '主键id';
comment on column tb_sensor.means_point_id is '测点id';
comment on column tb_sensor.sensor_details_id is '传感器细项id';
comment on column tb_sensor.sensor_principle_id is '传感器原理id';
comment on column tb_sensor.sensor_coding is '传感器编号(数据读取编码)';
comment on column tb_sensor.initial_value is '初始值';
comment on column tb_sensor.warning_interval is '预警间隔';
comment on column tb_sensor.first_warning_upper is '一级预警上限';
comment on column tb_sensor.first_warning_lower is '一级预警下限';
comment on column tb_sensor.second_warning_upper is '二级预警上限';
comment on column tb_sensor.second_warning_lower is '二级预警下限';
comment on column tb_sensor.third_warning_upper is '三级预警上限';
comment on column tb_sensor.third_warning_lower is '三级预警下限';
comment on column tb_sensor.unit is '单位';
comment on column tb_sensor.precision is '精度';
comment on column tb_sensor.status is '状态';
comment on column tb_sensor.create_time is '创建时间';
comment on column tb_sensor.modify_time is '修改时间';


drop table if exists tb_sensor_log;

/*==============================================================*/
/* Table: tb_sensor_log                                         */
/*==============================================================*/
create table tb_sensor_log (
   id                   SERIAL not null,
   project_id           INT4                 null,
   describe             VARCHAR(256)         null,
   maintain_time        TIMESTAMP            null,
   solution             VARCHAR(256)         null,
   remarks              VARCHAR(256)         null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_SENSOR_LOG primary key (id)
);

comment on table tb_sensor_log is '维护日志表';
comment on column tb_sensor_log.id is '主键id';
comment on column tb_sensor_log.project_id is '项目id';
comment on column tb_sensor_log.describe is '日志描述';
comment on column tb_sensor_log.maintain_time is '维护时间';
comment on column tb_sensor_log.solution is '解决方案';
comment on column tb_sensor_log.remarks is '备注';
comment on column tb_sensor_log.status is '状态';
comment on column tb_sensor_log.create_time is '创建时间';
comment on column tb_sensor_log.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_sensor_record                                      */
/*==============================================================*/
drop table if exists tb_sensor_record;
create table tb_sensor_record (
   id                   SERIAL not null,
   means_point_id       INT4                 null,
   operate_time         TIMESTAMP            null,
   type                 VARCHAR(16)          null,
   reason               VARCHAR(32)          null,
   content              VARCHAR(256)         null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_SENSOR_RECORD primary key (id)
);

comment on table tb_sensor_record is '传感器维护记录';
comment on column tb_sensor_record.id is '主键id';
comment on column tb_sensor_record.means_point_id is '测点id';
comment on column tb_sensor_record.operate_time is '操作日期';
comment on column tb_sensor_record.type is '操作类型';
comment on column tb_sensor_record.reason is '操作原因';
comment on column tb_sensor_record.content is '操作内容';
comment on column tb_sensor_record.status is '状态';
comment on column tb_sensor_record.create_time is '创建时间';
comment on column tb_sensor_record.modify_time is '修改时间';


/*==============================================================*/
/* Table: tb_warning_record                                     */
/*==============================================================*/
drop table if exists tb_warning_record;
create table tb_warning_record (
   id                   SERIAL not null,
   structure_id         INT4                 null,
   component_id         INT4                 null,
   warning_time         TIMESTAMP            null,
   level                INT2                 null,
   handler_user_id      INT4                 null,
   measures             VARCHAR(512)         null,
   handler_time         TIMESTAMP            null,
   send_message         INT2                 null,
   sensor_id            INT4                 null,
   sensor_details_id    INT4                 null,
   sensor_coding        VARCHAR(64)          null,
   sensor_name          VARCHAR(32)          null,
   value                FLOAT4               null,
   machine_id           INT4                 null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_WARNING_RECORD primary key (id)
);

comment on table tb_warning_record is '预警记录表';
comment on column tb_warning_record.id is '主键id';
comment on column tb_warning_record.structure_id is '结构物id';
comment on column tb_warning_record.component_id is '构件id';
comment on column tb_warning_record.warning_time is '预警时间';
comment on column tb_warning_record.level is '1: I级 2:II级 3:III级';
comment on column tb_warning_record.handler_user_id is '处理人id';
comment on column tb_warning_record.measures is '处理措施';
comment on column tb_warning_record.handler_time is '处理时间';
comment on column tb_warning_record.send_message is '0: 否 1:是';
comment on column tb_warning_record.sensor_id is '传感器id';
comment on column tb_warning_record.sensor_details_id is '传感器细项id';
comment on column tb_warning_record.sensor_coding is '传感器编码';
comment on column tb_warning_record.sensor_name is '传感器名称';
comment on column tb_warning_record.value is '测值';
comment on column tb_warning_record.machine_id is '传感器机器码';
comment on column tb_warning_record.status is '0:未处理 1:已处理';
comment on column tb_warning_record.create_time is '创建时间';
comment on column tb_warning_record.modify_time is '修改时间';


/*==============================================================*/
/* Table: composedata                                           */
/*==============================================================*/
drop table if exists composedata;
create table composedata (
   id                   INT4                 not null,
   collecttime          TIMESTAMP            null,
   flag                 INT4                 null,
   paraid               VARCHAR(32)          null,
   paratypecode         VARCHAR(256)         null,
   paravalue            float4               null,
   rtucode              VARCHAR(256)         null,
   systemtime           TIMESTAMP            null,
   constraint PK_COMPOSEDATA primary key (id)
);

comment on table composedata is '基康数据表';
comment on column composedata.id is '主键id';
comment on column composedata.collecttime is '采集时间';
comment on column composedata.flag is '标志';
comment on column composedata.paraid is '传感器编码';
comment on column composedata.paratypecode is '传感器编码2';
comment on column composedata.paravalue is '数值';
comment on column composedata.rtucode is 'r编码';
comment on column composedata.systemtime is '系统时间';


/*==============================================================*/
/* Table: oridata                                               */
/*==============================================================*/
drop table if exists oridata;
create table oridata (
   id                   INT4                 not null,
   collecttime          TIMESTAMP            null,
   flag                 INT4                 null,
   paraid               VARCHAR(32)          null,
   paratypecode         VARCHAR(256)         null,
   paravalue            float4               null,
   rtucode              VARCHAR(256)         null,
   systemtime           TIMESTAMP            null,
   constraint PK_ORIDATA primary key (id)
);

comment on table oridata is '基康数据表';
comment on column oridata.id is '主键id';
comment on column oridata.collecttime is '采集时间';
comment on column oridata.flag is '标志';
comment on column oridata.paraid is '传感器编码';
comment on column oridata.paratypecode is '传感器编码2';
comment on column oridata.paravalue is '数值';
comment on column oridata.rtucode is 'r编码';
comment on column oridata.systemtime is '系统时间';


/*==============================================================*/
/* Table: wmt_generator_table                                   */
/*==============================================================*/
drop table if exists wmt_generator_table;
create table wmt_generator_table (
   g_key                VARCHAR(256)         null,
   g_value              INT4                 null
);

comment on table wmt_generator_table is '基康表主键';


/*==============================================================*/
/* Table: tb_axle_type                                          */
/*==============================================================*/
drop table if exists tb_axle_type;
create table tb_axle_type (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   constraint PK_TB_AXLE_TYPE primary key (id)
);

comment on table tb_axle_type is '车轴类型表';
comment on column tb_axle_type.id is '主键id';
comment on column tb_axle_type.name is '名称';


/*==============================================================*/
/* Table: tb_axle_type_model_rel                                */
/*==============================================================*/
drop table if exists tb_axle_type_model_rel;
create table tb_axle_type_model_rel (
   id                   SERIAL not null,
   axle_type_id         INT4                 null,
   model_id             INT4                 null,
   name                 VARCHAR(16)          null,
   constraint PK_TB_AXLE_TYPE_MODEL_REL primary key (id)
);

comment on table tb_axle_type_model_rel is '车轴类型表车型关联表';
comment on column tb_axle_type_model_rel.id is '主键id';
comment on column tb_axle_type_model_rel.axle_type_id is '车轴类型id';
comment on column tb_axle_type_model_rel.model_id is '车型id';
comment on column tb_axle_type_model_rel.name is '车型名称';

drop table if exists tb_sensor_weight_hour_weight;

/*==============================================================*/
/* Table: tb_sensor_weight_hour_weight                          */
/*==============================================================*/
create table tb_sensor_weight_hour_weight (
   "time"               timestamp            not null,
   structure_id         int4                 null,
   means_point_id       int4                 null,
   max_weight           float4               null,
   ten_weight           int4                 null,
   ten_thirty_weight    int4                 null,
   thirty_fifty_weight  int4                 null,
   fifty_weight         int4                 null,
   sixty_weight         int4                 null,
   seventy_weight       int4                 null,
   eighty_weight        int4                 null,
   ninety_weight        int4                 null,
   hundred_weight       int4                 null,
   two_model_weight     int4                 null,
   three_model_weight   int4                 null,
   four_model_weight    int4                 null,
   five_model_weight    int4                 null,
   six_model_weight     int4                 null,
   six_up_model_weight  int4                 null,
   two_model_weight_not int4                 null,
   three_model_weight_not int4                 null,
   four_model_weight_not int4                 null,
   five_model_weight_not int4                 null,
   six_model_weight_not int4                 null,
   six_up_model_weight_n int4                 null
);

comment on table tb_sensor_weight_hour_weight is '称重传感器每小时统计车重数据';
comment on column tb_sensor_weight_hour_weight."time" is '统计时间';
comment on column tb_sensor_weight_hour_weight.structure_id is '结构物id';
comment on column tb_sensor_weight_hour_weight.means_point_id is '测点id';
comment on column tb_sensor_weight_hour_weight.max_weight is '最大车重';
comment on column tb_sensor_weight_hour_weight.ten_weight is '车重0-10数量';
comment on column tb_sensor_weight_hour_weight.ten_thirty_weight is '车重10-30数量';
comment on column tb_sensor_weight_hour_weight.thirty_fifty_weight is '车重30-50数量';
comment on column tb_sensor_weight_hour_weight.fifty_weight is '车重超过50数量';
comment on column tb_sensor_weight_hour_weight.sixty_weight is '车重超过60数量';
comment on column tb_sensor_weight_hour_weight.seventy_weight is '车重超过70数量';
comment on column tb_sensor_weight_hour_weight.eighty_weight is '车重超过80数量';
comment on column tb_sensor_weight_hour_weight.ninety_weight is '车重超过90数量';
comment on column tb_sensor_weight_hour_weight.hundred_weight is '车重超过100数量';
comment on column tb_sensor_weight_hour_weight.two_model_weight is '二轴超重车数量';
comment on column tb_sensor_weight_hour_weight.three_model_weight is '三轴超重车数量';
comment on column tb_sensor_weight_hour_weight.four_model_weight is '四轴超重车数量';
comment on column tb_sensor_weight_hour_weight.five_model_weight is '五轴超重车数量';
comment on column tb_sensor_weight_hour_weight.six_model_weight is '六轴超重车数量';
comment on column tb_sensor_weight_hour_weight.six_up_model_weight is '六轴以上超重车数量';
comment on column tb_sensor_weight_hour_weight.two_model_weight_not is '二轴未超重车数量';
comment on column tb_sensor_weight_hour_weight.three_model_weight_not is '三轴未超重车数量';
comment on column tb_sensor_weight_hour_weight.four_model_weight_not is '四轴未超重车数量';
comment on column tb_sensor_weight_hour_weight.five_model_weight_not is '五轴未超重车数量';
comment on column tb_sensor_weight_hour_weight.six_model_weight_not is '六轴未超重车数量';
comment on column tb_sensor_weight_hour_weight.six_up_model_weight_n is '六轴以上未超重车数量';


/*==============================================================*/
/* Index: tb_sensor_weight_hour_model_ind                       */
/*==============================================================*/
create  index tb_sensor_weight_hour_model_ind on tb_sensor_weight_hour_weight using BTREE (
"time",
means_point_id
);

/*==============================================================*/
/* Table: tb_sensor_weight_hour_speed                           */
/*==============================================================*/
drop table if exists tb_sensor_weight_hour_speed;
create table tb_sensor_weight_hour_speed (
   "time"               timestamp            not null,
   structure_id         int4                 null,
   means_point_id       int4                 null,
   twenty_speed         int4                 null,
   twenty_forty_speed   int4                 null,
   forty_sixty_speed    int4                 null,
   sixty_eighty_speed   int4                 null,
   eighty_hundred_speed int4                 null,
   hundred_speed        int4                 null
);

comment on table tb_sensor_weight_hour_speed is '称重传感器每小时统计车速数据';
comment on column tb_sensor_weight_hour_speed."time" is '统计时间';
comment on column tb_sensor_weight_hour_speed.structure_id is '结构物id';
comment on column tb_sensor_weight_hour_speed.means_point_id is '测点id';
comment on column tb_sensor_weight_hour_speed.twenty_speed is '车速小于20数量';
comment on column tb_sensor_weight_hour_speed.twenty_forty_speed is '车速20-40数量';
comment on column tb_sensor_weight_hour_speed.forty_sixty_speed is '车速40-60数量';
comment on column tb_sensor_weight_hour_speed.sixty_eighty_speed is '车速60-80数量';
comment on column tb_sensor_weight_hour_speed.eighty_hundred_speed is '车速80-100数量';
comment on column tb_sensor_weight_hour_speed.hundred_speed is '车速大于100数量';


/*==============================================================*/
/* Index: tb_sensor_weight_hour_speed_ind                       */
/*==============================================================*/
create  index tb_sensor_weight_hour_speed_ind on tb_sensor_weight_hour_speed using BTREE (
"time",
means_point_id
);


/*==============================================================*/
/* Table: tb_sensor_weight_hour_model                           */
/*==============================================================*/
drop table if exists tb_sensor_weight_hour_model;
create table tb_sensor_weight_hour_model (
   "time"               timestamp            not null,
   structure_id         int4                 null,
   means_point_id       int4                 null,
   two_model            int4                 null,
   three_model          int4                 null,
   four_model           int4                 null,
   five_model           int4                 null,
   six_model            int4                 null,
   six_up_model         int4                 null
);

comment on table tb_sensor_weight_hour_model is '称重传感器每小时统计车轴数量';
comment on column tb_sensor_weight_hour_model."time" is '统计时间';
comment on column tb_sensor_weight_hour_model.structure_id is '结构物id';
comment on column tb_sensor_weight_hour_model.means_point_id is '测点id';
comment on column tb_sensor_weight_hour_model.two_model is '二轴车数量';
comment on column tb_sensor_weight_hour_model.three_model is '三轴车数量';
comment on column tb_sensor_weight_hour_model.four_model is '四轴车数量';
comment on column tb_sensor_weight_hour_model.five_model is '五轴车数量';
comment on column tb_sensor_weight_hour_model.six_model is '六轴车数量';
comment on column tb_sensor_weight_hour_model.six_up_model is '六轴以上车数量';


/*==============================================================*/
/* Index: tb_sensor_weight_hour_weight_in                       */
/*==============================================================*/
create  index tb_sensor_weight_hour_weight_in on tb_sensor_weight_hour_model using BTREE (
"time",
means_point_id
);

/*==============================================================*/
/* Table: tb_sensor_calculate                                   */
/*==============================================================*/
drop table if exists tb_sensor_calculate;
create table tb_sensor_calculate (
   sensor_coding        VARCHAR(64)          null,
   sensor_details_id    INT4                 null,
   frequency            FLOAT4               null,
   calculate_time       TIMESTAMP            not null
);

comment on table tb_sensor_calculate is '传感器频谱数据';
comment on column tb_sensor_calculate.sensor_coding is '传感器编码';
comment on column tb_sensor_calculate.sensor_details_id is '传感器细项id';
comment on column tb_sensor_calculate.frequency is '频率值';
comment on column tb_sensor_calculate.calculate_time is '计算时间';


/*==============================================================*/
/* Table: tb_sensor_converge                                    */
/*==============================================================*/
drop table if exists tb_sensor_converge;
create table tb_sensor_converge (
   structure_id         INT4                 null,
   sensor_id            INT4                 null,
   sampling_time        TIMESTAMP            not null,
   value                FLOAT4               null,
   sensor_coding        VARCHAR(64)          null,
   sensor_details_id    INT4                 null
);

comment on table tb_sensor_converge is '传感器汇聚表';
comment on column tb_sensor_converge.structure_id is '结构物id';
comment on column tb_sensor_converge.sensor_id is '传感器id';
comment on column tb_sensor_converge.sampling_time is '采样时间';
comment on column tb_sensor_converge.value is '实测值';
comment on column tb_sensor_converge.sensor_coding is '传感器编码';
comment on column tb_sensor_converge.sensor_details_id is '传感器细项id';


/*==============================================================*/
/* Table: tb_sensor_day                                         */
/*==============================================================*/
drop table if exists tb_sensor_day;
create table tb_sensor_day (
   max_value            FLOAT4               null,
   min_value            FLOAT4               null,
   avg_value            FLOAT4               null,
   sensor_coding        VARCHAR(64)          null,
   "time"               TIMESTAMP            not null,
   sensor_details_id    INT4                 null
);

comment on table tb_sensor_day is '传感器一小时内自动生成的数据';
comment on column tb_sensor_day.max_value is '最大值（每1小时）';
comment on column tb_sensor_day.min_value is '最小值（每1小时）';
comment on column tb_sensor_day.avg_value is '平均值（每1小时）';
comment on column tb_sensor_day.sensor_coding is '传感器编码';
comment on column tb_sensor_day."time" is '接收数据时间';
comment on column tb_sensor_day.sensor_details_id is '传感器细项id';


/*==============================================================*/
/* Table: tb_sensor_hour                                        */
/*==============================================================*/
drop table if exists tb_sensor_hour;
create table tb_sensor_hour (
   max_value            FLOAT4               null,
   min_value            FLOAT4               null,
   avg_value            FLOAT4               null,
   sensor_coding        VARCHAR(64)          null,
   "time"               TIMESTAMP            not null,
   sensor_details_id    INT4                 null
);

comment on table tb_sensor_hour is '传感器十分钟内自动生成数据';
comment on column tb_sensor_hour.max_value is '最大值（每1小时）';
comment on column tb_sensor_hour.min_value is '最小值（每1小时）';
comment on column tb_sensor_hour.avg_value is '平均值（每1小时）';
comment on column tb_sensor_hour.sensor_coding is '传感器编码';
comment on column tb_sensor_hour."time" is '接收数据时间';
comment on column tb_sensor_hour.sensor_details_id is '传感器细项id';


/*==============================================================*/
/* Table: tb_sensor_minute                                      */
/*==============================================================*/
drop table if exists tb_sensor_minute;
create table tb_sensor_minute (
   max_value            FLOAT4               null,
   min_value            FLOAT4               null,
   avg_value            FLOAT4               null,
   sensor_coding        VARCHAR(64)          null,
   "time"               TIMESTAMP            not null,
   sensor_details_id    INT4                 null
);

comment on table tb_sensor_minute is '传感器1小时内自动生成的数据(每1分)';
comment on column tb_sensor_minute.max_value is '最大值（每1小时）';
comment on column tb_sensor_minute.min_value is '最小值（每1小时）';
comment on column tb_sensor_minute.avg_value is '平均值（每1小时）';
comment on column tb_sensor_minute.sensor_coding is '传感器编码';
comment on column tb_sensor_minute."time" is '接收数据时间';
comment on column tb_sensor_minute.sensor_details_id is '传感器细项id';


/*==============================================================*/
/* Table: tb_sensor_second                                      */
/*==============================================================*/
drop table if exists tb_sensor_second;
create table tb_sensor_second (
   max_value            FLOAT4               null,
   min_value            FLOAT4               null,
   avg_value            FLOAT4               null,
   sensor_coding        VARCHAR(64)          null,
   "time"               TIMESTAMP            not null,
   sensor_details_id    INT4                 null
);

comment on table tb_sensor_second is '传感器10分钟内自动生成的数据(每3秒)';
comment on column tb_sensor_second.max_value is '最大值（每1小时）';
comment on column tb_sensor_second.min_value is '最小值（每1小时）';
comment on column tb_sensor_second.avg_value is '平均值（每1小时）';
comment on column tb_sensor_second.sensor_coding is '传感器编码';
comment on column tb_sensor_second."time" is '接收数据时间';
comment on column tb_sensor_second.sensor_details_id is '传感器细项id';


/*==============================================================*/
/* Table: tb_sensor_second_one                                  */
/*==============================================================*/
drop table if exists tb_sensor_second_one;
create table tb_sensor_second_one (
   max_value            FLOAT4               null,
   min_value            FLOAT4               null,
   avg_value            FLOAT4               null,
   sensor_coding        VARCHAR(64)          null,
   "time"               TIMESTAMP            not null,
   sensor_details_id    INT4                 null
);

comment on table tb_sensor_second_one is '传感器10分钟内自动生成的数据(每1秒)';
comment on column tb_sensor_second_one.max_value is '最大值（每1小时）';
comment on column tb_sensor_second_one.min_value is '最小值（每1小时）';
comment on column tb_sensor_second_one.avg_value is '平均值（每1小时）';
comment on column tb_sensor_second_one.sensor_coding is '传感器编码';
comment on column tb_sensor_second_one."time" is '接收数据时间';
comment on column tb_sensor_second_one.sensor_details_id is '传感器细项id';


/*==============================================================*/
/* Table: tb_sensor_weight                                      */
/*==============================================================*/
drop table if exists tb_sensor_weight;
create table tb_sensor_weight (
   message_type         INT4                 null,
   reserved             INT4                 null,
   direction            INT4                 null,
   computer_number      INT4                 null,
   command_code         INT4                 null,
   data_type            INT4                 null,
   img_number           INT8                 null,
   track_number         INT4                 null,
   sampling_time        TIMESTAMP            not null,
   lastrow              INT4                 null,
   single_shaft_nuber   INT4                 null,
   sets_shaft_nuber     INT4                 null,
   total_weight         INT4                 null,
   overrun_rate         INT4                 null,
   models               INT4                 null,
   left_wheel_one       INT4                 null,
   left_wheel_two       INT4                 null,
   left_wheel_three     INT4                 null,
   left_wheel_four      INT4                 null,
   left_wheel_five      INT4                 null,
   left_wheel_six       INT4                 null,
   left_wheel_seven     INT4                 null,
   left_wheel_eight     INT4                 null,
   right_wheel_one      INT4                 null,
   right_wheel_two      INT4                 null,
   right_wheel_three    INT4                 null,
   right_wheel_four     INT4                 null,
   right_wheel_five     INT4                 null,
   right_wheel_six      INT4                 null,
   right_wheel_seven    INT4                 null,
   right_wheel_eight    INT4                 null,
   spacing_one_two      INT4                 null,
   spacing_two_three    INT4                 null,
   spacing_three_four   INT4                 null,
   spacing_four_five    INT4                 null,
   spacing_five_six     INT4                 null,
   spacing_six_seven    INT4                 null,
   spacing_seven_eight  INT4                 null,
   transfinite_labeled  VARCHAR(64)          null,
   violation_code       INT4                 null,
   car_speed            INT4                 null,
   acceleration         FLOAT4               null,
   equivalent_shaft     FLOAT4               null,
   license_plate        VARCHAR(16)          null,
   sensor_coding        VARCHAR(64)          null,
   sensor_details_id    INT4                 null,
   license_color        VARCHAR(32)          null,
   bridge_code          VARCHAR(64)          null,
   vehicle_length       INT4                 null
);

comment on table tb_sensor_weight is '称重传感器';
comment on column tb_sensor_weight.message_type is '报文类型';
comment on column tb_sensor_weight.reserved is '预留';
comment on column tb_sensor_weight.direction is '方向';
comment on column tb_sensor_weight.computer_number is '网络通讯计算机编号';
comment on column tb_sensor_weight.command_code is '命令码';
comment on column tb_sensor_weight.data_type is '数据类型';
comment on column tb_sensor_weight.img_number is '图片编号';
comment on column tb_sensor_weight.track_number is '车道号';
comment on column tb_sensor_weight.sampling_time is '检测时间';
comment on column tb_sensor_weight.lastrow is '上下行';
comment on column tb_sensor_weight.single_shaft_nuber is '单轴数';
comment on column tb_sensor_weight.sets_shaft_nuber is '轴组数';
comment on column tb_sensor_weight.total_weight is '总重';
comment on column tb_sensor_weight.overrun_rate is '超限率';
comment on column tb_sensor_weight.models is '车型';
comment on column tb_sensor_weight.left_wheel_one is '左1轮重';
comment on column tb_sensor_weight.left_wheel_two is '左2轮重';
comment on column tb_sensor_weight.left_wheel_three is '左3轮重';
comment on column tb_sensor_weight.left_wheel_four is '左4轮重';
comment on column tb_sensor_weight.left_wheel_five is '左5轮重';
comment on column tb_sensor_weight.left_wheel_six is '左6轮重';
comment on column tb_sensor_weight.left_wheel_seven is '左7轮重';
comment on column tb_sensor_weight.left_wheel_eight is '左8轮重';
comment on column tb_sensor_weight.right_wheel_one is '右1轮重';
comment on column tb_sensor_weight.right_wheel_two is '右2轮重';
comment on column tb_sensor_weight.right_wheel_three is '右3轮重';
comment on column tb_sensor_weight.right_wheel_four is '右4轮重';
comment on column tb_sensor_weight.right_wheel_five is '右5轮重';
comment on column tb_sensor_weight.right_wheel_six is '右6轮重';
comment on column tb_sensor_weight.right_wheel_seven is '右7轮重';
comment on column tb_sensor_weight.right_wheel_eight is '右8轮重';
comment on column tb_sensor_weight.spacing_one_two is '轴1、2间距';
comment on column tb_sensor_weight.spacing_two_three is '轴2、3间距';
comment on column tb_sensor_weight.spacing_three_four is '轴3、4间距';
comment on column tb_sensor_weight.spacing_four_five is '轴4、5间距';
comment on column tb_sensor_weight.spacing_five_six is '轴5、6间距';
comment on column tb_sensor_weight.spacing_six_seven is '轴6、7间距';
comment on column tb_sensor_weight.spacing_seven_eight is '轴7、8间距';
comment on column tb_sensor_weight.transfinite_labeled is '超限标示';
comment on column tb_sensor_weight.violation_code is '违例码';
comment on column tb_sensor_weight.car_speed is '车速';
comment on column tb_sensor_weight.acceleration is '加速度';
comment on column tb_sensor_weight.equivalent_shaft is '当量轴次';
comment on column tb_sensor_weight.license_plate is '车牌';
comment on column tb_sensor_weight.sensor_coding is '传感器编码';
comment on column tb_sensor_weight.sensor_details_id is '传感器细项';
comment on column tb_sensor_weight.license_color is '车牌颜色';
comment on column tb_sensor_weight.bridge_code is '桥梁编号';
comment on column tb_sensor_weight.vehicle_length is '车长度';


SELECT create_hypertable('tb_sensor_converge', 'sampling_time', chunk_time_interval => INTERVAL '7 day');
SELECT create_hypertable('tb_sensor_weight', 'sampling_time', chunk_time_interval => INTERVAL '7 day');
SELECT create_hypertable('tb_sensor_second', 'sampling_time', chunk_time_interval => INTERVAL '7 day');
SELECT create_hypertable('tb_sensor_second_one', 'sampling_time', chunk_time_interval => INTERVAL '1 month');
