package com.cs102.game.maps;

import com.badlogic.gdx.math.Vector2;

import static com.cs102.game.LastRemaindersOfThePandemic.UNIT_SCALE;

public class CollisionArea {
    private final float x;
    private final float y;
    private final float[] vertices;
    public CollisionArea(final float x, final float y, float[] vertices) {
        this.x = x * UNIT_SCALE;
        this.y = y * UNIT_SCALE;
        this.vertices = vertices;
        for(int i = 0; i < vertices.length; i++) {
            this.vertices[i] *= UNIT_SCALE;
        }
    }
    public float[] getVertices() {
        return vertices;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
