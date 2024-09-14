/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.client.runable;

import com.nhnacademy.server.runable.MessageServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

@Slf4j
class MessageClientTest {

    static Thread serverThread;
    static MessageServer messageServer;

    @BeforeAll
    static void beforeAllSetUp(){
        messageServer = new MessageServer();
        serverThread = new Thread(messageServer);
        serverThread.start();

    }

    @Test
    void constructorTest(){
        Assertions.assertAll(
                //TODO#2-8 serverAddress is null or serverPort <=0 IllegalArgumentException 발생하는지 검증 합니다.
                ()->{
                    Assertions.assertThrows(IllegalArgumentException.class,()->{
                        new MessageClient(null,8888);
                    });
                },
                ()->{
                    Assertions.assertThrows(IllegalArgumentException.class,()->{
                        new MessageClient("localhost",-8888);
                    });
                }
        );
    }

    @Test
    @DisplayName("instance of runnable")
    void instanceOfRunable(){
        //TODO#2-9 MessageClient의 instance가 runnable을 구현했는지 검증 합니다.
        Assertions.assertInstanceOf(Runnable.class, new MessageClient());
    }

    @Test
    void echoMessageTest() throws Exception {

        //System.in <-- 즉 사용자가의 입력을 hello로 서버로 전송하기 위해서 아래와 같이 설정 합니다.
        InputStream originalSystemIn = System.in;
        String messageInput = String.format("hello%s",System.lineSeparator());
        ByteArrayInputStream testIn = new ByteArrayInputStream(messageInput.getBytes());
        System.setIn(testIn);

        //System.out 캡쳐하기 위해서 아래와 같이 설정 합니다.
        PrintStream originalSystemOut = System.out;
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(testOut);
        System.setOut(printStream);

        //TODO#2-10 MessageClient 객체를 생성하고 시작 합니다.
        MessageClient messageClient = new MessageClient();
        Thread clientThread = new Thread(messageClient);
        clientThread.start();

        //2초 sleep
        Thread.sleep(2000);

        //TODO#2-11 System in/out 원래대로 복원 합니다.
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);

        log.debug("print-message:{}",testOut.toString());

        //TODO#2-12 client에서 "[clinet]recv-message:hello" 출력되었는지 ByteArrayOutputStream testOut을 이용하여 검증 합니다.
        Assertions.assertTrue(testOut.toString().contains("[clinet]recv-message:hello"));

    }

    @AfterAll
    static void tearDown(){
        serverThread.interrupt();
    }
}