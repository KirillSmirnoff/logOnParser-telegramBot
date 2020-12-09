package IO;

import java.awt.*;

public class LocalFileAction {
    public static Desktop getDesktop(){
        Desktop desktop = null;
        if (Desktop.isDesktopSupported())
            desktop = Desktop.getDesktop();
        return desktop;
    }
}
