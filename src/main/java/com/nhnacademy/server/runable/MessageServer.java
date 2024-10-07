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

package com.nhnacademy.server.runable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageServer implements Runnable {

    private static final int DEFAULT_PORT=8888;
    private final int port;
    private final ServerSocket serverSocket;

    public MessageServer(){
        this(DEFAULT_PORT);
    }

    public MessageServer(int port) {
        if (port <= 0) {
            throw new IllegalArgumentException(String.format("port : %d", port));
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
        while(!Thread.currentThread().isInterrupted()) {
            try (Socket client = serverSocket.accept();
                 BufferedReader clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
                 PrintWriter out = new PrintWriter(client.getOutputStream(), false);
                ){
                InetAddress inetAddress = client.getInetAddress();
                log.debug("ip:{}, port:{}", inetAddress.getAddress(), client.getPort());

                String recvMessage = null;

                while ((recvMessage = clientIn.readLine()) != null) {
                    System.out.println("[server]recv-message:" + recvMessage);

                    out.println(recvMessage);
                    out.flush();
                }
            } catch (Exception e) {
                log.debug("{}", e.getMessage(), e);
                if(e instanceof InterruptedIOException) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}