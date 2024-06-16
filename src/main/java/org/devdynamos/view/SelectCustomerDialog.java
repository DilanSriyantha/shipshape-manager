package org.devdynamos.view;

import org.devdynamos.contollers.CustomersController;
import org.devdynamos.interfaces.DialogNegativeCallback;
import org.devdynamos.interfaces.DialogPositiveCallback;
import org.devdynamos.models.Customer;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.CustomerSelectTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.Arrays;
import java.util.List;

public class SelectCustomerDialog extends JDialog {
    private JPanel contentPane;
    private JButton btnConfirm;
    private JButton btnCancel;
    private JPanel pnlContainer;
    private JButton btnNew;
    private JTable tblCustomers;
    private JTextField txtSearch;
    private JButton btnSearch;

    private final DialogPositiveCallback<Customer> positiveCallback;
    private final DialogNegativeCallback<Customer> negativeCallback;
    private final CustomersController customersController;
    private List<Customer> customerList;
    private CustomerSelectTableModel customerSelectTableModel;

    public SelectCustomerDialog(DialogPositiveCallback<Customer> positiveCallback, DialogNegativeCallback<Customer> negativeCallback) {
        this.positiveCallback = positiveCallback;
        this.negativeCallback = negativeCallback;
        this.customersController = new CustomersController();

        setupModel();
        loadCustomers();
        renderTable();
        initButtons();
    }

    private void setupModel() {
        setContentPane(contentPane);
        setModal(true);
    }

    private void loadCustomers() {
        customerList = customersController.getCustomersList();
    }

    private void renderTable() {
        tblCustomers.removeAll();

        customerSelectTableModel = new CustomerSelectTableModel(customerList);
        tblCustomers.setModel(customerSelectTableModel);

        TableRowSorter<CustomerSelectTableModel> sorter = new TableRowSorter<>(customerSelectTableModel);
        tblCustomers.setRowSorter(sorter);

        tblCustomers.setFocusable(true);
        tblCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblCustomers.getTableHeader().setReorderingAllowed(false);
    }

    private void initButtons() {
        btnConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        btnNew.setIcon(AssetsManager.getImageIcon("AddIcon"));
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                if(e.getKeyCode() == KeyEvent.VK_ENTER) btnSearch.doClick();
            }
        });

        tblCustomers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && !e.isConsumed())
                    onOK();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private int getSelectedIndex() {
        final int viewSelectedIndex = tblCustomers.getSelectedRow();
        if(viewSelectedIndex == -1) return -1;
        final int modelSelectedIndex = tblCustomers.convertRowIndexToModel(viewSelectedIndex);

        return modelSelectedIndex;
    }

    private void onOK() {
        if(getSelectedIndex() == -1){
            onCancel();
            return;
        }

        Customer selectedCustomer = customerSelectTableModel.getCustomerAt(getSelectedIndex());
        positiveCallback.execute(this, selectedCustomer);
    }

    private void onCancel() {
        negativeCallback.execute(this);
    }

    private void filterTableData(String key){
        TableRowSorter<?> sorter = (TableRowSorter<?>) tblCustomers.getRowSorter();

        RowFilter<Object, Object> idFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 0);
        RowFilter<Object, Object> nameFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 1);

        if(!key.isEmpty())
            sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(idFilter, nameFilter)));
        else
            sorter.setRowFilter(null);
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

    public void showDialog() {
        setTitle("Select a customer...");
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
