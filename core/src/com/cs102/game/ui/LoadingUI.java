package com.cs102.game.ui;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.cs102.game.LastRemaindersOfThePandemic;

public class LoadingUI extends Table {
    private final TextButton textButton;

    private final TextButton pressToPlay;
    public LoadingUI(final LastRemaindersOfThePandemic game) {
        super(game.getSkin());
        this.setFillParent(true);

        // create text button
        textButton = new TextButton("Loading...", game.getSkin());
        textButton.getLabel().setWrap(true);

        pressToPlay = new TextButton("Press Any Key", game.getSkin());
        pressToPlay.getLabel().setWrap(true); // set wrap true to allow text to be longer than the button
        pressToPlay.setVisible(false);
        add(pressToPlay).expand().fill().center().row();
        add(textButton).expand().fill().bottom().row();
        bottom();
    }



}
