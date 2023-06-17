---------------------------------------------------------------------------
--用户相关表修改
---------------------------------------------------------------------------

DROP TABLE IF EXISTS tb_unit;

CREATE TABLE tb_unit (
   id                   SERIAL NOT NULL,
   phone                VARCHAR(32)          NULL,
   name                 VARCHAR(64)          NULL,
   role_id              INT4                 NULL,
   parent_id            INT4                 NULL,
   CREATE_user_id       INT4                 NULL,
   status               INT2                 NULL,
   CREATE_time          TIMESTAMP            NULL,
   modIFy_time          TIMESTAMP            NULL,
   CONSTRAINT PK_TB_UNIT PRIMARY KEY(id)
);

COMMENT ON TABLE tb_unit IS '企业单位表';
COMMENT ON COLUMN tb_unit.id IS '主键id';
COMMENT ON COLUMN tb_unit.phone IS '电话号码';
COMMENT ON COLUMN tb_unit.name IS '单位名称';
COMMENT ON COLUMN tb_unit.role_id IS '单位类型';
COMMENT ON COLUMN tb_unit.parent_id IS '父级单位';
COMMENT ON COLUMN tb_unit.CREATE_user_id IS '创建者';
COMMENT ON COLUMN tb_unit.status IS '状态';
COMMENT ON COLUMN tb_unit.CREATE_time IS '创建时间';
COMMENT ON COLUMN tb_unit.modIFy_time IS '修改时间';


--将原本用户拷贝到单位表
INSERT INTO tb_unit ( id, name, role_id, parent_id, CREATE_user_id, status, CREATE_time, modIFy_time )
SELECT id, name, role_id, parent_id, CREATE_user_id, status, CREATE_time, modIFy_time
FROM
(
	SELECT tu.id, tu.real_name AS name, urr.role_id,
	(SELECT CASE urr.role_id WHEN 0 THEN u.CREATE_user_id ELSE NULL END AS id FROM tb_user u WHERE u.id = tu.id ) AS parent_id,
	tu.CREATE_user_id, tu.status, tu.CREATE_time, tu.modIFy_time
	FROM tb_user tu LEFT JOIN tb_user_role_rel urr ON urr.user_id = tu.ID
	WHERE urr.role_id != 3
) AS rs;
--表id设置为当前最大值
SELECT setval('tb_unit_id_seq',(SELECT MAX(id) FROM tb_unit));

--用户表添加字段
ALTER TABLE "public"."tb_user" ADD COLUMN unit_id INT4;
COMMENT ON COLUMN "public"."tb_user"."unit_id" IS '所属单位id';
ALTER TABLE "public"."tb_user" ADD COLUMN device_id VARCHAR(128);
COMMENT ON COLUMN "public"."tb_user"."device_id" IS '设备编号';
--设置用户表的单位id
UPDATE tb_user SET unit_id = id WHERE username != 'admin';

--app用户权限关联表修改
ALTER TABLE "public"."tb_app_user_role_rel" RENAME TO "tb_user_app_role_rel";
ALTER TABLE "public"."tb_user_app_role_rel" RENAME "app_user_id" TO user_id;
ALTER SEQUENCE tb_app_user_role_rel_id_seq RENAME TO tb_user_app_role_rel_id_seq;

--app用户迁移到用户表
INSERT INTO tb_user ( id, username, password, phone, real_name, status, CREATE_time, modIFy_time, unit_id, device_id )
SELECT id, username, password, phone, real_name, status, CREATE_time, modIFy_time, unit_id, device_id
FROM
(
	SELECT id, username, password, phone, real_name,
	CASE WHEN status IS NULL THEN 1 ELSE status END AS status,
	CREATE_time, modIFy_time, user_id AS unit_id, device_id
	FROM tb_app_user tau ORDER BY id
) AS rs;

--权限表数据重置
DELETE FROM tb_power;
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (1, '首页', '首页', '/home', 0, 0, 1, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (2, '查看', '首页查看', '/home', 1, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (3, '在线监测', '在线监测', '/online', 0, 0, 2, 1, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (4, '监测总览', '监测总览', '/online/online', 3, 0, 1, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (5, '查看', '监测总览查看', '/online/online', 4, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (6, '结构物监测', '结构物监测', '/online/onebridgeMonitoring', 3, 0, 2, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (7, '查看', '结构物监测查看', '/online/onebridgeMonitoring', 6, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (8, '预警管理', '预警管理', '/online/earlyWarningManagement', 3, 0, 3, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (9, '查看', '预警管理查看', '/online/earlyWarningManagement', 8, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (10, '修改', '预警管理修改', '/online/earlyWarningManagement', 8, 1, 2, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (11, '时程数据', '时程数据', '/online/warningDetails', 3, 0, 4, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (12, '查看', '时程数据查看', '/online/warningDetails', 11, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (14, '统计数据', '统计数据', '/online/statisticsData', 3, 0, 5, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (15, '查看', '统计数据查看', '/online/statisticsData', 14, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (16, '报表管理', '报表管理', '/online/tableManage', 3, 0, 6, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (17, '查看', '报表管理查看', '/online/tableManage', 16, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (18, '新增', '报表管理新增', '/online/tableManage', 16, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (19, '修改', '报表管理修改', '/online/tableManage', 16, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (20, '删除', '报表管理删除', '/online/tableManage', 16, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (21, '维护日志', '维护日志', '/online/diary', 3, 0, 7, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (22, '查看', '维护日志查看', '/online/diary', 21, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (23, '新增', '维护日志新增', '/online/diary', 21, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (24, '修改', '维护日志修改', '/online/diary', 21, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (25, '删除', '维护日志删除', '/online/diary', 21, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (26, '传感器设置', '传感器设置', '/online/warningSetting', 3, 0, 8, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (27, '查看', '传感器设置查看', '/online/warningSetting', 26, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (28, '新增', '传感器设置新增', '/online/warningSetting', 26, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (29, '修改', '传感器设置修改', '/online/warningSetting', 26, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (30, '删除', '传感器设置删除', '/online/warningSetting', 26, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (31, '日常巡查', '日常巡查', '/normal', 0, 0, 3, 1, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (32, '巡查任务总览', '巡查任务总览', '/normal/normal', 31, 0, 1, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (33, '查看', '巡查任务总览查看', '/normal/normal', 32, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (34, '巡查计划', '巡查计划', '/normal/plan', 31, 0, 2, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (35, '查看', '巡查计划查看', '/normal/plan', 34, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (36, '新增', '巡查计划新增', '/normal/plan', 34, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (37, '修改', '巡查计划修改', '/normal/plan', 34, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (38, '删除', '巡查计划删除', '/normal/plan', 34, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (39, '巡查记录', '巡查记录', '/normal/record', 31, 0, 3, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (40, '查看', '巡查记录查看', '/normal/record', 39, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (43, '巡查报告', '巡查报告', '/normal/report', 31, 0, 4, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (44, '查看', '巡查报告查看', '/normal/report', 43, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (45, '新增', '巡查报告新增', '/normal/report', 43, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (46, '修改', '巡查报告修改', '/normal/report', 43, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (47, '删除', '巡查报告删除', '/normal/report', 43, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (48, '巡查结果统计', '巡查结果统计', '/normal/checkAnalysis', 31, 0, 5, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (49, '查看', '巡查结果统计查看', '/normal/checkAnalysis', 48, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (50, '维修养护', '维修养护', '/maintain', 0, 0, 4, 1, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (51, '维养任务总览', '维养任务总览', '/maintain/maintain', 50, 0, 1, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (52, '查看', '维养任务总览查看', '/maintain/maintain', 51, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (53, '维养计划', '维养计划', '/maintain/planManagement', 50, 0, 2, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (54, '查看', '维养计划查看', '/maintain/planManagement', 53, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (55, '新增', '维养计划新增', '/maintain/planManagement', 53, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (56, '修改', '维养计划修改', '/maintain/planManagement', 53, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (57, '删除', '维养计划删除', '/maintain/planManagement', 53, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (58, '维养记录', '维养记录', '/maintain/wordRecord', 50, 0, 3, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (59, '查看', '维养记录查看', '/maintain/wordRecord', 58, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (62, '维养报告', '维养报告', '/maintain/report', 50, 0, 4, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (63, '查看', '维养报告查看', '/maintain/report', 62, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (64, '新增', '维养报告新增', '/maintain/report', 62, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (65, '修改', '维养报告修改', '/maintain/report', 62, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (66, '删除', '维养报告删除', '/maintain/report', 62, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (67, '维养效果分析', '维养效果分析', '/maintain/checkAnalysis', 50, 0, 5, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (68, '查看', '维养效果分析查看', '/maintain/checkAnalysis', 67, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (69, '检测评估', '检测评估', '/assessment', 0, 0, 5, 1, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (70, '检测总览', '检测总览', '/assessment/overview', 69, 0, 1, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (71, '查看', '检测总览查看', '/assessment/overview', 70, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (72, '结构物检测', '结构物检测', '/assessment/detection', 69, 0, 2, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (73, '查看', '结构物检测查看', '/assessment/detection', 72, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (74, '检测计划', '检测计划', '/assessment/plan', 69, 0, 3, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (75, '查看', '检测计划查看', '/assessment/plan', 74, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (76, '新增', '检测计划新增', '/assessment/plan', 74, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (77, '修改', '检测计划修改', '/assessment/plan', 74, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (78, '删除', '检测计划删除', '/assessment/plan', 74, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (79, '检测记录', '检测记录', '/assessment/record', 69, 0, 4, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (80, '查看', '检测记录查看', '/assessment/record', 79, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (81, '新增', '检测记录新增', '/assessment/record', 79, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (82, '修改', '检测记录修改', '/assessment/record', 79, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (83, '删除', '检测记录删除', '/assessment/record', 79, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (84, '检测数据分析', '检测数据分析', '/assessment/analysis', 69, 0, 5, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (85, '查看', '检测数据分析查看', '/assessment/analysis', 84, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (86, '视频观察', '视频观察', '/video', 0, 0, 6, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (87, '查看', '视频观察查看', '/video', 86, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (88, '新增', '视频观察新增', '/video', 86, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (89, '修改', '视频观察修改', '/video', 86, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (90, '删除', '视频观察删除', '/video', 86, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (91, '信息管理', '信息管理', '/infoManage', 0, 0, 7, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (92, '桥梁信息管理', '桥梁信息管理', '/infoManage/bridgeManage', 91, 0, 1, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (93, '查看', '桥梁信息管理查看', '/infoManage/bridgeManage', 92, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (94, '新增', '桥梁信息管理新增', '/infoManage/bridgeManage', 92, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (95, '修改', '桥梁信息管理修改', '/infoManage/bridgeManage', 92, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (96, '删除', '桥梁信息管理删除', '/infoManage/bridgeManage', 92, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (97, '隧道信息管理', '隧道信息管理', '/infoManage/tunnelManage', 91, 0, 2, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (98, '查看', '隧道信息管理查看', '/infoManage/tunnelManage', 97, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (99, '新增', '隧道信息管理新增', '/infoManage/tunnelManage', 97, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (100, '修改', '隧道信息管理修改', '/infoManage/tunnelManage', 97, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (101, '删除', '隧道信息管理删除', '/infoManage/tunnelManage', 97, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (102, '用户管理', '用户管理', '/infoManage/userManage', 91, 0, 6, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (103, '查看', '用户管理查看', '/infoManage/userManage', 102, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (104, '新增', '用户管理新增', '/infoManage/userManage', 102, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (105, '修改', '用户管理修改', '/infoManage/userManage', 102, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (106, '删除', '用户管理删除', '/infoManage/userManage', 102, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (107, '公告管理', '公告管理', '/infoManage/announcement', 91, 0, 4, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (108, '查看', '公告管理查看', '/infoManage/announcement', 107, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (109, '新增', '公告管理新增', '/infoManage/announcement', 107, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (110, '修改', '公告管理修改', '/infoManage/announcement', 107, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (111, '删除', '公告管理删除', '/infoManage/announcement', 107, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (112, '项目管理', '项目管理', '/infoManage/projectManage', 91, 0, 3, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (113, '查看', '项目管理查看', '/infoManage/projectManage', 112, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (114, '新增', '项目管理新增', '/infoManage/projectManage', 112, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (115, '修改', '项目管理修改', '/infoManage/projectManage', 112, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (116, '删除', '项目管理删除', '/infoManage/projectManage', 112, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (117, '指派', '项目管理指派', '/infoManage/projectManage', 112, 1, 5, 0, 'appointOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (118, '单位管理', '单位管理', '/infoManage/unitManage', 91, 0, 5, 0, NULL);
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (119, '查看', '单位管理查看', '/infoManage/unitManage', 118, 1, 1, 0, 'checkOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (120, '新增', '单位管理新增', '/infoManage/unitManage', 118, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (121, '修改', '单位管理修改', '/infoManage/unitManage', 118, 1, 3, 0, 'updateOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (122, '删除', '单位管理删除', '/infoManage/unitManage', 118, 1, 4, 0, 'deleteOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (123, '新增', '巡查记录新增', '/normal/record', 39, 1, 2, 0, 'addOpt');
INSERT INTO "public"."tb_power"("id", "name", "remarks", "router_url", "parent_id", "is_active", "priority", "business", "type") VALUES (124, '新增', '维养记录新增', '/maintain/wordRecord', 58, 1, 2, 0, 'addOpt');


--角色权限数据重置
DELETE FROM tb_role_power_rel;
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (1, 3, 1);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (2, 3, 2);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (3, 3, 3);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (4, 3, 4);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (5, 3, 5);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (6, 3, 6);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (7, 3, 7);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (8, 3, 8);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (9, 3, 9);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (10, 3, 10);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (11, 3, 11);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (12, 3, 12);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (13, 3, 14);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (14, 3, 15);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (15, 3, 16);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (16, 3, 17);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (17, 3, 18);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (18, 3, 19);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (19, 3, 20);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (20, 3, 21);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (21, 3, 22);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (22, 3, 23);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (23, 3, 24);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (24, 3, 25);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (25, 3, 26);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (26, 3, 27);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (27, 3, 28);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (28, 3, 29);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (29, 3, 30);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (30, 3, 31);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (31, 3, 32);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (32, 3, 33);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (33, 3, 34);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (34, 3, 35);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (35, 3, 36);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (36, 3, 37);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (37, 3, 38);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (38, 3, 39);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (39, 3, 40);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (40, 3, 43);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (41, 3, 44);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (42, 3, 45);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (43, 3, 46);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (44, 3, 47);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (45, 3, 48);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (46, 3, 49);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (47, 3, 50);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (48, 3, 51);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (49, 3, 52);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (50, 3, 53);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (51, 3, 54);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (52, 3, 55);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (53, 3, 56);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (54, 3, 57);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (55, 3, 58);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (56, 3, 59);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (57, 3, 62);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (58, 3, 63);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (59, 3, 64);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (60, 3, 65);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (61, 3, 66);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (62, 3, 67);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (63, 3, 68);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (64, 3, 69);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (65, 3, 70);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (66, 3, 71);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (67, 3, 72);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (68, 3, 73);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (69, 3, 74);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (70, 3, 75);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (71, 3, 76);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (72, 3, 77);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (73, 3, 78);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (74, 3, 79);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (75, 3, 80);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (76, 3, 81);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (77, 3, 82);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (78, 3, 83);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (79, 3, 84);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (80, 3, 85);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (81, 3, 86);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (82, 3, 87);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (83, 3, 88);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (84, 3, 89);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (85, 3, 90);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (86, 3, 91);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (87, 3, 92);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (88, 3, 93);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (89, 3, 94);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (90, 3, 95);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (91, 3, 96);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (92, 3, 97);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (93, 3, 98);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (94, 3, 99);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (95, 3, 100);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (96, 3, 101);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (97, 3, 102);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (98, 3, 103);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (99, 3, 104);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (100, 3, 105);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (101, 3, 106);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (102, 3, 107);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (103, 3, 108);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (104, 3, 109);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (105, 3, 110);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (106, 3, 111);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (107, 3, 112);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (108, 3, 113);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (109, 3, 114);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (110, 3, 115);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (111, 3, 116);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (112, 3, 117);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (113, 3, 118);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (114, 3, 119);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (115, 3, 120);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (116, 3, 121);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (117, 3, 122);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (118, 2, 1);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (119, 2, 2);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (120, 2, 3);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (121, 2, 4);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (122, 2, 5);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (123, 2, 6);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (124, 2, 7);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (125, 2, 8);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (126, 2, 9);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (127, 2, 11);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (128, 2, 12);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (129, 2, 14);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (130, 2, 15);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (131, 2, 16);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (132, 2, 17);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (133, 2, 21);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (134, 2, 22);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (135, 2, 26);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (136, 2, 27);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (137, 2, 31);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (138, 2, 32);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (139, 2, 33);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (140, 2, 34);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (141, 2, 35);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (142, 2, 39);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (143, 2, 40);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (144, 2, 43);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (145, 2, 44);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (146, 2, 48);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (147, 2, 49);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (148, 2, 50);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (149, 2, 51);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (150, 2, 52);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (151, 2, 53);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (152, 2, 54);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (153, 2, 58);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (154, 2, 59);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (155, 2, 62);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (156, 2, 63);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (157, 2, 67);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (158, 2, 68);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (159, 2, 69);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (160, 2, 70);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (161, 2, 71);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (162, 2, 72);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (163, 2, 73);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (164, 2, 74);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (165, 2, 75);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (166, 2, 79);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (167, 2, 80);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (168, 2, 84);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (169, 2, 85);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (170, 2, 86);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (171, 2, 87);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (172, 2, 91);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (173, 2, 92);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (174, 2, 93);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (175, 2, 94);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (176, 2, 95);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (177, 2, 96);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (178, 2, 97);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (179, 2, 98);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (180, 2, 99);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (181, 2, 100);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (182, 2, 101);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (183, 2, 107);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (184, 2, 108);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (185, 2, 109);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (186, 2, 110);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (187, 2, 111);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (188, 2, 112);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (189, 2, 113);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (190, 2, 114);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (191, 2, 115);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (192, 2, 116);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (193, 2, 117);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (194, 1, 1);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (195, 1, 2);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (196, 1, 3);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (197, 1, 4);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (198, 1, 5);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (199, 1, 6);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (200, 1, 7);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (201, 1, 8);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (202, 1, 9);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (203, 1, 10);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (204, 1, 11);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (205, 1, 12);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (206, 1, 14);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (207, 1, 15);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (208, 1, 16);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (209, 1, 17);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (210, 1, 18);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (211, 1, 19);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (212, 1, 20);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (213, 1, 21);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (214, 1, 22);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (215, 1, 23);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (216, 1, 24);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (217, 1, 25);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (218, 1, 26);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (219, 1, 27);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (220, 1, 28);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (221, 1, 29);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (222, 1, 30);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (223, 1, 31);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (224, 1, 32);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (225, 1, 33);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (226, 1, 34);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (227, 1, 35);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (228, 1, 36);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (229, 1, 37);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (230, 1, 38);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (231, 1, 39);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (232, 1, 40);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (233, 1, 43);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (234, 1, 44);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (235, 1, 45);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (236, 1, 46);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (237, 1, 47);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (238, 1, 48);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (239, 1, 49);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (240, 1, 50);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (241, 1, 51);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (242, 1, 52);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (243, 1, 53);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (244, 1, 54);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (245, 1, 55);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (246, 1, 56);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (247, 1, 57);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (248, 1, 58);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (249, 1, 59);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (250, 1, 62);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (251, 1, 63);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (252, 1, 64);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (253, 1, 65);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (254, 1, 66);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (255, 1, 67);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (256, 1, 68);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (257, 1, 69);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (258, 1, 70);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (259, 1, 71);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (260, 1, 72);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (261, 1, 73);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (262, 1, 74);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (263, 1, 75);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (264, 1, 76);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (265, 1, 77);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (266, 1, 78);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (267, 1, 79);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (268, 1, 80);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (269, 1, 82);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (270, 1, 84);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (271, 1, 85);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (272, 1, 86);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (273, 1, 87);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (274, 1, 88);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (275, 1, 89);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (276, 1, 90);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (277, 1, 91);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (278, 1, 92);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (279, 1, 93);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (280, 1, 97);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (281, 1, 98);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (282, 1, 102);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (283, 1, 103);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (284, 1, 104);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (285, 1, 105);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (286, 1, 106);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (287, 1, 107);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (288, 1, 108);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (289, 1, 109);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (290, 1, 110);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (291, 1, 111);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (292, 1, 112);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (293, 1, 113);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (294, 1, 117);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (295, 1, 118);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (296, 1, 119);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (297, 1, 120);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (298, 1, 121);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (299, 1, 122);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (300, 0, 1);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (301, 0, 2);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (302, 0, 3);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (303, 0, 4);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (304, 0, 5);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (305, 0, 6);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (306, 0, 7);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (307, 0, 8);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (308, 0, 9);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (309, 0, 10);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (310, 0, 11);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (311, 0, 12);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (312, 0, 14);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (313, 0, 15);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (314, 0, 16);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (315, 0, 17);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (316, 0, 18);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (317, 0, 19);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (318, 0, 20);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (319, 0, 21);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (320, 0, 22);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (321, 0, 23);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (322, 0, 24);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (323, 0, 25);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (324, 0, 26);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (325, 0, 27);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (326, 0, 28);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (327, 0, 29);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (328, 0, 30);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (329, 0, 31);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (330, 0, 32);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (331, 0, 33);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (332, 0, 34);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (333, 0, 35);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (334, 0, 36);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (335, 0, 37);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (336, 0, 38);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (337, 0, 39);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (338, 0, 40);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (339, 0, 43);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (340, 0, 44);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (341, 0, 45);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (342, 0, 46);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (343, 0, 47);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (344, 0, 48);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (345, 0, 49);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (346, 0, 50);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (347, 0, 51);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (348, 0, 52);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (349, 0, 53);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (350, 0, 54);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (351, 0, 55);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (352, 0, 56);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (353, 0, 57);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (354, 0, 58);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (355, 0, 59);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (356, 0, 62);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (357, 0, 63);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (358, 0, 64);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (359, 0, 65);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (360, 0, 66);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (361, 0, 67);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (362, 0, 68);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (363, 0, 69);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (364, 0, 70);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (365, 0, 71);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (366, 0, 72);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (367, 0, 73);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (368, 0, 74);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (369, 0, 75);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (370, 0, 76);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (371, 0, 77);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (372, 0, 78);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (373, 0, 79);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (374, 0, 80);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (375, 0, 82);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (376, 0, 84);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (377, 0, 85);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (378, 0, 86);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (379, 0, 87);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (380, 0, 88);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (381, 0, 89);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (382, 0, 90);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (383, 0, 91);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (384, 0, 92);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (385, 0, 93);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (386, 0, 97);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (387, 0, 98);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (388, 0, 107);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (389, 0, 108);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (390, 0, 109);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (391, 0, 110);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (392, 0, 111);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (393, 0, 112);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (394, 0, 113);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (396, 2, 102);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (397, 2, 103);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (398, 2, 104);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (399, 2, 105);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (400, 2, 106);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (401, 0, 123);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (402, 0, 124);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (403, 1, 123);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (404, 1, 124);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (405, 3, 123);
INSERT INTO "public"."tb_role_power_rel"("id", "role_id", "power_id") VALUES (406, 3, 124);


--用户权限关联表改为单位权限关联表
ALTER TABLE "public"."tb_user_power_rel" RENAME TO "tb_unit_power_rel";
COMMENT ON TABLE "public"."tb_unit_power_rel" IS '单位权限关联表';
ALTER TABLE "public"."tb_unit_power_rel" RENAME "user_id" TO unit_id;
COMMENT ON COLUMN "public"."tb_unit_power_rel"."unit_id" IS '单位id';
--修改序列名称
ALTER SEQUENCE tb_user_power_rel_id_seq RENAME TO tb_unit_power_rel_id_seq;
--删除旧数据
DELETE FROM tb_unit_power_rel;
--重置序列
SELECT setval('tb_unit_power_rel_id_seq', 1);
--给单位分配默认权限
INSERT INTO tb_unit_power_rel ( unit_id, power_id )
SELECT unit_id, power_id
FROM
(
	SELECT tu.id AS unit_id, trpr.power_id
	FROM tb_unit tu
	LEFT JOIN tb_role_power_rel trpr ON trpr.role_id = tu.role_id
	ORDER BY tu.id, trpr.power_id
) AS rs;


---------------------------------------------------------------------------
--通用相关表修改
---------------------------------------------------------------------------


--新建通知相关表
DROP TABLE IF EXISTS tb_user_notice_rel;
CREATE TABLE tb_user_notice_rel (
   id                   SERIAL NOT NULL,
   notice_id            INT4                 NULL,
   user_id              INT4                 NULL,
   status               INT2                 NULL,
   CONSTRAINT PK_TB_USER_NOTICE_REL PRIMARY KEY(id)
);

COMMENT ON TABLE tb_user_notice_rel IS'用户通知关联表';
COMMENT ON COLUMN tb_user_notice_rel.id IS'主键id';
COMMENT ON COLUMN tb_user_notice_rel.notice_id IS'通知id';
COMMENT ON COLUMN tb_user_notice_rel.user_id IS'用户id';
COMMENT ON COLUMN tb_user_notice_rel.status IS'0:未读 1:已读';


DROP TABLE IF EXISTS tb_app_notice;
CREATE TABLE tb_app_notice (
   id                   SERIAL NOT NULL,
   title                VARCHAR(32)          NULL,
   content              VARCHAR(256)         NULL,
   status               INT2                 NULL,
   CREATE_time          TIMESTAMP            NULL,
   modIFy_time          TIMESTAMP            NULL,
   CONSTRAINT PK_TB_APP_NOTICE PRIMARY KEY(id)
);
COMMENT ON TABLE tb_app_notice IS'App通知表';
COMMENT ON COLUMN tb_app_notice.id IS'主键id';
COMMENT ON COLUMN tb_app_notice.title IS'公告标题';
COMMENT ON COLUMN tb_app_notice.content IS'公告内容';
COMMENT ON COLUMN tb_app_notice.status IS'状态';
COMMENT ON COLUMN tb_app_notice.CREATE_time IS'创建时间';
COMMENT ON COLUMN tb_app_notice.modIFy_time IS'修改时间';


--新建项目结构物关联表
DROP TABLE IF EXISTS tb_project_structure_rel;
CREATE TABLE tb_project_structure_rel (
   id                   SERIAL NOT NULL,
   project_id           INT4                 NULL,
   structure_id         INT4                 NULL,
   CONSTRAINT PK_TB_PROJECT_STRUCTURE_REL PRIMARY KEY(id)
);
COMMENT ON TABLE tb_project_structure_rel IS '项目结构物关联表';
COMMENT ON COLUMN tb_project_structure_rel.id IS '主键id';
COMMENT ON COLUMN tb_project_structure_rel.project_id IS '项目id';
COMMENT ON COLUMN tb_project_structure_rel.structure_id IS '结构物id';

--项目指派表字段新增修改
ALTER TABLE "public"."tb_project_appoint" ADD COLUMN create_user_id INT4;
COMMENT ON COLUMN "public"."tb_project_appoint"."create_user_id" IS '创建者id';
ALTER TABLE "public"."tb_project_appoint" RENAME "appoint_user_id" TO appoint_unit_id;
COMMENT ON COLUMN "public"."tb_project_appoint"."appoint_unit_id" IS '指派单位';
ALTER TABLE "public"."tb_project_appoint" RENAME "receive_user_id" TO receive_unit_id;
COMMENT ON COLUMN "public"."tb_project_appoint"."receive_unit_id" IS '接收单位';

UPDATE tb_project_appoint SET create_user_id = appoint_unit_id;

--修改项目表/结构物表用户id 为单位id
ALTER TABLE "public"."tb_project" RENAME "user_id" TO unit_id;
COMMENT ON COLUMN "public"."tb_project"."unit_id" IS '所属业主单位id';

ALTER TABLE "public"."tb_structure" RENAME "user_id" TO unit_id;
COMMENT ON COLUMN "public"."tb_structure"."unit_id" IS '所属业主单位id';

--结构物表新增字段
ALTER TABLE "public"."tb_structure" ADD COLUMN maintain_category INT2;
COMMENT ON COLUMN "public"."tb_structure"."maintain_category" IS '1:I类 2:II类 3:III类 4:IV类 5:V类';
--桥梁表中的养护列别更新到结构物表
UPDATE tb_structure ts
SET ( maintain_category ) =
( SELECT maintain_category FROM tb_bridge_info tbi WHERE ts.ID = tbi.structure_id );

--桥梁表删除maintain_category字段
ALTER TABLE "public"."tb_bridge_info" DROP COLUMN IF EXISTS maintain_category;

--桥梁类型修改
COMMENT ON COLUMN "public"."tb_structure"."bridge_type" IS '桥梁类型 1:梁桥 2:拱桥 3:刚架拱桥 4:悬索桥 5:斜拉桥 6:钢管混凝土拱桥';
UPDATE tb_structure SET bridge_type = (
	CASE WHEN bridge_type < 3 THEN 1
	WHEN bridge_type = 3 OR bridge_type = 4 THEN 2
	WHEN bridge_type = 5 OR bridge_type = 6 THEN 6
	WHEN bridge_type = 7 THEN 5
	ELSE NULL
	END
);

--将结构物项目关联关系保存
INSERT INTO tb_project_structure_rel ( project_id, structure_id )
SELECT  project_id, structure_id
FROM
(
	SELECT ts.id AS structure_id, ts.project_id
	FROM tb_structure ts
	INNER JOIN tb_project tp ON ts.project_id = tp.id
	ORDER BY tp.id, ts.id
) AS rs;

--删除结构物表项目字段
ALTER TABLE "public"."tb_structure" DROP COLUMN IF EXISTS project_id;


--公告表新增字段
ALTER TABLE "public"."tb_announcement" ADD COLUMN unit_id INT4;
COMMENT ON COLUMN "public"."tb_announcement"."unit_id" IS '单位id';

---------------------------------------------------------------------------
--巡查维养表修改
---------------------------------------------------------------------------

--传感器细项表添加精度
ALTER TABLE "public"."tb_sensor_details" ADD COLUMN precision FLOAT4;
COMMENT ON COLUMN "public"."tb_sensor_details"."precision" IS '默认精度';
--删除传感器细项旧数据
DELETE FROM tb_sensor_details;
--保存新数据
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "unit", "precision") VALUES (1, '加速度', 'mm/s2', '0.1');
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "unit", "precision") VALUES (2, '应变', 'με', '1');
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "unit", "precision") VALUES (3, '索力', 'mm/s2', '0.1');
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "unit", "precision") VALUES (4, '风速', 'm/s', NULL);
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "unit", "precision") VALUES (5, '温度', '°C', '0.1');
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "unit", "precision") VALUES (6, '倾角', '°', '0.001');
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "unit", "precision") VALUES (7, '称重', 't', '0.1');
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "unit", "precision") VALUES (8, '位移', 'mm', '0.01');
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "unit", "precision") VALUES (9, '风向', '°', NULL);

--传感器记录表修改字段
ALTER TABLE "public"."tb_sensor_record" RENAME "sensor_id" TO "means_point_id";
COMMENT ON COLUMN "public"."tb_sensor_record"."means_point_id" IS '测点id';


--新增测点信息表
DROP TABLE IF EXISTS tb_means_point;
CREATE TABLE tb_means_point (
   id                   SERIAL NOT NULL,
   component_id         INT4                 NULL,
   company_id           INT4                 NULL,
   structure_id         INT4                 NULL,
   sensor_type_id       INT4                 NULL,
   name                 VARCHAR(32)          NULL,
   describe             VARCHAR(64)          NULL,
   deployment_date      TIMESTAMP            NULL,
   section_position     VARCHAR(32)          NULL,
   collector_information VARCHAR(256)         NULL,
   x_axIS               FLOAT4               NULL,
   y_axIS               FLOAT4               NULL,
   z_axIS               FLOAT4               NULL,
   sampling_frequency   VARCHAR(16)          NULL,
   production_date      TIMESTAMP            NULL,
   production_coding    VARCHAR(64)          NULL,
   photo_url            VARCHAR(64)          NULL,
   status               INT2                 NULL,
   CREATE_time          TIMESTAMP            NULL,
   modIFy_time          TIMESTAMP            NULL,
   CONSTRAINT PK_TB_MEANS_POINT PRIMARY KEY(id)
);
COMMENT ON TABLE tb_means_point IS '测点信息表';
COMMENT ON COLUMN tb_means_point.id IS '主键id';
COMMENT ON COLUMN tb_means_point.component_id IS '构件id';
COMMENT ON COLUMN tb_means_point.company_id IS '产商id';
COMMENT ON COLUMN tb_means_point.structure_id IS '结构物id';
COMMENT ON COLUMN tb_means_point.sensor_type_id IS '传感器类型id';
COMMENT ON COLUMN tb_means_point.name IS '测点编号';
COMMENT ON COLUMN tb_means_point.describe IS '测点编号说明';
COMMENT ON COLUMN tb_means_point.deployment_date IS '部署日期';
COMMENT ON COLUMN tb_means_point.section_position IS '截面位置';
COMMENT ON COLUMN tb_means_point.collector_information IS '采集器信息';
COMMENT ON COLUMN tb_means_point.x_axIS IS 'X坐标';
COMMENT ON COLUMN tb_means_point.x_axIS IS 'Y坐标';
COMMENT ON COLUMN tb_means_point.z_axIS IS 'Z坐标';
COMMENT ON COLUMN tb_means_point.sampling_frequency IS '采样频率';
COMMENT ON COLUMN tb_means_point.production_date IS '出产日期';
COMMENT ON COLUMN tb_means_point.production_coding IS '出产编号';
COMMENT ON COLUMN tb_means_point.photo_url IS '图片位置';
COMMENT ON COLUMN tb_means_point.status IS '状态 0：离线 1：正常 2: 故障';
COMMENT ON COLUMN tb_means_point.CREATE_time IS '创建时间';
COMMENT ON COLUMN tb_means_point.modIFy_time IS '修改时间';

--拷贝传感器表到测点表
INSERT INTO tb_means_point (
	ID,
	component_id,
	company_id,
	structure_id,
	sensor_type_id,
	NAME,
	describe,
	deployment_date,
	section_position,
	collector_information,
	x_axIS,
	y_axIS,
	z_axIS,
	sampling_frequency,
	production_date,
	production_coding,
	photo_url,
	status,
	CREATE_time,
	modIFy_time
)
SELECT ID,
component_id,
company_id,
structure_id,
sensor_type_id,
NAME,
describe,
deployment_date,
section_position,
collector_information,
x_axIS,
y_axIS,
z_axIS,
sampling_frequency,
production_date,
production_coding,
photo_url,
status,
CREATE_time,
modIFy_time
FROM
	tb_sensor;
--设置序列
SELECT setval('tb_means_point_id_seq',(SELECT MAX(id) FROM tb_means_point));

--删除、添加传感器表相关列
ALTER TABLE "public"."tb_sensor" ADD COLUMN means_point_id INT2;
COMMENT ON COLUMN "public"."tb_sensor"."means_point_id" IS '测点id';
ALTER TABLE "public"."tb_sensor" ADD COLUMN precision FLOAT4;
COMMENT ON COLUMN "public"."tb_sensor"."precision" IS '精度';
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS component_id;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS company_id;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS structure_id;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS sensor_type_id;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS name;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS describe;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS deployment_date;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS section_position;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS collector_information;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS x_axIS;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS y_axIS;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS z_axIS;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS sampling_frequency;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS production_date;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS production_coding;
ALTER TABLE "public"."tb_sensor" DROP COLUMN IF EXISTS photo_url;
COMMENT ON COLUMN "public"."tb_sensor"."status" IS '状态';
--传感器绑定测点
UPDATE tb_sensor ts SET means_point_id  = id;
--传感器默认单位、精度填充
UPDATE tb_sensor ts
SET ( unit, precision ) =
( SELECT tsd.unit, tsd.precision FROM tb_sensor_details tsd WHERE ts.sensor_details_id = tsd.id );


--车型关联表修改
ALTER TABLE "public"."tb_axle_type_model_rel" ADD COLUMN name VARCHAR(16);
COMMENT ON COLUMN "public"."tb_axle_type_model_rel"."name" IS '车型';
DELETE FROM tb_axle_type_model_rel;
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (1, 1, 20, '二轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (2, 1, 23, '两轴轻型车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (3, 1, 24, '两轴中重型车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (4, 1, 25, '两轴中重型车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (5, 1, 26, '两轴中重型车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (6, 2, 30, '三轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (7, 2, 31, '三轴中重型车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (8, 2, 32, '三轴半挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (9, 2, 33, '三轴中重型车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (10, 2, 34, '三轴中重型车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (11, 2, 35, '三轴中重型车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (12, 2, 39, '三轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (13, 3, 40, '四轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (14, 3, 41, '四轴半挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (15, 3, 42, '四轴半挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (16, 3, 43, '四轴以上(含)重型汽车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (17, 3, 46, '四轴以上(含)重型汽车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (18, 3, 44, '四轴挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (19, 3, 45, '四轴挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (20, 3, 49, '四轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (21, 4, 50, '五轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (22, 4, 51, '五轴半挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (23, 4, 53, '五轴半挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (24, 4, 54, '五轴半挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (25, 4, 52, '五轴(含)拖挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (26, 4, 57, '五轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (27, 4, 58, '五轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (28, 4, 59, '五轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (29, 5, 60, '六轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (30, 5, 68, '六轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (31, 5, 69, '六轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (32, 6, 70, '七轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (33, 6, 71, '七轴半挂拖挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (34, 6, 72, '七轴半挂拖挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (35, 6, 73, '七轴半挂拖挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (36, 6, 74, '七轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (37, 6, 80, '八轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (38, 6, 85, '八轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (39, 6, 86, '八轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (40, 6, 87, '八轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (41, 6, 88, '八轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (42, 6, 89, '八轴车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (43, 1, 21, '微型车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (44, 1, 22, '微型车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (45, 5, 61, '六轴以上(含)半挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (46, 5, 62, '六轴以上(含)半挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (47, 5, 63, '六轴以上(含)半挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (48, 5, 64, '六轴以上(含)半挂车');
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id", "name") VALUES (49, 5, 65, '六轴以上(含)半挂车');

--车辆统计表表修改字段
ALTER TABLE "public"."tb_sensor_weight_hour_model" RENAME "sensor_id" TO "means_point_id";
COMMENT ON COLUMN "public"."tb_sensor_weight_hour_model"."means_point_id" IS '测点id';
ALTER TABLE "public"."tb_sensor_weight_hour_speed" RENAME "sensor_id" TO "means_point_id";
COMMENT ON COLUMN "public"."tb_sensor_weight_hour_speed"."means_point_id" IS '测点id';
ALTER TABLE "public"."tb_sensor_weight_hour_weight" RENAME "sensor_id" TO "means_point_id";
COMMENT ON COLUMN "public"."tb_sensor_weight_hour_weight"."means_point_id" IS '测点id';

---------------------------------------------------------------------------
--巡查维养
---------------------------------------------------------------------------

--添加桥梁类型病害关联表和数据
DROP TABLE IF EXISTS tb_bridge_type_disease_rel;
CREATE TABLE tb_bridge_type_disease_rel (
   id                   SERIAL NOT NULL,
   inspection_disease_id INT4                 NULL,
   bridge_type_id       INT4                 NULL,
   seq_num              INT4                 NULL,
   CONSTRAINT PK_tb_bridge_type_disease_rel PRIMARY KEY(id)
);
COMMENT ON TABLE tb_bridge_type_disease_rel IS '结构物类型巡检病害关联表';
COMMENT ON COLUMN tb_bridge_type_disease_rel.id IS '主键id';
COMMENT ON COLUMN tb_bridge_type_disease_rel.inspection_disease_id IS '巡检病害表id';
COMMENT ON COLUMN tb_bridge_type_disease_rel.bridge_type_id IS '桥梁类型表id';
COMMENT ON COLUMN tb_bridge_type_disease_rel.seq_num IS '顺序';

INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (1, 1, 1, 1);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (2, 2, 1, 2);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (3, 3, 1, 3);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (4, 4, 1, 4);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (5, 5, 1, 5);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (6, 6, 1, 6);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (7, 7, 1, 7);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (8, 8, 1, 8);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (9, 9, 1, 9);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (10, 10, 1, 10);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (11, 11, 1, 11);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (12, 12, 1, 12);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (13, 13, 1, 13);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (14, 14, 1, 14);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (15, 16, 1, 15);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (16, 17, 1, 16);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (17, 20, 1, 17);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (18, 21, 1, 18);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (19, 23, 1, 19);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (20, 24, 1, 20);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (21, 25, 1, 21);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (22, 26, 1, 22);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (23, 1, 2, 1);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (24, 2, 2, 2);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (25, 3, 2, 3);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (26, 4, 2, 4);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (27, 6, 2, 5);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (28, 7, 2, 6);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (29, 9, 2, 7);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (30, 10, 2, 8);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (31, 11, 2, 9);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (32, 12, 2, 10);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (33, 15, 2, 11);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (34, 65, 2, 12);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (35, 66, 2, 13);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (36, 67, 2, 14);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (37, 21, 2, 15);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (38, 23, 2, 16);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (39, 24, 2, 17);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (40, 25, 2, 18);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (41, 26, 2, 19);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (42, 1, 3, 1);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (43, 2, 3, 2);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (44, 3, 3, 3);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (45, 4, 3, 4);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (46, 6, 3, 5);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (47, 7, 3, 6);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (48, 9, 3, 7);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (49, 10, 3, 8);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (50, 11, 3, 9);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (51, 12, 3, 10);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (52, 15, 3, 11);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (53, 68, 3, 12);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (54, 17, 3, 13);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (55, 67, 3, 14);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (56, 21, 3, 15);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (57, 23, 3, 16);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (58, 24, 3, 17);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (59, 25, 3, 18);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (60, 26, 3, 19);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (61, 1, 4, 1);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (62, 2, 4, 2);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (63, 3, 4, 3);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (64, 4, 4, 4);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (65, 5, 4, 5);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (66, 6, 4, 6);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (67, 7, 4, 7);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (68, 8, 4, 8);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (69, 9, 4, 9);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (70, 10, 4, 10);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (71, 11, 4, 11);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (72, 12, 4, 12);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (73, 13, 4, 13);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (74, 14, 4, 14);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (75, 69, 4, 15);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (76, 70, 4, 16);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (77, 71, 4, 17);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (78, 72, 4, 18);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (79, 20, 4, 19);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (80, 21, 4, 20);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (81, 23, 4, 21);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (82, 24, 4, 22);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (83, 25, 4, 23);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (84, 26, 4, 24);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (85, 1, 5, 1);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (86, 2, 5, 2);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (87, 3, 5, 3);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (88, 4, 5, 4);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (89, 5, 5, 5);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (90, 6, 5, 6);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (91, 7, 5, 7);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (92, 8, 5, 8);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (93, 9, 5, 9);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (94, 10, 5, 10);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (95, 11, 5, 11);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (96, 12, 5, 12);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (97, 13, 5, 13);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (98, 14, 5, 14);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (99, 73, 5, 15);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (100, 16, 5, 16);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (101, 72, 5, 17);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (102, 20, 5, 18);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (103, 21, 5, 19);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (104, 23, 5, 20);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (105, 24, 5, 21);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (106, 25, 5, 22);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (107, 26, 5, 23);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (108, 1, 6, 1);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (109, 2, 6, 2);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (110, 3, 6, 3);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (111, 4, 6, 4);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (112, 5, 6, 5);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (113, 6, 6, 6);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (114, 7, 6, 7);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (115, 8, 6, 8);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (116, 9, 6, 9);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (117, 10, 6, 10);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (118, 11, 6, 11);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (119, 12, 6, 12);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (120, 13, 6, 13);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (121, 14, 6, 14);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (122, 15, 6, 15);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (123, 16, 6, 16);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (124, 17, 6, 17);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (125, 18, 6, 18);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (126, 19, 6, 19);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (127, 20, 6, 20);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (128, 21, 6, 21);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (129, 23, 6, 22);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (130, 24, 6, 23);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (131, 25, 6, 24);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (132, 26, 6, 25);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (133, 27, 0, 1);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (134, 28, 0, 2);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (135, 29, 0, 3);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (136, 30, 0, 4);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (137, 31, 0, 5);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (138, 32, 0, 6);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (139, 33, 0, 7);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (140, 34, 0, 8);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (141, 35, 0, 9);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (142, 36, 0, 10);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (143, 37, 0, 11);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (144, 38, 0, 12);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (145, 39, 0, 13);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (146, 40, 0, 14);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (147, 41, 0, 15);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (148, 42, 0, 16);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (149, 43, 0, 17);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (150, 44, 0, 18);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (151, 45, 0, 19);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (152, 46, 0, 20);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (153, 47, 0, 21);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (154, 48, 0, 22);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (155, 49, 0, 23);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (156, 50, 0, 24);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (157, 51, 0, 25);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (158, 52, 0, 26);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (159, 53, 0, 27);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (160, 54, 0, 28);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (161, 55, 0, 29);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (162, 56, 0, 30);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (163, 57, 0, 31);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (164, 58, 0, 32);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (165, 59, 0, 33);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (166, 60, 0, 34);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (167, 61, 0, 35);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (168, 62, 0, 36);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (169, 63, 0, 37);
INSERT INTO "public"."tb_bridge_type_disease_rel"("id", "inspection_disease_id", "bridge_type_id", "seq_num") VALUES (170, 64, 0, 38);


--重置病害选项表
delete from tb_inspection_disease_option;
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (1, 1, '完整', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (2, 1, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (3, 2, '完整', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (4, 2, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (5, 3, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (6, 3, '碎裂', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (7, 4, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (8, 4, '坑槽', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (9, 5, '平整', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (10, 5, '拥包', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (11, 6, '平整', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (12, 6, '车辙', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (13, 7, '平顺', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (14, 7, '跳车', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (15, 8, '完整', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (16, 8, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (17, 9, '完整', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (18, 9, '破损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (19, 10, '平整', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (20, 10, '坑塘', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (21, 11, '完整', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (22, 11, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (23, 12, '完整', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (24, 12, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (25, 13, '完整', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (26, 13, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (27, 14, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (28, 14, '阻塞', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (29, 15, '通畅', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (30, 15, '阻塞', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (31, 16, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (32, 16, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (33, 17, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (34, 17, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (35, 18, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (36, 18, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (37, 19, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (38, 19, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (39, 20, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (40, 20, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (41, 21, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (42, 21, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (43, 22, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (44, 22, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (45, 23, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (46, 23, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (47, 25, '桥面落物', 1, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (48, 25, '电线管道破损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (49, 25, '车辆事故', 3, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (50, 25, '车辆违规停放', 4, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (51, 24, '异常', 1, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (52, 24, '正常', 2, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (66, 27, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (67, 27, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (68, 28, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (69, 28, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (70, 29, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (71, 29, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (72, 30, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (73, 30, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (74, 31, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (75, 31, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (76, 32, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (77, 32, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (78, 33, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (79, 33, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (80, 34, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (81, 34, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (82, 35, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (83, 35, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (84, 36, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (85, 36, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (86, 37, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (87, 37, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (88, 38, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (89, 38, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (90, 39, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (91, 39, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (92, 40, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (93, 40, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (94, 41, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (95, 41, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (96, 42, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (97, 42, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (98, 43, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (99, 43, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (100, 44, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (101, 44, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (102, 45, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (103, 45, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (104, 46, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (105, 46, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (106, 47, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (107, 47, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (108, 48, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (109, 48, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (110, 49, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (111, 49, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (112, 50, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (113, 50, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (114, 51, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (115, 51, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (116, 52, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (117, 52, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (118, 53, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (119, 53, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (120, 54, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (121, 54, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (122, 55, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (123, 55, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (124, 56, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (125, 56, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (126, 57, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (127, 57, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (128, 58, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (129, 58, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (130, 59, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (131, 59, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (132, 60, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (133, 60, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (134, 61, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (135, 61, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (136, 62, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (137, 62, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (138, 63, '完好', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (139, 63, '缺损', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (141, 65, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (142, 65, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (143, 66, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (144, 66, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (145, 67, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (146, 67, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (147, 68, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (148, 68, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (149, 69, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (150, 69, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (151, 70, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (152, 70, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (153, 71, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (154, 71, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (155, 72, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (156, 72, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (157, 73, '正常', 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (158, 73, '异常', 2, 0);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (159, 26, NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (160, 64, NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease_option"("id", "inspection_disease_id", "name", "seq_num", "option_status") VALUES (161, 25, NULL, 1, 1);


--重置病害表
delete from tb_inspection_disease;
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (1, '桥名牌/限载牌', '桥名牌', '桥名牌', '桥名牌', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (2, '桥名牌/限载牌', '限载牌', '限载牌', '限载牌', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (3, '桥面系', '桥面系', '车行道', '碎裂', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (4, '桥面系', '桥面系', '车行道', '局部坑槽', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (5, '桥面系', '桥面系', '车行道', '拥包', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (6, '桥面系', '桥面系', '车行道', '车辙', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (7, '桥面系', '桥面系', '车行道', '桥头跳车', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (8, '桥面系', '桥面系', '车行道', '隔离栏', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (9, '桥面系', '桥面系', '人行道', '板件', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (10, '桥面系', '桥面系', '人行道', '铺装(砖)', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (11, '桥面系', '桥面系', '栏杆', '栏杆扶手', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (12, '桥面系', '桥面系', '栏杆', '栏杆立柱', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (13, '桥面系', '桥面系', '伸缩缝装置', '缺损', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (14, '桥面系', '桥面系', '伸缩缝装置', '阻塞', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (15, '桥面系', '桥面系', '泄水孔', '泄水孔', '处', 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (16, '上部结构', '上部结构', '上部结构', '主梁(整体完好情况、振动)', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (17, '上部结构', '上部结构', '上部结构', '横向联系', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (18, '上部结构', '上部结构', '上部结构', '拱肋(拱脚区、钢管节点)', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (19, '上部结构', '上部结构', '上部结构', '吊杆(上下锚头、振动)', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (20, '上部结构', '上部结构', '上部结构', '上部其他变异', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (21, '台/墩/附属物', '桥墩、桥台、附属物', '桥墩、桥台、附属物', '桥墩/桥台', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (22, '台/墩/附属物', '桥墩、桥台、附属物', '桥墩、桥台、附属物', '锥坡', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (23, '台/墩/附属物', '桥墩、桥台、附属物', '桥墩、桥台、附属物', '下部其他变异', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (24, '施工', '桥、桥区施工', '桥、桥区施工', '桥、桥区施工', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (25, '其他病害因素', '其他危及行车、行船、行人安全的病害因素', '其他危及行车、行船、行人安全的病害因素', '其他危及行车、行船、行人安全的病害因素', NULL, 1, 2);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (26, '其它说明', '其它说明', '其它说明', '其它说明', NULL, 1, 3);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (27, '洞口', '洞口', '洞口', '边坡危石', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (28, '洞口', '洞口', '洞口', '边坡积水', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (29, '洞口', '洞口', '洞口', '边沟淤塞', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (30, '洞口', '洞口', '洞口', '开裂', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (31, '洞口', '洞口', '洞口', '倾斜', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (32, '洞口', '洞口', '洞口', '沉陷', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (33, '洞门', '洞门', '洞门', '开裂', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (34, '洞门', '洞门', '洞门', '倾斜', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (35, '洞门', '洞门', '洞门', '沉陷', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (36, '洞门', '洞门', '洞门', '错台', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (37, '洞门', '洞门', '洞门', '起层', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (38, '洞门', '洞门', '洞门', '剥落', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (39, '洞门', '洞门', '洞门', '渗漏水', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (40, '衬砌', '衬砌', '衬砌', '裂纹', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (41, '衬砌', '衬砌', '衬砌', '错台', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (42, '衬砌', '衬砌', '衬砌', '起层', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (43, '衬砌', '衬砌', '衬砌', '剥落', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (44, '衬砌', '衬砌', '衬砌', '渗漏水', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (45, '路面', '路面', '路面', '落物', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (46, '路面', '路面', '路面', '油污', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (47, '路面', '路面', '路面', '滞水', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (48, '路面', '路面', '路面', '路面拱起', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (49, '路面', '路面', '路面', '坑槽', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (50, '路面', '路面', '路面', '开裂', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (51, '路面', '路面', '路面', '错台', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (52, '检修道', '检修道', '检修道', '结构破损', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (53, '检修道', '检修道', '检修道', '盖板缺损', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (54, '排水设施', '排水设施', '排水设施', '缺损', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (55, '排水设施', '排水设施', '排水设施', '堵塞', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (56, '排水设施', '排水设施', '排水设施', '积水', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (57, '吊顶/预埋件', '吊顶及各种预埋件', '吊顶及各种预埋件', '变形', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (58, '吊顶/预埋件', '吊顶及各种预埋件', '吊顶及各种预埋件', '缺损', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (59, '吊顶/预埋件', '吊顶及各种预埋件', '吊顶及各种预埋件', '漏水', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (60, '内饰', '内饰', '内饰', '脏污', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (61, '内饰', '内饰', '内饰', '变形', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (62, '内饰', '内饰', '内饰', '缺损', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (63, '标志标线轮廓标', '各种标志标线轮廓标', '各种标志标线轮廓标', '各种标志标线轮廓标', '处', 2, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (64, '其它说明', '其它说明', '其它说明', '其它说明', NULL, 2, 3);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (65, '上部结构', '上部结构', '上部结构', '主拱圈', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (66, '上部结构', '上部结构', '上部结构', '拱上结构', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (67, '上部结构', '上部结构', '上部结构', '桥面板', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (68, '上部结构', '上部结构', '上部结构', '拱片', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (69, '上部结构', '上部结构', '上部结构', '主缆', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (70, '上部结构', '上部结构', '上部结构', '吊索', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (71, '上部结构', '上部结构', '上部结构', '加劲梁', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (72, '上部结构', '上部结构', '上部结构', '索塔', NULL, 1, 1);
INSERT INTO "public"."tb_inspection_disease"("id", "name", "disease_part", "check_item", "damage_type", "unit", "structure_type", "type") VALUES (73, '上部结构', '上部结构', '上部结构', '斜拉索（包括锚具）', NULL, 1, 1);
