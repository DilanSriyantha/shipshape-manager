package org.devdynamos.view;

import org.devdynamos.Utilities.AssetsManager;
import org.devdynamos.Utilities.JobData;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private JButton btnViewAllocatedList;

    // Save newly allocated objects temporarily
    private ArrayList<Object> tmpAllocateList = new ArrayList<Object>();

    public AllocateView() {
        TableData tableData = new TableData();
        setAllocateBtn();

        btnAllocate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get selected row when clicking Allocate button
                int row = tblEmp.getSelectedRow();

                // Change cell text when allocating
                if (tableData.model.getValueAt(row, 4) != "Allocated") {
                    tableData.model.setValueAt("Allocated", row, 4);
                    tableData.model.setValueAt("Unavailable", row, 3);

                    // Save newly allocated objects
                    for(int i = 0; i < tableData.data.length; i++) {
                        if (tableData.data[i][0] == tableData.model.getValueAt(row, 0)) {
                            tmpAllocateList.add(tableData.data[i]);
                            break;
                        }
                    }
                }

                // Change Allocate button text and style after clicking
                behaveAllocateBtn();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the program when clicking Cancel button
                System.exit(0);
            }
        });
        btnSearchEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = txtSearchEmp.getText();
                System.out.println(key);
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

        // Changes Allocate button when selecting a row
        tblEmp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                behaveAllocateBtn();
            }
        });
    }

    private void behaveAllocateBtn() {
        if (tblEmp.getValueAt(tblEmp.getSelectedRow(), 4) != "Allocated") {
            btnAllocate.setText("Allocate");
            btnAllocate.setEnabled(true);
        } else {
            btnAllocate.setText("Allocated");
            btnAllocate.setEnabled(false);
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