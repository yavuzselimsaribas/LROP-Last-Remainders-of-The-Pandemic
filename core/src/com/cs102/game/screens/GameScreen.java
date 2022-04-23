package com.cs102.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.cs102.game.Control;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.maps.CollisionArea;
import com.cs102.game.maps.Map;
//import com.sun.tools.javac.jvm.Code;

import static com.cs102.game.LastRemaindersOfThePandemic.*;

public class GameScreen extends AbstractScreen{
    //Deniz
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    private final Body player;
    private Body ground;
    private Body Item;
    Control control;
    private final AssetManager assetManager;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera gameCamera;
    private final GLProfiler profiler;
    private Map map;
    public GameScreen(LastRemaindersOfThePandemic mainGame) {
        super(mainGame);

        viewport.setWorldHeight(9);
        viewport.setWorldWidth(16);

        assetManager = mainGame.getAssetManager();
        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, mainGame.getSpriteBatch());
        this.gameCamera = mainGame.getGameCamera();

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();

        control = new Control(1280, 720, mainGame.getGameCamera());
        Gdx.input.setInputProcessor(control);

        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        //create player
        bodyDef.position.set(8f, 3);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bodyDef);
        player.setUserData("PLAYER");

        fixtureDef.isSensor = false;
        fixtureDef.density = 1;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_ITEM;
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = pShape;
        player.createFixture(fixtureDef);
        pShape.dispose();

        final TiledMap tiledMap = assetManager.get("map3/mock-up.tmx", TiledMap.class);
        mapRenderer.setMap(tiledMap);
        map = new Map(tiledMap);
        spawnCollisionAreas();
    }
    private void resetBodyAndFixtureDef() {

    }
    private void spawnCollisionAreas() {
        for(final CollisionArea collisionArea : map.getCollisionAreas()) {
            resetBodyAndFixtureDef();
            //create room
            bodyDef.position.set(collisionArea.getX(), collisionArea.getY());
            bodyDef.fixedRotation =  true;
            bodyDef.type = BodyDef.BodyType.StaticBody;
            ground = world.createBody(bodyDef);
            ground.setUserData("GROUND");

            fixtureDef.filter.categoryBits = BIT_GROUND;
            fixtureDef.filter.maskBits = -1;
            final ChainShape chainShape = new ChainShape();
            chainShape.createChain(collisionArea.getVertices());
            fixtureDef.shape = chainShape;
            ground.createFixture(fixtureDef);
            chainShape.dispose();
        }
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        final float speed = 5;
        final float speedX;
        final float speedY;

        if (control.left) {
            speedX = -1 * speed;
        }
        else if (control.right) {
            speedX = speed;
        }
        else {
            speedX = 0;
        }

        if (control.down) {
            speedY = -1 * speed;
        }
        else if (control.up) {
            speedY = speed;
        }
        else {
            speedY = 0;
        }


        player.applyLinearImpulse(
                (speedX - player.getLinearVelocity().x) * player.getMass(),
                (speedY - player.getLinearVelocity().y) * player.getMass(),
                player.getWorldCenter().x,
                player.getWorldCenter().y,
                true
        );

        viewport.apply(true);
        mapRenderer.setView(gameCamera);
        mapRenderer.render();
        b2DDebugRenderer.render(world, viewport.getCamera().combined);
        Gdx.app.debug("RenderInfo", "Bindings: " + profiler.getTextureBindings());
        Gdx.app.debug("RenderInfo", "Drawcalls: " + profiler.getDrawCalls());
        profiler.reset();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
    }
}

