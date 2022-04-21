package com.cs102.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.cs102.game.LastRemaindersOfThePandemic;

public class GameScreen implements Screen {
    private LastRemaindersOfThePandemic mainGame;
    //Deniz
    private final BodyDef bodyDef;
    private final FixtureDef fixtureDef;
    public GameScreen(LastRemaindersOfThePandemic mainGame) {

        this.mainGame = mainGame;


        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

