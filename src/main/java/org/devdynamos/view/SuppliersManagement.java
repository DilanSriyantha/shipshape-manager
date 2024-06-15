package org.devdynamos.view;

import org.devdynamos.contollers.SuppliersController;
import org.devdynamos.models.SparePart;
import org.devdynamos.models.Supplier;
import org.devdynamos.utils.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class SuppliersManagement {
    private JTextField txtSearch;

    private JLabel lblTotalEmp;

    private JTable tblSuppliers;

    private JPanel pnlAllocate;
    private JPanel pnlRoot;

    private JButton btnBack;
    private JButton btnSearch;
    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;

    private final SuppliersController suppliersController;
    private SuppliersTableModel suppliersTableModel;
    private final RootView rootView;

    private List<Supplier> suppliersList;

    public SuppliersManagement(RootView rootView){
        this.rootView = rootView;
        this.suppliersController = new SuppliersController();

        loadSuppliers();
        renderTable();
        initButtons();
    }

    public JPanel getRootPanel() {
        return this.pnlRoot;
    }

    private void loadSuppliers() {
        this.suppliersList = this.suppliersController.getSuppliersList();
    }

    private void renderTable(){
        suppliersTableModel = new SuppliersTableModel(this.suppliersList);
        tblSuppliers.setModel(this.suppliersTableModel);

        TableRowSorter<SuppliersTableModel> sorter = new TableRowSorter<>(this.suppliersTableModel);
        tblSuppliers.setRowSorter(sorter);

        tblSuppliers.setFocusable(true);
        tblSuppliers.getTableHeader().setReorderingAllowed(false);
        tblSuppliers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
                InsertSupplierDialog insertSupplierDialog = new InsertSupplierDialog(
                        (dialog, supplier) -> {
                            suppliersController.insertSupplier(supplier);
                            updateView();
                            dialog.dispose();
                        },
                        (dialog) -> {
                            dialog.dispose();
                        }
                );
                insertSupplierDialog.showDialog();
            }
        });

        btnUpdate.setIcon(AssetsManager.getImageIcon("UpdateIcon"));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Supplier selectedSupplier = suppliersTableModel.getSupplierAt(getSelectedRowIndex());
                InsertSupplierDialog updateSupplierDialog = new InsertSupplierDialog(
                        selectedSupplier,
                        (dialog, supplier) -> {
                            suppliersController.updateSupplier(selectedSupplier.getSupplierId(), supplier.toHashMap());
                            updateView();
                            dialog.dispose();
                        },
                        (dialog) -> {
                            dialog.dispose();
                        }
                );
                updateSupplierDialog.showDialog();
            }
        });

        btnDelete.setIcon(AssetsManager.getImageIcon("DeleteIcon"));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Supplier selectedSupplier = suppliersTableModel.getSupplierAt(getSelectedRowIndex());
                final int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + selectedSupplier.getSupplierName() + " from the system?");

                if(res == 0){
                    suppliersController.deleteSupplier(selectedSupplier.getSupplierId());
                    updateView();
                }
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
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    btnSearch.doClick();
                }
            }
        });

        tblSuppliers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = tblSuppliers.getSelectedRow();

                behaveUpdateButton(selectedIndex);
                behaveDeleteButton(selectedIndex);
            }
        });

        tblSuppliers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && !e.isConsumed()){
                    Supplier selectedSupplier = suppliersTableModel.getSupplierAt(getSelectedRowIndex());
                    rootView.navigate(NavPath.SUPPLIER_PRODUCTS, new ProductsView(rootView, selectedSupplier).getRootPanel());
                }
            }
        });
    }

    private int getSelectedRowIndex() {
        if(tblSuppliers.getSelectedRow() == -1) return -1;

        int viewSelectedIndex = tblSuppliers.getSelectedRow();
        int modelSelectedIndex = tblSuppliers.convertRowIndexToModel(viewSelectedIndex);

        return modelSelectedIndex;
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
                loadSuppliers();
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

    private void filterTableData(String key){
        TableRowSorter<?> sorter = (TableRowSorter<?>) this.tblSuppliers.getRowSorter();

        RowFilter<Object, Object> idFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 0);
        RowFilter<Object, Object> nameFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 1);

        if(!key.isEmpty()){
            sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(idFilter, nameFilter)));
        }else{
            sorter.setRowFilter(null);
        }
    }
}
