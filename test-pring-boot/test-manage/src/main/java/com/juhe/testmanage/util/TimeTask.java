package com.juhe.testmanage.util;

import com.juhe.testmanage.service.ToyService;
import com.juhe.testmanage.websocket.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArraySet;

@Component
@EnableScheduling
public class TimeTask {
    private static Logger logger = LoggerFactory.getLogger(TimeTask.class);

    @Autowired
    private ToyService toyService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void test() {
        CopyOnWriteArraySet<Socket> websocket = Socket.getWebSocketSet();
        websocket.forEach(c->{
            try {
                c.sendMessage("");
            }catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Scheduled(cron="0 59 23 * * ?")
    public void methods1() {
        toyService.addData();
    }
}
