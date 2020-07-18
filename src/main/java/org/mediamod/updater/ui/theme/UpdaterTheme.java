package org.mediamod.updater.ui.theme;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class UpdaterTheme {
    public static ColorUIResource getTextColor() {
        return new ColorUIResource(Color.white);
    }

    public static ColorUIResource getBackgroundColor() {
        return new ColorUIResource(new Color(35, 35, 35));
    }

    public static ColorUIResource getPrimaryColor() {
        return new ColorUIResource(new Color(123, 136, 204));
    }
}
