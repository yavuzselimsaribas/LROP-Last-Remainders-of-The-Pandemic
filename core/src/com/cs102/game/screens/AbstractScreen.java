package com.cs102.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cs102.game.LastRemaindersOfThePandemic;

public class AbstractScreen implements Screen {
    protected Viewport viewport;
    protected final LastRemaindersOfThePandemic mainGame;
    protected final World world;

    public AbstractScreen( final LastRemaindersOfThePandemic mainGame) {
        this.mainGame = mainGame;
        viewport = mainGame.getViewport();
        this.world = mainGame.getWorld();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
