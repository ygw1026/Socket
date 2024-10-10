package com.nhnacademy.server.thread.channel;

import java.net.Socket;
import java.util.Objects;

public class Session {
    private static final ThreadLocal<Socket> currentSocketLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> currentIdLocal = new ThreadLocal<>();

    public static void initializeSocket(Socket socket) {
        currentSocketLocal.set(socket);
    }

    public static void initializeId(String id) {
        currentIdLocal.set(id);
    }

    public static void reset() {
        currentIdLocal.remove();
        currentSocketLocal.remove();
    }

    public static Socket getCurrentSocket() {
        return currentSocketLocal.get();
    }

    public static String getCurrentId() {
        return currentIdLocal.get();
    }

    public static boolean isLogin() {
        return Objects.nonNull(currentIdLocal.get());
    }
}


