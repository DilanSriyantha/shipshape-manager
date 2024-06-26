package org.devdynamos.view;

import org.devdynamos.contollers.InventoryController;
import org.devdynamos.interfaces.GetRequestCallback;
import org.devdynamos.interfaces.SendOrderPlacementCallback;
import org.devdynamos.models.GetRequestResultSet;
import org.devdynamos.models.SparePart;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.customerCellRenderers.CustomBooleanCellRenderer;
import org.devdynamos.tableModels.InventoryTableModel;
import org.devdynamos.utils.NavPath;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class InventoryManagement {
    private JTextField txtSearch;

    private JLabel lblTotalEmp;

    private JTable tblInventory;
    private JCheckBox checkBoxFilterOnShip;

    private JPanel pnlAllocate;
    private JPanel pnlRoot;

    private JButton btnBack;
    private JButton btnSearch;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnPlaceOrderFromSupplier;
    private JLabel lblOrderPlacementStatus;
    private JButton btnPending;

    private final InventoryController inventoryController;
    private InventoryTableModel inventoryTableModel;
    private final RootView rootView;

    private List<SparePart> sparePartList;

    private JPopupMenu popupMenu;

    public InventoryManagement(RootView rootView){
        this.rootView = rootView;
        this.inventoryController = new InventoryController();

        loadSpareParts();
        renderTable();
        initButtons();
        initPopupMenu();
    }

    public JPanel getRootPanel() {
        return this.pnlRoot;
    }

    private void loadSpareParts() {
        this.sparePartList = this.inventoryController.getSparePartsList();
    }

    private void renderTable(){
        tblInventory.removeAll();

        inventoryTableModel = new InventoryTableModel(this.sparePartList);
        tblInventory.setModel(this.inventoryTableModel);

        TableRowSorter<InventoryTableModel> sorter = new TableRowSorter<>(this.inventoryTableModel);
        tblInventory.setRowSorter(sorter);

        tblInventory.getColumnModel().getColumn(6).setCellRenderer(new CustomBooleanCellRenderer("true"));

        tblInventory.setFocusable(true);
        tblInventory.getTableHeader().setReorderingAllowed(false);
        tblInventory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initButtons() {
        btnBack.setIcon(AssetsManager.getImageIcon("BackIcon"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.goBack();
            }
        });

        btnPending.setIcon(AssetsManager.getImageIcon("NextIcon"));
        btnPending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGotoPending();
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

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableData(txtSearch.getText(), checkBoxFilterOnShip.isSelected());
            }
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    btnSearch.doClick();
                }
            }
        });

        checkBoxFilterOnShip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableData(txtSearch.getText(), checkBoxFilterOnShip.isSelected());
            }
        });

        tblInventory.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = tblInventory.getSelectedRow();

                behaveUpdateButton(selectedIndex);
                behaveDeleteButton(selectedIndex);
            }
        });

        tblInventory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3)
                    if(getSelectedRowIndex() > -1)
                        popupMenu.show(tblInventory, e.getX(), e.getY());
            }
        });
    }

    private void initPopupMenu() {
        popupMenu = new JPopupMenu("Item options");

        JMenuItem menuItem = new JMenuItem("Predict stock out date");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getSelectedRowIndex() > -1)
                    inventoryTableModel.getSparePartAt(getSelectedRowIndex()).predictOutOfStockDate(new GetRequestCallback<LocalDate>() {
                        @Override
                        public void onSuccess(GetRequestResultSet<LocalDate> resultSet) {
                            JOptionPane.showMessageDialog(null,  inventoryTableModel.getSparePartAt(getSelectedRowIndex()).getName() + " Stock may be out by " + resultSet.getResultList().getFirst().toString(), "Info", JOptionPane.INFORMATION_MESSAGE);
                        }

                        @Override
                        public void onFailed(Exception ex) {
                            JOptionPane.showMessageDialog(null, "No sales records found on the subjected product yet.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });
            }
        });

        popupMenu.add(menuItem);
    }

    private int getSelectedRowIndex() {
        int viewSelectedIndex = tblInventory.getSelectedRow();
        if(viewSelectedIndex == -1) return -1;

        int modelSelectedIndex = tblInventory.convertRowIndexToModel(viewSelectedIndex);
        return modelSelectedIndex;
    }

    private void handleGotoPending() {
        rootView.navigate(NavPath.PENDING_ORDERS, new PendingOrders(rootView, this).getRootPanel());
    }

    private void handleUpdate() {
        int viewSelectedIndex = tblInventory.getSelectedRow();
        int modelSelectedIndex = tblInventory.convertRowIndexToModel(viewSelectedIndex);
        SparePart selectedSparePart = inventoryTableModel.getSparePartAt(modelSelectedIndex);

        InsertSparePartDialog updateSparePartDialog = new InsertSparePartDialog(
                selectedSparePart,
                (dialog, sparePart) -> {
                    inventoryController.updateSparePart(sparePart.getPartId(), sparePart.toHashMap());
                    updateView();
                    dialog.dispose();
                },
                (dialog) -> {
                    dialog.dispose();
                }
        );
        updateSparePartDialog.showDialog();
    }

    private void handleDelete() {
        int viewSelectedIndex = tblInventory.getSelectedRow();
        int modelSelectedIndex = tblInventory.convertRowIndexToModel(viewSelectedIndex);
        SparePart selectedSparePart = inventoryTableModel.getSparePartAt(modelSelectedIndex);
        final int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + selectedSparePart.getName() + " from the system?");

        if(res == 0){
            inventoryController.deleteSparePart(selectedSparePart.getPartId());
            updateView();
        }
    }

    private void executePlaceOrder(int qty, String expectedDate){
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Placing order...");

        SparePart selectedSparePart = inventoryTableModel.getSparePartAt(tblInventory.getSelectedRow());
        inventoryController.sendOrderPlacementToTheSupplier(
                selectedSparePart,
                expectedDate,
                qty,
                new SendOrderPlacementCallback() {
                    @Override
                    public void onSuccess() {
                        loadingSpinner.stop();
                        JOptionPane.showMessageDialog(null, "Order placement successful!", "Successful", JOptionPane.INFORMATION_MESSAGE);
                    }

                    @Override
                    public void onFailed(Exception ex) {
                        loadingSpinner.stop();
                        JOptionPane.showMessageDialog(null, "Order placement failed!");
                        ex.printStackTrace();
                    }
                }
        );
    }

    private void behaveUpdateButton(int selectedIndex){
        btnUpdate.setEnabled(selectedIndex >= 0);
    }

    private void behaveDeleteButton(int selectedIndex){
        btnDelete.setEnabled(selectedIndex >= 0);
    }

    private void updateView(){
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                loadSpareParts();
            }
        });

        t1.start();

        System.out.println("Loading...");

        try{
            t1.join();
            renderTable();
            System.out.println("Done!");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void filterTableData(String key, boolean checkBoxOnShipSelection){
        TableRowSorter<?> sorter = (TableRowSorter<?>) this.tblInventory.getRowSorter();

        RowFilter<Object, Object> idFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 0);
        RowFilter<Object, Object> nameFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 1);
        RowFilter<Object, Object> onShipFilter = RowFilter.regexFilter("(?i)true", 6);

        if (checkBoxOnShipSelection){
            if(!key.isEmpty()){
                sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(RowFilter.orFilter(Arrays.asList(idFilter, nameFilter)), onShipFilter)));
            }else{
                sorter.setRowFilter(onShipFilter); // filter onShip=true rows only
            }
        }else{
            if(!key.isEmpty()){
                sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(idFilter, nameFilter)));
            }else{
                sorter.setRowFilter(null);
            }
        }
    }

    public void reload() {
        Thread loadDataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                loadSpareParts();
            }
        });

        Thread waitThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    loadDataThread.join();

                    renderTable();
                    tblInventory.revalidate();
                    tblInventory.repaint();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        loadDataThread.start();
        waitThread.start();
    }
}
