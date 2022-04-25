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
import com.cs102.game.input.GameKeys;
import com.cs102.game.input.InputListener;
import com.cs102.game.input.InputManager;

public class PlayerMovementSystem extends IteratingSystem implements InputListener {
    private boolean directionChanged;
    private int xFactor;
    private int yFactor;
    public PlayerMovementSystem(final LastRemaindersOfThePandemic mainGame) {
        super(Family.all(PlayerComponent.class, B2DComponent.class).get());
        mainGame.getInputManager().addInputListener(this);
        directionChanged = false;
        xFactor = yFactor = 0;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        if(directionChanged){
            final PlayerComponent playerComponent = ECSEngine.playerCmpMapper.get(entity);
            final B2DComponent b2DComponent = ECSEngine.b2dCmpMapper.get(entity);
            directionChanged = false;
            b2DComponent.body.applyLinearImpulse(
                    (xFactor * playerComponent.speed.x - b2DComponent.body.getLinearVelocity().x) * b2DComponent.body.getMass(),
                    (yFactor * playerComponent.speed.y - b2DComponent.body.getLinearVelocity().y) * b2DComponent.body.getMass(),
                    b2DComponent.body.getWorldCenter().x,b2DComponent.body.getWorldCenter().y,true);
        }


    }

    @Override
    public void keyPressed(InputManager manager, GameKeys keys) {
        switch (keys) {
            case LEFT:
                directionChanged = true;
                xFactor = -1;
                break;
            case RIGHT:
                directionChanged = true;
                xFactor = 1;
                break;
            case UP:
                directionChanged = true;
                yFactor = 1;
                break;
            case DOWN:
                directionChanged = true;
                yFactor = -1;
                break;
            default:
                return;

        }
    }

    @Override
    public void keyUp(InputManager manager, GameKeys keys) {
        switch (keys) {
            case LEFT:
                directionChanged = true;
                xFactor = manager.isPressed(GameKeys.RIGHT) ? 1 : 0;
                break;
            case RIGHT:
                directionChanged = true;
                xFactor = manager.isPressed(GameKeys.LEFT) ? -1 : 0;
                break;
            case UP:
                directionChanged = true;
                yFactor = manager.isPressed(GameKeys.DOWN) ? -1 : 0;
                break;
            case DOWN:
                directionChanged = true;
                yFactor = manager.isPressed(GameKeys.UP) ? 1 : 0;
                break;
            default:
                break;

        }
    }
}
