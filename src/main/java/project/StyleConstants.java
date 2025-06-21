package project;

public final class StyleConstants {

    public static final String CARD_ADD_BUTTON = """
        -fx-background-color: #4CAF50;
        -fx-text-fill: white;
    """;

    public static final String CARD_ADD_BUTTON_HOVER = """
        -fx-background-color: #45A049;
        -fx-text-fill: white;
    """;

    public static final String CARD_REMOVE_BUTTON = """
        -fx-background-color: #f44336;
        -fx-text-fill: white;
    """;

    public static final String CARD_REMOVE_BUTTON_HOVER = """
        -fx-background-color: #d32f2f;
        -fx-text-fill: white;
    """;

    public static final String CONTENT_TEXTFIELD = """
        -fx-background-color: #f9f9f9;
        -fx-border-color: #e0e0e0;
        -fx-border-radius: 6;
        -fx-background-radius: 6;
        -fx-padding: 8;
        -fx-font-size: 13px;
        -fx-focus-color: #2196F3;
        -fx-faint-focus-color: #2196F330;
    """;

    public static final String DECK_SAVE_BUTTON = """
        -fx-background-color: #2196F3;
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-padding: 10 20 10 20;
        -fx-background-radius: 8;
    """;

    public static final String DECK_SAVE_BUTTON_HOVER = """
        -fx-background-color: #1976D2;
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-padding: 10 20 10 20;
        -fx-background-radius: 8;
    """;
    public static final String TRANSPARENT = """
            -fx-background-color: transparent;
            -fx-border-color: transparent;
            -fx-background-insets: 0;
            -fx-padding: 0;
        """;
    public static final String DECK_TITLE_FIELD = """
            -fx-font-size: 14px;
            -fx-padding: 8 10 8 10;
            -fx-background-color: #ffffff;
            -fx-border-color: #dcdcdc;
        """;
    public static final String CARD_ROW = """
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
                -fx-background-radius: 10; 
                -fx-border-radius: 10;
                -fx-border-color: #ddd;
                -fx-border-width: 2px;
                -fx-padding: 30px;
                """;

    public static final String START_BUTTON = """
            -fx-text-fill: white; 
            -fx-font-size: 14px;
            -fx-background-color: #4CAF50;
            -fx-padding: 8px 16px;
            """;  
    
    public static final String START_BUTTON_HOVER = """
            -fx-text-fill: white; 
            -fx-font-size: 14px;
            -fx-background-color: #377a3a;
            -fx-padding: 8px 16px;
            """;  

    public static final String DECK_BOX = """
            -fx-background-color: white; 
            -fx-background-radius: 10; 
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);
            """;

    public static final String ADD_DECK_BOX_BUTTTON = """
            -fx-background-color: white;
            -fx-background-radius: 10;
            -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);
            """;
    public static final String ADD_DECK_BOX_BUTTTON_HOVER = """
            -fx-background-color: #f8f9fa;
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

    public static final String DECK_BOX_NAME = "-fx-font-size: 16px; -fx-font-weight: bold;";
    public static final String DECK_BOX_COUNT = "-fx-font-size: 14px; -fx-text-fill: #666;";

    public static final String TITLE = "-fx-font-size: 24px; -fx-font-weight: bold;";
    public static final String SCROLLBAR = "-fx-pref-width: 8px; -fx-background-color: transparent;";

    private StyleConstants() {} // verhindert Instanziierung

}
