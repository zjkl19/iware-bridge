---------------------------------------------------------------------------
--通用模块数据库
---------------------------------------------------------------------------

/*==============================================================*/
/* Table: tb_project                                            */
/*==============================================================*/
drop table IF EXISTS tb_project;

create table tb_project (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   synopsis             VARCHAR(256)         null,
   user_id              INT4                 null,
   create_user_id       INT4                 null,
   charge_person        VARCHAR(32)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_PROJECT primary key (id)
);

comment on table tb_project is
'项目信息表';

comment on column tb_project.id is
'主键id';

comment on column tb_project.name is
'项目名称';

comment on column tb_project.synopsis is
'项目简介';

comment on column tb_project.user_id is
'所属业主id';

comment on column tb_project.create_user_id is
'创建者id';

comment on column tb_project.charge_person is
'相关负责人';

comment on column tb_project.status is
'状态';

comment on column tb_project.create_time is
'创建时间';

comment on column tb_project.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_structure                                          */
/*==============================================================*/
drop table IF EXISTS tb_structure;

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
   project_id           INT4                 null,
   user_id              INT4                 null,
   create_user_id       INT4                 null,
   technology           VARCHAR(16)          null,
   grade                VARCHAR(16)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_STRUCTURE primary key (id)
);

comment on table tb_structure is
'结构物信息表';

comment on column tb_structure.id is
'主键id';

comment on column tb_structure.code is
'结构物代码';

comment on column tb_structure.name is
'结构物名称';

comment on column tb_structure.number is
'结构物编号';

comment on column tb_structure.province_id is
'所在省';

comment on column tb_structure.city_id is
'所在市';

comment on column tb_structure.county_id is
'所在区(县)';

comment on column tb_structure.charge_name is
'相关责任人';

comment on column tb_structure.charge_phone is
'责任人电话';

comment on column tb_structure.road_name is
'路线名称';

comment on column tb_structure.build_time is
'建成年月';

comment on column tb_structure.maintain_grade is
'养护等级 1: I等 2:II等 3:III等';

comment on column tb_structure.maintain_department is
'养护单位';

comment on column tb_structure.maintain_depart_phone is
'养护单位电话';

comment on column tb_structure.design_load is
'设计荷载';

comment on column tb_structure.span_type is
'跨径分类';

comment on column tb_structure.longitude is
'经度';

comment on column tb_structure.latitude is
'纬度';

comment on column tb_structure.structure_type is
'1:桥梁 2:隧道';

comment on column tb_structure.bridge_type is
'桥梁类型 1:简支梁桥 2:连续梁桥 3:圬工拱桥 4:钢筋混凝土拱桥 5:钢管混凝土拱桥 6:钢砼组合体系桥 7:悬索桥与斜拉桥类 ';

comment on column tb_structure.running_department is
'运行管理单位';

comment on column tb_structure.supervision_department is
'监理单位';

comment on column tb_structure.building_department is
'建设单位';

comment on column tb_structure.construction_department is
'施工单位';

comment on column tb_structure.design_department is
'设计单位';

comment on column tb_structure.project_id is
'所属项目id';

comment on column tb_structure.user_id is
'所属业主id';

comment on column tb_structure.create_user_id is
'创建者id';

comment on column tb_structure.technology is
'技术状况';

comment on column tb_structure.grade is
'状况等级';

comment on column tb_structure.status is
'状态';

comment on column tb_structure.create_time is
'创建时间';

comment on column tb_structure.modify_time is
'修改时间';

/*==============================================================*/
/* Table: tb_bridge_info                                        */
/*==============================================================*/
drop table IF EXISTS tb_bridge_info;

create table tb_bridge_info (
   id                   SERIAL not null,
   structure_id         INT4                 null,
   cost                 DECIMAL(10,2)        null,
   maintain_category    INT2                 null,
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

comment on table tb_bridge_info is
'桥梁详情信息表';

comment on column tb_bridge_info.id is
'主键id';

comment on column tb_bridge_info.structure_id is
'结构物id';

comment on column tb_bridge_info.cost is
'总造价';

comment on column tb_bridge_info.maintain_category is
'1:I类 2:II类 3:III类 4:IV类 5:V类';

comment on column tb_bridge_info.road_grade is
'道路等级';

comment on column tb_bridge_info.posting_standard is
'限载标准';

comment on column tb_bridge_info.quake_intensity is
'抗震烈度';

comment on column tb_bridge_info.skew_angle is
'正斜交角';

comment on column tb_bridge_info.span_num is
'桥梁跨度';

comment on column tb_bridge_info.span_combination is
'跨径组合';

comment on column tb_bridge_info.area is
'桥梁面积';

comment on column tb_bridge_info.total_length is
'桥梁总长';

comment on column tb_bridge_info.total_width is
'桥梁总宽';

comment on column tb_bridge_info.roadway_width is
'车行道净宽';

comment on column tb_bridge_info.sidewalk_width is
'人行道净宽';

comment on column tb_bridge_info.streamway_grade is
'河道等级';

comment on column tb_bridge_info.highest_stage is
'最高水位';

comment on column tb_bridge_info.usual_stage is
'常水位';

comment on column tb_bridge_info.up_main_beam_form is
'上部结构_主梁型式';

comment on column tb_bridge_info.up_main_beam_size is
'上部结构_主梁尺寸';

comment on column tb_bridge_info.up_main_beam_quantity is
'上部结构_主梁数量';

comment on column tb_bridge_info.up_cross_beam_form is
'上部结构_横梁型式';

comment on column tb_bridge_info.clearance_span is
'上部结构_主跨桥下净空';

comment on column tb_bridge_info.lower_limit is
'上部结构_桥下限高';

comment on column tb_bridge_info.up_rise_span is
'上部结构_跨桥矢跨比';

comment on column tb_bridge_info.up_bearing_form is
'上部结构_支座型式';

comment on column tb_bridge_info.up_bearing_num is
'上部结构_支座数量';

comment on column tb_bridge_info.up_deck_composition is
'上部结构_桥面结构';

comment on column tb_bridge_info.up_pavement_land is
'上部结构_桥面铺装厚度';

comment on column tb_bridge_info.up_expansion_form is
'上部结构_伸缩缝型式';

comment on column tb_bridge_info.up_expansion_quantity is
'上部结构_伸缩缝数量';

comment on column tb_bridge_info.up_main_longitudinal_slope is
'上部结构_主桥纵坡';

comment on column tb_bridge_info.up_main_cross_slope is
'上部结构_主桥横坡';

comment on column tb_bridge_info.up_approach_longitudinal_slope is
'上部结构_引桥纵坡';

comment on column tb_bridge_info.up_approach_cross_slope is
'上部结构_引桥横坡';

comment on column tb_bridge_info.pier_form is
'桥墩_型式';

comment on column tb_bridge_info.pier_num is
'桥墩_数量';

comment on column tb_bridge_info.pier_elevation is
'桥墩_标高';

comment on column tb_bridge_info.pier_cap_size is
'桥墩_盖梁尺寸';

comment on column tb_bridge_info.pier_base_elevation is
'桥墩_基底标高（m）';

comment on column tb_bridge_info.pier_floor_size is
'桥墩_底板尺寸';

comment on column tb_bridge_info.pier_pile_size is
'桥墩_基桩尺寸';

comment on column tb_bridge_info.pier_pile_num is
'桥墩_基桩根数';

comment on column tb_bridge_info.abutment_form is
'桥台_型式';

comment on column tb_bridge_info.abutment_num is
'桥台_数量';

comment on column tb_bridge_info.abutment_elevation is
'桥台_标高';

comment on column tb_bridge_info.abutment_base_elevation is
'桥台_基底标高（m）';

comment on column tb_bridge_info.abutment_cap_size is
'桥台_台帽尺寸';

comment on column tb_bridge_info.abutment_baseboard_size is
'桥台_底板尺寸';

comment on column tb_bridge_info.abutment_pile_size is
'桥台_基桩尺寸';

comment on column tb_bridge_info.abutment_pile_num is
'桥台_基桩根数';

comment on column tb_bridge_info.abutment_retain_thick is
'桥台_挡土板厚度';

comment on column tb_bridge_info.abutment_wing_wall_form is
'桥台_翼墙型式';

comment on column tb_bridge_info.abutment_wing_wall_length is
'桥台_翼墙长度';

comment on column tb_bridge_info.auxiliary_gulley_size is
'附属工程_集水口尺寸';

comment on column tb_bridge_info.auxiliary_gulley_num is
'附属工程_集水口数量';

comment on column tb_bridge_info.water_drain_pipe_size is
'附属工程-泄水管尺寸';

comment on column tb_bridge_info.water_drain_pipe_length is
'附属工程-泄水管长度';

comment on column tb_bridge_info.auxiliary_rail_length is
'附属工程_栏杆总长';

comment on column tb_bridge_info.auxiliary_rail_struction is
'附属工程_栏杆结构';

comment on column tb_bridge_info.auxiliary_boundary_size is
'附属工程_端柱尺寸';

comment on column tb_bridge_info.auxiliary_revetment_type is
'附属工程_护岸类型';

comment on column tb_bridge_info.auxiliary_barricade_type is
'附属工程-引坡挡墙类型';

comment on column tb_bridge_info.pipeline_water is
'附挂管线_给水管';

comment on column tb_bridge_info.pipeline_gas is
'附挂管线_燃气管';

comment on column tb_bridge_info.pipeline_electricity is
'附挂管线_电力缆';

comment on column tb_bridge_info.pipeline_cable is
'附挂管线_通讯电缆';

comment on column tb_bridge_info.status is
'状态';

comment on column tb_bridge_info.create_time is
'创建时间';

comment on column tb_bridge_info.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_tunnel_info                                        */
/*==============================================================*/
drop table IF EXISTS tb_tunnel_info;

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

comment on table tb_tunnel_info is
'隧道信息表';

comment on column tb_tunnel_info.id is
'主键id';

comment on column tb_tunnel_info.structure_id is
'结构结构物id';

comment on column tb_tunnel_info.road_code is
'路线编号';

comment on column tb_tunnel_info.road_grade is
'路线等级';

comment on column tb_tunnel_info.center_mileage is
'中心桩号';

comment on column tb_tunnel_info.length is
'长度';

comment on column tb_tunnel_info.width is
'宽度';

comment on column tb_tunnel_info.road_width is
'路面宽度';

comment on column tb_tunnel_info.height is
'净高';

comment on column tb_tunnel_info.stratum_geology is
'岩层地质';

comment on column tb_tunnel_info.wall_rock_types is
'围岩分类';

comment on column tb_tunnel_info.lining_type is
'衬砌类型';

comment on column tb_tunnel_info.lining_thick is
'衬砌厚度';

comment on column tb_tunnel_info.portal_form is
'洞门型式';

comment on column tb_tunnel_info.road_type is
'路面类型';

comment on column tb_tunnel_info.lighting_installation is
'照明设施';

comment on column tb_tunnel_info.air_installation is
'通风设施';

comment on column tb_tunnel_info.fire_installation is
'消防与救援设施';

comment on column tb_tunnel_info.watch_installation is
'监控设施';

comment on column tb_tunnel_info.electric_installation is
'供配电设施';

comment on column tb_tunnel_info.tunnel_longitudinal_slope is
'洞内纵坡';

comment on column tb_tunnel_info.status is
'状态';

comment on column tb_tunnel_info.create_time is
'创建时间';

comment on column tb_tunnel_info.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_project_appoint                                    */
/*==============================================================*/
drop table IF EXISTS tb_project_appoint;

create table tb_project_appoint (
   id                   SERIAL not null,
   project_id           INT4                 null,
   appoint_user_id      INT4                 null,
   receive_user_id      INT4                 null,
   start_time           TIMESTAMP            null,
   end_time             TIMESTAMP            null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_PROJECT_APPOINT primary key (id)
);

comment on table tb_project_appoint is
'项目指派记录表';

comment on column tb_project_appoint.id is
'主键id';

comment on column tb_project_appoint.project_id is
'项目id';

comment on column tb_project_appoint.appoint_user_id is
'指派方id';

comment on column tb_project_appoint.receive_user_id is
'接收方id';

comment on column tb_project_appoint.start_time is
'开始时间';

comment on column tb_project_appoint.end_time is
'结束时间';

comment on column tb_project_appoint.status is
'状态';

comment on column tb_project_appoint.create_time is
'创建时间';

comment on column tb_project_appoint.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_project_appoint_power_rel                          */
/*==============================================================*/
drop table IF EXISTS tb_project_appoint_power_rel;

create table tb_project_appoint_power_rel (
   id                   SERIAL not null,
   project_appoint_id   INT4                 null,
   power_id             INT4                 null,
   constraint PK_TB_PROJECT_APPOINT_POWER_RE primary key (id)
);

comment on column tb_project_appoint_power_rel.id is
'主键id';

comment on column tb_project_appoint_power_rel.project_appoint_id is
'项目指派记录id';

comment on column tb_project_appoint_power_rel.power_id is
'业务权限id';


/*==============================================================*/
/* Table: tb_video                                              */
/*==============================================================*/
drop table IF EXISTS tb_video;

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

comment on table tb_video is
'视频表';

comment on column tb_video.id is
'主键id';

comment on column tb_video.structure_id is
'结构物id';

comment on column tb_video.part_id is
'部位id';

comment on column tb_video.component_id is
'构件id';

comment on column tb_video.monitor_name is
'监控视角名称';

comment on column tb_video.type is
'设备类型 1:萤石 2:海康威视';

comment on column tb_video.ezopen_url is
'萤石云监控地址';

comment on column tb_video.ip is
'视频ip地址';

comment on column tb_video.port is
'视频端口号';

comment on column tb_video.app_key is
'监控appkey';

comment on column tb_video.password is
'监控密码';

comment on column tb_video.video_coding is
'监控视频码';

comment on column tb_video.x_axis is
'X坐标';

comment on column tb_video.y_axis is
'Y坐标';

comment on column tb_video.z_axis is
'Z坐标';

comment on column tb_video.status is
'状态';

comment on column tb_video.create_time is
'创建时间';

comment on column tb_video.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_announcement                                       */
/*==============================================================*/
drop table IF EXISTS tb_announcement;

create table tb_announcement (
   id                   SERIAL not null,
   title                VARCHAR(32)          null,
   content              VARCHAR(256)         null,
   create_user_id       INT4                 null,
   creator              VARCHAR(32)          null,
   effect_time          TIMESTAMP            null,
   expires_time         TIMESTAMP            null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_ANNOUNCEMENT primary key (id)
);

comment on table tb_announcement is
'公告表';

comment on column tb_announcement.id is
'主键id';

comment on column tb_announcement.title is
'公告标题';

comment on column tb_announcement.content is
'公告内容';

comment on column tb_announcement.create_user_id is
'创建者id';

comment on column tb_announcement.creator is
'创建者';

comment on column tb_announcement.effect_time is
'生效时间';

comment on column tb_announcement.expires_time is
'到期时间';

comment on column tb_announcement.status is
'0:未发布 1:已发布';

comment on column tb_announcement.create_time is
'创建时间';

comment on column tb_announcement.modify_time is
'修改时间';

/*==============================================================*/
/* Table: tb_user_announcement_rel                              */
/*==============================================================*/
drop table IF EXISTS tb_user_announcement_rel;

create table tb_user_announcement_rel (
   id                   SERIAL not null,
   announcement_id      INT4                 null,
   user_id              INT4                 null,
   status               INT2                 null,
   constraint PK_TB_USER_ANNOUNCEMENT_REL primary key (id)
);

comment on table tb_user_announcement_rel is
'用户公告关联表';

comment on column tb_user_announcement_rel.id is
'主键id';

comment on column tb_user_announcement_rel.announcement_id is
'公告id';

comment on column tb_user_announcement_rel.user_id is
'用户id';

comment on column tb_user_announcement_rel.status is
'0:未读 1:已读';


/*==============================================================*/
/* Table: tb_area                                               */
/*==============================================================*/
drop table IF EXISTS tb_area;

create table tb_area (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   parent               INT4                 null,
   code                 VARCHAR(32)          null,
   constraint PK_TB_AREA primary key (id)
);

comment on table tb_area is
'省市区表';

comment on column tb_area.id is
'主键id';

comment on column tb_area.name is
'名称';

comment on column tb_area.parent is
'父级id';

comment on column tb_area.code is
'编码';


/*==============================================================*/
/* Table: tb_photo                                              */
/*==============================================================*/
drop table IF EXISTS tb_photo;

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

comment on table tb_photo is
'图片表';

comment on column tb_photo.id is
'主键id';

comment on column tb_photo.type is
'1:桥梁图片 2:隧道图片 3:桥梁附件 4:隧道附件 5:日常巡检 6.7.8 维养前中后';

comment on column tb_photo.target_id is
'目标id';

comment on column tb_photo.name is
'文件名';

comment on column tb_photo.path is
'存储位置';

comment on column tb_photo.terrain is
'是否加载地形';

comment on column tb_photo.remarks is
'备注';

comment on column tb_photo.status is
'状态';

comment on column tb_photo.create_time is
'创建时间';

comment on column tb_photo.modify_time is
'修改时间';

/*==============================================================*/
/* Table: tb_component_info                                     */
/*==============================================================*/
drop table IF EXISTS tb_component_info;

create table tb_component_info (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   english_name         VARCHAR(32)          null,
   part_id              INT4                 null,
   constraint PK_TB_COMPONENT_INFO primary key (id)
);

comment on table tb_component_info is
'巡查维养构件表';

comment on column tb_component_info.id is
'主键id';

comment on column tb_component_info.name is
'构件名称';

comment on column tb_component_info.english_name is
'英文名';

comment on column tb_component_info.part_id is
'1:桥面系 2:上部结构 3:上部结构 4:人行地下通道';


/*==============================================================*/
/* Table: tb_report                                             */
/*==============================================================*/
drop table IF EXISTS tb_report;

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

comment on table tb_report is
'报表信息';

comment on column tb_report.id is
'主键id';

comment on column tb_report.project_id is
'项目id';

comment on column tb_report.name is
'名称';

comment on column tb_report.user_id is
'上传人';

comment on column tb_report.type is
'1:监测报表 2:巡查报表 3:维养报表';

comment on column tb_report.specific is
'1:日报 2:月报 3:年报';

comment on column tb_report.path is
'路径';

comment on column tb_report.status is
'状态';

comment on column tb_report.create_time is
'创建时间';

comment on column tb_report.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_part_info                                          */
/*==============================================================*/
drop table IF EXISTS tb_part_info;

create table tb_part_info (
   id                   SERIAL not null,
   name                 VARCHAR(16)          null,
   english_name         VARCHAR(32)          null,
   constraint PK_TB_PART_INFO primary key (id)
);

comment on table tb_part_info is
'部位信息表';

comment on column tb_part_info.id is
'主键id';

comment on column tb_part_info.name is
'部位名称';

comment on column tb_part_info.english_name is
'英文名';


/*==============================================================*/
/* Table: tb_electronic_archives                                */
/*==============================================================*/
drop table IF EXISTS tb_electronic_archives;

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

comment on table tb_electronic_archives is
'电子档案资料表';

comment on column tb_electronic_archives.id is
'主键id';

comment on column tb_electronic_archives.structure_id is
'结构物id';

comment on column tb_electronic_archives.file_name is
'文件名称';

comment on column tb_electronic_archives.code is
'文件编号';

comment on column tb_electronic_archives.type is
'1:设计档案 2:施工文件 3:合同文件 4:基本资料 5:养护档案 6:其他';

comment on column tb_electronic_archives.summary is
'文件摘要';

comment on column tb_electronic_archives.user_id is
'创建者id';

comment on column tb_electronic_archives.path is
'路径';

comment on column tb_electronic_archives.status is
'状态';

comment on column tb_electronic_archives.create_time is
'创建时间';

comment on column tb_electronic_archives.modify_time is
'修改时间';
