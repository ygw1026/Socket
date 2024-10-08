package com.nhnacademy.client.ui.form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.nhnacademy.client.event.action.MessageAction;
import com.nhnacademy.client.event.action.impl.RecvMessageAction;
import com.nhnacademy.client.event.observer.Observer;
import com.nhnacademy.client.event.observer.impl.MessageRecvObserver;
import com.nhnacademy.client.event.subject.EventType;
import com.nhnacademy.client.event.subject.Subject;
import com.nhnacademy.client.ui.listener.InputFieldKeyListener;
import com.nhnacademy.client.ui.listener.MessageAreaChangeListener;
import com.nhnacademy.client.ui.listener.SendButtonEventListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageClientForm {
    private JPanel panel;
    private JFrame frame;
    private JTextArea messageArea;
    private JTextField inputField;
    private JButton sendButton;
    private JScrollPane scrollPane;

    private final Subject subject;

    public MessageClientForm(Subject subject) {
        this.subject = subject;
        frame = new JFrame();
        panel = new JPanel();
        messageArea = new JTextArea();
        inputField = new JTextField();
        sendButton = new JButton();
        scrollPane = new JScrollPane(messageArea);

        initializeUi();
        configureEvent();
        configureRecvObserver();
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }

    public JTextField getInputField() {
        return inputField;
    }

    public JButton getSendbutton() {
        return sendButton;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public Subject getSubject() {
        return subject;
    }

    private void initializeUi() {
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setRows(50);
        messageArea.setColumns(100);

        inputField.setColumns(100);

        sendButton.setText("Send");

        panel.setLayout(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.setTitle("Message Client");
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();

        frame.setLocation((screenSize.width - frameSize.width) / 2,  (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }

    private void configureEvent() {
        sendButton.addActionListener(new SendButtonEventListener(this));
        inputField.addKeyListener(new InputFieldKeyListener(this));
        messageArea.getDocument().addDocumentListener(new MessageAreaChangeListener(this));
    }

    private void configureRecvObserver() {
        MessageAction messageAction = new RecvMessageAction(this);
        Observer observer = new MessageRecvObserver(messageAction);
        subject.register(EventType.RECV, observer);
    }

    public static void showUI(Subject subject) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MessageClientForm(subject);
            }
        });
    }
}
