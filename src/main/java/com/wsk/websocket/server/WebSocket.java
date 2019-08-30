package com.wsk.websocket.server;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 描述：webSocket服务
 *
 * @author: dayun_wang 2019-08-30
 */
@ServerEndpoint("/websocket")
@Component
public class WebSocket {

    private static int onlineCount = 0;

    public static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;

    /**
     * 连接
     */
    @OnOpen
    public void onOpen(Session session)throws Exception{
        this.session = session;

        addOnlineCount();
        webSocketSet.add(this);
        onMessage("Hello,很高兴认识你！", this.session);
    }

    /**
     * 关闭
     *
     */
    @OnClose
    public void onClose()throws Exception{
        webSocketSet.remove(this);
        subOnlineCount();
    }

    /**
     * 接收
     */
    @OnMessage
    public void onMessage(String message,Session session) throws Exception{
        this.session = session;
        message = "来自客户端的消息:" + message;
        try {
            sendMessageTo(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }

    public void sendMessageTo(String message)throws IOException {
        this.session.getAsyncRemote().sendText(message);
    }

    public void sendMessageAll(String message)throws IOException{
        for (WebSocket item : webSocketSet) {
            try {
                item.sendMessageTo(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }

    public static synchronized void addOnlineCount(){
        onlineCount++;
    }

    public static synchronized void subOnlineCount(){
        onlineCount--;
    }
}
