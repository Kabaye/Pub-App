package by.pub.bar.app.ui.utils;

import java.awt.Color;
import java.awt.Font;
import lombok.Getter;

public class WindowUtils {

    private static final int MAC_OS_X = 0;
    private static final int WINDOWS = 1;

    private static final Font[] HEADER_FONT = new Font[]{new Font("Serif", Font.PLAIN, 30),
        new Font("Serif", Font.PLAIN, 30)};
    private static final Font[] TEXT_FONT = new Font[]{new Font("Serif", Font.PLAIN, 20),
        new Font("Serif", Font.PLAIN, 20)};
    private static final Color MENU_BAR_COLOR = Color.GREEN;
    private static final int[] SCREEN_WIDTH = new int[]{1000, 1000};
    private static final int[] SCREEN_HEIGHT = new int[]{600, 600};

    private static final int sys;
    @Getter
    private static final Font headerFont;
    @Getter
    private static final Font textFont;
    @Getter
    private static final Color menuBarColor;
    @Getter
    private static final int screenWidth;
    @Getter
    private static final int screenHeight;
    @Getter
    private static final int authScreenWidth;
    @Getter
    private static final int authScreenHeight;


    static {
        if (System.getProperty("os.name").equals("Mac OS X")) {
            sys = MAC_OS_X;
        } else {
            sys = WINDOWS;
        }
        headerFont = HEADER_FONT[sys];
        textFont = TEXT_FONT[sys];
        menuBarColor = MENU_BAR_COLOR;
        screenWidth = SCREEN_WIDTH[sys];
        screenHeight = SCREEN_HEIGHT[sys];

        authScreenWidth = screenWidth / 4;
        authScreenHeight = screenHeight / 5;
    }

    private WindowUtils() {
    }


}
