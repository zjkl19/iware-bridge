---------------------------------------------------------------------------
--检测评估数据库
---------------------------------------------------------------------------


/*==============================================================*/
/* Table: "tb_attachment"                                      */
/*==============================================================*/
drop table IF EXISTS "tb_attachment";

create table tb_attachment (
   id                   SERIAL not null,
   name                 VARCHAR(64)          null,
   path                 VARCHAR(128)          null,
   type                 INT2                 null,
   target_id            INT4                 null,
   part_type            INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   status               INT2                 null,
   constraint PK_TB_ATTACHMENT primary key (id)
);

comment on table tb_attachment is
'附件表';

comment on column tb_attachment.id is
'主键id';

comment on column tb_attachment.name is
'附件名称';

comment on column tb_attachment.path is
'附件地址';

comment on column tb_attachment.type is
'附件类型 1：线路图片 2：桥垮图片 3：部位图片 4：构件图片 5：病害图片';

comment on column tb_attachment.target_id is
'目标id';

comment on column tb_attachment.part_type is
'1：桥面系 2：上部结构 3：下部结构';

comment on column tb_attachment.create_time is
'创建时间';

comment on column tb_attachment.modify_time is
'修改时间';

comment on column tb_attachment.status is
'1：正常 0：删除 2:选中';


/*==============================================================*/
/* Table: tb_monitor_plan                                       */
/*==============================================================*/
drop table IF EXISTS tb_monitor_plan;

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

comment on table tb_monitor_plan is
'监测计划';

comment on column tb_monitor_plan.id is
'id主键';

comment on column tb_monitor_plan.type is
'监测类型1：定期监测 2：特殊检测 3：静载实验';

comment on column tb_monitor_plan.project_info_id is
'项目id';

comment on column tb_monitor_plan.start_time is
'开始日期';

comment on column tb_monitor_plan.end_time is
'结束日期';

comment on column tb_monitor_plan.upload is
'app是否上传';

comment on column tb_monitor_plan.create_user_id is
'创建者用户id';

comment on column tb_monitor_plan.status is
'状态';

comment on column tb_monitor_plan.create_time is
'创建日期';

comment on column tb_monitor_plan.modify_time is
'修改日期';


/*==============================================================*/
/* Table: tb_monitor_plan_structure_rel                         */
/*==============================================================*/
drop table IF EXISTS tb_monitor_plan_structure_rel;

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

comment on table tb_monitor_plan_structure_rel is
'监测计划结构物关联表';

comment on column tb_monitor_plan_structure_rel.id is
'id主键';

comment on column tb_monitor_plan_structure_rel.monitor_plan_id is
'计划id';

comment on column tb_monitor_plan_structure_rel.structure_info_id is
'结构物id';

comment on column tb_monitor_plan_structure_rel.structure_name is
'结构物名称';

comment on column tb_monitor_plan_structure_rel.file_name is
'方案名称';

comment on column tb_monitor_plan_structure_rel.file_url is
'方案url';

comment on column tb_monitor_plan_structure_rel.is_delete is
'0:否1:是 (为空也是否)';


/*==============================================================*/
/* Table: tb_monitor_plan_original_record                       */
/*==============================================================*/
drop table IF EXISTS tb_monitor_plan_original_record;

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

comment on table tb_monitor_plan_original_record is
'监测计划原始记录表';

comment on column tb_monitor_plan_original_record.monitor_plan_structure_rel_id is
'监测计划结构物关联id';

comment on column tb_monitor_plan_original_record.instrumentation is
'检测仪器';

comment on column tb_monitor_plan_original_record.project_location is
'工程地点';

comment on column tb_monitor_plan_original_record.test_basis is
'检测依据';

comment on column tb_monitor_plan_original_record.record_number is
'记录编号';

comment on column tb_monitor_plan_original_record.create_user_id is
'创建者id';

comment on column tb_monitor_plan_original_record.status is
'状态';

comment on column tb_monitor_plan_original_record.create_time is
'创建时间';

comment on column tb_monitor_plan_original_record.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_bridge_road                                        */
/*==============================================================*/
drop table IF EXISTS tb_bridge_road;

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

comment on table tb_bridge_road is
'桥梁线路表';

comment on column tb_bridge_road.id is
'主键id';

comment on column tb_bridge_road.name is
'线路名称';

comment on column tb_bridge_road.monitor_plan_structure_rel_id is
'计划结构物id';

comment on column tb_bridge_road.bridge_type_id is
'桥梁类型id';

comment on column tb_bridge_road.span_number is
'桥跨数量';

comment on column tb_bridge_road.main_span_number is
'主跨数量';

comment on column tb_bridge_road.stairway_number is
'梯道数';

comment on column tb_bridge_road.stairway_span_number is
'梯道跨数';

comment on column tb_bridge_road.report_path is
'报告路径';

comment on column tb_bridge_road.creator is
'创建者';

comment on column tb_bridge_road.create_time is
'创建时间';

comment on column tb_bridge_road.modify_time is
'修改时间';

comment on column tb_bridge_road.status is
'1：正常 0：被回收';


/*==============================================================*/
/* Table: tb_bridge_span                                        */
/*==============================================================*/
drop table IF EXISTS tb_bridge_span;

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
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   status               INT2                 null,
   constraint PK_TB_BRIDGE_SPAN primary key (id)
);

comment on table tb_bridge_span is
'桥跨表';

comment on column tb_bridge_span.id is
'主键id';

comment on column tb_bridge_span.bridge_road_id is
'线路id';

comment on column tb_bridge_span.bridge_shape is
'桥梁形状';

comment on column tb_bridge_span.span_code is
'桥跨编号';

comment on column tb_bridge_span.length is
'桥跨长度';

comment on column tb_bridge_span.outer_arc_length is
'外弧长';

comment on column tb_bridge_span.inner_arc_length is
'内弧长';

comment on column tb_bridge_span.convex is
'0：凹  1：凸';

comment on column tb_bridge_span.creator is
'创建者';

comment on column tb_bridge_span.create_time is
'创建时间';

comment on column tb_bridge_span.modify_time is
'修改时间';

comment on column tb_bridge_span.status is
'1：正常 0：回收';


/*==============================================================*/
/* Table: "tb_bridge_deck_system"                               */
/*==============================================================*/
drop table IF EXISTS "tb_bridge_deck_system";

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

comment on table tb_bridge_deck_system is
'桥面系信息表';

comment on column tb_bridge_deck_system.id is
'主键id';

comment on column tb_bridge_deck_system.bridge_span_id is
'桥跨id';

comment on column tb_bridge_deck_system.direction is
'桥梁走向 ：东 南 西 北 东南 西南 东北 西北';

comment on column tb_bridge_deck_system.tilt_angle is
'倾斜角度';

comment on column tb_bridge_deck_system.sidewalk is
'有无人行道';

comment on column tb_bridge_deck_system.sidewalk_width is
'人行道宽';

comment on column tb_bridge_deck_system.left_sidewalk_width is
'人行道宽(左)';

comment on column tb_bridge_deck_system.right_sidewalk_width is
'人行道宽(右)';

comment on column tb_bridge_deck_system.lane_width is
'行车道宽';

comment on column tb_bridge_deck_system.midle_barrier_width is
'中间隔离栏宽度';

comment on column tb_bridge_deck_system.left_rail_width is
'护栏(左)';

comment on column tb_bridge_deck_system.right_rail_width is
'护栏(右)';

comment on column tb_bridge_deck_system.stairway is
'梯道';

comment on column tb_bridge_deck_system.stairway_shadow_length is
'梯道长(投影)';

comment on column tb_bridge_deck_system.stairway_length is
'梯道长';

comment on column tb_bridge_deck_system.stairway_width is
'梯道宽';

comment on column tb_bridge_deck_system.platform_number is
'梯台数';

comment on column tb_bridge_deck_system.expansion_joint is
'0：无 1：有';

comment on column tb_bridge_deck_system.bridge_head_board_length is
'桥头搭板长';

comment on column tb_bridge_deck_system.monitor_diagram is
'模型地址';

comment on column tb_bridge_deck_system.create_time is
'创建时间';

comment on column tb_bridge_deck_system.modify_time is
'修改时间';

comment on column tb_bridge_deck_system.creator is
'创建者';

comment on column tb_bridge_deck_system.status is
'1：正常 0：回收';


/*==============================================================*/
/* Table: tb_bridge_supstructure                                */
/*==============================================================*/
drop table IF EXISTS tb_bridge_supstructure;

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

comment on table tb_bridge_supstructure is
'桥梁上部结构';

comment on column tb_bridge_supstructure.id is
'id';

comment on column tb_bridge_supstructure.bridge_span_id is
'桥跨id';

comment on column tb_bridge_supstructure.beam_type is
'梁类型';

comment on column tb_bridge_supstructure.beam_number is
'梁数量';

comment on column tb_bridge_supstructure.hanging_beam is
'挂梁数';

comment on column tb_bridge_supstructure.hanging_beam_support is
'挂梁支座数';

comment on column tb_bridge_supstructure.anti_falling_beam is
'防落梁装置数';

comment on column tb_bridge_supstructure.truss is
'桁片数';

comment on column tb_bridge_supstructure.primary_node is
'主节点数';

comment on column tb_bridge_supstructure.stringer is
'纵梁数';

comment on column tb_bridge_supstructure.cross_beam is
'横梁数';

comment on column tb_bridge_supstructure.connector is
'连接件数';

comment on column tb_bridge_supstructure.arch_ring is
'主拱圈数';

comment on column tb_bridge_supstructure.horizonta_connection is
'横向联系数';

comment on column tb_bridge_supstructure.arch_structure is
'拱上构造数';

comment on column tb_bridge_supstructure.exterior_trim_panel is
'外部装饰板数';

comment on column tb_bridge_supstructure.monitor_diagram is
'模型地址';

comment on column tb_bridge_supstructure.creator is
'创建者';

comment on column tb_bridge_supstructure.create_time is
'创建时间';

comment on column tb_bridge_supstructure.modify_time is
'修改时间';

comment on column tb_bridge_supstructure.status is
'1：正常 0：回收';


/*==============================================================*/
/* Table: tb_bridge_substructure                                */
/*==============================================================*/
drop table IF EXISTS tb_bridge_substructure;

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

comment on table tb_bridge_substructure is
'桥梁下部结构';

comment on column tb_bridge_substructure.id is
'主键id';

comment on column tb_bridge_substructure.bridge_span_id is
'桥跨id';

comment on column tb_bridge_substructure.single_row_dun is
'单排敦数';

comment on column tb_bridge_substructure.single_row_support is
'单排支座数';

comment on column tb_bridge_substructure.arch_foot is
'拱脚数';

comment on column tb_bridge_substructure.exterior_trim_panel is
'外部装饰板数';

comment on column tb_bridge_substructure.monitor_diagram is
'模型地址';

comment on column tb_bridge_substructure.creator is
'创建者';

comment on column tb_bridge_substructure.create_time is
'创建时间';

comment on column tb_bridge_substructure.modify_time is
'修改时间';

comment on column tb_bridge_substructure.status is
'1：正常 0：回收';


/*==============================================================*/
/* Table: tb_bridge_other_structure                             */
/*==============================================================*/
drop table IF EXISTS tb_bridge_other_structure;

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

comment on column tb_bridge_other_structure.id is
'id主键';

comment on column tb_bridge_other_structure.bridge_span_id is
'桥跨id';

comment on column tb_bridge_other_structure.descript is
'描述';

comment on column tb_bridge_other_structure.creator is
'创建者';

comment on column tb_bridge_other_structure.create_time is
'创建时间';

comment on column tb_bridge_other_structure.modify_time is
'修改时间';

comment on column tb_bridge_other_structure.status is
'1：正常 0：回收';


/*==============================================================*/
/* Table: tb_bridge_deck_component                              */
/*==============================================================*/
drop table IF EXISTS tb_bridge_deck_component;

create table tb_bridge_deck_component (
   id                   SERIAL not null,
   bridge_deck_system_id INT4                 null,
   component_id         INT4                 null,
   code                 VARCHAR(32)          null,
   initial_weight       FLOAT8               null,
   actual_weight        FLOAT8               null,
   deduct               FLOAT8               null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   creator              VARCHAR(32)          null,
   status               INT2                 null,
   constraint PK_TB_BRIDGE_DECK_COMPONENT primary key (id)
);

comment on table tb_bridge_deck_component is
'桥面系构件实例';

comment on column tb_bridge_deck_component.id is
'主键id';

comment on column tb_bridge_deck_component.bridge_deck_system_id is
'桥梁桥面系id';

comment on column tb_bridge_deck_component.component_id is
'构件类型id';

comment on column tb_bridge_deck_component.code is
'结构编号';

comment on column tb_bridge_deck_component.initial_weight is
'原始权重';

comment on column tb_bridge_deck_component.actual_weight is
'实际权重';

comment on column tb_bridge_deck_component.deduct is
'扣分';

comment on column tb_bridge_deck_component.create_time is
'创建时间';

comment on column tb_bridge_deck_component.modify_time is
'修改时间';

comment on column tb_bridge_deck_component.creator is
'创建者';

comment on column tb_bridge_deck_component.status is
'1：正常 0：回收';


/*==============================================================*/
/* Table: tb_bridge_supcomponent                                */
/*==============================================================*/
drop table IF EXISTS tb_bridge_supcomponent;

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
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   creator              VARCHAR(32)          null,
   status               INT2                 null,
   constraint PK_TB_BRIDGE_SUPCOMPONENT primary key (id)
);

comment on table tb_bridge_supcomponent is
'桥梁上部构件实例';

comment on column tb_bridge_supcomponent.id is
'主键id';

comment on column tb_bridge_supcomponent.supstructure_id is
'桥梁上部结构id';

comment on column tb_bridge_supcomponent.component_id is
'构件类型id';

comment on column tb_bridge_supcomponent.code is
'结构编号';

comment on column tb_bridge_supcomponent.initial_weight is
'原始权重';

comment on column tb_bridge_supcomponent.actual_weight is
'实际权重';

comment on column tb_bridge_supcomponent.deduct is
'扣分';

comment on column tb_bridge_supcomponent.web_plate_hight is
'腹板高度';

comment on column tb_bridge_supcomponent.wing_plate_width is
'翼板宽度';

comment on column tb_bridge_supcomponent.baseplate_width is
'底板宽度';

comment on column tb_bridge_supcomponent.diaphragms_height is
'横隔板高度';

comment on column tb_bridge_supcomponent.diaphragms_number is
'横隔板道数';

comment on column tb_bridge_supcomponent.cantilever_lenght is
'悬臂长度';

comment on column tb_bridge_supcomponent.hanging_beam_length is
'挂梁长度';

comment on column tb_bridge_supcomponent.create_time is
'创建时间';

comment on column tb_bridge_supcomponent.modify_time is
'修改时间';

comment on column tb_bridge_supcomponent.creator is
'创建者';

comment on column tb_bridge_supcomponent.status is
'1：正常 0：回收';


/*==============================================================*/
/* Table: tb_bridge_subcomponent                                */
/*==============================================================*/
drop table IF EXISTS tb_bridge_subcomponent;

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
   creator              VARCHAR(32)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_BRIDGE_SUBCOMPONENT primary key (id)
);

comment on table tb_bridge_subcomponent is
'桥梁下部构件实例';

comment on column tb_bridge_subcomponent.id is
'主键id';

comment on column tb_bridge_subcomponent.bridge_substructure_id is
'桥梁下部结构id';

comment on column tb_bridge_subcomponent.component_id is
'构件类型id';

comment on column tb_bridge_subcomponent.code is
'结构编号';

comment on column tb_bridge_subcomponent.initial_weight is
'原始权重';

comment on column tb_bridge_subcomponent.actual_weight is
'实际权重';

comment on column tb_bridge_subcomponent.deduct is
'扣分';

comment on column tb_bridge_subcomponent.cap_beams_height is
'盖梁高度';

comment on column tb_bridge_subcomponent.cap_beams_width is
'盖梁宽度';

comment on column tb_bridge_subcomponent.cap_beams_thick is
'盖梁厚度';

comment on column tb_bridge_subcomponent.abutment_height is
'台身高度';

comment on column tb_bridge_subcomponent.abutment_width is
'台身宽度';

comment on column tb_bridge_subcomponent.abutment_thick is
'台身厚度';

comment on column tb_bridge_subcomponent.abutment_cap_height is
'台帽高度';

comment on column tb_bridge_subcomponent.pier_shape is
'墩身形状 1:矩形 2:原型';

comment on column tb_bridge_subcomponent.pier_height is
'墩身高度';

comment on column tb_bridge_subcomponent.pier_width is
'墩身宽度';

comment on column tb_bridge_subcomponent.pier_thick is
'墩身厚度';

comment on column tb_bridge_subcomponent.pier_radius is
'墩身半径';

comment on column tb_bridge_subcomponent.is_abutment is
'有无桥台 1:有 0:没有';

comment on column tb_bridge_subcomponent.pier_abutment_id is
'墩台id';

comment on column tb_bridge_subcomponent.creator is
'创建者';

comment on column tb_bridge_subcomponent.status is
'1：正常 0：被删除';

comment on column tb_bridge_subcomponent.create_time is
'创建时间';

comment on column tb_bridge_subcomponent.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_disease_instance                                   */
/*==============================================================*/
drop table IF EXISTS tb_disease_instance;

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

comment on table tb_disease_instance is
'桥梁检测病害实例';

comment on column tb_disease_instance.id is
'主键id';

comment on column tb_disease_instance.part_type is
'1：桥面系构件 2：上部构件 3：下部构件';

comment on column tb_disease_instance.target_id is
'构件实例id';

comment on column tb_disease_instance.disease_id is
'病害id';

comment on column tb_disease_instance.length is
'长度';

comment on column tb_disease_instance.width is
'宽度';

comment on column tb_disease_instance.depth is
'深度';

comment on column tb_disease_instance.seam_length is
'缝长';

comment on column tb_disease_instance.seam_width is
'缝宽';

comment on column tb_disease_instance.angle is
'角度';

comment on column tb_disease_instance.degree is
'程度';

comment on column tb_disease_instance.x_axis is
'X坐标';

comment on column tb_disease_instance.y_axis is
'Y坐标';

comment on column tb_disease_instance.remark is
'备注';

comment on column tb_disease_instance.height_difference is
'高度差';

comment on column tb_disease_instance.deflection_value is
'下挠值';

comment on column tb_disease_instance.trend is
'走向';

comment on column tb_disease_instance.number is
'数量';

comment on column tb_disease_instance.crack_axis is
'裂缝坐标';

comment on column tb_disease_instance.deduct is
'扣分值';

comment on column tb_disease_instance.sort is
'序号';

comment on column tb_disease_instance.creator is
'创建者';

comment on column tb_disease_instance.is_delete is
'是否删除操作';

comment on column tb_disease_instance.status is
'1：正常 0：回收';

comment on column tb_disease_instance.create_time is
'创建时间';

comment on column tb_disease_instance.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_disease_instance_record                            */
/*==============================================================*/
drop table IF EXISTS tb_disease_instance_record;

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

comment on table tb_disease_instance_record is
'桥梁检测病害实例记录表';

comment on column tb_disease_instance_record.id is
'主键id';

comment on column tb_disease_instance_record.tdi_id is
'桥梁检测病害实例id';

comment on column tb_disease_instance_record.part_type is
'1：桥面系构件 2：上部构件 3：下部构件';

comment on column tb_disease_instance_record.target_id is
'构件实例id';

comment on column tb_disease_instance_record.disease_id is
'病害id';

comment on column tb_disease_instance_record.length is
'长度';

comment on column tb_disease_instance_record.width is
'宽度';

comment on column tb_disease_instance_record.depth is
'深度';

comment on column tb_disease_instance_record.seam_length is
'缝长';

comment on column tb_disease_instance_record.seam_width is
'缝宽';

comment on column tb_disease_instance_record.angle is
'角度';

comment on column tb_disease_instance_record.degree is
'程度';

comment on column tb_disease_instance_record.x_axis is
'X坐标';

comment on column tb_disease_instance_record.y_axis is
'Y坐标';

comment on column tb_disease_instance_record.remark is
'备注';

comment on column tb_disease_instance_record.height_difference is
'高度差';

comment on column tb_disease_instance_record.deflection_value is
'下挠值';

comment on column tb_disease_instance_record.trend is
'走向';

comment on column tb_disease_instance_record.number is
'数量';

comment on column tb_disease_instance_record.deduct is
'扣分值';

comment on column tb_disease_instance_record.sort is
'序号';

comment on column tb_disease_instance_record.crack_axis is
'裂缝坐标';

comment on column tb_disease_instance_record.creator is
'创建者';

comment on column tb_disease_instance_record.status is
'1：正常 0：回收';

comment on column tb_disease_instance_record.create_time is
'创建时间';

comment on column tb_disease_instance_record.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_bridge_score                                       */
/*==============================================================*/
drop table IF EXISTS tb_bridge_score;

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

comment on table tb_bridge_score is
'桥梁评分表';

comment on column tb_bridge_score.id is
'主键id';

comment on column tb_bridge_score.target_id is
'目标id';

comment on column tb_bridge_score.type is
'1：结构物评分 2：线路评分 3：桥面系 4：上部结构 5：下部结构 6：墩台评分 7：要素 8：跨';

comment on column tb_bridge_score.bridge_condition_index is
'BCI分数';

comment on column tb_bridge_score.bridge_structure_index is
'BSI分数';

comment on column tb_bridge_score.rating_level is
'评定等级';

comment on column tb_bridge_score.evaluation_unit is
'评价单位';

comment on column tb_bridge_score.evaluation_time is
'评价时间';

comment on column tb_bridge_score.evaluator_id is
'评价人员id（对应用户id）';

comment on column tb_bridge_score.score is
'评分';

comment on column tb_bridge_score.create_time is
'创建时间';

comment on column tb_bridge_score.modify_time is
'修改时间';

comment on column tb_bridge_score.status is
'状态';


/*==============================================================*/
/* Table: tb_pier_abutment                                      */
/*==============================================================*/
drop table IF EXISTS tb_pier_abutment;

create table tb_pier_abutment (
   id                   SERIAL not null,
   bridge_substructure_id INT4                 null,
   code                 VARCHAR(16)          null,
   constraint PK_TB_PIER_ABUTMENT primary key (id)
);

comment on table tb_pier_abutment is
'墩台表';

comment on column tb_pier_abutment.id is
'id主键';

comment on column tb_pier_abutment.bridge_substructure_id is
'所属下部结构id';

comment on column tb_pier_abutment.code is
'墩台号';


/*==============================================================*/
/* Table: tb_bridge_weight                                      */
/*==============================================================*/
drop table IF EXISTS tb_bridge_weight;

create table tb_bridge_weight (
   id                   SERIAL not null,
   bridge_type_id       INT4                 not null,
   part_type            INT2                 not null,
   weight               FLOAT8               not null,
   constraint PK_TB_BRIDGE_WEIGHT primary key (id)
);

comment on table tb_bridge_weight is
'桥梁部位权重表';

comment on column tb_bridge_weight.bridge_type_id is
'桥梁类型id';

comment on column tb_bridge_weight.part_type is
'1：桥面系 2：上部结构 3：下部结构';

comment on column tb_bridge_weight.weight is
'权重';


/*==============================================================*/
/* Table: tb_bridge_type                                        */
/*==============================================================*/
drop table IF EXISTS tb_bridge_type;

create table tb_bridge_type (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   constraint PK_TB_BRIDGE_TYPE primary key (id)
);

comment on table tb_bridge_type is
'桥梁类型表';

comment on column tb_bridge_type.id is
'主键id';

comment on column tb_bridge_type.name is
'名称';


/*==============================================================*/
/* Table: tb_bridge_type_component_rel                          */
/*==============================================================*/
drop table IF EXISTS tb_bridge_type_component_rel;

create table tb_bridge_type_component_rel (
   id                   SERIAL not null,
   bridge_type_id       INT4                 null,
   component_id         INT4                 null,
   part_type            INT2                 null,
   pier_abutment        INT2                 null,
   initial_weight       FLOAT8               null,
   constraint PK_TB_BRIDGE_TYPE_COMPONENT_RE primary key (id)
);

comment on table tb_bridge_type_component_rel is
'桥梁类型构件关联表';

comment on column tb_bridge_type_component_rel.id is
'主键id';

comment on column tb_bridge_type_component_rel.bridge_type_id is
'桥梁类型id';

comment on column tb_bridge_type_component_rel.component_id is
'构件id';

comment on column tb_bridge_type_component_rel.part_type is
'1：桥面系 2：上部结构 3：下部结构';

comment on column tb_bridge_type_component_rel.pier_abutment is
'0：桥墩  1：桥台';

comment on column tb_bridge_type_component_rel.initial_weight is
'原始权重';


/*==============================================================*/
/* Table: tb_component                                          */
/*==============================================================*/
drop table IF EXISTS tb_component;

create table tb_component (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   need_judge           INT2                 null,
   constraint PK_TB_COMPONENT primary key (id)
);

comment on table tb_component is
'桥梁构件类型表';

comment on column tb_component.id is
'主键id';

comment on column tb_component.name is
'构件名称';

comment on column tb_component.need_judge is
'1：首次添加需判断是否创建 0：首次添加无需判断';


/*==============================================================*/
/* Table: tb_component_disease_rel                              */
/*==============================================================*/
drop table IF EXISTS tb_component_disease_rel;

create table tb_component_disease_rel (
   id                   SERIAL not null,
   component_id         INT4                 null,
   bridge_type_id       INT4                 null,
   part_type            INT2                 null,
   disease_id           INT4                 null,
   groups               INT4                 null,
   constraint PK_TB_COMPONENT_DISEASE_REL primary key (id)
);

comment on table tb_component_disease_rel is
'构件病害关联表';

comment on column tb_component_disease_rel.id is
'主键id';

comment on column tb_component_disease_rel.component_id is
'桥梁构件_id';

comment on column tb_component_disease_rel.bridge_type_id is
'桥梁类型id';

comment on column tb_component_disease_rel.part_type is
'部位类型';

comment on column tb_component_disease_rel.disease_id is
'病害id';

comment on column tb_component_disease_rel.groups is
'属性组组别';


/*==============================================================*/
/* Table: tb_bridge_disease                                     */
/*==============================================================*/
drop table IF EXISTS tb_bridge_disease;

create table tb_bridge_disease (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   code                 VARCHAR(8)           null,
   constraint PK_TB_BRIDGE_DISEASE primary key (id)
);

comment on table tb_bridge_disease is
'桥梁病害表';

comment on column tb_bridge_disease.id is
'主键id';

comment on column tb_bridge_disease.name is
'病害名称';

comment on column tb_bridge_disease.code is
'病害编码缩写';


/*==============================================================*/
/* Table: tb_deduction                                          */
/*==============================================================*/
drop table IF EXISTS tb_deduction;

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

comment on table tb_deduction is
'桥梁病害扣分表';

comment on column tb_deduction.id is
'主键id';

comment on column tb_deduction.component_id is
'构件id';

comment on column tb_deduction.disease_id is
'病害id';

comment on column tb_deduction.degress is
'程度';

comment on column tb_deduction.upper is
'范围上限';

comment on column tb_deduction.value is
'扣分值';

comment on column tb_deduction.type is
'1：百分比或范围 2：个数 3：字符串';

comment on column tb_deduction.level_limit is
'最高等级不得超过D级 0：不限制  1：限制';


/*==============================================================*/
/* Table: tb_property_group                                     */
/*==============================================================*/
drop table IF EXISTS tb_property_group;

create table tb_property_group (
   id                   SERIAL not null,
   property_id          INT4                 null,
   groups               INT4                 null,
   accord               INT2                 null,
   constraint PK_TB_PROPERTY_GROUP primary key (id)
);

comment on table tb_property_group is
'属性组表';

comment on column tb_property_group.id is
'主键id';

comment on column tb_property_group.property_id is
'属性id';

comment on column tb_property_group.groups is
'组别';

comment on column tb_property_group.accord is
'扣分依据';

drop table IF EXISTS tb_property_group_option_rel;

/*==============================================================*/
/* Table: tb_property_group_option_rel                          */
/*==============================================================*/
create table tb_property_group_option_rel (
   id                   SERIAL not null,
   property_group_id    INT4                 null,
   property_option_id   INT4                 null,
   component_disease_rel INT4                 null,
   seq_num              INT4                 null,
   constraint PK_TB_PROPERTY_GROUP_OPTION_RE primary key (id)
);

comment on table tb_property_group_option_rel is
'属性组选项关联表';

comment on column tb_property_group_option_rel.id is
'主键id';

comment on column tb_property_group_option_rel.property_group_id is
'属性组id';

comment on column tb_property_group_option_rel.property_option_id is
'选项id';

comment on column tb_property_group_option_rel.component_disease_rel is
'构件病害关联表id';

comment on column tb_property_group_option_rel.seq_num is
'顺序';


/*==============================================================*/
/* Table: tb_property_rel                                       */
/*==============================================================*/
drop table IF EXISTS tb_property_rel;

create table tb_property_rel (
   id                   SERIAL not null,
   target_id            INT4                 null,
   property_id          INT4                 null,
   target_type          INT2                 null,
   part_type            INT2                 null,
   bridge_type_id       INT4                 null,
   constraint PK_TB_PROPERTY_REL primary key (id)
);

comment on table tb_property_rel is
'属性关联表';

comment on column tb_property_rel.id is
'主键id';

comment on column tb_property_rel.target_id is
'目标id';

comment on column tb_property_rel.property_id is
'属性id';

comment on column tb_property_rel.target_type is
'1：部位 2：桥梁构件关联 ';

comment on column tb_property_rel.part_type is
'1：桥面系 2：上部结构 3：下部结构';

comment on column tb_property_rel.bridge_type_id is
'桥梁类型id';


/*==============================================================*/
/* Table: tb_property                                           */
/*==============================================================*/
drop table IF EXISTS tb_property;

create table tb_property (
   id                   SERIAL not null,
   name                 VARCHAR(16)          null,
   english_name         VARCHAR(32)          null,
   unit                 VARCHAR(16)          null,
   type                 INT2                 null,
   constraint PK_TB_PROPERTY primary key (id)
);

comment on table tb_property is
'桥属性表';

comment on column tb_property.id is
'主键id';

comment on column tb_property.name is
'属性名';

comment on column tb_property.english_name is
'属性名英文';

comment on column tb_property.unit is
'单位';

comment on column tb_property.type is
'1：常规属性 2：单选框类型 1代表有，0代表无  3：下拉框类型 4：多选';


/*==============================================================*/
/* Table: tb_option_property_rel                                */
/*==============================================================*/
drop table IF EXISTS tb_option_property_rel;

create table tb_option_property_rel (
   id                   SERIAL not null,
   property_option_rel_id INT4                 null,
   property_id          INT4                 null,
   constraint PK_TB_OPTION_PROPERTY_REL primary key (id)
);

comment on table tb_option_property_rel is
'选项子属性关联表';

comment on column tb_option_property_rel.id is
'主键id';

comment on column tb_option_property_rel.property_option_rel_id is
'属性选项关联id';

comment on column tb_option_property_rel.property_id is
'属性id';


/*==============================================================*/
/* Table: tb_property_option_rel                                */
/*==============================================================*/
drop table IF EXISTS tb_property_option_rel;

create table tb_property_option_rel (
   id                   SERIAL not null,
   property_options_id  INT4                 null,
   property_rel_id      INT4                 null,
   property_id          INT4                 null,
   constraint PK_TB_PROPERTY_OPTION_REL primary key (id)
);

comment on table tb_property_option_rel is
'属性选项关联表';

comment on column tb_property_option_rel.id is
'主键id';

comment on column tb_property_option_rel.property_options_id is
'属性选项id';

comment on column tb_property_option_rel.property_rel_id is
'属性关联表id';

comment on column tb_property_option_rel.property_id is
'属性表id';


/*==============================================================*/
/* Table: tb_property_option                                    */
/*==============================================================*/
drop table IF EXISTS tb_property_option;

create table tb_property_option (
   id                   SERIAL not null,
   option_name          VARCHAR(16)          null,
   constraint PK_TB_PROPERTY_OPTION primary key (id)
);

comment on table tb_property_option is
'属性选项表';

comment on column tb_property_option.id is
'主键id';

comment on column tb_property_option.option_name is
'选项名称';
