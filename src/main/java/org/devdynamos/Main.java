package org.devdynamos;

import org.devdynamos.utils.ArrayUtils;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.DBManager;
import org.devdynamos.view.RootView;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/person_add.png"), "AllocateIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/person_remove.png"), "DeallocateIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/add.png"), "AddIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/edit.png"), "UpdateIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/delete.png"), "DeleteIcon");
        AssetsManager.loadImageIcon(Main.class.getClassLoader().getResource("images/back_dark.png"), "BackIcon");

        RootView rootView = new RootView();
        rootView.show();

//        DBManager.insertBatch("skills", new String[]{ "empId", "skillDescription" }, new Object[][]{
//                {1, "Java"},
//                {2, "C#"},
//                {3, "C++"}
//        });
    }
}