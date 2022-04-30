package com.cs102.game.audio;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.screens.ScreenType;

public class AudioManager {
    private AudioType currentMusicType;
    private Music currentMusic;
    private final AssetManager assetManager;

    public AudioManager(final LastRemaindersOfThePandemic mainGame) {
        this.assetManager = mainGame.getAssetManager();
        currentMusicType = null;
        currentMusic = null;
    }

    public void playAudio(final AudioType audioType) {
        if(audioType.isMusic()){
            //play music
            if(currentMusicType != null && currentMusicType.equals(audioType)){
                return;
            }
            else if(currentMusic != null){
                currentMusic.stop();
            }
            currentMusicType = audioType;
            currentMusic = assetManager.get(audioType.getFilePath(), Music.class);
            currentMusic.setLooping(true);
            currentMusic.setVolume(audioType.getVolume());
            currentMusic.play();
        }
        else{
            assetManager.get(audioType.getFilePath(), Sound.class).play(audioType.getVolume());
            //play sound

        }
    }

    public void stopCurrentMusic() {
        if(currentMusic != null){
            currentMusic.stop();
            currentMusic = null;
            currentMusicType = null;
        }

    }

    }

