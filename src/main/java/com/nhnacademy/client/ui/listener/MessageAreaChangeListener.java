package com.nhnacademy.client.ui.listener;

import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.nhnacademy.client.ui.form.MessageClientForm;

public class MessageAreaChangeListener implements DocumentListener{
    private final MessageClientForm messageClientForm;

    public MessageAreaChangeListener (MessageClientForm messageClientForm) {
        this.messageClientForm = messageClientForm;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        handleTextChange();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        handleTextChange();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        handleTextChange();
    }

    private void handleTextChange() {
        JScrollBar verticalScrollBar = messageClientForm.getScrollPane().getVerticalScrollBar();
        SwingUtilities.invokeLater(() -> verticalScrollBar.setValue(verticalScrollBar.getMaximum())); 
    }
}
