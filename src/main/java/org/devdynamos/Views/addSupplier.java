package org.devdynamos.Views;

import javax.swing.*;
import java.awt.*;

public class addSupplier {
    private JPanel addSupplierPanel;
    private JTextField txtSupplierName;
    private JTextField txtSupplierUID;
    private JTextField txtPartName;
    private JTextField txtSupplierEmail;
    private JTextField txtSupplierPhone;
    private JTextField txtSupplierAddress;
    private JButton addSupplierSubmit;
    private JButton addSupplierReset;

    public void showAddSupplier() {
        JFrame frame = new JFrame("addSupplier");
        frame.setMinimumSize(new Dimension(300, 400));
        frame.setContentPane(new addSupplier().addSupplierPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}


