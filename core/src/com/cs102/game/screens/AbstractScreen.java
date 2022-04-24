package com.cs102.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cs102.game.LastRemaindersOfThePandemic;

public abstract class AbstractScreen<T extends Table> implements Screen {
    protected final Viewport viewport;
    protected final LastRemaindersOfThePandemic mainGame;
    protected final World world;
    protected final Box2DDebugRenderer b2DDebugRenderer;
    protected final Stage stage;
    protected final T screenUI;

    public AbstractScreen( final LastRemaindersOfThePandemic mainGame) {
        this.mainGame = mainGame;
        viewport = mainGame.getViewport();
        this.world = mainGame.getWorld();
        this.b2DDebugRenderer = mainGame.getB2dDebugRenderer();
        stage = mainGame.getStage();
        screenUI = getScreenUI(mainGame);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(final int width,final int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        stage.getRoot().removeActor(screenUI);
    }
    @Override
    public void show() {
        stage.addActor(screenUI);
    }

    protected abstract T getScreenUI(final LastRemaindersOfThePandemic mainGame);

    @Override
    public void dispose() {

    }
}
