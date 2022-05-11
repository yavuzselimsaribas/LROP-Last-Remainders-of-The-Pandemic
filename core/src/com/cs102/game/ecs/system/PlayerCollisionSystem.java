package com.cs102.game.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.WorldContactListener;
import com.cs102.game.ecs.ECSEngine;
import com.cs102.game.ecs.components.B2DComponent;
import com.cs102.game.ecs.components.GameObjectComponent;
import com.cs102.game.ecs.components.RemoveComponent;
import com.cs102.game.map.GameObjectType;
import com.cs102.game.ui.GameUI;

public class PlayerCollisionSystem extends IteratingSystem implements WorldContactListener.PlayerCollisionListener {
    public static boolean teleport;
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
            case INFECTIOUS :
                ECSEngine.playerCmpMapper.get(player).health--;
                break;
            case PORTAL1 :
                if (ECSEngine.playerCmpMapper.get(player).itemCount >= 10) {
                    teleport = true;
                }
                break;
            case PORTAL2 :
                teleport = true;
                break;
        }
    }
}
