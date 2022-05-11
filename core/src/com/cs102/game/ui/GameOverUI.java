package com.cs102.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.screens.ScreenType;

public class GameOverUI extends Table {

    public GameOverUI(final LastRemaindersOfThePandemic mainGame) {
        super(mainGame.getSkin());
        this.setFillParent(true);
        this.setDebug(true);
        // add labels
        this.add(new Label("Game Over", mainGame.getSkin()));
        this.row();
        this.add(new Label("You succumbed to the pandemic", mainGame.getSkin()));
        this.row();

        // add buttons
        TextButton restartButton = new TextButton("Restart", mainGame.getSkin());
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainGame.setScreen(ScreenType.MENU);
            }
        });
        this.add(restartButton);
        this.row();
        TextButton exitButton = new TextButton("Exit", mainGame.getSkin());
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        }
        );
        this.add(exitButton);
        this.row();
    }
}


