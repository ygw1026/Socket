package com.nhnacademy.client.ui.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.nhnacademy.client.ui.form.MessageClientForm;

public class InputFieldKeyListener implements KeyListener{
    private final MessageClientForm messageClientForm;

    public InputFieldKeyListener(MessageClientForm messageClientForm) {
        this.messageClientForm = messageClientForm;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            messageClientForm.getSubject().sendMessage(messageClientForm.getInputField().getText());
            messageClientForm.getInputField().setText("");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
