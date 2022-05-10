package com.cs102.game.input;

import com.badlogic.gdx.Input;

public enum GameKeys {
    UP(Input.Keys.W, Input.Keys.UP),
    DOWN(Input.Keys.S, Input.Keys.DOWN),
    LEFT(Input.Keys.A, Input.Keys.LEFT),
    RIGHT(Input.Keys.D, Input.Keys.RIGHT),
    SELECT(Input.Keys.ENTER, Input.Keys.SPACE),
    BACK(Input.Keys.ESCAPE, Input.Keys.BACKSPACE);
    final int[] keyCodes;
    GameKeys(final int... keyCodes) {
        this.keyCodes = keyCodes;
    }

    public int[] getKeyCodes() {
        return keyCodes;
    }
}

