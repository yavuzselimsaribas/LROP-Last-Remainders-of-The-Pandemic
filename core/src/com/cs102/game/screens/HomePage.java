package com.cs102.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.input.GameKeys;
import com.cs102.game.input.InputManager;
import com.cs102.game.ui.HomePageUI;

public class HomePage extends AbstractScreen<HomePageUI> {
    public HomePage(LastRemaindersOfThePandemic mainGame) {
        super(mainGame);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(16384);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    protected HomePageUI getScreenUI(LastRemaindersOfThePandemic mainGame) {
        return new HomePageUI(mainGame);
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys keys) {

    }

    @Override
    public void keyUp(InputManager manager, GameKeys keys) {

    }
}

