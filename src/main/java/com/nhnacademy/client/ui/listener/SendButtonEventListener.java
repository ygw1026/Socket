package com.nhnacademy.client.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.nhnacademy.client.ui.form.MessageClientForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendButtonEventListener implements ActionListener {
    private final MessageClientForm messageClientForm;

    public SendButtonEventListener (MessageClientForm messageClientForm) {
        this.messageClientForm = messageClientForm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        log.debug("button actionCommand:{}", e.getActionCommand());
        messageClientForm.getSubject().sendMessage(messageClientForm.getInputField().getText());
        messageClientForm.getInputField().setText("");
    }
}
