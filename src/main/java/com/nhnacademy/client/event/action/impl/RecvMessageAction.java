package com.nhnacademy.client.event.action.impl;

import com.nhnacademy.client.event.action.MessageAction;
import com.nhnacademy.client.ui.form.MessageClientForm;

public class RecvMessageAction implements MessageAction{
    private final MessageClientForm messageClientForm;

    public RecvMessageAction(MessageClientForm messageClientForm) {
        this.messageClientForm = messageClientForm;
    }

    @Override
    public void execute(String message) {
        messageClientForm.getMessageArea().append(message);
        messageClientForm.getMessageArea().append(System.lineSeparator());
    }
}
