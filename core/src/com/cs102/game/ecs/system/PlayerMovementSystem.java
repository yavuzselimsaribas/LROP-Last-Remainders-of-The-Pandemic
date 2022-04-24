package com.cs102.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.cs102.game.Control;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.ecs.ECSEngine;
import com.cs102.game.ecs.components.B2DComponent;
import com.cs102.game.ecs.components.PlayerComponent;

public class PlayerMovementSystem extends IteratingSystem {
    Control control;
    private float speedX;
    private float speedY;
    public PlayerMovementSystem(final LastRemaindersOfThePandemic mainGame) {
        super(Family.all(PlayerComponent.class, B2DComponent.class).get());
        control = new Control(1280, 720, mainGame.getGameCamera());
        Gdx.input.setInputProcessor(control);
        speedX = speedY = 0;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final PlayerComponent playerComponent = ECSEngine.playerCmpMapper.get(entity);
        final B2DComponent b2DComponent = ECSEngine.b2dCmpMapper.get(entity);

        final float speed = 5;


        if (control.left) {
            speedX = -1 * speed;
        } else if (control.right) {
            speedX = speed;
        } else {
            speedX = 0;
        }

        if (control.down) {
            speedY = -1 * speed;
        } else if (control.up) {
            speedY = speed;
        } else {
            speedY = 0;
        }


        b2DComponent.body.applyLinearImpulse(
                (speedX - b2DComponent.body.getLinearVelocity().x) * b2DComponent.body.getMass(),
                (speedY - b2DComponent.body.getLinearVelocity().y) * b2DComponent.body.getMass(),
                b2DComponent.body.getWorldCenter().x,
                b2DComponent.body.getWorldCenter().y,
                true
        );

    }
}
