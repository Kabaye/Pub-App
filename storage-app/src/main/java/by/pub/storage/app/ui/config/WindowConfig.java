package by.pub.storage.app.ui.config;

import lombok.Getter;

import java.awt.Color;
import java.awt.Font;

public class WindowConfig {

    private static final int MAC_OS_X = 0;
    private static final int WINDOWS = 1;

    private static final Font[] HEADER_FONT = new Font[]{new Font("Serif", Font.PLAIN, 30),
            new Font("Serif", Font.PLAIN, 30)};
    private static final Font[] TEXT_FONT = new Font[]{new Font("Serif", Font.PLAIN, 20),
            new Font("Serif", Font.PLAIN, 20)};
    private static final Color MENU_BAR_COLOR = Color.GREEN;
    private static final Color UPPER_PANEL_COLOR = Color.GREEN;
    private static final int[] SCREEN_WIDTH = new int[]{1000, 1500};
    private static final int[] SCREEN_HEIGHT = new int[]{600, 900};
    private static final Font[] DIALOG_HEADER_FONT = {new Font("Serif", Font.PLAIN, 15),
            new Font("Serif", Font.PLAIN, 15)};
    private static final int[] DIALOG_SCREEN_WIDTH = {250, 350};
    private static final int[] DIALOG_SCREEN_HEIGHT = {100, 150};

    private static final int sys;
    @Getter
    private static final Font headerFont;
    @Getter
    private static final Font textFont;
    @Getter
    private static final Color menuBarColor;
    @Getter
    private static final Color upperPanelColor;
    @Getter
    private static final int screenWidth;
    @Getter
    private static final int screenHeight;
    @Getter
    private static final int authScreenWidth;
    @Getter
    private static final int authScreenHeight;
    @Getter
    private static final Font dialogHeaderFont;
    @Getter
    private static final int dialogScreenWidth;
    @Getter
    private static final int dialogScreenHeight;

    static {
        if (System.getProperty("os.name").equals("Mac OS X")) {
            sys = MAC_OS_X;
        } else {
            sys = WINDOWS;
        }
        headerFont = HEADER_FONT[sys];
        textFont = TEXT_FONT[sys];
        menuBarColor = MENU_BAR_COLOR;
        upperPanelColor = UPPER_PANEL_COLOR;
        screenWidth = SCREEN_WIDTH[sys];
        screenHeight = SCREEN_HEIGHT[sys];
        dialogHeaderFont = DIALOG_HEADER_FONT[sys];
        dialogScreenWidth = DIALOG_SCREEN_WIDTH[sys];
        dialogScreenHeight = DIALOG_SCREEN_HEIGHT[sys];

        authScreenWidth = screenWidth / 4;
        authScreenHeight = screenHeight / 5;
    }

    private WindowConfig() {
    }


}
