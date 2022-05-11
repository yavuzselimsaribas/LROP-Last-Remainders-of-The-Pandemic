package com.cs102.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.input.GameKeys;
import com.cs102.game.input.InputManager;
import com.cs102.game.ui.WinScreenUI;

public class WinScreen extends AbstractScreen{
    public WinScreen(LastRemaindersOfThePandemic mainGame) {
        super(mainGame);
    }

    @Override
    protected Table getScreenUI(LastRemaindersOfThePandemic mainGame) {
        return new WinScreenUI(mainGame);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void keyPressed(InputManager manager, GameKeys keys) {

    }

    @Override
    public void keyUp(InputManager manager, GameKeys keys) {

    }
}
