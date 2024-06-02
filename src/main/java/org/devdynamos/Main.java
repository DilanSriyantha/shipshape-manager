package org.devdynamos;

import org.devdynamos.utils.AssetsManager;
import org.devdynamos.view.AllocateView;

public class Main {
    public static void main(String[] args) {
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/person_add.png"), "AllocateIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/person_remove.png"), "DeallocateIcon");

        AllocateView allocateView = new AllocateView();
        allocateView.show();
    }
}