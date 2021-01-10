package com.juhe.testmanage.websocket;

import com.alibaba.fastjson.JSON;
import com.juhe.testmanage.service.ToyService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{name}/{age}")
@Component
public class Socket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<Socket> webSocketSet = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private static ToyService toyService;

    //websocket里面的参数
    private String name = "";
    private int age;

    //连接service层
    private static ApplicationContext applicationContext;
    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
        toyService  =  applicationContext.getBean(ToyService.class);
    }

/**
 * 连接建立成功调用的方法
 * 连接关闭调用的方法
 * <p>
 * 收到客户端消息后调用的方法
 *

 * <p>
 * 实现服务器主动推送
 * <p>
 * 群发自定义消息
 */
    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name, @PathParam("age") int age) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        this.name = name;
        this.age = age;
        try {
            sendMessage("连接成功");
            System.err.println("当前连接人数: "+ onlineCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/**
 * 连接关闭调用的方法
 */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
    }

/**
 * 收到客户端消息后调用的方法
 *
 * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        //群发消息
        for (Socket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

/**
 *
 * @param session
 * @param error
 */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
/**
 * 实现服务器主动推送
 */
    public void sendMessage(String message) throws IOException {
        List<Map<String, Object>> list =  toyService.socketList(this.name, this.age);
        String messages = JSON.toJSONString(list);
        this.session.getBasicRemote().sendText(messages);
    }
/**
 * 群发自定义消息
 * */
    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException {
        for (Socket item : webSocketSet) {
            item.sendMessage(message);
        }
    }
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount() {
        Socket.onlineCount++;
    }
    public static synchronized void subOnlineCount() {
        Socket.onlineCount--;
    }
    public static CopyOnWriteArraySet<Socket> getWebSocketSet() {
        return webSocketSet;
    }
}
