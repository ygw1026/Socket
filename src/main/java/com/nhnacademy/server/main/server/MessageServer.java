package com.nhnacademy.server.main.server;

import com.nhnacademy.server.main.method.parser.MethodParser;
import com.nhnacademy.server.main.method.response.Response;
import com.nhnacademy.server.main.method.response.ResponseFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

@Slf4j
public class MessageServer implements Runnable {
    private static final int DEFAULT_PORT=8888;
    private final int port;
    private final ServerSocket serverSocket;

    public MessageServer(){
        this(DEFAULT_PORT);
    }

    public MessageServer(int port) {
        if(port <= 0){
            throw new IllegalArgumentException(String.format("port:%d",port));
        }

        this.port = port;

        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while(true) {
            try(Socket client = serverSocket.accept();
                BufferedReader clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(),false);
            ){
                InetAddress inetAddress = client.getInetAddress();
                log.debug("ip:{},port:{}", inetAddress.getAddress(), client.getPort());

                String recvMessage;

                while ((recvMessage = clientIn.readLine()) != null) {
                    System.out.println("recv-message: " + recvMessage);

                    MethodParser.MethodAndValue methodAndValue = MethodParser.parse(recvMessage);
                    log.debug("method:{},value:{}",methodAndValue.getMethod(),methodAndValue.getValue());
                    Response response = ResponseFactory.getResponse(methodAndValue.getMethod());
                    String sendMessage;

                    if(Objects.nonNull(response)){
                        sendMessage = response.execute(methodAndValue.getValue());
                    }else {
                        sendMessage=String.format("{%s} method not found!",methodAndValue.getMethod());
                    }
                    out.println(sendMessage);
                    out.flush();
                }
            }catch (Exception e){
                log.debug("{}",e.getMessage(),e);
            }
        }
    }//end method
}