package com.nhnacademy.client.main;

import java.io.IOException;

import com.nhnacademy.client.runable.MessageClient;

import lombok.extern.slf4j.Slf4j;
 
 @Slf4j
 public class ClientMain {
     public static void main(String[] args) throws IOException {
         MessageClient messageClient = new MessageClient();
         Thread thread = new Thread(messageClient);
         thread.start();
     }
 }