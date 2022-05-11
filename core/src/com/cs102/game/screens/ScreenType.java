package com.cs102.game.screens;

public enum ScreenType{
    GAME(Screen.class),
    MENU(HomePage.class),
    LOADING(LoadingScreen.class),
    OPTIONS(SettingsScreen.class),
    ABOUT(AboutScreen.class),

    GAMEOVER(GameOver.class),

    WIN(WinScreen.class);

    private Class<? extends com.badlogic.gdx.Screen> screenClass;

    ScreenType(Class<? extends com.badlogic.gdx.Screen> screenClass) {
        this.screenClass = screenClass;
    }

    public Class<? extends com.badlogic.gdx.Screen> getScreenClass() {
        return screenClass;
    }
}


