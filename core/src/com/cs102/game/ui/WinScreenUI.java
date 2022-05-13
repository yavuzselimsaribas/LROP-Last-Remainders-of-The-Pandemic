package com.cs102.game.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.screens.ScreenType;
import com.cs102.game.screens.WinScreen;



public class WinScreenUI extends GameRenderer {
    public WinScreenUI(final LastRemaindersOfThePandemic mainGame) {
        super(mainGame.getSkin());
        this.setFillParent(true);
        //this.setDebug(true);

        //add game over title Lable
        this.add(new Label("You Win!", mainGame.getSkin()));
        this.row();

        //add game over message Label
        this.add(new Label("You have saved the world!", mainGame.getSkin()));
        this.row();

        TextButton playAgainButton = new TextButton("Play Again", mainGame.getSkin());
        playAgainButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainGame.setScreen(ScreenType.MENU);
            }
        });
        this.add(playAgainButton);
        this.row();



    }
}
