package com.cs102.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.WorldContactListener;
import com.cs102.game.ecs.ECSEngine;
import com.cs102.game.ecs.components.GameObjectComponent;
import com.cs102.game.ecs.components.RemoveComponent;
import com.cs102.game.map.GameObjectType;

public class PlayerCollisionSystem extends IteratingSystem implements WorldContactListener.PlayerCollisionListener {
    public PlayerCollisionSystem(final LastRemaindersOfThePandemic mainGame) {
        super(Family.all(RemoveComponent.class).get());

        mainGame.getWorldContactListener().addPlayerCollisionListener(this);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        getEngine().removeEntity(entity);
    }

    @Override
    public void playerCollision(Entity player, final Entity gameObject) {
        final GameObjectComponent gameObjComp = ECSEngine.gameObjCmpMapper.get(gameObject);
        switch (gameObjComp.type) {
            case ITEM :
                //remove crystal and increase player item num
                gameObject.add(((ECSEngine) getEngine()).createComponent(RemoveComponent.class));
                ECSEngine.playerCmpMapper.get(player).itemCount++;
                break;
            case FIRE :
                break;
        }
    }
}
