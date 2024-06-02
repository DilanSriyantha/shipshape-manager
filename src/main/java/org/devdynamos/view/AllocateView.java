package org.devdynamos.view;

import org.devdynamos.contollers.AllocateController;
import org.devdynamos.models.Employee;
import org.devdynamos.models.EmployeeTableModel;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.CustomBooleanCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private AllocateController allocateController = new AllocateController();
    private EmployeeTableModel employeeTableModel;
    private List<Employee> employeesList;

    // Save newly allocated objects temporarily
    private ArrayList<Object[]> tmpAllocateList = new ArrayList<Object[]>();

    public AllocateView() {

        loadEmployees();
        renderTable();
        setAllocateBtn();

        btnAllocate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int selectedRowIndex = tblEmp.getSelectedRow();

                employeeTableModel.setAllocated(
                        selectedRowIndex,
                        !employeeTableModel.getEmployeeAt(selectedRowIndex).isAllocated()
                );
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
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

    private void loadEmployees() {
        this.employeesList = this.allocateController.getEmployeesList();
    }

    private void renderTable() {
        this.employeeTableModel = new EmployeeTableModel(this.employeesList);
        this.tblEmp.setModel(this.employeeTableModel);

        // setup row sorter
        TableRowSorter<EmployeeTableModel> sorter = new TableRowSorter<>(this.employeeTableModel);
        this.tblEmp.setRowSorter(sorter);

        // custom cell renderer for specific cells
        this.tblEmp.getColumnModel().getColumn(5).setCellRenderer(new CustomBooleanCellRenderer("Allocated"));
        this.tblEmp.getColumnModel().getColumn(3).setCellRenderer(new CustomBooleanCellRenderer("Available"));

        this.tblEmp.setFocusable(false);
        this.tblEmp.getTableHeader().setReorderingAllowed(false);
        this.tblEmp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        TableRowSorter<?> sorter = (TableRowSorter<?>) this.tblEmp.getRowSorter();

        System.out.println(key);

        RowFilter<Object, Object> idFilter = RowFilter.regexFilter("(?i)" + key, 0);
        RowFilter<Object, Object> nameFilter = RowFilter.regexFilter("(?i)" + key, 1);
        RowFilter<Object, Object> allocatedFilter = RowFilter.regexFilter("(?i)true", 5);

        if (checkBoxAllocateEmpSelection){
            if(!key.isEmpty()){
                sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.orFilter(Arrays.asList(idFilter, nameFilter)), allocatedFilter)));
            }else{
                sorter.setRowFilter(allocatedFilter); // filter allocated=true rows only
            }
        }else{
            if(!key.isEmpty()){
                sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(idFilter, nameFilter)));
            }else{
                sorter.setRowFilter(null);
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