package com.nhnacademy.client.runable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;
 
 @Slf4j
 public class MessageClient implements Runnable {
     private final static String DEFAULT_SERVER_ADDRESS = "localhost";
     private final static int DEFAULT_PORT = 8888;
 
     private final String serverAddress;
     private final int serverPort;
 
     private final Socket clientSocket;
 
     public MessageClient() {
         this(DEFAULT_SERVER_ADDRESS,DEFAULT_PORT);
     }
 
     public MessageClient(String serverAddress, int serverPort){
         if (StringUtils.isEmpty(serverAddress) || serverPort <=0){
            throw new IllegalArgumentException();
         }
 
         this.serverAddress = serverAddress;
         this.serverPort = serverPort;
 
         try {
             clientSocket = new Socket(this.serverAddress, this.serverPort);
         } catch (Exception e) {
             log.debug("create client socket error : {}",e.getMessage(),e);
             throw new RuntimeException(e);
         }
     }
 
     @Override
     public void run() {
         try(
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
         ){
 
             System.out.print("send-message:");
             String userMessage;
 
             /*TODO#2-6 while 조건을 수정하세요
                 - stdIn.readLine()  : 사용자로 부터 입력받은 값이 null 아니라면 입력받은 userMessage를 PrintWriter out을 이용해서 서버로 전송 합니다.
              */
             while ((userMessage = stdIn.readLine()) != null){
                out.println(userMessage);
                //System.out.println 을 log.debug 로 바꿔도 결과가 같나?
                 System.out.println(String.format("[clinet]recv-message:%s",clientIn.readLine()));
                 System.out.print("send-message:");
             }
 
         }catch (Exception e){
             log.debug("message:{}",e.getMessage(),e);
             log.debug("client close");
             if(e instanceof InterruptedIOException){
                 log.debug("exit!");
             }
         }finally {
             if(Objects.nonNull(clientSocket)) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
             }
         }
     }
 }
 