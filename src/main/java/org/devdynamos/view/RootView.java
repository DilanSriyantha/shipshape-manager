package org.devdynamos.view;

import org.devdynamos.interfaces.DBConnectionListener;
import org.devdynamos.models.User;
import org.devdynamos.utils.*;

import javax.swing.*;
import java.awt.*;

public class RootView {

    private JFrame frame;
    private JPanel pnlRoot;
    private JButton button1;
    private User user;
    CardLayout cl = (CardLayout)(this.pnlRoot.getLayout());

    NavPath ACTIVE_PATH = NavPath.NONE;

    public RootView() {
        checkConnection();
    }

    public void show() {
        frame = new JFrame("ShipShape Manager");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel loginPanel = new Login(this).getRootPanel();

        this.pnlRoot.add(loginPanel, String.valueOf(NavPath.LOGIN));

        this.cl.show(this.pnlRoot, String.valueOf(NavPath.LOGIN));

        frame.setContentPane(this.pnlRoot);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setSize(Dimension dimension){
        if(frame == null) return;

        frame.setPreferredSize(dimension);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
    }

    public void setResizable(boolean status){
        if(frame == null) return;

        frame.setResizable(status);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
    }

    public void navigate(NavPath to){
        this.cl.show(this.pnlRoot, String.valueOf(to));
    }

    public void navigate(NavPath to, JPanel pnl){
        this.pnlRoot.add(pnl, String.valueOf(to));
        this.cl.show(this.pnlRoot, String.valueOf(to));
    }

    public void goBack(){
        this.cl.previous(this.pnlRoot);
        destroyLastPath();
    }

    public void destroyLastPath(){
        pnlRoot.remove(pnlRoot.getComponent(pnlRoot.getComponents().length - 1));
    }

    private void checkConnection() {
        tryToReconnectDB();
    }

    public User getCurrentUser() {
        return user;
    }

    public void setCurrentUser(User user){
        this.user = user;
    }

    private void tryToReconnectDB() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(DBManager.getConnection() != null) continue;
                    DBManager.establishConnection("localhost", 4000, "shipshape", "root", "");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Console.log("Checking connection");
                }
            }
        });
        t.start();
    }
}
