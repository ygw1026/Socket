package com.nhnacademy.client.main;

import com.nhnacademy.client.server.MessageClient;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class ClientMain {
    public static void main(String[] args) throws IOException {
        MessageClient messageClient = new MessageClient();
        Thread thread = new Thread(messageClient);
        thread.start();
    }
}
