package org.mediamod.updater.ui.panes;

import org.mediamod.updater.ui.core.UpdaterFrame;
import org.mediamod.updater.ui.theme.UpdaterTheme;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class ErrorPane extends UpdaterFrame.UpdaterPanel {
    public ErrorPane(String error, String buttonText, Runnable onButton) {
        super();

        JLabel label = new JLabel();
        label.setText(String.format("<html><body style=\"padding-left: 10px; padding-right: 10px; text-align: center;\">%s</body></html>", error));
        label.setForeground(UpdaterTheme.getTextColor());
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button = new JButton();
        button.setText(buttonText);
        button.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
        button.setUI(new BasicButtonUI());
        button.setBackground(UpdaterTheme.getPrimaryColor());
        button.setForeground(UpdaterTheme.getTextColor());
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener((a) -> onButton.run());

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
