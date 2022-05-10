package com.cs102.game.ecs;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import box2dLight.BlendFunc;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.ecs.components.AnimationComponent;
import com.cs102.game.ecs.components.B2DComponent;
import com.cs102.game.ecs.components.GameObjectComponent;
import com.cs102.game.ecs.components.PlayerComponent;
import com.cs102.game.ecs.system.*;
import com.cs102.game.map.GameObject;
import com.cs102.game.ui.AnimationType;

import static com.cs102.game.LastRemaindersOfThePandemic.*;

public class ECSEngine extends PooledEngine {
    public static final ComponentMapper<PlayerComponent> playerCmpMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<B2DComponent> b2dCmpMapper = ComponentMapper.getFor(B2DComponent.class);
    public static final ComponentMapper<AnimationComponent> animationCmpMapper = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<GameObjectComponent> gameObjCmpMapper = ComponentMapper.getFor(GameObjectComponent.class);

    private final RayHandler rayHandler;
    private final World world;
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    private final Vector2 localPosition;
    private final Vector2 posBeforeRotation;
    private final Vector2 posAfterRotation;

    public static Entity player;


    public ECSEngine(final LastRemaindersOfThePandemic mainGame) {
        super();

        world = mainGame.getWorld();
        rayHandler = mainGame.getRayHandler();
        bodyDef = mainGame.BODY_DEF;
        fixtureDef = mainGame.FIXTURE_DEF;


        localPosition = new Vector2();
        posBeforeRotation = new Vector2();
        posAfterRotation = new Vector2();

        this.addSystem(new PlayerMovementSystem(mainGame));
        this.addSystem(new PlayerCameraSystem(mainGame));
        this.addSystem(new AnimationSystem(mainGame));
        this.addSystem(new PlayerAnimationSystem(mainGame));
        this.addSystem(new PlayerCollisionSystem(mainGame));
        this.addSystem(new LightingSystem());
    }

    public void createPlayer(final Vector2 playerStartLocation, final float width, final float height) {
        player = this.createEntity();

        final PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.speed.set(3, 3);
        player.add(playerComponent);

        LastRemaindersOfThePandemic.resetBodiesAndFixtureDefinition();

        final B2DComponent b2DComponent = this.createComponent(B2DComponent.class);
        bodyDef.position.set(playerStartLocation.x, playerStartLocation.y + height*0.5f);
        bodyDef.fixedRotation = true;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2DComponent.body = world.createBody(bodyDef);
        b2DComponent.body.setUserData(player);
        b2DComponent.width = width;
        b2DComponent.height = height;
        b2DComponent.renderPosition.set(b2DComponent.body.getPosition());

        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_GAME_OBJECT;
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

    public void createGameObject(final GameObject gameObject) {
        final Entity gameObjEntity = this.createEntity();

        final AnimationComponent animationComponent = this.createComponent(AnimationComponent.class);
        animationComponent.animationType = null;
        animationComponent.width = gameObject.getWidth();
        animationComponent.height = gameObject.getHeight();
        gameObjEntity.add(animationComponent);

        LastRemaindersOfThePandemic.resetBodiesAndFixtureDefinition();
        final float halfW = gameObject.getWidth() * 0.5f;
        final float halfH = gameObject.getHeight() * 0.5f;
        final float angleRad = -gameObject.getRotDegree() * MathUtils.degreesToRadians;
        final B2DComponent b2DComponent = this.createComponent(B2DComponent.class);
        BODY_DEF.type = BodyDef.BodyType.StaticBody;
        BODY_DEF.position.set(gameObject.getPosition().x + halfW, gameObject.getPosition().y + halfH);
        b2DComponent.body = world.createBody(BODY_DEF);
        b2DComponent.body.setUserData(gameObjEntity);
        b2DComponent.width = gameObject.getWidth();
        b2DComponent.height = gameObject.getHeight();

        localPosition.set(-halfW, -halfH);
        posBeforeRotation.set(b2DComponent.body.getWorldPoint(localPosition));
        b2DComponent.body.setTransform(b2DComponent.body.getPosition(), angleRad);
        posAfterRotation.set(b2DComponent.body.getWorldPoint(localPosition));

        b2DComponent.body.setTransform(b2DComponent.body.getPosition().add(posBeforeRotation).sub(posAfterRotation), angleRad);
        b2DComponent.renderPosition.set(b2DComponent.body.getPosition().x - animationComponent.width * 0.5f, b2DComponent.body.getPosition().y - b2DComponent.height * 0.5f);

        FIXTURE_DEF.filter.categoryBits = BIT_GAME_OBJECT;
        FIXTURE_DEF.filter.maskBits = BIT_PLAYER;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(halfW, halfH);
        FIXTURE_DEF.shape = pShape;
        b2DComponent.body.createFixture(FIXTURE_DEF);
        pShape.dispose();
        gameObjEntity.add(b2DComponent);

        final GameObjectComponent gameObjectComponent = this.createComponent(GameObjectComponent.class);
        gameObjectComponent.animationIndex = gameObject.getAnimationIndex();
        gameObjectComponent.type = gameObject.getType();
        gameObjEntity.add(gameObjectComponent);

        this.addEntity(gameObjEntity);
    }
}
