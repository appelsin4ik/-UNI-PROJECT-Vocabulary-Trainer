package project;

public class AppSettings {
    private boolean generateDefaultDecks = true;

    public boolean isGenerateDefaultDecks() {
        return generateDefaultDecks;
    }

    public void setGenerateDefaultDecks(boolean generateDefaultDecks) {
        this.generateDefaultDecks = generateDefaultDecks;
    }
}