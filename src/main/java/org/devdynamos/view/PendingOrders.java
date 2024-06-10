package org.devdynamos.view;

import org.devdynamos.contollers.OrdersController;
import org.devdynamos.models.Order;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.PendingOrdersTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

public class PendingOrders {
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTable tblPendingOrders;
    private JLabel lblOrderPlacementStatus;
    private JLabel lblTotalEmp;
    private JPanel pnlAllocate;
    private JButton btnPlaceOrderFromSupplier;
    private JPanel pnlRoot;
    private JButton btnInStock;

    private final OrdersController ordersController;
    private PendingOrdersTableModel pendingOrdersTableModel;
    private final RootView rootView;

    private List<Order> ordersList;

    public PendingOrders(RootView rootView) {
        this.rootView = rootView;
        this.ordersController = new OrdersController();

        loadPendingOrders();
        renderTable();
        initButtons();
    }

    public JPanel getRootPanel() {
        return pnlRoot;
    }

    private void loadPendingOrders() {
        ordersList = ordersController.getOrderRecordsList();
    }

    private void renderTable() {
        pendingOrdersTableModel = new PendingOrdersTableModel(ordersList);
        tblPendingOrders.setModel(pendingOrdersTableModel);

        TableRowSorter<PendingOrdersTableModel> sorter = new TableRowSorter<>(pendingOrdersTableModel);
        tblPendingOrders.setRowSorter(sorter);

        tblPendingOrders.setFocusable(true);
        tblPendingOrders.getTableHeader().setReorderingAllowed(false);
        tblPendingOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initButtons() {
        btnPlaceOrderFromSupplier.setIcon(AssetsManager.getImageIcon("AddIcon"));

        btnInStock.setIcon(AssetsManager.getImageIcon("BackLightIcon"));
        btnInStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.goBack();
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

    private void filterTableData(String key){
        TableRowSorter<?> sorter = (TableRowSorter<?>) this.tblPendingOrders.getRowSorter();

        RowFilter<Object, Object> supplierNameFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 2);
        RowFilter<Object, Object> partNameFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 3);

        if(!key.isEmpty()){
            sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(supplierNameFilter, partNameFilter)));
        }else{
            sorter.setRowFilter(null);
        }
    }
}
