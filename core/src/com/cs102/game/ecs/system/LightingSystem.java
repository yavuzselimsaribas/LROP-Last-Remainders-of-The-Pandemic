package com.cs102.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.cs102.game.ecs.ECSEngine;
import com.cs102.game.ecs.components.B2DComponent;

public class LightingSystem extends IteratingSystem {
    public LightingSystem() {
        super(Family.all(B2DComponent.class).get());
    }

    @Override
    protected void processEntity(final Entity entity, float deltaTime) {
        final B2DComponent b2dComponent = ECSEngine.b2dCmpMapper.get(entity);
        if(b2dComponent.light != null && b2dComponent.lightFluctuationDistance>0) {
            b2dComponent.lightFluctuationTime += (b2dComponent.lightFluctuationSpeed)*deltaTime;
            if(b2dComponent.lightFluctuationTime> MathUtils.PI2) {
                b2dComponent.lightFluctuationTime = 0;
            }
            b2dComponent.light.setDistance(b2dComponent.lightDistance+MathUtils.sin(b2dComponent.lightFluctuationTime)*b2dComponent.lightFluctuationDistance);

        }
    }
}
