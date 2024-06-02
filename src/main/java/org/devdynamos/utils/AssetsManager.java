package org.devdynamos.utils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;

public class AssetsManager {
    private static final HashMap<String, ImageIcon> imageIconsMap = new HashMap<String, ImageIcon>();

    public static void loadImageIcon(URL url, String description) {
        try {
            Image image = new ImageIcon(url, description).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            imageIconsMap.put(description, new ImageIcon(image));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ImageIcon getImageIcon(String imageDescription) {
        return imageIconsMap.get(imageDescription);
    }
}
