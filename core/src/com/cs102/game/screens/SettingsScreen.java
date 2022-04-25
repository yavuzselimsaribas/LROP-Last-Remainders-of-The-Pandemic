package com.cs102.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.input.GameKeys;
import com.cs102.game.input.InputManager;
import com.cs102.game.ui.SettingsScreenUI;

public class SettingsScreen extends AbstractScreen {


    public SettingsScreen(LastRemaindersOfThePandemic game) {
        super(game);
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }


    @Override
    protected SettingsScreenUI getScreenUI(final LastRemaindersOfThePandemic game) {
        return new SettingsScreenUI(game);
    }


    @Override
    public void keyPressed(InputManager manager, GameKeys keys) {

    }

    @Override
    public void keyUp(InputManager manager, GameKeys keys) {

    }
}
