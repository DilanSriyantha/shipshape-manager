package org.devdynamos.view;

import org.devdynamos.contollers.EmployeesController;
import org.devdynamos.models.Employee;
import org.devdynamos.utils.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmployeesView {
    private JPanel pnlRoot;
    private JPanel pnlAllocate;

    private JTable tblEmp;

    private JButton btnBack;
    private JButton btnAllocate;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnInsert;
    private JButton btnSearchEmp;

    private JTextField txtSearchEmp;

    private JLabel lblAllocateEmp;
    private JLabel lblTotalEmp;

    private JCheckBox checkBoxFilterAllocatedEmp;

    private EmployeesController allocateController = new EmployeesController();
    private EmployeeTableModel employeeTableModel;
    private RootView rootView;

    private List<Employee> employeesList;

    // Save newly allocated objects temporarily
    private ArrayList<Object[]> tmpAllocateList = new ArrayList<Object[]>();

    public EmployeesView(RootView rootView) {
        this.rootView = rootView;

        loadEmployees();
        renderTable();
        initButtons();

        checkBoxFilterAllocatedEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = txtSearchEmp.getText().toLowerCase();
                filterTableData(key, checkBoxFilterAllocatedEmp.isSelected());
            }
        });
    }

    public JPanel getRootPanel() {
        return this.pnlRoot;
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
        this.tblEmp.getColumnModel().getColumn(7).setCellRenderer(new CustomBooleanCellRenderer("Allocated"));
        this.tblEmp.getColumnModel().getColumn(5).setCellRenderer(new CustomBooleanCellRenderer("Available"));

        this.tblEmp.setFocusable(false);
        this.tblEmp.getTableHeader().setReorderingAllowed(false);
        this.tblEmp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        checkBoxFilterAllocatedEmp.setSelected(false);
    }

    private void initButtons() {
        btnAllocate.setIcon(AssetsManager.getImageIcon("PersonAddIcon"));
        btnAllocate.setEnabled(false);

        tblEmp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rowIndex = tblEmp.getSelectedRow();
                if(rowIndex > -1){
                    behaveAllocateBtn(rowIndex);
                    behaveUpdateBtn(rowIndex);
                    behaveDeleteBtn(rowIndex);
                }
            }
        });

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

        btnBack.setIcon(AssetsManager.getImageIcon("BackIcon"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.goBack();
            }
        });

        btnSearchEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = txtSearchEmp.getText().toLowerCase();
                filterTableData(key, checkBoxFilterAllocatedEmp.isSelected());
            }
        });

        txtSearchEmp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    btnSearchEmp.doClick();
                }
            }
        });

        btnInsert.setIcon(AssetsManager.getImageIcon("AddIcon"));
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertEmployeeDialog insertEmployeeDialog = new InsertEmployeeDialog();
                insertEmployeeDialog.showDialog();

                loadEmployees();
                renderTable();
            }
        });

        btnUpdate.setIcon(AssetsManager.getImageIcon("UpdateIcon"));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdate();
            }
        });

        btnDelete.setIcon(AssetsManager.getImageIcon("DeleteIcon"));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDelete();
            }
        });
    }

    private void handleUpdate() {
        int viewSelectedRowIndex = tblEmp.getSelectedRow();
        int modelSelectedRowIndex = tblEmp.convertRowIndexToModel(viewSelectedRowIndex);
        Employee employee = employeeTableModel.getEmployeeAt(modelSelectedRowIndex);

        InsertEmployeeDialog updateEmployeeDialog = new InsertEmployeeDialog();
        updateEmployeeDialog.showDialog(employee);

        loadEmployees();
        renderTable();
    }

    private void handleDelete() {
        int viewSelectedRowIndex = tblEmp.getSelectedRow();
        int modelSelectedRowIndex = tblEmp.convertRowIndexToModel(viewSelectedRowIndex);
        Employee employee = employeeTableModel.getEmployeeAt(modelSelectedRowIndex);

        int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure want to delete " + employee.getEmpName() + " from the system?", "Are you sure?", JOptionPane.YES_NO_OPTION);
        if(confirmed == 0) employee.delete();

        loadEmployees();
        renderTable();
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

    private void behaveUpdateBtn(int row){
        btnUpdate.setEnabled(row >= 0);
    }

    private void behaveDeleteBtn(int row){
        btnDelete.setEnabled(row >= 0);
    }

    private void filterTableData(String key, boolean checkBoxAllocateEmpSelection) {
        TableRowSorter<?> sorter = (TableRowSorter<?>) this.tblEmp.getRowSorter();

        System.out.println(key);

        RowFilter<Object, Object> idFilter = RowFilter.regexFilter("(?i)" + key, 0);
        RowFilter<Object, Object> nameFilter = RowFilter.regexFilter("(?i)" + key, 1);
        RowFilter<Object, Object> allocatedFilter = RowFilter.regexFilter("(?i)true", 7);

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
        frame.setContentPane(this.pnlRoot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}