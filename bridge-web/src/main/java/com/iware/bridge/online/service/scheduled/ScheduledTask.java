package com.iware.bridge.online.service.scheduled;

import com.iware.bridge.evaluation.service.EvaluationPlanService;
import com.iware.bridge.inspection.serivce.PlanService;
import com.iware.bridge.online.service.SensorDataService;
import com.iware.bridge.online.service.SensorService;
import com.iware.bridge.online.service.SensorWeightDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 调度任务配置
 *
 * @author ZRB
 */

@Configuration
@EnableScheduling
public class ScheduledTask {

    @Autowired
    private SensorDataService sensorDataService;
    @Autowired
    private SensorService sensorService;

    @Resource
    private PlanService planService;

    @Autowired
    private EvaluationPlanService evaluationPlanService;

    @Autowired
    private SensorWeightDataService sensorWeightDataService;

    @Scheduled(cron = "${common.schedule.day.cron}")
    public void addDay() {
        sensorDataService.addSensorDay();
    }

    @Scheduled(cron = "${common.schedule.hour.cron}")
    public void addHour() {
        sensorDataService.addSensorHour();// 10分钟
        sensorDataService.addSensorMinute();// 1分钟
        //添加完数据查询一个小时内传感器是否有数据，修改状态
        sensorService.updateStatusByFrequency();
    }

    @Scheduled(cron = "${common.schedule.minute.cron}")
    public void addSecond() {
        sensorDataService.addSensorSecond();// 3秒
        sensorDataService.addSensorSecondOne();// 1秒
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void planStatus() {
        planService.resetPlanStatus();
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void planStatus2() {
        evaluationPlanService.planTimeout();
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void sensorWeight() {
        sensorWeightDataService.weightHourData();
    }

}
