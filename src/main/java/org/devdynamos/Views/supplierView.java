package org.devdynamos.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.devdynamos.Views.addSupplier;

public class supplierView {
    private JPanel supplierPanel;
    private JLabel pageTitle;
    private JButton addSupplierButton;
    private JButton updateSupplierButton;
    private JButton deleteSupplierButton;


    public supplierView() {
        addSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSupplier a1 = new addSupplier();
                a1.showAddSupplier();
            }
        });
    }

    public void showPanel() {
        JFrame frame = new JFrame("supplierView");
        frame.setMinimumSize(new Dimension(600, 300));
        frame.setContentPane(new supplierView().supplierPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }




}
