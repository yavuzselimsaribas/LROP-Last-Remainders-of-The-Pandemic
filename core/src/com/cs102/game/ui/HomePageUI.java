package com.cs102.game.ui;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.screens.ScreenType;

public class HomePageUI extends GameRenderer {

    private final TextButton playButton;
    private final TextButton settingsButton;
    private final TextButton aboutButton;
    private final TextButton exitButton;

    private final Label titleLabel;

    private LastRemaindersOfThePandemic mainGame;


    public HomePageUI(final LastRemaindersOfThePandemic game) {
        super(game.getSkin());
        setFillParent(true);
        this.mainGame = game;
        this.setBackground(new NinePatchDrawable(new NinePatch(new Texture("main_menu.jpg"), 1, 1, 1, 1)));
        titleLabel = new Label("Last Remainders of the Pandemic", game.getSkin());
        titleLabel.setFontScale(2);
        titleLabel.setAlignment(2);
        titleLabel.setWrap(true);
        titleLabel.setWidth(Gdx.graphics.getWidth());
        titleLabel.setHeight(Gdx.graphics.getHeight()/2);
        titleLabel.setPosition(0, Gdx.graphics.getHeight()/2);
        add(titleLabel).row();
        Gdx.input.setInputProcessor(new InputMultiplexer(mainGame.getInputManager(), mainGame.getStage()));
        playButton = new TextButton("Play", game.getSkin());
        playButton.getLabel().setWrap(true);
        settingsButton = new TextButton("Settings", game.getSkin());
        settingsButton.getLabel().setWrap(true);
        aboutButton = new TextButton("About", game.getSkin());
        aboutButton.getLabel().setWrap(true);
        exitButton = new TextButton("Exit", game.getSkin());
        exitButton.getLabel().setWrap(true);
        playButton.addListener(new ChangeListener() {
                                   @Override
                                   public void changed(ChangeEvent event, Actor actor) {
                                       mainGame.setScreen(ScreenType.LOADING);
                                   }
                               }
        );
        settingsButton.addListener(new ChangeListener() {
                                       @Override
                                       public void changed(ChangeEvent event, Actor actor) {
                                           mainGame.setScreen(ScreenType.OPTIONS);
                                       }
                                   }
        );
        aboutButton.addListener(new ChangeListener() {
                                    @Override
                                    public void changed(ChangeEvent event, Actor actor) {
                                        mainGame.setScreen(ScreenType.ABOUT);
                                    }
                                }
        );
        exitButton.addListener(new ChangeListener() {
                                   @Override
                                   public void changed(ChangeEvent event, Actor actor) {
                                       Gdx.app.exit();
                                   }
                               }
        );

        add(playButton).width(200).height(50).pad(10).center().row();
        add(settingsButton).width(200).height(50).pad(10).center().row();
        add(aboutButton).width(200).height(50).pad(10).center().row();
        add(exitButton).width(200).height(50).pad(10).center().row();

    }





    }




