package com.cs102.game.map;

import com.badlogic.gdx.math.Vector2;

public class GameObject {
    private final GameObjectType type;
    private final Vector2 position;
    private final float height;
    private final float width;
    private final float rotDegree;
    private final int animationIndex;

    public GameObject(final GameObjectType type, final Vector2 position, final float height, final float width, final float rotDegree, final int animationIndex) {
        this.type = type;
        this.position = position;
        this.height = height;
        this.width = width;
        this.rotDegree = rotDegree;
        this.animationIndex = animationIndex;
    }

    public float getHeight() {
        return height;
    }

    public float getRotDegree() {
        return rotDegree;
    }

    public float getWidth() {
        return width;
    }

    public GameObjectType getType() {
        return type;
    }

    public int getAnimationIndex() {
        return animationIndex;
    }

    public Vector2 getPosition() {
        return position;
    }

}
