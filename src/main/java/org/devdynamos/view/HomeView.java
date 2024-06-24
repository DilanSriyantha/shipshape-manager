package org.devdynamos.view;

import org.devdynamos.interfaces.DBConnectionListener;
import org.devdynamos.models.Customer;
import org.devdynamos.models.Supplier;
import org.devdynamos.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeView {
    private JPanel pnlRoot;
    private JLabel lblTitle;
    private JLabel lblDBConStatus;
    private JPanel pnlOrderManagement;
    private JPanel pnlSupplierManagement;
    private JPanel pnlInventoryManagement;
    private JPanel pnlEmployeeManagement;
    private JPanel pnlSalesReports;
    private JPanel pnlMail;
    private JPanel pnlServiceManagement;
    private JPanel pnlCustomerManagement;
    private RootView rootView;

    private final Color IDLE_COLOR = new Color(244, 244, 244);
    private final Color HOVER_COLOR = new Color(200, 200, 200, 255);
    private final Color CLICKED_COLOR = new Color(90, 149, 204);

    public HomeView(RootView rootView){
        this.rootView = rootView;

        initButtons();
        checkConnection();
    }

    private void initButtons() {
        pnlOrderManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnlOrderManagement.setBackground(CLICKED_COLOR);
                for (int i = 0; i < pnlOrderManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlOrderManagement.getComponents()[i].setBackground(CLICKED_COLOR);

                    for(Component child : ((JPanel)pnlOrderManagement.getComponents()[i]).getComponents()){
                        child.setBackground(CLICKED_COLOR);
                    }
                }

                rootView.navigate(NavPath.CASHIER_DASHBOARD, new CashierDashboard(rootView).getRootPanel());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pnlOrderManagement.setBackground(HOVER_COLOR);
                for (int i = 0; i < pnlOrderManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlOrderManagement.getComponents()[i].setBackground(HOVER_COLOR);

                    for(Component child : ((JPanel)pnlOrderManagement.getComponents()[i]).getComponents()){
                        child.setBackground(HOVER_COLOR);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pnlOrderManagement.setBackground(IDLE_COLOR);
                for (int i = 0; i < pnlOrderManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlOrderManagement.getComponents()[i].setBackground(IDLE_COLOR);

                    for(Component child : ((JPanel)pnlOrderManagement.getComponents()[i]).getComponents()){
                        child.setBackground(IDLE_COLOR);
                    }
                }
            }
        });

        pnlSupplierManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnlSupplierManagement.setBackground(CLICKED_COLOR);
                for (int i = 0; i < pnlSupplierManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlSupplierManagement.getComponents()[i].setBackground(CLICKED_COLOR);

                    for(Component child : ((JPanel)pnlSupplierManagement.getComponents()[i]).getComponents()){
                        child.setBackground(CLICKED_COLOR);
                    }
                }

                rootView.navigate(NavPath.SUPPLIERS, new SuppliersManagement(rootView).getRootPanel());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pnlSupplierManagement.setBackground(HOVER_COLOR);
                for (int i = 0; i < pnlSupplierManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlSupplierManagement.getComponents()[i].setBackground(HOVER_COLOR);

                    for(Component child : ((JPanel)pnlSupplierManagement.getComponents()[i]).getComponents()){
                        child.setBackground(HOVER_COLOR);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pnlSupplierManagement.setBackground(IDLE_COLOR);
                for (int i = 0; i < pnlSupplierManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlSupplierManagement.getComponents()[i].setBackground(IDLE_COLOR);

                    for(Component child : ((JPanel)pnlSupplierManagement.getComponents()[i]).getComponents()){
                        child.setBackground(IDLE_COLOR);
                    }
                }
            }
        });

        pnlInventoryManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnlInventoryManagement.setBackground(CLICKED_COLOR);
                for (int i = 0; i < pnlInventoryManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlInventoryManagement.getComponents()[i].setBackground(CLICKED_COLOR);

                    for(Component child : ((JPanel)pnlInventoryManagement.getComponents()[i]).getComponents()){
                        child.setBackground(CLICKED_COLOR);
                    }
                }

                rootView.navigate(NavPath.INVENTORY, new InventoryManagement(rootView).getRootPanel());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pnlInventoryManagement.setBackground(HOVER_COLOR);
                for (int i = 0; i < pnlInventoryManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlInventoryManagement.getComponents()[i].setBackground(HOVER_COLOR);

                    for(Component child : ((JPanel)pnlInventoryManagement.getComponents()[i]).getComponents()){
                        child.setBackground(HOVER_COLOR);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pnlInventoryManagement.setBackground(IDLE_COLOR);
                for (int i = 0; i < pnlInventoryManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlInventoryManagement.getComponents()[i].setBackground(IDLE_COLOR);

                    for(Component child : ((JPanel)pnlInventoryManagement.getComponents()[i]).getComponents()){
                        child.setBackground(IDLE_COLOR);
                    }
                }
            }
        });

        pnlEmployeeManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnlEmployeeManagement.setBackground(CLICKED_COLOR);
                for (int i = 0; i < pnlEmployeeManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlEmployeeManagement.getComponents()[i].setBackground(CLICKED_COLOR);

                    for(Component child : ((JPanel)pnlEmployeeManagement.getComponents()[i]).getComponents()){
                        child.setBackground(CLICKED_COLOR);
                    }
                }

                rootView.navigate(NavPath.EMPLOYEES, new EmployeesView(rootView).getRootPanel());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pnlEmployeeManagement.setBackground(HOVER_COLOR);
                for (int i = 0; i < pnlEmployeeManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlEmployeeManagement.getComponents()[i].setBackground(HOVER_COLOR);

                    for(Component child : ((JPanel)pnlEmployeeManagement.getComponents()[i]).getComponents()){
                        child.setBackground(HOVER_COLOR);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pnlEmployeeManagement.setBackground(IDLE_COLOR);
                for (int i = 0; i < pnlEmployeeManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlEmployeeManagement.getComponents()[i].setBackground(IDLE_COLOR);

                    for(Component child : ((JPanel)pnlEmployeeManagement.getComponents()[i]).getComponents()){
                        child.setBackground(IDLE_COLOR);
                    }
                }
            }
        });

        pnlServiceManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnlServiceManagement.setBackground(CLICKED_COLOR);
                for (int i = 0; i < pnlServiceManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlServiceManagement.getComponents()[i].setBackground(CLICKED_COLOR);

                    for(Component child : ((JPanel)pnlServiceManagement.getComponents()[i]).getComponents()){
                        child.setBackground(CLICKED_COLOR);
                    }
                }

                rootView.navigate(NavPath.SERVICES, new ServiceManagement(rootView).getRootPanel());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pnlServiceManagement.setBackground(HOVER_COLOR);
                for (int i = 0; i < pnlServiceManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlServiceManagement.getComponents()[i].setBackground(HOVER_COLOR);

                    for(Component child : ((JPanel)pnlServiceManagement.getComponents()[i]).getComponents()){
                        child.setBackground(HOVER_COLOR);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pnlServiceManagement.setBackground(IDLE_COLOR);
                for (int i = 0; i < pnlServiceManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlServiceManagement.getComponents()[i].setBackground(IDLE_COLOR);

                    for(Component child : ((JPanel)pnlServiceManagement.getComponents()[i]).getComponents()){
                        child.setBackground(IDLE_COLOR);
                    }
                }
            }
        });

        pnlCustomerManagement.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnlCustomerManagement.setBackground(CLICKED_COLOR);
                for (int i = 0; i < pnlCustomerManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlCustomerManagement.getComponents()[i].setBackground(CLICKED_COLOR);

                    for(Component child : ((JPanel)pnlCustomerManagement.getComponents()[i]).getComponents()){
                        child.setBackground(CLICKED_COLOR);
                    }
                }

                rootView.navigate(NavPath.CUSTOMERS, new CustomerManagement(rootView).getRootPanel());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pnlCustomerManagement.setBackground(HOVER_COLOR);
                for (int i = 0; i < pnlCustomerManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlCustomerManagement.getComponents()[i].setBackground(HOVER_COLOR);

                    for(Component child : ((JPanel)pnlCustomerManagement.getComponents()[i]).getComponents()){
                        child.setBackground(HOVER_COLOR);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pnlCustomerManagement.setBackground(IDLE_COLOR);
                for (int i = 0; i < pnlCustomerManagement.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlCustomerManagement.getComponents()[i].setBackground(IDLE_COLOR);

                    for(Component child : ((JPanel)pnlCustomerManagement.getComponents()[i]).getComponents()){
                        child.setBackground(IDLE_COLOR);
                    }
                }
            }
        });

        pnlSalesReports.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnlSalesReports.setBackground(CLICKED_COLOR);
                for (int i = 0; i < pnlSalesReports.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlSalesReports.getComponents()[i].setBackground(CLICKED_COLOR);

                    for(Component child : ((JPanel)pnlSalesReports.getComponents()[i]).getComponents()){
                        child.setBackground(CLICKED_COLOR);
                    }
                }

                rootView.navigate(NavPath.REPORTS, new GenerateReports(rootView).getRootPanel());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pnlSalesReports.setBackground(HOVER_COLOR);
                for (int i = 0; i < pnlSalesReports.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlSalesReports.getComponents()[i].setBackground(HOVER_COLOR);

                    for(Component child : ((JPanel)pnlSalesReports.getComponents()[i]).getComponents()){
                        child.setBackground(HOVER_COLOR);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pnlSalesReports.setBackground(IDLE_COLOR);
                for (int i = 0; i < pnlSalesReports.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlSalesReports.getComponents()[i].setBackground(IDLE_COLOR);

                    for(Component child : ((JPanel)pnlSalesReports.getComponents()[i]).getComponents()){
                        child.setBackground(IDLE_COLOR);
                    }
                }
            }
        });

        pnlMail.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pnlMail.setBackground(CLICKED_COLOR);
                for (int i = 0; i < pnlMail.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlMail.getComponents()[i].setBackground(CLICKED_COLOR);

                    for(Component child : ((JPanel)pnlMail.getComponents()[i]).getComponents()){
                        child.setBackground(CLICKED_COLOR);
                    }
                }

//                rootView.navigate(NavPath.MAIL, new Mail(rootView).getRootPanel());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pnlMail.setBackground(HOVER_COLOR);
                for (int i = 0; i < pnlMail.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlMail.getComponents()[i].setBackground(HOVER_COLOR);

                    for(Component child : ((JPanel)pnlMail.getComponents()[i]).getComponents()){
                        child.setBackground(HOVER_COLOR);
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pnlMail.setBackground(IDLE_COLOR);
                for (int i = 0; i < pnlMail.getComponents().length; i++) {
                    if(i == 0) continue;

                    pnlMail.getComponents()[i].setBackground(IDLE_COLOR);

                    for(Component child : ((JPanel)pnlMail.getComponents()[i]).getComponents()){
                        child.setBackground(IDLE_COLOR);
                    }
                }
            }
        });
    }

    private void checkConnection() {
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
