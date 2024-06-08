package org.devdynamos.components;

import org.devdynamos.interfaces.OrderItemRecordDeleteCallback;
import org.devdynamos.models.OrderItem;
import org.devdynamos.utils.AssetsManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrderItemRecord extends JPanel {
    private JPanel pnlContainer;
    private JLabel lblNumber;
    private JPanel pnlInfoContainer;
    private JLabel lblName;
    private JLabel lblUnitPrice;
    private JLabel lblTotal;
    private JPanel pnlActionsContainer;
    private JLabel lblRemove;
    private JSeparator separator;

    private final int number;
    private final String name;
    private final double unitPrice;
    private final int quantity;
    private final double total;

    private OrderItem orderItem;

    private OrderItemRecordDeleteCallback deleteCallback;

    public static int defaultHeight = 100;
    public static int defaultWidth = 300;

    public OrderItemRecord(int number, String name, double unitPrice, int quantity){
        this.number = number;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = (unitPrice * quantity);

        initComponent();
    }

    public OrderItemRecord(int number, OrderItem orderItem){
        this.number = number;
        this.name = orderItem.getName();
        this.unitPrice = orderItem.getUnitPrice();
        this.quantity = orderItem.getQuantity();
        this.total = orderItem.getTotal();

        initComponent();
    }

    public OrderItemRecord(int number, OrderItem orderItem, OrderItemRecordDeleteCallback deleteCallback){
        this.number = number;
        this.name = orderItem.getName();
        this.unitPrice = orderItem.getUnitPrice();
        this.quantity = orderItem.getQuantity();
        this.total = orderItem.getTotal();
        this.orderItem = orderItem;
        this.deleteCallback = deleteCallback;

        initComponent();
    }


    private void initComponent(){
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setBackground(new Color(213, 213, 213));
        setPreferredSize(new Dimension(defaultWidth, defaultHeight));

        pnlContainer = new JPanel();
        pnlContainer.setLayout(new GridLayout(1, 4));
        pnlContainer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        pnlContainer.setBackground(new Color(213, 213, 213));
        pnlContainer.setPreferredSize(new Dimension(defaultWidth, defaultHeight));

        lblNumber = new JLabel(String.valueOf(this.number), JLabel.LEADING);
        lblNumber.setHorizontalTextPosition(JLabel.LEADING);
        lblNumber.setVerticalTextPosition(JLabel.CENTER);
        lblNumber.setFont(new Font("Arial", Font.PLAIN, 16));

        pnlInfoContainer = new JPanel();
        pnlInfoContainer.setLayout(new GridLayout(2, 1));
        pnlInfoContainer.setBorder(BorderFactory.createEmptyBorder());
        pnlInfoContainer.setBackground(new Color(213, 213, 213));
        pnlInfoContainer.setPreferredSize(new Dimension(150, defaultHeight));

        lblName = new JLabel(String.valueOf(this.name));
        lblName.setHorizontalTextPosition(JLabel.CENTER);
        lblName.setHorizontalAlignment(JLabel.CENTER);
        lblName.setVerticalTextPosition(JLabel.BOTTOM);
        lblName.setVerticalAlignment(JLabel.BOTTOM);
        lblName.setFont(new Font("Arial", Font.PLAIN, 16));

        lblUnitPrice = new JLabel(this.quantity + " x " + this.unitPrice);
        lblUnitPrice.setHorizontalTextPosition(JLabel.CENTER);
        lblUnitPrice.setHorizontalAlignment(JLabel.CENTER);
        lblUnitPrice.setVerticalTextPosition(JLabel.CENTER);
        lblUnitPrice.setFont(new Font("Arial", Font.PLAIN, 16));

        pnlInfoContainer.add(lblName, BorderLayout.NORTH);
        pnlInfoContainer.add(lblUnitPrice, BorderLayout.CENTER);

        lblTotal = new JLabel(String.valueOf(this.total));
        lblTotal.setHorizontalTextPosition(JLabel.TRAILING);
        lblTotal.setHorizontalAlignment(JLabel.TRAILING);
        lblTotal.setVerticalTextPosition(JLabel.CENTER);
        lblTotal.setFont(new Font("Arial", Font.PLAIN, 16));

        pnlActionsContainer = new JPanel();
        pnlActionsContainer.setLayout(new GridLayout(1, 1));
        pnlActionsContainer.setBorder(BorderFactory.createEmptyBorder());
        pnlActionsContainer.setBackground(new Color(213, 213, 213));
        pnlActionsContainer.setPreferredSize(new Dimension(48, 48));

        lblRemove = new JLabel("");
        lblRemove.setIcon(AssetsManager.getImageIcon("DeleteRedIcon"));
        lblRemove.setBackground(new Color(220, 76, 100));
        lblRemove.setHorizontalTextPosition(JLabel.CENTER);
        lblRemove.setHorizontalAlignment(JLabel.CENTER);

        lblRemove.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(deleteCallback == null) return;
                deleteCallback.execute(orderItem);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lblRemove.setIcon(AssetsManager.getImageIcon("DeleteRedEnterIcon"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblRemove.setIcon(AssetsManager.getImageIcon("DeleteRedIcon"));
            }
        });

        pnlActionsContainer.add(lblRemove);

        pnlContainer.add(lblNumber);
        pnlContainer.add(pnlInfoContainer);
        pnlContainer.add(lblTotal);
        pnlContainer.add(pnlActionsContainer);

        add(pnlContainer, BorderLayout.CENTER);
    }
}
