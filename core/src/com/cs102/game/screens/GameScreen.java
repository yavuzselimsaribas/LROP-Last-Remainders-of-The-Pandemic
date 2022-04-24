package com.cs102.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.cs102.game.Control;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.map.CollisionArea;
import com.cs102.game.map.Map;
import com.cs102.game.ui.GameUI;
import com.cs102.game.ui.LoadingUI;
//import com.sun.tools.javac.jvm.Code;

import static com.cs102.game.LastRemaindersOfThePandemic.*;

public class GameScreen extends AbstractScreen {
    //Deniz
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    private Body player;
    //private Body ground;
    //private Body Item;
    Control control;
    private final AssetManager assetManager;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera gameCamera;
    private final GLProfiler profiler;
    private final Map map;

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

        final TiledMap tiledMap = assetManager.get("map3/mock-up.tmx", TiledMap.class);
        mapRenderer.setMap(assetManager.get("map3/mock-up.tmx", TiledMap.class));
        map = new Map(tiledMap);


        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        //create player

        /*
        //create item
        bodyDef.position.set(13, 6);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Item = world.createBody(bodyDef);
        Item.setUserData("ITEM");

        fixtureDef.isSensor = true;
        fixtureDef.restitution = 0;
        fixtureDef.friction  =0.2f;
        fixtureDef.filter.categoryBits = BIT_ITEM;
        fixtureDef.filter.maskBits = BIT_PLAYER;
        CircleShape cShape = new CircleShape();
        cShape.setRadius(0.5f);
        fixtureDef.shape = cShape;
        Item.createFixture(fixtureDef);
        cShape.dispose();

         */
        /*
        //create a circle
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        bodyDef.position.set(690, 600);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);
        body.setUserData("CIRCLE");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_CIRCLE;
        fixtureDef.filter.maskBits = BIT_GROUND;
        CircleShape cShape = new CircleShape();
        cShape.setRadius(30f);
        fixtureDef.shape = cShape;
        body.createFixture(fixtureDef);
        cShape.dispose();


        fixtureDef.isSensor = true;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_CIRCLE;
        fixtureDef.filter.maskBits = BIT_BOX;
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(30f, 30f);
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef);
        polygonShape.dispose();



        //create a box
        bodyDef.position.set(690f, 600f);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        body.setUserData("BOX");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.1f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_BOX;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_CIRCLE;
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(30f, 30f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        pShape.dispose();

        //create a platform
        bodyDef.position.set(690f, 20f);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        body.setUserData("PLATFORM");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        pShape = new PolygonShape();
        pShape.setAsBox(200f, 20f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        pShape.dispose();
         */


        spawnCollisionAreas();
        spawnPlayer();
    }

    private void spawnPlayer() {
        resetBodiesAndFixtureDefinition();

        bodyDef.position.set(map.getPlayerStartLocation());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bodyDef);
        player.setUserData("PLAYER");

        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_ITEM;
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(0.5f, 0.5f);
        fixtureDef.shape = pShape;
        player.createFixture(fixtureDef);
        pShape.dispose();
    }

    private void resetBodiesAndFixtureDefinition() {
        bodyDef.position.set(0, 0);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = false;

        fixtureDef.density = 0;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = 0x0001;
        fixtureDef.filter.maskBits = -1;
        fixtureDef.shape = null;
    }

    private void spawnCollisionAreas() {
        for (final CollisionArea collisionArea : map.getCollisionAreas()) {
            resetBodiesAndFixtureDefinition();

            //create room
            bodyDef.position.set(collisionArea.getX(), collisionArea.getY());
            bodyDef.fixedRotation = true;
            final Body body = world.createBody(bodyDef);
            body.setUserData("GROUND");

            fixtureDef.filter.categoryBits = BIT_GROUND;
            fixtureDef.filter.maskBits = -1;
            final ChainShape chainShape = new ChainShape();
            chainShape.createChain(collisionArea.getVertices());
            fixtureDef.shape = chainShape;
            body.createFixture(fixtureDef);
            chainShape.dispose();
        }
    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        final float speed = 5;
        final float speedX;
        final float speedY;

        if (control.V) {
            save();
        }

        if (control.left) {
            speedX = -1 * speed;
        } else if (control.right) {
            speedX = speed;
        } else {
            speedX = 0;
        }

        if (control.down) {
            speedY = -1 * speed;
        } else if (control.up) {
            speedY = speed;
        } else {
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

    }

    public void save() {
        float x = mainGame.getPreferences().getPrefs().getFloat("x");
        x = player.getPosition().x;

        float y = mainGame.getPreferences().getPrefs().getFloat("y");
        y = player.getPosition().y;

        mainGame.getPreferences().getPrefs().putFloat("x", x);
        mainGame.getPreferences().getPrefs().putFloat("y", y);

        mainGame.getPreferences().getPrefs().flush();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    //@Override
    protected GameUI getScreenUI(final LastRemaindersOfThePandemic mainGame) {
        return new GameUI(mainGame.getSkin());
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
    }

}

