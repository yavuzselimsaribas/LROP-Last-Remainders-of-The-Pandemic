package com.cs102.game.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {
    public boolean hasAxe;

    public int health;
    public int xp;
    public Vector2 speed = new Vector2();

    @Override
    public void reset() {
        health = 0;
        xp = 0;
        hasAxe = false;
        speed.setZero();
    }


    //getter of the health and xp
    public int getHealth() {
        return health;
    }

    public int getXp() {
        return xp;
    }


}
