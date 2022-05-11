package com.cs102.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.screens.WinScreen;



public class WinScreenUI extends Table {
    public WinScreenUI(LastRemaindersOfThePandemic mainGame) {
        super(mainGame.getSkin());
        this.setFillParent(true);
        this.setDebug(true);
    }
}
