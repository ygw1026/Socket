package com.nhnacademy.server.thread.pool;

import java.util.Objects;

import com.nhnacademy.server.thread.channel.Executable;
import com.nhnacademy.server.thread.channel.RequestChannel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestHandler implements Runnable{

    public RequestHandler (RequestChannel requestChannel) {
        if (Objects.isNull(requestChannel)) {
            throw new IllegalArgumentException();
        }

        this.requestChannel = requestChannel;
    }

    private final RequestChannel requestChannel;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Executable executable = requestChannel.getJob();
                executable.execute();
            }catch (Exception e) {
                if(e instanceof InterruptedException) {
                    log.debug("thread 종료");
                    Thread.currentThread().interrupt();
                }
                log.error("thread-exception : {}", e.getMessage(), e);
            }
        }
    }
}
