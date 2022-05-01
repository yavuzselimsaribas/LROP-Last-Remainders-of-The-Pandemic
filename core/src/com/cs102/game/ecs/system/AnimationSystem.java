package com.cs102.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.ecs.ECSEngine;
import com.cs102.game.ecs.components.AnimationComponent;

public class AnimationSystem extends IteratingSystem {

    public AnimationSystem(final LastRemaindersOfThePandemic game) {
        super(Family.all(AnimationComponent.class).get());
    }


    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        final AnimationComponent animationComponent = ECSEngine.animationCmpMapper.get(entity);
        if(animationComponent.animationType != null) {
            animationComponent.animationTime += deltaTime;  // update animation time
        }

    }
}
