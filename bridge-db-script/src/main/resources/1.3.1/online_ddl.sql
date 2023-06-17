---------------------------------------------------------------------------
--在线监测模块数据库
---------------------------------------------------------------------------

/*==============================================================*/
/* Table: tb_sensor                                             */
/*==============================================================*/
drop table IF EXISTS tb_sensor;

create table tb_sensor (
   id                   SERIAL not null,
   component_id         INT4                 null,
   company_id           INT4                 null,
   structure_id         INT4                 null,
   sensor_type_id       INT4                 null,
   sensor_details_id    INT4                 null,
   sensor_principle_id  INT4                 null,
   name                 VARCHAR(32)          null,
   describe             VARCHAR(64)          null,
   deployment_date      TIMESTAMP            null,
   section_position     VARCHAR(32)          null,
   collector_information VARCHAR(256)         null,
   sensor_coding        VARCHAR(64)          null,
   x_axis               FLOAT4               null,
   y_axis               FLOAT4               null,
   z_axis               FLOAT4               null,
   unit                 VARCHAR(16)          null,
   initial_value        FLOAT4               null,
   warning_interval     INT4                 null,
   first_warning_upper  FLOAT4               null,
   first_warning_lower  FLOAT4               null,
   second_warning_upper FLOAT4               null,
   second_warning_lower FLOAT4               null,
   third_warning_upper  FLOAT4               null,
   third_warning_lower  FLOAT4               null,
   sampling_frequency   VARCHAR(16)          null,
   production_date      TIMESTAMP            null,
   production_coding    VARCHAR(64)          null,
   photo_url            VARCHAR(64)          null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_SENSOR primary key (id)
);

comment on table tb_sensor is
'传感器信息表';

comment on column tb_sensor.id is
'主键id';

comment on column tb_sensor.component_id is
'构件id';

comment on column tb_sensor.company_id is
'产商id';

comment on column tb_sensor.structure_id is
'结构物id';

comment on column tb_sensor.sensor_type_id is
'传感器类型id';

comment on column tb_sensor.sensor_details_id is
'传感器细项id';

comment on column tb_sensor.sensor_principle_id is
'传感器原理id';

comment on column tb_sensor.name is
'测点编号';

comment on column tb_sensor.describe is
'测点编号说明';

comment on column tb_sensor.deployment_date is
'部署日期';

comment on column tb_sensor.section_position is
'截面位置';

comment on column tb_sensor.collector_information is
'采集器信息';

comment on column tb_sensor.sensor_coding is
'传感器编号(数据读取编码)';

comment on column tb_sensor.x_axis is
'X坐标';

comment on column tb_sensor.y_axis is
'Y坐标';

comment on column tb_sensor.z_axis is
'Z坐标';

comment on column tb_sensor.unit is
'单位';

comment on column tb_sensor.initial_value is
'初始值';

comment on column tb_sensor.warning_interval is
'预警间隔';

comment on column tb_sensor.first_warning_upper is
'一级预警上限';

comment on column tb_sensor.first_warning_lower is
'一级预警下限';

comment on column tb_sensor.second_warning_upper is
'二级预警上限';

comment on column tb_sensor.second_warning_lower is
'二级预警下限';

comment on column tb_sensor.third_warning_upper is
'三级预警上限';

comment on column tb_sensor.third_warning_lower is
'三级预警下限';

comment on column tb_sensor.sampling_frequency is
'采样频率';

comment on column tb_sensor.production_date is
'出产日期';

comment on column tb_sensor.production_coding is
'出产编号';

comment on column tb_sensor.photo_url is
'图片位置';

comment on column tb_sensor.status is
'状态';

comment on column tb_sensor.create_time is
'创建时间';

comment on column tb_sensor.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_company                                            */
/*==============================================================*/
drop table IF EXISTS tb_company;

create table tb_company (
   id                   SERIAL not null,
   company              VARCHAR(32)          null,
   remarks              VARCHAR(64)          null,
   constraint PK_TB_COMPANY primary key (id)
);

comment on table tb_company is
'产商表';

comment on column tb_company.id is
'主键id';

comment on column tb_company.company is
'产商';

comment on column tb_company.remarks is
'备注';

/*==============================================================*/
/* Table: tb_sensor_type                                        */
/*==============================================================*/
drop table IF EXISTS tb_sensor_type;

create table tb_sensor_type (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   remarks              VARCHAR(64)          null,
   constraint PK_TB_SENSOR_TYPE primary key (id)
);

comment on table tb_sensor_type is
'传感器类型表';

comment on column tb_sensor_type.id is
'主键id';

comment on column tb_sensor_type.name is
'名称';

comment on column tb_sensor_type.remarks is
'备注';


/*==============================================================*/
/* Table: tb_sensor_details                                     */
/*==============================================================*/
drop table IF EXISTS tb_sensor_details;

create table tb_sensor_details (
   id                   SERIAL not null,
   detail_name          VARCHAR(16)          null,
   sensor_type_id       INT4                 null,
   unit                 VARCHAR(16)          null,
   constraint PK_TB_SENSOR_DETAILS primary key (id)
);

comment on table tb_sensor_details is
'传感器细项表';

comment on column tb_sensor_details.id is
'主键id';

comment on column tb_sensor_details.detail_name is
'细项名称';

comment on column tb_sensor_details.sensor_type_id is
'所属类型id';

comment on column tb_sensor_details.unit is
'单位';


/*==============================================================*/
/* Table: tb_sensor_principle                                   */
/*==============================================================*/
drop table IF EXISTS tb_sensor_principle;

create table tb_sensor_principle (
   id                   SERIAL not null,
   principle            VARCHAR(16)          null,
   remarks              VARCHAR(64)          null,
   constraint PK_TB_SENSOR_PRINCIPLE primary key (id)
);

comment on table tb_sensor_principle is
'传感器原理表';

comment on column tb_sensor_principle.id is
'主键id';

comment on column tb_sensor_principle.principle is
'原理名称';

comment on column tb_sensor_principle.remarks is
'备注';


/*==============================================================*/
/* Table: tb_warning_record                                     */
/*==============================================================*/
drop table IF EXISTS tb_warning_record;

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

comment on table tb_warning_record is
'预警记录表';

comment on column tb_warning_record.id is
'主键id';

comment on column tb_warning_record.structure_id is
'结构物id';

comment on column tb_warning_record.component_id is
'构件id';

comment on column tb_warning_record.warning_time is
'预警时间';

comment on column tb_warning_record.level is
'1: I级 2:II级 3:III级';

comment on column tb_warning_record.handler_user_id is
'处理人id';

comment on column tb_warning_record.measures is
'处理措施';

comment on column tb_warning_record.handler_time is
'处理时间';

comment on column tb_warning_record.send_message is
'0: 否 1:是';

comment on column tb_warning_record.sensor_id is
'传感器id';

comment on column tb_warning_record.sensor_details_id is
'传感器细项id';

comment on column tb_warning_record.sensor_coding is
'传感器编码';

comment on column tb_warning_record.sensor_name is
'传感器名称';

comment on column tb_warning_record.value is
'测值';

comment on column tb_warning_record.machine_id is
'传感器机器码';

comment on column tb_warning_record.status is
'0:未处理 1:已处理';

comment on column tb_warning_record.create_time is
'创建时间';

comment on column tb_warning_record.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_sensor_record                                         */
/*==============================================================*/
drop table IF EXISTS tb_sensor_record;

create table tb_sensor_record (
   id                   SERIAL not null,
   sensor_id            INT4                 null,
   operate_time         TIMESTAMP            null,
   type                 VARCHAR(16)          null,
   reason               VARCHAR(32)          null,
   content              VARCHAR(256)         null,
   status               INT2                 null,
   create_time          TIMESTAMP            null,
   modify_time          TIMESTAMP            null,
   constraint PK_TB_SENSOR_RECORD primary key (id)
);

comment on table tb_sensor_record is
'传感器维护记录';

comment on column tb_sensor_record.id is
'主键id';

comment on column tb_sensor_record.sensor_id is
'传感器id';

comment on column tb_sensor_record.operate_time is
'操作日期';

comment on column tb_sensor_record.type is
'操作类型';

comment on column tb_sensor_record.reason is
'操作原因';

comment on column tb_sensor_record.content is
'操作内容';

comment on column tb_sensor_record.status is
'状态';

comment on column tb_sensor_record.create_time is
'创建时间';

comment on column tb_sensor_record.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_sensor_log                                       */
/*==============================================================*/
drop table IF EXISTS tb_sensor_log;

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

comment on table tb_sensor_log is
'维护日志表';

comment on column tb_sensor_log.id is
'主键id';

comment on column tb_sensor_log.project_id is
'项目id';

comment on column tb_sensor_log.describe is
'日志描述';

comment on column tb_sensor_log.maintain_time is
'维护时间';

comment on column tb_sensor_log.solution is
'解决方案';

comment on column tb_sensor_log.remarks is
'备注';

comment on column tb_sensor_log.status is
'状态';

comment on column tb_sensor_log.create_time is
'创建时间';

comment on column tb_sensor_log.modify_time is
'修改时间';


/*==============================================================*/
/* Table: tb_axle_type                                          */
/*==============================================================*/

drop table IF EXISTS tb_axle_type;
create table tb_axle_type (
   id                   SERIAL not null,
   name                 VARCHAR(32)          null,
   constraint PK_TB_AXLE_TYPE primary key (id)
);

comment on table tb_axle_type is
'车轴类型表';

comment on column tb_axle_type.id is
'主键id';

comment on column tb_axle_type.name is
'名称';


/*==============================================================*/
/* Table: tb_axle_type_model_rel                                */
/*==============================================================*/
drop table IF EXISTS tb_axle_type_model_rel;

create table tb_axle_type_model_rel (
   id                   SERIAL not null,
   axle_type_id         INT4                 null,
   model_id             INT4                 null,
   constraint PK_TB_AXLE_TYPE_MODEL_REL primary key (id)
);

comment on table tb_axle_type_model_rel is
'车轴类型表车型关联表';

comment on column tb_axle_type_model_rel.id is
'主键id';

comment on column tb_axle_type_model_rel.axle_type_id is
'车轴类型id';

comment on column tb_axle_type_model_rel.model_id is
'车型id';


/*==============================================================*/
/* Table: composedata                                           */
/*==============================================================*/
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

comment on table composedata is
'基康数据表';

comment on column composedata.id is
'主键id';

comment on column composedata.collecttime is
'采集时间';

comment on column composedata.flag is
'标志';

comment on column composedata.paraid is
'传感器编码';

comment on column composedata.paratypecode is
'传感器编码2';

comment on column composedata.paravalue is
'数值';

comment on column composedata.rtucode is
'r编码';

comment on column composedata.systemtime is
'系统时间';


/*==============================================================*/
/* Table: oridata                                               */
/*==============================================================*/
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

comment on table oridata is
'基康数据表';

comment on column oridata.id is
'主键id';

comment on column oridata.collecttime is
'采集时间';

comment on column oridata.flag is
'标志';

comment on column oridata.paraid is
'传感器编码';

comment on column oridata.paratypecode is
'传感器编码2';

comment on column oridata.paravalue is
'数值';

comment on column oridata.rtucode is
'r编码';

comment on column oridata.systemtime is
'系统时间';


/*==============================================================*/
/* Table: tb_sensor_converge                                    */
/*==============================================================*/
create table tb_sensor_converge (
   structure_id         INT4                 null,
   sensor_id            INT4                 null,
   sampling_time        TIMESTAMP            not null,
   value                FLOAT4               null,
   sensor_coding        VARCHAR(64)          null,
   sensor_details_id    INT4                 null
);

comment on table tb_sensor_converge is
'传感器汇聚表';

comment on column tb_sensor_converge.structure_id is
'结构物id';

comment on column tb_sensor_converge.sensor_id is
'传感器id';

comment on column tb_sensor_converge.sampling_time is
'采样时间';

comment on column tb_sensor_converge.value is
'实测值';

comment on column tb_sensor_converge.sensor_coding is
'传感器编码';

comment on column tb_sensor_converge.sensor_details_id is
'传感器细项id';


/*==============================================================*/
/* Table: tb_sensor_day                                         */
/*==============================================================*/
create table tb_sensor_day (
   max_value            FLOAT4               null,
   min_value            FLOAT4               null,
   avg_value            FLOAT4               null,
   sensor_coding        VARCHAR(64)          null,
   time                 TIMESTAMP            not null,
   sensor_details_id    INT4                 null
);

comment on table tb_sensor_day is
'传感器一小时内自动生成的数据';

comment on column tb_sensor_day.max_value is
'最大值（每1小时）';

comment on column tb_sensor_day.min_value is
'最小值（每1小时）';

comment on column tb_sensor_day.avg_value is
'平均值（每1小时）';

comment on column tb_sensor_day.sensor_coding is
'传感器编码';

comment on column tb_sensor_day.time is
'接收数据时间';

comment on column tb_sensor_day.sensor_details_id is
'传感器细项id';


/*==============================================================*/
/* Table: tb_sensor_hour                                        */
/*==============================================================*/
create table tb_sensor_hour (
   max_value            FLOAT4               null,
   min_value            FLOAT4               null,
   avg_value            FLOAT4               null,
   sensor_coding        VARCHAR(64)          null,
   time                 TIMESTAMP            not null,
   sensor_details_id    INT4                 null
);

comment on table tb_sensor_hour is
'传感器十分钟内自动生成数据';

comment on column tb_sensor_hour.max_value is
'最大值（每1小时）';

comment on column tb_sensor_hour.min_value is
'最小值（每1小时）';

comment on column tb_sensor_hour.avg_value is
'平均值（每1小时）';

comment on column tb_sensor_hour.sensor_coding is
'传感器编码';

comment on column tb_sensor_hour.time is
'接收数据时间';

comment on column tb_sensor_hour.sensor_details_id is
'传感器细项id';


/*==============================================================*/
/* Table: tb_sensor_minute                                      */
/*==============================================================*/
create table tb_sensor_minute (
   max_value            FLOAT4               null,
   min_value            FLOAT4               null,
   avg_value            FLOAT4               null,
   sensor_coding        VARCHAR(64)          null,
   time                 TIMESTAMP            not null,
   sensor_details_id    INT4                 null
);

comment on table tb_sensor_minute is
'传感器1小时内自动生成的数据(每1分)';

comment on column tb_sensor_minute.max_value is
'最大值（每1小时）';

comment on column tb_sensor_minute.min_value is
'最小值（每1小时）';

comment on column tb_sensor_minute.avg_value is
'平均值（每1小时）';

comment on column tb_sensor_minute.sensor_coding is
'传感器编码';

comment on column tb_sensor_minute.time is
'接收数据时间';

comment on column tb_sensor_minute.sensor_details_id is
'传感器细项id';


/*==============================================================*/
/* Table: tb_sensor_second                                      */
/*==============================================================*/
create table tb_sensor_second (
   max_value            FLOAT4               null,
   min_value            FLOAT4               null,
   avg_value            FLOAT4               null,
   sensor_coding        VARCHAR(64)          null,
   time                 TIMESTAMP            not null,
   sensor_details_id    INT4                 null
);

comment on table tb_sensor_second is
'传感器10分钟内自动生成的数据(每3秒)';

comment on column tb_sensor_second.max_value is
'最大值（每1小时）';

comment on column tb_sensor_second.min_value is
'最小值（每1小时）';

comment on column tb_sensor_second.avg_value is
'平均值（每1小时）';

comment on column tb_sensor_second.sensor_coding is
'传感器编码';

comment on column tb_sensor_second."time" is
'接收数据时间';

comment on column tb_sensor_second.sensor_details_id is
'传感器细项id';


/*==============================================================*/
/* Table: tb_sensor_second_one                                   */
/*==============================================================*/
create table tb_sensor_second_one (
   max_value            FLOAT4               null,
   min_value            FLOAT4               null,
   avg_value            FLOAT4               null,
   sensor_coding        VARCHAR(64)          null,
   time                 TIMESTAMP            not null,
   sensor_details_id    INT4                 null
);

comment on table tb_sensor_second_one is
'传感器10分钟内自动生成的数据(每1秒)';

comment on column tb_sensor_second_one.max_value is
'最大值（每1小时）';

comment on column tb_sensor_second_one.min_value is
'最小值（每1小时）';

comment on column tb_sensor_second_one.avg_value is
'平均值（每1小时）';

comment on column tb_sensor_second_one.sensor_coding is
'传感器编码';

comment on column tb_sensor_second_one."time" is
'接收数据时间';

comment on column tb_sensor_second_one.sensor_details_id is
'传感器细项id';

/*==============================================================*/
/* Table: tb_sensor_weight                                      */
/*==============================================================*/
drop table IF EXISTS tb_sensor_weight;

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

comment on table tb_sensor_weight is
'称重传感器';

comment on column tb_sensor_weight.message_type is
'报文类型';

comment on column tb_sensor_weight.reserved is
'预留';

comment on column tb_sensor_weight.direction is
'方向';

comment on column tb_sensor_weight.computer_number is
'网络通讯计算机编号';

comment on column tb_sensor_weight.command_code is
'命令码';

comment on column tb_sensor_weight.data_type is
'数据类型';

comment on column tb_sensor_weight.img_number is
'图片编号';

comment on column tb_sensor_weight.track_number is
'车道号';

comment on column tb_sensor_weight.sampling_time is
'检测时间';

comment on column tb_sensor_weight.lastrow is
'上下行';

comment on column tb_sensor_weight.single_shaft_nuber is
'单轴数';

comment on column tb_sensor_weight.sets_shaft_nuber is
'轴组数';

comment on column tb_sensor_weight.total_weight is
'总重';

comment on column tb_sensor_weight.overrun_rate is
'超限率';

comment on column tb_sensor_weight.models is
'车型';

comment on column tb_sensor_weight.left_wheel_one is
'左1轮重';

comment on column tb_sensor_weight.left_wheel_two is
'左2轮重';

comment on column tb_sensor_weight.left_wheel_three is
'左3轮重';

comment on column tb_sensor_weight.left_wheel_four is
'左4轮重';

comment on column tb_sensor_weight.left_wheel_five is
'左5轮重';

comment on column tb_sensor_weight.left_wheel_six is
'左6轮重';

comment on column tb_sensor_weight.left_wheel_seven is
'左7轮重';

comment on column tb_sensor_weight.left_wheel_eight is
'左8轮重';

comment on column tb_sensor_weight.right_wheel_one is
'右1轮重';

comment on column tb_sensor_weight.right_wheel_two is
'右2轮重';

comment on column tb_sensor_weight.right_wheel_three is
'右3轮重';

comment on column tb_sensor_weight.right_wheel_four is
'右4轮重';

comment on column tb_sensor_weight.right_wheel_five is
'右5轮重';

comment on column tb_sensor_weight.right_wheel_six is
'右6轮重';

comment on column tb_sensor_weight.right_wheel_seven is
'右7轮重';

comment on column tb_sensor_weight.right_wheel_eight is
'右8轮重';

comment on column tb_sensor_weight.spacing_one_two is
'轴1、2间距';

comment on column tb_sensor_weight.spacing_two_three is
'轴2、3间距';

comment on column tb_sensor_weight.spacing_three_four is
'轴3、4间距';

comment on column tb_sensor_weight.spacing_four_five is
'轴4、5间距';

comment on column tb_sensor_weight.spacing_five_six is
'轴5、6间距';

comment on column tb_sensor_weight.spacing_six_seven is
'轴6、7间距';

comment on column tb_sensor_weight.spacing_seven_eight is
'轴7、8间距';

comment on column tb_sensor_weight.transfinite_labeled is
'超限标示';

comment on column tb_sensor_weight.violation_code is
'违例码';

comment on column tb_sensor_weight.car_speed is
'车速';

comment on column tb_sensor_weight.acceleration is
'加速度';

comment on column tb_sensor_weight.equivalent_shaft is
'当量轴次';

comment on column tb_sensor_weight.license_plate is
'车牌';

comment on column tb_sensor_weight.sensor_coding is
'传感器编码';

comment on column tb_sensor_weight.sensor_details_id is
'传感器细项';

comment on column tb_sensor_weight.license_color is
'车牌颜色';

comment on column tb_sensor_weight.bridge_code is
'桥梁编号';

comment on column tb_sensor_weight.vehicle_length is
'车长度';
