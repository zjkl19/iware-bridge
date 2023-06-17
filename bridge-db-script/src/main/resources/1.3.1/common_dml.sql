---------------------------------------------------------------------------
--通用模块初始数据
---------------------------------------------------------------------------

--部位信息表
INSERT INTO "public"."tb_part_info"("id", "name", "english_name") VALUES (1, '桥面系', '00');
INSERT INTO "public"."tb_part_info"("id", "name", "english_name") VALUES (2, '上部结构', '01');
INSERT INTO "public"."tb_part_info"("id", "name", "english_name") VALUES (3, '下部结构', '01');
INSERT INTO "public"."tb_part_info"("id", "name", "english_name") VALUES (4, '人行地下通道', '10');


--构件信息表
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (1, '桥面铺装', NULL, 1);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (2, '桥头平顺', NULL, 1);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (3, '伸缩缝', NULL, 1);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (4, '排水系统', NULL, 1);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (5, '栏杆或护栏', NULL, 1);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (6, '人行道块件', NULL, 1);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (7, '钢结构物', NULL, 2);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (8, 'PC或RC梁式构件', NULL, 2);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (9, '横向联系', NULL, 2);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (10, '拱桥横向联系', NULL, 2);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (11, '防落梁装置', NULL, 2);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (12, '主拱圈', NULL, 2);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (13, '拱上构造-实腹式', NULL, 2);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (14, '拱上构造-空腹式', NULL, 2);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (15, '人行天桥外部装饰板', NULL, 2);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (16, '台帽盖梁', NULL, 3);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (17, '墩台身', NULL, 3);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (18, '支座', NULL, 3);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (19, '基础', NULL, 3);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (20, '耳墙翼墙（台）', NULL, 3);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (21, '拱脚', NULL, 3);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (22, '衬砌结构', NULL, 4);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (23, '挡墙结构', NULL, 4);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (24, '变形缝', NULL, 4);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (25, '梯道', NULL, 4);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (26, '栏杆或护栏', NULL, 4);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (27, '道面', NULL, 4);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (28, '排水设施', NULL, 4);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (29, '照明设施', NULL, 4);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (30, '通风设施', NULL, 4);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (31, '雨棚', NULL, 4);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (32, '台帽（台）', NULL, 3);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (33, '台身（台）', NULL, 3);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (34, '基础（台）', NULL, 3);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (35, '拱脚（台）', NULL, 3);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (36, '主拱圈（桁）', NULL, 2);
INSERT INTO "public"."tb_component_info"("id", "name", "english_name", "part_id") VALUES (37, '吊杆', NULL, 0);
