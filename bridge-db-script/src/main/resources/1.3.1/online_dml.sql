---------------------------------------------------------------------------
--通用模块初始数据
---------------------------------------------------------------------------

--产商表
INSERT INTO "public"."tb_company"("id", "company", "remarks") VALUES (1, '基康', NULL);
INSERT INTO "public"."tb_company"("id", "company", "remarks") VALUES (2, '东华', NULL);


--传感器类型表
INSERT INTO "public"."tb_sensor_type"("id", "name", "remarks") VALUES (1, '加速度传感器', NULL);
INSERT INTO "public"."tb_sensor_type"("id", "name", "remarks") VALUES (2, '应变传感器', NULL);
INSERT INTO "public"."tb_sensor_type"("id", "name", "remarks") VALUES (3, '索力传感器', NULL);
INSERT INTO "public"."tb_sensor_type"("id", "name", "remarks") VALUES (4, '动态采集仪', NULL);
INSERT INTO "public"."tb_sensor_type"("id", "name", "remarks") VALUES (5, '温度传感器', NULL);
INSERT INTO "public"."tb_sensor_type"("id", "name", "remarks") VALUES (6, '倾角传感器', NULL);
INSERT INTO "public"."tb_sensor_type"("id", "name", "remarks") VALUES (7, '称重传感器', NULL);
INSERT INTO "public"."tb_sensor_type"("id", "name", "remarks") VALUES (8, '位移传感器', NULL);


--传感器细项表
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "sensor_type_id", "unit") VALUES (1, '加速度', 1, 'm*s^-2');
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "sensor_type_id", "unit") VALUES (2, '应变', 2, 'n');
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "sensor_type_id", "unit") VALUES (3, '索力', 3, 'N');
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "sensor_type_id", "unit") VALUES (4, '动态采集', 4, 'Hz');
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "sensor_type_id", "unit") VALUES (5, '温度', 5, NULL);
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "sensor_type_id", "unit") VALUES (6, '倾角', 6, NULL);
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "sensor_type_id", "unit") VALUES (7, '称重', 7, NULL);
INSERT INTO "public"."tb_sensor_details"("id", "detail_name", "sensor_type_id", "unit") VALUES (8, '位移', 8, NULL);


--传感器原理表
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (1, '振弦式', NULL);
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (2, '电阻式', NULL);
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (3, '电感式', NULL);
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (4, '电容式', NULL);
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (5, '压电式', NULL);
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (6, '磁电式', NULL);
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (7, '热电式', NULL);
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (8, '光点式', NULL);
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (9, '数字式', NULL);
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (10, '光纤式', NULL);
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (11, '超声波式', NULL);
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (12, '热敏式', NULL);
INSERT INTO "public"."tb_sensor_principle"("id", "principle", "remarks") VALUES (13, 'MEMS', NULL);

--车轴类型
INSERT INTO "public"."tb_axle_type"("id", "name") VALUES (1, '二轴车');
INSERT INTO "public"."tb_axle_type"("id", "name") VALUES (2, '三轴车');
INSERT INTO "public"."tb_axle_type"("id", "name") VALUES (3, '四轴车');
INSERT INTO "public"."tb_axle_type"("id", "name") VALUES (4, '五轴车');
INSERT INTO "public"."tb_axle_type"("id", "name") VALUES (5, '六轴车');
INSERT INTO "public"."tb_axle_type"("id", "name") VALUES (6, '六轴以上车');

--车轴类型模型关联表
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (10, 1, 23);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (11, 1, 24);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (12, 1, 25);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (13, 1, 26);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (14, 1, 20);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (15, 1, 27);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (16, 1, 28);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (17, 1, 29);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (18, 2, 30);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (19, 2, 31);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (20, 2, 32);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (21, 2, 33);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (22, 2, 34);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (23, 2, 35);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (24, 2, 37);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (25, 2, 38);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (26, 2, 39);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (27, 3, 40);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (28, 3, 41);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (29, 3, 42);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (30, 3, 43);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (31, 3, 44);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (32, 3, 45);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (33, 3, 46);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (34, 3, 47);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (35, 3, 48);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (36, 3, 49);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (37, 4, 50);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (38, 4, 51);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (39, 4, 53);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (40, 4, 54);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (41, 4, 55);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (42, 4, 56);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (43, 4, 57);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (44, 4, 58);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (45, 4, 59);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (46, 5, 60);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (47, 5, 61);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (48, 5, 62);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (49, 5, 63);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (50, 5, 64);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (51, 5, 65);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (52, 5, 66);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (53, 5, 67);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (54, 5, 68);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (55, 5, 69);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (56, 5, 70);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (57, 5, 71);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (58, 5, 72);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (59, 5, 73);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (60, 5, 74);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (61, 5, 75);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (62, 5, 76);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (63, 5, 77);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (64, 5, 78);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (65, 5, 79);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (66, 5, 80);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (67, 5, 81);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (68, 5, 82);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (69, 5, 83);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (70, 5, 84);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (71, 5, 85);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (72, 5, 86);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (73, 5, 87);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (74, 5, 88);
INSERT INTO "public"."tb_axle_type_model_rel"("id", "axle_type_id", "model_id") VALUES (75, 5, 89);



