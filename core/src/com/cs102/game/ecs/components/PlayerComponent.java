package com.cs102.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {
    public int itemCount;
    public Vector2 speed = new Vector2();

    @Override
    public void reset() {
        itemCount = 0;
        speed.setZero();
    }
}
