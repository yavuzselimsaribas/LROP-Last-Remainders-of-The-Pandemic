package com.cs102.game.ecs;

import box2dLight.BlendFunc;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.MathUtils;
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
import com.cs102.game.ecs.system.AnimationSystem;
import com.cs102.game.ecs.system.PlayerAnimationSystem;
import com.cs102.game.ecs.system.PlayerCameraSystem;
import com.cs102.game.ecs.system.PlayerMovementSystem;
import com.cs102.game.map.GameObject;
import com.cs102.game.ui.AnimationType;

import static com.cs102.game.LastRemaindersOfThePandemic.*;

public class ECSEngine extends PooledEngine {
    public static final ComponentMapper<PlayerComponent> playerCmpMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static final ComponentMapper<B2DComponent> b2dCmpMapper = ComponentMapper.getFor(B2DComponent.class);
    public static final ComponentMapper<AnimationComponent> animationCmpMapper = ComponentMapper.getFor(AnimationComponent.class);
    public static final ComponentMapper<GameObjectComponent> gameObjectCmpMapper = ComponentMapper.getFor(GameObjectComponent.class);
    private final World world;
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    private Vector2 localPosition;
    private Vector2 posBeforeRotation;
    private Vector2 posAfterRotation;

    public ECSEngine(final LastRemaindersOfThePandemic mainGame) {
        super();

        world = mainGame.getWorld();
        bodyDef = mainGame.BODY_DEF;
        fixtureDef = mainGame.FIXTURE_DEF;
        localPosition = new Vector2();
        posBeforeRotation = new Vector2();
        posAfterRotation = new Vector2();

        this.addSystem(new PlayerMovementSystem(mainGame));
        this.addSystem(new PlayerCameraSystem(mainGame));
        this.addSystem(new AnimationSystem(mainGame));
        this.addSystem(new PlayerAnimationSystem(mainGame));
    }

    public void createPlayer(final Vector2 playerStartLocation, final float width, final float height) {
        final Entity player = this.createEntity();

        final PlayerComponent playerComponent = this.createComponent(PlayerComponent.class);
        playerComponent.speed.set(3, 3);
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

        player.add(b2DComponent);

        //animation component
        final AnimationComponent animationComponent = this.createComponent(AnimationComponent.class);
        animationComponent.animationType = AnimationType.HERO_MOVE_DOWN;
        animationComponent.width = 64* UNIT_SCALE*0.3f;
        animationComponent.height = 64* UNIT_SCALE*0.3f;
        player.add(animationComponent);

        this.addEntity(player);
    }

    public void createGameObject(GameObject gameObject) {
        final Entity gameObjectEntity = this.createEntity();

        //GameObject component
        final GameObjectComponent gameObjectComponent = this.createComponent(GameObjectComponent.class);
        gameObjectComponent.animationIndex = gameObject.getAnimationIndex();
        gameObjectComponent.type = gameObject.getType();
        gameObjectEntity.add(gameObjectComponent);

        //AnimationComponent
        final AnimationComponent animationComponent = this.createComponent(AnimationComponent.class);
        animationComponent.animationType = null;
        animationComponent.width = gameObject.getWidth();
        animationComponent.height = gameObject.getHeight();
        gameObjectEntity.add(animationComponent);

        //BOX2DComponent
        LastRemaindersOfThePandemic.resetBodiesAndFixtureDefinition();
        final float halfW = gameObject.getWidth() * 0.5f;
        final float halfH = gameObject.getHeight() * 0.5f;
        final float angleRad = -gameObject.getRotDegree() * MathUtils.degreesToRadians;
        final B2DComponent b2DComponent = this.createComponent(B2DComponent.class);
        LastRemaindersOfThePandemic.BODY_DEF.type = BodyDef.BodyType.StaticBody;
        LastRemaindersOfThePandemic.BODY_DEF.position.set(gameObject.getPosition().x + halfW, gameObject.getPosition().y + halfH);
        b2DComponent.body = world.createBody(LastRemaindersOfThePandemic.BODY_DEF);
        b2DComponent.body.setUserData("GAMEOBJECT");
        b2DComponent.width = gameObject.getWidth();
        b2DComponent.height = gameObject.getHeight();

        //save position before rotation - Tiled is rotating around the bottom left corner instead of the center of a Tile
        localPosition.set(-halfW, -halfH);
        posBeforeRotation.set(b2DComponent.body.getWorldPoint(localPosition));
        //rotate body
        b2DComponent.body.setTransform(b2DComponent.body.getPosition(), angleRad);
        //get position after rotation
        posAfterRotation.set(b2DComponent.body.getWorldPoint(localPosition));
        //adjust position to its original value before rotation
        b2DComponent.body.setTransform(b2DComponent.body.getPosition().add(posBeforeRotation).sub(posAfterRotation), angleRad);
        b2DComponent.renderPosition.set(b2DComponent.body.getPosition().x - animationComponent.width * 0.5f, b2DComponent.body.getPosition().y - animationComponent.height * 0.5f);

        LastRemaindersOfThePandemic.FIXTURE_DEF.filter.categoryBits = BIT_GAME_OBJECT;
        LastRemaindersOfThePandemic.FIXTURE_DEF.filter.maskBits = BIT_PLAYER;
        final PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(halfW, halfH);
        LastRemaindersOfThePandemic.FIXTURE_DEF.shape = pShape;
        b2DComponent.body.createFixture(LastRemaindersOfThePandemic.FIXTURE_DEF);
        pShape.dispose();
        gameObjectEntity.add(b2DComponent);

        this.addEntity(gameObjectEntity);
    }
}
