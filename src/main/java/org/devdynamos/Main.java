package org.devdynamos;

import org.devdynamos.components.DatePicker;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.view.LoadingSpinner;
import org.devdynamos.view.RootView;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/person_add.png"), "PersonAddIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/person_remove.png"), "DeallocateIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/add.png"), "AddIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/edit.png"), "UpdateIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/delete.png"), "DeleteIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/delete_red.png"), "DeleteRedIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/delete_red_2.png"), "DeleteRedEnterIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/back_dark.png"), "BackIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/back.png"), "BackLightIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/next.png"), "NextIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/gear.png"), "GearIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/paint.png"), "PaintIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/repair.png"), "RepairIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/spinner.gif"), "LoadingSpinnerGif");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/connected.png"), "ConnectedIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/disconnected.png"), "DisconnectedIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/find_connection.png"), "SearchConnectionIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/merge.png"), "MergeIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/warning.png"), "WarningIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/service_management.png"), "ServiceIcon");

        RootView rootView = new RootView();
        rootView.show();
    }
}