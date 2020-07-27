package org.mediamod.updater.ui.panes;

import org.mediamod.updater.ui.core.UpdaterFrame;
import org.mediamod.updater.ui.theme.UpdaterTheme;

import javax.swing.*;
import java.awt.*;

public class UpdatingMediaModPane extends UpdaterFrame.UpdaterPanel {
    public UpdatingMediaModPane() {
        super();

        JLabel label = new JLabel();
        label.setText("Updating MediaMod...");
        label.setForeground(UpdaterTheme.getTextColor());
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(headerLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(label);
        add(Box.createVerticalGlue());
    }
}
