package org.mediamod.updater.ui.panes;

import org.mediamod.updater.ui.core.UpdaterFrame;
import org.mediamod.updater.ui.theme.UpdaterTheme;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class SuccessPane extends UpdaterFrame.UpdaterPanel {
    public SuccessPane() {
        super();

        JLabel label = new JLabel();
        label.setText("Successfully updated MediaMod!");
        label.setForeground(UpdaterTheme.getTextColor());
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button = new JButton();
        button.setText("Close");
        button.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
        button.setUI(new BasicButtonUI());
        button.setBackground(UpdaterTheme.getPrimaryColor());
        button.setForeground(UpdaterTheme.getTextColor());
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener((a) -> System.exit(0));

        add(Box.createVerticalGlue());
        add(headerLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(label);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(button);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(Box.createVerticalGlue());
    }
}
