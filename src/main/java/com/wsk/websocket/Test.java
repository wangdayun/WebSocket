package com.wsk.websocket;

import com.wsk.websocket.server.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.imageio.IIOException;

/**
 * 描述：
 *
 * @author: dayun_wang 2019-08-30
 */
@Configuration
@EnableScheduling
public class Test {

    @Autowired
    private WebSocket webSocket;

    @Scheduled(fixedDelay = 3 * 1000)
    public void sendMessage() throws Exception {
        webSocket.sendMessageAll("ssss");
    }
}
