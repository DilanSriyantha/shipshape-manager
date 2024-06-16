package org.devdynamos.view;

import org.devdynamos.contollers.OrderHistoryController;
import org.devdynamos.interfaces.GetOrdersListCallback;
import org.devdynamos.models.HistoryOrder;
import org.devdynamos.tableModels.OrderHistoryTableModel;
import org.devdynamos.tableModels.OrderProductsTableModel;
import org.devdynamos.tableModels.OrderServicesTableModel;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.Console;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

public class OrderHistory {
    private JPanel pnlRoot;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JLabel lblTotalEmp;
    private JButton btnBack;
    private JTable tblOrders;
    private JTable tblProducts;
    private JTable tblServices;

    private final RootView rootView;

    private final OrderHistoryController orderHistoryController;
    private OrderHistoryTableModel orderHistoryTableModel;
    private OrderProductsTableModel orderProductsTableModel;
    private OrderServicesTableModel orderServicesTableModel;
    private List<HistoryOrder> ordersList;

    public OrderHistory(RootView rootView){
        this.rootView = rootView;
        this.orderHistoryController = new OrderHistoryController();

        loadOrders();
        initButtons();
    }

    public JPanel getRootPanel() {
        return pnlRoot;
    }

    private void loadOrders() {
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Loading...");

        orderHistoryController.getOrdersList(new GetOrdersListCallback() {
            @Override
            public void onSuccess(List<HistoryOrder> orders) {
                ordersList = orders;
                renderOrdersTable();
                loadingSpinner.stop();
            }

            @Override
            public void onFailed(Exception ex) {
                loadingSpinner.stop();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void renderOrdersTable() {
        orderHistoryTableModel = new OrderHistoryTableModel(ordersList);
        tblOrders.setModel(orderHistoryTableModel);

        TableRowSorter<OrderHistoryTableModel> sorter = new TableRowSorter<>(orderHistoryTableModel);
        tblOrders.setRowSorter(sorter);

        tblOrders.setFocusable(true);
        tblOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblOrders.getTableHeader().setReorderingAllowed(false);

        if(!ordersList.isEmpty())
            tblOrders.changeSelection(0, 0, false, false);
    }

    private void renderProductsTable(HistoryOrder order) {
        orderProductsTableModel = new OrderProductsTableModel(order.getProductList());
        tblProducts.setModel(orderProductsTableModel);

        TableRowSorter<OrderProductsTableModel> sorter = new TableRowSorter<>(orderProductsTableModel);
        tblProducts.setRowSorter(sorter);

        tblProducts.setFocusable(true);
        tblProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblProducts.getTableHeader().setReorderingAllowed(false);
    }

    private void renderServicesTable(HistoryOrder order) {
        orderServicesTableModel = new OrderServicesTableModel(order.getServicesList());
        tblServices.setModel(orderServicesTableModel);

        TableRowSorter<OrderServicesTableModel> sorter = new TableRowSorter<>(orderServicesTableModel);
        tblServices.setRowSorter(sorter);

        tblServices.setFocusable(true);
        tblServices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblServices.getTableHeader().setReorderingAllowed(false);
    }

    private void initButtons() {
        btnBack.setIcon(AssetsManager.getImageIcon("BackIcon"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.goBack();
            }
        });

        tblOrders.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(getSelectedRowIndex() == -1) return;

                HistoryOrder order = orderHistoryTableModel.getOrderAt(getSelectedRowIndex());
                renderProductsTable(order);
                renderServicesTable(order);

                Console.log(getSelectedRowIndex());
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

    private int getSelectedRowIndex() {
        if(tblOrders.getSelectedRow() == -1) return -1;

        int viewSelectedIndex = tblOrders.getSelectedRow();
        int modelSelectedIndex = tblOrders.convertRowIndexToModel(viewSelectedIndex);

        return modelSelectedIndex;
    }

    private void filterTableData(String key){
        TableRowSorter<?> sorter = (TableRowSorter<?>) tblOrders.getRowSorter();

        RowFilter<Object, Object> idFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 0);
        RowFilter<Object, Object> nameFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 1);
        RowFilter<Object, Object> customerNameFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 2);

        if(!key.isEmpty())
            sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(idFilter, nameFilter, customerNameFilter)));
        else
            sorter.setRowFilter(null);
    }
}
