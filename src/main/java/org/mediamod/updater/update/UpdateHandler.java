package org.mediamod.updater.update;

import org.mediamod.updater.utils.Metadata;

import java.io.File;

public class UpdateHandler {
    public final File mcDir;
    public final File modJar;
    public final File updateJar;
    public final File lockFile;

    public UpdateHandler(String path) {
        modJar = new File(path);
        mcDir = new File(modJar.getAbsolutePath().substring(0, modJar.getAbsolutePath().lastIndexOf("mods")));
        updateJar = new File(mcDir, "mediamod/update.jar");
        lockFile = new File(mcDir, "mediamod/update.lock");
    }

    public boolean updateIsValid() {
        System.out.println("MediaMod Updater " + Metadata.VERSION);

        System.out.println("Mod Jar: " + modJar);
        System.out.println("MC Dir: " + mcDir);
        System.out.println("Update JAR: " + updateJar);
        System.out.println("Lock File: " + lockFile);

        if (mcDir.exists() && mcDir.isDirectory()) {
            boolean valid = updateJar.exists() && lockFile.exists();
            System.out.println(valid ? "Required files are available!" : "Failed to find required files");

            return valid;
        } else {
            System.out.println("Minecraft directory doesn't exist");
        }

        return false;
    }

    public boolean performUpdate() {
        System.out.println("Removing existing MediaMod: " + modJar.getAbsolutePath());

        if (modJar.delete()) {
            System.out.println("Removed existing MediaMod");
            if (updateJar.renameTo(new File(modJar.getParentFile(), "MediaMod.jar"))) {
                System.out.println("Replaced existing MediaMod with newer one!");
                if (lockFile.delete()) {
                    System.out.println("Deleted lockfile\nDone!");
                    return true;
                } else {
                    System.out.println("ERROR: Failed to delete lockfile!");
                }
            } else {
                System.out.println("ERROR: Failed to move updated jar!");
            }
        } else {
            System.out.println("ERROR: Failed to remove original modjar!");
        }

        return false;
    }

    public boolean isReady() {
        return modJar.renameTo(modJar) && lockFile.renameTo(lockFile);
    }
}
