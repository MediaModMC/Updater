package org.mediamod.updater.update;

import org.apache.commons.io.FileUtils;
import org.mediamod.updater.exceptions.UpdateFailedException;
import org.mediamod.updater.ui.panes.ErrorPane;
import org.mediamod.updater.utils.Metadata;

import java.awt.*;
import java.io.File;
import java.net.URI;

public class UpdateHandler {
    public final File mcDir;
    public final File modJar;
    public final File originalModJar;
    public final File updateJar;
    public final File lockFile;
    public final File logFile;
    public final File backupDir;
    public File backupJar;

    public UpdateHandler(String path) {
        modJar = new File(path);
        originalModJar = modJar;
        mcDir = new File(modJar.getAbsolutePath().substring(0, modJar.getAbsolutePath().lastIndexOf("mods")));
        updateJar = new File(mcDir, "mediamod/update.jar");
        lockFile = new File(mcDir, "mediamod/update.lock");
        backupDir = new File(mcDir, "mediamod/backup");

        logFile = new File(mcDir, "mediamod/log/MEDIAMOD_UPDATE_LOG.txt");
    }

    public boolean updateIsValid() {
        System.out.println("MediaMod Updater " + Metadata.VERSION);

        System.out.println("\n==== Paths ====\nMod Jar: " + modJar);
        System.out.println("MC Dir: " + mcDir);
        System.out.println("Update JAR: " + updateJar);
        System.out.println("Lock File: " + lockFile);

        if(modJar.exists()) {
            if(mcDir.exists()) {
                if(updateJar.exists()) {
                    return true;
                } else {
                    System.out.println("\nERROR: Update jar doesn't exist");
                }
            } else {
                System.out.println("\nERROR: Minecraft directory doesn't exist");
            }
        } else {
            System.out.println("\nERROR: Mod Jar doesn't exist");
        }

        return false;
    }

    public void performUpdate() throws UpdateFailedException {
        System.out.println("\n==== Starting Update ====");

        if(!backupDir.exists()) {
            if(!backupDir.mkdirs()) {
                throw new UpdateFailedException("Failed to create backup directory");
            } else {
                System.out.println("Made new backup directory");
            }
        } else {
            if(!clearBackup()) {
                throw new UpdateFailedException("Failed to clear backup directory");
            } else {
                System.out.println("Cleared backup directory");
            }
        }

        File dirToMove = modJar.getParentFile();
        backupJar = new File(backupDir, modJar.getName());

        System.out.println("Backing up existing MediaMod: " + modJar + " to " + backupJar.getAbsolutePath());

        if (modJar.renameTo(backupJar)) {
            System.out.println("Backed-up existing MediaMod");

            File newLoc = new File(dirToMove, "MediaMod.jar");
            if (updateJar.renameTo(newLoc)) {
                System.out.println("Moved update jar to " + newLoc.getAbsolutePath());

                if(lockFile.delete()) {
                    if(clearBackup() && backupDir.delete()) {
                        System.out.println("Deleted temporary files\n==== Done! ====");
                    } else {
                        throw new UpdateFailedException("Failed to delete backup directory");
                    }
                } else {
                    throw new UpdateFailedException("Failed to delete lockfile");
                }
            } else {
                throw new UpdateFailedException("Failed to move update jar");
            }
        } else {
            throw new UpdateFailedException("Failed to backup original mod jar");
        }
    }

    private boolean clearBackup() {
        boolean toReturn = false;

        for (File file : FileUtils.listFiles(backupDir, null, false)) {
            toReturn = file.delete();
        }

        return toReturn;
    }

    public boolean restore() {
        System.out.println("====\nRestoring...====\nbackupJar: " + backupJar.getAbsolutePath() + "\n originalModJar: " + originalModJar);
        if(backupJar.exists()) {
            if(backupJar.renameTo(originalModJar)) {
                System.out.println("==== Done ====");
            }
        }

        return false;
    }

    public boolean isReady() {
        return modJar.renameTo(modJar) && lockFile.renameTo(lockFile);
    }
}
