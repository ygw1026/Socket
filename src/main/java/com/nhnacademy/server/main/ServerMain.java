package com.nhnacademy.server.main;

import com.nhnacademy.server.runable.MessageServer;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class ServerMain {
    public static void main(String[] args) throws IOException {
        //TODO#1-10 MessageServer 객체를 생성하고 시작 합니다.
        //왼쪽 녹색 화살표 버튼을 클릭 하면  서버를 실행 할 수 있습니다.
        MessageServer messageServer = new MessageServer();
        Thread thread = new Thread(messageServer);
        thread.start();
    }
}