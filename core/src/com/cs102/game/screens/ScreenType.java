package com.cs102.game.screens;

import com.badlogic.gdx.Screen;

public enum ScreenType{
    GAME(GameScreen.class),
    MENU(HomePage.class),
    LOADING(LoadingScreen.class),
    OPTIONS(SettingsScreen.class);


    private Class<? extends Screen> screenClass;

    ScreenType(Class<? extends Screen> screenClass) {
        this.screenClass = screenClass;
    }

    public Class<? extends Screen> getScreenClass() {
        return screenClass;
    }
}


