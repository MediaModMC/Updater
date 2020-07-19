package org.mediamod.updater.exceptions;

public class UpdateFailedException extends Exception {
    public final String message;

    public UpdateFailedException(String message) {
        this.message = message;

        System.out.println("\n==== ERROR ====\n" + message);
    }
}
