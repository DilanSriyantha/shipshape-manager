package org.devdynamos.view;

import org.devdynamos.contollers.CustomersController;
import org.devdynamos.models.Customer;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.Console;
import org.devdynamos.utils.CustomersTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

public class CustomerManagement {
    private JPanel pnlRoot;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTable tblCustomers;
    private JLabel lblTotalEmp;
    private JButton btnBack;
    private JPanel pnlAllocate;
    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;

    private final RootView rootView;
    private List<Customer> customerList;
    private CustomersTableModel customersTableModel;
    private final CustomersController customersController;

    public CustomerManagement(RootView rootView){
        this.rootView = rootView;
        this.customersController = new CustomersController();

        loadCustomers();
        renderTable();
        initButtons();
    }

    public JPanel getRootPanel() {
        return pnlRoot;
    }

    private void loadCustomers() {
        this.customerList = customersController.getCustomersList();
    }

    private void renderTable() {
        customersTableModel = new CustomersTableModel(customerList);
        tblCustomers.setModel(customersTableModel);

        TableRowSorter<CustomersTableModel> sorter = new TableRowSorter<>(customersTableModel);
        tblCustomers.setRowSorter(sorter);

        tblCustomers.setFocusable(true);
        tblCustomers.getTableHeader().setReorderingAllowed(false);
        tblCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initButtons() {
        btnBack.setIcon(AssetsManager.getImageIcon("BackIcon"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.goBack();
            }
        });

        btnInsert.setIcon(AssetsManager.getImageIcon("AddIcon"));
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInsertCustomer();
            }
        });

        btnUpdate.setIcon(AssetsManager.getImageIcon("UpdateIcon"));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateCustomer();
            }
        });

        btnDelete.setIcon(AssetsManager.getImageIcon("DeleteIcon"));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer customer = customersTableModel.getCustomerAt(getSelectedRowIndex());

                final int res = JOptionPane.showConfirmDialog(null, "Are you sure want to delete " + customer.getCustomerName() + " from the system?");
                if(res == 0){
                    customersController.deleteCustomer(customer.getCustomerId());
                    updateView();
                }
            }
        });

        tblCustomers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                behaveUpdateButton(getSelectedRowIndex());
                behaveDeleteButton(getSelectedRowIndex());
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
    }

    private void behaveUpdateButton(int selectedIndex) {
        btnUpdate.setEnabled(selectedIndex > -1);
    }

    private void behaveDeleteButton(int selectedIndex){
        btnDelete.setEnabled(selectedIndex > -1);
    }

    private void handleInsertCustomer() {
        InsertCustomerInputDialog insertCustomerInputDialog = new InsertCustomerInputDialog(
                new Customer(),
                (dialog, customer) -> {
                    customersController.insertCustomer(customer);
                    dialog.dispose();
                    updateView();
                },
                Window::dispose
        );
        insertCustomerInputDialog.showDialog();
    }

    private void handleUpdateCustomer() {
        Customer selectedCustomer = customersTableModel.getCustomerAt(getSelectedRowIndex());
        InsertCustomerInputDialog updateCustomerDialog = new InsertCustomerInputDialog(
                selectedCustomer,
                (dialog, customer) -> {
                    customersController.updateCustomer(customer);
                    dialog.dispose();
                    updateView();
                },
                Window::dispose
        );
        updateCustomerDialog.showDialog();
    }

    private int getSelectedRowIndex() {
        if(tblCustomers.getSelectedRow() == -1) return -1;

        int viewSelectedIndex = tblCustomers.getSelectedRow();
        int modelSelectedIndex = tblCustomers.convertRowIndexToModel(viewSelectedIndex);

        return modelSelectedIndex;
    }

    private void filterTableData(String key){
        TableRowSorter<?> sorter = (TableRowSorter<?>) tblCustomers.getRowSorter();

        RowFilter<Object, Object> idFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 0);
        RowFilter<Object, Object> nameFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 1);

        if(!key.isEmpty()){
            sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(idFilter, nameFilter)));
        }else{
            sorter.setRowFilter(null);
        }
    }

    private void updateView() {
        Thread loadDataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                loadCustomers();
            }
        });

        loadDataThread.start();

        try{
            loadDataThread.join();
            renderTable();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
