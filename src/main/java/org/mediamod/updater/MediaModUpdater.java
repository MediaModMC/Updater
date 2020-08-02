package org.mediamod.updater;

import org.mediamod.updater.exceptions.UpdateFailedException;
import org.mediamod.updater.ui.core.UpdaterFrame;
import org.mediamod.updater.ui.panes.ErrorPane;
import org.mediamod.updater.ui.panes.SuccessPane;
import org.mediamod.updater.ui.panes.UpdatingMediaModPane;
import org.mediamod.updater.ui.panes.WaitingForMCPane;
import org.mediamod.updater.ui.theme.UpdaterTheme;
import org.mediamod.updater.update.UpdateHandler;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.net.URI;

public class MediaModUpdater {
    public static void main(String... args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        UpdaterFrame frame = new UpdaterFrame();
        frame.setBackground(UpdaterTheme.getBackgroundColor());

        UpdateHandler updateHandler = new UpdateHandler(String.join(" ", args));
        if (!updateHandler.logFile.exists()) {
            System.out.println(updateHandler.logFile.getParentFile());
            if (!updateHandler.logFile.getParentFile().mkdirs()) {
                frame.setContentPane(new ErrorPane("Failed to create logfile! Join our discord for help", "Join", () -> {
                    try {
                        Desktop.getDesktop().browse(new URI("https://discord.gg/mrPanbw"));
                    } catch (Exception ignored) {
                    }
                }));

                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                return;
            } else {
                if (!updateHandler.logFile.createNewFile()) {
                    frame.setContentPane(new ErrorPane("Failed to create logfile! Join our discord for help", "Join", () -> {
                        try {
                            Desktop.getDesktop().browse(new URI("https://discord.gg/mrPanbw"));
                        } catch (Exception ignored) {
                        }
                    }));

                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                    return;
                }
            }
        }

        PrintStream fileStream = new PrintStream(updateHandler.logFile);
        System.setOut(fileStream);
        System.setErr(fileStream);

        if (updateHandler.updateIsValid()) {
            frame.setContentPane(new WaitingForMCPane());

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            while (!updateHandler.isReady()) {
                Thread.sleep(500);
            }

            frame.setContentPane(new UpdatingMediaModPane());
            frame.invalidate();
            frame.revalidate();

            try {
                updateHandler.performUpdate();
                frame.setContentPane(new SuccessPane());
                frame.invalidate();
                frame.revalidate();

                updateHandler.logFile.deleteOnExit();
            } catch (UpdateFailedException e) {
                boolean restored = updateHandler.restore();

                frame.setContentPane(new ErrorPane("Failed to perform update: " + e.message + (restored ? ". All changes have been reverted" : " + Failed to revert changes") + ". Join our discord for help", "Export log + Join", () -> {
                    try {
                        Desktop.getDesktop().browse(new URI("https://discord.gg/mrPanbw"));
                        Desktop.getDesktop().open(updateHandler.logFile);
                    } catch (Exception ignored) {
                    }
                }));

                frame.invalidate();
                frame.revalidate();
            }
        } else {
            frame.setContentPane(new ErrorPane("Failed to find necessary files. You can download the update manually below:", "Download", () -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://mediamod.conorthedev.me"));
                } catch (Exception ignored) {
                }
            }));

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }
}
