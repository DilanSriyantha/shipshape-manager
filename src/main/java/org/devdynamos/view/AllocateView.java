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
    private JCheckBox checkBoxFilterAllocatedEmp;

    JobData jobData = new JobData();
    private Object[][] data;
    private DefaultTableModel model;

    // Save newly allocated objects temporarily
    private ArrayList<Object[]> tmpAllocateList = new ArrayList<Object[]>();

    public AllocateView() {
        String[] title = {"ID", "Employee", "Job Role", "Availability", "Allocate"};
        data = jobData.getData();
        model = new DefaultTableModel(data, title) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableRender();
        setAllocateBtn();

        btnAllocate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tblEmp.getSelectedRow();

                if (!model.getValueAt(row, 4).equals("Allocated")) {
                    model.setValueAt("Allocated", row, 4);
                    model.setValueAt("Unavailable", row, 3);

                    for (Object[] rowData : data) {
                        if (rowData[0].equals(model.getValueAt(row, 0))) {
                            rowData[3] = "Unavailable";
                            rowData[4] = "Allocated";
                            tmpAllocateList.add(rowData);
                            break;
                        }
                    }
                    lblTotalEmp.setText(tmpAllocateList.size() + " Selected");
                }
                behaveAllocateBtn(row);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnSearchEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = txtSearchEmp.getText().toLowerCase();
                filterTableData(key, checkBoxFilterAllocatedEmp.isSelected());
            }
        });

        checkBoxFilterAllocatedEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = txtSearchEmp.getText().toLowerCase();
                filterTableData(key, checkBoxFilterAllocatedEmp.isSelected());
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = "";
                for (Object[] rowData : tmpAllocateList) {
                    str += rowData[0] + "\n";
                }
                JOptionPane.showMessageDialog(pnlRoot, str);
            }
        });
    }

    private void tableRender() {
        tblEmp.setModel(model);
        tblEmp.setFocusable(false);
        tblEmp.getTableHeader().setReorderingAllowed(false);
        tblEmp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void setAllocateBtn() {
        btnAllocate.setIcon(AssetsManager.getImageIcon("AllocateIcon"));
        btnAllocate.setEnabled(false);

        tblEmp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = tblEmp.getSelectedRow();
                if (row != -1) {
                    behaveAllocateBtn(row);
                }
            }
        });
    }

    private void behaveAllocateBtn(int row) {
        if (!tblEmp.getValueAt(row, 4).equals("Allocated")) {
            btnAllocate.setText("Allocate");
            btnAllocate.setEnabled(true);
        } else {
            btnAllocate.setText("Allocated");
            btnAllocate.setEnabled(false);
        }
    }

    private void filterTableData(String key, boolean checkBoxAllocateEmpSelection) {
        model.setRowCount(0);

        for (Object[] rowData : data) {
            String tempID = ((String) rowData[0]).toLowerCase();
            String tempName = ((String) rowData[1]).toLowerCase();
            String tempJobRole = ((String) rowData[2]).toLowerCase();
            boolean matchesSearch = tempID.contains(key) || tempName.contains(key) || tempJobRole.contains(key);

            if (matchesSearch && (!checkBoxAllocateEmpSelection || rowData[4].equals("Allocated"))) {
                model.addRow(rowData);
            }
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