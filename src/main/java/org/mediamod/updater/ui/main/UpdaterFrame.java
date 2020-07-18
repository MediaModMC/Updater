package org.mediamod.updater.ui.main;

import org.mediamod.updater.ui.theme.UpdaterTheme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class UpdaterFrame extends JFrame {
    public UpdaterPanel panel;

    public UpdaterFrame() {
        JDialog.setDefaultLookAndFeelDecorated(true);
        JFrame.setDefaultLookAndFeelDecorated(false);

        this.setTitle("MediaMod Updater");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(340, 250);

        panel = new UpdaterPanel();
        this.add(panel, BorderLayout.CENTER);

        try {
            URL resource = getClass().getResource("/mediamod.png");
            BufferedImage image = ImageIO.read(resource);
            setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class UpdaterPanel extends JPanel {
        public UpdaterPanel() {
            super();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(UpdaterTheme.getBackgroundColor());
        }
    }
}