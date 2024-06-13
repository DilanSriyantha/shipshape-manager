package org.devdynamos.view;

import javax.swing.*;
import java.awt.event.*;

public class LoadingSpinner extends JDialog {
    private JPanel contentPane;
    private JLabel lblStatus;
    private Thread loadingThread;

    public LoadingSpinner() {
        setContentPane(contentPane);
        setModal(true);
    }

    private void start() {
        this.setUndecorated(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void start(String status){
        lblStatus.setText(status);

        loadingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });

        loadingThread.start();
    }

    public void setStatus(String status){
        lblStatus.setText(status);
    }

    public void stop() {
        loadingThread.interrupt();
        this.dispose();
    }
}
