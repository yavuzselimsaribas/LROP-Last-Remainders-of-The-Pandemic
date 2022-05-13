package com.cs102.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.ecs.ECSEngine;
import com.cs102.game.screens.ScreenType;

import javax.swing.text.html.parser.Entity;

public class GameUI extends GameRenderer {
    // buttons include in hud

    private Table optionsTable;
    private TextButton optionsButton;
    private TextButton settingsButton;
    private TextButton resumeButton;
    private TextButton saveAndExitButton;
    private int xp;
    public int itemCount;

    private int health;
    private Label xpLabel;

    private Label healthLabel;

    private Label itemCountLabel;



    public GameUI(final LastRemaindersOfThePandemic mainGame) {
        super(mainGame.getSkin());
        this.top();
        this.setFillParent(true);
        Gdx.input.setInputProcessor(mainGame.getStage());
        xp = 0;
        itemCount = 0;
        health = 100;
        xpLabel = new Label("XP: " + xp, mainGame.getSkin());
        itemCountLabel = new Label("Item Count: " +itemCount , mainGame.getSkin());
        healthLabel = new Label("Health: " + health, mainGame.getSkin());
        init(mainGame);


    }

    public void init(final LastRemaindersOfThePandemic mainGame) {

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
        add(healthLabel).left().top().expandX();
        add(xpLabel).center().top().expandX();
        add(itemCountLabel).right().top().expandX();
        this.row();
        add(optionsButton).left().top().expandX();
        add(optionsTable).expand().center().width(250).height(250);


    }



    private void showOptionBar(boolean show) {
        if (show) {
            makeVisibleOptions(true);
        } else {
            makeVisibleOptions(false);
        }

    }

    private void initOptionsTable(final LastRemaindersOfThePandemic mainGame) {
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
        optionsTable.setBackground(new NinePatchDrawable(new NinePatch(new Texture("options.jpg"), 1, 1, 1, 1)));
        optionsTable.add(settingsButton).width(200).height(50).pad(10).row();
        optionsTable.add(resumeButton).width(200).height(50).pad(10).row();
        optionsTable.add(saveAndExitButton).width(200).height(50).pad(10).row();
        makeVisibleOptions(false);

    }

    private void makeVisibleOptions(boolean show) {
        if (show) {
            optionsTable.setVisible(true);
            optionsButton.setVisible(false);
        } else {
            optionsTable.setVisible(false);
            optionsButton.setVisible(true);
        }
    }

    //add 1 to the item count and update the label
    public void addItem(int newItemCount) {
        itemCount = newItemCount;
        itemCountLabel.setText("Item Count: " + itemCount);
    }
    public void updateHealth(int newHealth) {
        health = newHealth;
        healthLabel.setText("Health: " + health);
    }

    public void addXP(int newXP) {
        xp = newXP;
        xpLabel.setText("XP: " + xp);
    }
}







