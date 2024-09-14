package com.nhnacademy.client.main;

import com.nhnacademy.client.runable.MessageClient;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class ClientMain {
    public static void main(String[] args) {
        //TODO#2-8 messageClient 객체를 생성하고 시작 합니다.
        MessageClient messageClient = new MessageClient();
        Thread thread = new Thread(messageClient);
        thread.start();
    }
}
