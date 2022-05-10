package com.cs102.game;

public class SaveClass {
    private final LastRemaindersOfThePandemic mainGame;

    public SaveClass(LastRemaindersOfThePandemic mainGame) {
        this.mainGame = mainGame;
    }
    public static void save() {

    }

    /*
    public void savePosition(float currentX, float currentY) {
        float tempX = mainGame.getPreferences().getPrefs().getFloat("x");
        tempX = currentX;

        float tempY = mainGame.getPreferences().getPrefs().getFloat("y");
        tempY = currentY;

        mainGame.getPreferences().getPrefs().putFloat("x", tempX);
        mainGame.getPreferences().getPrefs().putFloat("y", tempY);

        mainGame.getPreferences().getPrefs().flush();
    }
    */
}
