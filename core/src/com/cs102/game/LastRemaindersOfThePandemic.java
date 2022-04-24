package com.cs102.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cs102.game.screens.HomePage;
import com.cs102.game.screens.ScreenType;

import java.util.EnumMap;
import java.util.HashMap;

public class LastRemaindersOfThePandemic extends Game {
	private Skin skin;
	//initialize the ortographic camera
	public OrthographicCamera gameCamera;
	private static final String TAG = LastRemaindersOfThePandemic.class.getSimpleName();
	private EnumMap<ScreenType, Screen> screenCache;
	private Viewport viewport;
	private Stage stage;
	private SpriteBatch batch;

	// Yavuz add AppPreferences
	private AppPreferences preferences;

	//public static final short BIT_CIRCLE = 1 << 0;
	//public static final short BIT_BOX = 1 << 1;
	public static final float UNIT_SCALE = 1 / 16f;
	public static final short BIT_PLAYER = 1 << 0;
	public static final short BIT_GROUND = 1 << 1;
	public static final short BIT_ITEM = 1 << 2;
	private Box2DDebugRenderer b2dDebugRenderer;
	private static final float FIXED_TIME_STEP = 1 / 60f;
	private float accumulator;
	private World world;
	private WorldContactListener worldContactListener;
	//Yavuz add AssetManager
	private AssetManager assetManager;

	private  Control control;

	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		batch = new SpriteBatch();

		accumulator = 0;
		Box2D.init();
		world = new World(new Vector2(0, 0), true);
		worldContactListener = new WorldContactListener();
		world.setContactListener(worldContactListener);
		b2dDebugRenderer = new Box2DDebugRenderer();

		//initialize asset manager
		assetManager = new AssetManager();
		assetManager.setLoader(TiledMap.class,new TmxMapLoader(assetManager.getFileHandleResolver()));
		skinInitializer();
		//Yavuz initialize the stage
		stage = new Stage(new FitViewport(1280,720), batch);

		gameCamera = new OrthographicCamera();
		viewport = new FitViewport(1280, 720, gameCamera);
		screenCache = new EnumMap<ScreenType, Screen>(ScreenType.class);
		setScreen(ScreenType.MENU);
		preferences = new AppPreferences();

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
		stage.getViewport().apply();
		stage.act();
		stage.draw();


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
		batch.dispose();
		stage.dispose();
	}
	public SpriteBatch getSpriteBatch() {
		return this.batch;
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

  //Deniz added additionally
	public WorldContactListener getWorldContactListener() {
		return this.worldContactListener;
	}
  
	//Deniz added additionally
	//getter of skin and stage
	public Stage getStage() {
		return this.stage;
	}
	public Skin getSkin() {
		return this.skin;
	}

	public void skinInitializer(){
		final ObjectMap<String, Object> resources = new ObjectMap<String, Object>();
		// generate tff bitmap font
		final FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("assets/ui/SaucerBB.ttf"));
		final FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontParameter.minFilter = Texture.TextureFilter.Linear;
		fontParameter.magFilter = Texture.TextureFilter.Linear;
		final int[] sizes = {16,20,26,32};
		for(int size : sizes){
			fontParameter.size = size;
			fontGenerator.generateFont(fontParameter);
			resources.put("font_"+size, fontGenerator.generateFont(fontParameter));
		}
		fontGenerator.dispose();
		// LOAD SKIN
		final SkinLoader.SkinParameter skinParameter = new SkinLoader.SkinParameter("assets/uiskin.atlas",resources);
		assetManager.load("assets/uiskin.json", Skin.class, skinParameter);
		assetManager.finishLoading();
		skin = assetManager.get("assets/uiskin.json");

	}

	// game include basement map and three other neigbouring maps
	// Maps have isUnlocked boolean value
	// Basement map is unlocked by default
	// other maps are locked by default
	// when player reach the map, map is unlocked
	//  if map is unlocked, player can go to that map
	// check map is unlocked or not
	// if map is unlocked, setScreen(map)
	// if map is locked, pop up a dialog box

	// update setScreen method
	// if map is unlocked, setScreen(map)
	// if map is locked, pop up a dialog box
}
