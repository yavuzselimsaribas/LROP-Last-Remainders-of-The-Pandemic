package com.cs102.game.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.cs102.game.LastRemaindersOfThePandemic;

public class GameUI extends Table {
    public GameUI(final LastRemaindersOfThePandemic mainGame){
        super(mainGame.getSkin());
    }

}
