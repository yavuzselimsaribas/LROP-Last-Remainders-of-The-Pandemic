package com.cs102.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.cs102.game.LastRemaindersOfThePandemic;

public class GameOverUI extends Table {

    public GameOverUI(final LastRemaindersOfThePandemic mainGame) {
        super(mainGame.getSkin());
        this.setFillParent(true);
        this.setDebug(true);
    }


}
