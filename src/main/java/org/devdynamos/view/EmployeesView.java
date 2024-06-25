package org.devdynamos.view;

import org.devdynamos.contollers.EmployeesController;
import org.devdynamos.customerCellRenderers.CustomBooleanCellRenderer;
import org.devdynamos.interfaces.GetRequestCallback;
import org.devdynamos.interfaces.InsertRequestCallback;
import org.devdynamos.models.Employee;
import org.devdynamos.models.GetRequestResultSet;
import org.devdynamos.tableModels.EmployeeTableModel;
import org.devdynamos.utils.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
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
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnInsert;
    private JButton btnSearchEmp;
    private JTextField txtSearchEmp;
    private JLabel lblAllocateEmp;
    private JLabel lblTotalEmp;
    private JCheckBox checkBoxFilterAllocatedEmp;

    private final RootView rootView;

    private final EmployeesController employeesController = new EmployeesController();
    private EmployeeTableModel employeeTableModel;
    private List<Employee> employeesList;

    public EmployeesView(RootView rootView) {
        this.rootView = rootView;

        loadEmployees();
        initButtons();
    }

    public JPanel getRootPanel() {
        return pnlRoot;
    }

    private void loadEmployees() {
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Loading...");

        employeesController.getEmployeesList(new GetRequestCallback<Employee>() {
            @Override
            public void onSuccess(GetRequestResultSet<Employee> resultSet) {
                employeesList = resultSet.getResultList();
                renderTable();
                loadingSpinner.stop();
            }

            @Override
            public void onFailed(Exception ex) {
                employeesList = new ArrayList<>();
                renderTable();
                loadingSpinner.stop();

                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void renderTable() {
        employeeTableModel = new EmployeeTableModel(employeesList);
        tblEmp.setModel(employeeTableModel);

        // setup row sorter
        TableRowSorter<EmployeeTableModel> sorter = new TableRowSorter<>(employeeTableModel);
        tblEmp.setRowSorter(sorter);

        // custom cell renderer for specific cells
        tblEmp.getColumnModel().getColumn(7).setCellRenderer(new CustomBooleanCellRenderer("Allocated"));
        tblEmp.getColumnModel().getColumn(5).setCellRenderer(new CustomBooleanCellRenderer("Available"));

        tblEmp.setFocusable(false);
        tblEmp.getTableHeader().setReorderingAllowed(false);
        tblEmp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        checkBoxFilterAllocatedEmp.setSelected(false);
    }

    private void initButtons() {
        tblEmp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int rowIndex = tblEmp.getSelectedRow();
                if(rowIndex > -1){
                    behaveUpdateBtn(rowIndex);
                    behaveDeleteBtn(rowIndex);
                }
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
                handleInsert();
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
                int empId = employeeTableModel.getEmployeeAt(getSelectedRowIndex()).getEmpId();
                deleteEmployee(empId);
            }
        });

        checkBoxFilterAllocatedEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String key = txtSearchEmp.getText().toLowerCase();
                filterTableData(key, checkBoxFilterAllocatedEmp.isSelected());
            }
        });
    }

    private void handleInsert() {
        InsertEmployeeDialog insertEmployeeDialog = new InsertEmployeeDialog(
                new Employee(),
                (dialog, _employee) -> {
                    insertEmployee(_employee);
                    dialog.dispose();
                },
                Window::dispose
        );
        insertEmployeeDialog.showDialog();
    }

    private void handleUpdate() {
        Employee employee = employeeTableModel.getEmployeeAt(getSelectedRowIndex());

        InsertEmployeeDialog updateEmployeeDialog = new InsertEmployeeDialog(
                employee,
                (dialog, _employee) -> {
                    updateEmployee(_employee);
                    dialog.dispose();
                },
                Window::dispose
        );
        updateEmployeeDialog.showDialog();
    }

    private void deleteEmployee(int id) {
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Deletion in progress");

        employeesController.deleteEmployee(
                id,
                new InsertRequestCallback() {
                    @Override
                    public void onSuccess() {
                        loadEmployees();
                        loadingSpinner.stop();
                        JOptionPane.showMessageDialog(null, "Employee deleted successfully.", "Successful", JOptionPane.INFORMATION_MESSAGE);
                    }

                    @Override
                    public void onFailed(Exception ex) {
                        loadingSpinner.stop();
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
    }

    private void insertEmployee(Employee employee){
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Insertion in progress...");

        employeesController.insertEmployee(
                employee,
                new InsertRequestCallback() {
                    @Override
                    public void onSuccess() {
                        loadEmployees();
                        loadingSpinner.stop();
                        JOptionPane.showMessageDialog(null, "Employee insertion successful.", "Successful", JOptionPane.INFORMATION_MESSAGE);
                    }

                    @Override
                    public void onFailed(Exception ex) {
                        loadingSpinner.stop();
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
    }

    private void updateEmployee(Employee employee){
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Insertion in progress...");

        employeesController.updateEmployee(
                employee,
                new InsertRequestCallback() {
                    @Override
                    public void onSuccess() {
                        loadEmployees();
                        loadingSpinner.stop();
                        JOptionPane.showMessageDialog(null, "Employee updated successfully.", "Successful", JOptionPane.INFORMATION_MESSAGE);
                    }

                    @Override
                    public void onFailed(Exception ex) {
                        loadingSpinner.stop();
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
    }

    private int getSelectedRowIndex() {
        if (tblEmp.getSelectedRow() == -1) return -1;

        int viewSelectedIndex = tblEmp.getSelectedRow();
        int modelSelectedIndex = tblEmp.convertRowIndexToModel(viewSelectedIndex);

        return modelSelectedIndex;
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
}