package com.nhnacademy.server.thread.channel;

import java.util.LinkedList;
import java.util.Queue;

public class RequestChannel {
    
    private final Queue<Executable> requestQueue;
    private static long QUEUE_MAX_SIZE = 10;

    public RequestChannel() {
        this.requestQueue = new LinkedList<>();
    }

    public synchronized void addJob(Executable executable) {
        while(requestQueue.size() >= QUEUE_MAX_SIZE) {
            try {
                wait();
            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        requestQueue.add(executable);
        notify();
    }

    public synchronized Executable getJob() {
        while(requestQueue.isEmpty()) {
            try {
                wait();
            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        notify();
        return requestQueue.poll();
    }
}
