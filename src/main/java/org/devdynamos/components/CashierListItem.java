package org.devdynamos.components;

import org.devdynamos.interfaces.CashierListItemOnClickCallback;
import org.devdynamos.models.Service;
import org.devdynamos.models.SparePart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CashierListItem extends JPanel {
    private JLabel label;
    private String text;
    private ImageIcon icon;

    private SparePart sparePart;
    private Service service;

    private CashierListItemOnClickCallback onClickCallback;

    public static int defaultHeight = 100;
    public static int defaultWidth = 100;

    public CashierListItem(String text, ImageIcon icon){
        this.text = text;
        this.icon = icon;

        initComponent();
    }

    public CashierListItem(Service service, ImageIcon icon, CashierListItemOnClickCallback<Service> onClickCallback){
        this.text = service.getServiceName();
        this.icon = icon;
        this.service = service;
        this.onClickCallback = onClickCallback;

        initComponent();
    }

    public CashierListItem(SparePart sparePart, ImageIcon icon){
        this.text = sparePart.getName();
        this.icon = icon;
        this.sparePart = sparePart;

        initComponent();
    }

    public CashierListItem(SparePart sparePart, ImageIcon icon, CashierListItemOnClickCallback<SparePart> onClickCallback){
        this.text = sparePart.getName();
        this.icon = icon;
        this.sparePart = sparePart;
        this.onClickCallback = onClickCallback;

        initComponent();
    }

    private void initComponent() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(new Color(213, 213, 213), 2));
        setBackground(new Color(213, 213, 213));
        setPreferredSize(new Dimension(defaultWidth, defaultHeight));

        // create the label with the text
        label = new JLabel("<html>" + text + "</html>", icon, JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        add(label, BorderLayout.CENTER);

        setToolTipText(this.text);

        // create mouse click action listener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onItemClicked();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(213, 213, 213));
            }
        });
    }

    private void onItemClicked() {
        if(this.onClickCallback == null) return;

        if(this.sparePart != null){
            onClickCallback.execute(sparePart);
        }else{
            onClickCallback.execute(service);
        }
    }
}
