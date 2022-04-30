package com.cs102.game.screens;

import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.input.GameKeys;
import com.cs102.game.input.InputManager;
import com.cs102.game.map.*;
import com.cs102.game.ui.GameUI;
//import com.sun.tools.javac.jvm.Code;


public class GameScreen extends AbstractScreen implements MapListener {
    private final MapManager mapManager;


    public GameScreen(LastRemaindersOfThePandemic mainGame) {
        super(mainGame);

        mapManager = mainGame.getMapManager();
        mapManager.addMapListener(this);
        mapManager.setMap(MapType.MAP_1);

        mainGame.getEcsEngine().createPlayer(mapManager.getCurrentMap().getPlayerStartLocation(), 0.5f, 1f);
    }



    @Override
    public void render(float delta) {
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




