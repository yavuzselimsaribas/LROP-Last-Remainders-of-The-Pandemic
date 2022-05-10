package com.cs102.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;
import com.cs102.game.ui.AnimationType;

public class AnimationComponent implements Component, Pool.Poolable {

    public AnimationType animationType;
    public float animationTime;
    public float width;
    public float height;


    @Override
    public void reset() {
        animationType = null;
        animationTime = 0;
        width = height = 0;
    }
}
