package project;

public final class StyleConstants {

    // ╔══════════════════╗
    // ║ BUTTONS          ║
    // ╚══════════════════╝


    public static final String BUTTON_ADD_CARD = """
        -fx-background-color: #4CAF50;
        -fx-text-fill: white;
    """;

    public static final String BUTTON_ADD_CARD_HOVER = """
        -fx-background-color: #45A049;
        -fx-text-fill: white;
    """;

    public static final String BUTTON_REMOVE_CARD = """
        -fx-background-color: #f44336;
        -fx-text-fill: white;
    """;

    public static final String BUTTON_REMOVE_CARD_HOVER = """
        -fx-background-color: #d32f2f;
        -fx-text-fill: white;
    """;

    public static final String BUTTON_SAVE_DECK = """
        -fx-background-color: #2196F3;
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-padding: 10 20 10 20;
        -fx-background-radius: 8;
    """;

    public static final String BUTTON_SAVE_DECK_HOVER = """
        -fx-background-color: #1976D2;
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-padding: 10 20 10 20;
        -fx-background-radius: 8;
    """;

    public static final String BUTTON_DELETE_DECK = """
        -fx-background-color: #c62828;
        -fx-text-fill: white;
        -fx-padding: 8 16;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_DELETE_DECK_HOVER = """
        -fx-background-color: #b71c1c;
        -fx-text-fill: white;
        -fx-padding: 8 16;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_EXPORT_DECK = """
        -fx-background-color: #1976D2;
        -fx-text-fill: white;
        -fx-padding: 8 16;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_EXPORT_DECK_HOVER = """
        -fx-background-color: #1565C0;
        -fx-text-fill: white;
        -fx-padding: 8 16;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_START = """
        -fx-text-fill: white; 
        -fx-font-size: 14px;
        -fx-background-color: #4CAF50;
        -fx-padding: 8px 16px;
    """;  
    
    public static final String BUTTON_START_HOVER = """
        -fx-text-fill: white; 
        -fx-font-size: 14px;
        -fx-background-color: #377a3a;
        -fx-padding: 8px 16px;
    """; 

    public static final String BUTTON_ADD_DECK_BOX = """
        -fx-background-color: white;
        -fx-background-radius: 10;
        -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);
    """;

    public static final String BUTTON_ADD_DECK_BOX_HOVER = """
        -fx-background-color: #f8f9fa;
        -fx-background-radius: 10;
        -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);
    """;

    public static final String BUTTON_OK_DIALOG = """
        -fx-background-color: #2196F3;
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-padding: 10 20;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_OK_DIALOG_HOVER = """
        -fx-background-color: #1976D2;
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-padding: 10 20;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_CANCEL_DIALOG = """
        -fx-background-color: #e0e0e0;
        -fx-text-fill: #333;
        -fx-font-size: 14px;
        -fx-padding: 10 20;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_CANCEL_DIALOG_HOVER = """
        -fx-background-color: #cfcfcf;
        -fx-text-fill: #333;
        -fx-font-size: 14px;
        -fx-padding: 10 20;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_DELETE_DIALOG = """
        -fx-background-color: #f44336;
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-padding: 8 20;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_DELETE_DIALOG_HOVER = """
        -fx-background-color: #d32f2f;
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-padding: 8 20;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_CANCEL_DELETE_DIALOG = """
        -fx-background-color: #e0e0e0;
        -fx-text-fill: #333;
        -fx-font-size: 14px;
        -fx-padding: 8 20;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_CANCEL_DELETE_DIALOG_HOVER = """
        -fx-background-color: #d5d5d5;
        -fx-text-fill: #333;
        -fx-font-size: 14px;
        -fx-padding: 8 20;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_CLEAR = """
        -fx-background-color: transparent;
        -fx-text-fill: #aaa;
        -fx-font-size: 12px;
        -fx-padding: 0 6;
        -fx-cursor: hand;
    """;

    public static final String BUTTON_SIDEBAR_DEFAULT = """
        -fx-text-fill: white;
        -fx-font-size: 16px;
        -fx-background-color: transparent;
        -fx-padding: 10px;
    """;

    public static final String BUTTON_SIDEBAR_SELECTED = """
        -fx-text-fill: white;
        -fx-font-size: 16px;
        -fx-background-color: #1e2c3b;
        -fx-padding: 10px;
    """;

    public static final String BUTTON_SIDEBAR_HOVER = """
        -fx-text-fill: white;
        -fx-font-size: 16px;
        -fx-background-color: #34495e;
        -fx-padding: 10px;
    """;

    public static final String BUTTON_OVERWRITE_DIALOG = """
        -fx-background-color: #2196F3;
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-padding: 10 20;
        -fx-background-radius: 6;
    """;

    public static final String BUTTON_OVERWRITE_DIALOG_HOVER = """
        -fx-background-color: #1976D2;
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-padding: 10 20;
        -fx-background-radius: 6;
    """;

    // public static final String BUTTON_CIRCLE_ICON = """
    //     -fx-background-color: white;
    //     -fx-background-radius: 50;
    //     -fx-border-color: #ddd;
    //     -fx-border-radius: 50;
    //     -fx-padding: 12;
    //     -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 2);
    // """;

    public static final String BUTTON_CIRCLE_ICON = """
        -fx-background-color: white;
        -fx-background-radius: 50;          /* Kreis */
        -fx-border-color: #d0d0d0;          /* etwas heller */
        -fx-border-width: 1;                /* schmaler Rand */
        -fx-border-radius: 50;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.04), 2, 0, 0, 1);
        -fx-content-display: graphic-only;  /* nur Icon, kein Text-Abstand */
    """;

    public static final String BUTTON_CIRCLE_ICON_HOVER = """
        -fx-background-color: #f7f7f7;
        -fx-background-radius: 50;
        -fx-border-color: #aaa;
        -fx-border-radius: 50;
        -fx-padding: 14;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 4, 0, 0, 2);
        -fx-content-display: graphic-only;
    """;

    public static final String BUTTON_BACK = """
        -fx-background-color: white;
        -fx-border-color: #ccc;
        -fx-border-radius: 6;
        -fx-background-radius: 6;
        -fx-padding: 8 12;
        -fx-font-size: 14px;
        -fx-cursor: hand;
        -fx-alignment: center;
    """;

    public static final String BUTTON_BACK_HOVER = """
        -fx-background-color: #f0f0f0;
        -fx-border-color: #bbb;
        -fx-border-radius: 6;
        -fx-background-radius: 6;
        -fx-padding: 8 12;
        -fx-font-size: 14px;
        -fx-cursor: hand;
        -fx-alignment: center;
    """;

    public static final String BUTTON_DIFFICULTY = """
        -fx-font-size: 16px;
        -fx-font-weight: bold;
        -fx-text-fill: white;
        -fx-padding: 8px 16px; 
        -fx-background-radius: 20px;
    """;

    public static final String BUTTON_DELETE_CARD = """
        -fx-background-color: #e57373;
        -fx-text-fill: white;
        -fx-background-radius: 20;
        -fx-padding: 6 16;
    """;

    public static final String BUTTON_DELETE_CARD_HOVER = """
        -fx-background-color: #ef5350;
        -fx-text-fill: white;
        -fx-background-radius: 20;
        -fx-padding: 6 16;
    """;


    ///////////////////////////////////////////////

    // ╔══════════════════╗
    // ║ DECK_BOX         ║
    // ╚══════════════════╝


    public static final String DECK_BOX = """
        -fx-background-color: white; 
        -fx-background-radius: 10; 
        -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);
    """;

    public static final String DECK_BOX_PINNED = """
        -fx-background-color: #f0f4f8;
        -fx-background-radius: 10;
        -fx-border-radius: 10;
        -fx-border-color: #3498db;
        -fx-border-width: 0 0 0 5; /* nur links farbiger Rand */
        -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);
    """;
    // MANAGEN
    public static final String DECK_BOX_DEFAULT = """
        -fx-background-color: white;
        -fx-background-radius: 10;
        -fx-border-color: #ddd;
        -fx-border-radius: 10;
    """;

    public static final String DECK_BOX_SELECTED = """
        -fx-background-color: #f0f0f0;
        -fx-background-radius: 10;
        -fx-border-color: #bbb;
        -fx-border-width: 2;
        -fx-border-radius: 10;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 6, 0, 0, 2);
    """;

    public static final String DECK_BOX_HOVER = """
        -fx-background-color: #f0f0f0;
        -fx-background-radius: 10;
        -fx-border-color: #bbb;
        -fx-border-radius: 10;
        -fx-cursor: hand;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 5, 0, 0, 2);
    """;


    public static final String DECK_BOX_NAME = "-fx-font-size: 16px; -fx-font-weight: bold;";
    public static final String DECK_BOX_COUNT = "-fx-font-size: 14px; -fx-text-fill: #666;";

    ///////////////////////


    // ╔══════════════════╗
    // ║ CARD_BOX         ║
    // ╚══════════════════╝


    public static final String CARD_BOX_EDIT = """
        -fx-background-color: white;
        -fx-background-radius: 10;
        -fx-border-radius: 10;
        -fx-border-color: #dcdcdc;
        -fx-border-width: 1;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.03), 3, 0, 0, 1);
        -fx-padding: 20;
    """;

    public static final String CARD_BOX_VIEW = """
        -fx-background-color: white;
        -fx-background-radius: 16;
        -fx-border-radius: 16;
        -fx-border-color: #ccc;
        -fx-border-width: 2;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.04), 6, 0, 0, 2);
        -fx-padding: 20;
    """;

    public static final String CARD_BOX_VIEW_HOVER = """
        -fx-background-color: #f0f0f0;
        -fx-background-radius: 16;
        -fx-border-radius: 16;
        -fx-border-color: #999;
        -fx-border-width: 2;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.04), 6, 0, 0, 2);
        -fx-padding: 20;
    """;


    //////////////////////////////////////


    // ╔══════════════════╗
    // ║ TEXTFIELD        ║
    // ╚══════════════════╝

    public static final String TEXTFIELD_HEADER = """
        -fx-font-size: 14px;
        -fx-padding: 8 10 8 10;
        -fx-background-color: #ffffff;
        -fx-border-color: #dcdcdc;
    """;

    public static final String TEXTFIELD_CONTENT = """
        -fx-background-color: #f9f9f9;
        -fx-border-color: #e0e0e0;
        -fx-border-radius: 6;
        -fx-background-radius: 6;
        -fx-padding: 8;
        -fx-font-size: 13px;
        -fx-focus-color: #2196F3;
        -fx-faint-focus-color: #2196F330;
    """;

    public static final String TEXTFIELD_DIALOG = """
        -fx-background-color: #f9f9f9;
        -fx-border-color: #ddd;
        -fx-border-radius: 6;
        -fx-background-radius: 6;
        -fx-padding: 10;
        -fx-font-size: 14px;
    """;

    public static final String TEXTFIELD_SEARCH = """
        -fx-background-color: transparent;
        -fx-border-color: transparent;
        -fx-padding: 6 6 6 6;
        -fx-font-size: 13px;
    """;

    public static final String TEXTFIELD_EDITABLE = """
        -fx-font-size: 18px;
        -fx-font-weight: bold;
        -fx-background-color: #f9f9f9;
        -fx-background-radius: 8;
        -fx-padding: 10;
        -fx-border-color: transparent;
    """;


    // ╔══════════════════╗
    // ║ LABEL            ║
    // ╚══════════════════╝

    public static final String LABEL_TITLE = "-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;";

    public static final String LABEL_SETTINGS = "-fx-font-weight: bold; -fx-font-size: 14px;";

    public static final String LABEL_SUBTITLE = "-fx-font-size: 18px; -fx-font-weight: bold;";

    public static final String LABEL_HEADER = "-fx-font-size: 16px; -fx-font-weight: bold;";

    public static final String LABEL_TRANSLATION = """
        -fx-font-size: 22px;
        -fx-text-fill: #444;
        -fx-text-alignment: center;
        -fx-alignment: center;
    """;

    public static final String LABEL_TERM = """
        -fx-font-size: 26px;
        -fx-font-weight: bold;
        -fx-text-alignment: center;
        -fx-alignment: center;
    """;

    public static final String LABEL_SIDEBAR = "-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;";

    //////////////////////////


    // ╔══════════════════╗
    // ║ NO THEME         ║
    // ╚══════════════════╝

    public static final String TRANSPARENT = """
        -fx-background-color: transparent;
        -fx-border-color: transparent;
        -fx-background-insets: 0;
        -fx-padding: 0;
    """;

    public static final String CONTAINER_SEARCH = """
        -fx-background-color: white;
        -fx-border-color: #ccc;
        -fx-border-radius: 8;
        -fx-background-radius: 8;
    """;

    public static final String BACKGROUND_DEFAULT = "-fx-background-color: #f5f5f5;";
    public static final String BACKGROUND_DARK = "-fx-background-color: #2c2c2c;";

    public static final String SCROLLBAR = "-fx-pref-width: 8px; -fx-background-color: transparent;";

    public static final String ICON_PLUS = """
        -fx-font-size: 32px;
        -fx-text-fill: #888;
    """;

    public static final String COMBOBOX = """
        -fx-background-color: white;
        -fx-border-color: #ccc;
        -fx-border-radius: 6;
        -fx-background-radius: 6;
        -fx-padding: 6 12;
        -fx-font-size: 13px;
    """;

    public static final String CHECKBOX = """
        -fx-padding: 5;
        -fx-font-size: 13px;
    """;

    public static final String SETTINGS_CONTAINER = """
        -fx-background-color: white;
        -fx-border-color: #ddd;
        -fx-border-radius: 6;
        -fx-background-radius: 6;
    """;

    public static final String ICON_PLUS_HOVER = """
        -fx-font-size: 32px;
        -fx-text-fill: #3498db;
    """;

    private StyleConstants() {} // verhindert Instanziierung

}
