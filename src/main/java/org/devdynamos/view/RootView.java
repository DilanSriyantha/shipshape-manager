package org.devdynamos.view;

import org.devdynamos.utils.ArrayUtils;
import org.devdynamos.utils.GenericFactory;
import org.devdynamos.utils.NavPath;

import javax.swing.*;
import java.awt.*;

public class RootView {

    private JPanel pnlRoot;
    private JButton button1;
    CardLayout cl = (CardLayout)(this.pnlRoot.getLayout());

    NavPath ACTIVE_PATH = NavPath.NONE;

    public RootView() {

    }

    public void show() {
        JFrame frame = new JFrame("ShipShape Manager");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel homePanel = new HomeView(this).getRootPanel();
//        JPanel allocateEmployeePanel = new EmployeesView(this).getRootPanel();
//        JPanel cashierDashboard = new CashierDashboard(this).getRootPanel();
//        JPanel inventoryPanel = new InventoryManagement(this).getRootPanel();
//        JPanel suppliersPanel = new SuppliersManagement(this).getRootPanel();

        this.pnlRoot.add(homePanel, String.valueOf(NavPath.HOME));
//        this.pnlRoot.add(allocateEmployeePanel, String.valueOf(NavPath.EMPLOYEES));
//        this.pnlRoot.add(cashierDashboard, String.valueOf(NavPath.CASHIER_DASHBOARD));
//        this.pnlRoot.add(inventoryPanel, String.valueOf(NavPath.INVENTORY));
//        this.pnlRoot.add(suppliersPanel, String.valueOf(NavPath.SUPPLIERS));

        this.cl.show(this.pnlRoot, String.valueOf(NavPath.HOME));

        frame.setContentPane(this.pnlRoot);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void navigate(NavPath to){
        this.cl.show(this.pnlRoot, String.valueOf(to));
    }

    public void navigate(NavPath to, JPanel pnl){
        this.pnlRoot.add(pnl, String.valueOf(to));
        this.cl.show(this.pnlRoot, String.valueOf(to));
    }

    public void goBack(){
        this.cl.previous(this.pnlRoot);
        destroyLastPath();
    }

    public void destroyLastPath(){
        pnlRoot.remove(pnlRoot.getComponent(pnlRoot.getComponents().length - 1));
    }
}
