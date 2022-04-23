package com.cs102.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.cs102.game.screens.ScreenType;

public class Control extends InputAdapter implements InputProcessor{
    OrthographicCamera camera;

    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    //Deniz added additionally
    public boolean collect;
    //Deniz added additionally
    public boolean esc;

    public boolean LMB;
    public boolean RMB;
    public boolean processedClick;
    public Vector2 mouseClickPos = new Vector2();
    public Vector2 mapClickPos = new Vector2();

    public boolean debug;

    int screenWidth;
    int screenHeight;

    public Control ( int screenWidth, int screenHeight, OrthographicCamera camera) {
        this.camera = camera;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
    }

    private void setMouseClickPos ( int screenX, int screenY) {
        mouseClickPos.set(screenX, screenHeight-screenY);
        mapClickPos.set(getMapCords(mouseClickPos));
    }

    public Vector2 getMapCords(Vector2 mouseCords) {
        Vector3 vector3 = new Vector3(mouseCords.x, screenHeight - mouseCords.y, 0);
        this.camera.unproject(vector3);
        return new Vector2(vector3.x, vector3.y);
    }
    @Override
    public boolean keyDown(int keycode) {
        //Deniz added additionally
        if (keycode == Keys.E) {
            collect = true;
        }
        //Deniz added additionally
        switch (keycode) {
            case Keys.E:
                collect = true;
                break;
            case Keys.DOWN:
            case Keys.S:
                down = true;
                break;
            case Keys.UP:
            case Keys.W:
                up = true;
                break;
            case Keys.LEFT:
            case Keys.A:
                left = true;
                break;
            case Keys.RIGHT:
            case Keys.D:
                right = true;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //Deniz added additionally
        //Deniz added additionally
        switch (keycode) {
            case Keys.E:
                collect = false;
                break;
            case Keys.DOWN:
            case Keys.S:
                down = false;
                break;
            case Keys.UP:
            case Keys.W:
                up = false;
                break;
            case Keys.LEFT:
            case Keys.A:
                left = false;
                break;
            case Keys.RIGHT:
            case Keys.D:
                right = false;
                break;
            case Keys.ESCAPE:
                //Gdx.app.exit();
                esc = true;
                break;
            case Keys.BACKSPACE:
                debug = !debug;
                break;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char ch) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer == 0 && button == 0) {
            LMB = true;
        }
        else if (pointer == 0 && button == 0) {
            RMB = true;
        }

        setMouseClickPos(screenX, screenY);
        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer == 0 && button == 0){
            LMB = false;
            processedClick = false;
        }
        else if (pointer == 0 && button == 0){
            RMB = false;
        }

        setMouseClickPos(screenX, screenY);
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        setMouseClickPos(screenX, screenY);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }


    public boolean scrolled(int amount) {
        return false;
    }
}
