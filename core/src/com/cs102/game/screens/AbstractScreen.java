package com.cs102.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cs102.game.LastRemaindersOfThePandemic;

public class AbstractScreen implements Screen {
    protected Viewport viewport;
    protected final LastRemaindersOfThePandemic mainGame;
    protected final World world;
    protected final Box2DDebugRenderer b2DDebugRenderer;
    protected Stage stage;

    public AbstractScreen( final LastRemaindersOfThePandemic mainGame) {
        this.mainGame = mainGame;
        viewport = mainGame.getViewport();
        this.world = mainGame.getWorld();
        this.b2DDebugRenderer = mainGame.getB2dDebugRenderer();
        stage = new Stage(new FitViewport(1280, 720));
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
        stage.getRoot().remove();
    }

    @Override
    public void dispose() {

    }
}
