package com.nhnacademy.client.event.action.impl;

import java.io.PrintWriter;

import com.nhnacademy.client.event.action.MessageAction;

public class SendMessageAction implements MessageAction{
    public SendMessageAction(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    private final PrintWriter printWriter;

    @Override
    public void execute(String message) {
        printWriter.println(message);
    }
}
