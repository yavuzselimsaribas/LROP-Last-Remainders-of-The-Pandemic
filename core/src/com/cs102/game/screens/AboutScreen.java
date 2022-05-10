package com.cs102.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.input.GameKeys;
import com.cs102.game.input.InputManager;
import com.cs102.game.ui.AboutScreenUI;

public class AboutScreen extends AbstractScreen<AboutScreenUI>{
    public AboutScreen(LastRemaindersOfThePandemic mainGame) {
        super(mainGame);
    }

    @Override
    protected AboutScreenUI getScreenUI(LastRemaindersOfThePandemic mainGame) {
        return new AboutScreenUI(mainGame);
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
    public void keyPressed(InputManager manager, GameKeys keys) {

    }

    @Override
    public void keyUp(InputManager manager, GameKeys keys) {

    }
}
