package com.cs102.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cs102.game.screens.HomePage;
import com.cs102.game.screens.ScreenType;

import java.util.EnumMap;

public class LastRemaindersOfThePandemic extends Game {
	private static final String TAG = LastRemaindersOfThePandemic.class.getSimpleName();
	private EnumMap<ScreenType, Screen> screenCache;
	private Viewport viewport;

	private World world;
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		screenCache = new EnumMap<ScreenType, Screen>(ScreenType.class);
		viewport = new ExtendViewport(1280, 720);
		setScreen(ScreenType.MENU);


		Box2D.init();
		world = new World(new Vector2(0, 9.01f), true);
	}

	public World getWorld() {
		return this.world;
	}

	public Viewport getViewport() {
		return this.viewport;
	}
	public void render () {
		super.render();
	}

	//Yavuz Set screen method
	public void setScreen(ScreenType screenType) {
		final Screen screen = screenCache.get(screenType);
		if (screen == null) {
			try {
				final Screen screenInstance = (Screen)ClassReflection.getConstructor(screenType.getScreenClass(),LastRemaindersOfThePandemic.class).newInstance(this);
				screenCache.put(screenType, screenInstance);
				super.setScreen(screenInstance);
			}
			catch (Exception e) {
				throw new GdxRuntimeException("Could not instantiate screen: " + screenType, e);
			}
		}
		else {
			super.setScreen(screen);
		}
	}

}
