package com.cs102.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.screens.ScreenType;

public class AboutScreenUI extends GameRenderer {

    public AboutScreenUI(final LastRemaindersOfThePandemic mainGame) {
        super(mainGame.getSkin());
        this.setFillParent(true);
        add(new Label("About", mainGame.getSkin())).colspan(2).center().row();
        add(new Label("Last Remainders Of The Pandemic", mainGame.getSkin())).colspan(2).center().row();
        add(new Label("Version: 1.0", mainGame.getSkin())).colspan(2).center().row();
        add(new Label("Authors:", mainGame.getSkin())).colspan(2).center().row();
        add(new Label("- Deniz", mainGame.getSkin())).colspan(2).center().row();
        add(new Label("- Yavuz Selim", mainGame.getSkin())).colspan(2).center().row();
        add(new Label("- Mehmet Eren", mainGame.getSkin())).colspan(2).center().row();
        TextButton backButton = new TextButton("Back", mainGame.getSkin());
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainGame.setScreen(ScreenType.MENU);
            }
        });
        add(backButton).colspan(2).center().row();

    }

    }

