package com.nhnacademy.client.runable;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

@Slf4j
public class MessageClient implements Runnable {
    //default server address
    private final static String DEFAULT_SERVER_ADDRESS = "localhost";
    //default server port
    private final static int DEFAULT_PORT = 8888;

    private final String serverAddress;
    private final int serverPort;

    private final Socket clientSocket;

    public MessageClient() {
        //TODO#2-1 기본 생성자를 초기화 합니다.
        this(DEFAULT_SERVER_ADDRESS,DEFAULT_PORT);
    }

    public MessageClient(String serverAddress, int serverPort){
        //TODO#2-2 (serverAddress null or "") or serverPort <=0   IllegalArgumentException 발생 합니다.
        if(StringUtils.isEmpty(serverAddress) || serverPort <=0 ){
            throw new IllegalArgumentException();
        }

        //TODO#2-3 serverAddress, serverProt를 초기화 합니다.
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        try {
            //TODO#2-4 (serverAddress, serverProt)를 이용해서 cleintSocket을 생성 합니다.
            clientSocket = new Socket(this.serverAddress,this.serverPort);
        } catch (IOException e) {
            log.debug("create client socket error : {}",e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        /*TODO#2-5 client -> try with resources 구문을 수정하세요.
           - 서버로 데이터를 전송하기 위해서 PrintWriter out 객체를 초기화 합니다. ( clientSocket.getOutputStream() 을 사용하세요 )
           - 서버로 부터 전송되는 데이터를 수신하기 위해서  BufferedReader clientIn 객체를 초기화 합니다. ( clientSocket.getInputStream(), InputStreamReader를 사용하세요)
           - 사용자의 입력을 standard / io를 통해서 받기  위해서 BufferedReader stdIn 를 초기화 하세요(System.in를 사용 하세요)
        */
        try(
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
                BufferedReader clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        ){

            System.out.print("send-message:");
            String userMessage;

            /*TODO#2-6 while 조건을 수정하세요
                - stdIn.readLine()  : 사용자로 부터 입력받은 값이 null 아니라면 입력받은 userMessage를 PrintWriter out을 이용해서 서버로 전송 합니다.
             */
            while ((userMessage = stdIn.readLine())!=null){
                out.println(userMessage);
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
            //TODO#2-7 client가 종료되면 cleintSocket이 null 아니라면 clientSocket의 close() method를 호출해서 정상 종료 합니다.
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
