package com.nhnacademy.client.runable;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.nhnacademy.client.event.action.impl.SendMessageAction;
import com.nhnacademy.client.event.observer.Observer;
import com.nhnacademy.client.event.observer.impl.MessageSendObserver;
import com.nhnacademy.client.event.subject.EventType;
import com.nhnacademy.client.event.subject.MessageSubject;
import com.nhnacademy.client.event.subject.Subject;
import com.nhnacademy.client.ui.form.MessageClientForm;

import lombok.extern.slf4j.Slf4j;
 
 @Slf4j
 public class MessageClient implements Runnable {
     private final static String DEFAULT_SERVER_ADDRESS = "localhost";
     private final static int DEFAULT_PORT = 8888;
 
     private final String serverAddress;
     private final int serverPort;
 
     private final Socket clientSocket;

     private final Subject subject;
 
     public MessageClient() {
         this(DEFAULT_SERVER_ADDRESS,DEFAULT_PORT);
     }
 
     public MessageClient(String serverAddress, int serverPort){

         if (StringUtils.isEmpty(serverAddress) || serverPort <=0){
            throw new IllegalArgumentException();
         }
 
         this.serverAddress = serverAddress;
         this.serverPort = serverPort;
         
         subject = new MessageSubject();
 
         try {
             clientSocket = new Socket(this.serverAddress, this.serverPort);

             if (clientSocket.isConnected()) {
                log.debug("client connect!");
                startReceivedMessageClient();
             }
         } catch (Exception e) {
             log.debug("create client socket error : {}",e.getMessage(),e);
             throw new RuntimeException(e);
         }
     }

     private void startReceivedMessageClient() {
        ReceivedMessageClient receivedMessageClient = new ReceivedMessageClient(clientSocket, subject);
        Thread thread = new Thread(receivedMessageClient);
        thread.start();
     }
 
     @Override
     public void run() {
         try(
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
         ){
            configSendObserver(out);

            MessageClientForm.showUI(subject);

            while(!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
            }
         } catch (Exception e){
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

     private void configSendObserver(PrintWriter printWriter) {
        SendMessageAction sendMessageAction = null;
        try {
            sendMessageAction = new SendMessageAction(printWriter);
            Observer observer = new MessageSendObserver(sendMessageAction);
            subject.register(EventType.SEND, observer);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
     }
 }
 