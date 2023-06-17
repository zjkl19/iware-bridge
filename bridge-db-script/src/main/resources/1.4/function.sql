--判断是否指定格式的分数

CREATE OR REPLACE FUNCTION is_fraction (txtStr VARCHAR)
RETURNS BOOLEAN AS $$
  DECLARE
		sensor tb_sensor%ROWTYPE;
		modelRow record;
  BEGIN
			IF txtStr IS NULL
			then RETURN FALSE;
			end if;

			RETURN txtStr ~ '^(1/[0-9]+)$';
	END
$$ LANGUAGE plpgsql;



/*==============================================================*/
/* 基康传感器创建触发器及触发函数保证故障传感器数据不入库          */
/*==============================================================*/
CREATE OR REPLACE FUNCTION judge_jk_insert() RETURNS TRIGGER AS $$
	DECLARE
		status smallint;
	BEGIN

		SELECT tmp.status INTO status FROM tb_sensor ts INNER JOIN tb_means_point tmp ON tmp.id = ts.means_point_id WHERE sensor_coding = new.paraid;

		if status is NULL
		then RETURN NULL;
		end if;

		if status = 2::smallint
		then return NULL;
		end if;
		RETURN NEW;

	END
$$ LANGUAGE plpgsql;

--基康数据保存触发器
create trigger watch_jk_before BEFORE INSERT ON composedata FOR EACH ROW EXECUTE PROCEDURE judge_jk_insert();

/*==============================================================*/
/* 基康传感器创建触发器及触发函数监听是否预警                      */
/*==============================================================*/
CREATE OR REPLACE FUNCTION judge_jk_warning() RETURNS TRIGGER AS $$
	DECLARE
		sensor tb_sensor%ROWTYPE;
		means_point tb_means_point%ROWTYPE;
		warning_level int2;
	BEGIN

		SELECT * INTO sensor FROM tb_sensor WHERE sensor_coding = new.paraid;

		if sensor is null
		then RETURN NULL;
		end if;

		SELECT * INTO means_point FROM tb_means_point WHERE id = sensor.means_point_id;

		if means_point.status != 2::smallint
		then

			if new.paravalue > sensor.first_warning_upper or new.paravalue < sensor.first_warning_lower
			then warning_level = 1;
			elsif new.paravalue > sensor.second_warning_upper or new.paravalue < sensor.second_warning_lower
			then warning_level = 2;
			elsif new.paravalue > sensor.third_warning_upper or new.paravalue < sensor.third_warning_lower
			then warning_level = 3;
			else return null;
			end if;

			INSERT INTO tb_warning_record (structure_id,component_id,warning_time,level,sensor_id,sensor_details_id,sensor_coding,sensor_name,value,status,create_time,modify_time) VALUES (means_point.structure_id,means_point.component_id,new.collecttime,warning_level,sensor.id,sensor.sensor_details_id,sensor.sensor_coding,means_point.name,new.paravalue,0,now(),now());

		end if;

		RETURN NEW;

	END
$$ LANGUAGE plpgsql;
--基康预警保存触发器
create trigger watch_jk_before BEFORE INSERT ON composedata FOR EACH ROW EXECUTE PROCEDURE judge_jk_insert();
