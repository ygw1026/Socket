package com.nhnacademy.client.runable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;

import com.nhnacademy.client.event.subject.Subject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReceivedMessageClient implements Runnable{
    private final Socket socket;
    private final Subject subject;

    public ReceivedMessageClient(Socket socket, Subject subject) {
        if (Objects.isNull(socket) || Objects.isNull(subject)) {
            throw new IllegalArgumentException();
        }

        this.socket = socket;
        this.subject = subject;
    }

    @Override
    public void run() {
        while(! Thread.currentThread().isInterrupted()) {
            try (InputStream inputStream = socket.getInputStream();
                BufferedReader clientIn = new BufferedReader(new InputStreamReader(inputStream));
            ){
                String line;

                while((line = clientIn.readLine())!=null) {
                    log.debug("recv-message:{}", line);
                    subject.receiveMessage(line);
                }
            }catch (IOException e) {
                log.error("receivedMessage Error:{}", e.getMessage(),e);
                throw new RuntimeException();
            }
        }
    }
}
