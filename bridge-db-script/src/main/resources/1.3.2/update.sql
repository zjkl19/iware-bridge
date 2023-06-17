---------------------------------------------------------------------------
--更新数据库
---------------------------------------------------------------------------

ALTER TABLE "public"."tb_inspection_disease_instance" ADD COLUMN exception_type INT2;
COMMENT ON COLUMN "public"."tb_inspection_disease_instance"."exception_type" IS '判定 1:一般异常 2:严重异常';

ALTER TABLE "public"."tb_bridge_deck_component" ADD COLUMN serious INT2;
COMMENT ON COLUMN "public"."tb_bridge_deck_component"."serious" IS '病害是否带*(0：不带 1：带*)';
ALTER TABLE "public"."tb_bridge_supcomponent" ADD COLUMN serious INT2;
COMMENT ON COLUMN "public"."tb_bridge_supcomponent"."serious" IS '病害是否带*(0：不带 1：带*)';
ALTER TABLE "public"."tb_bridge_subcomponent" ADD COLUMN serious INT2;
COMMENT ON COLUMN "public"."tb_bridge_subcomponent"."serious" IS '病害是否带*(0：不带 1：带*)';

/*==============================================================*/
/* Table: tb_sensor_calculate                                   */
/*==============================================================*/
create table tb_sensor_calculate (
   sensor_coding        VARCHAR(64)          null,
   sensor_details_id    INT4                 null,
   frequency            FLOAT4               null,
   calculate_time       TIMESTAMP            not null
);

comment on table tb_sensor_calculate is
'传感器频谱数据';

comment on column tb_sensor_calculate.sensor_coding is
'传感器编码';

comment on column tb_sensor_calculate.sensor_details_id is
'传感器细项id';

comment on column tb_sensor_calculate.frequency is
'频率值';

comment on column tb_sensor_calculate.calculate_time is
'计算时间';


/*==============================================================*/
/* 基康传感器创建触发器及触发函数监听是否预警                      */
/*==============================================================*/


CREATE OR REPLACE FUNCTION judge_jk_warning() RETURNS TRIGGER AS $$
	DECLARE
		sensor tb_sensor%ROWTYPE;
		warning_level int2;
	BEGIN

		SELECT * INTO sensor FROM tb_sensor WHERE sensor_coding = new.paraid;

		if sensor is null
		then RETURN NULL;
		end if;

		if new.paravalue > sensor.first_warning_upper or new.paravalue < sensor.first_warning_lower
		then warning_level = 1;
		elsif new.paravalue > sensor.second_warning_upper or new.paravalue < sensor.second_warning_lower
		then warning_level = 2;
		elsif new.paravalue > sensor.third_warning_upper or new.paravalue < sensor.third_warning_lower
		then warning_level = 3;
		else return null;
		end if;

		INSERT INTO tb_warning_record (structure_id,component_id,warning_time,level,sensor_id,sensor_details_id,sensor_coding,sensor_name,value,status,create_time,modify_time) VALUES (sensor.structure_id,sensor.component_id,new.collecttime,warning_level,sensor.id,sensor.sensor_details_id,sensor.sensor_coding,sensor.name,new.paravalue,0,now(),now());
		RETURN NEW;

	END
$$ LANGUAGE plpgsql;

create trigger watch_jk AFTER INSERT ON composedata FOR EACH ROW EXECUTE PROCEDURE judge_jk_warning();

/*==============================================================*/
/* 基康传感器创建触发器及触发函数保证故障传感器数据不入库          */
/*==============================================================*/

CREATE OR REPLACE FUNCTION judge_jk_insert() RETURNS TRIGGER AS $$
	DECLARE
		sensor tb_sensor%ROWTYPE;
	BEGIN

		SELECT * INTO sensor FROM tb_sensor WHERE sensor_coding = new.paraid;

		if sensor is null
		then RETURN NULL;
		end if;

		if sensor.status = 2::smallint
		then return null;
		end if;

		RETURN NEW;

	END
$$ LANGUAGE plpgsql;

create trigger watch_jk_before BEFORE INSERT ON composedata FOR EACH ROW EXECUTE PROCEDURE judge_jk_insert();
