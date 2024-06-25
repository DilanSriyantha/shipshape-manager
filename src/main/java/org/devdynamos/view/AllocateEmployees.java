package org.devdynamos.view;

import org.devdynamos.contollers.AllocateEmployeesController;
import org.devdynamos.customerCellRenderers.CustomBooleanCellRenderer;
import org.devdynamos.interfaces.GetRequestCallback;
import org.devdynamos.interfaces.InsertRequestCallback;
import org.devdynamos.listModels.RequiredSkillsListModel;
import org.devdynamos.models.Employee;
import org.devdynamos.models.GetRequestResultSet;
import org.devdynamos.models.ServiceJob;
import org.devdynamos.tableModels.AllocatedEmployeesTableModel;
import org.devdynamos.tableModels.EmployeeTableModel;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.NavPath;

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

public class AllocateEmployees {
    private JPanel pnlRoot;
    private JLabel lblTotalEmp;
    private JButton btnBack;
    private JButton btnAllocate;
    private JButton btnDeAllocate;
    private JList lstSkills;
    private JTable tblEmp;
    private JLabel lblOrder;
    private JLabel lblService;
    private JLabel lblStatus;
    private JButton btnSuggest;
    private JTable tblAllocatedEmployees;
    private JTextField txtSearch;
    private JButton btnSearch;

    private final RootView rootView;
    private final ServiceJob job;

    private final int EMPLOYEE_TABLE = 0;
    private final int ALLOCATED_TABLE = 1;

    private final AllocateEmployeesController allocateEmployeesController = new AllocateEmployeesController();
    private EmployeeTableModel employeeTableModel;
    private AllocatedEmployeesTableModel allocatedEmployeesTableModel;
    private final RequiredSkillsListModel requiredSkillsListModel = new RequiredSkillsListModel();
    private List<Employee> employeesList;
    private List<Employee> allocatedEmployeesList;

    private boolean SUGGESTION_MADE = false;

    public AllocateEmployees(RootView rootView, ServiceJob job){
        this.rootView = rootView;
        this.job = job;

        populateJobInfo();
        loadInitialData();
        initButtons();
    }

    public JPanel getRootPanel() {
        return pnlRoot;
    }

    private void loadInitialData() {
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Loading...");

        Thread loadEmployees = new Thread(new Runnable() {
            @Override
            public void run() {
                loadEmployees();
            }
        });

        Thread loadAllocatedEmployees = new Thread(new Runnable() {
            @Override
            public void run() {
                loadAllocatedEmployees();
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    loadEmployees.join();
                    loadAllocatedEmployees.join();

                    loadingSpinner.stop();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        loadEmployees.start();
        loadAllocatedEmployees.start();
        waitingThread.start();
    }

    private void loadEmployees() {
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Loading...");

        allocateEmployeesController.getEmployeesList(new GetRequestCallback<Employee>() {
            @Override
            public void onSuccess(GetRequestResultSet<Employee> resultSet) {
                employeesList = resultSet.getResultList();
                renderEmployeesTable();
                loadingSpinner.stop();
            }

            @Override
            public void onFailed(Exception ex) {
                employeesList = new ArrayList<>();
                renderEmployeesTable();
                loadingSpinner.stop();

                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void loadAllocatedEmployees() {
        allocateEmployeesController.getAllocatedEmployeesList(job, new GetRequestCallback<Employee>() {
            @Override
            public void onSuccess(GetRequestResultSet<Employee> resultSet) {
                allocatedEmployeesList = resultSet.getResultList();
                renderAllocatedEmployeesTable();
            }

            @Override
            public void onFailed(Exception ex) {
                allocatedEmployeesList = new ArrayList<>();
                renderAllocatedEmployeesTable();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void populateJobInfo() {
        lblOrder.setText(job.getCustomerOrderCaption());
        lblService.setText(job.getServiceName());
        lblStatus.setText(job.isFinished() ? "Finished" : "Not Finished");
        lstSkills.setModel(requiredSkillsListModel);
        requiredSkillsListModel.addAll(job.getRequiredSkills());
    }

    private void renderEmployeesTable() {
        tblEmp.removeAll();

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
    }

    private void renderAllocatedEmployeesTable() {
        tblAllocatedEmployees.removeAll();

        allocatedEmployeesTableModel = new AllocatedEmployeesTableModel(allocatedEmployeesList);
        tblAllocatedEmployees.setModel(allocatedEmployeesTableModel);

        TableRowSorter<AllocatedEmployeesTableModel> sorter = new TableRowSorter<>(allocatedEmployeesTableModel);
        tblAllocatedEmployees.setRowSorter(sorter);

        tblAllocatedEmployees.setFocusable(false);
        tblAllocatedEmployees.getTableHeader().setReorderingAllowed(false);
        tblAllocatedEmployees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initButtons() {
        btnBack.setIcon(AssetsManager.getImageIcon("BackIcon"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // this will re-construct the view
                rootView.navigate(NavPath.CREATED_JOBS, new CreatedJobs(rootView).getRootPanel());
            }
        });

        btnSuggest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                suggestEmployees();
            }
        });

        btnAllocate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allocateEmployee();
            }
        });

        btnDeAllocate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deallocateEmployee();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableData(txtSearch.getText());
            }
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    btnSearch.doClick();
            }
        });

        tblEmp.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                btnAllocate.setEnabled(getSelectedRowIndex(EMPLOYEE_TABLE) > -1);
            }
        });

        tblAllocatedEmployees.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                btnDeAllocate.setEnabled(getSelectedRowIndex(ALLOCATED_TABLE) > -1);
            }
        });
    }

    private int getSelectedRowIndex(int table) {
        if(table == EMPLOYEE_TABLE && tblEmp.getSelectedRow() == -1) return -1;
        if(table == ALLOCATED_TABLE && tblAllocatedEmployees.getSelectedRow() == -1) return -1;

        int viewSelectedIndex = table == EMPLOYEE_TABLE ? tblEmp.getSelectedRow() : tblAllocatedEmployees.getSelectedRow();
        int modelSelectedIndex = table == EMPLOYEE_TABLE ? tblEmp.convertRowIndexToModel(viewSelectedIndex) : tblAllocatedEmployees.convertRowIndexToModel(viewSelectedIndex);

        return modelSelectedIndex;
    }

    private void suggestEmployees() {
        if(SUGGESTION_MADE){
            btnSuggest.setText("Suggest Employees");
            loadInitialData();
            SUGGESTION_MADE = false;
        }else{
            allocateEmployeesController.getSuggestedEmployees(job, new GetRequestCallback<Employee>() {
                @Override
                public void onSuccess(GetRequestResultSet<Employee> resultSet) {
                    employeesList = resultSet.getResultList();
                    renderEmployeesTable();
                    btnSuggest.setText("Clear Suggestion");
                    SUGGESTION_MADE = true;
                }

                @Override
                public void onFailed(Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    private void allocateEmployee() {
        final Employee employee = employeeTableModel.getEmployeeAt(getSelectedRowIndex(EMPLOYEE_TABLE));
        employee.setAllocationStatus(true);

        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("<html>Allocation process in progress</html>");

        allocateEmployeesController.allocateEmployee(job, employee, new InsertRequestCallback() {
            @Override
            public void onSuccess() {
                loadingSpinner.stop();
                loadInitialData();
                JOptionPane.showMessageDialog(null, "Employee allocated successfully.", "Successful", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void onFailed(Exception ex) {
                loadingSpinner.stop();
                JOptionPane.showMessageDialog(null, "Error occurred", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void deallocateEmployee() {
        final Employee employee = allocatedEmployeesTableModel.getEmployeeAt(getSelectedRowIndex(ALLOCATED_TABLE));

        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("<html>De-Allocation process in progress</html>");

        allocateEmployeesController.deallocateEmployee(job, employee, new InsertRequestCallback() {
            @Override
            public void onSuccess() {
                loadingSpinner.stop();
                loadInitialData();
                JOptionPane.showMessageDialog(null, "Employee de-allocated successfully.", "Successful", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void onFailed(Exception ex) {
                loadingSpinner.stop();
                JOptionPane.showMessageDialog(null, "Error occurred!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void filterTableData(String key) {
        TableRowSorter<?> sorter = (TableRowSorter<?>) tblEmp.getRowSorter();

        RowFilter<Object, Object> idFilter = RowFilter.regexFilter("(?i)" + key, 0);
        RowFilter<Object, Object> nameFilter = RowFilter.regexFilter("(?i)" + key, 1);

        if(!key.isEmpty()){
            sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(idFilter, nameFilter)));
        }else{
            sorter.setRowFilter(null);
        }
    }
}
