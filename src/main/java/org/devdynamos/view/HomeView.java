package org.devdynamos.view;

import org.devdynamos.interfaces.DBConnectionListener;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.DBManager;
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
    private JLabel lblDBConStatus;
    private RootView rootView;

    public HomeView(RootView rootView){
        this.rootView = rootView;

        DBManager.addConnectionListener(new DBConnectionListener() {
            @Override
            public void onConnect() {
                lblDBConStatus.setIcon(AssetsManager.getImageIcon("ConnectedIcon"));
                lblDBConStatus.setText("Database connected");
            }

            @Override
            public void onDisconnect() {
                lblDBConStatus.setIcon(AssetsManager.getImageIcon("DisconnectedIcon"));
                lblDBConStatus.setText("Database disconnected");
            }
        });

        tryToReconnectDB();

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

    private void tryToReconnectDB() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(DBManager.getConnection() != null) continue;
                    DBManager.establishConnection("localhost", 4000, "shipshape", "root", "");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        t.start();
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
