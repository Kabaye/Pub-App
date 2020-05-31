package by.pub.client.app.ui.utils;

import java.awt.Color;
import java.awt.Font;
import lombok.Getter;

public class WindowUtils {

    private static final int MAC_OS_X = 0;
    private static final int WINDOWS = 1;

    //main window
    private static final Font[] HEADER_FONT = new Font[]{new Font("Serif", Font.PLAIN, 30),
        new Font("Serif", Font.PLAIN, 30)};
    private static final Font[] TEXT_FONT = new Font[]{new Font("Serif", Font.PLAIN, 20),
        new Font("Serif", Font.PLAIN, 20)};
    private static final Color MENU_BAR_COLOR = Color.CYAN;
    private static final int[] SCREEN_WIDTH = new int[]{800, 800};
    private static final int[] SCREEN_HEIGHT = new int[]{600, 600};
    //order info dialog
    private static final Font[] INFO_DIALOG_HEADER_FONT = new Font[]{
        new Font("Serif", Font.PLAIN, 20), new Font("Serif", Font.PLAIN, 20)
    };
    private static final Font[] INFO_DIALOG_TEXT_FONT = new Font[]{
        new Font("Serif", Font.PLAIN, 15), new Font("Serif", Font.PLAIN, 15)
    };
    private static final int[] INFO_DIALOG_SCREEN_WIDTH = {400, 400};
    private static final int[] INFO_DIALOG_SCREEN_HEIGHT = {200, 200};

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
    @Getter
    private static final Font infoDialogHeaderFont;
    @Getter
    private static final Font infoDialogTextFontFont;
    @Getter
    private static final int infoDialogScreenWidth;
    @Getter
    private static final int infoDialogScreenHeight;


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
        infoDialogHeaderFont = INFO_DIALOG_HEADER_FONT[sys];
        infoDialogTextFontFont = INFO_DIALOG_TEXT_FONT[sys];
        infoDialogScreenWidth = INFO_DIALOG_SCREEN_WIDTH[sys];
        infoDialogScreenHeight = INFO_DIALOG_SCREEN_HEIGHT[sys];

        authScreenWidth = screenWidth / 4;
        authScreenHeight = screenHeight / 7;
    }

    private WindowUtils() {
    }


}

