package org.devdynamos.view;

import org.devdynamos.contollers.InventoryController;
import org.devdynamos.models.SparePart;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.CustomBooleanCellRenderer;
import org.devdynamos.utils.InventoryTableModel;
import org.devdynamos.utils.NavPath;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
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

    private final InventoryController inventoryController;
    private InventoryTableModel inventoryTableModel;
    private final RootView rootView;

    private List<SparePart> sparePartList;

    public InventoryManagement(RootView rootView){
        this.rootView = rootView;
        this.inventoryController = new InventoryController();

        loadSpareParts();
        renderTable();
        initButtons();
    }

    public JPanel getRootPanel() {
        return this.pnlRoot;
    }

    private void loadSpareParts() {
        this.sparePartList = this.inventoryController.getSparePartsList();
    }

    private void renderTable(){
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

        btnUpdate.setIcon(AssetsManager.getImageIcon("UpdateIcon"));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SparePart selectedSparePart = inventoryTableModel.getSparePartAt(tblInventory.getSelectedRow());
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
        });

        btnDelete.setIcon(AssetsManager.getImageIcon("DeleteIcon"));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SparePart selectedSparePart = inventoryTableModel.getSparePartAt(tblInventory.getSelectedRow());
                final int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + selectedSparePart.getName() + " from the system?");

                if(res == 0){
                    inventoryController.deleteSparePart(selectedSparePart.getPartId());
                    updateView();
                }
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
}
