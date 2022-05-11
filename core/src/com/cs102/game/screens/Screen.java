package com.cs102.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.PreferenceManager;
import com.cs102.game.audio.AudioType;
import com.cs102.game.ecs.ECSEngine;
import com.cs102.game.ecs.components.PlayerComponent;
import com.cs102.game.ecs.system.PlayerCollisionSystem;
import com.cs102.game.input.GameKeys;
import com.cs102.game.input.InputManager;
import com.cs102.game.map.*;
import com.cs102.game.ui.GameUI;


import static com.cs102.game.LastRemaindersOfThePandemic.alpha;
import static com.cs102.game.ecs.ECSEngine.player;
//import com.sun.tools.javac.jvm.Code;

public class Screen extends AbstractScreen implements MapListener {
    private final MapManager mapManager;
    PreferenceManager preferenceManager;
    private Entity player;



    public Screen(LastRemaindersOfThePandemic mainGame) {
        super(mainGame);

        mapManager = mainGame.getMapManager();
        mapManager.addMapListener(this);
        mapManager.setMap(MapType.MAP_1);
        preferenceManager = new PreferenceManager();

        player = mainGame.getEcsEngine().createPlayer(mapManager.getCurrentMap().getPlayerStartLocation(), 0.5f, 1f);
        //TEMP
        mainGame.getGameCamera().position.set(mapManager.getCurrentMap().getPlayerStartLocation(), 0);
        audioManager.playAudio(AudioType.GAME);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        mainGame.getGameRenderer().render(alpha);

        if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            preferenceManager.saveGameState(player);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            preferenceManager.loadGameState(player);
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.K) && PlayerCollisionSystem.teleport) {
            ECSEngine.b2dCmpMapper.get(player).body.setTransform(20,20,0);
            PlayerCollisionSystem.teleport = false;
        }

        ((GameUI) screenUI).addItem(ECSEngine.playerCmpMapper.get(player).itemCount);
        ((GameUI) screenUI).updateHealth(ECSEngine.playerCmpMapper.get(player).health);

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
    public void keyPressed(InputManager manager, GameKeys keys) {
    }
    @Override
    public void keyUp(InputManager manager, GameKeys keys) {
        //input handling is done with ECS engine
    }

    @Override
    public void mapChange(Map map) {

    }

}




