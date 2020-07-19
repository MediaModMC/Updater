package org.mediamod.updater.ui.core;

import org.mediamod.updater.MediaModUpdater;
import org.mediamod.updater.ui.theme.UpdaterTheme;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class UpdaterFrame extends JFrame {
    public UpdaterPanel pane;

    public UpdaterFrame() {
        JDialog.setDefaultLookAndFeelDecorated(true);
        JFrame.setDefaultLookAndFeelDecorated(false);

        this.setTitle("MediaMod Updater");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(340, 250);

        pane = new UpdaterPanel();
        this.setContentPane(pane);

        try {
            URL resource = getClass().getResource("/mediamod.png");
            BufferedImage image = ImageIO.read(resource);
            setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class UpdaterPanel extends JPanel {
        public JLabel headerLabel;

        public UpdaterPanel() {
            super();
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(UpdaterTheme.getBackgroundColor());

            BufferedImage header = null;
            try {
                URL resource = MediaModUpdater.class.getResource("/header.png");
                header = ImageIO.read(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert header != null;
            headerLabel = new JLabel(new ImageIcon(header.getScaledInstance(330, 91, Image.SCALE_SMOOTH)));
            headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
    }
}