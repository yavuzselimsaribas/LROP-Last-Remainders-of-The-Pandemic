package com.cs102.game.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.ecs.ECSEngine;
import com.cs102.game.ecs.system.PlayerMovementSystem;
import com.cs102.game.input.GameKeys;
import com.cs102.game.input.InputManager;
import com.cs102.game.map.*;
import com.cs102.game.ui.GameUI;
import com.cs102.game.ui.LoadingUI;
//import com.sun.tools.javac.jvm.Code;

import static com.cs102.game.LastRemaindersOfThePandemic.*;

public class GameScreen extends AbstractScreen implements MapListener {
    //Deniz
    //private Body ground;
    //private Body Item;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final OrthographicCamera gameCamera;
    private final GLProfiler profiler;
    //private final Map map;
    private final MapManager mapManager;


    public GameScreen(LastRemaindersOfThePandemic mainGame) {
        super(mainGame);
        viewport.setWorldHeight(9);
        viewport.setWorldWidth(16);

        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, mainGame.getSpriteBatch());
        this.gameCamera = mainGame.getGameCamera();

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();

        mapManager = mainGame.getMapManeger();
        mapManager.addMapListener(this);
        mapManager.setMap(MapType.MAP_1);

        mapRenderer.setMap(mapManager.getTiledMap());
        mainGame.getEcsEngine().createPlayer(mapManager.getCurrentMap().getPlayerStartLocation(), 1, 2);
    }



    @Override
    public void render(float delta) {

        //Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        viewport.apply(false);
        mapRenderer.setView(gameCamera);
        mapRenderer.render();
        b2DDebugRenderer.render(world, viewport.getCamera().combined);
    }


    /*
    public void save() {
        float x = mainGame.getPreferences().getPrefs().getFloat("x");
        x = player.getPosition().x;

        float y = mainGame.getPreferences().getPrefs().getFloat("y");
        y = player.getPosition().y;

        mainGame.getPreferences().getPrefs().putFloat("x", x);
        mainGame.getPreferences().getPrefs().putFloat("y", y);

        mainGame.getPreferences().getPrefs().flush();
    }

     */

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    //@Override
    protected GameUI getScreenUI(final LastRemaindersOfThePandemic mainGame) {
        return new GameUI(mainGame);
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
    }

    @Override
    public void keyPressed(InputManager manager, GameKeys keys) {
        if (keys == GameKeys.BACK) {
            mainGame.setScreen(ScreenType.MENU);
        }
    }
    @Override
    public void keyUp(InputManager manager, GameKeys keys) {
        //input handling is done with ECS engine
    }

    @Override
    public void mapChange(Map map) {

    }
}




