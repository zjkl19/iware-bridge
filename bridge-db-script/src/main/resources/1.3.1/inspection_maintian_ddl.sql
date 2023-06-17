---------------------------------------------------------------------------
--日常巡查、维修养护模块数据库
---------------------------------------------------------------------------

drop table IF EXISTS tb_plan_info;

/*==============================================================*/
/* Table: tb_plan_info                                          */
/*==============================================================*/
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

comment on table tb_plan_info is
'巡查维养计划表';

comment on column tb_plan_info.id is
'主键id';

comment on column tb_plan_info.name is
'计划名称';

comment on column tb_plan_info.project_id is
'所属项目';

comment on column tb_plan_info.plan_time is
'巡查/维修月份';

comment on column tb_plan_info.budget is
'预算金额';

comment on column tb_plan_info.expenditure is
'实际金额';

comment on column tb_plan_info.type is
'1:日常巡查 2:经常检查 3:特殊检查 4:维修养护';

comment on column tb_plan_info.user_id is
'创建者id';

comment on column tb_plan_info.status is
'0:未执行 1:执行中 2:已完成 ';

comment on column tb_plan_info.create_time is
'创建时间';

comment on column tb_plan_info.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_plan_detail                                        */
/*==============================================================*/
drop table IF EXISTS tb_plan_detail;

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

comment on table tb_plan_detail is
'计划详情';

comment on column tb_plan_detail.id is
'主键id';

comment on column tb_plan_detail.plan_id is
'计划id';

comment on column tb_plan_detail.structure_id is
'结构物id';

comment on column tb_plan_detail.inspector is
'巡查人员';

comment on column tb_plan_detail.inspection_unit is
'巡查单位';

comment on column tb_plan_detail.file_save_collator is
'核对存档人';

comment on column tb_plan_detail.inspection_time is
'巡查日期';

comment on column tb_plan_detail.weather is
'天气';

comment on column tb_plan_detail.longitude is
'经度';

comment on column tb_plan_detail.latitude is
'纬度';

comment on column tb_plan_detail.location is
'位置信息';

comment on column tb_plan_detail.status is
'0:未完成 1:已完成 2:已超时';

comment on column tb_plan_detail.create_time is
'创建日期';

comment on column tb_plan_detail.modify_time is
'修改日期';

/*==============================================================*/
/* Table: tb_inspection_disease_instance                        */
/*==============================================================*/
drop table IF EXISTS tb_inspection_disease_instance;

create table tb_inspection_disease_instance (
   id                   SERIAL not null,
   plan_detail_id       INT4                 null,
   inspection_disease_id INT4                 null,
   quantity             INT2                 null,
   strategy             INT2                 null,
   exception_part       VARCHAR(64)          null,
   remarks              VARCHAR(256)         null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_INSPECTION_DISEASE_INSTA primary key (id)
);

comment on table tb_inspection_disease_instance is
'巡检病害实例表';

comment on column tb_inspection_disease_instance.id is
'主键id';

comment on column tb_inspection_disease_instance.plan_detail_id is
'计划详情id';

comment on column tb_inspection_disease_instance.inspection_disease_id is
'巡检病害id';

comment on column tb_inspection_disease_instance.quantity is
'病害数量';

comment on column tb_inspection_disease_instance.strategy is
'1:观察 2:报修 3:监测 4:即修 5:更换 6:增设';

comment on column tb_inspection_disease_instance.exception_part is
'异常部位';

comment on column tb_inspection_disease_instance.remarks is
'备注';

comment on column tb_inspection_disease_instance.status is
'0:未加入维养计划 1:待修 2:已修';

comment on column tb_inspection_disease_instance.create_time is
'创建时间';

comment on column tb_inspection_disease_instance.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_inspection_disease                                 */
/*==============================================================*/
drop table IF EXISTS tb_inspection_disease;

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

comment on table tb_inspection_disease is
'巡检病害表';

comment on column tb_inspection_disease.id is
'主键id';

comment on column tb_inspection_disease.name is
'病害名称';

comment on column tb_inspection_disease.disease_part is
'病害部位';

comment on column tb_inspection_disease.check_item is
'检查项';

comment on column tb_inspection_disease.damage_type is
'损害类型';

comment on column tb_inspection_disease.unit is
'单位';

comment on column tb_inspection_disease.structure_type is
'1:桥梁病害 2:隧道病害';

comment on column tb_inspection_disease.type is
'1:常规 2:多选 3:输入框';


/*==============================================================*/
/* Table: tb_inspection_disease_option                          */
/*==============================================================*/
drop table IF EXISTS tb_inspection_disease_option;

create table tb_inspection_disease_option (
   id                   SERIAL not null,
   inspection_disease_id INT4                 null,
   name                 VARCHAR(32)          null,
   seq_num              INT2                 null,
   option_status        INT2                 null,
   constraint PK_TB_INSPECTION_DISEASE_OPTIO primary key (id)
);

comment on table tb_inspection_disease_option is
'巡检病害选项表';

comment on column tb_inspection_disease_option.id is
'主键id';

comment on column tb_inspection_disease_option.inspection_disease_id is
'巡检病害id';

comment on column tb_inspection_disease_option.name is
'名称';

comment on column tb_inspection_disease_option.seq_num is
'排序,下标从1开始';

comment on column tb_inspection_disease_option.option_status is
'0:病害 1:完好';


/*==============================================================*/
/* Table: tb_disease_instance_option_rel                        */
/*==============================================================*/
drop table IF EXISTS  tb_disease_instance_option_rel;

create table tb_disease_instance_option_rel (
   id                   SERIAL not null,
   inspection_disease_instance_id INT4                 not null,
   inspection_disease_option_id INT4                 null,
   constraint PK_TB_DISEASE_INSTANCE_OPTION_ primary key (id)
);

comment on table tb_disease_instance_option_rel is
'巡查病害实例选项关联表';

comment on column tb_disease_instance_option_rel.id is
'主键id';

comment on column tb_disease_instance_option_rel.inspection_disease_instance_id is
'病害实例表id';

comment on column tb_disease_instance_option_rel.inspection_disease_option_id is
'病害选项表id';


/*==============================================================*/
/* Table: tb_maintain_item                                      */
/*==============================================================*/
drop table IF EXISTS tb_maintain_item;

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

comment on table tb_maintain_item is
'维修项表';

comment on column tb_maintain_item.id is
'主键id';

comment on column tb_maintain_item.plan_id is
'计划id';

comment on column tb_maintain_item.structure_id is
'结构物id';

comment on column tb_maintain_item.disease_instance_id is
'病害实例id';

comment on column tb_maintain_item.name is
'维修项名称';

comment on column tb_maintain_item.type is
'0:日常保养 1:小修 2:中修 3:大修';

comment on column tb_maintain_item.proposed_time is
'拟定日期';

comment on column tb_maintain_item.maintain_time is
'維修日期';

comment on column tb_maintain_item.creator is
'维修人员';

comment on column tb_maintain_item.content is
'维修内容';

comment on column tb_maintain_item.method is
'维修方法';

comment on column tb_maintain_item.result is
'维修结果';

comment on column tb_maintain_item.maintenance_unit is
'维修单位';

comment on column tb_maintain_item.quantities is
'工程量';

comment on column tb_maintain_item.status is
'0:未完成 1:已完成 2:已超时';

comment on column tb_maintain_item.create_time is
'创建日期';

comment on column tb_maintain_item.modify_time is
'修改日期';
