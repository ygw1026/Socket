package com.nhnacademy.server.thread.pool;

import java.util.Objects;

public class WorkerThreadPool {
    
    private final int poolSize;
    private final static int DEFAULT_POOL_SIZE=5;

    private final Thread[] workerThreads;
    private final Runnable runnable;

    public WorkerThreadPool(Runnable runnable){
        this(DEFAULT_POOL_SIZE, runnable);
    }

    public WorkerThreadPool(int poolSize, Runnable runnable) {
        if(poolSize < 1 || Objects.isNull(runnable)) {
            throw new IllegalArgumentException("poolSize > 0");
        }

        this.poolSize = poolSize;
        this.runnable = runnable;

        workerThreads = new Thread[poolSize];
        initializePool();
    }

    public void initializePool() {
        for (int i = 0; i < poolSize; i++) {
            workerThreads[i] = new Thread(runnable);
        }
    }

    public synchronized void start() {
        for (Thread thread : workerThreads) {
            thread.start();
        }
    }

    public synchronized void stop() {
        for(Thread thread : workerThreads) {
            thread.interrupt();
        }

        for(Thread thread : workerThreads) {
            thread.interrupt();
        }

        for (Thread thread : workerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
