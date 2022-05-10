package com.cs102.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.ecs.ECSEngine;
import com.cs102.game.ecs.components.AnimationComponent;
import com.cs102.game.ecs.components.B2DComponent;
import com.cs102.game.ecs.components.PlayerComponent;
import com.cs102.game.ui.AnimationType;

public class PlayerAnimationSystem extends IteratingSystem {

    public PlayerAnimationSystem(final LastRemaindersOfThePandemic game) {
        super(Family.all(AnimationComponent.class, PlayerComponent.class, B2DComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final B2DComponent b2DComponent = ECSEngine.b2dCmpMapper.get(entity);
        final AnimationComponent animationComponent = ECSEngine.animationCmpMapper.get(entity);
        if( b2DComponent.body.getLinearVelocity().equals(Vector2.Zero) ) {
            animationComponent.animationTime = 0;
        } else if (b2DComponent.body.getLinearVelocity().x > 0) {
            //player moves right
            animationComponent.animationType = AnimationType.HERO_MOVE_RIGHT;
        }
        else if (b2DComponent.body.getLinearVelocity().x < 0) {
            //player moves left
            animationComponent.animationType = AnimationType.HERO_MOVE_LEFT;
        }
        else if (b2DComponent.body.getLinearVelocity().y > 0) {
            //player moves up
            animationComponent.animationType = AnimationType.HERO_MOVE_UP;
        }
        else if (b2DComponent.body.getLinearVelocity().y < 0) {
            //player moves down
            animationComponent.animationType = AnimationType.HERO_MOVE_DOWN;
        }

    }
}