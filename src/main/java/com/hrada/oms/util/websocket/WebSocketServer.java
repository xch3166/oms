package com.hrada.oms.util.websocket;

import com.hrada.oms.dao.common.UserRepository;
import com.hrada.oms.model.common.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shin on 2018/11/7.
 */
@Component
@ServerEndpoint(value = "/webSocket")
public class WebSocketServer {

    @Autowired
    UserRepository userRepository;

    private static Logger log = LogManager.getLogger(WebSocketServer.class);

    private static int onlineCount = 0;
    private static ConcurrentHashMap<Long, WebSocketServer> ClientMap = new ConcurrentHashMap<>();
    private Session session;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        ClientMap.put(user.getId(), this); 
        addOnlineCount();
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        ClientMap.remove(this);
        subOnlineCount();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("消息推送发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 发送信息给指定ID用户
     * @param message
     * @param id
     * @throws IOException
     */
    public void sendToUser(String message,Long id) throws IOException {
        if (ClientMap.get(id) != null) {
            ClientMap.get(id).sendMessage(message);
        }
    }

    /**
     * 发送信息给所有人
     * @param message
     * @throws IOException
     */
    public void sendToAll(String message) {
        for (Long key : ClientMap.keySet()) {
            try {
                ClientMap.get(key).sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
