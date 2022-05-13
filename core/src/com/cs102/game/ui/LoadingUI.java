package com.cs102.game.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.input.GameKeys;

public class LoadingUI extends GameRenderer {
    private final TextButton textButton;

    private final TextButton pressToPlay;
    public LoadingUI(final LastRemaindersOfThePandemic game) {
        super(game.getSkin());
        this.setFillParent(true);
        this.setBackground(new NinePatchDrawable(new NinePatch(new Texture("main_menu.jpg"), 1, 1, 1, 1)));

        // create text button
        textButton = new TextButton("Loading...", game.getSkin());
        textButton.getLabel().setWrap(true);

        pressToPlay = new TextButton("Press Any Key", game.getSkin());
        pressToPlay.getLabel().setWrap(true);// set wrap true to allow text to be longer than the button
        pressToPlay.setVisible(false);
        pressToPlay.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.getInputManager().onKeyDown(GameKeys.SELECT);
                return true;
            }
        });
        pressToPlay.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.getInputManager().onKeyDown(GameKeys.SELECT);
                return true;
            }
                                }
        );
        add(pressToPlay).expand().fill().row();
        add(textButton).expand().fill().row();
    }


    public void setProgress(float progress) {
        textButton.setText("Loading... " + (int) (progress * 100) + "%");
        textButton.pack();
        pressToPlay.setVisible(progress == 1);
    }
}
