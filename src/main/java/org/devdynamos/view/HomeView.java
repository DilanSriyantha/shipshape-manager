package org.devdynamos.view;

import org.devdynamos.utils.NavPath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView {
    private JPanel pnlRoot;
    private JLabel lblTitle;
    private JButton goButton;
    private JButton btnEmployees;
    private RootView rootView;

    public HomeView(RootView rootView){
        this.rootView = rootView;

        btnEmployees.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.navigate(NavPath.EMPLOYEES);
            }
        });
    }

    public void show() {
        JFrame frame = new JFrame("HomeView");
        frame.setContentPane(this.pnlRoot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getRootPanel() {
        return this.pnlRoot;
    }
}
