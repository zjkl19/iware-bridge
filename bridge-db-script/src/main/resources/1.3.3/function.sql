---------------------------------------------------------------------------
--统计数据优化部分历史数据处理
---------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION add_models ()
RETURNS void AS $$
  DECLARE
		sensor tb_sensor%ROWTYPE;
		modelRow record;
  BEGIN

		--遍历传感器
		FOR sensor IN
			SELECT * FROM tb_sensor where sensor_type_id = 7
			LOOP
					raise notice '传感器：%',sensor.sensor_coding;
					--遍历模型--
					FOR modelRow IN
							-----车型数据------
							select sensor_coding, hour as time,
							sum(case when single_shaft_nuber=2 then cnt else 0 end) as two_model,
							sum(case when single_shaft_nuber=3 then cnt else 0 end) as three_model,
							sum(case when single_shaft_nuber=4 then cnt else 0 end) as four_model,
							sum(case when single_shaft_nuber=5 then cnt else 0 end) as five_model,
							sum(case when single_shaft_nuber=6 then cnt else 0 end) as six_model,
							sum(case when single_shaft_nuber>6 then cnt else 0 end) as six_up_model
							from (
							SELECT date_trunc('hour', sampling_time) + interval '1 H' as hour, sensor_coding, single_shaft_nuber,
							COUNT(1) as cnt FROM tb_sensor_weight
							WHERE sampling_time < '2021-11-09 00:00:00' and sampling_time >= '2020-05-29 00:00:00' AND sensor_coding = sensor.sensor_coding
							GROUP BY hour, sensor_coding, single_shaft_nuber
							ORDER BY hour DESC, sensor_coding, single_shaft_nuber
							) as rs group by sensor_coding, hour order by hour desc
							-----车型数据------
						LOOP

							INSERT INTO tb_sensor_weight_hour_model (time,structure_id,two_model,three_model,four_model,five_model,six_model,six_up_model,sensor_id) VALUES (modelRow.time::timestamp,sensor.structure_id,modelRow.two_model,modelRow.three_model,modelRow.four_model,modelRow.five_model,modelRow.six_model,modelRow.six_up_model,sensor.id);
							--raise notice '时间：%, 两轴：%,  三轴：%, 四轴：%,  五轴：%, 六轴：%,  六轴以上：%',modelRow.time, modelRow.two_model,modelRow.three_model,modelRow.four_model,modelRow.five_model,modelRow.six_model,modelRow.six_up_model;

						END LOOP;
			END LOOP;

			RETURN;
	END
$$ LANGUAGE plpgsql;





CREATE OR REPLACE FUNCTION add_car_speed ()
RETURNS void AS $$
  DECLARE
		sensor tb_sensor%ROWTYPE;
		speed record;
  BEGIN

		--遍历传感器
		FOR sensor IN
			SELECT * FROM tb_sensor where sensor_type_id = 7
			LOOP
					raise notice '传感器：%',sensor.sensor_coding;
					--遍历模型--
					FOR speed IN
							-----车速数据------
							SELECT date_trunc('hour', sampling_time) + interval '1 H' as time, sensor_coding,
							sum(case when car_speed is null or car_speed <= 20 then 1 else 0 end) as twenty_speed,
							sum(case when car_speed > 20 and car_speed <= 40 then 1 else 0 end) as twenty_forty_speed,
							sum(case when car_speed > 40 and car_speed <= 60 then 1 else 0 end) as forty_sixty_speed,
							sum(case when car_speed > 60 and car_speed <= 80 then 1 else 0 end) as sixty_eighty_speed,
							sum(case when car_speed > 80 and car_speed <= 100 then 1 else 0 end) as eighty_hundred_speed,
							sum(case when car_speed > 100 then 1 else 0 end) as hundred_speed
							FROM tb_sensor_weight
							WHERE sampling_time < '2021-11-09 00:00:00' and sampling_time >= '2020-05-29 00:00:00' AND sensor_coding = sensor.sensor_coding
							GROUP BY time, sensor_coding
							ORDER BY time DESC, sensor_coding
							-----车速数据------
						LOOP

							INSERT INTO tb_sensor_weight_hour_speed (time,structure_id,sensor_id,twenty_speed,twenty_forty_speed,forty_sixty_speed,sixty_eighty_speed,eighty_hundred_speed,hundred_speed) VALUES (speed.time::timestamp,sensor.structure_id,sensor.id,speed.twenty_speed,speed.twenty_forty_speed,speed.forty_sixty_speed,speed.sixty_eighty_speed,speed.eighty_hundred_speed,speed.hundred_speed);
							--raise notice '时间：%, <20：%, 20~40：%,  40~60：%, 60~80：%,  80~100：%, >100：%',speed.time,speed.twenty_speed,speed.twenty_forty_speed,speed.forty_sixty_speed,speed.sixty_eighty_speed,speed.eighty_hundred_speed,speed.hundred_speed;

						END LOOP;
			END LOOP;

			RETURN;
	END
$$ LANGUAGE plpgsql;




CREATE OR REPLACE FUNCTION add_car_weight ()
RETURNS void AS $$
  DECLARE
		sensor tb_sensor%ROWTYPE;
		weight record;
  BEGIN

		--遍历传感器
		FOR sensor IN
			SELECT * FROM tb_sensor where sensor_type_id = 7
			LOOP
					raise notice '传感器：%',sensor.sensor_coding;
					--遍历模型--
					FOR weight IN
							-----车重数据------
							SELECT date_trunc('hour', sampling_time) + interval '1 H' as time, sensor_coding,
							max(CAST(total_weight/1000::numeric AS DECIMAL(10,2))) as max_weight,
							sum(case when total_weight is null or total_weight <= 10000 then 1 else 0 end) as ten_weight,
							sum(case when total_weight > 10000 and total_weight <= 30000 then 1 else 0 end) as ten_thirty_weight,
							sum(case when total_weight > 30000 and total_weight <= 50000 then 1 else 0 end) as thirty_fifty_weight,
							sum(case when total_weight > 50000 then 1 else 0 end) as fifty_weight,
							sum(case when total_weight > 60000 then 1 else 0 end) as sixty_weight,
							sum(case when total_weight > 70000 then 1 else 0 end) as seventy_weight,
							sum(case when total_weight > 80000 then 1 else 0 end) as eighty_weight,
							sum(case when total_weight > 90000 then 1 else 0 end) as ninety_weight,
							sum(case when total_weight > 100000 then 1 else 0 end) as hundred_weight,

							sum(case when single_shaft_nuber = 2 and transfinite_labeled = '1' then 1 else 0 end) as two_model_weight,
							sum(case when single_shaft_nuber = 2 and transfinite_labeled = '0' then 1 else 0 end) as two_model_weight_not,
							sum(case when single_shaft_nuber = 3 and transfinite_labeled = '1' then 1 else 0 end) as three_model_weight,
							sum(case when single_shaft_nuber = 3 and transfinite_labeled = '0' then 1 else 0 end) as three_model_weight_not,
							sum(case when single_shaft_nuber = 4 and transfinite_labeled = '1' then 1 else 0 end) as four_model_weight,
							sum(case when single_shaft_nuber = 4 and transfinite_labeled = '0' then 1 else 0 end) as four_model_weight_not,
							sum(case when single_shaft_nuber = 5 and transfinite_labeled = '1' then 1 else 0 end) as five_model_weight,
							sum(case when single_shaft_nuber = 5 and transfinite_labeled = '0' then 1 else 0 end) as five_model_weight_not,
							sum(case when single_shaft_nuber = 6 and transfinite_labeled = '1' then 1 else 0 end) as six_model_weight,
							sum(case when single_shaft_nuber = 6 and transfinite_labeled = '0' then 1 else 0 end) as six_model_weight_not,
							sum(case when single_shaft_nuber > 6 and transfinite_labeled = '1' then 1 else 0 end) as six_up_model_weight,
							sum(case when single_shaft_nuber > 6 and transfinite_labeled = '0' then 1 else 0 end) as six_up_model_weight_not
							FROM tb_sensor_weight
							WHERE sampling_time < '2021-11-09 00:00:00' and sampling_time >= '2020-05-29 00:00:00' AND sensor_coding = sensor.sensor_coding
							GROUP BY time, sensor_coding
							ORDER BY time DESC, sensor_coding
							-----车重数据------
						LOOP

							INSERT INTO tb_sensor_weight_hour_weight (time,structure_id,sensor_id,max_weight,ten_weight,ten_thirty_weight,thirty_fifty_weight,fifty_weight,sixty_weight,seventy_weight,eighty_weight,ninety_weight,hundred_weight,two_model_weight,two_model_weight_not,three_model_weight,three_model_weight_not,four_model_weight,four_model_weight_not,five_model_weight,five_model_weight_not,six_model_weight,six_model_weight_not,six_up_model_weight,six_up_model_weight_not) VALUES (weight.time::timestamp,sensor.structure_id,sensor.id,weight.max_weight,weight.ten_weight,weight.ten_thirty_weight,weight.thirty_fifty_weight,weight.fifty_weight,weight.sixty_weight,weight.seventy_weight,weight.eighty_weight,weight.ninety_weight,weight.hundred_weight,weight.two_model_weight,weight.two_model_weight_not,weight.three_model_weight,weight.three_model_weight_not,weight.four_model_weight,weight.four_model_weight_not,weight.five_model_weight,weight.five_model_weight_not,weight.six_model_weight,weight.six_model_weight_not,weight.six_up_model_weight,weight.six_up_model_weight_not);
							--raise notice '时间：%, 车重[<10：%, 10~30：%,  30~50：%, >50：%,  >60：%, >70：%, >80：%,  >90：%, >100：%] 最重：%',weight.time,weight.ten_weight,weight.ten_thirty_weight,weight.thirty_fifty_weight,weight.fifty_weight,weight.sixty_weight,weight.seventy_weight,weight.eighty_weight,weight.ninety_weight,weight.hundred_weight,weight.max_weight;

						END LOOP;
			END LOOP;

			RETURN;
	END
$$ LANGUAGE plpgsql;
