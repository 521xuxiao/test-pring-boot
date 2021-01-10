package com.juhe.testmanage;

import com.juhe.testmanage.websocket.Socket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication

public class TestManageApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(TestManageApplication.class, args);
        Socket.setApplicationContext(applicationContext);
    }

}
