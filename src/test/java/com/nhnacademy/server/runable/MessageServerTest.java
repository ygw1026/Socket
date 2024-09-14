package com.nhnacademy.server.runable;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageServerTest {

    static MessageServer messageServer;
    static Thread thread;

    @BeforeAll
    static void beforeAllSetup(){
        messageServer = new MessageServer(8888);
        thread = new Thread(messageServer);
        thread.start();
    }

    @Test
    @Order(1)
    @DisplayName("constructor : port <= 0")
    void constructorTest1(){
        //TODO#1-11 portt < 0  검증하는 코드를 작성하세요
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            MessageServer messageServer = new MessageServer(-100);
        });
    }

    @Test
    @Order(2)
    @DisplayName("aready used port")
    void constructorTest2(){
        //TODO#1-12 이미 서버는 8888 port를 사용하고 있을 때 8888포트로 서버를 시작한다면 RuntimeException 발생하는지 검증 합니다.
        Assertions.assertThrows(RuntimeException.class,()->{
            MessageServer messageServer2 = new MessageServer(8888);
            new Thread(messageServer2).start();
        });
    }

    @Test
    @Order(3)
    @DisplayName("echo message")
    void echoMessageTest() throws IOException {
        /*TODO#1-13 8888 port를 사용하고 있는 echo 서버에 접근하기 위헤서 client socket을 생성 합니다.
            - host : localhost
            - port : 8888
         */
        Socket client = new Socket("localhost", 8888);

        /*TODO#1-14 간단한 test client 구현
          - cleint 가 server에 메시지를 전송하기 위해서 PrintWriter 객체를 사용할 수 있도록 초기화 합니다.
          - server가 전송하는 데이터를 받기 위해서 BufferedReader 객체를 초기화 합니다.
         */

        try(PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ){
            Assertions.assertAll(
                    ()->{
                        String message = "hello";
                        out.println(message);
                        String actual = in.readLine();
                        log.debug("actual:{}",actual);
                        Assertions.assertEquals(message,actual);
                    },
                    ()->{
                        // TODO#1-15  client가 server로 'java' message를  전송할 때 server가 client가 전송한 message를 응답하는지 검증하는 코드를 작성하세요
                        String message = "java";
                        out.println(message);
                        String actual = in.readLine();
                        log.debug("actual:{}",actual);
                        Assertions.assertEquals(message,actual);

                    },
                    ()->{
                        // TODO#1-16 '엔에이치엔아카데미' 검증하는 코드를 작성 하세요

                        String message = "엔에이치엔아카데미";
                        out.println(message);
                        String actual = in.readLine();
                        log.debug("actual:{}",actual);
                        Assertions.assertEquals(message,actual);
                    }
            );
        }
    }

    @AfterAll
    static void tearDown(){
        // TODO#1-17 모든 테스트가 종료되면 server를 구동하고있는 thread에 interrupt()를 발생시켜 종료 합니다.
        thread.interrupt();
    }

}