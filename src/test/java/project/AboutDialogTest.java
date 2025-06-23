    package project;

    import javafx.application.Platform;
    import org.junit.jupiter.api.Test;

    import static org.junit.jupiter.api.Assertions.*;


    public class AboutDialogTest {

        @Test
        public void testAboutDialogDoesNotThrow() {
            // Da JavaFX UI-Komponenten im JavaFX Application Thread laufen müssen:
            Platform.startup(() -> {
                try {
                    AboutDialog.show();  // Sollte ohne Exception geöffnet und geschlossen werden
                    // Wenn wir hier sind: Test ist "bestanden"
                } catch (Exception e) {
                    fail("AboutDialog.show() should not throw exception, but threw: " + e.getMessage());
                }

                // Beendet das JavaFX Toolkit nach dem Test (wichtig bei mehreren Tests)
                Platform.exit();
            });
        }
        
    }
