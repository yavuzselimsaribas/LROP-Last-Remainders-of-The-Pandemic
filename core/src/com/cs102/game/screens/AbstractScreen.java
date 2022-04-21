package com.cs102.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cs102.game.LastRemaindersOfThePandemic;

public class AbstractScreen implements Screen {
    //protected final FitViewport viewport;
    protected final LastRemaindersOfThePandemic context;
    protected final World world;

    public AbstractScreen( final LastRemaindersOfThePandemic context) {
        this.context = context;
        //viewport = context.getScreen()
        this.world = context.getWorld();
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
