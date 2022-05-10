package com.cs102.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class RemoveComponent implements Component, Pool.Poolable {
    @Override
    public void reset() {
    }
}
