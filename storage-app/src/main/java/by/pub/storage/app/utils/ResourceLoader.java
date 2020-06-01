package by.pub.storage.app.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class ResourceLoader {
    private ResourceLoader() {
    }

    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }
}
