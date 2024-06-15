package org.devdynamos.view;

import org.devdynamos.components.CashierListItem;
import org.devdynamos.components.OrderItemRecord;
import org.devdynamos.contollers.CashierDashboardController;
import org.devdynamos.interfaces.GetProductsAndServicesCallback;
import org.devdynamos.models.OrderItem;
import org.devdynamos.models.Service;
import org.devdynamos.models.SparePart;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.Console;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

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
    private JTextField textField1;
    private JButton searchButton;
    private JButton btnAddDiscount;
    private JButton btnVat;
    private JLabel lblDiscountRate;
    private JLabel lblVatRate;
    private JLabel lblServiceChargeRate;
    private JButton btnAddServiceCharge;

    private final int ALL = 0;
    private final int PRODUCTS = 1;
    private final int SERVICES = 2;
    private final Color SELECTED_COLOR = new Color(138, 178, 215);
    private final Color IDLE_COLOR = new Color(184, 207, 229);
    private int selectedTab = ALL;
    private Dimension orderItemRecordsListSize;
    private RootView rootView;
    private final CashierDashboardController cashierDashboardController;
    private List<SparePart> sparePartList;
    private List<Service> serviceList;

    public CashierDashboard(RootView rootView){
        this.rootView = rootView;
        this.cashierDashboardController = new CashierDashboardController();

        loadProductsAndServices();
        renderOrderItemRecords();
        initBackButton();
        initAddCustomerButton();
        initTabButtons();
        registerKeyboardShortcuts();
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

    private void loadProductsAndServices() {
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Loading data...");

        cashierDashboardController.getProductsAndServices(new GetProductsAndServicesCallback() {
            @Override
            public void onSuccess(CashierDashboardController.GetProductsAndServicesResultSet resultSet) {
                Console.log(resultSet.getServiceList().size());
                Console.log(resultSet.getSparePartList().size());
                sparePartList = resultSet.getSparePartList();
                serviceList = resultSet.getServiceList();

                loadingSpinner.stop();
                renderQuickItemsPanel();
            }

            @Override
            public void onFailed(Exception ex) {
                loadingSpinner.stop();
                ex.printStackTrace();
            }
        });
    }

    private void renderQuickItemsPanel() {
        if(sparePartList == null || serviceList == null) return;

        pnlProducts.removeAll();

        if(selectedTab == ALL){
            // render services
            for(Service service : serviceList){
                pnlProducts.add(
                        new CashierListItem(
                                service,
                                AssetsManager.getImageIcon("ServiceIcon"),
                                this::addServiceRecord
                        )
                );
            }

            // render products
            for(SparePart part : sparePartList){
                pnlProducts.add(
                        new CashierListItem(
                                part,
                                AssetsManager.getImageIcon("GearIcon"),
                                this::addItemRecord
                        )
                );
            }
        }

        if(selectedTab == PRODUCTS){
            // render products
            for(SparePart part : sparePartList){
                pnlProducts.add(
                        new CashierListItem(
                                part,
                                AssetsManager.getImageIcon("GearIcon"),
                                this::addItemRecord
                        )
                );
            }
        }

        if(selectedTab == SERVICES){
            // render services
            for(Service service : serviceList){
                pnlProducts.add(
                        new CashierListItem(
                                service,
                                AssetsManager.getImageIcon("ServiceIcon"),
                                this::addServiceRecord
                        )
                );
            }
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

    private void addItemRecord(SparePart sparePart) {
        AddInvoiceRecordInputDialog addInvoiceRecordInputDialog = new AddInvoiceRecordInputDialog(
                (dialog, qty) -> {
                    cashierDashboardController.addRecord(
                            new OrderItem(
                                    (int) (Math.random() * 100),
                                    1,
                                    sparePart.getName(),
                                    sparePart.getSellingPrice(),
                                    qty,
                                    sparePart.getSellingPrice() * qty
                            )
                    );
                    renderOrderItemRecords();
                    dialog.dispose();
                },
                Window::dispose
        );
        addInvoiceRecordInputDialog.showDialog();
    }

    private void addServiceRecord(Service service) {
        AddInvoiceRecordInputDialog addInvoiceRecordInputDialog = new AddInvoiceRecordInputDialog(
                (dialog, qty) -> {
                    cashierDashboardController.addRecord(
                            new OrderItem(
                                    (int) (Math.random() * 100),
                                    1,
                                    service.getServiceName(),
                                    service.getUnitPrice(),
                                    qty,
                                    service.getUnitPrice() * qty
                            )
                    );
                    renderOrderItemRecords();
                    dialog.dispose();
                },
                Window::dispose
        );
        addInvoiceRecordInputDialog.showDialog();
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

    private void registerKeyboardShortcuts() {
        pnlRoot.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceedPayment();
            }
        }, KeyStroke.getKeyStroke("control ENTER"), JComponent.WHEN_IN_FOCUSED_WINDOW);

        pnlRoot.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceedAddCustomer();
            }
        }, KeyStroke.getKeyStroke("control shift C"), JComponent.WHEN_IN_FOCUSED_WINDOW);

        pnlRoot.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceedAddDiscount();
            }
        }, KeyStroke.getKeyStroke("control shift D"), JComponent.WHEN_IN_FOCUSED_WINDOW);

        pnlRoot.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceedAddVat();
            }
        }, KeyStroke.getKeyStroke("control shift V"), JComponent.WHEN_IN_FOCUSED_WINDOW);

        pnlRoot.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceedAddServiceCharge();
            }
        }, KeyStroke.getKeyStroke("control shift E"), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void proceedPayment() {
        Console.log("proceed payment triggered");
    }

    private void proceedAddCustomer() {
        Console.log("proceed add customer triggered");
    }

    private void proceedAddDiscount() {
        AddDiscountInputDialog addDiscountInputDialog = new AddDiscountInputDialog(
                (dialog, discountRate) -> {
                    updateDiscount();
                    dialog.dispose();
                },
                Window::dispose
        );
        addDiscountInputDialog.showDialog();
    }

    private void proceedAddVat() {
        AddVatRateInputDialog addVatRateInputDialog = new AddVatRateInputDialog(
                (dialog, vatRate) -> {
                    updateVatRate();
                    dialog.dispose();
                },
                Window::dispose
        );
        addVatRateInputDialog.showDialog();
    }

    private void proceedAddServiceCharge() {
        AddServiceChargeRateInputDialog addServiceChargeRateInputDialog = new AddServiceChargeRateInputDialog(
                (dialog, serviceChargeRate) -> {
                    updateServiceCharge();
                    dialog.dispose();
                },
                Window::dispose
        );
        addServiceChargeRateInputDialog.showDialog();
    }

    private void updateDiscount() {

    }

    private void updateVatRate() {

    }

    private void updateServiceCharge() {

    }
}
