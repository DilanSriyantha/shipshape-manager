package org.devdynamos.view;

import org.devdynamos.components.CashierListItem;
import org.devdynamos.components.OrderItemRecord;
import org.devdynamos.contollers.CashierDashboardController;
import org.devdynamos.models.OrderItem;
import org.devdynamos.models.SparePart;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.NavPath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CashierDashboard {
    private JPanel pnlRoot;
    private JTabbedPane tabPanelCategory;
    private JPanel tabBestSellers;
    private JPanel tabAll;
    private JPanel pnlBestSellers;
    private JPanel pnlAll;
    private JButton btnAddCustomer;
    private JButton btnTotal;
    private JButton btnBack;
    private JPanel pnlOrderItemRecords;
    private JPanel pnlOrderRecordsListContainer;

    private Dimension orderItemRecordsListSize;

    private RootView rootView;

    private final CashierDashboardController cashierDashboardController;

    public CashierDashboard(RootView rootView){
        this.rootView = rootView;
        this.cashierDashboardController = new CashierDashboardController();

        renderQuickItemsPanel();
        renderOrderItemRecords();
        initBackButton();
        initAddCustomerButton();
    }

    public JPanel getRootPanel(){
        return this.pnlRoot;
    }

    private void renderQuickItemsPanel() {
        CashierListItem item1 = new CashierListItem("item 1", AssetsManager.getImageIcon("RepairIcon"), (sparePart -> {
            cashierDashboardController.addRecord(new OrderItem((int) (Math.random() * 100), 1, sparePart.getName(), sparePart.getSellingPrice(), 1, sparePart.getSellingPrice() * 1));
            renderOrderItemRecords();
        }));
        CashierListItem item2 = new CashierListItem("item 2", AssetsManager.getImageIcon("PaintIcon"));

        this.pnlBestSellers.add(item1);
        this.pnlBestSellers.add(item2);

        for(SparePart part : this.cashierDashboardController.getSpareParts()){
            if(part.isTopSeller()){
                this.pnlBestSellers.add(new CashierListItem(part, AssetsManager.getImageIcon("GearIcon"), (sparePart) -> {
                    cashierDashboardController.addRecord(new OrderItem((int) (Math.random() * 100), 1, sparePart.getName(), sparePart.getSellingPrice(), 1, sparePart.getSellingPrice() * 1));
                    renderOrderItemRecords();
                }));
            }

            this.pnlAll.add(new CashierListItem(part, AssetsManager.getImageIcon("GearIcon"), (sparePart) -> {
                cashierDashboardController.addRecord(new OrderItem((int) (Math.random() * 100), 1, sparePart.getName(), sparePart.getSellingPrice(), 1, sparePart.getSellingPrice() * 1));
                renderOrderItemRecords();
            }));
        }

        tabBestSellers.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int numberOfComponentsCanBeMappedInARow = e.getComponent().getWidth() / CashierListItem.defaultWidth;
                if(numberOfComponentsCanBeMappedInARow <= 0)
                    numberOfComponentsCanBeMappedInARow = 1;
                int numberOfComponentsInAColumn = 12 / numberOfComponentsCanBeMappedInARow;
                int totalHeight = numberOfComponentsInAColumn * (CashierListItem.defaultHeight + 10);

                pnlBestSellers.setPreferredSize(new Dimension(e.getComponent().getWidth() - 20, totalHeight));
            }
        });

        tabAll.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int numberOfComponentsCanBeMappedInARow = e.getComponent().getWidth() / CashierListItem.defaultWidth;
                if(numberOfComponentsCanBeMappedInARow <= 0)
                    numberOfComponentsCanBeMappedInARow = 1;
                int numberOfComponentsInAColumn = 12 / numberOfComponentsCanBeMappedInARow;
                int totalHeight = numberOfComponentsInAColumn * (CashierListItem.defaultHeight + 10);

                pnlAll.setPreferredSize(new Dimension(e.getComponent().getWidth() - 20, totalHeight));
            }
        });
    }

    private void renderOrderItemRecords(){
        this.pnlOrderItemRecords.removeAll();

        for (int i = 0; i < this.cashierDashboardController.getOrderItemRecords().size(); i++) {
            final OrderItem item = this.cashierDashboardController.getOrderItemRecords().get(i);
            OrderItemRecord rec = new OrderItemRecord(
                    (i + 1),
                    item,
                    (orderItem) -> {
                        this.cashierDashboardController.getOrderItemRecords().removeIf((_orderItem) -> {
                            return _orderItem.getRecordId() == orderItem.getRecordId();
                        });
                        renderOrderItemRecords();
                    }
            );

            this.pnlOrderItemRecords.add(rec);
        }

        updateTotal(cashierDashboardController.getTotal());

        this.pnlOrderItemRecords.revalidate();
        this.pnlOrderItemRecords.repaint();

        if(this.orderItemRecordsListSize != null)
            syncOrderItemRecordsList((int)this.orderItemRecordsListSize.getWidth() + 20); // add 20 here since I subtract 20 from container width in the sync method and when performing this subtraction without setting the container width each time deduct 20 from the last container width each time. finally it becomes 0 and performs a division by zero which leads to an unexpected behaviour.

        pnlOrderRecordsListContainer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                syncOrderItemRecordsList(e.getComponent().getWidth());
            }
        });
    }

    private void syncOrderItemRecordsList(int containerWidth){
        int numberOfComponentsCanBeMappedInRow = containerWidth / (OrderItemRecord.defaultWidth);
        int numberOfComponentsInAColumn = cashierDashboardController.getOrderItemRecords().size() / numberOfComponentsCanBeMappedInRow;
        int totalHeight = numberOfComponentsInAColumn * (OrderItemRecord.defaultHeight + 10);

        this.orderItemRecordsListSize = new Dimension(containerWidth - 20, totalHeight);

        this.pnlOrderItemRecords.setPreferredSize(this.orderItemRecordsListSize);

        println(this.orderItemRecordsListSize);
    }

    private void updateTotal(double total) {
        this.btnTotal.setText("LKR " + String.valueOf(total) + "/=");
    }

    private void initBackButton() {
        this.btnBack.setIcon(AssetsManager.getImageIcon("BackIcon"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.goBack();
            }
        });
    }

    private void initAddCustomerButton() {
        this.btnAddCustomer.setIcon(AssetsManager.getImageIcon("PersonAddIcon"));
    }

    private <T> void println(T val){
        if(val instanceof String){
            System.out.println(val);
        }
        System.out.println(val.toString());
    }
}
