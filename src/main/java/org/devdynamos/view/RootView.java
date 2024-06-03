package org.devdynamos.view;

import org.devdynamos.utils.NavPath;

import javax.swing.*;
import java.awt.*;

public class RootView {

    private JPanel pnlRoot;
    private JButton button1;
    CardLayout cl = (CardLayout)(this.pnlRoot.getLayout());

    public RootView() {

    }

    public void show() {
        JFrame frame = new JFrame("ShipShape Manager");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel homePanel = new HomeView(this).getRootPanel();
        JPanel allocateEmployeePanel = new EmployeesView(this).getRootPanel();

        this.pnlRoot.add(homePanel, String.valueOf(NavPath.HOME));
        this.pnlRoot.add(allocateEmployeePanel, String.valueOf(NavPath.EMPLOYEES));

        this.cl.show(this.pnlRoot, String.valueOf(NavPath.HOME));

        frame.setContentPane(this.pnlRoot);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void navigate(NavPath to){
        this.cl.show(this.pnlRoot, String.valueOf(to));
    }
}
