package org.devdynamos.view;

import org.devdynamos.Utilities.AssetsManager;
import org.devdynamos.Utilities.JobData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AllocateView {
    private JPanel pnlRoot;
    private JTable tblEmp;
    private JButton btnCancel;
    private JTextField txtSearchEmp;
    private JButton btnSearchEmp;
    private JButton btnSave;
    private JLabel lblAllocateEmp;
    private JLabel lblTotalEmp;
    private JPanel pnlAllocate;
    private JButton btnAllocate;

    public AllocateView() {
        TableData tableData = new TableData();
        setAllocateBtn();

        btnAllocate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tblEmp.getSelectedRow();
                if (tableData.model.getValueAt(row, 4) != "Allocated") {
                    tableData.model.setValueAt("Allocated", row, 4);
                    tableData.model.setValueAt("Unavailable", row, 3);
                } else {
                    tableData.model.setValueAt("Not Allocated", row, 4);
                    tableData.model.setValueAt("Available", row, 3);
                }
                behaveAllocateBtn();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private class TableData {
        JobData jobData = new JobData();
        Object[][] data = jobData.getData();

        String[] title = {"ID", "Employee", "Job Role", "Availability", "Allocate"};

        DefaultTableModel model = new DefaultTableModel(data, title) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        TableData() {
            tblEmp.setModel(model);
            tblEmp.setFocusable(false);
            tblEmp.getTableHeader().setReorderingAllowed(false);
            tblEmp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }
    }

    private void setAllocateBtn() {
        btnAllocate.setIcon(AssetsManager.getImageIcon("AllocateIcon"));
        btnAllocate.setEnabled(false);
        tblEmp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                btnAllocate.setEnabled(true);
                behaveAllocateBtn();
            }
        });
    }

    private void behaveAllocateBtn() {
        if (tblEmp.getValueAt(tblEmp.getSelectedRow(), 4) != "Allocated") {
            btnAllocate.setText("Allocate");
            btnAllocate.setIcon(AssetsManager.getImageIcon("AllocateIcon"));
        } else {
            btnAllocate.setText("Deallocate");
            btnAllocate.setIcon(AssetsManager.getImageIcon("DeallocateIcon"));
        }
    }

    public void show() {
        JFrame frame = new JFrame("AllocateView");
        frame.setContentPane(new AllocateView().pnlRoot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}