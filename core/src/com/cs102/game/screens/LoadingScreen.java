package com.cs102.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.audio.AudioManager;
import com.cs102.game.audio.AudioType;
import com.cs102.game.input.GameKeys;
import com.cs102.game.input.InputListener;
import com.cs102.game.input.InputManager;
import com.cs102.game.map.MapManager;
import com.cs102.game.map.MapType;
import com.cs102.game.ui.GameUI;
import com.cs102.game.ui.LoadingUI;

import java.awt.*;
import java.nio.file.LinkOption;

public class LoadingScreen extends AbstractScreen<LoadingUI> {
    private AssetManager assetManager;

    private boolean isMusicLoaded;

    public LoadingScreen(LastRemaindersOfThePandemic game) {
        super(game);
        assetManager = game.getAssetManager();

        //assetManager.load("loadingScreenTile/lab.tmx", TiledMap.class);
        assetManager.load(MapType.MAP_1.getFilePath(), TiledMap.class);
        //assetManager.load("default.fnt", BitmapFont.class);
        //load audios
        isMusicLoaded = false;
        for(final AudioType audioType: AudioType.values()){
            assetManager.load(audioType.getFilePath(), audioType.isMusic()? Music.class: Sound.class);
        }
        //Gdx.input.setInputProcessor(game.getInputManager());
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(16384);
        System.out.println("LoadingScreen: " + assetManager.getProgress());
        // assetManager updated && any key pressed
        assetManager.update();
        if(assetManager.isLoaded(AudioType.INTRO.getFilePath()) && !isMusicLoaded){
            isMusicLoaded = true;
            audioManager.playAudio(AudioType.INTRO);
        }
        screenUI.setProgress(assetManager.getProgress());

    }

    @Override
    public void show() {
        super.show();
    }
    // override hide
    @Override
    public void hide() {
        super.hide();
        audioManager.stopCurrentMusic();
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
            audioManager.playAudio(AudioType.SELECT);
            mainGame.setScreen(ScreenType.GAME);
        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys keys) {

    }
}
