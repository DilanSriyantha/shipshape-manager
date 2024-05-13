package org.devdynamos.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AllocateView {
    private JPanel panel1;
    private JTable tblEmp;
    private JButton btnCancel;
    private JTextField txtSearchEmp;
    private JButton btnSearchEmp;
    private JButton btnSave;
    private JLabel lblAllocateEmp;
    private JLabel lblTotalEmp;

    private void setTableData() {
        String[] title = {"ID", "Employee", "Job Role", "Availability", "Allocate"};
        Object[][] data = {
                {"E280", "Lorem Ipsum", "Mechanic", "available", true},
                {"E280", "Lorem Ipsum", "Mechanic", "available", false},
                {"E280", "Lorem Ipsum", "Mechanic", "available", false},
                {"E280", "Lorem Ipsum", "Mechanic", "available", false},
                {"E280", "Lorem Ipsum", "Mechanic", "available", true},
                {"E280", "Lorem Ipsum", "Mechanic", "available", false},
                {"E280", "Lorem Ipsum", "Mechanic", "available", false},
                {"E280", "Lorem Ipsum", "Mechanic", "available", true},
                {"E280", "Lorem Ipsum", "Mechanic", "available", false},
                {"E280", "Lorem Ipsum", "Mechanic", "available", false}
        };
        DefaultTableModel model = new DefaultTableModel(data, title) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblEmp.setModel(model);
        tblEmp.setFocusable(false);
        tblEmp.getTableHeader().setReorderingAllowed(false);
    }

    public void show() {
        AllocateView allocateView = new AllocateView();
        allocateView.setTableData();

        JFrame frame = new JFrame("AllocateView");
        frame.setContentPane(allocateView.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
