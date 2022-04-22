package com.cs102.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.cs102.game.LastRemaindersOfThePandemic;

import static com.cs102.game.LastRemaindersOfThePandemic.*;

public class GameScreen extends AbstractScreen{
    //Deniz
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;

    private final Body player;

    public GameScreen(LastRemaindersOfThePandemic mainGame) {
        super(mainGame);

        //create a player
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();

        bodyDef.position.set(320, 200);
        bodyDef.gravityScale = 1;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bodyDef);
        player.setUserData("PLAYER");

        fixtureDef.density = 1;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_PLAYER;
        fixtureDef.filter.maskBits = BIT_ROOM;
        PolygonShape pShape = new PolygonShape();
        pShape.setAsBox(30, 40);
        fixtureDef.shape = pShape;
        player.createFixture(fixtureDef);
        pShape.dispose();

        //create room
        bodyDef.position.set(10, 10);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);
        body.setUserData("ROOM");

        fixtureDef.filter.categoryBits = BIT_ROOM;
        fixtureDef.filter.maskBits = -1;
        ChainShape gShape = new ChainShape();
        gShape.createLoop(new float[]{10, 10, 10, 700, 1260, 700, 1260, 10});
        fixtureDef.shape = gShape;
        body.createFixture(fixtureDef);
        gShape.dispose();

        //create circle
        /*bodyDef.position.set(645, 450);
        bodyDef.gravityScale = 9.8f;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body body = world.createBody(bodyDef);
        body.setUserData("CIRCLE");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.9f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_CIRCLE;
        fixtureDef.filter.maskBits = BIT_GROUND | BIT_BOX;
        CircleShape cShape = new CircleShape();
        cShape.setRadius(30f);
        fixtureDef.shape = cShape;
        body.createFixture(fixtureDef);
        cShape.dispose();

        //create a box
        bodyDef.position.set(690f, 360f);
        bodyDef.gravityScale = 9.8f;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        body.setUserData("BOX");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.5f;
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
        bodyDef.gravityScale = 9.8f;
        bodyDef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bodyDef);
        body.setUserData("GROUND");

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_GROUND;
        fixtureDef.filter.maskBits = -1;
        pShape = new PolygonShape();
        pShape.setAsBox(200f, 20f);
        fixtureDef.shape = pShape;
        body.createFixture(fixtureDef);
        pShape.dispose();*/

        //create a triangle
        /*bodyDef.position.set(740, 15);
        bodyDef.gravityScale = 9.8f;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.5f;
        fixtureDef.friction = 0.2f;
        fixtureDef.filter.categoryBits = BIT_TRIANGLE;
        fixtureDef.filter.maskBits = BIT_BOX | BIT_CIRCLE | BIT_GROUND;
        pShape = new PolygonShape();
        pShape.set()*/
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        final float speedX;
        final float speedY;


        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            speedX = -50;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            speedX = 50;
        } else {
            speedX = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            speedY = -50;
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            speedY = 50;
        } else {
            speedY = 0;
        }

        player.applyLinearImpulse(
                (speedX - player.getLinearVelocity().x) * player.getMass(),
                (speedY - player.getLinearVelocity().y) * player.getMass(),
                player.getWorldCenter().x + 30, player.getWorldCenter().y + 40, true
        );

        viewport.apply(true);
        b2DDebugRenderer.render(world, viewport.getCamera().combined);
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

    }
}

