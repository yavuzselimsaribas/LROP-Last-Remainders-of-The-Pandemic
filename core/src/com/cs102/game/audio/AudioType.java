package com.cs102.game.audio;
public enum AudioType {
    INTRO("audio/intro.mp3",true,0.3f),
    SELECT("audio/android_assets_audio_select.wav", false,0.5f),

    GAME("audio/gamesound.mp3", false,0.5f);

    private final String filePath;
    private final boolean isMusic;
    private final float volume;
    AudioType(String filePath, boolean isMusic, float volume) {
        this.filePath = filePath;
        this.isMusic = isMusic;
        this.volume = volume;
    }

    public String getFilePath() {
        return filePath;
    }

    public boolean isMusic() {
        return isMusic;
    }

    public float getVolume() {
        return volume;
    }
}
