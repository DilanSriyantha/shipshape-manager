package org.devdynamos.view;

import org.devdynamos.contollers.InventoryController;
import org.devdynamos.contollers.OrdersController;
import org.devdynamos.interfaces.MergeCompletedCallback;
import org.devdynamos.models.Order;
import org.devdynamos.models.SparePart;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.Console;
import org.devdynamos.customerCellRenderers.CustomBooleanCellRenderer;
import org.devdynamos.tableModels.PendingOrdersTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.util.ArrayList;
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
    private JButton btnMerge;
    private JCheckBox checkBoxFilterPending;

    private final OrdersController ordersController;
    private PendingOrdersTableModel pendingOrdersTableModel;
    private final RootView rootView;

    private final InventoryController inventoryController = new InventoryController();
    private final InventoryManagement inventoryManagementView;

    private List<Order> ordersList;

    public PendingOrders(RootView rootView, InventoryManagement inventoryManagementView) {
        this.rootView = rootView;
        this.inventoryManagementView = inventoryManagementView;
        this.ordersController = new OrdersController();

        loadPendingOrders();
        renderTable();
        initButtons();
    }

    public JPanel getRootPanel() {
        return pnlRoot;
    }

    private void loadPendingOrders() {
        tblPendingOrders.removeAll();

        ordersList = ordersController.getOrderRecordsList();

        tblPendingOrders.revalidate();
        tblPendingOrders.repaint();
    }

    private void renderTable() {
        pendingOrdersTableModel = new PendingOrdersTableModel(ordersList);
        tblPendingOrders.setModel(pendingOrdersTableModel);

        TableRowSorter<PendingOrdersTableModel> sorter = new TableRowSorter<>(pendingOrdersTableModel);
        tblPendingOrders.setRowSorter(sorter);

        tblPendingOrders.getColumnModel().getColumn(7).setCellRenderer(new CustomBooleanCellRenderer("Delivered"));

        tblPendingOrders.setFocusable(true);
        tblPendingOrders.getTableHeader().setReorderingAllowed(false);
        tblPendingOrders.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        checkBoxFilterPending.setSelected(false);
    }

    private void initButtons() {
        btnMerge.setIcon(AssetsManager.getImageIcon("MergeIcon"));
        btnMerge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleMergeProcess();
            }
        });

        btnInStock.setIcon(AssetsManager.getImageIcon("BackLightIcon"));
        btnInStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryManagementView.reload();
                rootView.goBack();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableData(txtSearch.getText(), checkBoxFilterPending.isSelected());
            }
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    btnSearch.doClick();
            }
        });

        checkBoxFilterPending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableData(txtSearch.getText(), checkBoxFilterPending.isSelected());
            }
        });

        tblPendingOrders.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRowIndex = tblPendingOrders.getSelectedRow();
                btnMerge.setEnabled(selectedRowIndex > -1);
            }
        });

        tblPendingOrders.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && !e.isConsumed()){
                    Order part = pendingOrdersTableModel.getOrderAt(tblPendingOrders.getSelectedRow());
                    Console.log(part.toString());;
                }
            }
        });
    }

    private void handleMergeProcess() {
        List<SparePart> productsToMerge = new ArrayList<>();
        List<Order> ordersToUpdate = new ArrayList<>();

        // convert the selected row indices on the view to corresponding indices of the model
        int[] viewSelectedRows = tblPendingOrders.getSelectedRows();
        int[] modelSelectedRows = new int[viewSelectedRows.length];
        for(int i = 0; i < viewSelectedRows.length; i++){
            modelSelectedRows[i] = tblPendingOrders.convertRowIndexToModel(viewSelectedRows[i]);
        }

        Order[] relevantOrders = pendingOrdersTableModel.getRelevantOrdersAt(modelSelectedRows);

        if(relevantOrders.length == 0){
            JOptionPane.showMessageDialog(null, "Selected product(s) are already delivered.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Order order : relevantOrders) {
            MergeInputDialog mergeInputDialog = new MergeInputDialog(
                    order,
                    (dialog, resultsMap) -> {
                        productsToMerge.add((SparePart) resultsMap.get("product"));
                        ordersToUpdate.add((Order) resultsMap.get("order"));

                        dialog.dispose();
                    },
                    (dialog) -> {
                        dialog.dispose();
                    }
            );
            mergeInputDialog.showDialog();
        }

        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Merging in progress");

        ordersController.executeMergeOperation(
                productsToMerge,
                ordersToUpdate,
                new MergeCompletedCallback() {
                    @Override
                    public void onSuccess() {

                        updateView();
                        loadingSpinner.stop();

                        Console.log("merge successful");
                    }

                    @Override
                    public void onFailed(Exception ex) {
                        loadingSpinner.stop();
                        ex.printStackTrace();
                    }
                }
        );
    }

    private void updateView() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                loadPendingOrders();
            }
        });

        t1.start();

        try {
            t1.join();
            renderTable();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void filterTableData(String key, boolean checkBoxPendingSelection){
        TableRowSorter<?> sorter = (TableRowSorter<?>) this.tblPendingOrders.getRowSorter();

        RowFilter<Object, Object> supplierNameFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 2);
        RowFilter<Object, Object> partNameFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 3);
        RowFilter<Object, Object> pending = RowFilter.regexFilter("(?i)false", 7);

        if (checkBoxPendingSelection){
            if(!key.isEmpty()){
                sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.orFilter(Arrays.asList(supplierNameFilter, partNameFilter)), pending)));
            }else{
                sorter.setRowFilter(pending); // filter pending=true rows only
            }
        }else{
            if(!key.isEmpty()){
                sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(supplierNameFilter, partNameFilter)));
            }else{
                sorter.setRowFilter(null);
            }
        }
    }
}
