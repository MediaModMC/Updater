package org.mediamod.updater;

import org.mediamod.updater.ui.main.UpdaterFrame;
import org.mediamod.updater.ui.theme.UpdaterTheme;
import org.mediamod.updater.update.UpdateHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;

public class MediaModUpdater {
    private static File logFile;

    public static void main(String... args) throws Exception {
        UpdateHandler updateHandler = new UpdateHandler(String.join(" ", args));
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        UpdaterFrame frame = new UpdaterFrame();
        frame.setBackground(UpdaterTheme.getBackgroundColor());

        BufferedImage header = null;
        try {
            URL resource = MediaModUpdater.class.getResource("/header.png");
            header = ImageIO.read(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert header != null;
        JLabel headerLabel = new JLabel(new ImageIcon(header.getScaledInstance(330, 91, Image.SCALE_SMOOTH)));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        logFile = new File(updateHandler.mcDir, "mediamod/log/MEDIAMOD_UPDATE_LOG.txt");
        if (!logFile.exists()) {
            if (!(logFile.getParentFile().mkdir() && logFile.createNewFile())) {
                JLabel label = new JLabel();
                label.setText("An error occurred: failed to create logfile!");
                label.setForeground(UpdaterTheme.getTextColor());
                label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
                label.setAlignmentX(Component.CENTER_ALIGNMENT);

                JButton button = new JButton();
                button.setText("Join our Discord for support");
                button.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
                button.setUI(new BasicButtonUI());
                button.setBackground(UpdaterTheme.getPrimaryColor());
                button.setForeground(UpdaterTheme.getTextColor());
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.addActionListener((a) -> {
                    try {
                        Desktop.getDesktop().browse(new URI("https://discord.gg/mrPanbw"));
                    } catch (Exception ignored) {
                    }
                });

                frame.panel.add(Box.createVerticalGlue());
                frame.panel.add(headerLabel);
                frame.panel.add(Box.createRigidArea(new Dimension(0, 10)));
                frame.panel.add(label);
                frame.panel.add(Box.createRigidArea(new Dimension(0, 10)));
                frame.panel.add(button);
                frame.panel.add(Box.createRigidArea(new Dimension(0, 10)));
                frame.panel.add(Box.createVerticalGlue());

                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                return;
            }
        }

        PrintStream fileStream = new PrintStream(logFile);
        System.setOut(fileStream);

        if (updateHandler.updateIsValid()) {
            JLabel label = new JLabel();
            label.setText("Waiting for Minecraft to finish...");
            label.setForeground(UpdaterTheme.getTextColor());
            label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);

            frame.panel.add(Box.createVerticalGlue());
            frame.panel.add(headerLabel);
            frame.panel.add(Box.createRigidArea(new Dimension(0, 10)));
            frame.panel.add(label);
            frame.panel.add(Box.createVerticalGlue());

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            while (!updateHandler.isReady()) {
                Thread.sleep(500);
            }

            label.setText("Updating MediaMod...");

            if (updateHandler.performUpdate()) {
                JButton button = new JButton();
                button.setText("Close");
                button.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
                button.setUI(new BasicButtonUI());
                button.setBackground(UpdaterTheme.getPrimaryColor());
                button.setForeground(UpdaterTheme.getTextColor());
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.addActionListener((a) -> System.exit(0));

                label.setText("Successfully updated MediaMod!");
                frame.panel.remove(frame.panel.getComponents().length - 1);
                frame.panel.add(Box.createRigidArea(new Dimension(0, 10)));
                frame.panel.add(button);
                frame.panel.add(Box.createRigidArea(new Dimension(0, 10)));
                frame.panel.add(Box.createVerticalGlue());

                frame.panel.revalidate();
                frame.panel.repaint();

                new File("MEDIAMOD_LOG.txt").deleteOnExit();
            } else {
                JButton button = new JButton();
                button.setText("Export Log + Join");
                button.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
                button.setUI(new BasicButtonUI());
                button.setBackground(UpdaterTheme.getPrimaryColor());
                button.setForeground(UpdaterTheme.getTextColor());
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.addActionListener((a) -> {
                    try {
                        Desktop.getDesktop().open(new File(updateHandler.mcDir, "mediamod/log/MEDIAMOD_UPDATE_LOG.txt"));
                        Desktop.getDesktop().browse(new URI("https://discord.gg/mrPanbw"));
                    } catch (Exception ignored) {
                    }
                });

                label.setText(String.format("<html><body style=\"padding-left: 10px; padding-right: 10px; text-align: center;\">%s</body></html>", "An error occurred. Press 'Export Log' and join our discord server for support"));
                frame.panel.remove(frame.panel.getComponents().length - 1);
                frame.panel.add(Box.createRigidArea(new Dimension(0, 10)));
                frame.panel.add(button);
                frame.panel.add(Box.createRigidArea(new Dimension(0, 10)));
                frame.panel.add(Box.createVerticalGlue());

                frame.panel.revalidate();
                frame.panel.repaint();
            }
        } else {
            JLabel label = new JLabel();
            label.setText(String.format("<html><body style=\"padding-left: 10px; padding-right: 10px; text-align: center;\">%s</body></html>", "Failed to find necessary files for MediaMod Update! You can download the latest version below"));
            label.setForeground(UpdaterTheme.getTextColor());
            label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton button = new JButton();
            button.setText("Download");
            button.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
            button.setUI(new BasicButtonUI());
            button.setBackground(UpdaterTheme.getPrimaryColor());
            button.setForeground(UpdaterTheme.getTextColor());
            button.setAlignmentX(Component.CENTER_ALIGNMENT);

            button.addActionListener(e -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://mediamod.conorthedev.me"));
                } catch (Exception ignored) {
                }
            });

            frame.panel.add(Box.createVerticalGlue());
            frame.panel.add(headerLabel);
            frame.panel.add(Box.createRigidArea(new Dimension(0, 10)));
            frame.panel.add(label);
            frame.panel.add(Box.createRigidArea(new Dimension(0, 10)));
            frame.panel.add(button);
            frame.panel.add(Box.createRigidArea(new Dimension(0, 10)));
            frame.panel.add(Box.createVerticalGlue());

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
}
