package com.cs102.game.ecs;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.ecs.components.AnimationComponent;
import com.cs102.game.ecs.components.B2DComponent;
import com.cs102.game.ecs.components.PlayerComponent;
import com.cs102.game.ecs.system.*;
import com.cs102.game.ui.AnimationType;

import static com.cs102.game.LastRemaindersOfThePandemic.*;

public class ECSEngine extends PooledEngine {
    public static final ComponentMapper<PlayerComponent> playerCmpMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<B2DComponent> b2dCmpMapper = ComponentMapper.getFor(B2DComponent.class);
    public static final ComponentMapper<AnimationComponent> animationCmpMapper = ComponentMapper.getFor(AnimationComponent.class);

    private final RayHandler rayHandler;
    private final World world;
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;


    public ECSEngine(final LastRemaindersOfThePandemic mainGame) {
        super();

        world = mainGame.getWorld();
        rayHandler = mainGame.getRayHandler();
        bodyDef = mainGame.BODY_DEF;
        fixtureDef = mainGame.FIXTURE_DEF;

        this.addSystem(new PlayerMovementSystem(mainGame));
        this.addSystem(new PlayerCameraSystem(mainGame));
        this.addSystem(new AnimationSystem(mainGame));
        this.addSystem(new PlayerAnimationSystem(mainGame));
        this.addSystem(new LightingSystem());
    }



    public void createPlayer(final Vector2 playerStartLocation, final float width, final float height) {
        final Entity player = this.createEntity();

        final PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.speed.set(3,3);
        player.add(playerComponent);

        LastRemaindersOfThePandemic.resetBodiesAndFixtureDefinition();

        final B2DComponent b2DComponent = this.createComponent(B2DComponent.class);
        bodyDef.position.set(playerStartLocation.x, playerStartLocation.y + height*0.5f);
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2DComponent.body = world.createBody(bodyDef);
        b2DComponent.body.setUserData("PLAYER");
        b2DComponent.width = width;
        b2DComponent.height = height;
        b2DComponent.renderPosition.set(b2DComponent.body.getPosition());

        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND;
        final PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.25f, 0.5f);
        fixtureDef.shape = polygonShape;
        b2DComponent.body.createFixture(fixtureDef);
        polygonShape.dispose();

        //create player light radius
        b2DComponent.lightDistance = 6;
        b2DComponent.lightFluctuationSpeed = 4;
        b2DComponent.light = new PointLight(rayHandler,64,new Color().set(1,1,1,0.7f), b2DComponent.lightDistance, b2DComponent.body.getPosition().x,b2DComponent.body.getPosition().y);
        b2DComponent.lightFluctuationDistance = b2DComponent.light.getDistance()*0.16f;
        b2DComponent.light.setSoft(true);
        b2DComponent.light.attachToBody(b2DComponent.body);
        player.add(b2DComponent);


        //animation component
        final AnimationComponent animationComponent = this.createComponent(AnimationComponent.class);
        animationComponent.animationType = AnimationType.HERO_MOVE_DOWN;
        animationComponent.width = 64* UNIT_SCALE*0.3f;
        animationComponent.height = 64* UNIT_SCALE*0.3f;
        player.add(animationComponent);

        this.addEntity(player);
    }
}
