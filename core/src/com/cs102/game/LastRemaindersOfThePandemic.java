package com.cs102.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.cs102.game.screens.HomePage;

public class LastRemaindersOfThePandemic extends Game {

	public void create () {
		setScreen(new HomePage(this));
	}

}
