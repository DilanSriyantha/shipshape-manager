package org.devdynamos.interfaces;

import javax.swing.*;

public interface DialogPositiveCallback <T> {
    void execute(JDialog dialog, T object);
}
