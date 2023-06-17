package com.iware.bridge.init;

import com.iware.bridge.online.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class SensorCommandLineRunner implements CommandLineRunner {

    @Autowired
    private SensorService sensorService;

    @Override
    public void run(String... args) {
        sensorService.loadSensorList();
    }
}
