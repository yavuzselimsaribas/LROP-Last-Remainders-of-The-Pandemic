package com.cs102.game.input;


public interface InputListener {
    void keyPressed(final InputManager manager,final GameKeys keys);
    void keyUp(final InputManager manager,final GameKeys keys);
}
