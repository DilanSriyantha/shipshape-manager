package org.devdynamos.view;

import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.InputValidator;
import org.devdynamos.utils.NotificationSender;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Mail {
    private JLabel lblTotalEmp;
    private JButton btnBack;
    private JButton btnSend;
    private JTextArea txtBody;
    private JTextField txtEmail;
    private JTextField txtSubject;
    private JPanel pnlRoot;

    private final RootView rootView;

    public Mail(RootView rootView){
        this.rootView = rootView;

        initButtons();
    }

    public JPanel getRootPanel() {
        return pnlRoot;
    }

    private void initButtons() {
        btnBack.setIcon(AssetsManager.getImageIcon("BackIcon"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.goBack();
            }
        });

        btnSend.setIcon(AssetsManager.getImageIcon("SendIcon"));
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAnEmail();
            }
        });
    }

    private void sendAnEmail() {
        if(txtEmail.getText().isEmpty() || txtSubject.getText().isEmpty() || txtBody.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Please provide the required information to send an email.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!InputValidator.isValidEmail(txtEmail.getText())){
            JOptionPane.showMessageDialog(null, "Invalid email address", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Please wait...");

        Thread sendEmail = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    NotificationSender.sendEmail(txtEmail.getText(), txtSubject.getText(), txtBody.getText(), NotificationSender.TEXT);

                    loadingSpinner.stop();
                    clearInputs();
                } catch (Exception e) {
                    loadingSpinner.stop();
                    JOptionPane.showMessageDialog(null, "Error occurred", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    sendEmail.join();

                    loadingSpinner.stop();
                    clearInputs();
                    JOptionPane.showMessageDialog(null, "Email sent successfully", "Successful", JOptionPane.INFORMATION_MESSAGE);
                }catch (Exception ex){
                    loadingSpinner.stop();
                    JOptionPane.showMessageDialog(null, "Error occurred", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        sendEmail.start();
        waitingThread.start();
    }

    private void clearInputs() {
        txtEmail.setText("");
        txtSubject.setText("");
        txtBody.setText("");
    }
}
