package com.iware.bridge.init;

import com.iware.bridge.online.client.ClientEnum;
import com.iware.bridge.online.client.IMessageProcess;
import com.iware.bridge.online.service.DataProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ConnectCommandLineRunner implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(ConnectCommandLineRunner.class);

    @Value("${remote-connect.username}")
    private String username;
    @Value("${remote-connect.password}")
    private String password;
    @Autowired
    private DataProcessService dataProcessService;

    @Override
    public void run(String... args) {

        new Thread(() -> {
            try {
                ClientEnum.CLIENT.initClient(username, password, new IMessageProcess() {
                    @Override
                    public void onOpen() {
                        logger.info("socket onOpen：");
                    }

                    @Override
                    public void onMessage(String message) {
                        logger.info("socket message：" + message);
                        dataProcessService.addSocketData(message);
                    }

                    @Override
                    public void onError(String error) {
                        logger.error("socket error：" + error);
                    }

                    @Override
                    public void onClose() {
                        logger.info("socket close");
                    }
                });
            } catch (Exception e) {
                logger.error("socket 连接错误：" + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

}
