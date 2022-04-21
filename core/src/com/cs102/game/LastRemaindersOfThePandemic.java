package com.cs102.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
	//initialize the ortographic camera
	public OrthographicCamera gameCamera;
	private static final String TAG = LastRemaindersOfThePandemic.class.getSimpleName();
	private EnumMap<ScreenType, Screen> screenCache;
	private Viewport viewport;

	// Yavuz add AppPreferences
	private AppPreferences preferences;
	public static final short BIT_CIRCLE = 1 << 0;
	public static final short BIT_BOX = 1 << 1;
	public static final short BIT_GROUND = 1 << 2;
	private Box2DDebugRenderer b2dDebugRenderer;
	private static final float FIXED_TIME_STEP = 1 / 60f;
	private float accumulator;
	private World world;
	private WorldContactListener worldContactListener;
	//Yavuz add AssetManager
	private AssetManager assetManager;
	public void create () {
    accumulator = 0;
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		gameCamera = new OrthographicCamera();
		preferences = new AppPreferences();
		screenCache = new EnumMap<ScreenType, Screen>(ScreenType.class);
		viewport = new ExtendViewport(1280, 720, gameCamera);
		setScreen(ScreenType.MENU);
		b2dDebugRenderer = new Box2DDebugRenderer();

		Box2D.init();
		world = new World(new Vector2(0, -50.0f), true);

    	worldContactListener = new WorldContactListener();
		world.setContactListener(worldContactListener);
		//initialize asset manager
		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class,new TmxMapLoader(assetManager.getFileHandleResolver()));
	}

	public World getWorld() {
		return this.world;
	}

	public Box2DDebugRenderer getB2dDebugRenderer() {
		return b2dDebugRenderer;
	}

	public Viewport getViewport() {
		return this.viewport;
	}
	@Override
	public void render () {
		super.render();

		accumulator += Math.min(0.25f, Gdx.graphics.getDeltaTime());
		while (accumulator >= FIXED_TIME_STEP) {
			world.step(FIXED_TIME_STEP, 6,2);
			accumulator -= FIXED_TIME_STEP;
		}

		//final float alpha = accumulator / FIXED_TIME_STEP;

	}

	//Yavuz Set screen method
	public void setScreen(ScreenType screenType) {
		final Screen screen = screenCache.get(screenType);
		if (screen == null) {
			try {
				Gdx.app.debug(TAG, "Creating new screen: " + screenType);
				final Screen screenInstance = (Screen)ClassReflection.getConstructor(screenType.getScreenClass(),LastRemaindersOfThePandemic.class).newInstance(this);
				screenCache.put(screenType, screenInstance);
				super.setScreen(screenInstance);
			}
			catch (Exception e) {
				throw new GdxRuntimeException("Could not instantiate screen: " + screenType, e);
			}
		}
		else {
			Gdx.app.debug(TAG, "Screen already instantiated: " + screenType);
			super.setScreen(screen);
		}
	}

  
	@Override
	public void dispose() {
		super.dispose();
		b2dDebugRenderer.dispose();
		world.dispose();
		assetManager.dispose();
	}


	//AssetManager getter
	public AssetManager getAssetManager() {
		return this.assetManager;
	}
	//get camera
	public OrthographicCamera getGameCamera() {
		return this.gameCamera;

	}

	//get preferences
	public AppPreferences getPreferences() {
		return this.preferences;
	}

}
