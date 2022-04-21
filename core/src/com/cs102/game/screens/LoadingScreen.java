package com.cs102.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.cs102.game.LastRemaindersOfThePandemic;

import java.awt.*;

public class LoadingScreen extends AbstractScreen {
    private AssetManager assetManager;

    public LoadingScreen(LastRemaindersOfThePandemic game) {
        super(game);
        assetManager = game.getAssetManager();
        assetManager.load("default.fnt", BitmapFont.class);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(16384);
        System.out.println("LoadingScreen: " + assetManager.getProgress());
        if (assetManager.update()) {
            mainGame.setScreen(ScreenType.GAME);
        }
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
