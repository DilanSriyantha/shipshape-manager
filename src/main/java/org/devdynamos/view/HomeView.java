package org.devdynamos.view;

import org.devdynamos.utils.NavPath;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView {
    private JPanel pnlRoot;
    private JLabel lblTitle;
    private JButton btnOrders;
    private JButton btnEmployees;
    private JButton btnInventory;
    private JButton btnSuppliers;
    private JButton btnSalesReport;
    private RootView rootView;

    public HomeView(RootView rootView){
        this.rootView = rootView;

        btnEmployees.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.navigate(NavPath.EMPLOYEES, new EmployeesView(rootView).getRootPanel());
            }
        });

        btnOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.navigate(NavPath.CASHIER_DASHBOARD, new CashierDashboard(rootView).getRootPanel());
            }
        });

        btnInventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.navigate(NavPath.INVENTORY, new InventoryManagement(rootView).getRootPanel());
            }
        });

        btnSuppliers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.navigate(NavPath.SUPPLIERS, new SuppliersManagement(rootView).getRootPanel());
            }
        });

        btnSalesReport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void show() {
        JFrame frame = new JFrame("HomeView");
        frame.setContentPane(this.pnlRoot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getRootPanel() {
        return this.pnlRoot;
    }
}
