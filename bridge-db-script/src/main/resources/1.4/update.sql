--统计数据改为数据分析
update tb_power set name = '数据分析',remarks='数据分析', router_url='/online/analysisData' where id = 14;
update tb_power set remarks='数据分析查看',router_url='/online/analysisData' where id = 15;


--频谱图表添加字段
ALTER TABLE "public"."tb_sensor_calculate" ADD COLUMN cable_force FLOAT4;
COMMENT ON COLUMN "public"."tb_sensor_calculate"."cable_force" IS '索力';

--传感器信息添加字段
ALTER TABLE "public"."tb_means_point" ADD COLUMN reference_frequency FLOAT4;
COMMENT ON COLUMN "public"."tb_means_point"."reference_frequency" IS '参考基频';
ALTER TABLE "public"."tb_means_point" ADD COLUMN float_range FLOAT4;
COMMENT ON COLUMN "public"."tb_means_point"."float_range" IS '频率浮动范围';
ALTER TABLE "public"."tb_means_point" ADD COLUMN cable_length FLOAT4;
COMMENT ON COLUMN "public"."tb_means_point"."cable_length" IS '索长';
ALTER TABLE "public"."tb_means_point" ADD COLUMN unit_cable_length FLOAT4;
COMMENT ON COLUMN "public"."tb_means_point"."unit_cable_length" IS '单位索长';


--传感器细项添加、修改
UPDATE tb_sensor_details SET detail_name = '倾角X方向' WHERE id = 6;
INSERT INTO tb_sensor_details (id, detail_name, unit, precision) VALUES (10, '倾角Y方向','°',0.001);

--新增产商
INSERT INTO tb_company (id, company) VALUES (3, '智博联');

--图片名字长度加长
ALTER TABLE tb_photo ALTER COLUMN name TYPE VARCHAR(128);
