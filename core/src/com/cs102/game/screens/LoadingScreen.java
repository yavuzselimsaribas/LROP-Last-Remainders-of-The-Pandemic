package com.cs102.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.input.GameKeys;
import com.cs102.game.input.InputListener;
import com.cs102.game.input.InputManager;
import com.cs102.game.ui.GameUI;
import com.cs102.game.ui.LoadingUI;

import java.awt.*;
import java.nio.file.LinkOption;

public class LoadingScreen extends AbstractScreen<LoadingUI>  {
    private AssetManager assetManager;

    public LoadingScreen(LastRemaindersOfThePandemic game) {
        super(game);
        assetManager = game.getAssetManager();

        //assetManager.load("loadingScreenTile/lab.tmx", TiledMap.class);
        assetManager.load("map3/mock-up.tmx", TiledMap.class);
        //assetManager.load("default.fnt", BitmapFont.class);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(16384);
        System.out.println("LoadingScreen: " + assetManager.getProgress());
        // assetManager updated && any key pressed
        assetManager.update();
        screenUI.setProgress(assetManager.getProgress());

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    protected LoadingUI getScreenUI(final LastRemaindersOfThePandemic game) {
        return new LoadingUI(game);
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys keys) {
        if(assetManager.getProgress()>=1){
            mainGame.setScreen(ScreenType.GAME);
        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys keys) {

    }
}
