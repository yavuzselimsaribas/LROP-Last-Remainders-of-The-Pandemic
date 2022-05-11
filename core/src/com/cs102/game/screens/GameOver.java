package com.cs102.game.screens;

import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.input.GameKeys;
import com.cs102.game.input.InputManager;
import com.cs102.game.ui.GameOverUI;

public class GameOver extends AbstractScreen<GameOverUI>{


    public GameOver(LastRemaindersOfThePandemic mainGame) {
        super(mainGame);
    }

    @Override
    protected GameOverUI getScreenUI(LastRemaindersOfThePandemic mainGame) {
        return new GameOverUI(mainGame);
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
