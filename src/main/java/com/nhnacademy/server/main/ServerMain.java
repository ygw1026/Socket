package com.nhnacademy.server.main;

import com.nhnacademy.server.main.server.MessageServer;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class ServerMain {
    public static void main(String[] args) throws IOException {
        MessageServer messageServer = new MessageServer();
        Thread thread = new Thread(messageServer);
        thread.start();
    }
}
