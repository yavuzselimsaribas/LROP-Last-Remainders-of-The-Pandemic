package com.cs102.game.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;

public class InputManager implements InputProcessor {
    private final GameKeys[] keyMappings;
    private final boolean[] keyStates;
    private final Array<InputListener> listeners;

    public InputManager() {
        this.keyMappings = new GameKeys[256];
        for(final GameKeys gameKey : GameKeys.values()) {
            for(final int code : gameKey.keyCodes) {
                this.keyMappings[code] = gameKey;
            }
        }
        keyStates = new boolean[GameKeys.values().length];
        listeners = new Array<InputListener>();
    }

    public void addInputListener(final InputListener listener) {
        listeners.add(listener);
    }
    public void removeListener(final InputListener listener) {
        listeners.removeValue(listener, true);
    }

    @Override
    public boolean keyDown(int keycode) {
        final GameKeys gameKey = keyMappings[keycode];
        if(gameKey != null) {
            onKeyDown(gameKey);
        }
        return false;
    }

    public void onKeyDown(GameKeys gameKey) {
        keyStates[gameKey.ordinal()] = true;
        for(final InputListener listener : listeners) {
            listener.keyPressed(this, gameKey);
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        final GameKeys gameKey = keyMappings[keycode];
        if(gameKey != null) {
            onKeyUp(gameKey);
        }
        return false;
    }

    public void onKeyUp(GameKeys gameKey) {
        keyStates[gameKey.ordinal()] = false;
        for(final InputListener listener : listeners) {
            listener.keyUp(this, gameKey);
        }
    }

    public boolean isPressed(GameKeys gameKey) {
        return keyStates[gameKey.ordinal()];
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


}
