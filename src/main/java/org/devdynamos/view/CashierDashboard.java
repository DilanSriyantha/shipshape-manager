package org.devdynamos.view;

import org.devdynamos.components.CashierListItem;
import org.devdynamos.components.OrderItemRecord;
import org.devdynamos.contollers.CashierDashboardController;
import org.devdynamos.models.OrderItem;
import org.devdynamos.models.SparePart;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CashierDashboard {
    private JPanel pnlRoot;
    private JPanel tabBestSellers;
    private JPanel tabAll;
    private JPanel pnlBestSellers;
    private JPanel pnlAll;
    private JButton btnAddCustomer;
    private JButton btnTotal;
    private JButton btnBack;
    private JPanel pnlOrderItemRecords;
    private JPanel pnlOrderRecordsListContainer;
    private JPanel pnlProducts;
    private JButton btnAll;
    private JButton btnProducts;
    private JButton btnServices;

    private final int ALL = 0;
    private final int PRODUCTS = 1;
    private final int SERVICES = 2;
    private final Color SELECTED_COLOR = new Color(138, 178, 215);
    private final Color IDLE_COLOR = new Color(184, 207, 229);

    private int selectedTab = ALL;

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
        initTabButtons();
    }

    public JPanel getRootPanel(){
        return this.pnlRoot;
    }

    private void selectTab(int tab) {
        switch (tab){
            case ALL:
                selectedTab = ALL;

                btnAll.setBackground(SELECTED_COLOR);
                btnProducts.setBackground(IDLE_COLOR);
                btnServices.setBackground(IDLE_COLOR);
                break;

            case PRODUCTS:
                selectedTab = PRODUCTS;

                btnAll.setBackground(IDLE_COLOR);
                btnProducts.setBackground(SELECTED_COLOR);
                btnServices.setBackground(IDLE_COLOR);
                break;

            case SERVICES:
                selectedTab = SERVICES;

                btnAll.setBackground(IDLE_COLOR);
                btnProducts.setBackground(IDLE_COLOR);
                btnServices.setBackground(SELECTED_COLOR);
                break;

            default: Console.log("Invalid");
        }

        renderQuickItemsPanel();
    }

    private void renderQuickItemsPanel() {
        pnlProducts.removeAll();

        if(selectedTab == ALL){
            CashierListItem item1 = new CashierListItem("item 1", AssetsManager.getImageIcon("RepairIcon"), (sparePart -> {
                cashierDashboardController.addRecord(new OrderItem((int) (Math.random() * 100), 1, sparePart.getName(), sparePart.getSellingPrice(), 1, sparePart.getSellingPrice() * 1));
                renderOrderItemRecords();
            }));
            CashierListItem item2 = new CashierListItem("item 2", AssetsManager.getImageIcon("PaintIcon"));

            pnlProducts.add(item1);
            pnlProducts.add(item2);

            for(SparePart part : this.cashierDashboardController.getSpareParts()){
                if(part.isTopSeller()){
                    pnlProducts.add(new CashierListItem(part, AssetsManager.getImageIcon("GearIcon"), (sparePart) -> {
                        cashierDashboardController.addRecord(new OrderItem((int) (Math.random() * 100), 1, sparePart.getName(), sparePart.getSellingPrice(), 1, sparePart.getSellingPrice() * 1));
                        renderOrderItemRecords();
                    }));
                }

                pnlProducts.add(new CashierListItem(part, AssetsManager.getImageIcon("GearIcon"), (sparePart) -> {
                    cashierDashboardController.addRecord(new OrderItem((int) (Math.random() * 100), 1, sparePart.getName(), sparePart.getSellingPrice(), 1, sparePart.getSellingPrice() * 1));
                    renderOrderItemRecords();
                }));
            }
        }

        if(selectedTab == PRODUCTS){
            for(SparePart part : this.cashierDashboardController.getSpareParts()){
                if(part.isTopSeller()){
                    pnlProducts.add(new CashierListItem(part, AssetsManager.getImageIcon("GearIcon"), (sparePart) -> {
                        cashierDashboardController.addRecord(new OrderItem((int) (Math.random() * 100), 1, sparePart.getName(), sparePart.getSellingPrice(), 1, sparePart.getSellingPrice() * 1));
                        renderOrderItemRecords();
                    }));
                }

                pnlProducts.add(new CashierListItem(part, AssetsManager.getImageIcon("GearIcon"), (sparePart) -> {
                    cashierDashboardController.addRecord(new OrderItem((int) (Math.random() * 100), 1, sparePart.getName(), sparePart.getSellingPrice(), 1, sparePart.getSellingPrice() * 1));
                    renderOrderItemRecords();
                }));
            }
        }

        if(selectedTab == SERVICES){
            CashierListItem item1 = new CashierListItem("item 1", AssetsManager.getImageIcon("RepairIcon"), (sparePart -> {
                cashierDashboardController.addRecord(new OrderItem((int) (Math.random() * 100), 1, sparePart.getName(), sparePart.getSellingPrice(), 1, sparePart.getSellingPrice() * 1));
                renderOrderItemRecords();
            }));
            CashierListItem item2 = new CashierListItem("item 2", AssetsManager.getImageIcon("PaintIcon"));

            pnlProducts.add(item1);
            pnlProducts.add(item2);
        }

        pnlProducts.revalidate();
        pnlProducts.repaint();

        pnlProducts.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                syncProductsPanel(e.getComponent().getWidth(), e.getComponent().getHeight());
            }
        });
    }

    private void syncProductsPanel(int width, int height) {
        int numberOfComponentsCanBeMappedInARow = width / CashierListItem.defaultWidth;
        if(numberOfComponentsCanBeMappedInARow <= 0)
            numberOfComponentsCanBeMappedInARow = 1;
//        int numberOfComponentsInAColumn = height / CashierListItem.defaultHeight;
        int numberOfComponentsInAColumn = Math.round((float) pnlProducts.getComponents().length / numberOfComponentsCanBeMappedInARow);
        int totalHeight = numberOfComponentsInAColumn * (CashierListItem.defaultHeight + 8);

        Console.log("number of components can be mapped in row : " + numberOfComponentsCanBeMappedInARow);
        Console.log("number of components in a column : " + numberOfComponentsInAColumn);
        Console.log("total height : " + totalHeight);
        Console.log("pnlProducts height : " + pnlProducts.getWidth());

        pnlProducts.setPreferredSize(new Dimension(width - 20, totalHeight));
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

    private void initTabButtons() {
        btnAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectTab(ALL);
            }
        });

        btnProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectTab(PRODUCTS);
            }
        });

        btnServices.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectTab(SERVICES);
            }
        });
    }

    private <T> void println(T val){
        if(val instanceof String){
            System.out.println(val);
        }
        System.out.println(val.toString());
    }
}
