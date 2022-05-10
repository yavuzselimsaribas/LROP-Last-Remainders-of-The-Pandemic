package com.cs102.game.ui;

import com.badlogic.gdx.Gdx;
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


public class GameUI extends Table {
    // buttons include in hud

    private Table optionsTable;
    private TextButton optionsButton;
    private TextButton settingsButton;
    private TextButton resumeButton;
    private TextButton saveAndExitButton;
    private int xp;
    private int level;
    private Label worldLabel;
    private Label xpLabel;


    public GameUI(final LastRemaindersOfThePandemic mainGame){
        super(mainGame.getSkin());
        this.top();
        this.setFillParent(true);
        Gdx.input.setInputProcessor(mainGame.getStage());
        xp = 0;
        level = 0;
        xpLabel = new Label("XP: " + xp, mainGame.getSkin());
        worldLabel = new Label("World: " + level, mainGame.getSkin());
        init(mainGame);
        add(xpLabel).left().top().row();
        add(worldLabel).left().top().row();


    }

    public void init(final LastRemaindersOfThePandemic mainGame){

        // set up buttons
        //optionsButton on the top left corner
        optionsButton = new TextButton("Options", mainGame.getSkin());
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // if the options button is clicked, show the options table
                showOptionBar(true);
            }
        }
        );
        initOptionsTable(mainGame);
        add(optionsButton).expand().right().top().row();


    }

    private void showOptionBar(boolean show){
        if(show){
            makeVisibleOptions(true);
        }else{
            makeVisibleOptions(false);
        }

    }
    private void initOptionsTable(final LastRemaindersOfThePandemic mainGame){
        //init settingsButton, cancelButton, resumeButton, saveAndExitButton
        settingsButton = new TextButton("Settings", mainGame.getSkin());
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainGame.setScreen(ScreenType.OPTIONS);
            }
        }
        );
        resumeButton = new TextButton("Resume", mainGame.getSkin());
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showOptionBar(false);
            }
        }
        );
        saveAndExitButton = new TextButton("Save and Exit", mainGame.getSkin());
        saveAndExitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showOptionBar(false);
                mainGame.setScreen(ScreenType.MENU);
            }
        }
        );
        // add the buttons to this table
        // set the table to the center of the screen
        // make the table background opaque with a grey color
        optionsTable = new Table();
        optionsTable.setFillParent(true);
        optionsTable.setBackground(new NinePatchDrawable(new NinePatch(new Texture("options.jpg"), 1, 1, 1, 1)));
        optionsTable.add(settingsButton).width(200).height(50).pad(10).row();
        optionsTable.add(resumeButton).width(200).height(50).pad(10).row();
        optionsTable.add(saveAndExitButton).width(200).height(50).pad(10).row();
        makeVisibleOptions(false);
        add(optionsTable).expand().center().fill();
    }

    private void makeVisibleOptions(boolean show){
        if(show){
            optionsTable.setVisible(true);
            optionsButton.setVisible(false);
        }
        else{
            optionsTable.setVisible(false);
            optionsButton.setVisible(true);
        }
    }

    }






