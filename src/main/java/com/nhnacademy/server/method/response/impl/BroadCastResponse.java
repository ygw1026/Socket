package com.nhnacademy.server.method.response.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.runable.MessageServer;
import com.nhnacademy.server.thread.channel.Session;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BroadCastResponse implements Response{
    @Override
    public String getMethod() {
        return "broadcast";
    }

    @Override
    public String execute(String value) {
        List<String> ids = MessageServer.getClientIds();
        int sendCount=0;

        for(String id : ids){
            Socket client = MessageServer.getClientSocket(id);
            if(client.isClosed()){
                if(Session.isLogin()) {
                    MessageServer.removeClient(Session.getCurrentId());
                }
                continue;
            }
            try{
                log.debug("id:{},{}", id, client);
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                out.println(value);
                sendCount++;
            }catch (IOException e) {
                log.debug("broadcast-error:{}", e.getMessage(), e);
            }
        }
        return String.format("{%d}명에게 \"{%S}\"를 전송 했습니다.", sendCount, value);
    }
}
