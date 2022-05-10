package com.cs102.game.ui;

public enum AnimationType {
    HERO_MOVE_UP("character_and_effects/character_and_effects.atlas","hero",0.05f,0),
    HERO_MOVE_DOWN("character_and_effects/character_and_effects.atlas","hero",0.05f,2),
    HERO_MOVE_LEFT("character_and_effects/character_and_effects.atlas","hero",0.05f,1),
    HERO_MOVE_RIGHT("character_and_effects/character_and_effects.atlas" ,"hero",0.05f,3);

    private final String atlasPath;
    private final String atlasKey;
    private final float frameDuration;
    private final int frameIndex;

    AnimationType(String atlasName, String atlasKey, float frameDuration, int frameIndex) {
        this.atlasPath = atlasName;
        this.atlasKey = atlasKey;
        this.frameDuration = frameDuration;
        this.frameIndex = frameIndex;
    }

    public String getAtlasPath() {
        return atlasPath;
    }

    public String getAtlasKey() {
        return atlasKey;
    }

    public float getFrameDuration() {
        return frameDuration;
    }

    public int getFrameIndex() {
        return frameIndex;
    }
}


