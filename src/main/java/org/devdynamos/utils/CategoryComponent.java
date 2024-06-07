package org.devdynamos.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CategoryComponent extends JPanel {

    private JLabel label;
    private ImageIcon icon;
    private String text;

    public CategoryComponent(String text, ImageIcon icon) {
        this.text = text;
        this.icon = icon;
        initializeComponent();
    }

    private void initializeComponent() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(200, 100));

        // Create the label with text and icon
        label = new JLabel(text, icon, JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.RIGHT);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));

        add(label, BorderLayout.CENTER);

        // Add mouse listener for click handling
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onCategoryClicked();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.LIGHT_GRAY);
            }
        });
    }

    private void onCategoryClicked() {
        JOptionPane.showMessageDialog(this, "Category " + text + " clicked!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Custom Category Component Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            // Example icons (replace with your own icons)
            ImageIcon icon1 = new ImageIcon("path/to/your/icon1.png");
            ImageIcon icon2 = new ImageIcon("path/to/your/icon2.png");

            // Create and add category components to the panel
            CategoryComponent category1 = new CategoryComponent("Category 1", icon1);
            CategoryComponent category2 = new CategoryComponent("Category 2", icon2);
            CategoryComponent category3 = new CategoryComponent("Category 3", icon1);
            CategoryComponent category4 = new CategoryComponent("Category 4", icon2);

            panel.add(category1);
            panel.add(category2);
            panel.add(category3);
            panel.add(category4);

            frame.add(panel);
            frame.setVisible(true);
        });
    }
}