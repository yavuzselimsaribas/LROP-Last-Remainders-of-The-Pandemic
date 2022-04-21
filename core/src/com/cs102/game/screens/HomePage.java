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

public class HomePage implements Screen {
    private Stage stage;
    private Viewport viewport;
    final private Skin skin;
    private Table homeScreenTable;
    final private LastRemaindersOfThePandemic mainGame;

    public HomePage(LastRemaindersOfThePandemic game) {
        mainGame = game;
        skin = new Skin(Gdx.files.internal("assets/uiskin.json"));
    }


    @Override
    public void show() {
        viewport = new ExtendViewport(1280, 720);
        stage = new Stage(viewport);
        homeScreenTable = new Table();
        homeScreenTable.setFillParent(true);
        stage.addActor(homeScreenTable);
        Gdx.input.setInputProcessor(stage);
        addButton("Play").addListener(new ChangeListener() {
                                          @Override
                                          public void changed(ChangeEvent event, Actor actor) {
                                              mainGame.setScreen(ScreenType.GAME);
                                              dispose();
                                          }
                                      }
        );
        addButton("Options").addListener(new ChangeListener() {
                                             @Override
                                             public void changed(ChangeEvent event, Actor actor) {
                                                 mainGame.setScreen(ScreenType.OPTIONS);
                                                 dispose();
                                             }
                                         }
        );
        addButton("About");
        addButton("Quit").addListener(new ClickListener()
                                      {
                                          @Override
                                          public void clicked(InputEvent event, float x, float y) {
                                              Gdx.app.exit();
                                          }
                                      }
        );
    }

    private TextButton addButton(String text) {
        TextButton button = new TextButton(text, skin);
        homeScreenTable.add(button).width(200).height(50).pad(10);
        homeScreenTable.row();
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
